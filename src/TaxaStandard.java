/** Estratégia padrão: 2% de taxa sobre saques */
public class TaxaStandard implements TaxaStrategy {

    private static final double PERCENTUAL = 0.02;

    @Override
    public double calcularTaxa(double valor) {
        return valor * PERCENTUAL;
    }

    @Override
    public String descricao() {
        return "Taxa Standard (2%)";
    }
}