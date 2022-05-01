package com.thoughtmechanix.organizationservice.repository;

import com.thoughtmechanix.organizationservice.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, String> {

}
