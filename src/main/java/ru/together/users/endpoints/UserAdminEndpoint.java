package ru.together.users.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.together.users.interfaces.IUserAdminService;
import ru.together.users.models.*;
import ru.together.users.services.UserAdminService;

@RestController
public class UserAdminEndpoint implements IUserAdminService {

    @Autowired
    UserAdminService userAdminService;

    @Override
    @RequestMapping(value = USER_GET, method = {RequestMethod.POST})
    public GetUserResponse get(@RequestBody GetUserRequest request) {
        return userAdminService.get(request);
    }

    @Override
    @RequestMapping(value = USER_LIST, method = {RequestMethod.GET})
    public ListUserResponse list() {
        return userAdminService.list();
    }

    @Override
    @RequestMapping(value = USER_UPDATE, method = {RequestMethod.POST})
    public UpdateUserResponse update(@RequestBody UpdateUserRequest request) {
        return userAdminService.update(request);
    }

    @Override
    @RequestMapping(value = USER_REMOVE, method = {RequestMethod.POST})
    public RemoveUserResponse remove(@RequestBody RemoveUserRequest request) {
        return userAdminService.remove(request);
    }
}
