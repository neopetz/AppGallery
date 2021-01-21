package com.example.gallery;

public class User {

    public String valFullName, valEmail,valUsername,valNumber,valAddress,myImage ;

    public User(){

    }

    public User(String valFullName, String valEmail, String valUsername, String valNumber, String valAddress, String myImage) {
        this.valFullName = valFullName;
        this.valEmail  = valEmail;
        this.valUsername = valUsername;
        this.valNumber = valNumber;
        this.valAddress = valAddress;
        this.myImage = myImage;
    }

}
