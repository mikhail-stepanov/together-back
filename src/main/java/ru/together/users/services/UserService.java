package ru.together.users.services;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.query.ObjectSelect;
import org.apache.cayenne.query.SelectById;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.together.common.exceptions.CommonException;
import ru.together.common.exceptions.ObjectNotFoundException;
import ru.together.database.entities.Images;
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
    public GetUserResponse get(GetUserRequest request) throws CommonException {
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
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .phone(user.getPhone())
                    .email(user.getEmail())
                    .facebook(user.getFacebook())
                    .instagram(user.getInstagram())
                    .picId((Integer) user.getUserToPic().getObjectId().getIdSnapshot().get("id"))
                    .isVerified(user.isIsVerified())
                    .build();
        } catch (Exception e) {
            throw new ObjectNotFoundException(Integer.toString(request.getId()), "Error while getting user");
        }
    }

    @Override
    public List<UserModel> list() throws CommonException {
        try {
            List<UserModel> response = new ArrayList<>();
            List<User> users = ObjectSelect.query(User.class).
                    where(User.DELETED_DATE.isNull())
                    .limit(20)
                    .select(objectContext);

            users.forEach(user -> {
                response.add(UserModel.builder()
                        .id((Integer) user.getObjectId().getIdSnapshot().get("id"))
                        .userId(user.getUserId())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .phone(user.getPhone())
                        .email(user.getEmail())
                        .facebook(user.getFacebook())
                        .instagram(user.getInstagram())
                        .picId((Integer) user.getUserToPic().getObjectId().getIdSnapshot().get("id"))
                        .isVerified(user.isIsVerified())
                        .build());
            });

            return response;
        } catch (Exception e) {
            throw new ObjectNotFoundException(e.getMessage(), "Error while getting list of users");
        }
    }

    @Override
    public List<UserModel> listVerified() throws CommonException {
        try {
            List<UserModel> response = new ArrayList<>();
            List<User> users = ObjectSelect.query(User.class).
                    where(User.DELETED_DATE.isNull()).and(User.IS_VERIFIED.isTrue())
                    .limit(20)
                    .select(objectContext);

            users.forEach(user -> {
                response.add(UserModel.builder()
                        .id((Integer) user.getObjectId().getIdSnapshot().get("id"))
                        .userId(user.getUserId())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .phone(user.getPhone())
                        .email(user.getEmail())
                        .facebook(user.getFacebook())
                        .instagram(user.getInstagram())
                        .picId((Integer) user.getUserToPic().getObjectId().getIdSnapshot().get("id"))
                        .isVerified(user.isIsVerified())
                        .build());
            });

            return response;
        } catch (Exception e) {
            throw new ObjectNotFoundException(e.getMessage(), "Error while getting list of users");
        }
    }

    @Override
    public List<UserModel> listUnverified() throws CommonException {
        try {
            List<UserModel> response = new ArrayList<>();
            List<User> users = ObjectSelect.query(User.class).
                    where(User.DELETED_DATE.isNull()).and(User.IS_VERIFIED.isFalse())
                    .limit(20)
                    .select(objectContext);

            users.forEach(user -> {
                response.add(UserModel.builder()
                        .id((Integer) user.getObjectId().getIdSnapshot().get("id"))
                        .userId(user.getUserId())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .phone(user.getPhone())
                        .email(user.getEmail())
                        .facebook(user.getFacebook())
                        .instagram(user.getInstagram())
                        .picId((Integer) user.getUserToPic().getObjectId().getIdSnapshot().get("id"))
                        .isVerified(user.isIsVerified())
                        .build());
            });

            return response;
        } catch (Exception e) {
            throw new ObjectNotFoundException(e.getMessage(), "Error while getting list of users");
        }
    }

    @Override
    public List<UserModel> listBlocked() throws CommonException {
        try {
            List<UserModel> response = new ArrayList<>();
            List<User> users = ObjectSelect.query(User.class).
                    where(User.DELETED_DATE.isNull()).and(User.IS_BLOCKED.isTrue())
                    .limit(20)
                    .select(objectContext);

            users.forEach(user -> {
                response.add(UserModel.builder()
                        .id((Integer) user.getObjectId().getIdSnapshot().get("id"))
                        .userId(user.getUserId())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .phone(user.getPhone())
                        .email(user.getEmail())
                        .facebook(user.getFacebook())
                        .instagram(user.getInstagram())
                        .picId((Integer) user.getUserToPic().getObjectId().getIdSnapshot().get("id"))
                        .isVerified(user.isIsVerified())
                        .build());
            });

            return response;
        } catch (Exception e) {
            throw new ObjectNotFoundException(e.getMessage(), "Error while getting list of users");
        }
    }

    @Override
    public List<UserModel> searchUser(SearchUserRequest request) throws CommonException {
        try {
            List<UserModel> response = new ArrayList<>();
            List<User> usersLastname = ObjectSelect.query(User.class).
                    where(User.DELETED_DATE.isNull()).and(User.LAST_NAME.like("%" + request.getLastName() + "%"))
                    .limit(20)
                    .select(objectContext);

            usersLastname.forEach(user -> {
                response.add(UserModel.builder()
                        .id((Integer) user.getObjectId().getIdSnapshot().get("id"))
                        .userId(user.getUserId())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .phone(user.getPhone())
                        .email(user.getEmail())
                        .facebook(user.getFacebook())
                        .instagram(user.getInstagram())
                        .picId((Integer) user.getUserToPic().getObjectId().getIdSnapshot().get("id"))
                        .isVerified(user.isIsVerified())
                        .build());
            });

            List<User> usersPhone = ObjectSelect.query(User.class).
                    where(User.DELETED_DATE.isNull()).and(User.PHONE.like("%" + request.getLastName() + "%"))
                    .limit(20)
                    .select(objectContext);

            usersPhone.forEach(user -> {
                response.add(UserModel.builder()
                        .id((Integer) user.getObjectId().getIdSnapshot().get("id"))
                        .userId(user.getUserId())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .phone(user.getPhone())
                        .email(user.getEmail())
                        .facebook(user.getFacebook())
                        .instagram(user.getInstagram())
                        .picId((Integer) user.getUserToPic().getObjectId().getIdSnapshot().get("id"))
                        .isVerified(user.isIsVerified())
                        .build());
            });

            if (request.getLastName().matches("[0-9]+") && request.getLastName().length() == 4) {
                List<User> usersId = SelectById.query(User.class, Integer.parseInt(request.getLastName())).select(objectContext);

                usersId.forEach(user -> {
                    response.add(UserModel.builder()
                            .id((Integer) user.getObjectId().getIdSnapshot().get("id"))
                            .userId(user.getUserId())
                            .firstName(user.getFirstName())
                            .lastName(user.getLastName())
                            .phone(user.getPhone())
                            .email(user.getEmail())
                            .facebook(user.getFacebook())
                            .instagram(user.getInstagram())
                            .picId((Integer) user.getUserToPic().getObjectId().getIdSnapshot().get("id"))
                            .isVerified(user.isIsVerified())
                            .build());
                });
            }

            return response;
        } catch (Exception e) {
            throw new ObjectNotFoundException(e.getMessage(), "Error while searching of users");
        }
    }

    @Override
    public BlockUserResponse block(BlockUserRequest request) throws CommonException {
        try {
            User user = ObjectSelect.query(User.class)
                    .where(User.USER_ID.eq(request.getUserId()))
                    .selectFirst(objectContext);

            user.setIsBlocked(true);

            objectContext.commitChanges();

            return BlockUserResponse.builder()
                    .success(true)
                    .build();

        } catch (Exception e) {
            throw new CommonException(e.getMessage(), "Error while blocking user");
        }
    }

    @Override
    public BlockUserResponse unblock(BlockUserRequest request) throws CommonException {
        try {
            User user = ObjectSelect.query(User.class)
                    .where(User.USER_ID.eq(request.getUserId()))
                    .selectFirst(objectContext);

            user.setIsBlocked(false);

            objectContext.commitChanges();

            return BlockUserResponse.builder()
                    .success(true)
                    .build();

        } catch (Exception e) {
            throw new CommonException(e.getMessage(), "Error while unblocking user");
        }
    }

    @Override
    public UpdateUserResponse update(UpdateUserRequest request) throws CommonException {
        try {
            User user = ObjectSelect.query(User.class)
                    .where(User.USER_ID.eq(request.getUserId()))
                    .selectFirst(objectContext);

            if (request.getPicId() != null) {
                Images video = SelectById.query(Images.class, request.getPicId()).selectFirst(objectContext);
                user.setUserToPic(video);
            }

            user.setUserId(Optional.ofNullable(request.getUserId()).orElse(user.getUserId()));
            user.setEmail(Optional.ofNullable(request.getEmail()).orElse(user.getEmail()));
            user.setFirstName(Optional.ofNullable(request.getFirstName()).orElse(user.getFirstName()));
            user.setLastName(Optional.ofNullable(request.getLastName()).orElse(user.getLastName()));
            user.setPhone(Optional.ofNullable(request.getPhone()).orElse(user.getPhone()));
            user.setFacebook(Optional.ofNullable(request.getFacebook()).orElse(user.getFacebook()));
            user.setInstagram(Optional.ofNullable(request.getInstagram()).orElse(user.getInstagram()));
            user.setIsVerified(Optional.ofNullable(request.getIsVerified()).orElse(user.isIsVerified()));

            objectContext.commitChanges();

            return UpdateUserResponse.builder()
                    .id((Integer) user.getObjectId().getIdSnapshot().get("id"))
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .isVerified(user.isIsVerified())
                    .userId(user.getUserId())
                    .phone(user.getPhone())
                    .facebook(user.getFacebook())
                    .instagram(user.getInstagram())
                    .picId((Integer) user.getUserToPic().getObjectId().getIdSnapshot().get("id"))
                    .build();

        } catch (Exception e) {
            throw new CommonException(e.getMessage(), "Error while updating user");
        }
    }

    @Override
    public UpdateUserResponse verify(UpdateUserRequest request) throws CommonException {
        try {
            User user = ObjectSelect.query(User.class)
                    .where(User.USER_ID.eq(request.getUserId()))
                    .selectFirst(objectContext);

            user.setIsVerified(true);

            objectContext.commitChanges();

            emailService.sendSimpleMessage(SendEmailRequest.builder()
                    .userId(user.getUserId())
                    .build());

            return UpdateUserResponse.builder()
                    .id((Integer) user.getObjectId().getIdSnapshot().get("id"))
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .isVerified(user.isIsVerified())
                    .userId(user.getUserId())
                    .phone(user.getPhone())
                    .facebook(user.getFacebook())
                    .instagram(user.getInstagram())
                    .picId((Integer) user.getUserToPic().getObjectId().getIdSnapshot().get("id"))
                    .build();

        } catch (Exception e) {
            throw new CommonException(e.getMessage(), "Error while verifying user");
        }
    }

    @Override
    public ResendEmailResponse resendEmail(ResendEmailRequest request) throws CommonException {
        try {
            emailService.sendSimpleMessage(SendEmailRequest.builder()
                    .userId(request.getUserId())
                    .build());

            return ResendEmailResponse.builder()
                    .success(true)
                    .build();

        } catch (Exception e) {
            throw new CommonException(e.getMessage(), "Error while resending email");
        }
    }

    @Override
    public RemoveUserResponse remove(RemoveUserRequest request) throws CommonException {
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
            throw new CommonException(e.getMessage(), "Error while removing user");
        }
    }
}
