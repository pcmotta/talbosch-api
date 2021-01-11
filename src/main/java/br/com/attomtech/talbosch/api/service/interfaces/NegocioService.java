package br.com.attomtech.talbosch.api.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NegocioService<T,F,ID>
{
    Page<T> pesquisar( F filtro, Pageable pageable );
    T cadastrar( T model );
    T atualizar( T model );
    void excluir( ID codigo );
    T buscarPorCodigo( ID codigo );
    T salvar( T model );
}
