package br.com.attomtech.talbosch.api.log;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

import br.com.attomtech.talbosch.api.model.abstracts.Model;
import br.com.attomtech.talbosch.api.model.enums.AcaoLog;
import br.com.attomtech.talbosch.api.model.enums.AreaLog;
import br.com.attomtech.talbosch.api.service.LogAtividadesService;

public abstract class ModelLog<T extends Model>
{
    private static final DateTimeFormatter dateFormatter     = DateTimeFormatter.ofPattern( "dd/MM/yyyy" );
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern( "dd/MM/yyyy HH:mm" );
    private static final DecimalFormat decimalFormat = new DecimalFormat( "#,###,##0.00", new DecimalFormatSymbols( new Locale( "pt", "BR" ) ) );
    
    protected LogAtividadesService logService;
    private AreaLog area;
    
    protected ModelLog( LogAtividadesService logService, AreaLog area )
    {
        this.logService = logService;
        this.area = area;
    }
    
    protected String formatarValor( BigDecimal valor )
    {
        if( valor == null )
            return "0,00";
        
        return decimalFormat.format( valor );
    }
    
    protected String formatarCep( Long cep )
    {
        if( cep == null )
            return "";
        
        Pattern pattern = Pattern.compile( "(\\d{2})(\\d{3})(\\d{3})" );
        Matcher matcher = pattern.matcher( cep.toString( ) );
        
        if( matcher.matches( ) )
            return matcher.replaceAll( "$1.$2-$3" );
        
        return "";
    }
    
    protected String formatarData( LocalDate data )
    {
        if( data == null )
            return "";
        
        return dateFormatter.format( data );
    }
    
    protected String formatarDataHora( LocalDateTime data )
    {
        if( data == null )
            return "";
        
        return dateTimeFormatter.format( data );
    }
    
    protected String formatarTelefone( Long numero )
    {
        if( numero == null )
            return "";
        
        String telefone = numero.toString( );
        
        Pattern pattern = Pattern.compile( telefone.length( ) == 10 ? "(\\d{2})(\\d{4})(\\d{4})" : "(\\d{2})(\\d{5})(\\d{4})" );
        Matcher matcher = pattern.matcher( telefone );
        
        if( matcher.matches( ) )
            return matcher.replaceAll( "($1) $2-$3" );
        
        return "";
    }
    
    protected String formatarDocumento( Long documento, boolean isPessoaFisica )
    {
        if( documento == null || documento == 0 )
            return null;
        
        String strDocumento = documento.toString( );
        int size = isPessoaFisica ? 11 : 14;
        
        while( strDocumento.length( ) < size )
            strDocumento = "0" + strDocumento;
        
        Pattern pattern = Pattern.compile( isPessoaFisica ? "(\\d{3})(\\d{3})(\\d{3})(\\d{2})" : "(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})" );
        Matcher matcher = pattern.matcher( strDocumento );
        
        if( matcher.matches( ) )
            return matcher.replaceAll( isPessoaFisica ? "$1.$2.$3-$4" : "$1.$2.$3/$4-$5" );
        
        return "";
    }
    
    public void logar( AcaoLog acao, Object codigo, T model )
    {
        logar( acao, codigo, model, null );
    }
    
    public void logar( AcaoLog acao, Object codigo, T modelOld, T modelNew ) 
    {
        boolean isLong = codigo instanceof Long;
        
        String log = log( modelOld, modelNew, acao == AcaoLog.ALTERACAO );
        
        if( StringUtils.hasText( log ) )
            logService.salvar( acao, area, isLong ? Long.valueOf( codigo.toString( ) ) : null, !isLong ? codigo.toString( ) : null, log, 
                acao == AcaoLog.CADASTRO ? modelOld.getAuditoria( ).getIncluidoPor( ) :
                acao == AcaoLog.EXCLUSAO ? modelOld.getAuditoria( ).getAlteradoPor( ) : modelNew.getAuditoria( ).getAlteradoPor( ) );
    }
    
    
    public abstract String log( T modelOld, T modelNew, boolean isAlteracao );
}
