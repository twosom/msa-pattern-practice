package com.thoughtmechanix.licenses.services;

import com.thoughtmechanix.licenses.clients.OrganizationDiscoveryClient;
import com.thoughtmechanix.licenses.clients.OrganizationFeignClient;
import com.thoughtmechanix.licenses.clients.OrganizationRestClient;
import com.thoughtmechanix.licenses.config.ServiceConfig;
import com.thoughtmechanix.licenses.model.License;
import com.thoughtmechanix.licenses.model.Organization;
import com.thoughtmechanix.licenses.repository.LicenseRepository;
import com.thoughtmechanix.licenses.utils.UserContextHolder;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;



@Slf4j
@Service
@RequiredArgsConstructor
public class LicenseService {

    private final OrganizationDiscoveryClient organizationDiscoveryClient;
    private final OrganizationRestClient organizationRestClient;
    private final OrganizationFeignClient organizationFeignClient;
    private final LicenseRepository licenseRepository;
    private final ServiceConfig serviceConfig;

    @Bulkhead(name = "helloBulkhead", type = Bulkhead.Type.THREADPOOL, fallbackMethod = "buildFallbackLicenseList")
//    @TimeLimiter(name = "helloRateLimiter", fallbackMethod = "buildFallbackLicenseList")
    public CompletableFuture<List<License>> getLicensesByOrg(String organizationId) {
        Thread thread = Thread.currentThread();
        log.info("current thread name : {}", thread.getName());
        log.debug("LicenseService.getLicenseByOrg id: {}", UserContextHolder.getContext().getCorrelationId());
        return CompletableFuture.supplyAsync(() -> {
            randomlyRunLong();
            return licenseRepository.findByOrganizationId(organizationId);
        });
    }

    public CompletableFuture<List<License>> buildFallbackLicenseList(String organizationId, Exception e) {
        return CompletableFuture.supplyAsync(() -> {
            e.printStackTrace();
            Thread thread = Thread.currentThread();
            log.info("current thread name : {}", thread.getName());
            List<License> fallbackList = new ArrayList<>();
            License license = License.builder()
                    .licenseId("0000000-00-00000")
                    .organizationId(organizationId)
                    .productName("Sorry no licensing information currently available")
                    .build();
            fallbackList.add(license);
            return fallbackList;
        });
    }


    public License getLicense(String organizationId, String licenseId) {
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId)
                .orElseThrow(NullPointerException::new);
        license.setComment(serviceConfig.getProperty());
        return license;
    }

    public License getLicense(String organizationId, String licenseId, String clientType) {
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId)
                .orElseGet(License::new);

        Organization org = retrieveOrgInfo(organizationId, clientType);
        license.setOrganization(org);
        license.setComment(serviceConfig.getProperty());
        return license;
    }

    private Organization retrieveOrgInfo(String organizationId, String clientType) {
        return switch (clientType) {
            case "feign" -> {
                System.out.println("I am using the feign client");
                yield organizationFeignClient.getOrganization(organizationId);
            }
            case "rest" -> {
                System.out.println("I am using the rest client");
                yield organizationRestClient.getOrganization(organizationId);
            }
            case "discovery" -> {
                System.out.println("I am using the discovery client");
                yield organizationDiscoveryClient.getOrganization(organizationId);
            }
            default -> organizationRestClient.getOrganization(organizationId);
        };
    }

    public List<License> getLicenseByOrg(String organizationId) {
        return licenseRepository.findByOrganizationId(organizationId);
    }

    public void saveLicense(License license) {
        license.setLicenseId(UUID.randomUUID().toString());
        licenseRepository.save(license);
    }

    private void randomlyRunLong() {
        Random random = new Random();
        int randomNum = random.nextInt((3 - 1) + 1) + 1;
        if (randomNum == 3) sleep();

    }

    private void sleep() {
        try {
            Thread.sleep(11_000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
