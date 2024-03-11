package by.mrrockka.features.accounting;

import by.mrrockka.domain.game.TournamentGame;
import by.mrrockka.domain.payout.Debt;
import by.mrrockka.domain.payout.Payout;
import by.mrrockka.domain.payout.TransferType;
import by.mrrockka.domain.summary.PlayerSummary;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


//TODO: strategy to work with different types of games
@Service
public class Accounting {

  public List<Payout> calculate(final TournamentGame game) {
    final var playerSummaries = game.getPlayers()
      .stream()
      .distinct()
      .map(player -> PlayerSummary.of(player, game.getTournamentGameSummary()))
      .sorted()
      .toList();

    return playerSummaries.stream()
      .filter(ps -> !ps.getTransferType().equals(TransferType.DEBIT))
      .map(creditorSummary -> {
        final var debtorSummaries = playerSummaries.stream()
          .filter(ps -> ps.getTransferType().equals(TransferType.DEBIT))
          .filter(ps -> (ps.getTransferAmount().compareTo(BigDecimal.ZERO) != 0))
          .sorted()
          .toList();

        return calculatePayouts(creditorSummary, debtorSummaries);
      }).toList();
  }

  private Payout calculatePayouts(final PlayerSummary creditorSummary, final List<PlayerSummary> debtorSummaries) {
    final var debts = new ArrayList<Debt>();
    var leftToPay = creditorSummary.getTransferAmount();
    final var payoutbuilder = Payout.builder()
      .creditor(creditorSummary.getPlayer());

    if (creditorSummary.getTransferType().equals(TransferType.EQUAL)) {
      return payoutbuilder.debts(Collections.emptyList()).build();
    }

    for (final var debtorSummary : debtorSummaries) {
      final var debtAmount = debtorSummary.getTransferAmount();
      final var debtBuilder = Debt.builder()
        .debtor(debtorSummary.getPlayer());

      final var debtComparison = debtAmount.compareTo(leftToPay);

      if (debtComparison == 0) {
        debtorSummary.subtractCalculated(debtAmount);
        debts.add(debtBuilder
                    .amount(debtAmount)
                    .build());
        break;
      }

      if (debtComparison < 0) {
        debtorSummary.subtractCalculated(debtAmount);
        debts.add(debtBuilder
                    .amount(debtAmount)
                    .build());
        leftToPay = leftToPay.subtract(debtAmount);
      }

      if (debtComparison > 0) {
        debtorSummary.subtractCalculated(leftToPay);
        debts.add(debtBuilder
                    .amount(leftToPay)
                    .build());
        break;
      }
    }

    return payoutbuilder.debts(debts).build();
  }

}
