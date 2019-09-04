package conecta.Repository;

import conecta.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    @Query(value = "select p from Pessoa p where p.ativa = 1 order by p.nome")
    List<Pessoa> findPessoaByAtiva();

    List<Pessoa> findAllById(Long id);

    @Query(value = "select p from Pessoa p order by p.nome")
    List<Pessoa> findAllOrderByNome();
}
