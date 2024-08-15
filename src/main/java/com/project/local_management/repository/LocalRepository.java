package com.project.local_management.repository;

import com.project.local_management.entity.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LocalRepository extends JpaRepository<Local, Long> {

    @Query(value = "select l from Local l order by l.dataRegistro desc")
    List<Local> findAllOrdenado();

}
