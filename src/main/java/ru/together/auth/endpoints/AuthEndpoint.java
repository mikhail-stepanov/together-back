package ru.together.auth.endpoints;

import org.springframework.web.bind.annotation.RestController;
import ru.together.auth.interfaces.IAuth;
import ru.together.auth.models.*;

@RestController
public class AuthEndpoint implements IAuth {

    @Override
    public LoginResponse login(LoginRequest request) {
        return null;
    }

    @Override
    public SignUpRequest singUp(SignUpResponse request) {
        return null;
    }

    @Override
    public SessionResponse session(SessionRequest request) {
        return null;
    }
}
