package com.thoughtmechanix.organizationservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "organizations")
@AllArgsConstructor
@NoArgsConstructor
public class Organization {

    @Id
    @Column(name = "organization_id")
    private String id;
    private String name;
    private String contactName;
    private String contactEmail;
    private String contactPhone;


    public Organization(String id) {
        this.id = id;
    }
}
