package com.project.local_management.entity;

import com.project.local_management.dto.LocalRequestDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Table(name = "locals")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Local {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "bairro")
    private String bairro;

    @Column(name = "cidade")
    private String cidade;

    @Column(name = "estado")
    private String estado;

    @Column(name = "dt_reg")
    private LocalDateTime dataRegistro;

    @Column(name = "dt_mdf")
    private LocalDateTime dataModificacao;

    public Local(LocalRequestDTO localRequestDTO) {
        this.nome = localRequestDTO.nome();
        this.bairro = localRequestDTO.bairro();
        this.cidade = localRequestDTO.cidade();
        this.estado = localRequestDTO.estado();
        this.dataRegistro = LocalDateTime.now(ZoneId.of("America/Fortaleza"));
        this.dataModificacao = LocalDateTime.now(ZoneId.of("America/Fortaleza"));
    }
}
