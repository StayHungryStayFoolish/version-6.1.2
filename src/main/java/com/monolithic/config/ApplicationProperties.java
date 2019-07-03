package com.monolithic.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Monolithic.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private String appPrefixName;
    private String appMiddleName;
    private EmailConfig emailConfig;
    private SmsConfig smsConfig;
    private RateLimit rateLimit;

    public static class EmailConfig {

        private String website;
        private String supportEmail;

        public String getWebsite() {
            return website;
        }

        public void setWebsite(String website) {
            this.website = website;
        }

        public String getSupportEmail() {
            return supportEmail;
        }

        public void setSupportEmail(String supportEmail) {
            this.supportEmail = supportEmail;
        }
    }

    public static class SmsConfig {

        private String defaultProvider;
        private ALi aLi;
        private Twilio twilio;
        private CommonTemplates commonTemplates;

        public static class ALi {
            private String[] countryCode;
            private String product;
            private String domain;
            private String accessKeyId;
            private String accessKeySecret;
            private String signName;
            private Templates templates;

            public static class Templates {
                // CN
                private String registryCN;
                private String welcomeCN;
                private String loginVerifyCN;
                private String loginNoticeCN;
                private String bindPhoneCN;
                private String changeOldPhoneCN;
                private String changeNewPhoneCN;
                // EN
                private String registryEN;
                private String welcomeEN;
                private String loginVerifyEN;
                private String loginNoticeEN;
                private String bindPhoneEN;
                private String changeOldPhoneEN;
                private String changeNewPhoneEN;
                // KR
                private String registryKR;
                private String welcomeKR;
                private String loginVerifyKR;
                private String loginNoticeKR;
                private String bindPhoneKR;
                private String changeOldPhoneKR;
                private String changeNewPhoneKR;
                // JP
                private String registryJP;
                private String welcomeJP;
                private String loginVerifyJP;
                private String loginNoticeJP;
                private String bindPhoneJP;
                private String changeOldPhoneJP;
                private String changeNewPhoneJP;

                public String getRegistryCN() {
                    return registryCN;
                }

                public void setRegistryCN(String registryCN) {
                    this.registryCN = registryCN;
                }

                public String getWelcomeCN() {
                    return welcomeCN;
                }

                public void setWelcomeCN(String welcomeCN) {
                    this.welcomeCN = welcomeCN;
                }

                public String getLoginVerifyCN() {
                    return loginVerifyCN;
                }

                public void setLoginVerifyCN(String loginVerifyCN) {
                    this.loginVerifyCN = loginVerifyCN;
                }

                public String getLoginNoticeCN() {
                    return loginNoticeCN;
                }

                public void setLoginNoticeCN(String loginNoticeCN) {
                    this.loginNoticeCN = loginNoticeCN;
                }

                public String getBindPhoneCN() {
                    return bindPhoneCN;
                }

                public void setBindPhoneCN(String bindPhoneCN) {
                    this.bindPhoneCN = bindPhoneCN;
                }

                public String getChangeOldPhoneCN() {
                    return changeOldPhoneCN;
                }

                public void setChangeOldPhoneCN(String changeOldPhoneCN) {
                    this.changeOldPhoneCN = changeOldPhoneCN;
                }

                public String getChangeNewPhoneCN() {
                    return changeNewPhoneCN;
                }

                public void setChangeNewPhoneCN(String changeNewPhoneCN) {
                    this.changeNewPhoneCN = changeNewPhoneCN;
                }

                public String getRegistryEN() {
                    return registryEN;
                }

                public void setRegistryEN(String registryEN) {
                    this.registryEN = registryEN;
                }

                public String getWelcomeEN() {
                    return welcomeEN;
                }

                public void setWelcomeEN(String welcomeEN) {
                    this.welcomeEN = welcomeEN;
                }

                public String getLoginVerifyEN() {
                    return loginVerifyEN;
                }

                public void setLoginVerifyEN(String loginVerifyEN) {
                    this.loginVerifyEN = loginVerifyEN;
                }

                public String getLoginNoticeEN() {
                    return loginNoticeEN;
                }

                public void setLoginNoticeEN(String loginNoticeEN) {
                    this.loginNoticeEN = loginNoticeEN;
                }

                public String getBindPhoneEN() {
                    return bindPhoneEN;
                }

                public void setBindPhoneEN(String bindPhoneEN) {
                    this.bindPhoneEN = bindPhoneEN;
                }

                public String getChangeOldPhoneEN() {
                    return changeOldPhoneEN;
                }

                public void setChangeOldPhoneEN(String changeOldPhoneEN) {
                    this.changeOldPhoneEN = changeOldPhoneEN;
                }

                public String getChangeNewPhoneEN() {
                    return changeNewPhoneEN;
                }

                public void setChangeNewPhoneEN(String changeNewPhoneEN) {
                    this.changeNewPhoneEN = changeNewPhoneEN;
                }

                public String getRegistryKR() {
                    return registryKR;
                }

                public void setRegistryKR(String registryKR) {
                    this.registryKR = registryKR;
                }

                public String getWelcomeKR() {
                    return welcomeKR;
                }

                public void setWelcomeKR(String welcomeKR) {
                    this.welcomeKR = welcomeKR;
                }

                public String getLoginVerifyKR() {
                    return loginVerifyKR;
                }

                public void setLoginVerifyKR(String loginVerifyKR) {
                    this.loginVerifyKR = loginVerifyKR;
                }

                public String getLoginNoticeKR() {
                    return loginNoticeKR;
                }

                public void setLoginNoticeKR(String loginNoticeKR) {
                    this.loginNoticeKR = loginNoticeKR;
                }

                public String getBindPhoneKR() {
                    return bindPhoneKR;
                }

                public void setBindPhoneKR(String bindPhoneKR) {
                    this.bindPhoneKR = bindPhoneKR;
                }

                public String getChangeOldPhoneKR() {
                    return changeOldPhoneKR;
                }

                public void setChangeOldPhoneKR(String changeOldPhoneKR) {
                    this.changeOldPhoneKR = changeOldPhoneKR;
                }

                public String getChangeNewPhoneKR() {
                    return changeNewPhoneKR;
                }

                public void setChangeNewPhoneKR(String changeNewPhoneKR) {
                    this.changeNewPhoneKR = changeNewPhoneKR;
                }

                public String getRegistryJP() {
                    return registryJP;
                }

                public void setRegistryJP(String registryJP) {
                    this.registryJP = registryJP;
                }

                public String getWelcomeJP() {
                    return welcomeJP;
                }

                public void setWelcomeJP(String welcomeJP) {
                    this.welcomeJP = welcomeJP;
                }

                public String getLoginVerifyJP() {
                    return loginVerifyJP;
                }

                public void setLoginVerifyJP(String loginVerifyJP) {
                    this.loginVerifyJP = loginVerifyJP;
                }

                public String getLoginNoticeJP() {
                    return loginNoticeJP;
                }

                public void setLoginNoticeJP(String loginNoticeJP) {
                    this.loginNoticeJP = loginNoticeJP;
                }

                public String getBindPhoneJP() {
                    return bindPhoneJP;
                }

                public void setBindPhoneJP(String bindPhoneJP) {
                    this.bindPhoneJP = bindPhoneJP;
                }

                public String getChangeOldPhoneJP() {
                    return changeOldPhoneJP;
                }

                public void setChangeOldPhoneJP(String changeOldPhoneJP) {
                    this.changeOldPhoneJP = changeOldPhoneJP;
                }

                public String getChangeNewPhoneJP() {
                    return changeNewPhoneJP;
                }

                public void setChangeNewPhoneJP(String changeNewPhoneJP) {
                    this.changeNewPhoneJP = changeNewPhoneJP;
                }
            }

            public String[] getCountryCode() {
                return countryCode;
            }

            public void setCountryCode(String[] countryCode) {
                this.countryCode = countryCode;
            }

            public String getProduct() {
                return product;
            }

            public void setProduct(String product) {
                this.product = product;
            }

            public String getDomain() {
                return domain;
            }

            public void setDomain(String domain) {
                this.domain = domain;
            }

            public String getAccessKeyId() {
                return accessKeyId;
            }

            public void setAccessKeyId(String accessKeyId) {
                this.accessKeyId = accessKeyId;
            }

            public String getAccessKeySecret() {
                return accessKeySecret;
            }

            public void setAccessKeySecret(String accessKeySecret) {
                this.accessKeySecret = accessKeySecret;
            }

            public String getSignName() {
                return signName;
            }

            public void setSignName(String signName) {
                this.signName = signName;
            }

            public Templates getTemplates() {
                return templates;
            }

            public void setTemplates(Templates templates) {
                this.templates = templates;
            }
        }

        public static class Twilio {
            private String[] countryCode;
            private String accountId;
            private String authToken;
            private String fromNumber;

            public String[] getCountryCode() {
                return countryCode;
            }

            public void setCountryCode(String[] countryCode) {
                this.countryCode = countryCode;
            }

            public String getAccountId() {
                return accountId;
            }

            public void setAccountId(String accountId) {
                this.accountId = accountId;
            }

            public String getAuthToken() {
                return authToken;
            }

            public void setAuthToken(String authToken) {
                this.authToken = authToken;
            }

            public String getFromNumber() {
                return fromNumber;
            }

            public void setFromNumber(String fromNumber) {
                this.fromNumber = fromNumber;
            }
        }

        public static class CommonTemplates {
            // CN
            private String registryCN;
            private String welcomeCN;
            private String loginVerifyCN;
            private String loginNoticeCN;
            private String bindPhoneCN;
            private String changeOldPhoneCN;
            private String changeNewPhoneCN;
            // EN
            private String registryEN;
            private String welcomeEN;
            private String loginVerifyEN;
            private String loginNoticeEN;
            private String bindPhoneEN;
            private String changeOldPhoneEN;
            private String changeNewPhoneEN;
            // KR
            private String registryKR;
            private String welcomeKR;
            private String loginVerifyKR;
            private String loginNoticeKR;
            private String bindPhoneKR;
            private String changeOldPhoneKR;
            private String changeNewPhoneKR;
            // JP
            private String registryJP;
            private String welcomeJP;
            private String loginVerifyJP;
            private String loginNoticeJP;
            private String bindPhoneJP;
            private String changeOldPhoneJP;
            private String changeNewPhoneJP;

            public String getRegistryCN() {
                return registryCN;
            }

            public void setRegistryCN(String registryCN) {
                this.registryCN = registryCN;
            }

            public String getWelcomeCN() {
                return welcomeCN;
            }

            public void setWelcomeCN(String welcomeCN) {
                this.welcomeCN = welcomeCN;
            }

            public String getLoginVerifyCN() {
                return loginVerifyCN;
            }

            public void setLoginVerifyCN(String loginVerifyCN) {
                this.loginVerifyCN = loginVerifyCN;
            }

            public String getLoginNoticeCN() {
                return loginNoticeCN;
            }

            public void setLoginNoticeCN(String loginNoticeCN) {
                this.loginNoticeCN = loginNoticeCN;
            }

            public String getBindPhoneCN() {
                return bindPhoneCN;
            }

            public void setBindPhoneCN(String bindPhoneCN) {
                this.bindPhoneCN = bindPhoneCN;
            }

            public String getChangeOldPhoneCN() {
                return changeOldPhoneCN;
            }

            public void setChangeOldPhoneCN(String changeOldPhoneCN) {
                this.changeOldPhoneCN = changeOldPhoneCN;
            }

            public String getChangeNewPhoneCN() {
                return changeNewPhoneCN;
            }

            public void setChangeNewPhoneCN(String changeNewPhoneCN) {
                this.changeNewPhoneCN = changeNewPhoneCN;
            }

            public String getRegistryEN() {
                return registryEN;
            }

            public void setRegistryEN(String registryEN) {
                this.registryEN = registryEN;
            }

            public String getWelcomeEN() {
                return welcomeEN;
            }

            public void setWelcomeEN(String welcomeEN) {
                this.welcomeEN = welcomeEN;
            }

            public String getLoginVerifyEN() {
                return loginVerifyEN;
            }

            public void setLoginVerifyEN(String loginVerifyEN) {
                this.loginVerifyEN = loginVerifyEN;
            }

            public String getLoginNoticeEN() {
                return loginNoticeEN;
            }

            public void setLoginNoticeEN(String loginNoticeEN) {
                this.loginNoticeEN = loginNoticeEN;
            }

            public String getBindPhoneEN() {
                return bindPhoneEN;
            }

            public void setBindPhoneEN(String bindPhoneEN) {
                this.bindPhoneEN = bindPhoneEN;
            }

            public String getChangeOldPhoneEN() {
                return changeOldPhoneEN;
            }

            public void setChangeOldPhoneEN(String changeOldPhoneEN) {
                this.changeOldPhoneEN = changeOldPhoneEN;
            }

            public String getChangeNewPhoneEN() {
                return changeNewPhoneEN;
            }

            public void setChangeNewPhoneEN(String changeNewPhoneEN) {
                this.changeNewPhoneEN = changeNewPhoneEN;
            }

            public String getRegistryKR() {
                return registryKR;
            }

            public void setRegistryKR(String registryKR) {
                this.registryKR = registryKR;
            }

            public String getWelcomeKR() {
                return welcomeKR;
            }

            public void setWelcomeKR(String welcomeKR) {
                this.welcomeKR = welcomeKR;
            }

            public String getLoginVerifyKR() {
                return loginVerifyKR;
            }

            public void setLoginVerifyKR(String loginVerifyKR) {
                this.loginVerifyKR = loginVerifyKR;
            }

            public String getLoginNoticeKR() {
                return loginNoticeKR;
            }

            public void setLoginNoticeKR(String loginNoticeKR) {
                this.loginNoticeKR = loginNoticeKR;
            }

            public String getBindPhoneKR() {
                return bindPhoneKR;
            }

            public void setBindPhoneKR(String bindPhoneKR) {
                this.bindPhoneKR = bindPhoneKR;
            }

            public String getChangeOldPhoneKR() {
                return changeOldPhoneKR;
            }

            public void setChangeOldPhoneKR(String changeOldPhoneKR) {
                this.changeOldPhoneKR = changeOldPhoneKR;
            }

            public String getChangeNewPhoneKR() {
                return changeNewPhoneKR;
            }

            public void setChangeNewPhoneKR(String changeNewPhoneKR) {
                this.changeNewPhoneKR = changeNewPhoneKR;
            }

            public String getRegistryJP() {
                return registryJP;
            }

            public void setRegistryJP(String registryJP) {
                this.registryJP = registryJP;
            }

            public String getWelcomeJP() {
                return welcomeJP;
            }

            public void setWelcomeJP(String welcomeJP) {
                this.welcomeJP = welcomeJP;
            }

            public String getLoginVerifyJP() {
                return loginVerifyJP;
            }

            public void setLoginVerifyJP(String loginVerifyJP) {
                this.loginVerifyJP = loginVerifyJP;
            }

            public String getLoginNoticeJP() {
                return loginNoticeJP;
            }

            public void setLoginNoticeJP(String loginNoticeJP) {
                this.loginNoticeJP = loginNoticeJP;
            }

            public String getBindPhoneJP() {
                return bindPhoneJP;
            }

            public void setBindPhoneJP(String bindPhoneJP) {
                this.bindPhoneJP = bindPhoneJP;
            }

            public String getChangeOldPhoneJP() {
                return changeOldPhoneJP;
            }

            public void setChangeOldPhoneJP(String changeOldPhoneJP) {
                this.changeOldPhoneJP = changeOldPhoneJP;
            }

            public String getChangeNewPhoneJP() {
                return changeNewPhoneJP;
            }

            public void setChangeNewPhoneJP(String changeNewPhoneJP) {
                this.changeNewPhoneJP = changeNewPhoneJP;
            }
        }

        ;

        public String getDefaultProvider() {
            return defaultProvider;
        }

        public void setDefaultProvider(String defaultProvider) {
            this.defaultProvider = defaultProvider;
        }

        public ALi getaLi() {
            return aLi;
        }

        public void setaLi(ALi aLi) {
            this.aLi = aLi;
        }

        public Twilio getTwilio() {
            return twilio;
        }

        public void setTwilio(Twilio twilio) {
            this.twilio = twilio;
        }

        public CommonTemplates getCommonTemplates() {
            return commonTemplates;
        }

        public void setCommonTemplates(CommonTemplates commonTemplates) {
            this.commonTemplates = commonTemplates;
        }
    }

    public static class RateLimit {
        private LoginLimit loginLimit;
        private SmsLimit smsLimit;

        public static class LoginLimit {
            private Long dayExpire;
            private Long dayLimit;

            public Long getDayExpire() {
                return dayExpire;
            }

            public void setDayExpire(Long dayExpire) {
                this.dayExpire = dayExpire;
            }

            public Long getDayLimit() {
                return dayLimit;
            }

            public void setDayLimit(Long dayLimit) {
                this.dayLimit = dayLimit;
            }

        }

        public static class SmsLimit {
            private Long dayExpire;
            private Long dayLimit;
            private Long hourExpire;
            private Long hourLimit;
            private Long minuteExpire;
            private Long minuteLimit;

            public Long getDayExpire() {
                return dayExpire;
            }

            public void setDayExpire(Long dayExpire) {
                this.dayExpire = dayExpire;
            }

            public Long getDayLimit() {
                return dayLimit;
            }

            public void setDayLimit(Long dayLimit) {
                this.dayLimit = dayLimit;
            }

            public Long getHourExpire() {
                return hourExpire;
            }

            public void setHourExpire(Long hourExpire) {
                this.hourExpire = hourExpire;
            }

            public Long getHourLimit() {
                return hourLimit;
            }

            public void setHourLimit(Long hourLimit) {
                this.hourLimit = hourLimit;
            }

            public Long getMinuteExpire() {
                return minuteExpire;
            }

            public void setMinuteExpire(Long minuteExpire) {
                this.minuteExpire = minuteExpire;
            }

            public Long getMinuteLimit() {
                return minuteLimit;
            }

            public void setMinuteLimit(Long minuteLimit) {
                this.minuteLimit = minuteLimit;
            }
        }

        public LoginLimit getLoginLimit() {
            return loginLimit;
        }

        public void setLoginLimit(LoginLimit loginLimit) {
            this.loginLimit = loginLimit;
        }

        public SmsLimit getSmsLimit() {
            return smsLimit;
        }

        public void setSmsLimit(SmsLimit smsLimit) {
            this.smsLimit = smsLimit;
        }

    }

    public String getAppPrefixName() {
        return appPrefixName;
    }

    public void setAppPrefixName(String appPrefixName) {
        this.appPrefixName = appPrefixName;
    }

    public String getAppMiddleName() {
        return appMiddleName;
    }

    public void setAppMiddleName(String appMiddleName) {
        this.appMiddleName = appMiddleName;
    }

    public EmailConfig getEmailConfig() {
        return emailConfig;
    }

    public void setEmailConfig(EmailConfig emailConfig) {
        this.emailConfig = emailConfig;
    }

    public SmsConfig getSmsConfig() {
        return smsConfig;
    }

    public void setSmsConfig(SmsConfig smsConfig) {
        this.smsConfig = smsConfig;
    }

    public RateLimit getRateLimit() {
        return rateLimit;
    }

    public void setRateLimit(RateLimit rateLimit) {
        this.rateLimit = rateLimit;
    }
}
