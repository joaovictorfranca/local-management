package com.project.local_management.service;  // Declaração do pacote da classe de teste.

import com.project.local_management.dto.LocalRequestDTO;  // Importa o DTO utilizado nas requisições.
import com.project.local_management.entity.Local;  // Importa a entidade Local, que é a representação do objeto no banco de dados.
import com.project.local_management.repository.LocalRepository;  // Importa o repositório LocalRepository que será mockado.
import jakarta.persistence.PersistenceException;  // Importa a exceção para erros de persistência no banco de dados.
import lombok.extern.slf4j.Slf4j;  // Importa o Lombok para logging, embora não esteja sendo usado aqui.
import org.junit.jupiter.api.Test;  // Importa a anotação de teste do JUnit 5.
import org.mockito.InjectMocks;  // Importa a anotação para injeção de mocks.
import org.mockito.Mock;  // Importa a anotação para criação de mocks.
import org.springframework.boot.test.context.SpringBootTest;  // Importa a anotação para testar o contexto do Spring Boot.

import java.util.ArrayList;  // Importa a classe ArrayList, não utilizada diretamente no código, mas pode ser útil.
import java.util.List;  // Importa a interface List, utilizada para listas de objetos.
import java.util.NoSuchElementException;  // Importa a exceção para quando um elemento não é encontrado.
import java.util.Optional;  // Importa a classe Optional, usada para valores que podem estar presentes ou não.

import static org.junit.jupiter.api.Assertions.*;  // Importa as asserções do JUnit para validação de testes.
import static org.mockito.ArgumentMatchers.any;  // Importa o matcher 'any' para argumentos genéricos.
import static org.mockito.Mockito.*;  // Importa métodos estáticos do Mockito para mocking e verificação.

@Slf4j  // Anotação do Lombok para adicionar um logger à classe.
@SpringBootTest  // Anotação para carregar o contexto do Spring Boot para os testes.
class LocalServiceTest {  // Declaração da classe de teste.

    @InjectMocks
    private LocalService localService;  // Injeção do mock do LocalService, que será testado.

    @Mock
    private LocalRepository localRepository;  // Criação de um mock do LocalRepository, que é uma dependência do LocalService.

    @Test
    void salvar() {  // Teste para o método 'salvar' do LocalService.
        LocalRequestDTO local = new LocalRequestDTO("Cordeiro","Serrinha","Fortaleza","Ceará");  // Cria um DTO de solicitação.
        Local entity = new Local(local);  // Cria uma entidade Local a partir do DTO.
        entity.setId(1L);  // Define um ID para a entidade Local.
        when(localRepository.save(any())).thenReturn(entity);  // Configura o mock para retornar a entidade ao salvar.

        Local result = localService.salvar(local);  // Chama o método 'salvar' do serviço.
        assertNotNull(result);  // Verifica se o resultado não é nulo.
    }

    @Test
    void salvarThrowsPersistenceException() {  // Teste para quando ocorre uma exceção de persistência ao salvar.
        LocalRequestDTO local = new LocalRequestDTO("lugar", "bairro", "cidade", "estado");  // Cria o DTO.

        // Configura o comportamento do mock para lançar a exceção PersistenceException ao salvar.
        doThrow(PersistenceException.class).when(localRepository).save(any());

        // Verifica se a exceção é lançada ao tentar salvar.
        assertThrows(PersistenceException.class, () -> localService.salvar(local));
    }

    @Test
    void listarTodos() {  // Teste para o método 'listarTodos' do LocalService.
        Local local1 = new Local(new LocalRequestDTO("Cordeiro","Serrinha","Fortaleza","Ceará"));  // Cria a primeira entidade Local.
        Local local2 = new Local(new LocalRequestDTO("Cordeiro2","Serrinha2","Fortaleza2","Ceará2"));  // Cria a segunda entidade Local.

        // Configura o mock para retornar uma lista de locais ao chamar findAllOrdenado().
        when(localRepository.findAllOrdenado()).thenReturn(List.of(local1, local2));

        List<Local> result = localService.listarTodos();  // Chama o método 'listarTodos' do serviço.
        assertNotNull(result);  // Verifica se o resultado não é nulo.
    }

    @Test
    void buscarPorId() {  // Teste para o método 'buscarPorId' do LocalService.
        LocalRequestDTO local = new LocalRequestDTO("Cordeiro","Serrinha","Fortaleza","Ceará");  // Cria o DTO.
        Local entity = new Local(local);  // Cria a entidade Local a partir do DTO.
        when(localRepository.findById(any())).thenReturn(Optional.of(entity));  // Configura o mock para retornar a entidade ao buscar por ID.

        Local result = localService.buscarPorId(1L);  // Chama o método 'buscarPorId' do serviço.
        assertNotNull(result);  // Verifica se o resultado não é nulo.
    }

    @Test
    void buscarPorIdThrowsNoSuchElementException() {  // Teste para quando ocorre uma exceção de elemento não encontrado ao buscar por ID.
        // Configura o comportamento do mock para retornar Optional.empty() ao buscar por ID.
        when(localRepository.findById(1L)).thenReturn(Optional.empty());

        // Verifica se a exceção NoSuchElementException é lançada ao tentar buscar por ID.
        assertThrows(NoSuchElementException.class, () -> localService.buscarPorId(anyLong()));
    }

    @Test
    void atualizar() {  // Teste para o método 'atualizar' do LocalService.
        LocalRequestDTO local = new LocalRequestDTO("Cordeiro","Serrinha","Fortaleza","Ceará");  // Cria o DTO.
        Local entity = new Local(local);  // Cria a entidade Local a partir do DTO.
        entity.setId(1L);  // Define um ID para a entidade Local.
        when(localRepository.findById(any())).thenReturn(Optional.of(entity));  // Configura o mock para retornar a entidade ao buscar por ID.

        localService.atualizar(local, 1L);  // Chama o método 'atualizar' do serviço.

        when(localRepository.save(any())).thenReturn(entity);  // Configura o mock para retornar a entidade ao salvar.

        verify(localRepository, times(1)).save(any());  // Verifica se o método 'save' do repositório foi chamado exatamente uma vez.
    }

    @Test
    void testAtualizarThrowsNoSuchElementException() {  // Teste para quando ocorre uma exceção de elemento não encontrado ao atualizar.
        Long id = 2L;  // Simula um ID inexistente.
        LocalRequestDTO localAltered = new LocalRequestDTO("lugar", "bairro", "cidade", "estado");  // Cria o DTO alterado.

        // Configura o comportamento do mock para retornar Optional.empty() ao buscar por ID.
        when(localRepository.findById(id)).thenReturn(Optional.empty());

        // Verifica se a exceção NoSuchElementException é lançada ao tentar atualizar com um ID inexistente.
        assertThrows(NoSuchElementException.class, () -> localService.atualizar(localAltered, id));
    }

    @Test
    void deletar() {  // Teste para o método 'deletar' do LocalService.
        localService.deletar(1L);  // Chama o método 'deletar' do serviço.

        // Verifica se o método 'deleteById' do repositório foi chamado exatamente uma vez.
        verify(localRepository, times(1)).deleteById(any());
    }

    @Test
    void testDeletarThrowsPersistenceException() {  // Teste para quando ocorre uma exceção de persistência ao deletar.
        Long id = 2L;  // Simula um ID existente.

        // Configura o comportamento do mock para lançar a exceção PersistenceException ao deletar por ID.
        doThrow(PersistenceException.class).when(localRepository).deleteById(id);

        // Verifica se a exceção PersistenceException é lançada ao tentar deletar com um ID existente.
        assertThrows(PersistenceException.class, () -> localService.deletar(id));
    }
}
