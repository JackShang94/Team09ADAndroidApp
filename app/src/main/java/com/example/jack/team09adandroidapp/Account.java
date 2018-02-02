package com.example.jack.team09adandroidapp;

import android.util.Log;

import org.json.JSONObject;

/**
 * Created by doujohner on 31/1/2018.
 */

public class Account {
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Account(){

    }

    public Account(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }
    private String email;
    private String password;
    private String role;
    public String authAccount(String email,String password){

        String url=URL.baseURL+"/AndroidServices/LoginService.svc/Login";
        JSONObject jsonObject = new JSONObject();
//        Account a = new Account(email,password,"");
        try{
            jsonObject.put("email",email);
            jsonObject.put("password",password);
            jsonObject.put("role","");

//            return 0;
        }
        catch (Exception e){
            Log.e("jsonobject put wrong",e.toString());
        }
        return JSONParser.postStream(url,jsonObject.toString());
    }
}
