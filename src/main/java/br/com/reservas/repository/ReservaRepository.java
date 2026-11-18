package br.com.reservas.repository;

import br.com.reservas.model.Reserva;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/** Singleton: compartilha o mesmo armazenamento em memoria na aplicacao. */
public final class ReservaRepository {
    private static final ReservaRepository INSTANCIA = new ReservaRepository();
    private final List<Reserva> reservas = new ArrayList<>();
    private int proximoId = 1;

    private ReservaRepository() {
    }

    public static ReservaRepository getInstance() {
        return INSTANCIA;
    }

    public synchronized Reserva salvar(String hospede, LocalDate checkin, LocalDate checkout,
                                       BigDecimal total, String formaPagamento) {
        Reserva reserva = new Reserva(proximoId++, hospede, checkin, checkout, total, formaPagamento);
        reservas.add(reserva);
        return reserva;
    }

    public synchronized List<Reserva> listar() {
        return List.copyOf(reservas);
    }
}
