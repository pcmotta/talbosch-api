package br.com.attomtech.talbosch.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.attomtech.talbosch.api.model.LogAtividades;
import br.com.attomtech.talbosch.api.repository.query.LogAtividadesRepositoryQuery;

@Repository
public interface LogAtividadesRepository extends JpaRepository<LogAtividades, Long>, LogAtividadesRepositoryQuery
{

}
