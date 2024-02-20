package by.mrrockka.mapper;

import by.mrrockka.domain.Player;
import by.mrrockka.domain.payments.Payments;
import by.mrrockka.repo.entries.EntriesEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface PlayerMapper {

  @Mapping(target = "payments", source = "amounts", qualifiedByName = "amountListToPayments")
  Player toPlayer(EntriesEntity entriesEntity);

  @Named("amountListToPayments")
  default Payments amountListToPayments(List<BigDecimal> amount) {
    return new Payments(amount);
  }
}