package br.com.attomtech.talbosch.api.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.attomtech.talbosch.api.model.Cliente;
import br.com.attomtech.talbosch.api.model.OrdemServico;
import br.com.attomtech.talbosch.api.repository.query.OrdemServicoRepositoryQuery;

@Repository
public interface OrdemServicoRepository extends JpaRepository<OrdemServico, Long>, OrdemServicoRepositoryQuery
{
    @Query("SELECT count(o) FROM OrdemServico o WHERE o.status = 'EMABERTO' AND o.dataAtendimento IS NULL")
    Long ordensAbertasSemAtendimento( );
    
    @Query("SELECT COUNT(o) FROM OrdemServico o WHERE o.dataAtendimento < ?1 AND o.status = 'EMABERTO'")
    Long atendidasNaoFinalizadas( LocalDate dataHoje );
    
    @Query("SELECT COUNT(o) FROM OrdemServico o WHERE o.status = 'AGUARDANDOPECA'")
    Long aguardandoPeca( );
    
    List<OrdemServico> findByCliente( Cliente cliente );
}
