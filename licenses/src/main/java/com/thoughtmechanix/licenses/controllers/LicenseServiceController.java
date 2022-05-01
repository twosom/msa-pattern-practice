package com.thoughtmechanix.licenses.controllers;

import com.thoughtmechanix.licenses.model.License;
import com.thoughtmechanix.licenses.services.LicenseService;
import com.thoughtmechanix.licenses.utils.UserContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequestMapping("/v1/organizations/{organizationId}/licenses")
@RequiredArgsConstructor
public class LicenseServiceController {

    private final LicenseService licenseService;

    @GetMapping
    public CompletableFuture<List<License>> getLicense(@PathVariable String organizationId) {
        log.debug("LicenseServiceController Correlation id: {}", UserContextHolder.getContext().getCorrelationId());
        return licenseService.getLicensesByOrg(organizationId);
    }

    @GetMapping("/{licenseId}")
    public License getLicenses(@PathVariable("organizationId") String organizationId,
                               @PathVariable("licenseId") String licenseId) {
        return License.builder()
                .licenseId(licenseId)
                .organizationId(organizationId)
                .productName("Telco")
                .licenseType("Seat")
                .build();

    }

    @GetMapping("/{licenseId}/{clientType}")
    public License getLicenseWithClient(@PathVariable String clientType,
                                        @PathVariable String licenseId,
                                        @PathVariable String organizationId) {
        return licenseService.getLicense(organizationId, licenseId, clientType);
    }


}
