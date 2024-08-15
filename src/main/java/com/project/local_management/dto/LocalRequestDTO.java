package com.project.local_management.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LocalRequestDTO(@NotNull(message = "O nome é obrigatório.")
                              @NotBlank(message = "O nome é obrigatório.") String nome,
                              String bairro,
                              String cidade,
                              String estado) {
}
