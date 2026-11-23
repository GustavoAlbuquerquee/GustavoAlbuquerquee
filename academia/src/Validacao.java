/**
 * Métodos utilitários de validação reaproveitados pelas classes do sistema.
 */
public final class Validacao {

    private Validacao() {
        // classe utilitária: não deve ser instanciada
    }

    public static String exigirTexto(String valor, String campo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException(campo + " não pode ser vazio.");
        }
        return valor.trim();
    }

    public static int exigirPositivo(int valor, String campo) {
        if (valor <= 0) {
            throw new IllegalArgumentException(campo + " deve ser maior que zero.");
        }
        return valor;
    }

    public static double exigirPositivo(double valor, String campo) {
        if (valor <= 0) {
            throw new IllegalArgumentException(campo + " deve ser maior que zero.");
        }
        return valor;
    }

    public static <T> T exigirNaoNulo(T valor, String campo) {
        if (valor == null) {
            throw new IllegalArgumentException(campo + " não pode ser nulo.");
        }
        return valor;
    }
}
