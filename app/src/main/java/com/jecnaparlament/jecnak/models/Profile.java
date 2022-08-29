package com.jecnaparlament.jecnak.models;

public class Profile {
    private String name;
    private String email;
    private String birth;

    public Profile(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.birth = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }
}
