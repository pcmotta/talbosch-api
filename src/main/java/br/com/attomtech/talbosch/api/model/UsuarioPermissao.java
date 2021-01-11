package br.com.attomtech.talbosch.api.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.attomtech.talbosch.api.model.enums.Permissao;

@Table(name = "usuario_permissao")
@Entity
public class UsuarioPermissao
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;
    
    @ManyToOne
    @JoinColumn(name = "codigo_usuario")
    private Usuario usuario;
    
    @Enumerated(EnumType.STRING)
    private Permissao permissao;

    public Long getCodigo( )
    {
        return codigo;
    }

    public void setCodigo( Long codigo )
    {
        this.codigo = codigo;
    }

    public Usuario getUsuario( )
    {
        return usuario;
    }

    public void setUsuario( Usuario usuario )
    {
        this.usuario = usuario;
    }

    public Permissao getPermissao( )
    {
        return permissao;
    }

    public void setPermissao( Permissao permissao )
    {
        this.permissao = permissao;
    }

}
