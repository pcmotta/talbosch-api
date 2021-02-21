package br.com.attomtech.talbosch.api.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils
{
    public static String formatarTelefone( Long numero )
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
    
    public static LocalDateTime horaAgora( )
    {
        return LocalDateTime.ofInstant( Instant.now( ), ZoneId.of( "GMT-3" ) );
    }
}
