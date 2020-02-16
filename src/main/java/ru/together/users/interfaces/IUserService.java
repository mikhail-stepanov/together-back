package ru.together.users.interfaces;

import ru.together.common.exceptions.CommonException;
import ru.together.users.models.*;

import java.util.List;

public interface IUserService {

    String USER_GET = "/v1/user/get";
    String USER_LIST = "/v1/user/list";
    String USER_LIST_VERIFIED = "/v1/user/list/verified";
    String USER_LIST_UNVERIFIED = "/v1/user/list/unverified";
    String USER_LIST_BLOCKED = "/v1/user/list/blocked";
    String USER_BLOCK = "/v1/user/block";
    String USER_UNBLOCK = "/v1/user/unblock";
    String USER_UPDATE = "/v1/user/update";
    String USER_REMOVE = "/v1/user/remove";
    String USER_VERIFY = "/v1/user/verify";
    String USER_EMAIL = "/v1/user/email";


    GetUserResponse get(GetUserRequest request) throws CommonException;

    List<UserModel> list() throws CommonException;

    List<UserModel> listVerified() throws CommonException;

    List<UserModel> listUnverified() throws CommonException;

    List<UserModel> listBlocked() throws CommonException;

    BlockUserResponse block(BlockUserRequest request) throws CommonException;

    BlockUserResponse unblock(BlockUserRequest request) throws CommonException;

    UpdateUserResponse update(UpdateUserRequest request) throws CommonException;

    UpdateUserResponse verify(UpdateUserRequest request) throws CommonException;

    ResendEmailResponse resendEmail(ResendEmailRequest request) throws CommonException;

    RemoveUserResponse remove(RemoveUserRequest request) throws CommonException;

}
