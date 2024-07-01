package io.bootify.health_hive.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public class FileDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    private String type;
    
    private String filePath;
    private String fileHash;

    private LocalDate createdDate;

    private Long labDataUpload;

    private Long dataUploadReqeust;


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

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(final LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Long getLabDataUpload() {
        return labDataUpload;
    }

    public void setLabDataUpload(final Long labDataUpload) {
        this.labDataUpload = labDataUpload;
    }

    public Long getDataUploadRequest() {
        return dataUploadReqeust;
    }

    public void setDataUploadReqeust(final Long dataUploadReqeust) {
        this.dataUploadReqeust = dataUploadReqeust;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFileHash(String fileHash) {
        this.fileHash = fileHash;
    }

    public String getFileHash() {
        return fileHash;
    }
}
