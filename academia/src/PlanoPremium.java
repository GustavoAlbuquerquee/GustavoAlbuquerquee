/**
 * Plano premium: permite qualquer atividade e oferece acesso ilimitado,
 * por isso não precisa registrar frequência (não implementa ControladorPresenca).
 */
public class PlanoPremium extends Plano {

    public PlanoPremium(String nome, double valorMensal) {
        super(nome, valorMensal);
    }

    @Override
    public boolean permiteAtividade(Atividade atividade) {
        return true;
    }

    @Override
    public String getCategoria() {
        return "Premium";
    }

    @Override
    public String getRegraAtividades() {
        return "permite qualquer atividade";
    }

    @Override
    public String getRegraAcesso() {
        return "acesso ilimitado (sem registro de frequência)";
    }
}
