package br.com.reservas.strategy;

import java.math.BigDecimal;

/** Contrato para trocar a regra de pagamento sem alterar o fluxo da reserva. */
public interface PagamentoStrategy {
    BigDecimal calcularTotal(BigDecimal subtotal);
    String getDescricao();
}
