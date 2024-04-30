package io.bootify.health_hive.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class LabDTO {

    @Size(max = 100)
    @LabEmailValid
    private String email;

    @NotNull
    @Size(max = 100)
    @LabLabRegIDUnique
    private String labRegID;

    @NotNull
    @Size(max = 255)
    private String labName;

    @NotNull
    private String address;

    @NotNull
    @Size(max = 12)
    private String telephone;

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getLabRegID() {
        return labRegID;
    }

    public void setLabRegID(final String labRegID) {
        this.labRegID = labRegID;
    }

    public String getLabName() {
        return labName;
    }

    public void setLabName(final String labName) {
        this.labName = labName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(final String telephone) {
        this.telephone = telephone;
    }

}
