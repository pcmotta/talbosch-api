package br.com.attomtech.talbosch.api.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.attomtech.talbosch.api.model.Tecnico;
import br.com.attomtech.talbosch.api.model.enums.AreaLog;
import br.com.attomtech.talbosch.api.service.LogAtividadesService;

@Component
public class TecnicoLog extends ModelLog<Tecnico>
{
    @Autowired
    protected TecnicoLog( LogAtividadesService logService )
    {
        super( logService, AreaLog.TECNICO );
    }

    @Override
    public String log( Tecnico tecnicoOld, Tecnico tecnicoNew, boolean isAlteracao )
    {
        if( tecnicoNew == null )
            tecnicoNew = tecnicoOld;
        
        StringBuffer log = new StringBuffer( );
        StringBuffer logCadastro = new StringBuffer( );
        
        logCadastro.append( logService.log( "Nome: ", isAlteracao, tecnicoOld.getNome( ), tecnicoNew.getNome( ) ) );
        logCadastro.append( logService.log( "Ativo: ", isAlteracao, tecnicoOld.isAtivo( ), tecnicoNew.isAtivo( ) ) );
        
        if( logCadastro.toString( ).trim( ).length( ) > 0 )
        {
            log.append( "----------------------------------------------------------------------\n" );
            log.append( " CADASTRO \n" );
            log.append( "----------------------------------------------------------------------\n" );
            log.append( logCadastro );
        }
        
        return log.toString( );
    }

}
