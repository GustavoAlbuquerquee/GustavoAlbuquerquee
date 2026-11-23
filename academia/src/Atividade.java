/**
 * Classe base de todas as atividades. Cada objeto representa uma turma
 * específica oferecida pela academia.
 */
public abstract class Atividade {

    private final String nome;
    private final String horario;
    private final int duracaoMinutos;
    private final int maxParticipantes;
    private final TipoAtividade tipo;
    private Status status;

    protected Atividade(String nome, String horario, int duracaoMinutos,
                        int maxParticipantes, TipoAtividade tipo) {
        this.nome = Validacao.exigirTexto(nome, "Nome da atividade");
        this.horario = Validacao.exigirTexto(horario, "Horário");
        this.duracaoMinutos = Validacao.exigirPositivo(duracaoMinutos, "Duração da aula");
        this.maxParticipantes = Validacao.exigirPositivo(maxParticipantes, "Quantidade máxima de participantes");
        this.tipo = Validacao.exigirNaoNulo(tipo, "Tipo da atividade");
        this.status = Status.ATIVO;
    }

    /** Nome da modalidade (Musculação, Natação, Spinning...). */
    public abstract String getModalidade();

    /** Informações próprias de cada subclasse, usadas na exibição. */
    protected abstract String getDetalhesEspecificos();

    public String getNome() {
        return nome;
    }

    public String getHorario() {
        return horario;
    }

    public int getDuracaoMinutos() {
        return duracaoMinutos;
    }

    public int getMaxParticipantes() {
        return maxParticipantes;
    }

    public TipoAtividade getTipo() {
        return tipo;
    }

    public Status getStatus() {
        return status;
    }

    public boolean isAtiva() {
        return status == Status.ATIVO;
    }

    public boolean isIndividual() {
        return tipo == TipoAtividade.INDIVIDUAL;
    }

    public void ativar() {
        status = Status.ATIVO;
    }

    public void inativar() {
        status = Status.INATIVO;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s | Status: %s%n"
                        + "    Horário: %s | Duração: %d min | Máx. participantes: %d | Tipo: %s%n"
                        + "    %s",
                getModalidade(), nome, status.getDescricao(),
                horario, duracaoMinutos, maxParticipantes, tipo.getDescricao(),
                getDetalhesEspecificos());
    }
}
