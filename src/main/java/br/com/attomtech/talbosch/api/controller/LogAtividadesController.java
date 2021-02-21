package br.com.attomtech.talbosch.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.attomtech.talbosch.api.model.LogAtividades;
import br.com.attomtech.talbosch.api.repository.filter.LogAtividadesFilter;
import br.com.attomtech.talbosch.api.service.LogAtividadesService;
import br.com.attomtech.talbosch.api.utils.LabelValue;

@RestController
@RequestMapping("/log")
public class LogAtividadesController
{
    private static final Logger LOGGER = LoggerFactory.getLogger( LogAtividadesController.class );
    
    private LogAtividadesService service;
    
    @Autowired
    public LogAtividadesController( LogAtividadesService service )
    {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('LOG')")
    public ResponseEntity<Page<LogAtividades>> pesquisar( LogAtividadesFilter filtro, Pageable pageable )
    {   
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Pesquisando" );
            
        Page<LogAtividades> pagina = service.pesquisar( filtro, pageable );
        
        return ResponseEntity.ok( pagina );
    }
    
    @GetMapping("/{codigo}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('LOG')")
    public ResponseEntity<LogAtividades> buscarLog( @PathVariable Long codigo )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Buscando por código > {}", codigo );
        
        LogAtividades log = service.buscar( codigo );
        
        return ResponseEntity.ok( log );
    }
    
    @PostMapping("/sair")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> logarSaida( Authentication auth )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Logout" );
        
        service.logout( auth.getName( ) );
        
        return ResponseEntity.ok( ).build( );
    }
    
    @PostMapping("/entrar")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> logarEntrada( Authentication auth )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Login" );
        
        service.login( auth.getName( ) );
        
        return ResponseEntity.ok( ).build( );
    }
    
    @GetMapping("/acoes")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<LabelValue[]> buscarAcoes( )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Buscando Ações Log" );
        
        LabelValue[] resultado = service.buscarAcoes( );
        
        return ResponseEntity.ok( resultado );
    }
    
    @GetMapping("/areas")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<LabelValue[]> buscarAreas( )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Buscando Áreas Log" );
        
        LabelValue[] resultado = service.buscarAreas( );
        
        return ResponseEntity.ok( resultado );
    }
}
