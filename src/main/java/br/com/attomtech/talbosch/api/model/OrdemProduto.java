package br.com.attomtech.talbosch.api.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.attomtech.talbosch.api.exception.NegocioException;
import br.com.attomtech.talbosch.api.model.abstracts.Produto;

@Table(name = "ordem_servico_produto")
@Entity
public class OrdemProduto extends Produto implements Cloneable, Serializable
{
    private static final long serialVersionUID = 1L;
    
    @Id
    @OneToOne
    @JoinColumn(name = "ordem_servico")
    private OrdemServico ordem;
    private String     defeito;
    
    @Override
    public OrdemProduto clone( ) throws NegocioException
    {
        OrdemProduto produto = null;
        try
        {
            produto = (OrdemProduto)super.clone( );
        }
        catch( CloneNotSupportedException e )
        {
            throw new NegocioException( "Erro ao clonar produto de ordem de serviço" );
        }
        
        return produto;
    }

    public OrdemServico getOrdem( )
    {
        return ordem;
    }

    public void setOrdem( OrdemServico ordem )
    {
        this.ordem = ordem;
    }
    
    public String getDefeito( )
    {
        return defeito;
    }

    public void setDefeito( String defeito )
    {
        this.defeito = defeito;
    }

    @Override
    public int hashCode( )
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( ordem == null ) ? 0 : ordem.hashCode( ) );
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
        OrdemProduto other = (OrdemProduto)obj;
        if( ordem == null )
        {
            if( other.ordem != null )
                return false;
        }
        else if( !ordem.equals( other.ordem ) )
            return false;
        return true;
    }

    @Override
    public String toString( )
    {
        return "OrdemProduto [ordem=" + ordem + ", defeito=" + defeito + ", aparelho=" + aparelho + ", fabricante="
                + fabricante + ", notaFiscal=" + notaFiscal + ", emissaoNotaFiscal=" + emissaoNotaFiscal + ", cor="
                + cor + ", modelo=" + modelo + ", fabSerie=" + fabSerie + ", modeloEvaporadora=" + modeloEvaporadora
                + ", fabSerieEvaporadora=" + fabSerieEvaporadora + ", tensao=" + tensao + ", revendedor=" + revendedor
                + "]";
    }
}
