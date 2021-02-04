package br.com.attomtech.talbosch.api.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.attomtech.talbosch.api.exception.NegocioException;
import br.com.attomtech.talbosch.api.model.Cliente;
import br.com.attomtech.talbosch.api.repository.ClienteRepository;
import br.com.attomtech.talbosch.api.repository.filter.ClienteFilter;
import br.com.attomtech.talbosch.api.service.interfaces.NegocioServiceAuditoria;

@Service
public class ClienteService extends AuditoriaService<Cliente> implements NegocioServiceAuditoria<Cliente, ClienteFilter, Long>
{
    private static final Logger LOGGER = LoggerFactory.getLogger( ClienteService.class );
    
    private ClienteRepository repository;
    
    @Autowired
    public ClienteService( ClienteRepository repository )
    {
        this.repository = repository;
    }
    
    @Override
    public Page<Cliente> pesquisar( ClienteFilter filtro, Pageable pageable )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Pesquisando > {}", filtro );
        
        Page<Cliente> pagina = repository.pesquisar( filtro, pageable );
        
        return pagina;
    }
    
    public List<Cliente> buscarTodosClientes( )
    {
        List<Cliente> clientes = repository.findAll( );
        
        return clientes;
    }
    
    @Override
    public Cliente cadastrar( Cliente cliente, String login )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Cadastrando > {}", cliente );
        
        if( cliente.getCpfCnpj( ) != null )
        {
            Optional<Cliente> clienteOpt = repository.findByCpfCnpj( cliente.getCpfCnpj( ) );
            
            if( clienteOpt.isPresent( ) )
            {
                Cliente clienteSalvo = clienteOpt.get( );
                
                if( clienteSalvo.isAtivo( ) )
                    throw new NegocioException( String.format( "%s já cadastrado", cliente.isPessoaFisica( ) ? "CPF" : "CNPJ" ) );
                else
                {
                    cliente.setCodigo( clienteSalvo.getCodigo( ) );
                    cliente.setAuditoria( clienteSalvo.getAuditoria( ) );
                }
            }
        }
        
        if( cliente.getCodigo( ) != null )
            atualizarAuditoriaAlteracao( cliente, login );
        else
            atualizarAuditoriaInclusao( cliente, login );
        
        tratarAssociacoes( cliente );
        
        return salvar( cliente );
    }
    
    @Override
    public Cliente atualizar( Cliente cliente, String login )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Atualizando > {}", cliente );
        
        Cliente clienteSalvo = buscarPorCodigo( cliente.getCodigo( ) );
        
        if( clienteSalvo.isInativo( ) )
            throw new NegocioException( "Não é possível alterar cliente inativo" );
        
        atualizarAuditoriaAlteracao( clienteSalvo, login );
        
        cliente.setAuditoria( clienteSalvo.getAuditoria( ) );
        tratarAssociacoes( cliente );
        
        return salvar( cliente );
    }
    
    @Override
    public void excluir( Long codigo, String login )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Inativando > {}", codigo );
        
        Cliente cliente = buscarPorCodigo( codigo );
        
        atualizarAuditoriaAlteracao( cliente, login );
        cliente.setAtivo( false );
        
        salvar( cliente );
    }
    
    @Override
    public Cliente salvar( Cliente cliente )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Salvando > {}", cliente );
        
        return repository.save( cliente );
    }
    
    @Override
    public Cliente buscarPorCodigo( Long codigo )
    {
        Optional<Cliente> cliente = repository.findById( codigo );
        
        return cliente.orElseThrow( ( ) -> new NegocioException( "Cliente não encontrado" ) );
    }
    
    private void tratarAssociacoes( Cliente cliente )
    {
        if( cliente.getEnderecos( ) != null )
            cliente.getEnderecos( ).forEach( endereco -> endereco.setCliente( cliente ) );
        
        if( cliente.getTelefones( ) != null )
            cliente.getTelefones( ).forEach( telefone -> telefone.setCliente( cliente ) );
        
        if( cliente.getProdutos( ) != null )
            cliente.getProdutos( ).forEach( produto -> produto.setCliente( cliente ) );
    }
}
