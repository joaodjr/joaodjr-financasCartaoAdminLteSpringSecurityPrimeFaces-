package conecta.model;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Entity
@Table(name = "pessoa")
public class Pessoa implements Serializable, AbstractEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_pessoa")
    private Long id;

    @NotEmpty(message = "Pessoa is not empty")
    @Size(min = 2, max = 30, message = "The name can not be less than 2 and greater than 30 caracters")
    @Column(name = "nome", nullable = false, length = 30, unique = true)
    private String nome;

    @NotNull(message = "Active is required")
    @Column(name = "ativa")
    private Integer ativa;

}