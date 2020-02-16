package ru.together.auth.interfaces;

import ru.together.auth.models.*;
import ru.together.common.exceptions.BadRequestException;
import ru.together.common.exceptions.CommonException;

public interface IAuthService {

    String AUTH_LOGIN_ID = "/v1/login";
    String AUTH_LOGIN_PASS = "/v1/login/pass";
    String AUTH_SIGN_UP = "/v1/signup";
    String AUTH_SESSION = "/v1/session";
    String AUTH_SET_PASSWORD = "/v1/setpass";
    String AUTH_INFO = "/v1/info";


    LoginIdResponse loginId(LoginIdRequest request) throws CommonException;

    LoginPassResponse loginPass(LoginPassRequest request) throws CommonException;

    SignUpResponse singUp(SignUpRequest request) throws CommonException;

    SessionResponse session(SessionRequest request) throws CommonException;

    SetPasswordResponse setPassword(SetPasswordRequest request) throws CommonException;

    InfoResponse info(InfoRequest request) throws CommonException;


}
