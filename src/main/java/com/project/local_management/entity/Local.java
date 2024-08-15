package com.project.local_management.entity;

import com.project.local_management.dto.LocalRequestDTO;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Table(name = "locals") // Define o nome da tabela no banco de dados
@Entity // Indica que essa classe é uma entidade JPA (será mapeada para uma tabela)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Local {
    @Id // Define que o campo abaixo é a chave primária
    @Column(name = "id") // Define o nome da coluna no banco de dados
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Gera automaticamente o valor do ID
    private Long id;

    @Column(name = "nome") // Mapeia o campo "nome" para a coluna "nome" no banco de dados
    private String nome;

    @Column(name = "bairro") // Mapeia o campo "bairro" para a coluna "bairro" no banco de dados
    private String bairro;

    @Column(name = "cidade") // Mapeia o campo "cidade" para a coluna "cidade" no banco de dados
    private String cidade;

    @Column(name = "estado") // Mapeia o campo "estado" para a coluna "estado" no banco de dados
    private String estado;

    @Column(name = "dt_reg") // Mapeia o campo "dataRegistro" para a coluna "dt_reg" no banco de dados
    private LocalDateTime dataRegistro;

    @Column(name = "dt_mdf") // Mapeia o campo "dataModificacao" para a coluna "dt_mdf" no banco de dados
    private LocalDateTime dataModificacao;

    // Construtor que recebe um objeto LocalRequestDTO e preenche os campos correspondentes
    public Local(LocalRequestDTO localRequestDTO) {
        this.nome = localRequestDTO.nome();
        this.bairro = localRequestDTO.bairro();
        this.cidade = localRequestDTO.cidade();
        this.estado = localRequestDTO.estado();
        this.dataRegistro = LocalDateTime.now(ZoneId.of("America/Fortaleza"));
        this.dataModificacao = LocalDateTime.now(ZoneId.of("America/Fortaleza"));
    }
}
