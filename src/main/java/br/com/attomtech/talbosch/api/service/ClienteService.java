package br.com.attomtech.talbosch.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.attomtech.talbosch.api.dto.ClienteDTO;
import br.com.attomtech.talbosch.api.exception.NegocioException;
import br.com.attomtech.talbosch.api.log.ClienteLog;
import br.com.attomtech.talbosch.api.model.Cliente;
import br.com.attomtech.talbosch.api.model.enums.AcaoLog;
import br.com.attomtech.talbosch.api.model.enums.Genero;
import br.com.attomtech.talbosch.api.model.enums.TipoCliente;
import br.com.attomtech.talbosch.api.model.enums.TipoPessoa;
import br.com.attomtech.talbosch.api.repository.ClienteRepository;
import br.com.attomtech.talbosch.api.repository.filter.ClienteFilter;
import br.com.attomtech.talbosch.api.service.interfaces.NegocioServiceAuditoria;
import br.com.attomtech.talbosch.api.utils.LabelValue;

@Service
public class ClienteService extends AuditoriaService<Cliente> implements NegocioServiceAuditoria<Cliente, ClienteFilter, Long>
{
    private static final Logger LOGGER = LoggerFactory.getLogger( ClienteService.class );
    
    private ClienteRepository repository;
    private ClienteLog log;
    private OrdemServicoService ordemService;
    
    @Autowired
    public ClienteService( ClienteRepository repository, ClienteLog log, OrdemServicoService ordemService )
    {
        this.repository = repository;
        this.log = log;
        this.ordemService = ordemService;
    }
    
    @Override
    public Page<Cliente> pesquisar( ClienteFilter filtro, Pageable pageable )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Pesquisando > {}", filtro );
        
        Page<Cliente> pagina = repository.pesquisar( filtro, pageable );
        
        return pagina;
    }
    
    @Cacheable(value = "clientesTodos")
    public List<ClienteDTO> buscarTodosClientes( )
    {
        List<Cliente> clientes = repository.findAll( );
        List<ClienteDTO> resultado = new ArrayList<ClienteDTO>( );
        
        clientes.forEach( cliente -> resultado.add( new ClienteDTO( cliente ) ) );
        
        return resultado;
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
        
        cliente = salvar( cliente );
        log.logar( AcaoLog.CADASTRO, cliente.getCodigo( ), cliente );
        
        return cliente;
    }
    
    @Caching(evict = { @CacheEvict(value = "cliente", key = "#cliente.codigo"), @CacheEvict(value = "clientesTodos", allEntries = true) })
    @Override
    public Cliente atualizar( Cliente cliente, String login )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Atualizando > {}", cliente );
        
        Cliente clienteSalvo = buscarPorCodigo( cliente.getCodigo( ) ).clone( );
        
        if( clienteSalvo.isInativo( ) )
            throw new NegocioException( "Não é possível alterar cliente inativo" );
        
        atualizarAuditoriaAlteracao( clienteSalvo, login );
        
        cliente.setAuditoria( clienteSalvo.getAuditoria( ) );
        tratarAssociacoes( cliente );
        
        cliente = salvar( cliente );
        log.logar( AcaoLog.ALTERACAO, clienteSalvo.getCodigo( ), clienteSalvo, cliente );
        
        return cliente;
    }
    
    @Caching(evict = { @CacheEvict(value = "cliente", key = "#codigo"), @CacheEvict(value = "clientesTodos", allEntries = true) })
    @Override
    public void excluir( Long codigo, String login )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Inativando > {}", codigo );
        
        Cliente cliente = buscarPorCodigo( codigo );
        
        atualizarAuditoriaAlteracao( cliente, login );
        cliente.setAtivo( false );
        
        salvar( cliente );
        log.logar( AcaoLog.EXCLUSAO, codigo, cliente );
    }
    
    @Override
    public Cliente salvar( Cliente cliente )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Salvando > {}", cliente );
        
        return repository.save( cliente );
    }
    
    @Cacheable(value = "cliente", key = "#codigo")
    @Override
    public Cliente buscarPorCodigo( Long codigo )
    {
        Optional<Cliente> cliente = repository.findById( codigo );
        
        Cliente clienteSalvo = cliente.orElseThrow( ( ) -> new NegocioException( "Cliente não encontrado" ) );
        clienteSalvo.setOrdensServico( ordemService.buscarPorCliente( clienteSalvo ) );
        
        return clienteSalvo;
    }
    
    @Cacheable(value = "tiposPessoa")
    public LabelValue[] buscarTiposPessoa( )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Buscando Tipos Pessoa" );
        
        TipoPessoa[] tipos = TipoPessoa.values( );
        LabelValue[] values = new LabelValue[tipos.length];
        
        IntStream.range( 0, tipos.length ).forEach( index ->
            values[index] = new LabelValue( tipos[index].toString( ), tipos[index].getDescricao( ) ) );
        
        return values;
    }
    
    @Cacheable(value = "tiposCliente")
    public LabelValue[] buscarTiposCliente( )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Buscando Tipos Cliente" );
        
        TipoCliente[] tipos = TipoCliente.values( );
        LabelValue[] values = new LabelValue[tipos.length];
        
        IntStream.range( 0, tipos.length ).forEach( index ->
            values[index] = new LabelValue( tipos[index].toString( ), tipos[index].getDescricao( ) ) );
        
        return values;
    }
    
    @Cacheable(value = "generos")
    public LabelValue[] buscarGeneros( )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Buscando Gêneros" );
        
        Genero[] generos = Genero.values( );
        LabelValue[] values = new LabelValue[generos.length];
        
        IntStream.range( 0, generos.length ).forEach( index ->
            values[index] = new LabelValue( generos[index].toString( ), generos[index].getDescricao( ) ) );
        
        return values;
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
