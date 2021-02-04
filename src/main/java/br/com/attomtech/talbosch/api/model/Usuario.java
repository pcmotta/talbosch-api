package br.com.attomtech.talbosch.api.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.group.GroupSequenceProvider;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import br.com.attomtech.talbosch.api.model.abstracts.Model;
import br.com.attomtech.talbosch.api.model.enums.Permissao;
import br.com.attomtech.talbosch.api.validation.UsuarioGroupSequenceProvider;
import br.com.attomtech.talbosch.api.validation.groups.SenhaObrigatoriaGroup;

@GroupSequenceProvider(value = UsuarioGroupSequenceProvider.class)
@Table(name = "usuario")
@Entity
public class Usuario extends Model
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @NotBlank(message = "O nome do usuário é obrigatório")
    private String nome;

    @NotBlank(message = "O login é obrigatório")
    private String login;

    @JsonProperty(access = Access.WRITE_ONLY)
    @NotBlank(message = "A senha é obrigatória", groups = SenhaObrigatoriaGroup.class)
    private String        senha;
    private LocalDateTime ultimoAcesso;
    private boolean       ativo = true;

    @ElementCollection(targetClass = Permissao.class)
    @JoinTable(name = "usuario_permissao", joinColumns = @JoinColumn(name = "codigo_usuario"))
    @Column(name = "permissao", nullable = false)
    @Enumerated(EnumType.STRING)
    private List<Permissao> permissoes;
    
    @JsonIgnore
    public boolean isNovo( )
    {
        return codigo == null;
    }

    @JsonIgnore
    public boolean isAdministrador( )
    {
        return permissoes.stream( ).filter( permissao -> permissao == Permissao.ADMINISTRADOR )
                .count( ) > 0;
    }

    public Long getCodigo( )
    {
        return codigo;
    }

    public void setCodigo( Long codigo )
    {
        this.codigo = codigo;
    }

    public String getNome( )
    {
        return nome;
    }

    public void setNome( String nome )
    {
        this.nome = nome;
    }

    public String getLogin( )
    {
        return login;
    }

    public void setLogin( String login )
    {
        this.login = login;
    }

    public String getSenha( )
    {
        return senha;
    }

    public void setSenha( String senha )
    {
        this.senha = senha;
    }

    public LocalDateTime getUltimoAcesso( )
    {
        return ultimoAcesso;
    }

    public void setUltimoAcesso( LocalDateTime ultimoAcesso )
    {
        this.ultimoAcesso = ultimoAcesso;
    }

    public boolean isAtivo( )
    {
        return ativo;
    }

    public void setAtivo( boolean ativo )
    {
        this.ativo = ativo;
    }

    public List<Permissao> getPermissoes( )
    {
        return permissoes;
    }

    public void setPermissoes( List<Permissao> permissoes )
    {
        this.permissoes = permissoes;
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
        Usuario other = (Usuario)obj;
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
