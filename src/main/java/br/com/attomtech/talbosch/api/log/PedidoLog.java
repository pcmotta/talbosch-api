package br.com.attomtech.talbosch.api.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.attomtech.talbosch.api.model.Pedido;
import br.com.attomtech.talbosch.api.model.enums.AreaLog;
import br.com.attomtech.talbosch.api.service.LogAtividadesService;

@Component
public class PedidoLog extends ModelLog<Pedido>
{
    @Autowired
    protected PedidoLog( LogAtividadesService logService )
    {
        super( logService, AreaLog.PEDIDOS );
    }

    @Override
    public String log( Pedido pedidoOld, Pedido pedidoNew, boolean isAlteracao )
    {
        if( pedidoNew == null )
            pedidoNew = pedidoOld;
            
        StringBuffer log         = new StringBuffer( );
        StringBuffer logCadastro = new StringBuffer( );
        
        logCadastro.append( logService.log( "Pedido: ", isAlteracao, pedidoOld.getPedido( ).trim( ), pedidoNew.getPedido( ).trim( ) ) );
        
        logCadastro.append( logService.log( "Nota Fiscal: ", isAlteracao, pedidoOld.getNotaFiscal( ) == null ? "" : pedidoOld.getNotaFiscal( ).trim( ), 
                pedidoNew.getNotaFiscal( ) == null ? "" : pedidoNew.getNotaFiscal( ).trim( ) ) );
        
        logCadastro.append( logService.log( "Data do Pedido: ", isAlteracao, formatarData( pedidoOld.getData( ) ), 
                formatarData( pedidoNew.getData( ) ) ) );
        
        logCadastro.append( logService.log( "Emissão da Nota Fiscal: ", isAlteracao, formatarData( pedidoOld.getEmissaoNotaFiscal( ) ), 
                formatarData( pedidoNew.getEmissaoNotaFiscal( ) ) ) );
        
        logCadastro.append( logService.log( "Chegada da Nota Fiscal: ", isAlteracao, formatarData( pedidoOld.getChegadaNotaFiscal( ) ), 
                formatarData( pedidoNew.getChegadaNotaFiscal( ) ) ) );
        
        logCadastro.append( logService.log( "Pedido Por: ", isAlteracao, pedidoOld.getPedidoPor( ).getCodigo( ), 
                pedidoNew.getPedidoPor( ).getCodigo( ) ) );
        
        if( logCadastro.toString( ).trim( ).length( ) > 0 )
            log.append( logCadastro );
        
        return log.toString( );
    }

}
