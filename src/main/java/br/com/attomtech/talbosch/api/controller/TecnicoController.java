package br.com.attomtech.talbosch.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.attomtech.talbosch.api.controller.interfaces.NegocioControllerAuditoria;
import br.com.attomtech.talbosch.api.model.Model;
import br.com.attomtech.talbosch.api.model.Tecnico;
import br.com.attomtech.talbosch.api.repository.filter.TecnicoFilter;
import br.com.attomtech.talbosch.api.service.TecnicoService;
import br.com.attomtech.talbosch.api.utils.LabelValue;

@RestController
@RequestMapping("/tecnicos")
public class TecnicoController implements NegocioControllerAuditoria<Tecnico, TecnicoFilter, Long>
{
    private static final Logger LOGGER = LoggerFactory.getLogger( TecnicoController.class );
    
    private TecnicoService service;
    
    public TecnicoController( TecnicoService service )
    {
        this.service = service;
    }
    
    @Override
    @GetMapping
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('CADASTROTECNICO')")
    public ResponseEntity<Page<? extends Model>> pesquisar( TecnicoFilter filtro, Pageable pageable )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Pesquisando > {}", filtro );
        
        Page<Tecnico> pagina = service.pesquisar( filtro, pageable );
        
        return ResponseEntity.ok( pagina );
    }
    
    @Override
    @PostMapping
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('CADASTROTECNICO')")
    public ResponseEntity<Tecnico> cadastrar( @RequestBody @Valid Tecnico tecnico, Authentication auth )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Cadastrando > {}", tecnico );
        
        Tecnico tecnicoSalvo = service.cadastrar( tecnico, auth.getName( ) );
        
        return ResponseEntity.status( HttpStatus.CREATED ).body( tecnicoSalvo );
    }

    @Override
    @PutMapping
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('CADASTROTECNICO')")
    public ResponseEntity<Tecnico> atualizar( @Valid Tecnico tecnico, Authentication auth )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Atualizando > {}", tecnico );
        
        Tecnico tecnicoSalvo = service.atualizar( tecnico, auth.getName( ) );
        
        return ResponseEntity.ok( tecnicoSalvo );
    }

    @Override
    @GetMapping("/{codigo}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('CADASTROTECNICO')")
    public ResponseEntity<Tecnico> buscarPorCodigo( Long codigo )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Buscando por Código > {}", codigo );
        
        Tecnico tecnico = service.buscarPorCodigo( codigo );
        
        return ResponseEntity.ok( tecnico );
    }

    @Override
    @DeleteMapping("/{codigo}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('CADASTROTECNICO')")
    public ResponseEntity<Void> excluir( Long codigo, Authentication auth )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Inativando > {}", codigo );
        
        service.excluir( codigo, auth.getName( ) );
        
        return ResponseEntity.noContent( ).build( );
    }
    
    @GetMapping("/ativos")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<LabelValue>> buscarAtivos( )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Buscando Ativos" );
        
        List<LabelValue> tecnicosAtivos = service.buscarAtivos( );
        
        return ResponseEntity.ok( tecnicosAtivos );
    }
}
