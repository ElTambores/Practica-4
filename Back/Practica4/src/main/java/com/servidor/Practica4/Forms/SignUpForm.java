package com.servidor.Practica4.Forms;


import jakarta.validation.constraints.Email;

public class SignUpForm {
    String name;

    @Email
    String email;

    String password;

    String role;

    String moderatedCategory;

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

    public String getModeratedCategory() {
        return moderatedCategory;
    }

    public void setModeratedCategory(String moderatedCategory) {
        this.moderatedCategory = moderatedCategory;
    }
}
