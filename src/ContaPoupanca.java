/**
 * Produto concreto criado pelo Factory.
 * Poupança: sem taxa em saques, mas com rendimento mensal.
 */
public class ContaPoupanca extends Conta {

    private static final double RENDIMENTO_MENSAL = 0.005; // 0,5% ao mês

    public ContaPoupanca(String numero, String titular, double saldoInicial) {
        super(numero, titular, saldoInicial);
    }

    /** Poupança não cobra taxa em saques */
    @Override
    protected double calcularTaxa(double valor) {
        return 0.0;
    }

    /** Aplica rendimento mensal e notifica observadores */
    public void aplicarRendimento() {
        double rendimento = saldo * RENDIMENTO_MENSAL;
        saldo += rendimento;
        System.out.printf("[Conta %s] Rendimento mensal: + %.2f MT — Saldo: %.2f%n MT",
                numero, rendimento, saldo);
        notificarObservadores("Rendimento", rendimento);
    }

    @Override
    public String tipo() { return "Poupança"; }
}