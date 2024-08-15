package com.project.local_management.controller;  // Declaração do pacote da classe de teste.

import com.project.local_management.dto.LocalRequestDTO;  // Importa o DTO utilizado nas requisições.
import com.project.local_management.entity.Local;  // Importa a entidade Local, que é a representação do objeto no banco de dados.
import com.project.local_management.service.LocalService;  // Importa o serviço que será mockado.
import jakarta.persistence.PersistenceException;  // Importa a exceção para erros de persistência no banco de dados.
import lombok.extern.slf4j.Slf4j;  // Importa o Lombok para logging, embora não esteja sendo usado aqui.
import org.junit.jupiter.api.Test;  // Importa a anotação de teste do JUnit 5.
import org.mockito.InjectMocks;  // Importa a anotação para injeção de mocks.
import org.mockito.Mock;  // Importa a anotação para criação de mocks.
import org.springframework.boot.test.context.SpringBootTest;  // Importa a anotação para testar o contexto do Spring Boot.
import org.springframework.http.HttpStatus;  // Importa o HttpStatus para verificar o status das respostas.
import org.springframework.http.ResponseEntity;  // Importa a ResponseEntity para as respostas HTTP.

import java.util.NoSuchElementException;  // Importa a exceção para quando um elemento não é encontrado.

import static org.junit.jupiter.api.Assertions.*;  // Importa as asserções do JUnit para validação de testes.
import static org.mockito.ArgumentMatchers.any;  // Importa o matcher 'any' para argumentos genéricos.
import static org.mockito.ArgumentMatchers.anyLong;  // Importa o matcher 'anyLong' para argumentos do tipo long.
import static org.mockito.Mockito.*;  // Importa métodos estáticos do Mockito para mocking e verificação.

@Slf4j  // Anotação do Lombok para adicionar um logger à classe.
@SpringBootTest  // Anotação para carregar o contexto do Spring Boot para os testes.
class LocalControllerTest {  // Declaração da classe de teste.

    @InjectMocks
    private LocalController localController;  // Injeção do mock do LocalController, que será testado.

    @Mock
    private LocalService localService;  // Criação de um mock do LocalService, que é uma dependência do LocalController.

    @Test
    void saveLocal() {  // Teste para o método 'saveLocal' do LocalController.
        LocalRequestDTO local = new LocalRequestDTO("Cordeiro","Serrinha","Fortaleza","Ceará");  // Cria um DTO de solicitação.
        Local entity = new Local(local);  // Cria uma entidade Local a partir do DTO.
        when(localService.salvar(any())).thenReturn(entity);  // Configura o mock para retornar a entidade quando o método 'salvar' é chamado.

        ResponseEntity<Object> result = localController.saveLocal(local);  // Chama o método 'saveLocal' do controller.
        assertEquals(HttpStatus.CREATED, result.getStatusCode());  // Verifica se o status da resposta é 201 Created.
    }

    @Test
    void saveLocalPersistenceException() {  // Teste para quando ocorre uma exceção de persistência ao salvar.
        LocalRequestDTO local = new LocalRequestDTO("Cordeiro","Serrinha","Fortaleza","Ceará");  // Cria o DTO.

        when(localService.salvar(any())).thenThrow(PersistenceException.class);  // Configura o mock para lançar uma exceção ao salvar.

        ResponseEntity<Object> result = localController.saveLocal(local);  // Chama o método 'saveLocal' do controller.
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());  // Verifica se o status da resposta é 500 Internal Server Error.
    }

    @Test
    void getAll() {  // Teste para o método 'getAll' do LocalController.
        ResponseEntity<Object> result = localController.getAll();  // Chama o método 'getAll' do controller.
        assertEquals(HttpStatus.OK, result.getStatusCode());  // Verifica se o status da resposta é 200 OK.
    }

    @Test
    void getAllException() {  // Teste para quando ocorre uma exceção ao obter todos os locais.
        when(localController.getAll()).thenThrow(PersistenceException.class);  // Configura o mock para lançar uma exceção ao obter todos.

        ResponseEntity<Object> result = localController.getAll();  // Chama o método 'getAll' do controller.
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());  // Verifica se o status da resposta é 500 Internal Server Error.
    }

    @Test
    void getById() {  // Teste para o método 'getById' do LocalController.
        when(localService.buscarPorId(anyLong())).thenReturn(  // Configura o mock para retornar uma entidade Local ao buscar por ID.
                new Local(new LocalRequestDTO("lugar", "bairro", "cidade", "estado")));

        ResponseEntity<Object> result = localController.getById(1L);  // Chama o método 'getById' do controller.

        assertEquals(HttpStatus.OK, result.getStatusCode());  // Verifica se o status da resposta é 200 OK.
        assertNotNull(result);  // Verifica se o resultado não é nulo.
    }

    @Test
    void getByIdNoSuchElementException() {  // Teste para quando o ID não é encontrado.
        when(localService.buscarPorId(anyLong())).thenThrow(NoSuchElementException.class);  // Configura o mock para lançar uma exceção ao buscar por ID.

        ResponseEntity<Object> result = localController.getById(1L);  // Chama o método 'getById' do controller.

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());  // Verifica se o status da resposta é 404 Not Found.
        assertNotNull(result);  // Verifica se o resultado não é nulo.
    }

    @Test
    void getByIdRuntimeException() {  // Teste para quando ocorre uma exceção de runtime ao buscar por ID.
        when(localService.buscarPorId(anyLong())).thenThrow(RuntimeException.class);  // Configura o mock para lançar uma exceção ao buscar por ID.

        ResponseEntity<Object> result = localController.getById(1L);  // Chama o método 'getById' do controller.

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());  // Verifica se o status da resposta é 500 Internal Server Error.
        assertNotNull(result);  // Verifica se o resultado não é nulo.
    }

    @Test
    void updateLocal() {  // Teste para o método 'updateLocal' do LocalController.
        ResponseEntity<Object> result = localController.updateLocal(1L, new LocalRequestDTO("lugar", "bairro", "cidade", "estado"));  // Chama o método 'updateLocal' do controller.
        assertEquals(HttpStatus.OK, result.getStatusCode());  // Verifica se o status da resposta é 200 OK.
    }

    @Test
    void updateLocalNoSuchElementException() {  // Teste para quando ocorre uma exceção de elemento não encontrado ao atualizar.
        LocalRequestDTO local = new LocalRequestDTO("lugar", "bairro", "cidade", "estado");  // Cria o DTO.

        doThrow(NoSuchElementException.class).when(localService).atualizar(any(), anyLong());  // Configura o mock para lançar uma exceção ao atualizar.

        ResponseEntity<Object> result = localController.updateLocal(1L, local);  // Chama o método 'updateLocal' do controller.
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());  // Verifica se o status da resposta é 404 Not Found.
    }

    @Test
    void updateLocalPersistenceException() {  // Teste para quando ocorre uma exceção de persistência ao atualizar.
        LocalRequestDTO local = new LocalRequestDTO("lugar", "bairro", "cidade", "estado");  // Cria o DTO.

        doThrow(PersistenceException.class).when(localService).atualizar(any(), anyLong());  // Configura o mock para lançar uma exceção ao atualizar.

        ResponseEntity<Object> result = localController.updateLocal(1L, local);  // Chama o método 'updateLocal' do controller.
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());  // Verifica se o status da resposta é 500 Internal Server Error.
    }

    @Test
    void deleteLocal() {  // Teste para o método 'deleteLocal' do LocalController.
        ResponseEntity<Object> result = localController.deleteLocal(1L);  // Chama o método 'deleteLocal' do controller.
        assertEquals(HttpStatus.OK, result.getStatusCode());  // Verifica se o status da resposta é 200 OK.
    }

    @Test
    void deleteLocalNoSuchElementException() {  // Teste para quando ocorre uma exceção de elemento não encontrado ao deletar.
        doThrow(NoSuchElementException.class).when(localService).deletar(anyLong());  // Configura o mock para lançar uma exceção ao deletar.

        ResponseEntity<Object> result = localController.deleteLocal(1L);  // Chama o método 'deleteLocal' do controller.
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }
}