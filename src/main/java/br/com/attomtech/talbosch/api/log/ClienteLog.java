package br.com.attomtech.talbosch.api.log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import br.com.attomtech.talbosch.api.model.Cliente;
import br.com.attomtech.talbosch.api.model.ClienteEndereco;
import br.com.attomtech.talbosch.api.model.ClienteProduto;
import br.com.attomtech.talbosch.api.model.ClienteTelefone;
import br.com.attomtech.talbosch.api.model.enums.Aparelho;
import br.com.attomtech.talbosch.api.model.enums.AreaLog;
import br.com.attomtech.talbosch.api.model.enums.Fabricante;
import br.com.attomtech.talbosch.api.service.LogAtividadesService;

@Component
public class ClienteLog extends ModelLog<Cliente>
{
    @Autowired
    protected ClienteLog( LogAtividadesService logService )
    {
        super( logService, AreaLog.CLIENTES );
    }

    @Override
    public String log( Cliente clienteOld, Cliente clienteNew, boolean isAlteracao )
    {
        if( clienteNew == null )
            clienteNew = clienteOld;
        
        StringBuffer log = new StringBuffer( );
        StringBuffer logCadastro = new StringBuffer( );
        
        logCadastro.append( logService.log( "Nome: ", isAlteracao, clienteOld.getNome( ), clienteNew.getNome( ) ) );
        logCadastro.append( logService.log( "Tipo: ", isAlteracao, clienteOld.getTipoPessoa( ).getDescricao( ), clienteNew.getTipoPessoa( ).getDescricao( ) ) );
        
        logCadastro.append( logService.log( "CPF/CNPJ: ", isAlteracao, formatarDocumento( clienteOld.getCpfCnpj( ), clienteOld.isPessoaFisica( ) ), 
                formatarDocumento( clienteNew.getCpfCnpj( ), clienteNew.isPessoaFisica( ) ) ) );
        
        logCadastro.append( logService.log( "Email: ", isAlteracao, clienteOld.getEmail( ), clienteNew.getEmail( ) ) );
        
        logCadastro.append( logService.log( "Inscrição Estadual: ",  isAlteracao, clienteOld.getInscricaoEstadual( ), clienteNew.getInscricaoEstadual( ) ) );
        logCadastro.append( logService.log( "Inscrição Municipal: ", isAlteracao, clienteOld.getInscricaoMunicipal( ), clienteNew.getInscricaoMunicipal( ) ) );
        logCadastro.append( logService.log( "Pessoa Contato: ",      isAlteracao, clienteOld.getPessoaContato( ), clienteNew.getPessoaContato( ) ) );

        logCadastro.append( logService.log( "Gênero: ",            isAlteracao, clienteOld.getGenero( ).getDescricao( ), clienteNew.getGenero( ).getDescricao( ) ) );
        logCadastro.append( logService.log( "Tipo Cliente: ",      isAlteracao, clienteOld.getTipoCliente( ).getDescricao( ), clienteNew.getTipoCliente( ).getDescricao( ) ) );
        logCadastro.append( logService.log( "Bandeira Vermelha: ", isAlteracao, clienteOld.isBandeiraVermelha( ), clienteNew.isBandeiraVermelha( ) ) );
        logCadastro.append( logService.log( "Motivo: ",            isAlteracao, clienteOld.getMotivo( ), clienteNew.getMotivo( ) ) );
        
        if( logCadastro.toString( ).trim( ).length( ) > 0 )
        {
            log.append( "----------------------------------------------------------------------\n" );
            log.append( " CADASTRO \n" );
            log.append( "----------------------------------------------------------------------\n" );
            log.append( logCadastro );
        }
        
        String logEndereco = logarEnderecos( clienteOld, clienteNew, isAlteracao );
        if( logEndereco.trim( ).length( ) > 0 )
        {
            log.append( "\n---------------------------------------------------------------------\n" );
            log.append( " ENDEREÇOS \n" );
            log.append( "---------------------------------------------------------------------" );
            log.append( logEndereco );
        }

        String logProdutos = logarProdutos( clienteOld, clienteNew, isAlteracao );
        if( logProdutos.trim( ).length( ) > 0 )
        {
            log.append( "\n---------------------------------------------------------------------\n" );
            log.append( " PRODUTOS \n" );
            log.append( "---------------------------------------------------------------------" );
            log.append( logProdutos );
        }
        
        String logTelefones = logarTelefones( clienteOld, clienteNew, isAlteracao );
        if( logTelefones.trim( ).length( ) > 0 )
        {
            log.append( "\n---------------------------------------------------------------------\n" );
            log.append( " TELEFONES \n" );
            log.append( "---------------------------------------------------------------------" );
            log.append( logTelefones );
        }
        
        return log.toString( );
    }
    
    private String logarProdutos( Cliente clienteOld, Cliente clienteNew, boolean isAlteracao )
    {
        StringBuffer log = new StringBuffer( );
        
        List<ClienteProduto> produtosOld = clienteOld.getProdutos( );
        List<ClienteProduto> produtosNew = isAlteracao ? clienteNew.getProdutos( ) : Arrays.asList( );
        
        ClienteProduto produtoDefault = new ClienteProduto( );
        produtoDefault.setAparelho( Aparelho.MAQUINA );
        produtoDefault.setFabricante( Fabricante.LG );
        
        produtosOld.forEach( produto -> {
            int indexProduto = produto.getCodigo( ) == null ? -1 : produtosNew.indexOf( produto );
            ClienteProduto telefoneNovo = indexProduto != -1 ? produtosNew.get( indexProduto ) : produtoDefault;
            
            String logProduto = logProduto( produto, telefoneNovo, indexProduto != -1 );
            
            if( StringUtils.hasText( logProduto ) )
                log.append( String.format( "\n[%s] -> %s \n%s\n", produto.getCodigo( ), 
                    isAlteracao ? (indexProduto != -1 ? "Alterado" : "Excluído") : "", logProduto ) );
        });
        
        produtosNew.forEach( produto -> {
            if( produtosOld.indexOf( produto ) == -1 )
            {
                String logProduto = logProduto( produto, produtoDefault, false );
                log.append( String.format( "\n[%s] -> Adicionado \n%s\n", produto.getCodigo( ), logProduto ) );
            }
        });
        
        return log.toString( );
    }
    
    private String logProduto( ClienteProduto produtoOld, ClienteProduto produtoNew, boolean isAlteracao )
    {
        StringBuffer logProduto = new StringBuffer( );
        
        logProduto.append( logService.log( "Aparelho: ",                     isAlteracao, produtoOld.getAparelho( ).getDescricao( ), produtoNew.getAparelho( ).getDescricao( ) ) );
        logProduto.append( logService.log( "Fabricante: ",                   isAlteracao, produtoOld.getFabricante( ).getNome( ), produtoNew.getFabricante( ).getNome( ) ) );
        logProduto.append( logService.log( "Nota Fiscal: ",                  isAlteracao, produtoOld.getNotaFiscal( ), produtoNew.getNotaFiscal( ) ) );
        logProduto.append( logService.log( "Emissão da Nota Fiscal: ",       isAlteracao, formatarData( produtoOld.getEmissaoNotaFiscal( ) ), formatarData( produtoNew.getEmissaoNotaFiscal( ) ) ) );
        logProduto.append( logService.log( "Cor: ",                          isAlteracao, produtoOld.getCor( ), produtoNew.getCor( ) ) );
        logProduto.append( logService.log( "Modelo: ",                       isAlteracao, produtoOld.getModelo( ), produtoNew.getModelo( ) ) );
        logProduto.append( logService.log( "Fabricação Série: ",             isAlteracao, produtoOld.getFabSerie( ), produtoNew.getFabSerie( ) ) );
        logProduto.append( logService.log( "Modelo Evaporadora: ",           isAlteracao, produtoOld.getModeloEvaporadora( ), produtoNew.getModeloEvaporadora( ) ) );
        logProduto.append( logService.log( "Fabricação Série Evaporadora: ", isAlteracao, produtoOld.getFabSerieEvaporadora( ), produtoNew.getFabSerieEvaporadora( ) ) );
        logProduto.append( logService.log( "Tensão: ",                       isAlteracao, produtoOld.getTensao( ), produtoNew.getTensao( ) ) );
        logProduto.append( logService.log( "Revendedor: ",                   isAlteracao, produtoOld.getRevendedor( ), produtoNew.getRevendedor( ) ) );
        
        return logProduto.toString( );
    }
    
    private String logarTelefones( Cliente clienteOld, Cliente clienteNew, boolean isAlteracao )
    {
        StringBuffer log = new StringBuffer( );
        
        List<ClienteTelefone> telefonesOld = clienteOld.getTelefones( );
        List<ClienteTelefone> telefonesNew = isAlteracao ? clienteNew.getTelefones( ) : Arrays.asList( );
        
        ClienteTelefone telefoneDefault = new ClienteTelefone( );
        telefonesOld.forEach( telefone -> {
            int indexTelefone = telefone.getCodigo( ) == null ? -1 : telefonesNew.indexOf( telefone );
            ClienteTelefone telefoneNovo = indexTelefone != -1 ? telefonesNew.get( indexTelefone ) : telefoneDefault;
            
            String logTelefone = logTelefone( telefone, telefoneNovo, indexTelefone != -1 );
            
            if( StringUtils.hasText( logTelefone ) )
                log.append( String.format( "\n[%s] -> %s \n%s\n", telefone.getCodigo( ), 
                        isAlteracao ? (indexTelefone != -1 ? "Alterado" : "Excluído") : "", logTelefone ) );
        });
        
        telefonesNew.forEach( telefone -> {
            if( telefonesOld.indexOf( telefone ) == -1 )
            {
                String logTelefone = logTelefone( telefone, telefoneDefault, false );
                log.append( String.format( "\n[%s] -> Adicionado \n%s\n", telefone.getCodigo( ), logTelefone ) );
            }
        });
        
        return log.toString( );
    }
    
    private String logTelefone( ClienteTelefone telefoneOld, ClienteTelefone telefoneNew, boolean isAlteracao )
    {
        StringBuffer logTelefone = new StringBuffer( );
        
        logTelefone.append( logService.log( "Número: ",     isAlteracao, formatarTelefone( telefoneOld.getNumero( ) ), formatarTelefone( telefoneNew.getNumero( ) ) ) );
        logTelefone.append( logService.log( "Operadora: ",  isAlteracao, telefoneOld.getOperadora( ),   telefoneNew.getOperadora( ) )   );
        logTelefone.append( logService.log( "Observação: ", isAlteracao, telefoneOld.getObservacoes( ), telefoneNew.getObservacoes( ) ) );
        
        return logTelefone.toString( );
    }
    
    private String logarEnderecos( Cliente clienteOld, Cliente clienteNew, boolean isAlteracao )
    {
        StringBuffer log = new StringBuffer( );
        
        List<ClienteEndereco> enderecosOld = clienteOld.getEnderecos( );
        List<ClienteEndereco> enderecosNew = isAlteracao ? clienteNew.getEnderecos( ) : new ArrayList<>( );
        
        ClienteEndereco enderecoDefault = new ClienteEndereco( );
        enderecosOld.forEach( endereco -> {
            int indexEnderecoNovo = endereco.getCodigo( ) == null ? -1 : enderecosNew.indexOf( endereco );
            ClienteEndereco enderecoNovo = indexEnderecoNovo != -1 ? enderecosNew.get( indexEnderecoNovo ) : enderecoDefault;
            
            String logEndereco = logEndereco( endereco, enderecoNovo, indexEnderecoNovo != -1 );
            
            if( StringUtils.hasText( logEndereco ) )
                log.append( String.format( "\n[%s] -> %s \n%s\n", endereco.getCodigo( ), 
                        (isAlteracao ? (indexEnderecoNovo != -1 ? "Alterado" : "Excluído") : ""), logEndereco ) );
         });
        
        enderecosNew.forEach( endereco -> {
            if( enderecosOld.indexOf( endereco ) == -1 )
            {
                String logEndereco = logEndereco( endereco, enderecoDefault, false );
                log.append( String.format( "\n[%s] -> Adicionado \n%s\n", endereco.getCodigo( ), logEndereco ) );
            }
        });
        
        return log.toString( );
    }
    
    private String logEndereco( ClienteEndereco enderecoOld, ClienteEndereco enderecoNew, boolean isAlteracao )
    {
        StringBuffer logEndereco = new StringBuffer( );

        logEndereco.append( logService.log( "Logradouro: ",  isAlteracao, enderecoOld.getLogradouro( ), enderecoNew.getLogradouro( ) ) );
        logEndereco.append( logService.log( "Número: ",      isAlteracao, enderecoOld.getNumero( ), enderecoNew.getNumero( ) ) );
        logEndereco.append( logService.log( "Complemento: ", isAlteracao, enderecoOld.getComplemento( ), enderecoNew.getComplemento( ) ) );
        logEndereco.append( logService.log( "Bairro: ",      isAlteracao, enderecoOld.getBairro( ), enderecoNew.getBairro( ) ) );
        logEndereco.append( logService.log( "Município: ",   isAlteracao, enderecoOld.getMunicipio( ), enderecoNew.getMunicipio( ) ) );
        logEndereco.append( logService.log( "Estado: ",      isAlteracao, enderecoOld.getEstado( ), enderecoNew.getEstado( ) ) );
        logEndereco.append( logService.log( "CEP: ",         isAlteracao, formatarCep( enderecoOld.getCep( ) ), formatarCep( enderecoNew.getCep( ) ) ) );
        logEndereco.append( logService.log( "Proximidade: ", isAlteracao, enderecoOld.getProximidade( ), enderecoNew.getProximidade( ) ) );
        
        return logEndereco.toString( );
    }
}
