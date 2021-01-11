package br.com.attomtech.talbosch.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.attomtech.talbosch.api.model.OrdemServico;
import br.com.attomtech.talbosch.api.repository.query.OrdemServicoRepositoryQuery;

@Repository
public interface OrdemServicoRepository extends JpaRepository<OrdemServico, Long>, OrdemServicoRepositoryQuery
{

}
