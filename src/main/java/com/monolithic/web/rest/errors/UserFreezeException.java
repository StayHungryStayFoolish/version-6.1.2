package com.monolithic.web.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class UserFreezeException extends AbstractThrowableProblem {

    public UserFreezeException() {
        super(ErrorConstants.USER_FREEZE_TYPE, "User Freeze", Status.LOCKED);
    }
}
