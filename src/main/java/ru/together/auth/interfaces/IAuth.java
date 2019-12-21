package ru.together.auth.interfaces;

import ru.together.auth.models.*;

public interface IAuth {

    String LOGIN = "/v1/login";
    String SIGN_UP = "/v1/signup";
    String SESSION = "/v1/session";

    LoginResponse login(LoginRequest request);

    SignUpResponse singUp(SignUpRequest request);

    SessionResponse session(SessionRequest request);

}
