package com.example.majorapp;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NAME = "Session";
    String SESSION_KEY = "session_user";
    public Session(Context context){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public void saveSession(User user){
        int id = user.id;
        editor.putInt(SESSION_KEY, id).commit();
    }
    public int getSession(){
        return sharedPreferences.getInt(SESSION_KEY, -1);
    }
    public void removeSession(){
        editor.putInt(SESSION_KEY, -1).commit();
    }
}
