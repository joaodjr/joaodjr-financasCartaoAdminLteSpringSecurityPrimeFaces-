package conecta.bean;


import conecta.Repository.PessoaRepository;
import conecta.model.Pessoa;
import conecta.util.FacesUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//import org.omnifaces.cdi.ViewScoped;

@Data
@Named("pessoaBean")
@Scope("view")
public class PessoaBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @Autowired
    private PessoaRepository pessoaRepository;
    private Pessoa pessoa;
    private Boolean novo;
    private List<Pessoa> listaPessoa;


    @PostConstruct
    public void init() {
        pessoa = new Pessoa();
        listaPessoa = new ArrayList<>();
        novo = Boolean.FALSE;
        carregarPesquisa();
    }


    public void salvar() {
        try {
            pessoa.setAtiva(1);
            pessoaRepository.save(pessoa);
            carregarPesquisa();
            novo = Boolean.FALSE;
            // Limpa campos
            pessoa = new Pessoa();
            FacesUtil.addMsgInfo("Pessoa salvo com sucesso.");
        } catch (RuntimeException e) {
            FacesUtil.addMsgError("Ocorreu erro ao tentar incluir um Pessoa." + e.getMessage());
        }
    }

    public void novoCadastro(){
        novo = Boolean.TRUE;
    }

    public void desativa() {
        try {
            pessoa.setAtiva(0);
            pessoaRepository.save(pessoa);
            pessoa = null;
            carregarPesquisa();
            FacesUtil.addMsgInfo("Pessoa desativado com sucesso.");
        } catch (RuntimeException e) {
            FacesUtil.addMsgError("Ocorreu erro ao tentar desativar essa pessoa." + e.getMessage());
        }
    }

    public void novo() {
        setPessoa(new Pessoa());
    }

    public void carregarPesquisa() {
        try {
            listaPessoa = pessoaRepository.findAllOrderByNome();
        } catch (RuntimeException e) {
            FacesUtil.addMsgError("Ocorreu erro ao tentar listar os pessoas."+ e.getMessage());
        }
    }

    public void cancela(){
        novo = Boolean.FALSE;
    }


    public Pessoa getPessoa() {
        return pessoa;
    }
    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
    public List<Pessoa> getListaPessoa() {
        return listaPessoa;
    }
    public void setListaPessoa(List<Pessoa> listaPessoa) {
        this.listaPessoa = listaPessoa;
    }
    public Boolean getNovo() {
        return novo;
    }
    public void setNovo(Boolean novo) {
        this.novo = novo;
    }
}
