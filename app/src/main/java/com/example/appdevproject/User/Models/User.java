package com.example.appdevproject.User.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Entity
public class User {

    private static final String TAG= User.class.getSimpleName();

    @PrimaryKey
    private Integer id; // this is the primary key

    @ColumnInfo(name = "username")
    private String userName;

    @ColumnInfo(name = "password")
    private String password;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "dob")
    private String dob; //saved as a date in the db.



    private Boolean empty;


    //constructors
    public User(){}


    public User(String userName, String email, String dob) {
        this.userName = userName;
        this.email = email;
        this.dob = dob;
    }


    public User(String userName, String password, String email, String dob) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.dob = dob;
    }

    public User(Integer id, String userName, String password, String email,
                String dob) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.dob = dob;
    }

    //be able to get the user's age , if they are loggin in on their bday show happy bday

    // encrypt
    public static String md5HashEncrypt(String text){
        try{
            MessageDigest md= MessageDigest.getInstance("md5");
            md.update(text.getBytes());
            byte[] digestedBytes= md.digest();

            StringBuilder sb= new StringBuilder();
            for(byte b: digestedBytes){
                    sb.append(String.format("%02x",b));
            }
            return sb.toString();

        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return "failedToHash";
    }

    // compare passwords
    public static Boolean comparePasswords(String passwordAttempt, String realPassword){
        String attemp=md5HashEncrypt(passwordAttempt);

        if(realPassword.equals(attemp)){
            return true;
        }
        return false;
    }







    // GETTERS and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = (password);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }


    public void setEmpty(Boolean f){
        this.empty= f;
    }
    public Boolean isEmpty(){
        return this.empty;
    }
}
