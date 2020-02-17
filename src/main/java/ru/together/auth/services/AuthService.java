package ru.together.auth.services;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.query.ObjectSelect;
import org.apache.cayenne.query.SelectById;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import ru.together.auth.interfaces.IAuthService;
import ru.together.auth.models.*;
import ru.together.common.exceptions.BadRequestException;
import ru.together.common.exceptions.CommonException;
import ru.together.common.exceptions.ObjectNotFoundException;
import ru.together.database.entities.Images;
import ru.together.database.entities.User;
import ru.together.database.entities.UserSession;
import ru.together.database.services.DatabaseService;
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
    public LoginIdResponse loginId(LoginIdRequest request) throws CommonException {
        if (checkUserId(request.getUserId())) {

            User user = ObjectSelect.query(User.class)
                    .where(User.USER_ID.eq(request.getUserId()))
                    .selectFirst(objectContext);

            if (user.isIsVerified() && !user.isIsBlocked()) {

                UserSession userSession = ObjectSelect.query(UserSession.class)
                        .where(UserSession.SESSION_TO_USER.eq(user))
                        .selectFirst(objectContext);

                if (userSession == null) {
                    return LoginIdResponse.builder()
                            .success(true)
                            .status(1)
                            .build();
                } else {
                    return LoginIdResponse.builder()
                            .success(true)
                            .status(2)
                            .build();
                }

            } else
                throw new BadRequestException(Integer.toString(request.getUserId()), "Unverified");
        } else
            throw new ObjectNotFoundException(User.class.toString(), Integer.toString(request.getUserId()));
    }

    @Override
    public LoginPassResponse loginPass(LoginPassRequest request) throws CommonException {
        if (checkUserId(request.getUserId())) {

            User user = ObjectSelect.query(User.class)
                    .where(User.USER_ID.eq(request.getUserId()))
                    .selectFirst(objectContext);

            if (user.getPassword().equals(password(request.getPassword()))) {

                UserSession session = objectContext.newObject(UserSession.class);
                session.setCreatedDate(LocalDateTime.now());
                session.setSessionToUser(user);

                objectContext.commitChanges();

                return LoginPassResponse.builder()
                        .success(true)
                        .build();

            } else
                throw new BadRequestException(Integer.toString(request.getUserId()), "Wrong Password");

        } else throw new ObjectNotFoundException(Integer.toString(request.getUserId()), "User doesn't exist");
    }

    @Override
    public SignUpResponse singUp(SignUpRequest request) throws CommonException {
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
            user.setIsVerified(false);
            user.setIsBlocked(false);

            Images image = SelectById.query(Images.class, 1).selectFirst(objectContext);

            user.setUserToPic(image);

            objectContext.commitChanges();

            return SignUpResponse.builder()
                    .success(true)
                    .build();

        } else
            throw new BadRequestException(request.getEmail(), "User with this email exist");
    }

    @Override
    public SessionResponse session(SessionRequest request) throws CommonException {
        try {
            User user = ObjectSelect.query(User.class)
                    .where(User.USER_ID.eq(request.getUserId()))
                    .selectFirst(objectContext);

            UserSession userSession = ObjectSelect.query(UserSession.class)
                    .where(UserSession.SESSION_TO_USER.eq(user))
                    .selectFirst(objectContext);

            if (userSession == null) {
                return SessionResponse.builder()
                        .status(1)
                        .build();
            } else {
                return SessionResponse.builder()
                        .status(2)
                        .build();
            }
        } catch (Exception e) {
            log.error("Error while getting session info: " + e.getMessage());
            throw new CommonException(Integer.toString(request.getUserId()), "Session exception");
        }
    }

    @Override
    public SetPasswordResponse setPassword(SetPasswordRequest request) throws CommonException {
        try {
            User user = ObjectSelect.query(User.class)
                    .where(User.USER_ID.eq(request.getUserId()))
                    .selectFirst(objectContext);

            user.setPassword(password(request.getPassword()));

            UserSession session = objectContext.newObject(UserSession.class);
            session.setCreatedDate(LocalDateTime.now());
            session.setSessionToUser(user);

            objectContext.commitChanges();

            return SetPasswordResponse.builder()
                    .success(true)
                    .build();

        } catch (Exception e) {
            log.error("Error while setting password: " + e.getMessage());
            throw new CommonException(Integer.toString(request.getUserId()), "Password setting exception");
        }
    }

    @Override
    public InfoResponse info(InfoRequest request) throws CommonException {
        if (checkUserId(request.getUserId())) {

            User user = ObjectSelect.query(User.class)
                    .where(User.USER_ID.eq(request.getUserId()))
                    .selectFirst(objectContext);

            return InfoResponse.builder()
                    .success(true)
                    .name(user.getName())
                    .facebook(user.getFacebook())
                    .instagram(user.getInstagram())
                    .phone(user.getPhone())
                    .userId(user.getUserId())
                    .pic_id((int) user.getUserToPic().getObjectId().getIdSnapshot().get("id"))
                    .build();

        } else
            throw new ObjectNotFoundException(Integer.toString(request.getUserId()), "User doesn't exist");

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

    private String password(String value) {
        String password = "tgh_" + value;
        return DigestUtils.md5DigestAsHex(password.getBytes());
    }

}
