package conecta.Repository.impl;


import com.uaihebert.uaicriteria.UaiCriteria;
import com.uaihebert.uaicriteria.UaiCriteriaFactory;
import conecta.Repository.DividaRepository;
import conecta.model.Divida;
import org.primefaces.model.SortOrder;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;


@SuppressWarnings("ALL")
@Named
public class DividaRepositoryImpl implements DividaRepository {

    @PersistenceContext
    private EntityManager manager;


    @Override
    public Integer getQuantidade(Map<String, Object> filtros) {
        UaiCriteria<Divida> query = UaiCriteriaFactory.createQueryCriteria(this.manager, Divida.class);
        query.innerJoin("cartao");
        query.innerJoin("pessoa");
        if (filtros.get("cartao.nome") != null) {
            query.andStringLike("cartao.nome", "%" + filtros.get("cartao.nome").toString().toUpperCase() + "%");
        }

        if (filtros.get("pessoa.nome") != null) {
            query.andStringLike("pessoa.nome", "%" + filtros.get("pessoa.nome").toString().toUpperCase() + "%");
        }

        if (filtros.get("local") != null) {
            query.andStringLike("local", "%" + filtros.get("local").toString().toUpperCase() + "%");
        }

        if (filtros.get("descricao") != null) {
            query.andStringLike("descricao", "%" + filtros.get("descricao").toString().toUpperCase() + "%");
        }

        return Math.toIntExact(query.countRegularCriteria());
    }

    @Override
    public List<Divida> BuscaTodosLazy(int firstResult, int maxResults, String sortField, SortOrder sortOrder, Map<String, Object> filtros) {
        UaiCriteria<Divida> query = UaiCriteriaFactory.createQueryCriteria(this.manager, Divida.class);
        query.innerJoin("cartao");
        query.innerJoin("pessoa");
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResults);
        if (SortOrder.ASCENDING.equals(sortOrder) && sortField != null) {
            query.orderByAsc(sortField);
        } else if (sortField != null) {
            query.orderByDesc(sortField);
        }

        if (filtros.get("cartao.nome") != null) {
            query.andStringLike("cartao.nome", "%" + filtros.get("cartao.nome").toString().toUpperCase() + "%");
        }

        if (filtros.get("pessoa.nome") != null) {
            query.andStringLike("pessoa.nome", "%" + filtros.get("pessoa.nome").toString().toUpperCase() + "%");
        }

        if (filtros.get("local") != null) {
            query.andStringLike("local", "%" + filtros.get("local").toString().toUpperCase() + "%");
        }

        if (filtros.get("descricao") != null) {
            query.andStringLike("descricao", "%" + filtros.get("descricao").toString().toUpperCase() + "%");
        }

        query.orderByDesc("dataCadastro");
        return query.getResultList();
    }

    @Transactional
    @Override
    public void salvar(Divida divida) {
       // if (divida.getId() != null){
          //  manager.merge(divida);
       // }else{
            manager.persist(divida);
       // }
    }

    @Transactional
    @Override
    public void excluir(Divida divida) {
        if(divida.getId() != null) {
            divida = buscarById(divida.getId());
            manager.remove(divida);
        }
    }

    @Override
    public Divida buscarById(Long id) {
        Divida divida = new Divida();
        try {
            UaiCriteria<Divida> query = UaiCriteriaFactory.createQueryCriteria(this.manager, Divida.class);
            query.andEquals("id", id);
            divida = query.getSingleResult();
        }catch (NoResultException e){
            divida = null;
        }catch (RuntimeException e){
            divida = null;
        }catch (Exception e){
            divida = null;
        }
        return divida;
    }
}
