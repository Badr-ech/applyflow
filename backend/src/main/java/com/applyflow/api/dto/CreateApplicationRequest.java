package com.applyflow.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateApplicationRequest {

    @NotBlank(message = "Company name is required")
    private String company;

    @NotBlank(message = "Position is required")
    private String position;

    private String notes;

    private String location;

    private Integer salaryExpectation;

    private String jobUrl;
}
