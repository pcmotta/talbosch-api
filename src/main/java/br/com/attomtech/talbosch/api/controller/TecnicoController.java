package br.com.attomtech.talbosch.api.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.attomtech.talbosch.api.controller.interfaces.NegocioController;
import br.com.attomtech.talbosch.api.model.Tecnico;
import br.com.attomtech.talbosch.api.repository.filter.TecnicoFilter;
import br.com.attomtech.talbosch.api.service.TecnicoService;
import br.com.attomtech.talbosch.api.utils.LabelValue;

@RestController
@RequestMapping("/tecnicos")
public class TecnicoController implements NegocioController<Tecnico, TecnicoFilter, Long>
{
    private static final Logger LOGGER = LoggerFactory.getLogger( TecnicoController.class );
    
    private TecnicoService service;
    
    public TecnicoController( TecnicoService service )
    {
        this.service = service;
    }
    
    @Override
    @GetMapping
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('USUARIO')")
    public ResponseEntity<Page<Tecnico>> pesquisar( TecnicoFilter filtro, Pageable pageable )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Pesquisando > {}", filtro );
        
        Page<Tecnico> pagina = service.pesquisar( filtro, pageable );
        
        return ResponseEntity.ok( pagina );
    }
    
    @Override
    @PostMapping
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('USUARIO')")
    public ResponseEntity<Tecnico> cadastrar( @RequestBody @Valid Tecnico tecnico )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Cadastrando > {}", tecnico );
        
        Tecnico tecnicoSalvo = service.cadastrar( tecnico );
        
        return ResponseEntity.status( HttpStatus.CREATED ).body( tecnicoSalvo );
    }

    @Override
    @PutMapping
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('USUARIO')")
    public ResponseEntity<Tecnico> atualizar( @Valid Tecnico tecnico )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Atualizando > {}", tecnico );
        
        Tecnico tecnicoSalvo = service.atualizar( tecnico );
        
        return ResponseEntity.ok( tecnicoSalvo );
    }

    @Override
    @GetMapping("/{codigo}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('USUARIO')")
    public ResponseEntity<Tecnico> buscarPorCodigo( Long codigo )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Buscando por Código > {}", codigo );
        
        Tecnico tecnico = service.buscarPorCodigo( codigo );
        
        return ResponseEntity.ok( tecnico );
    }

    @Override
    @DeleteMapping("/{codigo}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('USUARIO')")
    public ResponseEntity<Void> excluir( Long codigo )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Inativando > {}", codigo );
        
        service.excluir( codigo );
        
        return ResponseEntity.noContent( ).build( );
    }
    
    @GetMapping("/ativos")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<LabelValue>> buscarAtivos( )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Buscando Ativos" );
        
        List<Tecnico> tecnicos = service.buscarAtivos( );
        List<LabelValue> tecnicosAtivos = new ArrayList<LabelValue>( );
        
        tecnicos.forEach( tecnico -> tecnicosAtivos.add( new LabelValue( tecnico.getCodigo( ), tecnico.getNome( ) ) ) );
        
        return ResponseEntity.ok( tecnicosAtivos );
    }
}
