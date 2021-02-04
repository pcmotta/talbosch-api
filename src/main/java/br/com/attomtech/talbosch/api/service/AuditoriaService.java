package br.com.attomtech.talbosch.api.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.attomtech.talbosch.api.model.Auditoria;
import br.com.attomtech.talbosch.api.model.Usuario;
import br.com.attomtech.talbosch.api.model.abstracts.Model;

public abstract class AuditoriaService<T extends Model>
{
    @Autowired
    private UsuarioService usuarioService;
    
    protected void atualizarAuditoriaInclusao( T model, String login )
    {
        Usuario usuario = usuarioService.buscarPorLogin( login );
        
        model.getAuditoria( ).setIncluidoPor( usuario );
        model.getAuditoria( ).setIncluidoEm( LocalDateTime.ofInstant( Instant.now( ), ZoneId.of( "GMT-3" ) ) );
    }
    
    protected void atualizarAuditoriaAlteracao( T model, String login )
    {
        Usuario usuario = usuarioService.buscarPorLogin( login );

        if( model.getAuditoria( ) == null )
            model.setAuditoria( new Auditoria( ) );
        
        model.getAuditoria( ).setAlteradoPor( usuario );
        model.getAuditoria( ).setAlteradoEm( LocalDateTime.ofInstant( Instant.now( ), ZoneId.of( "GMT-3" ) ) );
    }
}
