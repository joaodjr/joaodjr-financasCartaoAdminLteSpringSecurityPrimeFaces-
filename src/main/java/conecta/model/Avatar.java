package conecta.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "avatar")
public class Avatar implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="cod_avatar")
    private Integer id;

    @Column(name = "arquivo", nullable = false)
    private String arquivo;

    @Column(name = "formato", nullable = false)
    private String formato;

    @Column(name = "nome", nullable = false)
    private String nome;
}
