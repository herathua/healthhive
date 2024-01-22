package io.test_group.my_app_test.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;


public class UserDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    @Size(max = 255)
    private String email;

    @NotNull
    @Size(max = 10)
    private String mobileNumber;

    @NotNull
    @Size(max = 255)
    private String address;

    @NotNull
    private LocalDate dateOfBIrth;

    @NotNull
    @Size(max = 4)
    private String birthCertificateNumber;

    @Size(max = 12)
    private String nic;

    @Size(max = 255)
    private String emergencyContactName;

    @Size(max = 255)
    private String emergencyContactNumber;

    @NotNull
    private Gender gender;

    private Long children;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(final String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public LocalDate getDateOfBIrth() {
        return dateOfBIrth;
    }

    public void setDateOfBIrth(final LocalDate dateOfBIrth) {
        this.dateOfBIrth = dateOfBIrth;
    }

    public String getBirthCertificateNumber() {
        return birthCertificateNumber;
    }

    public void setBirthCertificateNumber(final String birthCertificateNumber) {
        this.birthCertificateNumber = birthCertificateNumber;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(final String nic) {
        this.nic = nic;
    }

    public String getEmergencyContactName() {
        return emergencyContactName;
    }

    public void setEmergencyContactName(final String emergencyContactName) {
        this.emergencyContactName = emergencyContactName;
    }

    public String getEmergencyContactNumber() {
        return emergencyContactNumber;
    }

    public void setEmergencyContactNumber(final String emergencyContactNumber) {
        this.emergencyContactNumber = emergencyContactNumber;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(final Gender gender) {
        this.gender = gender;
    }

    public Long getChildren() {
        return children;
    }

    public void setChildren(final Long children) {
        this.children = children;
    }

}
