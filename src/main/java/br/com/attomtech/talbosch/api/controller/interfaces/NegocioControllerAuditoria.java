package br.com.attomtech.talbosch.api.controller.interfaces;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.attomtech.talbosch.api.model.Model;

public interface NegocioControllerAuditoria<T,F,ID>
{
    public ResponseEntity<Page<? extends Model>> pesquisar( F filtro, Pageable pageable );
    public ResponseEntity<T> cadastrar( @RequestBody @Valid T model, Authentication auth );
    public ResponseEntity<T> atualizar( @RequestBody @Valid T model, Authentication auth );
    public ResponseEntity<T> buscarPorCodigo( @PathVariable ID codigo );
    public ResponseEntity<Void> excluir( @PathVariable ID codigo, Authentication auth );
}
