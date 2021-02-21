package br.com.attomtech.talbosch.api.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.attomtech.talbosch.api.utils.LabelValue;

@Service
public class DashboardService
{
    private static final Logger LOGGER = LoggerFactory.getLogger( DashboardService.class );
    
    private EstoqueService estoqueService;
    private OrdemServicoService ordemServicoService;
    
    @Autowired
    public DashboardService( EstoqueService estoqueService, OrdemServicoService ordemServicoService )
    {
        this.estoqueService = estoqueService;
        this.ordemServicoService = ordemServicoService;
    }

    public List<LabelValue> buscarInformacoes( )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Buscando informações para o dashboard" );
        
        List<LabelValue> informacoes = new ArrayList<LabelValue>( );
        
        Long abertasSemAgendamentos = ordemServicoService.buscarAbertasSemAgendamento( );
        adicionar( informacoes, abertasSemAgendamentos, "OS Abertas sem Data de Atendimento" );
        
        Long atendidasNaoFinalizadas = ordemServicoService.buscarAtendidasNaoFinalizadas( );
        adicionar( informacoes, atendidasNaoFinalizadas, "OS Atendidas e não Finalizadas" );
        
        Long ordensAguardandoPeca = ordemServicoService.buscarAguardandoPeca( );
        adicionar( informacoes, ordensAguardandoPeca, "OS Aguardando Peça" );
        
        Long pecasAChegar = estoqueService.buscarPecasAChegar( );
        adicionar( informacoes, pecasAChegar, "Peças \"A Chegar\"" );
        
        Long semClienteEOrdemDeServico = estoqueService.buscarSemClienteEOrdemDeServico( );
        adicionar( informacoes, semClienteEOrdemDeServico, "Peças sem Cliente e OS associada" );
        
        Long pecasNaoAgendadas = estoqueService.buscarPecasNaoAgendadas( );
        adicionar( informacoes, pecasNaoAgendadas, "Peças em estoque não Agendadas" );
        
        return informacoes;
    }
    
    private void adicionar( List<LabelValue> informacoes, Object value, String label )
    {
        LabelValue lb = new LabelValue( value, label );
        informacoes.add( lb );
    }
}
