package br.com.attomtech.talbosch.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.attomtech.talbosch.api.model.Cliente;
import br.com.attomtech.talbosch.api.repository.query.ClienteRepositoryQuery;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>, ClienteRepositoryQuery
{
    long countByCpfCnpj( Long cpfCnpj );
    List<Cliente> findByAtivoTrue( );
    Optional<Cliente> findByCpfCnpj( Long cpfCpnj );
}
