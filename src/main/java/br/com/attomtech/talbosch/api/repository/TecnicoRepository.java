package br.com.attomtech.talbosch.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.attomtech.talbosch.api.model.Tecnico;
import br.com.attomtech.talbosch.api.repository.query.TecnicoRepositoryQuery;

@Repository
public interface TecnicoRepository extends JpaRepository<Tecnico, Long>, TecnicoRepositoryQuery
{
    List<Tecnico> findByAtivoTrue( );
}
