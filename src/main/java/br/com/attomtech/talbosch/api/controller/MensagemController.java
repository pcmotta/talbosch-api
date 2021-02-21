package br.com.attomtech.talbosch.api.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.attomtech.talbosch.api.controller.interfaces.NegocioControllerAuditoria;
import br.com.attomtech.talbosch.api.model.Mensagem;
import br.com.attomtech.talbosch.api.repository.filter.MensagemFilter;
import br.com.attomtech.talbosch.api.service.MensagemService;

@RestController
@RequestMapping("/mensagens")
public class MensagemController implements NegocioControllerAuditoria<Mensagem, MensagemFilter, Long>
{
    private static final Logger LOGGER = LoggerFactory.getLogger( MensagemController.class );

    private MensagemService service;
    
    @Autowired
    public MensagemController( MensagemService service )
    {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<Mensagem>> pesquisar( MensagemFilter filtro, Pageable pageable, Authentication auth )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Pesquisando > {}", filtro );
        
        filtro.setLoginUsuarioOrigem( auth.getName( ) );
        Page<Mensagem> pagina = service.pesquisar( filtro, pageable );
        
        return ResponseEntity.ok( pagina );
    }
    
    @GetMapping("/nao-lidas")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Integer> naoLidas( Authentication auth )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Buscando não lidas > {}", auth.getName( ) );
        
        int naoLidas = service.buscarNaoLidas( auth.getName( ) );
        
        return ResponseEntity.ok( naoLidas );
    }
    
    @Override
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Mensagem> cadastrar( @RequestBody @Valid Mensagem mensagem, Authentication auth )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Cadastrando > {}", mensagem );
        
        Mensagem mensagemSalva = service.cadastrar( mensagem, auth.getName( ) );
        
        return ResponseEntity.status( HttpStatus.CREATED ).body( mensagemSalva );
    }

    @GetMapping("/{codigo}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Mensagem> ler( @PathVariable Long codigo, Authentication auth )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Lendo > {}", codigo );
        
        Mensagem mensagem = service.ler( codigo, auth.getName( ) );
        
        return ResponseEntity.ok( mensagem );
    }

    @Override
    @DeleteMapping("/{codigo}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> excluir( @PathVariable Long codigo, Authentication auth )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Excluíndo > {}", codigo );
        
        service.excluir( codigo, auth.getName( ) );
        
        return ResponseEntity.noContent( ).build( );
    }

    @Override
    public ResponseEntity<Page<Mensagem>> pesquisar( MensagemFilter filtro, Pageable pageable )
    {
        return null;
    }
    
    @Override
    public ResponseEntity<Mensagem> buscarPorCodigo( @PathVariable Long codigo )
    {
        return null;
    }
    
    @Override
    public ResponseEntity<Mensagem> atualizar( @Valid Mensagem model, Authentication auth )
    {
        return null;
    }
}
