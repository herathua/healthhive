package io.bootify.health_hive.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Table(name = "Files")
@EntityListeners(AuditingEntityListener.class)
public class File {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String filePath;

    @Column(nullable = false)
    private String fileHash;

    @Column(nullable = false)
    private String type;

    @Column
    private LocalDate createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lab_data_upload_id")
    private LabDataUpload labDataUpload;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "data_upload_request_id")
    private DataUploadRequest dataUploadRequest;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(final String filePath) {
        this.filePath = filePath;
    }

    public String getFileHash() {
        return fileHash;
    }

    public void setFileHash(final String fileHash) {
        this.fileHash = fileHash;
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

    public LabDataUpload getLabDataUpload() {
        return labDataUpload;
    }

    public void setLabDataUpload(final LabDataUpload labDataUpload) {
        this.labDataUpload = labDataUpload;
    }

    public DataUploadRequest getDataUploadRequest() {
        return dataUploadRequest;
    }

    public void setDataUploadRequest(final DataUploadRequest dataUploadRequest) {
        this.dataUploadRequest = dataUploadRequest;
    }

    public OffsetDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(final OffsetDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public OffsetDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(final OffsetDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

}
