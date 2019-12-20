package ru.together.auth.interfaces;

import ru.together.auth.models.*;

public interface IAuth {

    LoginResponse login(LoginRequest request);

    SignUpRequest singUp(SignUpResponse request);

    SessionResponse session(SessionRequest request);

}
