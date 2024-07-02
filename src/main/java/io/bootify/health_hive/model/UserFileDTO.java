package io.bootify.health_hive.model;

import java.time.LocalDate;




public class UserFileDTO {
    private Long id;
    private LocalDate createdDate;
    private LocalDate dateCreated;
    private String fileHash;
    private String filePath;
    private LocalDate lastUpdated;
    private String name;
    private String type;
    private Long dataUploadRequestId;
    private Long labDataUploadId;
    private Long userId;
    private Long labRequestId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getFileHash() {
        return fileHash;
    }

    public void setFileHash(String fileHash) {
        this.fileHash = fileHash;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public LocalDate getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDate lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getDataUploadRequestId() {
        return dataUploadRequestId;
    }

    public void setDataUploadRequestId(Long dataUploadRequestId) {
        this.dataUploadRequestId = dataUploadRequestId;
    }

    public Long getLabDataUploadId() {
        return labDataUploadId;
    }

    public void setLabDataUploadId(Long labDataUploadId) {
        this.labDataUploadId = labDataUploadId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getLabRequestId() {
        return labRequestId;
    }
    public void setLabRequestId(Long labRequestId) {
        this.labRequestId = labRequestId;
    }
}
