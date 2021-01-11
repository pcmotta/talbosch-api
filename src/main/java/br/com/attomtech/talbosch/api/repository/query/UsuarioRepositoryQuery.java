package br.com.attomtech.talbosch.api.repository.query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.attomtech.talbosch.api.model.Usuario;
import br.com.attomtech.talbosch.api.repository.filter.UsuarioFilter;

public interface UsuarioRepositoryQuery
{
    Page<Usuario> pesquisar( UsuarioFilter filtro, Pageable pageable );
}
