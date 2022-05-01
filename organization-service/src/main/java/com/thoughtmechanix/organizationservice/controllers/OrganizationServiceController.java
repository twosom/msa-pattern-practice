package com.thoughtmechanix.organizationservice.controllers;

import com.thoughtmechanix.organizationservice.model.Organization;
import com.thoughtmechanix.organizationservice.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/organizations")
@RequiredArgsConstructor
public class OrganizationServiceController {

    private final OrganizationService organizationService;

    @GetMapping("{organizationId}")
    public Organization getOrganization(@PathVariable String organizationId) {
        return organizationService.getOrg(organizationId);
    }
}
