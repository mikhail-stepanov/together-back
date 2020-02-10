package ru.together.auth.interfaces;

import ru.together.auth.models.*;

public interface IAuthService {

    String AUTH_LOGIN_ID = "/v1/login";
    String AUTH_LOGIN_PASS = "/v1/login/pass";
    String AUTH_SIGN_UP = "/v1/signup";
    String AUTH_SESSION = "/v1/session";
    String AUTH_SET_PASSWORD = "/v1/setpass";
    String AUTH_INFO = "/v1/info";


    LoginIdResponse loginId(LoginIdRequest request);

    LoginPassResponse loginPass(LoginPassRequest request);

    SignUpResponse singUp(SignUpRequest request);

    SessionResponse session(SessionRequest request);

    SetPasswordResponse setPassword(SetPasswordRequest request);

    InfoResponse info(InfoRequest request);


}
