package br.com.reservas;

import br.com.reservas.facade.ReservaFacade;
import br.com.reservas.repository.ReservaRepository;
import br.com.reservas.strategy.PagamentoCartao;
import br.com.reservas.strategy.PagamentoPix;
import java.math.BigDecimal;
import java.time.LocalDate;

/** Testes sem bibliotecas externas. Execute seguindo o README. */
public class ReservaTest {
    private static int testes;

    public static void main(String[] args) {
        ReservaFacade facade = new ReservaFacade();
        LocalDate entrada = LocalDate.of(2026, 1, 30);
        LocalDate saida = LocalDate.of(2026, 2, 2);
        BigDecimal diaria = new BigDecimal("200.00");

        verificar(ReservaRepository.getInstance() == ReservaRepository.getInstance(), "Singleton");
        var pix = facade.reservar("Ana", entrada, saida, diaria, new PagamentoPix());
        verificar(pix.total().compareTo(new BigDecimal("540.00")) == 0, "Pix e virada de mes");
        var cartao = facade.reservar("Bruno", entrada, saida, diaria, new PagamentoCartao());
        verificar(cartao.total().compareTo(new BigDecimal("600.00")) == 0, "Cartao");
        var mesmoMes = facade.reservar("Carlos", entrada, entrada.plusDays(1), diaria, new PagamentoCartao());
        verificar(mesmoMes.total().compareTo(diaria) == 0, "Uma noite no mesmo mes");
        verificar(cartao.id() == pix.id() + 1, "IDs sequenciais");
        verificar(new ReservaFacade().listarReservas().contains(pix), "Repositorio compartilhado");
        verificar(new PagamentoPix().calcularTotal(new BigDecimal("100.05"))
                .compareTo(new BigDecimal("90.05")) == 0, "Arredondamento monetario");

        int antes = facade.listarReservas().size();
        rejeitar(() -> facade.reservar(" ", entrada, saida, diaria, new PagamentoPix()));
        rejeitar(() -> facade.reservar("Ana", entrada, entrada, diaria, new PagamentoPix()));
        rejeitar(() -> facade.reservar("Ana", saida, entrada, diaria, new PagamentoPix()));
        rejeitar(() -> facade.reservar("Ana", entrada, saida, BigDecimal.ZERO, new PagamentoPix()));
        rejeitar(() -> facade.reservar("Ana", entrada, saida, new BigDecimal("-1"), new PagamentoPix()));
        rejeitar(() -> facade.reservar("Ana", null, saida, diaria, new PagamentoPix()));
        rejeitar(() -> facade.reservar("Ana", entrada, saida, null, new PagamentoPix()));
        rejeitar(() -> facade.reservar("Ana", entrada, saida, diaria, null));
        verificar(facade.listarReservas().size() == antes, "Entradas invalidas nao sao salvas");

        try {
            facade.listarReservas().clear();
            throw new AssertionError("A listagem deve ser imutavel.");
        } catch (UnsupportedOperationException esperado) {
            testes++;
        }
        System.out.println("Todos os " + testes + " testes passaram.");
    }

    private static void verificar(boolean condicao, String nome) {
        if (!condicao) throw new AssertionError("Falhou: " + nome);
        testes++;
    }

    private static void rejeitar(Runnable acao) {
        try {
            acao.run();
            throw new AssertionError("Era esperado IllegalArgumentException.");
        } catch (IllegalArgumentException esperado) {
            testes++;
        }
    }
}
