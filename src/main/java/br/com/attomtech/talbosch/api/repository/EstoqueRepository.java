package br.com.attomtech.talbosch.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.attomtech.talbosch.api.model.Estoque;
import br.com.attomtech.talbosch.api.repository.query.EstoqueRepositoryQuery;

@Repository
public interface EstoqueRepository extends JpaRepository<Estoque, Long>, EstoqueRepositoryQuery
{

}
