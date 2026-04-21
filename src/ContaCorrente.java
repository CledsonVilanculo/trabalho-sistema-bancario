/**
 * Produto concreto criado pelo Factory.
 * Usa o PATTERN STRATEGY para calcular taxas de forma intercambiável.
 */
public class ContaCorrente extends Conta {

    private TaxaStrategy taxaStrategy;

    public ContaCorrente(String numero, String titular, double saldoInicial) {
        super(numero, titular, saldoInicial);
        this.taxaStrategy = new TaxaStandard(); // padrão
    }

    /** Troca a estratégia em tempo de execução — núcleo do Strategy pattern */
    public void setTaxaStrategy(TaxaStrategy strategy) {
        System.out.printf("[Conta %s] Estratégia de taxa alterada para: %s%n",
                numero, strategy.descricao());
        this.taxaStrategy = strategy;
    }

    @Override
    protected double calcularTaxa(double valor) {
        return taxaStrategy.calcularTaxa(valor);
    }

    @Override
    public String tipo() { return "Corrente"; }
}