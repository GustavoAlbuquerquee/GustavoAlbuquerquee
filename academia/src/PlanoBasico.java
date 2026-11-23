import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Plano básico: permite apenas atividades individuais e 1 acesso por dia.
 * Implementa ControladorPresenca para registrar (e limitar) os acessos
 * diários do cliente à academia.
 */
public class PlanoBasico extends Plano implements ControladorPresenca {

    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // nome do cliente -> datas em que acessou a academia
    private final Map<String, List<LocalDate>> acessosPorCliente;

    public PlanoBasico(String nome, double valorMensal) {
        super(nome, valorMensal);
        this.acessosPorCliente = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    }

    @Override
    public boolean permiteAtividade(Atividade atividade) {
        return atividade.isIndividual();
    }

    /**
     * Registra o acesso do cliente no dia de hoje.
     *
     * @throws IllegalStateException se o plano estiver inativo ou se o cliente já acessou hoje
     */
    @Override
    public void registrarPresenca(String nomeParticipante) {
        String cliente = Validacao.exigirTexto(nomeParticipante, "Nome do cliente");
        if (!isAtivo()) {
            throw new IllegalStateException("O plano \"" + getNome() + "\" está inativo. Acesso não permitido.");
        }

        LocalDate hoje = LocalDate.now();
        List<LocalDate> acessos = acessosPorCliente.get(cliente);
        if (acessos == null) {
            acessos = new ArrayList<>();
            acessosPorCliente.put(cliente, acessos);
        }
        if (acessos.contains(hoje)) {
            throw new IllegalStateException("Acesso negado: " + cliente + " já acessou a academia hoje ("
                    + hoje.format(FORMATO_DATA) + "). O plano básico permite apenas 1 acesso por dia.");
        }
        acessos.add(hoje);
    }

    public int getQuantidadeAcessos(String nomeCliente) {
        List<LocalDate> acessos = acessosPorCliente.get(Validacao.exigirTexto(nomeCliente, "Nome do cliente"));
        return acessos == null ? 0 : acessos.size();
    }

    @Override
    public String getCategoria() {
        return "Básico";
    }

    @Override
    public String getRegraAtividades() {
        return "apenas atividades individuais";
    }

    @Override
    public String getRegraAcesso() {
        return "1 acesso por dia (com controle de frequência)";
    }

    private String descreverAcessos() {
        if (acessosPorCliente.isEmpty()) {
            return "nenhum acesso registrado";
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, List<LocalDate>> entrada : acessosPorCliente.entrySet()) {
            List<LocalDate> datas = entrada.getValue();
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(entrada.getKey()).append(" (").append(datas.size()).append(" acesso(s), último em ")
                    .append(datas.get(datas.size() - 1).format(FORMATO_DATA)).append(")");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return super.toString() + System.lineSeparator() + "    Acessos de clientes: " + descreverAcessos();
    }
}
