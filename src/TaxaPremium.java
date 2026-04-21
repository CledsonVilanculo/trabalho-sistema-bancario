/** Estratégia premium: 0.5% — clientes VIP pagam menos */
public class TaxaPremium implements TaxaStrategy {

    private static final double PERCENTUAL = 0.005;

    @Override
    public double calcularTaxa(double valor) {
        return valor * PERCENTUAL;
    }

    @Override
    public String descricao() {
        return "Taxa Premium (0.5%)";
    }
}