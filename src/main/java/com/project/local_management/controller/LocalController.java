package com.project.local_management.controller;

import com.project.local_management.dto.LocalRequestDTO;
import com.project.local_management.entity.Local;
import com.project.local_management.service.LocalService;
import jakarta.persistence.PersistenceException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Slf4j // Anotação para logging (registros)
@RestController // Indica que esta classe é um controlador REST
@RequestMapping("local") // Mapeia a URL base para "/local"
public class LocalController {

    // Injeta o serviço LocalService
    private final LocalService localService;

    // Construtor que recebe uma instância de LocalService
    public LocalController(LocalService localService) {
        this.localService = localService;
    }

    // Endpoint para salvar um novo local
    @PostMapping // Mapeia a requisição HTTP POST para este método
    public ResponseEntity<Object> saveLocal(@RequestBody @Valid LocalRequestDTO local){
        try {
            Local entity = localService.salvar(local); // Chama o serviço para salvar o local
            return new ResponseEntity<>(String.format("Local ( %s ) registrado com sucesso com o código: ( %s ).", entity.getNome(), entity.getId()), HttpStatus.CREATED);
        } catch (PersistenceException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint para listar todos os locais
    @GetMapping // Mapeia a requisição HTTP GET para este método
    public ResponseEntity<Object> getAll(){
        try {
            return new ResponseEntity<>(localService.listarTodos(), HttpStatus.OK); // Chama o serviço para listar todos os locais
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint para buscar um local pelo ID
    @GetMapping("/{id}") // Mapeia a requisição HTTP GET com um parâmetro de ID
    public ResponseEntity<Object> getById(@PathVariable Long id){
        try {
            return new ResponseEntity<>(localService.buscarPorId(id), HttpStatus.OK); // Chama o serviço para buscar o local pelo ID
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(String.format("Registro de Local ( %s ) não encontrado", id), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint para atualizar um local pelo ID
    @PutMapping("/{id}") // Mapeia a requisição HTTP PUT com um parâmetro de ID
    public ResponseEntity<Object> updateLocal(@PathVariable Long id, @RequestBody LocalRequestDTO local){
        try {
            localService.atualizar(local, id); // Chama o serviço para atualizar o local
            return new ResponseEntity<>("Local atualizado com sucesso", HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Local não encontrado.", HttpStatus.NOT_FOUND);
        } catch (PersistenceException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint para deletar um local pelo ID
    @DeleteMapping("/{id}") // Mapeia a requisição HTTP DELETE com um parâmetro de ID
    public ResponseEntity<Object> deleteLocal(@PathVariable Long id){
        try {
            localService.deletar(id); // Chama o serviço para deletar o local pelo ID
            return new ResponseEntity<>("Local deletado com sucesso", HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (PersistenceException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
