/**
 * Comportamento de objetos que controlam frequência.
 * <ul>
 *   <li>Nas atividades: registra a presença do participante na aula.</li>
 *   <li>Nos planos: registra o acesso diário do cliente à academia.</li>
 * </ul>
 */
public interface ControladorPresenca {

    void registrarPresenca(String nomeParticipante);
}
