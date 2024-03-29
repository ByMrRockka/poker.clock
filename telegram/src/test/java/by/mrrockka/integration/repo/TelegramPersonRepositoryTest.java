package by.mrrockka.integration.repo;

import by.mrrockka.config.PostgreSQLExtension;
import by.mrrockka.domain.TelegramPerson;
import by.mrrockka.repo.person.TelegramPersonEntity;
import by.mrrockka.repo.person.TelegramPersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(PostgreSQLExtension.class)
@SpringBootTest
@ActiveProfiles("integration")
class TelegramPersonRepositoryTest {

  private static final UUID PERSON_ID = UUID.fromString("e2691144-3b1b-4841-9693-fad7af25bba9");
  private static final UUID GAME_ID = UUID.fromString("4a411a12-2386-4dce-b579-d806c91d6d17");
  private static final Long CHAT_ID = 123L;
  private static final String TELEGRAM = "jackas";

  @Autowired
  private TelegramPersonRepository telegramPersonRepository;

  @Test
  void givenPersonIdAndChatIdAndTelegram_whenSaveExecuted_shouldReturnValidEntities() {
    telegramPersonRepository.save(TelegramPerson.telegramPersonBuilder()
                                    .id(PERSON_ID)
                                    .chatId(CHAT_ID)
                                    .nickname(TELEGRAM)
                                    .build());
    assertThat(
      telegramPersonRepository.findAllByChatIdAndTelegrams(CHAT_ID, List.of(TELEGRAM)).stream()
        .map(TelegramPersonEntity::getId))
      .contains(PERSON_ID);
  }

  @Test
  void givenGameIdAndEntries_whenGetByGameIdExecuted_shouldReturnValidTelegramEntities() {
    final var expected = telegramPersonRepository.findAllByChatIdAndTelegrams(CHAT_ID, List.of("kinger", "queen"));
    assertThat(telegramPersonRepository.findAllByGameId(GAME_ID))
      .containsExactlyInAnyOrderElementsOf(expected);
  }
}