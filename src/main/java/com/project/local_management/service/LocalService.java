package com.project.local_management.service;

import com.project.local_management.dto.LocalRequestDTO;
import com.project.local_management.entity.Local;
import com.project.local_management.repository.LocalRepository;
import jakarta.persistence.PersistenceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j // Anotação para logging (registros)
@Service // Indica que esta classe é um componente de serviço gerenciado pelo Spring
public class LocalService {

    // Define o fuso horário de Fortaleza
    private static final ZoneId fortalezaZoneId = ZoneId.of("America/Fortaleza");

    // Injeta o repositório LocalRepository
    private final LocalRepository localRepository;

    // Construtor que recebe uma instância de LocalRepository
    public LocalService(LocalRepository localRepository) {
        this.localRepository = localRepository;
    }

    // Método para salvar um novo local
    public Local salvar(LocalRequestDTO localNew) throws PersistenceException {
        Local local = new Local();
        local.setNome(localNew.nome());
        local.setBairro(localNew.bairro());
        local.setCidade(localNew.cidade());
        local.setEstado(localNew.estado());
        local.setDataRegistro(LocalDateTime.now(fortalezaZoneId));
        local.setDataModificacao(LocalDateTime.now(fortalezaZoneId));
        return localRepository.save(local);
    }

    // Método para listar todos os locais ordenados pela data de registro
    public List<Local> listarTodos(){
        return localRepository.findAllOrdenado();
    }

    // Método para buscar um local pelo ID
    public Local buscarPorId(Long id) throws NoSuchElementException {
        return localRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Local não encontrado com o ID: " + id));
    }

    // Método para atualizar um local
    public void atualizar(LocalRequestDTO localAltered, Long id) throws PersistenceException, NoSuchElementException {
        Local local = buscarPorId(id);
        if (localAltered != null) {
            local.setNome(localAltered.nome() != null ? localAltered.nome() : local.getNome());
            local.setCidade(localAltered.cidade() != null ? localAltered.cidade() : local.getCidade());
            local.setEstado(localAltered.estado() != null ? localAltered.estado() : local.getEstado());
            local.setBairro(localAltered.bairro() != null ? localAltered.bairro() : local.getBairro());
            local.setDataModificacao(LocalDateTime.now(fortalezaZoneId));
            localRepository.save(local);
        }
    }

    // Método para deletar um local pelo ID
    public void deletar(Long id) throws NoSuchElementException, PersistenceException {
        localRepository.deleteById(id);
    }
}
