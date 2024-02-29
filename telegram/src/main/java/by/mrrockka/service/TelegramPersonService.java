package by.mrrockka.service;

import by.mrrockka.domain.TelegramPerson;
import by.mrrockka.mapper.person.PersonMessageMapper;
import by.mrrockka.mapper.person.TelegramPersonMapper;
import by.mrrockka.repo.person.TelegramPersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class TelegramPersonService {

  private final PersonMessageMapper personMessageMapper;
  private final TelegramPersonMapper telegramPersonMapper;
  private final PersonService personService;
  private final TelegramPersonRepository telegramPersonRepository;
  private final UsernameReplaceUtil usernameReplaceUtil;

  @Transactional(propagation = Propagation.REQUIRED)
  public List<TelegramPerson> storePersons(Update update) {
    final var command = usernameReplaceUtil.replaceUsername(update);
    final var chatId = update.getMessage().getChatId();
    final var persons = personMessageMapper.map(command, chatId);

    return storeMissed(persons, chatId);
  }

  public TelegramPerson getByTelegramAndChatId(String telegram, Long chatId) {
    return telegramPersonRepository.findByTelegram(chatId, telegram)
      .map(telegramPersonMapper::mapToTelegram)
      .orElseGet(() -> saveNew(telegram, chatId));
  }

  public List<TelegramPerson> getAllByTelegramsAndChatId(List<String> telegrams, Long chatId) {
    return telegramPersonMapper.mapToTelegrams(telegramPersonRepository.findAllByChatIdAndTelegrams(chatId, telegrams));
  }

  public List<TelegramPerson> getAllByGameId(UUID gameId) {
    return telegramPersonMapper.mapToTelegrams(telegramPersonRepository.findAllByGameId(gameId));
  }

  // todo: refactor
  private List<TelegramPerson> storeMissed(List<TelegramPerson> persons, Long chatId) {
    final var stored = telegramPersonMapper
      .mapToTelegrams(telegramPersonRepository.findAllByChatIdAndTelegrams(chatId, persons.stream().map(
        TelegramPerson::getTelegram).toList()));

    final var storedTelegrams = stored.stream()
      .map(TelegramPerson::getTelegram)
      .toList();

    final var toStore = persons.stream()
      .filter(person -> !storedTelegrams.contains(person.getTelegram()))
      .toList();

    if (!toStore.isEmpty()) {
      personService.storeAll(telegramPersonMapper.mapToPersons(toStore));
      telegramPersonRepository.saveAll(toStore);

      return Stream.concat(stored.stream(), toStore.stream()).toList();
    }

    return persons;
  }

  private TelegramPerson saveNew(String telegram, Long chatId) {
    final var telegramPerson = TelegramPerson.builder()
      .id(UUID.randomUUID())
      .telegram(telegram)
      .chatId(chatId)
      .build();

    telegramPersonRepository.save(telegramPerson);
    return telegramPerson;
  }

}
