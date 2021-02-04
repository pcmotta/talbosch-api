package br.com.attomtech.talbosch.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.attomtech.talbosch.api.controller.interfaces.NegocioControllerAuditoria;
import br.com.attomtech.talbosch.api.dto.ClienteDTO;
import br.com.attomtech.talbosch.api.model.Cliente;
import br.com.attomtech.talbosch.api.model.ClienteEndereco;
import br.com.attomtech.talbosch.api.model.ClienteProduto;
import br.com.attomtech.talbosch.api.model.enums.Genero;
import br.com.attomtech.talbosch.api.model.enums.TipoCliente;
import br.com.attomtech.talbosch.api.model.enums.TipoPessoa;
import br.com.attomtech.talbosch.api.repository.filter.ClienteFilter;
import br.com.attomtech.talbosch.api.service.ClienteService;
import br.com.attomtech.talbosch.api.utils.LabelValue;

@RestController
@RequestMapping("/clientes")
public class ClienteController implements NegocioControllerAuditoria<Cliente, ClienteFilter, Long>
{
    private static final Logger LOGGER = LoggerFactory.getLogger( ClienteController.class );
    
    @Autowired
    private ClienteService service;
    
    @Override
    @GetMapping
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('CLIENTES')")
    public ResponseEntity<Page<Cliente>> pesquisar( ClienteFilter filtro, Pageable pageable )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Pesquisando > {}", filtro );
        
        Page<Cliente> pagina = service.pesquisar( filtro, pageable );
        
        return ResponseEntity.ok( pagina );
    }
    
    @Override
    @PostMapping
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('CLIENTES')")
    public ResponseEntity<Cliente> cadastrar( @RequestBody @Valid Cliente cliente, Authentication auth )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Cadastrando > {}", cliente );
        
        Cliente clienteSalvo = service.cadastrar( cliente, auth.getName( ) );
        
        return ResponseEntity.status( HttpStatus.CREATED ).body( clienteSalvo );
    }
    
    @Override
    @PutMapping
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('CLIENTES')")
    public ResponseEntity<Cliente> atualizar( @RequestBody @Valid Cliente cliente, Authentication auth )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Atualizando > {}", cliente );
        
        Cliente clienteSalvo = service.atualizar( cliente, auth.getName( ) );
        
        return ResponseEntity.ok( clienteSalvo );
    }
    
    @Override
    @DeleteMapping("/{codigo}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('CLIENTES')")
    public ResponseEntity<Void> excluir( @PathVariable Long codigo, Authentication auth )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Inativando > {}", codigo );

        service.excluir( codigo, auth.getName( ) );
        
        return ResponseEntity.noContent( ).build( );
    }
    
    @GetMapping("/{codigo}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('CLIENTES')")
    public ResponseEntity<Cliente> buscarPorCodigo( @PathVariable Long codigo )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Buscando por Código > {}", codigo );

        Cliente cliente = service.buscarPorCodigo( codigo );
        
        return ResponseEntity.ok( cliente );
    }
    
    @GetMapping("/{codigo}/enderecos")
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('ORDEMSERVICO')")
    public ResponseEntity<List<ClienteEndereco>> buscarEnderecos( @PathVariable Long codigo )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Buscando Endereços por Código > {}", codigo );

        Cliente cliente = service.buscarPorCodigo( codigo );
        
        return ResponseEntity.ok( cliente.getEnderecos( ) );
    }
    
    @GetMapping("/{codigo}/produtos")
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('ORDEMSERVICO')")
    public ResponseEntity<List<ClienteProduto>> buscarProdutos( @PathVariable Long codigo )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Buscando Endereços por Código > {}", codigo );

        Cliente cliente = service.buscarPorCodigo( codigo );
        
        return ResponseEntity.ok( cliente.getProdutos( ) );
    }
    
    @GetMapping("/tipos-pessoa")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<LabelValue[]> buscarTiposPessoa( )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Buscando Tipos Pessoa" );
        
        TipoPessoa[] tipos = TipoPessoa.values( );
        LabelValue[] values = new LabelValue[tipos.length];
        
        IntStream.range( 0, tipos.length ).forEach( index ->
            values[index] = new LabelValue( tipos[index].toString( ), tipos[index].getDescricao( ) ) );
        
        return ResponseEntity.ok( values );
    }
    
    @GetMapping("/tipos-cliente")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<LabelValue[]> buscarTiposCliente( )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Buscando Tipos Cliente" );
        
        TipoCliente[] tipos = TipoCliente.values( );
        LabelValue[] values = new LabelValue[tipos.length];
        
        IntStream.range( 0, tipos.length ).forEach( index ->
            values[index] = new LabelValue( tipos[index].toString( ), tipos[index].getDescricao( ) ) );
        
        return ResponseEntity.ok( values );
    }
    
    @GetMapping("/generos")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<LabelValue[]> buscarGeneros( )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Buscando Gêneros" );
        
        Genero[] generos = Genero.values( );
        LabelValue[] values = new LabelValue[generos.length];
        
        IntStream.range( 0, generos.length ).forEach( index ->
            values[index] = new LabelValue( generos[index].toString( ), generos[index].getDescricao( ) ) );
        
        return ResponseEntity.ok( values );
    }
    
    @GetMapping("/todos")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ClienteDTO>> buscarClientes( )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Buscando Clientes" );
        
        List<Cliente> clientes = service.buscarTodosClientes( );
        List<ClienteDTO> resultado = new ArrayList<ClienteDTO>( );
        
        clientes.forEach( cliente -> resultado.add( new ClienteDTO( cliente ) ) );
        
        return ResponseEntity.ok( resultado );
    }
}
