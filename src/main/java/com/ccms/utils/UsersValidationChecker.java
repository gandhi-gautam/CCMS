package com.ccms.utils;

import com.ccms.dto.Response;
import com.ccms.exception.EmptyFieldException;
import com.ccms.model.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class UsersValidationChecker {
    Logger logger = LoggerFactory.getLogger(UsersValidationChecker.class);

    public boolean isUserContainsAllMandatoryData(Users users, Response response) {
        if (users.getName() == null || users.getName().isEmpty()) {
            logger.error("User Name not provided!");
            throw new EmptyFieldException("Error: User Name not provided!");
        } else if (users.getAge() <= 18) {
            logger.error("Age Not provided or less than 18.");
            throw new EmptyFieldException("Error: Age Not provided or less than 18.");
        } else if (users.getCreditScore() <= 0) {
            logger.error("Credit Score Not provided!");
            throw new EmptyFieldException("Error: Credit Score Not provided!");
        }
        return true;
    }
}
