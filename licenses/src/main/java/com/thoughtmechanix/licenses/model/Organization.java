package com.thoughtmechanix.licenses.model;

import lombok.Builder;

@Builder
public record Organization(String id, String name, String contactName, String contactEmail, String contactPhone) {}
