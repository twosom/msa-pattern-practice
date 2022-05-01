package com.thoughtmechanix.licenses.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "licenses")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class License {

    @Id
    @Column(name = "license_id", nullable = false)
    private String licenseId;

    private String organizationId;
    private String productName;
    private String licenseType;
    private Integer licenseMax;
    private Integer licenseAllocated;
    private String comment;
    private String organizationName;
    private String contactName;
    private String contactEmail;
    private String contactPhone;


    public void setOrganization(Organization org) {
        this.organizationName = org.name();
        this.contactName = org.contactName();
        this.contactEmail = org.contactEmail();
        this.contactPhone = org.contactPhone();
    }
}
