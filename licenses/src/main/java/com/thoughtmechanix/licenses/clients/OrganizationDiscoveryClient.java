package com.thoughtmechanix.licenses.clients;

import com.thoughtmechanix.licenses.model.Organization;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.springframework.http.HttpMethod.*;

@Component
@RequiredArgsConstructor
public class OrganizationDiscoveryClient {

    private final DiscoveryClient discoveryClient;


    public Organization getOrganization(String organizationId) {
        RestTemplate restTemplate = new RestTemplate();
        //조직 서비스의 모든 인스턴스 목록 얻기
        List<ServiceInstance> instances = discoveryClient.getInstances("organizationservice");
        if (instances.isEmpty()) {
            return null;
        }

        String serviceUri = "%s/v1/organizations/%s"
                .formatted(instances.get(0).getUri().toString(), organizationId);

        ResponseEntity<Organization> restExchange =
                restTemplate.exchange(serviceUri,
                        GET,
                        null,
                        Organization.class,
                        organizationId);

        return restExchange.getBody();
    }
}
