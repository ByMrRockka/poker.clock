package by.mrrockka.features.calculation;

import by.mrrockka.creator.GameCreator;
import by.mrrockka.creator.PayoutCreator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CalculationServiceTest {

  @Mock
  private TournamentCalculationStrategy strategy;

  @Test
  void givenStrategy_whenAccountingCalculateExecuted_thenStrategyShouldBeCalled() {
    final var accounting = new CalculationService(List.of(strategy));
    final var game = GameCreator.tournament();
    final var expected = List.of(PayoutCreator.payout());

    when(strategy.isApplicable(game)).thenReturn(true);
    when(strategy.calculate(game)).thenReturn(expected);

    final var actual = accounting.calculate(game);

    assertThat(actual).isEqualTo(expected);
  }

}