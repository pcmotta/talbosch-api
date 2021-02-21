package br.com.attomtech.talbosch.api.jasper;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.stereotype.Component;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Component
public class JasperUtils
{
    public byte[] gerarPdf( String jasperFilePath, Object fields ) throws JRException
    {
        return gerarPdf( jasperFilePath, fields, new HashMap<String, Object>( ) );
    }
    
    public byte[] gerarPdf( String jasperFilePath, Object fields, Map<String, Object> parameters ) throws JRException
    {
        parameters.put( "REPORT_LOCALE", new Locale( "pt", "BR" ) );
        parameters.put( "LOGO", this.getClass( ).getResourceAsStream( "/img/logo.png" ) );
        parameters.put( "FUNDO", this.getClass( ).getResourceAsStream( "/img/fundo.png" ) );
        parameters.put( "WHATSAPPLOGO", this.getClass( ).getResourceAsStream( "/img/whatsapplogo.png" ) );
        
        InputStream inputStream = this.getClass( ).getResourceAsStream( String.format( "/reports/%s", jasperFilePath ) );
        JasperPrint jasperPrint = JasperFillManager.fillReport( inputStream, parameters, 
                new JRBeanCollectionDataSource( fields instanceof List ? (List<?>)fields : Arrays.asList( fields ) ) );
        
        byte[] pdf = JasperExportManager.exportReportToPdf( jasperPrint );
        
        return pdf;
    }
}
