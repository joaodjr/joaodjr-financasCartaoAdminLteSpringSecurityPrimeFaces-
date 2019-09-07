package conecta.bean;

import conecta.Repository.PerfilUsuarioRepository;
import conecta.Repository.PessoaRepository;
import conecta.Repository.UsuarioRepository;
import conecta.model.PerfilUsuario;
import conecta.model.Pessoa;
import conecta.model.Usuario;
import conecta.util.FacesUtil;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Scope("view")
@Named("usuarioBean")
public class UsuarioBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private PerfilUsuarioRepository perfilUsuarioRepository;

    List<Pessoa> listaPessoas;
    List<PerfilUsuario> listaPerfilUsuario;
    List<Usuario> listaUsuarios;

    private Usuario usuario;
    private Pessoa pessoa;
    private PerfilUsuario perfilUsuario;

    private UploadedFile imagem;
    private Boolean novo;


    @PostConstruct
    public void init() {
        novo = Boolean.FALSE;
        usuario = new Usuario();
        perfilUsuario = new PerfilUsuario();
        pessoa = new Pessoa();
        listaUsuarios = usuarioRepository.findAll();
    }

    public void novoUsuario() {
        novo = Boolean.TRUE;
        listaPessoas = pessoaRepository.findPessoaByAtiva();
        listaPerfilUsuario = perfilUsuarioRepository.findAll();
    }

    public void salvar() {
        if(imagem != null){
            System.out.println(" tem imagem");
        }
        usuario.setAtivo(1);
        usuarioRepository.save(usuario);
        usuario = new Usuario();
        FacesUtil.addMsgInfo("Usuário salvo com sucesso!");
        listaUsuarios = usuarioRepository.findAll();
    }

    public void cancelar() {
        novo = Boolean.FALSE;
    }

    public void desativa() {
        usuario.setAtivo(0);
        usuarioRepository.save(usuario);
        usuario = new Usuario();
        listaUsuarios = usuarioRepository.findAll();
        FacesUtil.addMsgInfo("Usuário desativado com sucesso!");
    }

}
