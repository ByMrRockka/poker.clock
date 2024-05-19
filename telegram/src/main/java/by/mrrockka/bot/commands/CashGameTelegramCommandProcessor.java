package by.mrrockka.bot.commands;

import by.mrrockka.service.game.GameTelegramService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class CashGameTelegramCommandProcessor implements TelegramCommandProcessor {
  private static final String COMMAND = "^/cash$";
  private final GameTelegramService gameService;

  @Override
  public BotApiMethodMessage process(final Update update) {
    return gameService.storeCashGame(update);
  }

  @Override
  public String commandPattern() {
    return COMMAND;
  }

}
