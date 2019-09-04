package conecta.model;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Data
@Entity
@Table(name = "situacao")
public class Situacao implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_situacao")
    private Long id;

    @Column(name = "nome", nullable = false, length = 30)
    private String nome;


}