package io.bootify.health_hive.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;


public class UserDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String fullName;

    @NotNull
    @Size(max = 255)
    @UserEmailUnique
    private String email;

    @NotNull
    @Size(max = 255)
    private String telephoneNumber;

    @NotNull
    private Gender gender;

    @NotNull
    private Integer age;

    @NotNull
    private LocalDate dateOfBirth;

    @Size(max = 5)
    private String birthCertificateNumber;

    @NotNull
    @Size(max = 12)
    @UserNicUnique
    private String nic;

    @Size(max = 60)
    private String emergencyContactName;

    @Size(max = 10)
    private String emergencyContactNumber;


    private String profilePictureUrl;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(final String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(final String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(final Gender gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(final Integer age) {
        this.age = age;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(final LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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

    public String getProfilePictureUrl() { return profilePictureUrl;}
    public void setProfilePictureUrl(final String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

}
