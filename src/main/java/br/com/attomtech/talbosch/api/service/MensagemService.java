package br.com.attomtech.talbosch.api.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.attomtech.talbosch.api.exception.NegocioException;
import br.com.attomtech.talbosch.api.model.Mensagem;
import br.com.attomtech.talbosch.api.model.Usuario;
import br.com.attomtech.talbosch.api.repository.MensagemRepository;
import br.com.attomtech.talbosch.api.repository.filter.MensagemFilter;
import br.com.attomtech.talbosch.api.service.interfaces.NegocioServiceAuditoria;
import br.com.attomtech.talbosch.api.utils.Utils;

@Service
public class MensagemService implements NegocioServiceAuditoria<Mensagem, MensagemFilter, Long>
{
    private static final Logger LOGGER = LoggerFactory.getLogger( MensagemService.class );
    
    private MensagemRepository repository;
    private UsuarioService     usuarioService;
    
    @Autowired
    public MensagemService( MensagemRepository repository, UsuarioService usuarioService )
    {
        this.repository = repository;
        this.usuarioService = usuarioService;
    }

    @Override
    public Page<Mensagem> pesquisar( MensagemFilter filtro, Pageable pageable )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Pesquisando > {}", filtro );
        
        Usuario usuario = usuarioService.buscarPorLogin( filtro.getLoginUsuarioOrigem( ) );
        
        filtro.setUsuarioLogado( usuario );
        
        return repository.pesquisar( filtro, pageable );
    }
    
    @Cacheable(value = "mensagensNaoLidas", key = "#login")
    public int buscarNaoLidas( String login )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Buscando não lidas > {}", login );
        
        int naoLidas = repository.buscarNaoLidas( usuarioService.buscarPorLogin( login ) );
        
        return naoLidas;
    }

    @CacheEvict(value = "mensagensNaoLidas", key = "#mensagem.usuarioDestino.login")
    @Override
    public Mensagem cadastrar( Mensagem mensagem, String login )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Pesquisando > {}", mensagem );
        
        Usuario usuario = usuarioService.buscarPorLogin( login );
        
        mensagem.setUsuarioOrigem( usuario );
        mensagem.setData( Utils.horaAgora( ) );
        validarPermissao( mensagem, usuario );
        
        return salvar( mensagem );
    }

    @Override
    public Mensagem atualizar( Mensagem mensagem, String login )
    {
        return null;
    }
    
    @CacheEvict(value = "mensagensNaoLidas", key = "#login")
    public Mensagem ler( Long codigo, String login )
    {
        Mensagem mensagem = buscarPorCodigo( codigo );
        Usuario usuario = usuarioService.buscarPorLogin( login );
        
        validarPermissao( mensagem, usuario );
        
        if( mensagem.naoLida( ) && usuario.equals( mensagem.getUsuarioDestino( ) ) )
        {
            Mensagem mensagemLida = new Mensagem( );
            BeanUtils.copyProperties( mensagem, mensagemLida );

            mensagemLida.setLido( true );
            salvar( mensagemLida );
        }
        
        return mensagem;
    }

    @Cacheable(value = "mensagem", key = "#codigo")
    @Override
    public Mensagem buscarPorCodigo( Long codigo )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Buscando por Código > {}", codigo );
        
        Optional<Mensagem> mensagem = repository.findById( codigo );
        
        return mensagem.orElseThrow( ( ) -> new NegocioException( "Mensagem não encontrada" ) );
    }

    @Caching(evict = { @CacheEvict(value = "mensagem", key = "#codigo"), @CacheEvict(value = "mensagensNaoLidas", key = "#login") })
    @Override
    public void excluir( Long codigo, String login )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Excluíndo > {}", codigo );
        
        Mensagem mensagem = buscarPorCodigo( codigo );
        Usuario usuario = usuarioService.buscarPorLogin( login );
        
        validarPermissao( mensagem, usuario );
        
        if( usuario.equals( mensagem.getUsuarioDestino( ) ) )
            mensagem.setDeletadoDestino( true );
        else if( usuario.equals( mensagem.getUsuarioOrigem( ) ) )
            mensagem.setDeletadoOrigem( true );
        
        if( mensagem.excluida( ) )
            repository.deleteById( codigo );
        else
            salvar( mensagem );
    }

    @Override
    public Mensagem salvar( Mensagem mensagem )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Salvando > {}", mensagem );
        
        return repository.save( mensagem );
    }
    
    public void notificarEstoque( Usuario destino, String mensagem )
    {
        Mensagem message = new Mensagem( );
        
        message.setMensagem( mensagem );
        message.setUsuarioDestino( destino );
        message.setDeletadoOrigem( true );
        message.setData( Utils.horaAgora( ) );
        
        salvar( message );
    }
    
    private void validarPermissao( Mensagem mensagem, Usuario usuario )
    {
        if( !usuario.equals( mensagem.getUsuarioDestino( ) ) && !usuario.equals( mensagem.getUsuarioOrigem( ) ) )
            throw new NegocioException( "Você não tem acesso a essa mensagem" );
        
        if( mensagem.getUsuarioDestino( ).equals( mensagem.getUsuarioOrigem( ) ) )
            throw new NegocioException( "O Destinatário deve ser diferente do Remetente" );
    }
}
