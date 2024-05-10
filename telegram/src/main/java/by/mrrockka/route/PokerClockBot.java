package by.mrrockka.route;

import by.mrrockka.route.commands.TelegramCommand;
import by.mrrockka.route.properties.BotProperties;
import by.mrrockka.service.UpdateBotCommandsService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.generics.BotOptions;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import org.telegram.telegrambots.util.WebhookUtils;

import java.util.List;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class PokerClockBot implements LongPollingBot {

  private final PokerClockAbsSender pokerClockAbsSender;
  private final UpdateBotCommandsService updateBotCommandsService;
  private final List<TelegramCommand> telegramCommands;
  private final BotProperties botProperties;

  @Override
  public void onUpdateReceived(final Update update) {
//    todo: add logic to process edited message
    if (isProcessable(update)) {
      log.debug("Processing {\n%s\n} message from %s chat id.".
                  formatted(update.getMessage().getText(), update.getMessage().getChatId()));
    }

    if (isProcessable(update)) {
      telegramCommands.stream()
        .filter(telegramCommand -> telegramCommand.isApplicable(update))
        .map(telegramCommand -> telegramCommand.process(update))
        .filter(Objects::nonNull)
        .findFirst()
        .map(this::executeMessage)
        .orElseThrow(NoRoutesFoundException::new);
    }
  }

  @Override
  public String getBotUsername() {
    return botProperties.getName();
  }

  @SneakyThrows
  private Message executeMessage(final BotApiMethodMessage message) {
    return pokerClockAbsSender.execute(message);
  }

  @Override
  public void clearWebhook() throws TelegramApiRequestException {
    WebhookUtils.clearWebhook(pokerClockAbsSender);
  }

  @Override
  public void onClosing() {
    pokerClockAbsSender.shutdown();
  }

  @Override
  public BotOptions getOptions() {
    return pokerClockAbsSender.getOptions();
  }

  @Override
  public String getBotToken() {
    return botProperties.getToken();
  }

  private boolean isProcessable(final Update update) {
    return update.hasMessage() && update.getMessage().isCommand();
  }

  @Override
  public void onRegister() {
    updateBotCommandsService.updateBotCommands(botProperties.getCommands());
  }
}
