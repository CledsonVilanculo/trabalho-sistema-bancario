/**
 * PATTERN: Strategy
 * Encapsula o algoritmo de cálculo de taxa em objetos intercambiáveis.
 * Basta trocar a estratégia para mudar o comportamento sem alterar a Conta.
 */
public interface TaxaStrategy {
    double calcularTaxa(double valor);
    String descricao();
}