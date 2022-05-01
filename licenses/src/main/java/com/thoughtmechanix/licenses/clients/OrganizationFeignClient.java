package com.thoughtmechanix.licenses.clients;

import com.thoughtmechanix.licenses.model.Organization;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("ORGANIZATIONSERVICE")
public interface OrganizationFeignClient {
    @GetMapping("/v1/organizations/{organizationId}")
    Organization getOrganization(@PathVariable String organizationId);

}
