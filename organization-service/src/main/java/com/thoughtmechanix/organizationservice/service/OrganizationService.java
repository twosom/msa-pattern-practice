package com.thoughtmechanix.organizationservice.service;

import com.thoughtmechanix.organizationservice.model.Organization;
import com.thoughtmechanix.organizationservice.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    public Organization getOrg(String organizationId) {
        return organizationRepository.findById(organizationId)
                .orElseGet(() -> new Organization(organizationId));
    }
}
