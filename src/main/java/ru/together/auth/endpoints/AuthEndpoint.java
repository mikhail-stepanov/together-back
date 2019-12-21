package ru.together.auth.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.together.auth.interfaces.IAuth;
import ru.together.auth.models.*;
import ru.together.auth.services.AuthService;

@RestController
public class AuthEndpoint implements IAuth {

    @Autowired
    AuthService authService;

    @Override
    @RequestMapping(value = LOGIN, method = {RequestMethod.POST})
    public LoginResponse login(LoginRequest request) {

        return null;
    }

    @Override
    @RequestMapping(value = SIGN_UP, method = {RequestMethod.POST})
    public SignUpResponse singUp(SignUpRequest request) {
        return null;
    }

    @Override
    @RequestMapping(value = SESSION, method = {RequestMethod.POST})
    public SessionResponse session(SessionRequest request) {
        return null;
    }
}
