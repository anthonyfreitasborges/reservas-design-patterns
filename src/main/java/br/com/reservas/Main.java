package br.com.reservas;

import br.com.reservas.facade.ReservaFacade;
import br.com.reservas.model.Reserva;
import br.com.reservas.strategy.PagamentoCartao;
import br.com.reservas.strategy.PagamentoPix;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        ReservaFacade facade = new ReservaFacade();
        LocalDate checkin = LocalDate.of(2026, 12, 10);
        LocalDate checkout = LocalDate.of(2026, 12, 13);
        BigDecimal diaria = new BigDecimal("200.00");

        facade.reservar("Anthony", checkin, checkout, diaria, new PagamentoPix());
        facade.reservar("Maria", checkin, checkout, diaria, new PagamentoCartao());

        NumberFormat moeda = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("pt-BR"));
        System.out.println("=== Reservas de hospedagem ===");
        for (Reserva reserva : facade.listarReservas()) {
            System.out.printf("#%d | %s | %s ate %s | %s | %s%n",
                    reserva.id(), reserva.hospede(), reserva.checkin(), reserva.checkout(),
                    reserva.formaPagamento(), moeda.format(reserva.total()));
        }
    }
}
