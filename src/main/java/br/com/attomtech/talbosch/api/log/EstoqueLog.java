package br.com.attomtech.talbosch.api.log;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import br.com.attomtech.talbosch.api.model.Estoque;
import br.com.attomtech.talbosch.api.model.EstoqueObservacao;
import br.com.attomtech.talbosch.api.model.EstoqueTelefone;
import br.com.attomtech.talbosch.api.model.enums.AreaLog;
import br.com.attomtech.talbosch.api.service.LogAtividadesService;

@Component
public class EstoqueLog extends ModelLog<Estoque>
{
    @Autowired
    protected EstoqueLog( LogAtividadesService logService )
    {
        super( logService, AreaLog.ESTOQUE );
    }

    @Override
    public String log( Estoque estoqueOld, Estoque estoqueNew, boolean isAlteracao )
    {
        if( estoqueNew == null )
            estoqueNew = estoqueOld;
        
        StringBuffer log = new StringBuffer( );
        StringBuffer logCadastro = new StringBuffer( );
        
        logCadastro.append( logService.log( "Peça: ", isAlteracao, estoqueOld.getPeca( ).getCodigo( ), estoqueNew.getPeca( ).getCodigo( ) ) );
        
        logCadastro.append( logService.log( "Cliente: ", isAlteracao, estoqueOld.getCliente( ) != null ? estoqueOld.getCliente( ).getCodigo( ) : "", 
                estoqueNew.getCliente( ) != null ? estoqueNew.getCliente( ).getCodigo( ) : "" ) );
        
        logCadastro.append( logService.log( "Ordem de Serviço: ", isAlteracao, estoqueOld.getOrdemServico( ) != null ? estoqueOld.getOrdemServico( ).getNumero( ) : "", 
                estoqueNew.getOrdemServico( ) != null ? estoqueNew.getOrdemServico( ).getNumero( ) : "" ) );
        
        logCadastro.append( logService.log( "RNN: ", isAlteracao, estoqueOld.getRnn( ) == null ? "" : estoqueOld.getRnn( ).trim( ), 
                estoqueNew.getRnn( ) == null ? "" : estoqueNew.getRnn( ).trim( ) ) );
        
        logCadastro.append( logService.log( "Pedido: ", isAlteracao, estoqueOld.getPedido( ) == null ? "" : estoqueOld.getPedido( ).getPedido( ), 
                estoqueNew.getPedido( ) == null ? "" : estoqueNew.getPedido( ).getPedido( ) ) );
        
        logCadastro.append( logService.log( "Técnico: ", isAlteracao, estoqueOld.getTecnico( ) != null ? estoqueOld.getTecnico( ).getCodigo( ) : "", 
                estoqueNew.getTecnico( ) != null ? estoqueNew.getTecnico( ).getCodigo( ) : "" ) );
        
        logCadastro.append( logService.log( "Modelo: ", isAlteracao, estoqueOld.getModelo( ) == null ? "" : estoqueOld.getModelo( ).trim( ), 
                estoqueNew.getModelo( ) == null ? "" : estoqueNew.getModelo( ).trim( ) ) );
        
        logCadastro.append( logService.log( "Valor: ", isAlteracao, formatarValor( estoqueOld.getValor( ) ), formatarValor( estoqueNew.getValor( ) ) ) );
        
        logCadastro.append( logService.log( "Tipo: ", isAlteracao, estoqueOld.getTipo( ) != null ? estoqueOld.getTipo( ).getDescricao( ) : "", 
                estoqueNew.getTipo( ) != null ? estoqueNew.getTipo( ).getDescricao( ) : "" ) );
        
        logCadastro.append( logService.log( "Status: ", isAlteracao, estoqueOld.getStatus( ) != null ? estoqueOld.getStatus( ).getDescricao( ) : "", 
                estoqueNew.getStatus( ) != null ? estoqueNew.getStatus( ).getDescricao( ) : "" ) );
        
        logCadastro.append( logService.log( "Agendado Por: ", isAlteracao, estoqueOld.getAgendadoPor( ) != null ? estoqueOld.getAgendadoPor( ).getCodigo( ) : "", 
                estoqueNew.getAgendadoPor( ) != null ? estoqueNew.getAgendadoPor( ).getCodigo( ) : "" ) );
        
        logCadastro.append( logService.log( "Agendado Para: ", isAlteracao, formatarData( estoqueOld.getAgendadoPara( ) ), 
                formatarData( estoqueNew.getAgendadoPara( ) ) ) );

        if( logCadastro.toString( ).trim( ).length( ) > 0 )
        {
            log.append( "----------------------------------------------------------------------\n" );
            log.append( " CADASTRO \n" );
            log.append( "----------------------------------------------------------------------\n" );
            log.append( logCadastro );
        }
        
        String logObservacao = logarObservacoes( estoqueOld.getObservacoes( ), estoqueNew.getObservacoes( ), isAlteracao );
        if( logObservacao.toString( ).trim( ).length( ) > 0 )
        {
            log.append( "----------------------------------------------------------------------\n" );
            log.append( " OBSERVAÇÕES \n" );
            log.append( "----------------------------------------------------------------------\n" );
            log.append( logObservacao );
        }
        
        String logTelefone = logarTelefones( estoqueOld.getTelefones( ), estoqueNew.getTelefones( ), isAlteracao );
        if( logTelefone.toString( ).trim( ).length( ) > 0 )
        {
            log.append( "----------------------------------------------------------------------\n" );
            log.append( " TELEFONES \n" );
            log.append( "----------------------------------------------------------------------\n" );
            log.append( logTelefone );
        }
        
        return log.toString( );
    }

    private String logarTelefones( List<EstoqueTelefone> telefonesOld, List<EstoqueTelefone> telefonesNew, boolean isAlteracao )
    {
        StringBuffer log = new StringBuffer( );
        
        if( !isAlteracao )
            telefonesNew = new ArrayList<>( );
        
        List<EstoqueTelefone> old = telefonesOld;
        List<EstoqueTelefone> neww = telefonesNew;
        
        EstoqueTelefone telefoneDefault = new EstoqueTelefone( );
        old.forEach( telefone -> {
            int index = neww.indexOf( telefone );
            EstoqueTelefone telefoneNovo = index != -1 ? neww.get( index ) : telefoneDefault;
            
            String logTelefone = logTelefone( telefone, telefoneNovo, index != -1 );
            if( StringUtils.hasText( logTelefone ) )
                log.append( String.format( "\n[%s] %s \n%s\n", telefone.getCodigo( ), 
                        (isAlteracao ? (index != -1 ? "-> Alterado" : "-> Excluído") : ""), logTelefone ) );
        });
        
        neww.forEach( telefone -> {
            if( old.indexOf( telefone ) == -1 )
            {
                String logTelefone = logTelefone( telefone, telefoneDefault, false );
                if( StringUtils.hasText( logTelefone ) )
                    log.append( String.format( "\n[%s] -> Adicionado \n%s\n", telefone.getCodigo( ), logTelefone ) );
            }
        });
        
        return log.toString( );
    }

    private String logTelefone( EstoqueTelefone telefone, EstoqueTelefone telefoneNovo, boolean isAlteracao )
    {
        StringBuffer log = new StringBuffer( );
        
        log.append( logService.log( "Número: ", isAlteracao, telefone.getNumero( ), telefoneNovo.getNumero( ) ) );
        log.append( logService.log( "Operadora: ", isAlteracao, telefone.getOperadora( ), telefoneNovo.getOperadora( ) ) );
        
        return log.toString( );
    }

    private String logarObservacoes( List<EstoqueObservacao> observacoesOld, List<EstoqueObservacao> observacoesNew, boolean isAlteracao )
    {
        StringBuffer log = new StringBuffer( );
        
        if( !isAlteracao )
            observacoesNew = new ArrayList<EstoqueObservacao>( );
        
        List<EstoqueObservacao> old = observacoesOld;
        List<EstoqueObservacao> neww = observacoesNew;
        
        EstoqueObservacao obsDefault = new EstoqueObservacao( );
        old.forEach( obs -> {
            int index = obs.getCodigo( ) == null ? -1 : neww.indexOf( obs );
            EstoqueObservacao obsNovo = index != -1 ? neww.get( index ) : obsDefault;
            
            String logEndereco = logEndereco( obs, obsNovo, index != -1 );
            if( StringUtils.hasText( logEndereco ) )
                log.append( String.format( "\n[%s] %s \n%s\n", obs.getCodigo( ), 
                        (isAlteracao ? (index != -1 ? "-> Alterado" : "-> Excluído") : ""), logEndereco ) );
        });
        
        neww.forEach( obs -> {
            if( old.indexOf( obs ) == -1 )
            {
                String logEndereco = logEndereco( obs, obsDefault, false );
                if( StringUtils.hasText( logEndereco ) )
                    log.append( String.format( "\n[%s] -> Adicionado \n%s\n", obs.getCodigo( ), logEndereco ) );
            }
        });
        
        return log.toString( );
    }

    private String logEndereco( EstoqueObservacao old, EstoqueObservacao neww, boolean isAlteracao )
    {
        StringBuffer log = new StringBuffer( );
        
        log.append( logService.log( "Texto: ", isAlteracao, old.getTexto( ), neww.getTexto( ) ) );
        
        log.append( logService.log( "Usuário: ", isAlteracao, old.getUsuario( ) != null ? old.getUsuario( ).getCodigo( ) : "", 
                neww.getUsuario( ) != null ? neww.getUsuario( ).getCodigo( ) : "" ) );
        
        log.append( logService.log( "Data: ", isAlteracao, formatarDataHora( old.getData( ) ), formatarDataHora( neww.getData( ) ) ) );
        
        return log.toString( );
    }
}
