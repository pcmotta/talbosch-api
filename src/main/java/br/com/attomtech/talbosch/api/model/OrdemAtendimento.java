package br.com.attomtech.talbosch.api.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.attomtech.talbosch.api.exception.NegocioException;

@Entity
@Table(name = "ordem_servico_atendimento")
public class OrdemAtendimento implements Cloneable
{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @ManyToOne
    @JoinColumn(name = "ordem_servico")
    private OrdemServico ordem;

    @Column(name = "data_atendimento")
    @NotNull(message = "Data do Atendimento é obrigatório")
    private LocalDate dataAtendimento;
    private String    observacoes;
    
    @JsonIgnoreProperties(value = { "ativo" })
    @ManyToOne
    @JoinColumn(name = "codigo_tecnico")
    private Tecnico tecnico;

    public Long getCodigo( )
    {
        return codigo;
    }

    public OrdemServico getOrdem( )
    {
        return ordem;
    }

    public LocalDate getDataAtendimento( )
    {
        return dataAtendimento;
    }

    public String getObservacoes( )
    {
        return observacoes;
    }

    public void setCodigo( Long codigo )
    {
        this.codigo = codigo;
    }

    public void setOrdem( OrdemServico ordem )
    {
        this.ordem = ordem;
    }

    public void setDataAtendimento( LocalDate dataAtendimento )
    {
        this.dataAtendimento = dataAtendimento;
    }

    public void setObservacoes( String observacoes )
    {
        this.observacoes = observacoes;
    }

    public Tecnico getTecnico( )
    {
        return tecnico;
    }

    public void setTecnico( Tecnico tecnico )
    {
        this.tecnico = tecnico;
    }
    
    @Override
    public OrdemAtendimento clone( ) throws NegocioException
    {
        OrdemAtendimento atendimento = null;
        try
        {
            atendimento = (OrdemAtendimento)super.clone( );
        }
        catch( CloneNotSupportedException e )
        {
            throw new NegocioException( "Erro ao clonar atendimento de ordem de serviço", e );
        }
        
        return atendimento;
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
        OrdemAtendimento other = (OrdemAtendimento)obj;
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
