package com.zema.commons.security;

public class SecurityConstants {
    public static final String TOKEN_PREFIX="Bearer ";
    public static final String HEADER_STRING="Authorization";
    public static final String SIGN_UP_URL= "/api/v1/users";
    public static final String EMAIL_VERIFICATION_URL= "/users/email-verification";
    public static final String DATABASE_H2_URL= "/h2-console/**";
    public static final String FORGOT_PASSWORD_URL= "/users/forgot-password";
    public static final String RESET_PASSWORD_URL= "/users/reset-password/**";
    public static final long EXPIRATION_TIME=432_000_000;//5 DAYS milliseconds
    public static final long EXPIRATION_TIME_SHORT=86_400_000;//1 DAY milliseconds
    public static final int EXPIRATION_TIME_COOKIE=432_000;//5 DAYS in seconds
    public static final int EXPIRATION_TIME_COOKIE_SHORT=86_400;//1 DAYS in seconds
    public static final String COOKIE_NAME="s_xdjs8";//5 DAYS
    public static final String GET_USER_INTERNALLY_BASE_PATH="http://localhost:5001/api/v1/internal/users";//1 DAY
    //public static final String GET_USER_INTERNALLY_BASE_PATH="lb://USER/api/v1/internal/users";//1 DAY
    public static final String SECURITY_KEY="mknjhbubloijjzhy77zhajuz8zhnsnia8ushanmxsi9s8uahnsjsusjns" +
            "qwjshsgy7s7n=snhsyshsnsusnnxhiiou8g7gybtvtrcrctbubyubnim" +
            "zapjshsg0ok8nhsyshsnsujjijiojuhbtvtrcrcujjut8otbubyubnim" +
            "zapjshsg0ok8nhsyshsnsujjijiojuhbtvtrcrcujjut8otbubyubnim" +
            "zapjshsg0ok8nhsyshsnsujjijiojuhbtvtrcrcujjut8otbubyubnim" +
            "zapjshsg0ok8nhsyshsnsujjijiojuhbtvtrcrcujjut8otbubyubnim" +
            "zapjshsg0ok8nhsyshsnsujjijiojuhbtvtrcrcujjut8otbubyubnim" +
            "zapjshsg0ok8nhsyshsnsujjijiojuhbtvtrcrcujjut8otbubyubnim" +
            "zapjshsg0ok8nhsyshsnsujjijiojuhbtvtrcrcujjut8otbubyubnim" +
            "zapjshsg0ok8nhsyshsnsujjijiojuhbtvtrcrcujjut8otbubyubnim" +
            "zapjshsg0ok8nhsyshsnsujjijiojuhbtvtrcrcujjut8otbubyubnim" +
            "zapjshsg0ok8nhsyshsnsujjijiojuhbtvtrcrcujjut8otbubyubnim" +
            "zapjshsg0ok8nhsyshsnsujjijiojuhbtvtrcrcujjut8otbubyubnim" +
            "zapjshsg0ok8nhsyshsnsujjijiojuhbtvtrcrcujjut8otbubyubnim" +
            "zapjshsg0ok8nhsyshsnsujjijiojuhbtvtrcrcujjut8otbubyubnim" +
            "zapjshsg0ok8nhsyshsnsujjijiojuhbtvtrcrcujjut8otbubyubnim" +
            "zapjshsg0ok8nhsyshsnsujjijiojuhbtvtrcrcujjut8otbubyubnim" +
            "zapjshsg0ok8nhsyshsnsujjijiojuhbtvtrcrcujjut8otbubyubnim" +
            "zapjshsg0ok8nhsyshsnsujjijiojuhbtvtrcrcujjut8otbubyubnim" +
            "jshs200kqkogha7hkpa0skmusnnxhiiou8g7gybtvtrcrctbubyubnim" +
            "kkmjnbuyg8h9j9jnionuh8tv,p,i9hyg6t7yun7g6rfy8ytcvgy ygv5";//1 DAY

}