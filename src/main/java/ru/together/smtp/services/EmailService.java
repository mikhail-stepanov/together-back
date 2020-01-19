package ru.together.smtp.services;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.query.ObjectSelect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.together.database.entities.User;
import ru.together.database.services.DatabaseService;
import ru.together.smtp.models.SendEmailRequest;
import ru.together.smtp.models.SendEmailResponse;
import ru.together.users.services.UserService;

import javax.annotation.PostConstruct;

@Service
public class EmailService {

    @Qualifier("getJavaMailSender")
    @Autowired
    public JavaMailSender emailSender;

    @Autowired
    public SimpleMailMessage template;

    @Autowired
    DatabaseService databaseService;

    ObjectContext objectContext;

    protected static final Logger log = LoggerFactory.getLogger(EmailService.class);

    @PostConstruct
    public void init() {
        objectContext = databaseService.getContext();
    }


    public SendEmailResponse sendSimpleMessage(SendEmailRequest request) {
        try {
            User user = ObjectSelect.query(User.class)
                    .where(User.USER_ID.eq(request.getUserId()))
                    .selectFirst(objectContext);

            String text = String.format(template.getText(), user.getName(), user.getUserId());

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(user.getEmail());
            message.setSubject("Регистрация в TOGETHER");
            message.setText(text);
            emailSender.send(message);

            return SendEmailResponse.builder()
                    .success(true)
                    .build();

        } catch (Exception e) {
            log.error(e.getMessage());
            return SendEmailResponse.builder()
                    .success(false)
                    .build();
        }
    }
}

