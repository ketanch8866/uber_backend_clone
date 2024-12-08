package com.lirmo.uber.uberApp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OnBoardDriverDto {
    @NotNull
    private Long id;
   
    @NotBlank
    private String vehicalId;
}
