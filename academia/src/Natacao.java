import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Turma de natação: possui profundidade da piscina,
 * oferece certificado e registra frequência.
 */
public class Natacao extends Atividade implements Certificavel, ControladorPresenca {

    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final double profundidadePiscina;
    private final ControleFrequencia frequencia;

    public Natacao(String nome, String horario, int duracaoMinutos, int maxParticipantes,
                   TipoAtividade tipo, double profundidadePiscina) {
        super(nome, horario, duracaoMinutos, maxParticipantes, tipo);
        this.profundidadePiscina = Validacao.exigirPositivo(profundidadePiscina, "Profundidade da piscina");
        this.frequencia = new ControleFrequencia(maxParticipantes);
    }

    public double getProfundidadePiscina() {
        return profundidadePiscina;
    }

    public int getQuantidadePresencas(String nomeParticipante) {
        return frequencia.getQuantidadePresencas(nomeParticipante);
    }

    @Override
    public void registrarPresenca(String nomeParticipante) {
        if (!isAtiva()) {
            throw new IllegalStateException("A turma \"" + getNome() + "\" está inativa. Não é possível registrar presença.");
        }
        frequencia.registrar(nomeParticipante);
    }

    @Override
    public void emitirCertificado(String nomeParticipante) {
        String participante = Validacao.exigirTexto(nomeParticipante, "Nome do participante");
        System.out.println("==========================================================");
        System.out.println("                 CERTIFICADO DE NATAÇÃO");
        System.out.println("==========================================================");
        System.out.println("Certificamos que " + participante + " participou da turma");
        System.out.println("\"" + getNome() + "\" (horário " + getHorario() + ", aulas de "
                + getDuracaoMinutos() + " min).");
        System.out.println("Presenças registradas: " + frequencia.getQuantidadePresencas(participante));
        System.out.println("Emitido em: " + LocalDate.now().format(FORMATO_DATA));
        System.out.println("==========================================================");
    }

    @Override
    public String getModalidade() {
        return "Natação";
    }

    @Override
    protected String getDetalhesEspecificos() {
        return String.format("Profundidade da piscina: %.2f m | Oferece certificado%n"
                + "    Frequência: %s", profundidadePiscina, frequencia);
    }
}
