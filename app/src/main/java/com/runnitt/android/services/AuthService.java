package com.runnitt.android.services;

import android.content.Context;
import android.content.SharedPreferences;

public class AuthService {
    private static final String LOGIN_STATE = "LOGIN_STATE";
    private static final String IS_LOGGED_IN = "IS_LOGGED_IN";

    public static boolean isUserLoggedIn(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(LOGIN_STATE, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false);
    }

    public static void loggedIn(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(LOGIN_STATE, Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(IS_LOGGED_IN, true).apply();
    }

    public static void logout(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(LOGIN_STATE, Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(IS_LOGGED_IN, false).apply();
    }
}
