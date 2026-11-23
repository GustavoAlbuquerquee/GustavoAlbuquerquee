/**
 * Define se a atividade é praticada individualmente ou em grupo.
 * O plano básico só aceita atividades individuais.
 */
public enum TipoAtividade {
    INDIVIDUAL("Individual"),
    COLETIVA("Coletiva");

    private final String descricao;

    TipoAtividade(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
