package ru.together.users.interfaces;

import ru.together.users.models.*;

import java.util.List;

public interface IUserService {

    String USER_GET = "/v1/user/get";
    String USER_LIST = "/v1/user/list";
    String USER_UPDATE = "/v1/user/update";
    String USER_REMOVE = "/v1/user/remove";
    String USER_VERIFY = "/v1/user/verify";
    String USER_EMAIL = "/v1/user/email";


    GetUserResponse get(GetUserRequest request);

    List<UserModel> list();

    UpdateUserResponse update(UpdateUserRequest request);

    UpdateUserResponse verify(UpdateUserRequest request);

    ResendEmailResponse resendEmail(ResendEmailRequest request);

    RemoveUserResponse remove(RemoveUserRequest request);

}
