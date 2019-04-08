package com.example.designsapp.Utils;
import java.util.Date;

/**
 * Created by Abhi on 20 Jan 2018 020.
 */

public class User {
    String Email;
    String fullName;
    Date sessionExpiryDate;

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setSessionExpiryDate(Date sessionExpiryDate) {
        this.sessionExpiryDate = sessionExpiryDate;
    }



    public String getFullName() {
        return fullName;
    }

    public Date getSessionExpiryDate() {
        return sessionExpiryDate;
    }
}