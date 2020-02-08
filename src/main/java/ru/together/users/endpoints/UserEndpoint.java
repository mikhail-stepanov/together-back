package ru.together.users.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
    public GetUserResponse get(@RequestBody GetUserRequest request) {
        return userService.get(request);
    }

    @Override
    @CrossOrigin
    @RequestMapping(value = USER_LIST, method = {RequestMethod.GET})
    public List<UserModel> list() {
        return userService.list();
    }

    @Override
    @CrossOrigin
    @RequestMapping(value = USER_UPDATE, method = {RequestMethod.POST})
    public UpdateUserResponse update(@RequestBody UpdateUserRequest request) {
        return userService.update(request);
    }

    @Override
    @CrossOrigin
    @RequestMapping(value = USER_VERIFY, method = {RequestMethod.POST})
    public UpdateUserResponse verify(UpdateUserRequest request) {
        return userService.verify(request);
    }

    @Override
    @CrossOrigin
    @RequestMapping(value = USER_EMAIL, method = {RequestMethod.POST})
    public ResendEmailResponse resendEmail(ResendEmailRequest request) {
        return userService.resendEmail(request);
    }

    @Override
    @CrossOrigin
    @RequestMapping(value = USER_REMOVE, method = {RequestMethod.POST})
    public RemoveUserResponse remove(@RequestBody RemoveUserRequest request) {
        return userService.remove(request);
    }
}
