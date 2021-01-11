package br.com.attomtech.talbosch.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.attomtech.talbosch.api.model.Mensagem;
import br.com.attomtech.talbosch.api.repository.query.MensagemRepositoryQuery;

@Repository
public interface MensagemRepository extends JpaRepository<Mensagem, Long>, MensagemRepositoryQuery
{

}
