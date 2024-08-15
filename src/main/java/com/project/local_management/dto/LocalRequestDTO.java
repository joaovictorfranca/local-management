package com.project.local_management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LocalRequestDTO(
        @NotNull(message = "O nome é obrigatório.") // Anotação para garantir que o campo "nome" não seja nulo
        @NotBlank(message = "O nome é obrigatório.") // Anotação para garantir que o campo "nome" não seja vazio
        String nome, // Campo para o nome do local
        String bairro, // Campo para o bairro do local
        String cidade, // Campo para a cidade do local
        String estado // Campo para o estado do local
) {
}
