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
import br.com.attomtech.talbosch.api.model.OrdemServico;
import br.com.attomtech.talbosch.api.model.enums.StatusOrdem;
import br.com.attomtech.talbosch.api.model.enums.TipoOrdem;
import br.com.attomtech.talbosch.api.model.enums.TipoOrdemValor;
import br.com.attomtech.talbosch.api.repository.filter.OrdemServicoFilter;
import br.com.attomtech.talbosch.api.service.OrdemServicoService;
import br.com.attomtech.talbosch.api.utils.LabelValue;

@RestController
@RequestMapping("/ordens")
public class OrdemServicoController implements NegocioControllerAuditoria<OrdemServico, OrdemServicoFilter, Long>
{
    private static final Logger LOGGER = LoggerFactory.getLogger( OrdemServicoController.class );
    
    private OrdemServicoService service;

    @Autowired
    public OrdemServicoController( OrdemServicoService service )
    {
        this.service = service;
    }

    @Override
    @GetMapping
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('ORDEMSERVICO')")
    public ResponseEntity<Page<OrdemServico>> pesquisar( OrdemServicoFilter filtro, Pageable pageable )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Pesquisando > {}", filtro );
        
        Page<OrdemServico> pagina = service.pesquisar( filtro, pageable );
        
        return ResponseEntity.ok( pagina );
    }

    @Override
    @PostMapping
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('ORDEMSERVICO')")
    public ResponseEntity<OrdemServico> cadastrar( @RequestBody @Valid OrdemServico ordem, Authentication auth )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Cadastrando > {}", ordem );
        
        OrdemServico ordemSalva = service.cadastrar( ordem, auth.getName( ) );
        
        return ResponseEntity.status( HttpStatus.CREATED ).body( ordemSalva );
    }

    @Override
    @PutMapping
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('ORDEMSERVICO')")
    public ResponseEntity<OrdemServico> atualizar( @RequestBody @Valid OrdemServico ordem, Authentication auth )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Atualizando > {}", ordem );
        
        OrdemServico ordemSalva = service.atualizar( ordem, auth.getName( ) );
        
        return ResponseEntity.ok( ordemSalva );
    }

    @Override
    @GetMapping("/{numero}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('ORDEMSERVICO')")
    public ResponseEntity<OrdemServico> buscarPorCodigo( @PathVariable Long numero )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Buscar por Número > {}", numero );
        
        OrdemServico ordem = service.buscarPorCodigo( numero );
        
        return ResponseEntity.ok( ordem );
    }

    @Override
    @DeleteMapping("/{numero}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('ORDEMSERVICO')")
    public ResponseEntity<Void> excluir( @PathVariable Long numero, Authentication auth )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Excluindo > {}", numero );
        
        service.excluir( numero, null );
        
        return ResponseEntity.noContent( ).build( );
    }
    
    @GetMapping("/status")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<LabelValue[]> buscarStatus( )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Buscando Status" );

        StatusOrdem[] status     = StatusOrdem.values( );
        LabelValue[]  labelValue = new LabelValue[status.length];
        
        IntStream.range( 0, status.length ).forEach( index ->
                labelValue[index] = new LabelValue( status[index], status[index].getDescricao( ) ) );
        
        return ResponseEntity.ok( labelValue );
    }
    
    @GetMapping("/tipos")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<LabelValue[]> buscarTipos( )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Buscando Tipos" );

        TipoOrdem[]  tipos      = TipoOrdem.values( );
        LabelValue[] labelValue = new LabelValue[tipos.length];
        
        IntStream.range( 0, tipos.length ).forEach( index ->
                labelValue[index] = new LabelValue( tipos[index], tipos[index].getDescricao( ) ) );
        
        return ResponseEntity.ok( labelValue );
    }
    
    @GetMapping("/tipos-valor")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<LabelValue[]> buscarTiposValor( )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Buscando Tipos Valor" );

        TipoOrdemValor[] tipos      = TipoOrdemValor.values( );
        LabelValue[]     labelValue = new LabelValue[tipos.length];
        
        IntStream.range( 0, tipos.length ).forEach( index ->
                labelValue[index] = new LabelValue( tipos[index], tipos[index].getDescricao( ) ) );
        
        return ResponseEntity.ok( labelValue );
    }
}
