package br.com.reservas.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PagamentoPix implements PagamentoStrategy {
    @Override
    public BigDecimal calcularTotal(BigDecimal subtotal) {
        return subtotal.multiply(new BigDecimal("0.90")).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public String getDescricao() {
        return "Pix (10% de desconto)";
    }
}
