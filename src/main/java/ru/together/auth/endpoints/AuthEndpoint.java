package ru.together.auth.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.together.auth.interfaces.IAuthService;
import ru.together.auth.models.*;
import ru.together.auth.services.AuthService;

@RestController
public class AuthEndpoint implements IAuthService {

    @Autowired
    AuthService authService;

    @Override
    @RequestMapping(value = LOGIN, method = {RequestMethod.POST})
    public LoginResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @Override
    @RequestMapping(value = SIGN_UP, method = {RequestMethod.POST})
    public SignUpResponse singUp(@RequestBody SignUpRequest request) {
        return authService.singUp(request);
    }

    @Override
    @RequestMapping(value = SESSION, method = {RequestMethod.POST})
    public SessionResponse session(@RequestBody SessionRequest request) {
        return authService.session(request);
    }

    @Override
    @RequestMapping(value = INFO, method = {RequestMethod.POST})
    public InfoResponse info(@RequestBody InfoRequest request) {
        return authService.info(request);
    }
}
