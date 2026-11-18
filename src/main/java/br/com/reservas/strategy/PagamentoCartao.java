package br.com.reservas.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PagamentoCartao implements PagamentoStrategy {
    @Override
    public BigDecimal calcularTotal(BigDecimal subtotal) {
        return subtotal.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public String getDescricao() {
        return "Cartao (sem desconto)";
    }
}
