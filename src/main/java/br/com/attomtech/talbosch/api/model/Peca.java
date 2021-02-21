package br.com.attomtech.talbosch.api.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.attomtech.talbosch.api.exception.NegocioException;
import br.com.attomtech.talbosch.api.model.abstracts.Model;
import br.com.attomtech.talbosch.api.model.enums.Aparelho;
import br.com.attomtech.talbosch.api.model.enums.Fabricante;

@Table(name = "peca")
@Entity
public class Peca extends Model implements Cloneable
{
    @Id
    private String codigo;

    @NotNull(message = "A Descrição é obrigatória")
    private String descricao;

    @Min(value = 0)
    private BigDecimal valor;

    @Min(value = 0)
    private BigDecimal valorTecnico;

    @Min(value = 0)
    @Column(name = "valor_mo")
    private BigDecimal valorMaoDeObra;

    @Enumerated(EnumType.STRING)
    private Aparelho aparelho;

    @Enumerated(EnumType.STRING)
    private Fabricante fabricante;
    private boolean    ativo = true;
    
    @ElementCollection(targetClass = String.class)
    @JoinTable(name = "modelo", joinColumns = @JoinColumn(name = "codigo_peca"))
    @Column(name = "modelo", nullable = false)
    private List<String> modelos;

    @Override
    public Peca clone( ) throws NegocioException
    {
        Peca peca = null;
        try
        {
            peca = (Peca)super.clone( );
            peca.setModelos( getModelos( ).stream( ).map( modelo -> modelo ).collect( Collectors.toList( ) ) );
        }
        catch( CloneNotSupportedException e )
        {
            throw new NegocioException( "Erro ao clonar peça" );
        }
        
        return peca;
    }
    
    public String getCodigo( )
    {
        return codigo;
    }

    public void setCodigo( String codigo )
    {
        this.codigo = codigo;
    }

    public String getDescricao( )
    {
        return descricao;
    }

    public void setDescricao( String descricao )
    {
        this.descricao = descricao;
    }

    public BigDecimal getValor( )
    {
        return valor;
    }

    public void setValor( BigDecimal valor )
    {
        this.valor = valor;
    }

    public BigDecimal getValorTecnico( )
    {
        return valorTecnico;
    }

    public void setValorTecnico( BigDecimal valorTecnico )
    {
        this.valorTecnico = valorTecnico;
    }

    public BigDecimal getValorMaoDeObra( )
    {
        return valorMaoDeObra;
    }

    public void setValorMaoDeObra( BigDecimal valorMaoDeObra )
    {
        this.valorMaoDeObra = valorMaoDeObra;
    }

    public Aparelho getAparelho( )
    {
        return aparelho;
    }

    public void setAparelho( Aparelho aparelho )
    {
        this.aparelho = aparelho;
    }

    public Fabricante getFabricante( )
    {
        return fabricante;
    }

    public void setFabricante( Fabricante fabricante )
    {
        this.fabricante = fabricante;
    }

    public boolean isAtivo( )
    {
        return ativo;
    }
    
    @JsonIgnore
    public boolean isInativo( )
    {
        return !ativo;
    }

    public void setAtivo( boolean ativo )
    {
        this.ativo = ativo;
    }

    public List<String> getModelos( )
    {
        return modelos;
    }

    public void setModelos( List<String> modelos )
    {
        this.modelos = modelos;
    }

    @Override
    public int hashCode( )
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( codigo == null ) ? 0 : codigo.hashCode( ) );
        return result;
    }

    @Override
    public boolean equals( Object obj )
    {
        if( this == obj )
            return true;
        if( obj == null )
            return false;
        if( getClass( ) != obj.getClass( ) )
            return false;
        Peca other = (Peca)obj;
        if( codigo == null )
        {
            if( other.codigo != null )
                return false;
        }
        else if( !codigo.equals( other.codigo ) )
            return false;
        return true;
    }
}
