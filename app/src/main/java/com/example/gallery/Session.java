//package com.example.gallery;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//
//public class Session {
//    SharedPreferences sharedPreferences;
//    SharedPreferences.Editor editor;
//
//
//    public Session(Context context){
//        sharedPreferences=context.getSharedPreferences("appkey",0);
//        editor=sharedPreferences.edit();
//        editor.commit();
//
//    }
//
//    public void setlogin(boolean login){
//        editor.putBoolean("key_login",login);
//        editor.commit();
//    }
//
//    public boolean getlogin(){
//        return sharedPreferences.getBoolean("key_login",false);
//    }
//
//        public void setusername(String username){
//        editor.putString("key_username",username);
//        editor.commit();
//        }
//
//    public String getusername(){
//        return sharedPreferences.getString("key_username","");
//    }
//
//}
