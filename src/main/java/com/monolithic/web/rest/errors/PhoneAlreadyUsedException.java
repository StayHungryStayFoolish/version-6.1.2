package com.monolithic.web.rest.errors;

public class PhoneAlreadyUsedException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public PhoneAlreadyUsedException() {
        super(ErrorConstants.EMAIL_ALREADY_USED_TYPE, "Email is already in use!", "userManagement", "emailexists");
    }
}
