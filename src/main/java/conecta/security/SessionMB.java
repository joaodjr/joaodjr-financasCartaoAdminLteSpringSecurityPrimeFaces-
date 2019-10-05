package conecta.security;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import conecta.Repository.UsuarioRepository;
import conecta.model.Usuario;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@Getter
@Named
@Scope("session")
public class SessionMB implements Serializable {

	@Autowired
	private UsuarioLogadoService usuarioLogadoService;

	private String currentUser;
	private  Usuario usuarioLogado;

	@PostConstruct
	public void init() {
       usuarioLogado = usuarioLogadoService.usuarioLogado();

	}


	public static boolean isUserInRole(String role) {
		// get security context from thread local
		SecurityContext context = SecurityContextHolder.getContext();
		if (context == null)
			return false;

		Authentication authentication = context.getAuthentication();
		if (authentication == null)
			return false;

		for (GrantedAuthority auth : authentication.getAuthorities()) {
			if (role.equals(auth.getAuthority()))
				return true;
		}

		return false;
	}

	public static boolean isUserAdmin() {
		// get security context from thread local
		SecurityContext context = SecurityContextHolder.getContext();
		if (context == null)
			return false;

		Authentication authentication = context.getAuthentication();
		if (authentication == null)
			return false;

		for (GrantedAuthority auth : authentication.getAuthorities()) {
			if (auth.getAuthority().equalsIgnoreCase("ADMIN"))
				return true;
		}

		return false;
	}
	public String getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(String currentUser) {
		this.currentUser = currentUser;
	}
}
