package conecta.model;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {
    private static long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_usuario")
    private Long id;

    @Column(name = "nome", nullable = false, length = 30, unique = true)
    private String nome;

    @Column(name = "senha", nullable = false, length = 100)
    private String senha;

    @ManyToOne
    @JoinColumn(name = "fk_cod_pessoa", nullable = false)
    private Pessoa pessoa;

   @Column(name = "ativo", nullable = false)
   private Integer ativo;

   @OneToOne
   @JoinColumn(name = "fk_cod_avatar")
   private Avatar avatar;

    @ManyToOne
    @JoinColumn(name = "fk_cod_perfil_usuario", nullable = false)
    private PerfilUsuario perfilUsuario;
}