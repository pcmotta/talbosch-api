package br.com.attomtech.talbosch.api.repository.query;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.attomtech.talbosch.api.dto.pesquisa.EstoquePesquisaDTO;
import br.com.attomtech.talbosch.api.model.Estoque;
import br.com.attomtech.talbosch.api.reports.EstoqueTecnicoReport;
import br.com.attomtech.talbosch.api.repository.filter.EstoqueFilter;

public interface EstoqueRepositoryQuery
{
    Page<EstoquePesquisaDTO> pesquisar( EstoqueFilter filtro, Pageable pageable );
    List<Estoque> buscarEstoqueTecnico( EstoqueTecnicoReport filtro );
}
