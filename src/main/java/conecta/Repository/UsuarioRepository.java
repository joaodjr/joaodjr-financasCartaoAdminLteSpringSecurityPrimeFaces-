package conecta.Repository;

import conecta.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query(value = "select u from Usuario u where u.nome = :nomeUsuario and u.senha = :senhaUsuario")
    Usuario findByNomeAndSenha(@Param(value = "nomeUsuario")String nomeUsuario, @Param(value = "senhaUsuario")String senhaUsuario);

    @Query(value = "select u from Usuario u where u.nome = :nomeUsuario")
    Usuario findByNome(@Param(value = "nomeUsuario") String nome);
}
