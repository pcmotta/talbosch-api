package br.com.attomtech.talbosch.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import br.com.attomtech.talbosch.api.dto.pesquisa.EstoquePesquisaDTO;
import br.com.attomtech.talbosch.api.model.Estoque;
import br.com.attomtech.talbosch.api.model.Model;
import br.com.attomtech.talbosch.api.reports.EstoqueTecnicoReport;
import br.com.attomtech.talbosch.api.repository.filter.EstoqueFilter;
import br.com.attomtech.talbosch.api.service.EstoqueService;
import br.com.attomtech.talbosch.api.utils.LabelValue;

@RestController
@RequestMapping("/estoque")
public class EstoqueController implements NegocioControllerAuditoria<Estoque, EstoqueFilter, Long>
{
    private static final Logger LOGGER = LoggerFactory.getLogger( EstoqueController.class );

    private EstoqueService service;

    @Autowired
    public EstoqueController( EstoqueService service )
    {
        this.service = service;
    }

    @Override
    @GetMapping
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('ESTOQUE')")
    public ResponseEntity<Page<? extends Model>> pesquisar( EstoqueFilter filtro, Pageable pageable )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Pesquisando -> {}", filtro );
        
        Page<EstoquePesquisaDTO> pagina = service.pesquisar( filtro, pageable );
        
        return ResponseEntity.ok( pagina );
    }

    @Override
    @PostMapping
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('ESTOQUE')")
    public ResponseEntity<Estoque> cadastrar( @RequestBody @Valid Estoque estoque, Authentication auth )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Cadastrando -> {}", estoque );
        
        Estoque estoqueSalvo = service.cadastrar( estoque, auth.getName( ) );
        
        return ResponseEntity.status( HttpStatus.CREATED ).body( estoqueSalvo );
    }

    @Override
    @PutMapping
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('ESTOQUE')")
    public ResponseEntity<Estoque> atualizar( @RequestBody @Valid Estoque estoque, Authentication auth )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Atualizando -> {}", estoque );
        
        Estoque estoqueSalvo = service.atualizar( estoque, auth.getName( ) );
        
        return ResponseEntity.ok( estoqueSalvo );
    }

    @Override
    @GetMapping("/{codigo}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('ESTOQUE')")
    public ResponseEntity<Estoque> buscarPorCodigo( @PathVariable Long codigo )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Buscando por Código -> {}", codigo );
        
        Estoque estoque = service.buscarPorCodigo( codigo );
        
        return ResponseEntity.ok( estoque );
    }
    
    @Override
    @DeleteMapping("/{codigo}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('ESTOQUE')")
    public ResponseEntity<Void> excluir( @PathVariable Long codigo, Authentication auth )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Excluíndo -> {}", codigo );
        
        service.excluir( codigo, auth.getName( ) );
        
        return ResponseEntity.noContent( ).build( );
    }
    
    @GetMapping("/pdf/tecnicos")
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('ESTOQUE')")
    public ResponseEntity<byte[]> relatorioEstoqueTecnico( EstoqueTecnicoReport filtro )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Gerando PDF Estoque Técnico" );
        
        byte[] pdf = service.relatorioTecnico( filtro );
        
        return ResponseEntity.ok( )
                .header( HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE )
                .body( pdf );
    }
    
    @GetMapping("/tipos")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<LabelValue[]> buscarTipos( )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Buscando tipos" );
        
        LabelValue[] labelValue = service.buscarTipos( );
        
        return ResponseEntity.ok( labelValue );
    }
    
    @GetMapping("/status")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<LabelValue>> buscarStatus( )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Buscando status" );
        
        List<LabelValue> labelValue = service.buscarStatus( );
        
        return ResponseEntity.ok( labelValue );
    }
}
