package conecta.security;

import conecta.model.Usuario;
import lombok.Data;

import javax.enterprise.context.SessionScoped;

import org.springframework.context.annotation.Scope;

import java.io.Serializable;

@Data
@Scope("session")
public class UsuarioLogado implements Serializable {
    private static final long serialVersionUID = 1L;
    private static Usuario usuarioLogado;

    public Usuario usuarioLogado() {
      return usuarioLogado;
    }

    public static void guardarUsuario(Usuario usuario){

        usuarioLogado = usuario;
    }
}