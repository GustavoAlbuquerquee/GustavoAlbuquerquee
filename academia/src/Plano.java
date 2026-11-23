import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Classe base dos planos oferecidos pela academia.
 * Cada plano pode possuir várias atividades associadas.
 */
public abstract class Plano {

    private final String nome;
    private final double valorMensal;
    private Status status;
    private final List<Atividade> atividades;

    protected Plano(String nome, double valorMensal) {
        this.nome = Validacao.exigirTexto(nome, "Nome do plano");
        this.valorMensal = Validacao.exigirPositivo(valorMensal, "Valor mensal");
        this.status = Status.ATIVO;
        this.atividades = new ArrayList<>();
    }

    /** Cada tipo de plano define quais atividades aceita. */
    public abstract boolean permiteAtividade(Atividade atividade);

    /** Nome da categoria do plano (Básico, Premium...). */
    public abstract String getCategoria();

    /** Descrição da regra de atividades permitidas. */
    public abstract String getRegraAtividades();

    /** Descrição da regra de acesso à academia. */
    public abstract String getRegraAcesso();

    /**
     * Associa uma atividade ao plano, respeitando as regras do tipo de plano.
     *
     * @throws IllegalStateException    se o plano estiver inativo
     * @throws IllegalArgumentException se a atividade for inválida para este plano
     */
    public void associarAtividade(Atividade atividade) {
        Validacao.exigirNaoNulo(atividade, "Atividade");
        if (!isAtivo()) {
            throw new IllegalStateException("O plano \"" + nome + "\" está inativo e não aceita novas atividades.");
        }
        if (!atividade.isAtiva()) {
            throw new IllegalArgumentException("A atividade \"" + atividade.getNome() + "\" está inativa.");
        }
        if (atividades.contains(atividade)) {
            throw new IllegalArgumentException("A atividade \"" + atividade.getNome()
                    + "\" já está associada ao plano \"" + nome + "\".");
        }
        if (!permiteAtividade(atividade)) {
            throw new IllegalArgumentException("O plano " + getCategoria() + " não permite a atividade \""
                    + atividade.getNome() + "\" (" + atividade.getTipo().getDescricao() + "). Regra: "
                    + getRegraAtividades() + ".");
        }
        atividades.add(atividade);
    }

    public boolean removerAtividade(Atividade atividade) {
        return atividades.remove(atividade);
    }

    public String getNome() {
        return nome;
    }

    public double getValorMensal() {
        return valorMensal;
    }

    public Status getStatus() {
        return status;
    }

    public boolean isAtivo() {
        return status == Status.ATIVO;
    }

    public void ativar() {
        status = Status.ATIVO;
    }

    public void inativar() {
        status = Status.INATIVO;
    }

    public List<Atividade> getAtividades() {
        return Collections.unmodifiableList(atividades);
    }

    private String descreverAtividades() {
        if (atividades.isEmpty()) {
            return "nenhuma";
        }
        StringBuilder sb = new StringBuilder();
        for (Atividade atividade : atividades) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(atividade.getNome());
            if (!atividade.isAtiva()) {
                sb.append(" (inativa)");
            }
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return String.format("[Plano %s] %s | R$ %.2f/mês | Status: %s%n"
                        + "    Regras: %s; %s%n"
                        + "    Atividades associadas: %s",
                getCategoria(), nome, valorMensal, status.getDescricao(),
                getRegraAtividades(), getRegraAcesso(),
                descreverAtividades());
    }
}
