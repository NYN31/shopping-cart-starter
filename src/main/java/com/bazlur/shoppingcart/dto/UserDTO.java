package com.bazlur.shoppingcart.dto;

import com.bazlur.shoppingcart.utility.annotation.PasswordEqual;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
@PasswordEqual(
        first = "password",
        second = "passwordConfirmed"
)
public class UserDTO {
    @NotEmpty
    @Size(min=4, max=32)
    private String username;

    @NotEmpty
    @Size(min=5, max=16)
    private String password;

    @NotEmpty
    @Size(min=5, max=16)
    private String passwordConfirmed;

    @NotEmpty
    @Size(min=4, max=64)
    @Email
    private String email;

    @NotEmpty
    @Size(min=4, max=32)
    private String firstName;

    @NotEmpty
    @Size(min=4, max=32)
    private String lastName;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmed() {
        return passwordConfirmed;
    }

    public void setPasswordConfirmed(String passwordConfirmed) {
        this.passwordConfirmed = passwordConfirmed;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public UserDTO() {}
    public UserDTO(String username, String password, String passwordConfirmed, String email, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.passwordConfirmed = passwordConfirmed;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
