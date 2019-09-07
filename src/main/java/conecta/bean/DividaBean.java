//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package conecta.bean;

import conecta.Repository.*;
import conecta.enums.SituacaoEnum;
import conecta.model.*;
import conecta.util.FacesUtil;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

//import org.omnifaces.cdi.ViewScoped;

@Named("dividaBean")
@Scope("view")
public class DividaBean implements Serializable {
	private static final long serialVersionUID = 1L;
	@Autowired
	private PessoaRepository pessoaRepository;
	@Autowired
	private CartaoRepository cartaoRepository;
	@Autowired
	private DividaRepository dividaRepository;
    @Autowired
    private DividaSalvaRepository dividaSalvaRepository;
    @Autowired
    private ParcelaRepository parcelaRepository;
    @Autowired 
    private ParcelaSalvarRepository parcelaSalvarRepository;
	 

	private List<Parcela> listaParcelas;
	private List<Divida> listaDividas;
	private List<Pessoa> listaPessoas;
	private List<Cartao> listaCartoes;

	// @Inject
	private Pessoa pessoa;
	// @Inject
	private Cartao cartao;
	// @Inject
	private Divida divida;
	// @Inject
	private Parcela parcela;
	private LazyDataModel<Divida> listaDividaLazy;
	private String acao;

	public DividaBean() {
	}

	@PostConstruct
	public void init() {
		listaCartoes = cartaoRepository.findCartaoByAtivo();
		listaPessoas = pessoaRepository.findPessoaByAtiva();
		carregarDividas();
	}

	public LazyDataModel<Divida> carregarDividas() {
		return listaDividaLazy = new LazyDataModel<Divida>() {
			private static final long serialVersionUID = 1L;

			public List<Divida> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				this.setRowCount(dividaRepository.getQuantidade(filters));
				return dividaRepository.BuscaTodosLazy(first, pageSize, sortField, sortOrder, filters);
			}
		};

	}

	public Date calcularData(Date data) {
		Calendar c = Calendar.getInstance();
		c.setTime(data);
		c.set(2, c.get(2) + 1);
		data = c.getTime();
		return data;
	}

	public Integer diaVencimento(Date inicioPagamento) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		Integer dia = Integer.parseInt(sdf.format(inicioPagamento));
		return dia;
	}

	@Transactional(readOnly = false)
	public void salvar() {
		if (this.divida.getCartao().getDiaVencimento() != this.diaVencimento(this.divida.getDataInicioPagamento())) {
			FacesUtil.addMsgError(
					"O dia do mês de vencimento desse cartão é " + this.divida.getCartao().getDiaVencimento());
			return;
		} else {
			try {
				Integer numPar = this.divida.getQuantParcela();
				int cont = 1;
				Date dataVencimento = divida.getDataInicioPagamento();
				// divida.setParcelas(new ArrayList<>());
				List<Parcela> listaParcelas = new ArrayList<>();

				// divida.setParcelas(listaParcelas);
			
				divida.setLocal(divida.getLocal().toUpperCase());
				divida.setDescricao(divida.getDescricao().toUpperCase());
				divida.setDataCadastro(new Date()); divida.setId((Long)null); 
				divida =  dividaSalvaRepository.save(divida);
				

				while (cont <= numPar) {
					parcela = new Parcela();
					parcela.setData(dataVencimento);
					parcela.setNumero(cont);
					parcela.setValor(
							divida.getValorTotal().divide(BigDecimal.valueOf((long) numPar), RoundingMode.HALF_UP));
					parcela.setSituacao(new Situacao());
					parcela.getSituacao().setId(SituacaoEnum.EM_ABERTO.getValue());
					parcela.setDivida(divida);
					listaParcelas.add(parcela);
					dataVencimento = calcularData(dataVencimento);
					++cont;
				}

				parcelaSalvarRepository.saveAll(listaParcelas);
				/*
				 * divida.setParcelas(listaParcelas);
				 * divida.setLocal(divida.getLocal().toUpperCase());
				 * divida.setDescricao(divida.getDescricao().toUpperCase());
				 * divida.setDataCadastro(new Date()); divida.setId(null);
				 * dividaRepository.salvar(divida);
				 */
				divida = new Divida();
				cartao = new Cartao();
				pessoa = new Pessoa();
				carregarDividas();
				FacesUtil.addMsgInfo("Divida salva com sucesso.");
			} catch (RuntimeException var4) {
				FacesUtil.addMsgError("Ocorreu erro ao tentar incluir uma Divida." + var4.getMessage());
			}

		}
	}

	@Transactional(readOnly = false)
	public void editar() {
		try {
			if (this.divida.getCartao().getDiaVencimento() != this
					.diaVencimento(this.divida.getDataInicioPagamento())) {
				FacesUtil.addMsgError(
						"O dia do mês de vencimento desse cartão é " + this.divida.getCartao().getDiaVencimento());
				return;
			}
			parcelaRepository.delete(divida.getId());
			dividaRepository.excluir(divida);
			this.salvar();
		} catch (RuntimeException var2) {
			FacesUtil.addMsgError("Ocorreu erro ao tentar alterar um Divida." + var2.getMessage());
		}

	}

	@Transactional(readOnly = false)
	public void excluir() {
		try {
			parcelaRepository.delete(divida.getId());
			dividaRepository.excluir(divida);
			carregarDividas();
			divida = new Divida();
			FacesUtil.addMsgInfo("Divida excluido com sucesso.");
		} catch (RuntimeException var2) {
			FacesUtil.addMsgError("Ocorreu erro ao tentar excluir um Divida." + var2.getMessage());
		}

	}

	public void novo() {
		this.setDivida(new Divida());
	}

	public Divida getDivida() {
		if (this.divida == null) {
			this.divida = new Divida();
		}

		return this.divida;
	}

	public void setDivida(Divida divida) {
		this.divida = divida;
	}

	public List<Divida> getListaDividas() {
		return this.listaDividas;
	}

	public void setListaDividas(List<Divida> listaDividas) {
		this.listaDividas = listaDividas;
	}

	public String getAcao() {
		return this.acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}

	public List<Pessoa> getListaPessoas() {
		return this.listaPessoas;
	}

	public void setListaPessoas(List<Pessoa> listaPessoas) {
		this.listaPessoas = listaPessoas;
	}

	public Pessoa getPessoa() {
		return this.pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public List<Parcela> getListaParcelas() {
		return this.listaParcelas;
	}

	public void setListaParcelas(List<Parcela> listaParcelas) {
		this.listaParcelas = listaParcelas;
	}

	public List<Cartao> getListaCartoes() {
		return this.listaCartoes;
	}

	public void setListaCartoes(List<Cartao> listaCartoes) {
		this.listaCartoes = listaCartoes;
	}

	public Cartao getCartao() {
		return this.cartao;
	}

	public void setCartao(Cartao cartao) {
		this.cartao = cartao;
	}

	public LazyDataModel<Divida> getListaDividaLazy() {
		return this.listaDividaLazy;
	}

	public void setListaDividaLazy(LazyDataModel<Divida> listaDividaLazy) {
		this.listaDividaLazy = listaDividaLazy;
	}
}
