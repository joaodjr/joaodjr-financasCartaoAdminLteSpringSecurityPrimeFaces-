//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package conecta.bean;

import conecta.Repository.CartaoRepository;
import conecta.Repository.ParcelaRepository;
import conecta.Repository.PessoaRepository;
import conecta.Repository.SituacaoRepository;
import conecta.enums.SituacaoEnum;
import conecta.model.*;
import conecta.security.UsuarioLogadoService;
import conecta.util.FacesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Named("parcelaBean")
@Scope("view")
public class ParcelaBean implements Serializable {
    private static final long serialVersionUID = 1L;
    @Autowired
    PessoaRepository pessoaRepository;
    @Autowired
    CartaoRepository cartaoRepository;
    @Autowired
    private SituacaoRepository situacaoRepository;
    @Autowired
    private ParcelaRepository parcelaRepository;
    @Autowired
    private UsuarioLogadoService usuarioLogadoService;

    private Situacao situacao;
    private Pessoa pessoa;
    private Parcela parcela;
    private Cartao cartao;


    private List<Parcela> listaParcelas;
    private List<Pessoa> listaPessoas;
    private List<Cartao> listaCartoes;
    private List<Situacao> listaSituacao;
    private Date dataInicio;
    private Date dataFim;
    private BigDecimal valorTotalParcelas;

    @PostConstruct
    public void init() {

        Usuario usuario= usuarioLogadoService.usuarioLogado();
        if(usuario.getPerfilUsuario().getId() == 1){
            listaPessoas  = pessoaRepository.findPessoaByAtiva();
            listaCartoes  = cartaoRepository.findCartaoByAtivo();
            listaSituacao = situacaoRepository.findAll();
            pessoa   = new Pessoa();
        }else {
            listaPessoas = pessoaRepository.findAllById((usuario.getPessoa().getId()));
            listaCartoes = cartaoRepository.findCartaoByAtivo();
            listaSituacao = situacaoRepository.findAll();
            pessoa = getListaPessoas().iterator().next();
        }
        situacao = new Situacao();
        cartao   = new Cartao();
    }

    public void buscaParcelas() throws ParseException {
        if (this.dataInicio != null && this.dataFim != null && this.dataFim.getTime() < this.dataInicio.getTime()) {
            FacesUtil.addMsgError("Ops, a data inicio é maior que a data fim!");
        } else if (dataInicio == null && dataFim == null && situacao == null && cartao == null && pessoa == null) {
            FacesUtil.addMsgError("Escolha ao menos um filtro");
        } else {
                listaParcelas = parcelaRepository.buscarParcelas(situacao, dataInicio, dataFim, pessoa, cartao);

            this.valorTotalParcelas = BigDecimal.ZERO;
            this.valorTotalParcelas = (BigDecimal)this.listaParcelas.stream().map(Parcela::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
        }
    }


    public void salvar() {
        try {
            parcela.setSituacao(new Situacao());
            parcela.getSituacao().setId(SituacaoEnum.PAGO.getValue());
            parcelaRepository.salvar(parcela);
            this.parcela = new Parcela();
            FacesUtil.addMsgInfo("Parcela salvo com sucesso.");
        } catch (RuntimeException var2) {
            FacesUtil.addMsgError("Ocorreu erro ao tentar incluir um Parcela." + var2.getMessage());
        }

    }

    public void pagarParcela() throws ParseException {      
        if (this.parcela != null) {
            this.parcela.setSituacao(new Situacao());
            this.parcela.getSituacao().setId(SituacaoEnum.PAGO.getValue());
            this.parcelaRepository.salvar(parcela);
            this.parcela = new Parcela();
            this.buscaParcelas();
            FacesUtil.addMsgInfo("Parcela paga com sucesso");
        } else {
            FacesUtil.addMsgError("Erro ao pagar parcela");
        }

    }

    public Parcela getParcela() {
        if (this.parcela == null) {
            this.parcela = new Parcela();
        }

        return this.parcela;
    }

    public void setParcela(Parcela parcela) {
        this.parcela = parcela;
    }

    public List<Parcela> getListaParcelas() {
        return this.listaParcelas;
    }

    public void setListaParcelas(List<Parcela> listaParcelas) {
        this.listaParcelas = listaParcelas;
    }

    public Pessoa getPessoa() {
        if (this.pessoa == null) {
            this.pessoa = new Pessoa();
        }

        return this.pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public List<Pessoa> getListaPessoas() {
        return this.listaPessoas;
    }

    public void setListaPessoas(List<Pessoa> listaPessoas) {
        this.listaPessoas = listaPessoas;
    }

    public Cartao getCartao() {
        return this.cartao;
    }

    public void setCartao(Cartao cartao) {
        this.cartao = cartao;
    }

    public List<Cartao> getListaCartoes() {
        return this.listaCartoes;
    }

    public void setListaCartoes(List<Cartao> listaCartoes) {
        this.listaCartoes = listaCartoes;
    }

    public Date getDataInicio() {
        return this.dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return this.dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public List<Situacao> getListaSituacao() {
        if (this.listaSituacao == null) {
            this.listaSituacao = new ArrayList<Situacao>();
        }

        return this.listaSituacao;
    }

    public void setListaSituacao(List<Situacao> listaSituacao) {
        this.listaSituacao = listaSituacao;
    }

    public Situacao getSituacao() {
        if (this.situacao == null) {
            this.situacao = new Situacao();
        }

        return this.situacao;
    }

    public void setSituacao(Situacao situacao) {
        this.situacao = situacao;
    }

    public BigDecimal getValorTotalParcelas() {
        return this.valorTotalParcelas;
    }

    public void setValorTotalParcelas(BigDecimal valorTotalParcelas) {
        this.valorTotalParcelas = valorTotalParcelas;
    }
}
