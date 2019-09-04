package conecta.Repository;

import conecta.model.Divida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DividaSalvaRepository  extends JpaRepository<Divida, Long> {
}
