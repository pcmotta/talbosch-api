package br.com.attomtech.talbosch.api.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.attomtech.talbosch.api.service.DashboardService;
import br.com.attomtech.talbosch.api.utils.LabelValue;

@RestController
@RequestMapping("/dashboard")
public class Dashboard
{
    private static final Logger LOGGER = LoggerFactory.getLogger( Dashboard.class );
    
    private DashboardService service;
    
    @Autowired
    public Dashboard( DashboardService service )
    {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<LabelValue>> buscarInformacoes( )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Buscando informações para o Dashboard" );
        
        List<LabelValue> informacoes = service.buscarInformacoes( );
        
        return ResponseEntity.ok( informacoes );
    }
}
