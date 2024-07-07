package com.example.tutorconnect;

public class PostRequirement
{
    private String location, phone, email, details, subject, budget, language, gender, tutor, time, level, want;


    public PostRequirement() {
    }

    public PostRequirement(String location, String phone, String email, String details, String subject, String budget, String language, String gender, String tutor, String time, String level, String want) {
        this.location = location;
        this.phone = phone;
        this.email = email;
        this.details = details;
        this.subject = subject;
        this.budget = budget;
        this.language = language;
        this.gender = gender;
        this.tutor = tutor;
        this.time = time;
        this.level = level;
        this.want = want;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTutor() {
        return tutor;
    }

    public void setTutor(String tutor) {
        this.tutor = tutor;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getWant() {
        return want;
    }

    public void setWant(String want) {
        this.want = want;
    }
}
