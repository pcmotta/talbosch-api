package br.com.attomtech.talbosch.api.service;

import java.util.Optional;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.attomtech.talbosch.api.exception.NegocioException;
import br.com.attomtech.talbosch.api.model.LogAtividades;
import br.com.attomtech.talbosch.api.model.Usuario;
import br.com.attomtech.talbosch.api.model.enums.AcaoLog;
import br.com.attomtech.talbosch.api.model.enums.AreaLog;
import br.com.attomtech.talbosch.api.repository.LogAtividadesRepository;
import br.com.attomtech.talbosch.api.repository.UsuarioRepository;
import br.com.attomtech.talbosch.api.repository.filter.LogAtividadesFilter;
import br.com.attomtech.talbosch.api.utils.LabelValue;
import br.com.attomtech.talbosch.api.utils.Utils;

@Service
public class LogAtividadesService
{
    private static final Logger LOGGER = LoggerFactory.getLogger( LogAtividadesService.class );
    
    private LogAtividadesRepository repository;
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    public LogAtividadesService( LogAtividadesRepository repository, UsuarioRepository usuarioRepository )
    {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
    }

    public Page<LogAtividades> pesquisar( LogAtividadesFilter filtro, Pageable pageable )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Pesquisando" );
        
        Page<LogAtividades> pagina = repository.pesquisar( filtro, pageable );
        
        return pagina;
    }
    
    public void salvar( AcaoLog acao, AreaLog area, Long codigoObjeto, String codigoPeca, String texto, Usuario usuario )
    {
        LogAtividades log = new LogAtividades( acao, area, codigoObjeto, codigoPeca, usuario, texto );
        
        repository.save( log );
    }
    
    @Cacheable(value = "log", key = "#codigo")
    public LogAtividades buscar( Long codigo )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Buscando pelo código > {}", codigo );
        
        Optional<LogAtividades> log = repository.findById( codigo );
        
        return log.orElseThrow( () -> new NegocioException( "Log não encontrado" ) );
    }
    
    @Cacheable(value = "areasLog")
    public LabelValue[] buscarAreas( )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Buscando Áreas Log" );
        
        AreaLog[] acoes = AreaLog.values( );
        LabelValue[] resultado = new LabelValue[acoes.length];
        
        IntStream.range( 0, acoes.length ).forEach( index -> {
            resultado[index] = new LabelValue( acoes[index].toString( ), acoes[index].getDescricao( ) );
        });
        
        return resultado;
    }
     
    @Cacheable(value = "acoesLog")
    public LabelValue[] buscarAcoes( )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Buscando Ações Log" );
        
        AcaoLog[] acoes = AcaoLog.values( );
        LabelValue[] resultado = new LabelValue[acoes.length];
        
        IntStream.range( 0, acoes.length ).forEach( index -> {
            resultado[index] = new LabelValue( acoes[index].toString( ), acoes[index].getDescricao( ) );
        });
        
        return resultado;
    }
    
    public void login( String login )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Login" );
        
        Usuario usuario = buscarUsuario( login );
        usuario.setUltimoAcesso( Utils.horaAgora( ) );
        
        usuarioRepository.save( usuario );
        salvar( AcaoLog.LOGIN, null, usuario.getCodigo( ), null, null, usuario );
    }
    
    public void logout( String login )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Logout" );
        
        Usuario usuario = buscarUsuario( login );
        
        salvar( AcaoLog.LOGOUT, null, usuario.getCodigo( ), null, null, usuario );
    }
    
    public String log( String campo, boolean isAlteracao, Object valueOld, Object valueNew )
    {
        if( !isAlteracao || camposDiferentes( valueOld, valueNew ) )
            return campo + (isAlteracao ? format( valueOld, valueNew ) : format( valueOld ) ) + "\n";

        return "";
    }
    
    private boolean camposDiferentes( Object valueOld, Object valueNew )
    {
        if( valueOld == null && valueNew == null )
            return false;
        
        return (valueOld == null && valueNew != null) || (valueOld != null && valueNew == null) ||
                (!valueOld.equals( valueNew ));
    }
    
    private String format( Object value )
    {
        if( value instanceof Boolean )
        {
            boolean b = (boolean)value;
            
            if( b ) 
                return "Sim";
            else 
                return "Não";
        }
        
        return value == null ? "" : value.toString( );
    }
    
    private String format( Object valueOld, Object valueNew )
    {
        return String.format( "%s -> %s", format( valueOld ), format( valueNew ) );
    }
    
    private Usuario buscarUsuario( String login )
    {
        return usuarioRepository.findByLoginAndAtivoTrue( login ).orElseThrow( ( ) -> new NegocioException( "Usuário não encontrado" ) );
    }
}
