package ru.together.users.services;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.query.ObjectSelect;
import org.apache.cayenne.query.SelectById;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.together.database.entities.User;
import ru.together.database.services.DatabaseService;
import ru.together.smtp.models.SendEmailRequest;
import ru.together.smtp.services.EmailService;
import ru.together.users.interfaces.IUserService;
import ru.together.users.models.*;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    DatabaseService databaseService;

    @Autowired
    EmailService emailService;

    ObjectContext objectContext;

    protected static final Logger log = LoggerFactory.getLogger(UserService.class);

    @PostConstruct
    public void init() {
        objectContext = databaseService.getContext();
    }

    @Override
    public GetUserResponse get(GetUserRequest request) {
        try {
            User user = null;
            //by id
            if (request.getId() != null) {

                user = SelectById.query(User.class, request.getId()).selectFirst(objectContext);
            }
            //by userId
            else if (request.getUserId() != null) {

                user = ObjectSelect.query(User.class)
                        .where(User.USER_ID.eq(request.getUserId()))
                        .selectFirst(objectContext);
            }
            //by email
            else if (request.getEmail() != null) {
                user = ObjectSelect.query(User.class).where(User.EMAIL.eq(request.getEmail()))
                        .selectFirst(objectContext);
            }

            return GetUserResponse.builder()
                    .id((Integer) user.getObjectId().getIdSnapshot().get("id"))
                    .userId(user.getUserId())
                    .name(user.getName())
                    .phone(user.getPhone())
                    .email(user.getEmail())
                    .facebook(user.getFacebook())
                    .instagram(user.getInstagram())
                    .picUrl(user.getPicUrl())
                    .isVerified(user.isIsVerified())
                    .build();
        } catch (Exception e) {
            log.error("Exception while getting user: " + e.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public ListUserResponse list() {
        try {
            List<UserModel> response = new ArrayList<>();
            List<User> users = ObjectSelect.query(User.class).
                    where(User.DELETED_DATE.isNull())
                    .select(objectContext);

            users.forEach(user -> {
                response.add(UserModel.builder()
                        .id((Integer) user.getObjectId().getIdSnapshot().get("id"))
                        .userId(user.getUserId())
                        .name(user.getName())
                        .phone(user.getPhone())
                        .email(user.getEmail())
                        .facebook(user.getFacebook())
                        .instagram(user.getInstagram())
                        .picUrl(user.getPicUrl())
                        .isVerified(user.isIsVerified())
                        .build());
            });

            return ListUserResponse.builder()
                    .users(response)
                    .build();
        } catch (Exception e) {
            log.error("Exception while getting list of users: " + e.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public UpdateUserResponse update(UpdateUserRequest request) {
        try {
            User user = SelectById.query(User.class, request.getId()).selectFirst(objectContext);
            user.setUserId(Optional.ofNullable(request.getUserId()).orElse(user.getUserId()));
            user.setEmail(Optional.ofNullable(request.getEmail()).orElse(user.getEmail()));
            user.setName(Optional.ofNullable(request.getName()).orElse(user.getName()));
            user.setPhone(Optional.ofNullable(request.getPhone()).orElse(user.getPhone()));
            user.setFacebook(Optional.ofNullable(request.getFacebook()).orElse(user.getFacebook()));
            user.setInstagram(Optional.ofNullable(request.getInstagram()).orElse(user.getInstagram()));
            user.setPicUrl(Optional.ofNullable(request.getPicUrl()).orElse(user.getPicUrl()));
            user.setIsVerified(Optional.ofNullable(request.getIsVerified()).orElse(user.isIsVerified()));

            objectContext.commitChanges();

            return UpdateUserResponse.builder()
                    .id((Integer) user.getObjectId().getIdSnapshot().get("id"))
                    .name(user.getName())
                    .email(user.getEmail())
                    .isVerified(user.isIsVerified())
                    .userId(user.getUserId())
                    .phone(user.getPhone())
                    .facebook(user.getFacebook())
                    .instagram(user.getInstagram())
                    .picUrl(user.getPicUrl())
                    .build();

        } catch (Exception e) {
            log.error("Exception while updating user: " + e.getMessage());
            return null;
        }
    }

    @Override
    public UpdateUserResponse verify(UpdateUserRequest request) {
        try {
            User user = SelectById.query(User.class, request.getId()).selectFirst(objectContext);

            user.setIsVerified(Optional.ofNullable(request.getIsVerified()).orElse(user.isIsVerified()));

            objectContext.commitChanges();

            emailService.sendSimpleMessage(SendEmailRequest.builder()
                    .userId(user.getUserId())
                    .build());

            return UpdateUserResponse.builder()
                    .id((Integer) user.getObjectId().getIdSnapshot().get("id"))
                    .name(user.getName())
                    .email(user.getEmail())
                    .isVerified(user.isIsVerified())
                    .userId(user.getUserId())
                    .phone(user.getPhone())
                    .facebook(user.getFacebook())
                    .instagram(user.getInstagram())
                    .picUrl(user.getPicUrl())
                    .build();

        } catch (Exception e) {
            log.error("Exception while resending email: " + e.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public ResendEmailResponse resendEmail(ResendEmailRequest request) {
        try {
            emailService.sendSimpleMessage(SendEmailRequest.builder()
                    .userId(request.getUserId())
                    .build());

            return ResendEmailResponse.builder()
                    .success(true)
                    .build();

        } catch (Exception e) {
            log.error("Exception while resending email: " + e.getLocalizedMessage());
            return ResendEmailResponse.builder()
                    .success(false)
                    .build();
        }
    }

    @Override
    public RemoveUserResponse remove(RemoveUserRequest request) {
        try {
            User user = ObjectSelect.query(User.class)
                    .where(User.USER_ID.eq(request.getUserId()))
                    .selectFirst(objectContext);

            user.setDeletedDate(LocalDateTime.now());

            objectContext.commitChanges();

            return RemoveUserResponse.builder()
                    .success(true)
                    .build();
        } catch (Exception e) {
            log.error("Exception while removing user: " + e.getMessage());
            return RemoveUserResponse.builder()
                    .success(false)
                    .build();
        }
    }
}
