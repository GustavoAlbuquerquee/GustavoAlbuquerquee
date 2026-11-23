/**
 * Turma de spinning: possui quantidade de bicicletas e registra frequência.
 */
public class Spinning extends Atividade implements ControladorPresenca {

    private final int quantidadeBicicletas;
    private final ControleFrequencia frequencia;

    public Spinning(String nome, String horario, int duracaoMinutos, int maxParticipantes,
                    TipoAtividade tipo, int quantidadeBicicletas) {
        super(nome, horario, duracaoMinutos, maxParticipantes, tipo);
        this.quantidadeBicicletas = Validacao.exigirPositivo(quantidadeBicicletas, "Quantidade de bicicletas");
        this.frequencia = new ControleFrequencia(maxParticipantes);
    }

    public int getQuantidadeBicicletas() {
        return quantidadeBicicletas;
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
    public String getModalidade() {
        return "Spinning";
    }

    @Override
    protected String getDetalhesEspecificos() {
        return String.format("Bicicletas: %d%n    Frequência: %s", quantidadeBicicletas, frequencia);
    }
}
