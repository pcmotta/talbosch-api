package br.com.attomtech.talbosch.api.dto.pesquisa;

import br.com.attomtech.talbosch.api.model.Model;
import br.com.attomtech.talbosch.api.model.enums.Aparelho;
import br.com.attomtech.talbosch.api.utils.LabelValue;

public class PecaPesquisaDTO implements Model
{
    private String     codigo;
    private String     descricao;
    private LabelValue aparelho;
    
    public PecaPesquisaDTO( String codigo, String descricao, Aparelho aparelho )
    {
        this.codigo = codigo;
        this.descricao = descricao;
        this.aparelho = new LabelValue( aparelho.toString( ), aparelho.getDescricao( ) );
    }

    public String getCodigo( )
    {
        return codigo;
    }

    public String getDescricao( )
    {
        return descricao;
    }

    public LabelValue getAparelho( )
    {
        return aparelho;
    }

    public void setCodigo( String codigo )
    {
        this.codigo = codigo;
    }

    public void setDescricao( String descricao )
    {
        this.descricao = descricao;
    }

    public void setAparelho( LabelValue aparelho )
    {
        this.aparelho = aparelho;
    }

}
