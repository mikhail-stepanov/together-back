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
import ru.together.smtp.models.SendEmailRequest;
import ru.together.smtp.services.EmailService;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Random;

@Service
public class AuthService implements IAuthService {

    @Autowired
    DatabaseService databaseService;

    @Autowired
    EmailService emailService;

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
            if (user.isIsVerified()) {

                UserSession session = objectContext.newObject(UserSession.class);
                session.setCreatedDate(LocalDateTime.now());

                session.setSessionToUser(user);

                objectContext.commitChanges();

                return LoginResponse.builder()
                        .success(true)
                        .build();
            } else return LoginResponse.builder()
                    .success(false)
                    .error("User with this id doesn't verified")
                    .build();

        } else return LoginResponse.builder()
                .success(false)
                .error("User with this id doesn't exist")
                .build();
    }

    @Override
    public SignUpResponse singUp(SignUpRequest request) {
        if (checkEmailExist(request.getEmail())) {

            Random random = new Random();
            int newId;

            do {
                newId = random.nextInt(10000);
            } while (checkUserId(newId));

            User user = objectContext.newObject(User.class);
            user.setCreatedDate(LocalDateTime.now());
            user.setName(request.getName());
            user.setEmail(request.getEmail());
            user.setPhone(request.getPhone());
            user.setUserId(newId);
            user.setInstagram(request.getInstagram());
            user.setFacebook(request.getFacebook());
            user.setIsVerified(true);

            objectContext.commitChanges();

            emailService.sendSimpleMessage(SendEmailRequest.builder()
                    .userId(newId)
                    .build());

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

    @Override
    public InfoResponse info(InfoRequest request) {
        if (checkUserId(request.getUserId())) {

            User user = ObjectSelect.query(User.class)
                    .where(User.USER_ID.eq(request.getUserId()))
                    .selectFirst(objectContext);

            return InfoResponse.builder()
                    .success(true)
                    .name(user.getName())
                    .userId(user.getUserId())
                    .build();

        } else return InfoResponse.builder()
                .success(false)
                .error("User with this id doesn't exist")
                .build();
    }

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
