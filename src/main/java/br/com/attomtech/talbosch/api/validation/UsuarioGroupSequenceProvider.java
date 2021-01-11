package br.com.attomtech.talbosch.api.validation;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

import br.com.attomtech.talbosch.api.model.Usuario;
import br.com.attomtech.talbosch.api.validation.groups.SenhaObrigatoriaGroup;

public class UsuarioGroupSequenceProvider implements DefaultGroupSequenceProvider<Usuario>
{
    @Override
    public List<Class<?>> getValidationGroups( Usuario usuario )
    {
        List<Class<?>> groups = new ArrayList<Class<?>>( );
        
        groups.add( Usuario.class );
        
        if( usuario != null && usuario.isNovo( ) )
            groups.add( SenhaObrigatoriaGroup.class );
        
        return groups;
    }

}
