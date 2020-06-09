package ru.together.users.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.together.common.exceptions.CommonException;
import ru.together.users.interfaces.IUserService;
import ru.together.users.models.*;
import ru.together.users.services.UserService;

import java.util.List;

@RestController
public class UserEndpoint implements IUserService {

    @Autowired
    UserService userService;

    @Override
    @CrossOrigin
    @RequestMapping(value = USER_GET, method = {RequestMethod.POST})
    public GetUserResponse get(@RequestBody GetUserRequest request) throws CommonException {
        return userService.get(request);
    }

    @Override
    @CrossOrigin
    @RequestMapping(value = USER_LIST, method = {RequestMethod.GET})
    public List<UserModel> list() throws CommonException {
        return userService.list();
    }

    @Override
    @CrossOrigin
    @RequestMapping(value = USER_LIST_VERIFIED, method = {RequestMethod.GET})
    public List<UserModel> listVerified() throws CommonException {
        return userService.listVerified();
    }

    @Override
    @CrossOrigin
    @RequestMapping(value = USER_LIST_UNVERIFIED, method = {RequestMethod.GET})
    public List<UserModel> listUnverified() throws CommonException {
        return userService.listUnverified();
    }

    @Override
    @CrossOrigin
    @RequestMapping(value = USER_LIST_BLOCKED, method = {RequestMethod.GET})
    public List<UserModel> listBlocked() throws CommonException {
        return userService.listBlocked();
    }

    @Override
    @CrossOrigin
    @RequestMapping(value = USER_SEARCH, method = {RequestMethod.POST})
    public List<UserModel> searchUser(@RequestBody SearchUserRequest request) throws CommonException {
        return userService.searchUser(request);
    }

    @Override
    @CrossOrigin
    @RequestMapping(value = USER_BLOCK, method = {RequestMethod.POST})
    public BlockUserResponse block(@RequestBody BlockUserRequest request) throws CommonException {
        return userService.block(request);
    }

    @Override
    @CrossOrigin
    @RequestMapping(value = USER_UNBLOCK, method = {RequestMethod.POST})
    public BlockUserResponse unblock(@RequestBody BlockUserRequest request) throws CommonException {
        return userService.unblock(request);
    }

    @Override
    @CrossOrigin
    @RequestMapping(value = USER_UPDATE, method = {RequestMethod.POST})
    public UpdateUserResponse update(@RequestBody UpdateUserRequest request) throws CommonException {
        return userService.update(request);
    }

    @Override
    @CrossOrigin
    @RequestMapping(value = USER_VERIFY, method = {RequestMethod.POST})
    public UpdateUserResponse verify(@RequestBody UpdateUserRequest request) throws CommonException {
        return userService.verify(request);
    }

    @Override
    @CrossOrigin
    @RequestMapping(value = USER_EMAIL, method = {RequestMethod.POST})
    public ResendEmailResponse resendEmail(@RequestBody ResendEmailRequest request) throws CommonException {
        return userService.resendEmail(request);
    }

    @Override
    @CrossOrigin
    @RequestMapping(value = USER_REMOVE, method = {RequestMethod.POST})
    public RemoveUserResponse remove(@RequestBody RemoveUserRequest request) throws CommonException {
        return userService.remove(request);
    }
}
