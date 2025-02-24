package com.bazlur.shoppingcart.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class ShippingAddressDTO {
    private String address;
    private String address2;
    private String state;
    private String zip;
    private String country;
    @NotNull
    @Pattern(regexp = "^(?:\\+88|01)?\\d{11}",
        message = "Must be a valid Bangladeshi phone number")
    private String mobileNumber;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
