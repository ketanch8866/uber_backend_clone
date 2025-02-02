package com.lirmo.uber.uberApp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OnBoardDriverDto {
    @NotNull(message = "please enter user id")
    private Long id;

    @NotBlank(message = "please enter vehical id")
    private String vehicalId;
}
