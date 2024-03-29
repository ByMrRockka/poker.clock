package by.mrrockka.features.calculation;

import by.mrrockka.domain.game.BountyGame;
import by.mrrockka.domain.game.Game;
import by.mrrockka.domain.payout.Payer;
import by.mrrockka.domain.payout.Payout;
import by.mrrockka.domain.summary.player.BountyPlayerSummary;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class BountyCalculationStrategy extends AbstractCalculationStrategy<BountyPlayerSummary, BountyGame> {
  @Override
  public boolean isApplicable(final Game game) {
    return game.isBounty();
  }

  @Override
  protected List<BountyPlayerSummary> buildPlayerSummary(final Game game) {
    final var bountyGame = game.asBounty();
    return bountyGame.getEntries().stream()
      .map(entry -> BountyPlayerSummary.of(entry, bountyGame.getBountyList(), bountyGame.getFinaleSummary()))
      .sorted()
      .toList();
  }

  @Override
  protected Payout buildPayoutBase(final BountyPlayerSummary creditorSummary) {
    return Payout.builder()
      .personEntries(creditorSummary.getPersonEntries())
      .personBounties(creditorSummary.getPersonBounties())
      .build();
  }

  @Override
  protected Payer buildDebtBase(final BountyPlayerSummary debtorSummary) {
    return Payer.builder()
      .personEntries(debtorSummary.getPersonEntries())
      .personBounties(debtorSummary.getPersonBounties())
      .build();
  }
}