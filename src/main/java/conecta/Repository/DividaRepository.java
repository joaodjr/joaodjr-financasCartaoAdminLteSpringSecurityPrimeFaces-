package conecta.Repository;

import conecta.model.Divida;
import org.primefaces.model.SortOrder;

import java.util.List;
import java.util.Map;


public interface DividaRepository  {

    Integer getQuantidade(Map<String, Object> filtros);
    List<Divida> BuscaTodosLazy(int firstResult, int maxResults, String sortField, SortOrder sortOrder, Map<String, Object> filtros);
    void salvar(Divida divida);
    void excluir(Divida divida);
    Divida buscarById(Long id);
}

