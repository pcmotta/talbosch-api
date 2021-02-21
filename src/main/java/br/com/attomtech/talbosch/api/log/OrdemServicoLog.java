package br.com.attomtech.talbosch.api.log;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import br.com.attomtech.talbosch.api.model.OrdemAndamento;
import br.com.attomtech.talbosch.api.model.OrdemAtendimento;
import br.com.attomtech.talbosch.api.model.OrdemEndereco;
import br.com.attomtech.talbosch.api.model.OrdemProduto;
import br.com.attomtech.talbosch.api.model.OrdemServico;
import br.com.attomtech.talbosch.api.model.OrdemValor;
import br.com.attomtech.talbosch.api.model.enums.AreaLog;
import br.com.attomtech.talbosch.api.service.LogAtividadesService;

@Component
public class OrdemServicoLog extends ModelLog<OrdemServico>
{
    @Autowired
    protected OrdemServicoLog( LogAtividadesService logService )
    {
        super( logService, AreaLog.ORDEM );
    }

    @Override
    public String log( OrdemServico ordemOld, OrdemServico ordemNew, boolean isAlteracao )
    {
        if( ordemNew == null )
            ordemNew = ordemOld;
        
        StringBuffer log = new StringBuffer( );
        
        StringBuffer logCadastro = new StringBuffer( );
        
        logCadastro.append( logService.log( "Número: ",  isAlteracao, ordemOld.getNumero( ),               ordemNew.getNumero( ) ) );
        logCadastro.append( logService.log( "Cliente: ", isAlteracao, ordemOld.getCliente( ).getCodigo( ), ordemNew.getCliente( ).getCodigo( ) ) );
        logCadastro.append( logService.log( "Tipo: ",    isAlteracao, ordemOld.getTipo( ).getDescricao( ), ordemNew.getTipo( ).getDescricao( ) ) );
        
        logCadastro.append( logService.log( "Data Chamada: ", isAlteracao, 
                formatarData( ordemOld.getDataChamada( ) ), formatarData( ordemNew.getDataChamada( ) ) ) );
        
        logCadastro.append( logService.log( "Data Atendimento: ", isAlteracao, 
                formatarData( ordemOld.getDataAtendimento( ) ), formatarData( ordemNew.getDataAtendimento( ) ) ) );
        
        logCadastro.append( logService.log( "Atendente: ", isAlteracao, ordemOld.getAtendente( ) != null ? ordemOld.getAtendente( ).getCodigo( ) : "",
                ordemNew.getAtendente( ) != null ? ordemNew.getAtendente( ).getCodigo( ) : "" ) );
        
        logCadastro.append( logService.log( "Observação: ", isAlteracao, ordemOld.getObservacao( ), ordemNew.getObservacao( ) ) );
        logCadastro.append( logService.log( "Baixa: ",      isAlteracao, ordemOld.getBaixa( ),      ordemNew.getBaixa( ) ) );
        
        logCadastro.append( logService.log( "Data Baixa: ", isAlteracao, 
                formatarData( ordemOld.getDataBaixa( ) ), formatarData( ordemNew.getDataBaixa( ) ) ) );
        
        logCadastro.append( logService.log( "Técnico: ", isAlteracao, ordemOld.getTecnico( ) != null ? ordemOld.getTecnico( ).getCodigo( ) : "", 
                ordemNew.getTecnico( ) != null ? ordemNew.getTecnico( ).getCodigo( ) : "" ) );
        
        logCadastro.append( logService.log( "Status: ", isAlteracao, ordemOld.getStatus( ).getDescricao( ), ordemNew.getStatus( ).getDescricao( ) ) );
        
        if( logCadastro.toString( ).trim( ).length( ) > 0 )
        {
            log.append( "----------------------------------------------------------------------\n" );
            log.append( " CADASTRO \n" );
            log.append( "----------------------------------------------------------------------\n" );
            log.append( logCadastro );
        }
        
        String logEndereco = logarEndereco( ordemOld, ordemNew, isAlteracao );
        if( logEndereco.trim( ).length( ) > 0 )
        {
            log.append( "----------------------------------------------------------------------\n" );
            log.append( " ENDEREÇO \n" );
            log.append( "----------------------------------------------------------------------\n" );
            log.append( logEndereco );
        }
        
        String logProduto = logarProduto( ordemOld, ordemNew, isAlteracao );
        if( logProduto.trim( ).length( ) > 0 )
        {
            log.append( "----------------------------------------------------------------------\n" );
            log.append( " PRODUTO \n" );
            log.append( "----------------------------------------------------------------------\n" );
            log.append( logProduto );
        }
        
        String logValor = logarValores( ordemOld, ordemNew, isAlteracao );
        if( logValor.trim( ).length( ) > 0 )
        {
            log.append( "----------------------------------------------------------------------\n" );
            log.append( " VALORES \n" );
            log.append( "----------------------------------------------------------------------\n" );
            log.append( logValor );
        }
        
        String logAtendimento = logarAtendimentos( ordemOld, ordemNew, isAlteracao );
        if( logAtendimento.trim( ).length( ) > 0 )
        {
            log.append( "----------------------------------------------------------------------\n" );
            log.append( " ATENDIMENTOS \n" );
            log.append( "----------------------------------------------------------------------\n" );
            log.append( logAtendimento );
        }
        
        String logAndamento = logarAndamentos( ordemOld, ordemNew, isAlteracao );
        if( logAndamento.trim( ).length( ) > 0 )
        {
            log.append( "----------------------------------------------------------------------\n" );
            log.append( " ANDAMENTOS \n" );
            log.append( "----------------------------------------------------------------------\n" );
            log.append( logAndamento );
        }
        
        return log.toString( );
    }
    
    private String logarAtendimentos( OrdemServico ordemOld, OrdemServico ordemNew, boolean isAlteracao )
    {
        StringBuffer log = new StringBuffer( );
        
        List<OrdemAtendimento> atendimentosOld = ordemOld.getAtendimentos( ) == null ? new ArrayList<>( ) : ordemOld.getAtendimentos( );
        List<OrdemAtendimento> atendimentoNew = isAlteracao ? ordemNew.getAtendimentos( ) : new ArrayList<>( );
        
        OrdemAtendimento atendimentoDefault = new OrdemAtendimento( );
        
        atendimentosOld.forEach( atendimento -> {
            int index = atendimento.getCodigo( ) == null ? -1 : atendimentoNew.indexOf( atendimento );
            OrdemAtendimento atendimentoNovo = index != -1 ? atendimentoNew.get( index ) : atendimentoDefault;
            
            String logAtendimento = logAtendimento( atendimento, atendimentoNovo, index != -1 );
            
            if( StringUtils.hasText( logAtendimento ) )
                log.append( String.format( "\n[%s] -> %s\n%s\n", atendimento.getCodigo( ), 
                        isAlteracao ? (index != -1 ? "Alterado" : "Excluído") : "", logAtendimento ) );
        });
        
        atendimentoNew.forEach( andamento -> {
            if( atendimentosOld.indexOf( andamento ) == -1 )
            {
                String logAtendimento = logAtendimento( andamento, atendimentoDefault, false );
                log.append( String.format( "\n[%s] -> Adicionado \n%s\n", andamento.getCodigo( ), logAtendimento ) );
            }
        });
        
        return log.toString( );
    }
    
    private String logAtendimento( OrdemAtendimento atendimento, OrdemAtendimento atendimentoNovo, boolean isAlteracao )
    {
        StringBuffer log = new StringBuffer( );
        
        log.append( logService.log( "Data do Atendimento: ", isAlteracao, formatarData( atendimento.getDataAtendimento( ) ), 
                formatarData( atendimentoNovo.getDataAtendimento( ) ) ) );
        
        log.append( logService.log( "Técnico: ", isAlteracao, atendimento.getTecnico( ) == null ? "" : atendimento.getTecnico( ).getCodigo( ), 
                atendimentoNovo.getTecnico( ) == null ? "" : atendimentoNovo.getTecnico( ).getCodigo( ) ) );
        
        log.append( logService.log( "Observações: ", isAlteracao, atendimento.getObservacoes( ), atendimentoNovo.getObservacoes( ) ) );
        
        return log.toString( );
    }

    private String logarAndamentos( OrdemServico ordemOld, OrdemServico ordemNew, boolean isAlteracao )
    {
        StringBuffer log = new StringBuffer( );
        
        List<OrdemAndamento> andamentosOld = ordemOld.getAndamentos( ) == null ? new ArrayList<>( ) : ordemOld.getAndamentos( );
        List<OrdemAndamento> andamentosNew = isAlteracao ? ordemNew.getAndamentos( ) : new ArrayList<>( );
        
        OrdemAndamento andamentoDefault = new OrdemAndamento( );
        
        andamentosOld.forEach( andamento -> {
            int indexAndamento = andamento.getCodigo( ) == null ? -1 : andamentosNew.indexOf( andamento );
            OrdemAndamento andamentoNovo = indexAndamento != -1 ? andamentosNew.get( indexAndamento ) : andamentoDefault;
            
            String logAndamento = logAndamento( andamento, andamentoNovo, indexAndamento != -1 );
            
            if( StringUtils.hasText( logAndamento ) )
                log.append( String.format( "\n[%s] -> %s\n%s\n", andamento.getCodigo( ), 
                        isAlteracao ? (indexAndamento != -1 ? "Alterado" : "Excluído") : "", logAndamento ) );
        });
        
        andamentosNew.forEach( andamento -> {
            if( andamentosOld.indexOf( andamento ) == -1 )
            {
                String logAndamento = logAndamento( andamento, andamentoDefault, false );
                log.append( String.format( "\n[%s] -> Adicionado \n%s\n", andamento.getCodigo( ), logAndamento ) );
            }
        });
        
        return log.toString( );
    }

    private String logAndamento( OrdemAndamento andamento, OrdemAndamento andamentoNovo, boolean isAlteracao )
    {
        StringBuffer log = new StringBuffer( );
        
        log.append( logService.log( "Texto: ", isAlteracao, andamento.getTexto( ), andamentoNovo.getTexto( ) ) );
        log.append( logService.log( "Usuário: ", isAlteracao,  andamento.getUsuario( ) != null ? andamento.getUsuario( ).getCodigo( ) : "",
                andamentoNovo.getUsuario( ) != null ? andamentoNovo.getUsuario( ).getCodigo( ) : "" ) );
        log.append( logService.log( "Data: ", isAlteracao, formatarDataHora( andamento.getData( ) ), 
                formatarDataHora( andamentoNovo.getData( ) ) ) );
        
        return log.toString( );
    }

    private String logarValores( OrdemServico ordemOld, OrdemServico ordemNew, boolean isAlteracao )
    {
        StringBuffer log = new StringBuffer( );
        
        List<OrdemValor> valoresOld = ordemOld.getValores( ) == null ? new ArrayList<>( ) : ordemOld.getValores( );
        List<OrdemValor> valoresNew = isAlteracao ? ordemNew.getValores( ) : new ArrayList<>( );
        
        OrdemValor valorDefault = new OrdemValor( );
        
        valoresOld.forEach( valor -> {
            int indexValor = valor.getCodigo( ) == null ? -1 : valoresNew.indexOf( valor );
            OrdemValor valorNovo = indexValor != -1 ? valoresNew.get( indexValor ) : valorDefault;
            
            String logValor = logValor( valor, valorNovo, indexValor != -1 );
            
            if( StringUtils.hasText( logValor ) )
                log.append( String.format( "\n[%s] -> %s\n%s\n", valor.getCodigo( ), 
                        isAlteracao ? (indexValor != -1 ? "Alterado" : "Excluído") : "", logValor ) );
        });
        
        valoresNew.forEach( valor -> {
            if( valoresOld.indexOf( valor ) == -1 )
            {
                String logValor = logValor( valor, valorDefault, false );
                log.append( String.format( "\n[%s] -> Adicionado \n%s\n", valor.getCodigo( ), logValor ) );
            }
        });
        
        return log.toString( );
    }
    
    private String logValor( OrdemValor valor, OrdemValor valorNovo, boolean isAlteracao )
    {
        StringBuffer log = new StringBuffer( );
        
        log.append( logService.log( "Tipo: ", isAlteracao, valor.getTipo( ) != null ? valor.getTipo( ).getDescricao( ) : "", 
                valorNovo.getTipo( ) != null ? valorNovo.getTipo( ).getDescricao( ) : "" ) );
        
        log.append( logService.log( "Valor: ", isAlteracao, formatarValor( valor.getValor( ) ), formatarValor( valorNovo.getValor( ) ) ) );
        
        return log.toString( );
    }

    private String logarProduto( OrdemServico ordemOld, OrdemServico ordemNew, boolean isAlteracao )
    {
        StringBuffer log = new StringBuffer( );
        
        OrdemProduto produtoOld = ordemOld.getProduto( );
        OrdemProduto produtoNew = isAlteracao ? ordemNew.getProduto( ) : new OrdemProduto( );
        
        log.append( logService.log( "Aparelho: ", isAlteracao, produtoOld.getAparelho( ) != null ? produtoOld.getAparelho( ).getDescricao( ) : "", 
                produtoNew.getAparelho( ) != null ? produtoNew.getAparelho( ).getDescricao( ) : "" ) );
        
        log.append( logService.log( "Fabricante: ", isAlteracao, produtoOld.getFabricante( ) != null ? produtoOld.getFabricante( ).getNome( ) : "", 
                produtoNew.getFabricante( ) != null ? produtoNew.getFabricante( ).getNome( ) : "" ) );
        
        log.append( logService.log( "Nota Fiscal: ",                isAlteracao, produtoOld.getNotaFiscal( ), produtoNew.getNotaFiscal( ) ) );
        log.append( logService.log( "Emissão da Nota Fiscal: ",     isAlteracao, formatarData( produtoOld.getEmissaoNotaFiscal( ) ), formatarData( produtoNew.getEmissaoNotaFiscal( ) ) ) );
        log.append( logService.log( "Cor: ",                        isAlteracao, produtoOld.getCor( ), produtoNew.getCor( ) ) );
        log.append( logService.log( "Modelo: ",                     isAlteracao, produtoOld.getModelo( ), produtoNew.getModelo( ) ) );
        log.append( logService.log( "Nº de Série: ",                isAlteracao, produtoOld.getFabSerie( ), produtoNew.getFabSerie( ) ) );
        log.append( logService.log( "Modelo Evaporadora: ",         isAlteracao, produtoOld.getModeloEvaporadora( ), produtoNew.getModeloEvaporadora( ) ) );
        log.append( logService.log( "Nº de Série da Evaporadora: ", isAlteracao, produtoOld.getFabSerieEvaporadora( ), produtoNew.getFabSerieEvaporadora( ) ) );
        log.append( logService.log( "Tensão: ",                     isAlteracao, produtoOld.getTensao( ), produtoNew.getTensao( ) ) );
        log.append( logService.log( "Revendedor: ",                 isAlteracao, produtoOld.getRevendedor( ), produtoNew.getRevendedor( ) ) );
        log.append( logService.log( "Defeito: ",                    isAlteracao, produtoOld.getDefeito( ), produtoNew.getDefeito( ) ) );
        
        return log.toString( );
    }

    private String logarEndereco( OrdemServico ordemOld, OrdemServico ordemNew, boolean isAlteracao )
    {
        StringBuffer log = new StringBuffer( );

        OrdemEndereco enderecoOld = ordemOld.getEndereco( );
        OrdemEndereco enderecoNew = isAlteracao ? ordemNew.getEndereco( ) : new OrdemEndereco( );
        
        log.append( logService.log( "Logradouro: ",  isAlteracao, enderecoOld.getLogradouro( ),         enderecoNew.getLogradouro( ) ) );
        log.append( logService.log( "Número: ",      isAlteracao, enderecoOld.getNumero( ),             enderecoNew.getNumero( ) ) );
        log.append( logService.log( "Complemento: ", isAlteracao, enderecoOld.getComplemento( ),        enderecoNew.getComplemento( ) ) );
        log.append( logService.log( "Bairro: ",      isAlteracao, enderecoOld.getBairro( ),             enderecoNew.getBairro( ) ) );
        log.append( logService.log( "Município: ",   isAlteracao, enderecoOld.getMunicipio( ),          enderecoNew.getMunicipio( ) ) );
        log.append( logService.log( "Estado: ",      isAlteracao, enderecoOld.getEstado( ),             enderecoNew.getEstado( ) ) );
        log.append( logService.log( "CEP: ",         isAlteracao, formatarCep( enderecoOld.getCep( ) ), formatarCep( enderecoNew.getCep( ) ) ) );
        log.append( logService.log( "Proximidade: ", isAlteracao, enderecoOld.getProximidade( ),        enderecoNew.getProximidade( ) ) );
        
        return log.toString( );
    }
}
