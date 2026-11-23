/**
 * Turma de musculação: possui a quantidade de aparelhos disponíveis.
 */
public class Musculacao extends Atividade {

    private final int quantidadeAparelhos;

    public Musculacao(String nome, String horario, int duracaoMinutos, int maxParticipantes,
                      TipoAtividade tipo, int quantidadeAparelhos) {
        super(nome, horario, duracaoMinutos, maxParticipantes, tipo);
        this.quantidadeAparelhos = Validacao.exigirPositivo(quantidadeAparelhos, "Quantidade de aparelhos");
    }

    public int getQuantidadeAparelhos() {
        return quantidadeAparelhos;
    }

    @Override
    public String getModalidade() {
        return "Musculação";
    }

    @Override
    protected String getDetalhesEspecificos() {
        return "Aparelhos disponíveis: " + quantidadeAparelhos;
    }
}
