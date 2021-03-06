package ru.together.auth.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.together.auth.interfaces.IAuthService;
import ru.together.auth.models.*;
import ru.together.auth.services.AuthService;
import ru.together.common.controllers.AbstractController;
import ru.together.common.exceptions.CommonException;

@RestController
public class AuthEndpoint extends AbstractController implements IAuthService {

    @Autowired
    AuthService authService;

    @Override
    @RequestMapping(value = AUTH_LOGIN_ID, method = {RequestMethod.POST})
    public LoginIdResponse loginId(@RequestBody LoginIdRequest request) throws CommonException {
        return authService.loginId(request);
    }

    @Override
    @RequestMapping(value = AUTH_LOGIN_PASS, method = {RequestMethod.POST})
    public LoginPassResponse loginPass(@RequestBody LoginPassRequest request) throws CommonException {
        return authService.loginPass(request);
    }

    @Override
    @RequestMapping(value = AUTH_SIGN_UP, method = {RequestMethod.POST})
    public SignUpResponse singUp(@RequestBody SignUpRequest request) throws CommonException {
        return authService.singUp(request);
    }

    @Override
    @RequestMapping(value = AUTH_SESSION, method = {RequestMethod.POST})
    public SessionResponse session(@RequestBody SessionRequest request) throws CommonException {
        return authService.session(request);
    }

    @Override
    @RequestMapping(value = AUTH_SET_PASSWORD, method = {RequestMethod.POST})
    public SetPasswordResponse setPassword(@RequestBody SetPasswordRequest request) throws CommonException {
        return authService.setPassword(request);
    }

    @Override
    @RequestMapping(value = AUTH_INFO, method = {RequestMethod.POST})
    public InfoResponse info(@RequestBody InfoRequest request) throws CommonException {
        return authService.info(request);
    }
}
