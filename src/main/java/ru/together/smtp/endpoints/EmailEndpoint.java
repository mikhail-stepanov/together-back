package ru.together.smtp.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.together.smtp.models.SendEmailRequest;
import ru.together.smtp.models.SendEmailResponse;
import ru.together.smtp.services.EmailService;

@RestController
public class EmailEndpoint {

    @Autowired
    EmailService emailService;

    @RequestMapping(value = "v1/email", method = {RequestMethod.POST})
    public SendEmailResponse sendEmail(@RequestBody SendEmailRequest request) {
        return emailService.sendSimpleMessage(request);
    }

}
