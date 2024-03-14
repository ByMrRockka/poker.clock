package by.mrrockka.route;

import by.mrrockka.service.TelegramWithdrawalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static by.mrrockka.mapper.CommandRegexConstants.COMMAND_APPENDIX;

@Component
@RequiredArgsConstructor
public class WithdrawalTelegramCommand implements TelegramCommand {
  private static final String COMMAND = "/withdrawal%s".formatted(COMMAND_APPENDIX);

  private final TelegramWithdrawalService telegramWithdrawalService;

  @Override
  public BotApiMethodMessage process(final Update update) {
    return telegramWithdrawalService.storeWithdrawal(update);
  }

  @Override
  public boolean isApplicable(final Update update) {
    return TelegramCommand.super.isApplicable(update)
      && update.getMessage().getText().matches(COMMAND);
  }
}
