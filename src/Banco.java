import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * PATTERN: Singleton
 * Garante que existe apenas UMA instância do banco em toda a aplicação.
 * Acesso global via Banco.getInstance()
 */
public class Banco {

    private static Banco instancia;   // única instância

    private final String nome;
    private final List<Conta> contas = new ArrayList<>();
    private int proximoNumero = 1000;

    // Construtor privado — ninguém pode fazer "new Banco()"
    private Banco(String nome) {
        this.nome = nome;
    }

    /** Ponto de acesso global. Thread-safe com double-checked locking. */
    public static Banco getInstance() {
        if (instancia == null) {
            synchronized (Banco.class) {
                if (instancia == null) {
                    instancia = new Banco("Banco Central S.A.");
                }
            }
        }
        return instancia;
    }

    public String gerarNumeroConta() {
        return String.format("AG001-%04d", proximoNumero++);
    }

    public void adicionarConta(Conta conta) {
        contas.add(conta);
        System.out.println("[Banco] Conta " + conta.getNumero() + " cadastrada.");
    }

    public Optional<Conta> buscarConta(String numero) {
        return contas.stream()
                .filter(c -> c.getNumero().equals(numero))
                .findFirst();
    }

    public void listarContas() {
        System.out.println();
        System.out.println("=".repeat(38));
        
        if (contas.isEmpty()) {
            System.out.println("  Nenhuma conta cadastrada.");
        } else {
            contas.forEach(c -> System.out.println("  " + c.resumo()));
        }
        System.out.println("=".repeat(38));
    }

    public String getNome() { return nome; }
}