package br.com.attomtech.talbosch.api.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.attomtech.talbosch.api.exception.NegocioException;
import br.com.attomtech.talbosch.api.model.Estoque;
import br.com.attomtech.talbosch.api.model.OrdemServico;
import br.com.attomtech.talbosch.api.repository.EstoqueRepository;
import br.com.attomtech.talbosch.api.repository.OrdemServicoRepository;
import br.com.attomtech.talbosch.api.repository.filter.OrdemServicoFilter;
import br.com.attomtech.talbosch.api.service.interfaces.NegocioServiceAuditoria;

@Service
public class OrdemServicoService extends AuditoriaService<OrdemServico> implements NegocioServiceAuditoria<OrdemServico, OrdemServicoFilter, Long>
{
    private static final Logger LOGGER = LoggerFactory.getLogger( OrdemServicoService.class );

    private OrdemServicoRepository repository;
    private EstoqueRepository      estoqueRepository;
    private UsuarioService         usuarioService;
    
    @Autowired
    public OrdemServicoService( OrdemServicoRepository repository, EstoqueRepository estoqueRepository, UsuarioService usuarioService )
    {
        this.repository = repository;
        this.estoqueRepository = estoqueRepository;
        this.usuarioService = usuarioService;
    }

    @Override
    public Page<OrdemServico> pesquisar( OrdemServicoFilter filtro, Pageable pageable )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Pesquisando > {}", filtro );
        
        return repository.pesquisar( filtro, pageable );
    }


    @Override
    public OrdemServico buscarPorCodigo( Long codigo )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Buscando por Código > {}", codigo );
        
        Optional<OrdemServico> ordem = repository.findById( codigo );
        
        return ordem.orElseThrow( ( ) -> new NegocioException( "Ordem de Serviço não encontrada" ) );
    }

    @Override
    public OrdemServico salvar( OrdemServico ordem )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Salvando > {}", ordem );
        
        return repository.save( ordem );
    }

    @Override
    public OrdemServico cadastrar( OrdemServico ordem, String login )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Cadastrando > {}", ordem );
        
        atualizarAuditoriaInclusao( ordem, login );
        tratarRelacionamentos( ordem, login );
        
        return salvar( ordem );
    }
    
    private void tratarRelacionamentos( OrdemServico ordem, String login )
    {
        ordem.getEndereco( ).setOrdem( ordem );
        ordem.getProduto( ).setOrdem( ordem );
        
        if( ordem.temValor( ) )
            ordem.getValores( ).forEach( valor -> valor.setOrdem( ordem ) );
        
        if( ordem.temAndamento( ) )
            ordem.getAndamentos( ).forEach( andamento -> {
                andamento.setOrdem( ordem );
                
                if( andamento.getUsuario( ) == null )
                    andamento.setUsuario( usuarioService.buscarPorLogin( login ) );
            });
    }
    
    @Override
    public OrdemServico atualizar( OrdemServico ordem, String login )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Atualizando > {}", ordem );
        
        OrdemServico ordemSalva = buscarPorCodigo( ordem.getNumero( ) );
        
        ordem.setAuditoria( ordemSalva.getAuditoria( ) );
        atualizarAuditoriaAlteracao( ordem, login );
        tratarRelacionamentos( ordem, login );
        
        return salvar( ordem );
    }

    @Override
    public void excluir( Long numero, String login )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Excluíndo > {}", numero );
        
        Optional<List<Estoque>> estoques = estoqueRepository.findByOrdemServicoNumero( numero );
        
        if( estoques.isPresent( ) )
            throw new NegocioException( "Não é possível excluir, pois existem peças no estoque associadas a essa ordem de serviço" );
        
        repository.deleteById( numero );
    }

}
