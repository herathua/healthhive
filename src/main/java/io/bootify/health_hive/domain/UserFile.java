package io.bootify.health_hive.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "user_file")
public class UserFile {

    @Id
    private Long id;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "date_created")
    private LocalDate dateCreated;

    @Column(name = "file_hash")
    private String fileHash;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "last_updated")
    private LocalDate lastUpdated;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "data_upload_request_id")
    private Long dataUploadRequestId;

    @Column(name = "lab_data_upload_id")
    private Long labDataUploadId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "lab_request_id")
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
