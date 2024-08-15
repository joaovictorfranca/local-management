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

@Slf4j
@RestController
@RequestMapping("local")
public class LocalController {

    private final LocalService localService;

    public LocalController(LocalService localService) {
        this.localService = localService;
    }

    @PostMapping
    public ResponseEntity<Object> saveLocal(@RequestBody @Valid LocalRequestDTO local){
        try {
            Local entity = localService.salvar(local);
            return new ResponseEntity<>(String.format("Local {%s} registrado com sucesso com o código: {%s}.", entity.getNome(), entity.getId()), HttpStatus.CREATED) ;
        } catch (PersistenceException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR) ;
        }
    }
    @GetMapping
    public ResponseEntity<Object> getAll(){
        try {
            return new ResponseEntity<>(localService.listarTodos(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id){
        //Criar um objeto ResponseEntity que retorna o meu objeto e um status HTTP.
        try {
            return new ResponseEntity<>(localService.buscarPorId(id), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(String.format("Registro de Local {%s} não encontrado", id), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateLocal(@PathVariable Long id, @RequestBody LocalRequestDTO local){
        try {
            localService.atualizar(local, id);
            return new ResponseEntity<>("Local atualizado com sucesso", HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Local não encontrado.", HttpStatus.NOT_FOUND);
        } catch (PersistenceException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteLocal(@PathVariable Long id){
        try {
            localService.deletar(id);
            return new ResponseEntity<>("Local deletado com sucesso", HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (PersistenceException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
