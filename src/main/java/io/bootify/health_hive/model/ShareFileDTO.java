package io.bootify.health_hive.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class ShareFileDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String fileHash;

    private Long labReportShare;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getFileHash() {
        return fileHash;
    }

    public void setFileHash(final String fileHash) {
        this.fileHash = fileHash;
    }

    public Long getLabReportShare() {
        return labReportShare;
    }

    public void setLabReportShare(final Long labReportShare) {
        this.labReportShare = labReportShare;
    }

}
