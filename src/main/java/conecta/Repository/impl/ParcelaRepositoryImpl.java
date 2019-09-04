package conecta.Repository.impl;


import com.uaihebert.uaicriteria.UaiCriteria;
import com.uaihebert.uaicriteria.UaiCriteriaFactory;
import conecta.Repository.ParcelaRepository;
import conecta.model.Cartao;
import conecta.model.Parcela;
import conecta.model.Pessoa;
import conecta.model.Situacao;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@SuppressWarnings("ALL")
@Named
public class ParcelaRepositoryImpl implements ParcelaRepository {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Parcela> buscarParcelas(Situacao situacao, Date dataInicio, Date dataFim, Pessoa pessoa, Cartao cartao) throws ParseException {
        UaiCriteria<Parcela> query = UaiCriteriaFactory.createQueryCriteria(manager, Parcela.class);
        query.innerJoin("divida");
      //
        //  query.innerJoin("pessoa");
        if (situacao != null) {
            query.andEquals("situacao", situacao);
        }
        if (dataInicio != null && dataFim != null) {
            query.andBetween("data", dataInicio, dataFim);
        }
        if (dataInicio != null && dataFim == null) {
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            query.andBetween("data", dataInicio, df.parse("01/01/2100"));
        }

        if (dataInicio == null && dataFim != null) {
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            // java.sql.Date dateIncial = new java.sql.Date("01-01-2000");
            query.andBetween("data", df.parse("01/01/2000"), dataFim);
        }
        if (pessoa != null) {
            query.andEquals("divida.pessoa", pessoa);
        }
        if (cartao != null) {
            query.andEquals("divida.cartao", cartao);
        }
        query.orderByAsc("divida.pessoa");
        query.orderByDesc("divida.dataCompra");
        query.orderByAsc("numero");

        return query.getResultList();
    }


    @Transactional
    @Override
    public void salvar(Parcela parcela) {
      //  manager.getTransaction().begin();
        if(parcela.getId() != null){
            manager.merge(parcela);
        }else{
            manager.persist(parcela);
        }
      //  manager.getTransaction().commit();
    }
}

