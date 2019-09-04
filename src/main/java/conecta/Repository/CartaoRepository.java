package conecta.Repository;

import conecta.model.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartaoRepository extends JpaRepository<Cartao, Long> {

    @Query(value = "select c from Cartao c where c.ativo =1 order by c.nome")
    List<Cartao> findCartaoByAtivo();

    List<Cartao> findAllById(Long id);
}
