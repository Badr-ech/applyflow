package com.applyflow.api.dto;

import com.applyflow.domain.ApplicationStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

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
