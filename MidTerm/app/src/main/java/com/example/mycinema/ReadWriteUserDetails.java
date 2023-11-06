package com.example.mycinema;

public class ReadWriteUserDetails {
    public String fullName, dateOfBirth, phoneNumber, gender;

    public ReadWriteUserDetails(String fullNameText, String dateOfBirthText, String phoneNumberText, String genderText) {
        this.fullName = fullNameText;
        this.dateOfBirth = dateOfBirthText;
        this.phoneNumber = phoneNumberText;
        this.gender = genderText;
    }
}
