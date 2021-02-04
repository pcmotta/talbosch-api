package br.com.attomtech.talbosch.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.attomtech.talbosch.api.model.Estoque;
import br.com.attomtech.talbosch.api.repository.query.EstoqueRepositoryQuery;

@Repository
public interface EstoqueRepository extends JpaRepository<Estoque, Long>, EstoqueRepositoryQuery
{
    Optional<List<Estoque>> findByOrdemServicoNumero( Long numero );
}
