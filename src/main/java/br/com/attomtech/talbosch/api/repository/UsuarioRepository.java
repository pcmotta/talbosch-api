package br.com.attomtech.talbosch.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.attomtech.talbosch.api.model.Usuario;
import br.com.attomtech.talbosch.api.repository.query.UsuarioRepositoryQuery;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>, UsuarioRepositoryQuery
{
    Optional<Usuario> findByLoginAndAtivoTrue( String login );
}
