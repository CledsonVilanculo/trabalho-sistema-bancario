import java.util.ArrayList;
import java.util.List;

/**
 * Classe base para todas as contas.
 * Implementa o papel de "Subject" no pattern Observer:
 * mantém lista de observadores e os notifica a cada operação.
 */
public abstract class Conta {

    protected final String numero;
    protected final String titular;
    protected double saldo;

    // Lista de observadores (Observer pattern)
    private final List<NotificacaoObserver> observadores = new ArrayList<>();

    protected Conta(String numero, String titular, double saldoInicial) {
        this.numero   = numero;
        this.titular  = titular;
        this.saldo    = saldoInicial;
    }

    // ── Observer ──────────────────────────────────────────────
    public void adicionarObservador(NotificacaoObserver obs) {
        observadores.add(obs);
    }

    public void removerObservador(NotificacaoObserver obs) {
        observadores.remove(obs);
    }

    protected void notificarObservadores(String evento, double valor) {
        observadores.forEach(o -> o.notificar(numero, evento, valor));
    }

    // ── Operações comuns ──────────────────────────────────────
    public void depositar(double valor) {
        if (valor <= 0) throw new IllegalArgumentException("Valor inválido para depósito.");
        saldo += valor;
        System.out.printf("[Conta %s] Depósito de %.2f MT | Saldo: %.2f MT\n", numero, valor, saldo);
        notificarObservadores("Depósito", valor);
    }

    /** Saque com taxa calculada pela Strategy da subclasse */
    public void sacar(double valor) {
        double taxa  = calcularTaxa(valor);
        double total = valor + taxa;
        if (total > saldo) throw new IllegalStateException("Saldo insuficiente.");
        saldo -= total;
        System.out.printf("[Conta %s] Saque de %.2f MT(taxa: %.2f MT) | Saldo: %.2f MT\n",
                numero, valor, taxa, saldo);
        notificarObservadores("Saque", valor);
    }

    public void transferir(Conta destino, double valor) {
        this.sacar(valor);
        destino.depositar(valor);
        System.out.printf("[Conta %s → %s] Transferência de %.2f MT concluída.%n",
                numero, destino.getNumero(), valor);
    }

    // ── Template Method — cada subclasse implementa a sua taxa ─
    protected abstract double calcularTaxa(double valor);

    public abstract String tipo();

    // ── Getters ───────────────────────────────────────────────
    public String getNumero()  { return numero; }
    public String getTitular() { return titular; }
    public double getSaldo()   { return saldo; }

    public String resumo() {
        return String.format("%-10s | %-20s | %-12s | %9.2f MT",
                numero, titular, tipo(), saldo);
    }
}