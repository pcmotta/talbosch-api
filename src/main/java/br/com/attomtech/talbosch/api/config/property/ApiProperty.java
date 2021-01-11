package br.com.attomtech.talbosch.api.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("api")
public class ApiProperty
{
    private boolean enableHttps;
    private String  originPermitida;

    public boolean isEnableHttps( )
    {
        return enableHttps;
    }

    public void setEnableHttps( boolean enableHttps )
    {
        this.enableHttps = enableHttps;
    }

    public String getOriginPermitida( )
    {
        return originPermitida;
    }

    public void setOriginPermitida( String originPermitida )
    {
        this.originPermitida = originPermitida;
    }
}
