import java.util.Scanner;

/**
 * Ponto de entrada do sistema de gerenciamento da academia.
 */
public class Main {

    public static void main(String[] args) {
        Academia academia = new Academia();
        carregarDadosIniciais(academia);

        try (Scanner entrada = new Scanner(System.in)) {
            new Menu(academia, entrada).executar();
        }
    }

    /** Cria 3 atividades e 2 planos assim que o programa é executado. */
    private static void carregarDadosIniciais(Academia academia) {
        academia.cadastrarAtividade(
                new Musculacao("Musculação Livre", "06:00", 60, 30, TipoAtividade.INDIVIDUAL, 25));
        academia.cadastrarAtividade(
                new Natacao("Natação Adulto", "07:30", 50, 12, TipoAtividade.INDIVIDUAL, 1.80));
        academia.cadastrarAtividade(
                new Spinning("Spinning Intensivo", "19:00", 45, 20, TipoAtividade.COLETIVA, 20));

        academia.cadastrarPlano(new PlanoBasico("Básico Mensal", 89.90));
        academia.cadastrarPlano(new PlanoPremium("Premium Mensal", 179.90));
    }
}
