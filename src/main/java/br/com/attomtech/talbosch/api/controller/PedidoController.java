package br.com.attomtech.talbosch.api.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import br.com.attomtech.talbosch.api.filter.PedidoFilter;
import br.com.attomtech.talbosch.api.model.Pedido;
import br.com.attomtech.talbosch.api.service.PedidoService;
import br.com.attomtech.talbosch.api.utils.LabelValue;

@RestController
@RequestMapping("/pedidos")
public class PedidoController implements NegocioControllerAuditoria<Pedido, PedidoFilter, Long>
{
    private static final Logger LOGGER = LoggerFactory.getLogger( PedidoController.class );
    
    private PedidoService service;
    
    @Autowired
    public PedidoController( PedidoService service )
    {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('PEDIDO')")
    @Override
    public ResponseEntity<Page<Pedido>> pesquisar( PedidoFilter filtro, Pageable pageable )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Pesquisando > {}", filtro );
        
        Page<Pedido> pagina = service.pesquisar( filtro, pageable );
        
        return ResponseEntity.ok( pagina );
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('PEDIDO')")
    @Override
    public ResponseEntity<Pedido> cadastrar( @RequestBody @Valid Pedido pedido, Authentication auth )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Cadastrando > {}", pedido );
        
        Pedido pedidoSalvo = service.cadastrar( pedido, auth.getName( ) );
        
        return ResponseEntity.ok( pedidoSalvo );
    }

    @PutMapping
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('PEDIDO')")
    @Override
    public ResponseEntity<Pedido> atualizar( @RequestBody @Valid Pedido pedido, Authentication auth )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Atualizando > {}", pedido );
        
        Pedido pedidoSalvo = service.atualizar( pedido, auth.getName( ) );
        
        return ResponseEntity.ok( pedidoSalvo );
    }

    @GetMapping("/{codigo}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('PEDIDO')")
    @Override
    public ResponseEntity<Pedido> buscarPorCodigo( @PathVariable Long codigo )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Buscando por Código > {}", codigo );
        
        Pedido pedido = service.buscarPorCodigo( codigo );
        
        return ResponseEntity.ok( pedido );
    }

    @DeleteMapping("/{codigo}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('PEDIDO')")
    @Override
    public ResponseEntity<Void> excluir( @PathVariable Long codigo, Authentication auth )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Excluíndo > {}", codigo );
        
        service.excluir( codigo, auth.getName( ) );
        
        return ResponseEntity.ok( ).build( );
    }
    
    @GetMapping("/status")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<LabelValue[]> buscarStatus( )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Buscando Status de Pedido" );
        
        LabelValue[] valores = service.buscarStatus( );
        
        return ResponseEntity.ok( valores );
    }
}
