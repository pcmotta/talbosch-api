package br.com.attomtech.talbosch.api.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NegocioServiceAuditoriaDto<T, F, DTO, ID>
{
    Page<DTO> pesquisar( F filtro, Pageable pageable );
    T cadastrar( T model, String login );
    T atualizar( T model, String login );
    void excluir( ID codigo, String login );
    T buscarPorCodigo( ID codigo );
    T salvar( T model );
}
