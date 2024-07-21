package com.ccms.service.impl;

import com.ccms.dto.Response;
import com.ccms.exception.ResourceNotFoundException;
import com.ccms.model.Users;
import com.ccms.repository.UsersRepository;
import com.ccms.service.UsersService;
import com.ccms.utils.UsersValidationChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UsersService {

    public static final String ERROR_DATA_NOT_PROVIDED = "Error: Data Not Provided!";
    public static final String NEW_USER_CREATED = "New User Created";
    public static final String ERROR_CORRECT_ID_NOT_PROVIDED = "Error: Correct Id Not Provided";
    public static final String USER_FOUND = "User Found";
    public static final String USERS_FOUND = "Users Found";
    public static final String USER_CREATED = "User Created";
    public static final String ENTER_VALID_ID = "Enter Valid Id";
    public static final String USER_DELETED = "User Deleted!";
    private final UsersRepository usersRepository;
    private final UsersValidationChecker usersValidationChecker;

    @Autowired
    public UserServiceImpl(UsersRepository usersRepository, UsersValidationChecker usersValidationChecker) {
        this.usersRepository = usersRepository;
        this.usersValidationChecker = usersValidationChecker;
    }

    @Override
    public Response createNewUser(Users users) {
        Response response = new Response();
        if (usersValidationChecker.isUserContainsAllMandatoryData(users, response)) {
            users = usersRepository.save(users);
            response.setData(users);
            response.setMessage(NEW_USER_CREATED);
        }
        return response;
    }

    @Override
    public Response getUser(long id) {
        Response response = new Response();
        Optional<Users> optionalUsers = usersRepository.findById(id);
        if (optionalUsers.isPresent()) {
            response.setData(optionalUsers.get());
            response.setMessage(USER_FOUND);
        } else {
            throw new ResourceNotFoundException("User with id: " + id + " Not Exists!");
        }
        return response;
    }

    @Override
    public Response getAllUsers() {
        Response response = new Response();
        List<Users> users = usersRepository.findAll();
        response.setData(users);
        response.setMessage(USERS_FOUND);
        return response;
    }

    @Override
    public Response updatedUser(long id, Users users) {
        Response response = new Response();
        Optional<Users> optionalUser = usersRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new ResourceNotFoundException("User with id: \" + id + \" Not Exists!");
        } else {
            Users dbUsers = optionalUser.get();
            updateUserDetails(dbUsers, users);
            users = usersRepository.save(dbUsers);
            response.setMessage(USER_CREATED);
            response.setData(users);
        }
        return response;
    }

    @Override
    public Response deleteUser(long id) {
        Response response = new Response();
        if (usersRepository.existsById(id)) {
            usersRepository.deleteById(id);
            response.setMessage(USER_DELETED);
        } else {
            throw new ResourceNotFoundException("User with id: " + id + " not present.");
        }
        return response;
    }

    private void updateUserDetails(Users dbUsers, Users users) {
        if (users != null) {
            if (users.getName() != null && !users.getName().isEmpty()) {
                dbUsers.setName(users.getName());
            }

            if (users.getAge() > 18) {
                dbUsers.setAge(users.getAge());
            }

            if (users.getCreditScore() > 0) {
                dbUsers.setCreditScore(users.getCreditScore());
            }
        }
    }
}
