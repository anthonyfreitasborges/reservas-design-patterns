package br.com.reservas.facade;

import br.com.reservas.model.Reserva;
import br.com.reservas.repository.ReservaRepository;
import br.com.reservas.strategy.PagamentoStrategy;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

/** Facade: oferece uma entrada simples para validar, calcular e salvar reservas. */
public class ReservaFacade {
    private final ReservaRepository repository = ReservaRepository.getInstance();

    public Reserva reservar(String hospede, LocalDate checkin, LocalDate checkout,
                            BigDecimal valorDiaria, PagamentoStrategy pagamento) {
        if (hospede == null || hospede.isBlank()) {
            throw new IllegalArgumentException("O nome do hospede e obrigatorio.");
        }
        if (checkin == null || checkout == null || !checkout.isAfter(checkin)) {
            throw new IllegalArgumentException("O checkout deve ser posterior ao checkin.");
        }
        if (valorDiaria == null || valorDiaria.signum() <= 0) {
            throw new IllegalArgumentException("O valor da diaria deve ser maior que zero.");
        }
        if (pagamento == null) {
            throw new IllegalArgumentException("Informe a forma de pagamento.");
        }

        long noites = ChronoUnit.DAYS.between(checkin, checkout);
        BigDecimal subtotal = valorDiaria.multiply(BigDecimal.valueOf(noites));
        BigDecimal total = pagamento.calcularTotal(subtotal);
        return repository.salvar(hospede.strip(), checkin, checkout, total, pagamento.getDescricao());
    }

    public List<Reserva> listarReservas() {
        return repository.listar();
    }
}
