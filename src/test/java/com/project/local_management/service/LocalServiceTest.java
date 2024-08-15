package com.project.local_management.service;

import com.project.local_management.dto.LocalRequestDTO;
import com.project.local_management.entity.Local;
import com.project.local_management.repository.LocalRepository;
import jakarta.persistence.PersistenceException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Slf4j
@SpringBootTest
class LocalServiceTest {

    @InjectMocks
    private LocalService localService;

    @Mock
    private LocalRepository localRepository;

    @Test
    void salvar() {

        LocalRequestDTO local = new LocalRequestDTO("Cordeiro","Serrinha","Fortaleza","Ceará");

        Local entity = new Local(local);
        entity.setId(1L);
        when(localRepository.save(any())).thenReturn(entity);

        Local result = localService.salvar(local);
        assertNotNull(result);
    }

    @Test
    void salvarThrowsPersistenceException() {
        LocalRequestDTO local = new LocalRequestDTO("lugar", "bairro", "cidade", "estado");

        // Configura o comportamento do mock para lançar a exceção
        doThrow(PersistenceException.class).when(localRepository).save(any());

        assertThrows(PersistenceException.class, () -> localService.salvar(local));
    }



    @Test
    void listarTodos() {

        Local local1 = new Local(new LocalRequestDTO("Cordeiro","Serrinha","Fortaleza","Ceará"));
        Local local2 = new Local(new LocalRequestDTO("Cordeiro2","Serrinha2","Fortaleza2","Ceará2"));

        when(localRepository.findAllOrdenado()).thenReturn(List.of(local1, local2));

        List<Local> result = localService.listarTodos();
        assertNotNull(result);
    }

    @Test
    void buscarPorId() {

        LocalRequestDTO local = new LocalRequestDTO("Cordeiro","Serrinha","Fortaleza","Ceará");
        Local entity = new Local(local);
        when(localRepository.findById(any())).thenReturn(Optional.of(entity));

        Local result = localService.buscarPorId(1L);
        assertNotNull(result);
    }

    @Test
    void buscarPorIdThrowsNoSuchElementException() {

        // Configura o comportamento do mock para retornar Optional.empty()
        when(localRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> localService.buscarPorId(anyLong()));
    }


    @Test
    void atualizar() {

        LocalRequestDTO local = new LocalRequestDTO("Cordeiro","Serrinha","Fortaleza","Ceará");
        Local entity = new Local(local);
        entity.setId(1L);
        when(localRepository.findById(any())).thenReturn(Optional.of(entity));
        localService.atualizar(local, 1L);

        when(localRepository.save(any())).thenReturn(entity);

        verify(localRepository, times(1)).save(any());
    }

    @Test
    void testAtualizarThrowsPersistenceException() {
        Long id = 1L;
        LocalRequestDTO localAltered = new LocalRequestDTO("lugar", "bairro", "cidade", "estado");

        // Configura o comportamento do mock para lançar a exceção PersistenceException
        doThrow(PersistenceException.class).when(localRepository).save(any());

        assertThrows(PersistenceException.class, () -> localService.atualizar(localAltered, id));
    }

    @Test
    void testAtualizarThrowsNoSuchElementException() {
        Long id = 2L; // Simula um ID inexistente
        LocalRequestDTO localAltered = new LocalRequestDTO("lugar", "bairro", "cidade", "estado");

        // Configura o comportamento do mock para retornar Optional.empty()
        when(localRepository.findById(id)).thenReturn(Optional.empty());

        // Verifica se a exceção é lançada corretamente
        assertThrows(NoSuchElementException.class, () -> localService.atualizar(localAltered, id));
    }

    @Test
    void deletar() {

        localService.deletar(1L);

        verify(localRepository, times(1)).deleteById(any());
    }

    @Test
    void testDeletarThrowsNoSuchElementException() {
        Long id = 1L;

        // Configura o comportamento do mock para retornar Optional.empty()
        when(localRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> localService.deletar(id));
    }

    @Test
    void testDeletarThrowsPersistenceException() {
        Long id = 2L; // Simula um ID existente

        // Configura o comportamento do mock para lançar a exceção PersistenceException
        doThrow(PersistenceException.class).when(localRepository).deleteById(id);

        assertThrows(PersistenceException.class, () -> localService.deletar(id));
    }
}