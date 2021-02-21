package br.com.attomtech.talbosch.api.log;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.attomtech.talbosch.api.model.Peca;
import br.com.attomtech.talbosch.api.model.enums.AreaLog;
import br.com.attomtech.talbosch.api.service.LogAtividadesService;

@Component
public class PecaLog extends ModelLog<Peca>
{
    @Autowired
    protected PecaLog( LogAtividadesService logService )
    {
        super( logService, AreaLog.PECAS );
    }

    @Override
    public String log( Peca pecaOld, Peca pecaNew, boolean isAlteracao )
    {
        if( pecaNew == null )
            pecaNew = pecaOld;
        
        StringBuffer log = new StringBuffer( );
        StringBuffer logCadastro = new StringBuffer( );
        
        logCadastro.append( logService.log( "Código: ",            isAlteracao, pecaOld.getCodigo( ),                          pecaNew.getCodigo( ) ) );
        logCadastro.append( logService.log( "Descrição: ",         isAlteracao, pecaOld.getDescricao( ),                       pecaNew.getDescricao( ) ) );
        logCadastro.append( logService.log( "Valor: ",             isAlteracao, formatarValor( pecaOld.getValor( ) ),          formatarValor( pecaNew.getValor( ) ) ) );
        logCadastro.append( logService.log( "Valor Técnico: ",     isAlteracao, formatarValor( pecaOld.getValorTecnico( ) ),   formatarValor( pecaNew.getValorTecnico( ) ) ) );
        logCadastro.append( logService.log( "Valor Mão de Obra: ", isAlteracao, formatarValor( pecaOld.getValorMaoDeObra( ) ), formatarValor( pecaNew.getValorMaoDeObra( ) ) ) );
        logCadastro.append( logService.log( "Aparelho: ",          isAlteracao, pecaOld.getAparelho( ).getDescricao( ),        pecaNew.getAparelho( ).getDescricao( ) ) );
        logCadastro.append( logService.log( "Fabricante: ",        isAlteracao, pecaOld.getFabricante( ).getNome( ),           pecaNew.getFabricante( ).getNome( ) ) );
        logCadastro.append( logService.log( "Ativo: ",             isAlteracao, pecaOld.isAtivo( ),                            pecaNew.isAtivo( ) ) );
        logCadastro.append( logService.log( "Modelos: ",           isAlteracao, formatarModelos( pecaOld.getModelos( ) ),      formatarModelos( pecaNew.getModelos( ) ) ) );
        
        if( logCadastro.toString( ).trim( ).length( ) > 0 )
            log.append( logCadastro );
        
        return log.toString( );
    }

    private String formatarModelos( List<String> modelos )
    {
        if( modelos == null )
            return "";
        
        return modelos.stream( ).collect( Collectors.joining( "," ) );
    }
}
