package com.monolithic.web.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class UserNotFoundException extends AbstractThrowableProblem {

    public UserNotFoundException() {
        super(ErrorConstants.USER_NOT_FOUND_TYPE, "User could not be found", Status.NOT_FOUND);
    }
}
