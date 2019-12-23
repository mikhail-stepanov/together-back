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
import ru.together.users.interfaces.IUserAdminService;
import ru.together.users.models.*;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserAdminService implements IUserAdminService {

    @Autowired
    DatabaseService databaseService;

    ObjectContext objectContext;

    protected static final Logger log = LoggerFactory.getLogger(UserAdminService.class);

    @PostConstruct
    public void init() {
        objectContext = databaseService.getContext();
    }

    @Override
    public GetUserResponse get(GetUserRequest request) {

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
                .isVerified(user.isIsVerified())
                .build();
    }

    @Override
    public ListUserResponse list() {

        List<UserModel> response = new ArrayList<>();
        List<User> users = ObjectSelect.query(User.class).select(objectContext);

        users.forEach(user -> {
            response.add(UserModel.builder()
                    .id((Integer) user.getObjectId().getIdSnapshot().get("id"))
                    .userId(user.getUserId())
                    .name(user.getName())
                    .phone(user.getPhone())
                    .email(user.getEmail())
                    .isVerified(user.isIsVerified())
                    .build());
        });

        return ListUserResponse.builder()
                .users(response)
                .build();

    }

    @Override
    public UpdateUserResponse update(UpdateUserRequest request) {

        try {

            User user = SelectById.query(User.class, request.getId()).selectFirst(objectContext);
            user.setUserId(Optional.ofNullable(request.getUserId()).orElse(user.getUserId()));
            user.setEmail(Optional.ofNullable(request.getEmail()).orElse(user.getEmail()));
            user.setName(Optional.ofNullable(request.getName()).orElse(user.getName()));
            user.setPhone(Optional.ofNullable(request.getPhone()).orElse(user.getPhone()));
            user.setIsVerified(Optional.ofNullable(request.getIsVerified()).orElse(user.isIsVerified()));

            objectContext.commitChanges();

            return UpdateUserResponse.builder()
                    .id((Integer) user.getObjectId().getIdSnapshot().get("id"))
                    .name(user.getName())
                    .email(user.getEmail())
                    .isVerified(user.isIsVerified())
                    .userId(user.getUserId())
                    .phone(user.getPhone())
                    .build();

        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
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
            log.error(e.getMessage());
            return RemoveUserResponse.builder()
                    .success(false)
                    .build();
        }
    }
}
