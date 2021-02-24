package br.com.attomtech.talbosch.api.repository.query;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.attomtech.talbosch.api.dto.ClienteDTO;
import br.com.attomtech.talbosch.api.dto.pesquisa.ClientePesquisaDTO;
import br.com.attomtech.talbosch.api.repository.filter.ClienteFilter;

public interface ClienteRepositoryQuery
{
    Page<ClientePesquisaDTO> pesquisar( ClienteFilter filtro, Pageable pageable );
    List<ClienteDTO> buscarTodos( );
}
