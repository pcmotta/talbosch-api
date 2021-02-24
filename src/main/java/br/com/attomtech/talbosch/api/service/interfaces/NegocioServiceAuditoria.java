package br.com.attomtech.talbosch.api.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.attomtech.talbosch.api.model.Model;

public interface NegocioServiceAuditoria<T,F,ID>
{
    Page<? extends Model> pesquisar( F filtro, Pageable pageable );
    T cadastrar( T model, String login );
    T atualizar( T model, String login );
    void excluir( ID codigo, String login );
    T buscarPorCodigo( ID codigo );
    T salvar( T model );
}
