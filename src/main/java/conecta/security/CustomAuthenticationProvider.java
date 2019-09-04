package conecta.security;

import java.util.ArrayList;
import java.util.List;

import conecta.Repository.UsuarioRepository;
import conecta.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {


    Usuario usuario;
	//HttpSession session;

    @Autowired
	UsuarioRepository usuarioRepository;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String user = authentication.getName();
		String password = authentication.getCredentials().toString();
		
		List<GrantedAuthority> grantedAuths = new ArrayList<>();

		usuario = usuarioRepository.findByNomeAndSenha(user, password);

		if (usuario != null){
			if(usuario.getAtivo() == 0){
				return null;
			}
			grantedAuths.add(new SimpleGrantedAuthority(usuario.getPerfilUsuario().getNome()));

            UsuarioLogado.guardarUsuario(usuario);
		}
		
		if (grantedAuths.size() > 0) {
			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, password, grantedAuths);
			return auth;
		}
			
		return null;
	}

	@Override
	public boolean supports(Class<? extends Object> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));

	}
	
}
