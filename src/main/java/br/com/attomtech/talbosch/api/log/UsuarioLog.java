package br.com.attomtech.talbosch.api.log;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.attomtech.talbosch.api.model.Usuario;
import br.com.attomtech.talbosch.api.model.enums.AreaLog;
import br.com.attomtech.talbosch.api.model.enums.Permissao;
import br.com.attomtech.talbosch.api.service.LogAtividadesService;

@Component
public class UsuarioLog extends ModelLog<Usuario>
{
    @Autowired
    protected UsuarioLog( LogAtividadesService logService )
    {
        super( logService, AreaLog.USUARIO );
    }

    @Override
    public String log( Usuario usuarioOld, Usuario usuarioNew, boolean isAlteracao )
    {
        if( usuarioNew == null )
            usuarioNew = usuarioOld;
        
        StringBuffer log = new StringBuffer( );
        
        StringBuffer logCadastro = new StringBuffer( );
        
        logCadastro.append( logService.log( "Nome: ",       isAlteracao, usuarioOld.getNome( ),  usuarioNew.getNome( ) ) );
        logCadastro.append( logService.log( "Login: ",      isAlteracao, usuarioOld.getLogin( ), usuarioNew.getLogin( ) ) );
        logCadastro.append( logService.log( "Permissões: ", isAlteracao, formatarPermissoes( usuarioOld.getPermissoes( ) ), formatarPermissoes( usuarioNew.getPermissoes( ) ) ) );
        
        if( logCadastro.length( ) > 0 )
        {
            log.append( "----------------------------------------------------------------------\n" );
            log.append( " CADASTRO \n" );
            log.append( "----------------------------------------------------------------------\n" );
            log.append( logCadastro );
        }
        
        return log.toString( );
    }
    
    private String formatarPermissoes( List<Permissao> permissoes )
    {
        if( permissoes == null )
            return "";
        
        return permissoes.stream( ).map( p -> p.getDescricao( ) ).collect( Collectors.joining( "," ) );
    }
}
