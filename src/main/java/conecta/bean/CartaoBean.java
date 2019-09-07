/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conecta.bean;

import conecta.Repository.CartaoRepository;
import conecta.model.Cartao;
import conecta.util.FacesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

//import org.omnifaces.cdi.ViewScoped;

/**
 * @author joao
 */
@Named("cartaoBean")
@Scope("view")
public class CartaoBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @Autowired
    private CartaoRepository cartaoRepository;
    private Cartao cartao;
    private Boolean novo;

    private List<Cartao> listaCartoes;

    @PostConstruct
    public void init() {
        cartao = new Cartao();
        novo = Boolean.FALSE;
        carregarPesquisa();
    }

    public void carregarPesquisa() {
        listaCartoes = cartaoRepository.findAll();
    }

    public void salvar() {
        try {
            cartao.setAtivo(1);
            cartaoRepository.save(cartao);
            novo = Boolean.FALSE;
            cartao = new Cartao();
            FacesUtil.addMsgInfo("Cartão cadastrado com sucesso!");
        } catch (Exception e) {
            FacesUtil.addMsgError("erro: " + e);
        }
    }


    public void cancela() {
        novo = Boolean.FALSE;
    }

    public void novoCadastro() {
        novo = Boolean.TRUE;
    }

    public void desativar() {
        try {
            cartao.setAtivo(0);
            cartaoRepository.save(cartao);
            FacesUtil.addMsgInfo("Cartão desativado com sucesso.");
        } catch (RuntimeException var2) {
            FacesUtil.addMsgError("Ocorreu erro ao tentar desativar cartao." + var2.getMessage());
        }
    }


    public Cartao getCartao() {
        return cartao;
    }

    public void setCartao(Cartao cartao) {
        this.cartao = cartao;
    }

    public List<Cartao> getListaCartoes() {
        return listaCartoes;
    }

    public void setListaCartoes(List<Cartao> ListaCartoes) {
        this.listaCartoes = ListaCartoes;
    }

    public Boolean getNovo() {
        return novo;
    }

    public void setNovo(Boolean novo) {
        this.novo = novo;
    }
}
