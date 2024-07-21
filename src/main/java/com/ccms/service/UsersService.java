package com.ccms.service;

import com.ccms.dto.Response;
import com.ccms.model.Users;

public interface UsersService {
    Response createNewUser(Users users);

    Response getUser(long id);

    Response getAllUsers();

    Response updatedUser(long id, Users users);

    Response deleteUser(long id);
}
