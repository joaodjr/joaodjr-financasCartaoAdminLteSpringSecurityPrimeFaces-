package conecta.Repository;


import conecta.model.Cartao;
import conecta.model.Parcela;
import conecta.model.Pessoa;
import conecta.model.Situacao;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

//@Repository
public interface ParcelaRepository {
    List<Parcela> buscarParcelas(Situacao situacao, Date dataInicio, Date dataFim, Pessoa pessoa, Cartao cartao) throws ParseException;
    void salvar(Parcela parcela);
    void delete(Long idDivida);
}
