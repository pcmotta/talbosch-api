package br.com.attomtech.talbosch.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.attomtech.talbosch.api.model.Mensagem;
import br.com.attomtech.talbosch.api.model.Usuario;
import br.com.attomtech.talbosch.api.repository.query.MensagemRepositoryQuery;

@Repository
public interface MensagemRepository extends JpaRepository<Mensagem, Long>, MensagemRepositoryQuery
{
    Long countByUsuarioDestinoAndDeletadoDestinoFalseAndLidoFalse( Usuario usuario );
    
    @Query("SELECT COUNT(m) FROM Mensagem m WHERE m.usuarioDestino = ?1 AND m.deletadoDestino = false AND lido = false")
    int buscarNaoLidas( Usuario usuario );
}
