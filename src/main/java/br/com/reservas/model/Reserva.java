package br.com.reservas.model;

import java.math.BigDecimal;
import java.time.LocalDate;

/** Registro imutavel com os dados de uma reserva confirmada. */
public record Reserva(int id, String hospede, LocalDate checkin, LocalDate checkout,
                      BigDecimal total, String formaPagamento) {
}
