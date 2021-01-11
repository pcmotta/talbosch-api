package br.com.attomtech.talbosch.api.controller;

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
import br.com.attomtech.talbosch.api.model.Peca;
import br.com.attomtech.talbosch.api.model.enums.Aparelho;
import br.com.attomtech.talbosch.api.model.enums.Fabricante;
import br.com.attomtech.talbosch.api.repository.filter.PecaFilter;
import br.com.attomtech.talbosch.api.service.PecaService;
import br.com.attomtech.talbosch.api.utils.LabelValue;

@RestController
@RequestMapping("/pecas")
public class PecaController implements NegocioControllerAuditoria<Peca, PecaFilter, String>
{
    private static final Logger LOGGER = LoggerFactory.getLogger( PecaController.class );
    
    @Autowired
    private PecaService service;
    
    @Override
    @GetMapping
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('PECA')")
    public ResponseEntity<Page<Peca>> pesquisar( PecaFilter filtro, Pageable pageable )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Pesquisando > {}", filtro );
        
        Page<Peca> pagina = service.pesquisar( filtro, pageable );
        
        return ResponseEntity.ok( pagina );
    }
    
    @Override
    @PostMapping
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('PECA')")
    public ResponseEntity<Peca> cadastrar( @RequestBody @Valid Peca peca, Authentication auth )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Cadastrando > {}", peca );
        
        Peca pecaSalva = service.cadastrar( peca, auth.getName( ) );
        
        return ResponseEntity.status( HttpStatus.CREATED ).body( pecaSalva );
    }
    
    @Override
    @PutMapping
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('PECA')")
    public ResponseEntity<Peca> atualizar( @RequestBody @Valid Peca peca, Authentication auth )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Atualizando > {}", peca );
        
        Peca pecaSalva = service.atualizar( peca, auth.getName( ) );
        
        return ResponseEntity.ok( pecaSalva );
    }
    
    @Override
    @GetMapping("/{codigo}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('PECA')")
    public ResponseEntity<Peca> buscarPorCodigo( @PathVariable String codigo )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Buscando por Código > {}", codigo );
        
        Peca peca = service.buscarPorCodigo( codigo );
        
        return ResponseEntity.ok( peca );
    }
    
    @Override
    @DeleteMapping("{codigo}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('PECA')")
    public ResponseEntity<Void> excluir( @PathVariable String codigo, Authentication auth )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Inativando > {}", codigo );
        
        service.excluir( codigo, auth.getName( ) );
        
        return ResponseEntity.noContent( ).build( );
    }
    
    @GetMapping("/aparelhos")
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('PECA')")
    public ResponseEntity<LabelValue[]> buscarAparelhos( )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Buscando Aparelhos" );
        
        Aparelho[] aparelhos = Aparelho.values( );
        LabelValue[] values = new LabelValue[aparelhos.length];
        
        IntStream.range( 0, aparelhos.length ).forEach( index -> 
            values[index] = new LabelValue( aparelhos[index].toString( ), aparelhos[index].getDescricao( ) ) );
        
        return ResponseEntity.ok( values );
    }
    
    @GetMapping("/fabricantes")
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('PECA')")
    public ResponseEntity<LabelValue[]> buscarFabricantes( )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Buscando Fabricantes" );
        
        Fabricante[] fabricantes = Fabricante.values( );
        LabelValue[] values = new LabelValue[fabricantes.length];
        
        IntStream.range( 0, fabricantes.length ).forEach( index -> 
            values[index] = new LabelValue( fabricantes[index].toString( ), fabricantes[index].getNome( ) ) );
        
        return ResponseEntity.ok( values );
    }
}
