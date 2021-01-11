package br.com.attomtech.talbosch.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.attomtech.talbosch.api.model.Cliente;
import br.com.attomtech.talbosch.api.repository.query.ClienteRepositoryQuery;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>, ClienteRepositoryQuery
{
    long countByCpfCnpj( Long cpfCnpj );
}
