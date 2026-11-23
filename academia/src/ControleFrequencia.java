import java.util.Map;
import java.util.TreeMap;

/**
 * Registro de presença dos participantes de uma turma.
 * Usado por composição nas atividades que registram frequência
 * (Natação e Spinning), evitando duplicação de código.
 */
public class ControleFrequencia {

    private final int limiteParticipantes;
    // nome do participante -> quantidade de presenças (nomes sem diferenciar maiúsculas/minúsculas)
    private final Map<String, Integer> presencas;

    public ControleFrequencia(int limiteParticipantes) {
        this.limiteParticipantes = Validacao.exigirPositivo(limiteParticipantes, "Limite de participantes");
        this.presencas = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    }

    /**
     * Registra uma presença para o participante.
     *
     * @throws IllegalStateException se for um novo participante e a turma já estiver lotada
     */
    public void registrar(String nomeParticipante) {
        String participante = Validacao.exigirTexto(nomeParticipante, "Nome do participante");
        Integer quantidadeAtual = presencas.get(participante);

        if (quantidadeAtual == null && presencas.size() >= limiteParticipantes) {
            throw new IllegalStateException("Turma lotada: o limite de " + limiteParticipantes
                    + " participantes já foi atingido.");
        }
        presencas.put(participante, quantidadeAtual == null ? 1 : quantidadeAtual + 1);
    }

    public int getQuantidadePresencas(String nomeParticipante) {
        Integer quantidade = presencas.get(Validacao.exigirTexto(nomeParticipante, "Nome do participante"));
        return quantidade == null ? 0 : quantidade;
    }

    public int getQuantidadeParticipantes() {
        return presencas.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(presencas.size()).append('/').append(limiteParticipantes).append(" participantes");
        if (presencas.isEmpty()) {
            return sb.append(" - nenhuma presença registrada").toString();
        }
        sb.append(" - ");
        boolean primeiro = true;
        for (Map.Entry<String, Integer> entrada : presencas.entrySet()) {
            if (!primeiro) {
                sb.append(", ");
            }
            sb.append(entrada.getKey()).append(" (").append(entrada.getValue()).append(")");
            primeiro = false;
        }
        return sb.toString();
    }
}
