package by.mrrockka.domain.payout;

import by.mrrockka.domain.Player;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record Debt(Player debtor, BigDecimal amount) {
}
