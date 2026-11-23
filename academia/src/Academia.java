import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Responsável por gerenciar as atividades e os planos cadastrados (regras de negócio),
 * separada da interação com o usuário (classe Menu).
 */
public class Academia {

    private final List<Atividade> atividades;
    private final List<Plano> planos;

    public Academia() {
        this.atividades = new ArrayList<>();
        this.planos = new ArrayList<>();
    }

    public void cadastrarAtividade(Atividade atividade) {
        atividades.add(Validacao.exigirNaoNulo(atividade, "Atividade"));
    }

    public void cadastrarPlano(Plano plano) {
        planos.add(Validacao.exigirNaoNulo(plano, "Plano"));
    }

    public List<Atividade> getAtividades() {
        return Collections.unmodifiableList(atividades);
    }

    public List<Plano> getPlanos() {
        return Collections.unmodifiableList(planos);
    }

    public List<Plano> getPlanosAtivos() {
        List<Plano> ativos = new ArrayList<>();
        for (Plano plano : planos) {
            if (plano.isAtivo()) {
                ativos.add(plano);
            }
        }
        return ativos;
    }

    /** Todos os objetos do sistema (atividades ou planos) que implementam Certificavel. */
    public List<Certificavel> getCertificaveis() {
        List<Object> todos = new ArrayList<>();
        todos.addAll(atividades);
        todos.addAll(planos);
        return filtrarPorTipo(todos, Certificavel.class);
    }

    /** Atividades que implementam ControladorPresenca (presença em aula). */
    public List<ControladorPresenca> getAtividadesComControlePresenca() {
        return filtrarPorTipo(atividades, ControladorPresenca.class);
    }

    /** Planos que implementam ControladorPresenca (acesso diário à academia). */
    public List<ControladorPresenca> getPlanosComControlePresenca() {
        return filtrarPorTipo(planos, ControladorPresenca.class);
    }

    /**
     * Remove do sistema todas as atividades inativas, retirando-as também
     * dos planos em que estavam associadas.
     *
     * @return as atividades removidas
     */
    public List<Atividade> removerAtividadesInativas() {
        List<Atividade> removidas = new ArrayList<>();
        Iterator<Atividade> iterador = atividades.iterator();
        while (iterador.hasNext()) {
            Atividade atividade = iterador.next();
            if (!atividade.isAtiva()) {
                removidas.add(atividade);
                iterador.remove();
            }
        }
        for (Plano plano : planos) {
            for (Atividade removida : removidas) {
                plano.removerAtividade(removida);
            }
        }
        return removidas;
    }

    private static <T> List<T> filtrarPorTipo(List<?> origem, Class<T> tipo) {
        List<T> resultado = new ArrayList<>();
        for (Object item : origem) {
            if (tipo.isInstance(item)) {
                resultado.add(tipo.cast(item));
            }
        }
        return resultado;
    }
}
