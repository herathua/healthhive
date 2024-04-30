package io.bootify.health_hive.model;

import jakarta.validation.constraints.Size;


public class DataUploadRequestDTO {

    private Long id;

    @Size(max = 100)
    private String user;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(final String user) {
        this.user = user;
    }

}
