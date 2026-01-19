package com.applyflow.api.dto;

import com.applyflow.domain.ApplicationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TransitionRequest {

    @NotNull(message = "Target status is required")
    private ApplicationStatus newStatus;

    private String comment;
}
