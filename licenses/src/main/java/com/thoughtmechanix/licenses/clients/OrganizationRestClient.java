package com.thoughtmechanix.licenses.clients;

import com.thoughtmechanix.licenses.model.Organization;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.HttpMethod.GET;

@Component
@RequiredArgsConstructor
public class OrganizationRestClient {

    private final RestTemplate restTemplate;

    public Organization getOrganization(String organizationId) {
        ResponseEntity<Organization> restExchange = restTemplate.exchange(
                "http://organizationservice/v1/organizations/{organizationId}",
                GET,
                null,
                Organization.class,
                organizationId);
        return restExchange.getBody();
    }
}
