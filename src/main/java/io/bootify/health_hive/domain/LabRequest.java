package io.bootify.health_hive.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.Set;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Table(name = "LabRequests")
@EntityListeners(AuditingEntityListener.class)
public class LabRequest {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            nullable = false,
            name = "\"description\"",
            columnDefinition = "longtext"
    )
    private String description;

    @Column(nullable = false, columnDefinition = "longtext")
    private String invoice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_email_id")
    private User userEmail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lab_email_id")
    private Lab labEmail;

    @OneToMany(mappedBy = "labRequest")
    private Set<LabDataUpload> labDataUploads;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(final String invoice) {
        this.invoice = invoice;
    }

    public User getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(final User userEmail) {
        this.userEmail = userEmail;
    }

    public Lab getLabEmail() {
        return labEmail;
    }

    public void setLabEmail(final Lab labEmail) {
        this.labEmail = labEmail;
    }

    public Set<LabDataUpload> getLabDataUploads() {
        return labDataUploads;
    }

    public void setLabDataUploads(final Set<LabDataUpload> labDataUploads) {
        this.labDataUploads = labDataUploads;
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
