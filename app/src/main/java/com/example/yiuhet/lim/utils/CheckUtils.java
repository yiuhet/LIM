package com.example.yiuhet.lim.utils;

/**
 * Created by yiuhet on 2017/5/3.
 */

public class CheckUtils {

    private static final String USER_NAME_REGEX = "^[a-zA-Z]\\w{2,19}$";

    private static final String PASSWORD_REGEX = "^[0-9]{3,20}$";

    public static boolean checkUserName(String userName) {
        return userName.matches(USER_NAME_REGEX);
    }

    public static boolean checkPassword(String password) {
        return password.matches(PASSWORD_REGEX);
    }

}
