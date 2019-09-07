package conecta.model;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Getter
@Entity
@Table(name = "divida")
public class Divida implements Serializable, AbstractEntity {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "cod_divida")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "valor_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorTotal;

    @Column(name = "quant_parcela", nullable = false, length = 2)
    private Integer quantParcela;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_inicio_pagamento", nullable = false)
    private Date dataInicioPagamento;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_compra", nullable = false)
    private Date dataCompra;

    @Column(nullable = false, length = 30)
    private String local;

    @Column(nullable = false, length = 30)
    private String descricao;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_cadastro")
    private Date dataCadastro;

    @ManyToOne
    @JoinColumn(name = "fk_cod_pessoa", nullable = false)
    private Pessoa pessoa;

    @ManyToOne
    @JoinColumn(name = "fk_cod_cartao", nullable = false)
    private Cartao cartao;

	/*
	 * @JsonIgnore
	 * 
	 * @OneToMany(mappedBy ="divida",cascade = CascadeType.ALL, orphanRemoval =
	 * true) List<Parcela> parcelas = new ArrayList<>();
	 */

    public String getDataFormatada() {
        SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
        return dt.format(this.dataCompra);
    }

    public String getDataInicioFormatada() {
        SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
        return dt.format(this.dataInicioPagamento);
    }


}