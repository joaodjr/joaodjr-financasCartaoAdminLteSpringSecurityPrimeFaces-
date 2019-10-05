package conecta.security;

import conecta.Repository.UsuarioRepository;
import conecta.model.Usuario;
import lombok.Data;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Data
@Named
//@Service
//@Scope("session")
public class UsuarioLogadoService {

    @Autowired
    UsuarioRepository repository;

    private Usuario usuarioLogado;

    public Usuario usuarioLogado() {
        Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            //System.out.println(authentication.getName());
            usuarioLogado = repository.findByNome(authentication.getName());

        }

        return usuarioLogado;
    }

    public Object getUsuarioLogado() {
        return usuarioLogado;
    }

   /* public void setUsuarioLogado(Object usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }*/
   /* private static final long serialVersionUID = 1L;
    private static Usuario usuarioLogado;

    public Usuario usuarioLogado() {
      return usuarioLogado;
    }

    public static void guardarUsuario(Usuario usuario){

        usuarioLogado = usuario;
    }*/
}