package ru.together.users.interfaces;

import ru.together.users.models.*;

public interface IUserAdminService {

    String USER_GET = "/v1/user/get";
    String USER_LIST = "/v1/user/list";
    String USER_UPDATE = "/v1/user/update";
    String USER_REMOVE = "/v1/user/remove";

    GetUserResponse get(GetUserRequest request);

    ListUserResponse list();

    UpdateUserResponse update(UpdateUserRequest request);

    RemoveUserResponse remove(RemoveUserRequest request);

}
