package br.com.attomtech.talbosch.api.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Table(name = "ordem_servico_andamento")
@Entity
public class OrdemAndamento
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;
    
    @ManyToOne
    @JoinColumn(name = "ordem_servico")
    private OrdemServico ordem;
    
    @NotNull(message = "Texto é obrigatório")
    private String texto;
    
    @JsonIgnoreProperties(value = { "login", "ultimoAcesso", "ativo", "permissoes", "auditoria" })
    @NotNull(message = "Usuário é obrigatório")
    @ManyToOne
    @JoinColumn(name = "codigo_usuario")
    private Usuario usuario;
    
    @NotNull(message = "Data do Andamento é obrigatório")
    private LocalDate data;

    public Long getCodigo( )
    {
        return codigo;
    }

    public void setCodigo( Long codigo )
    {
        this.codigo = codigo;
    }

    public OrdemServico getOrdem( )
    {
        return ordem;
    }

    public void setOrdem( OrdemServico ordem )
    {
        this.ordem = ordem;
    }

    public String getTexto( )
    {
        return texto;
    }

    public void setTexto( String texto )
    {
        this.texto = texto;
    }

    public Usuario getUsuario( )
    {
        return usuario;
    }

    public void setUsuario( Usuario usuario )
    {
        this.usuario = usuario;
    }

    public LocalDate getData( )
    {
        return data;
    }

    public void setData( LocalDate data )
    {
        this.data = data;
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
        OrdemAndamento other = (OrdemAndamento)obj;
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
