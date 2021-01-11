package br.com.attomtech.talbosch.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.attomtech.talbosch.api.model.Peca;
import br.com.attomtech.talbosch.api.repository.query.PecaRepositoryQuery;

@Repository
public interface PecaRepository extends JpaRepository<Peca, String>, PecaRepositoryQuery
{
    long countByCodigo( String codigo );
}
