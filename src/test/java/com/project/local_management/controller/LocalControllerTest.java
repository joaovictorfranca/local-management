package com.project.local_management.controller;

import com.project.local_management.dto.LocalRequestDTO;
import com.project.local_management.entity.Local;
import com.project.local_management.service.LocalService;
import jakarta.persistence.PersistenceException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@Slf4j
@SpringBootTest
class LocalControllerTest {

    @InjectMocks
    private LocalController localController;

    @Mock
    private LocalService localService;

    @Test
    void saveLocal() {
        LocalRequestDTO local = new LocalRequestDTO("Cordeiro","Serrinha","Fortaleza","Ceará");
        Local entity = new Local(local);
        when(localService.salvar(any())).thenReturn(entity);

        ResponseEntity<Object> result = localController.saveLocal(local);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }
    @Test
    void saveLocalPersistenceException() {
        LocalRequestDTO local = new LocalRequestDTO("Cordeiro","Serrinha","Fortaleza","Ceará");

        when(localService.salvar(any())).thenThrow(PersistenceException.class);

        ResponseEntity<Object> result = localController.saveLocal(local);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }

    @Test
    void getAll() {
        ResponseEntity<Object> result = localController.getAll();
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
    @Test
    void getAllException() {
        when(localController.getAll()).thenThrow(PersistenceException.class);

        ResponseEntity<Object> result = localController.getAll();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }

    @Test
    void getById() {

        when(localService.buscarPorId(anyLong())).thenReturn(
                new Local(new LocalRequestDTO("lugar", "bairro", "cidade", "estado")));

        ResponseEntity<Object> result = localController.getById(1L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result);
    }

    @Test
    void getByIdNoSuchElementException() {

        when(localService.buscarPorId(anyLong())).thenThrow(NoSuchElementException.class);

        ResponseEntity<Object> result = localController.getById(1L);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertNotNull(result);
    }

    @Test
    void getByIdRuntimeException() {

        when(localService.buscarPorId(anyLong())).thenThrow(RuntimeException.class);

        ResponseEntity<Object> result = localController.getById(1L);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
        assertNotNull(result);
    }

    @Test
    void updateLocal() {

        ResponseEntity<Object> result = localController.updateLocal(1L, new LocalRequestDTO("lugar", "bairro", "cidade", "estado"));
        assertEquals(HttpStatus.OK, result.getStatusCode());

    }

    @Test
    void updateLocalNoSuchElementException() {
        LocalRequestDTO local = new LocalRequestDTO("lugar", "bairro", "cidade", "estado");

        // Configura o comportamento do serviço para lançar a exceção
        doThrow(NoSuchElementException.class).when(localService).atualizar(any(), anyLong());

        ResponseEntity<Object> result = localController.updateLocal(1L, local);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void updateLocalPersistenceException() {
        LocalRequestDTO local = new LocalRequestDTO("lugar", "bairro", "cidade", "estado");

        // Configura o comportamento do serviço para lançar a exceção
        doThrow(PersistenceException.class).when(localService).atualizar(any(), anyLong());

        ResponseEntity<Object> result = localController.updateLocal(1L, local);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }


    @Test
    void deleteLocal() {
        ResponseEntity<Object> result = localController.deleteLocal(1L);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void deleteLocalNoSuchElementException() {
        doThrow(NoSuchElementException.class).when(localService).deletar(anyLong());

        ResponseEntity<Object> result = localController.deleteLocal(1L);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void deleteLocalPersistenceException() {
        doThrow(PersistenceException.class).when(localService).deletar(anyLong());

        ResponseEntity<Object> result = localController.deleteLocal(1L);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }
}