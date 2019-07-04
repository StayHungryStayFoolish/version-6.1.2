package com.monolithic.web.rest.vm;

import java.io.Serializable;

public class EmailUserVM implements Serializable {

    private String email;

    private String phone;

    private String logo;

    private String website;

    private String activationKey;

    private String resetKey;

    private String langKey;

    private String supportEmail;

    private String loginDate;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getActivationKey() {
        return activationKey;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    public String getResetKey() {
        return resetKey;
    }

    public void setResetKey(String resetKey) {
        this.resetKey = resetKey;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public String getSupportEmail() {
        return supportEmail;
    }

    public void setSupportEmail(String supportEmail) {
        this.supportEmail = supportEmail;
    }

    public String getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(String loginDate) {
        this.loginDate = loginDate;
    }

    @Override
    public String toString() {
        return "EmailUserVM{" +
            "email='" + email + '\'' +
            ", phone='" + phone + '\'' +
            ", logo='" + logo + '\'' +
            ", website='" + website + '\'' +
            ", activationKey='" + activationKey + '\'' +
            ", resetKey='" + resetKey + '\'' +
            ", langKey='" + langKey + '\'' +
            ", supportEmail='" + supportEmail + '\'' +
            ", loginDate='" + loginDate + '\'' +
            '}';
    }
}
