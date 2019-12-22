package ru.together.auth.services;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.query.ObjectSelect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.together.auth.interfaces.IAuthService;
import ru.together.auth.models.*;
import ru.together.database.entities.User;
import ru.together.database.entities.UserSession;
import ru.together.database.services.DatabaseService;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@Service
public class AuthService implements IAuthService {

    @Autowired
    DatabaseService databaseService;

    ObjectContext objectContext;

    protected static final Logger log = LoggerFactory.getLogger(AuthService.class);

    @PostConstruct
    public void init() {
        objectContext = databaseService.getContext();
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        if (checkUserId(request.getUserId())) {

            User user = ObjectSelect.query(User.class)
                    .where(User.USER_ID.eq(request.getUserId()))
                    .selectFirst(objectContext);

            UserSession session = objectContext.newObject(UserSession.class);
            session.setCreatedDate(LocalDateTime.now());

            session.setSessionToUser(user);

            objectContext.commitChanges();

            return LoginResponse.builder()
                    .success(true)
                    .build();

        } else return LoginResponse.builder()
                .success(false)
                .error("User with this id doesn't exist")
                .build();
    }

    @Override
    public SignUpResponse singUp(SignUpRequest request) {
        if (checkEmailExist(request.getEmail())) {
            User user = objectContext.newObject(User.class);

            user.setCreatedDate(LocalDateTime.now());
            user.setName(request.getName());
            user.setEmail(request.getEmail());
            user.setPhone(request.getPhone());
            user.setUserId(ObjectSelect.columnQuery(User.class, User.USER_ID).selectFirst(objectContext) + 1);
            user.setIsVerified(false);

            objectContext.commitChanges();

            return SignUpResponse.builder()
                    .success(true)
                    .build();

        } else return SignUpResponse.builder()
                .success(false)
                .error("User with this email exist")
                .build();
    }

    @Override
    public SessionResponse session(SessionRequest request) {
        return null;
    }

    //TODO: Check work
    private boolean checkEmailExist(String phone) {
        try {
            User user = ObjectSelect.query(User.class)
                    .where(User.EMAIL.eq(phone))
                    .selectFirst(objectContext);

            return user == null;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    //TODO: Check work
    private boolean checkUserId(int UserId) {
        try {
            User user = ObjectSelect.query(User.class)
                    .where(User.USER_ID.eq(UserId))
                    .selectFirst(objectContext);
            return user != null;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

}
