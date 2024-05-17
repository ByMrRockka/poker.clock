package by.mrrockka.mapper.finaleplaces;

import by.mrrockka.creator.MessageEntityCreator;
import by.mrrockka.creator.MessageMetadataCreator;
import by.mrrockka.creator.TelegramPersonCreator;
import by.mrrockka.domain.MessageMetadata;
import by.mrrockka.domain.TelegramPerson;
import by.mrrockka.exception.BusinessException;
import by.mrrockka.mapper.exception.InvalidMessageFormatException;
import by.mrrockka.mapper.person.TelegramPersonMapper;
import by.mrrockka.service.exception.FinalPlaceContainsNicknameOfNonExistingPlayerException;
import lombok.Builder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mapstruct.factory.Mappers;
import org.testcontainers.shaded.org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FinalePlacesMessageMapperTest {

  private final TelegramPersonMapper personMapper = Mappers.getMapper(TelegramPersonMapper.class);
  private final FinalePlacesMessageMapper finalePlacesMessageMapper = new FinalePlacesMessageMapper(personMapper);

  @BeforeEach
  void setup() {
    finalePlacesMessageMapper.setBotName("pokerbot");
  }

  private static Stream<Arguments> finalePlacesMessage() {
    return Stream.of(
      Arguments.of(
        """
          /finaleplaces
          1 @mrrockka
          2 @ararat
          3 @andrei
          """
      ),
      Arguments.of(
        """
          /finaleplaces
          1. @mrrockka
          2. @ararat
          3. @andrei
          """
      ),
      Arguments.of(
        """
          /finaleplaces
          1 - @mrrockka
          2 -@ararat
          3-@andrei
          """
      ),
      Arguments.of(
        """
          /finaleplaces
          1 @mrrockka, 2. @ararat,3- @andrei
          """
      ),
      Arguments.of(
        """
          /finaleplaces
          1= @mrrockka, 2. @ararat,
          3: @andrei
          """
      ),
      Arguments.of("/finaleplaces 1 @mrrockka, 2. @ararat,3- @andrei")
    );
  }

  @ParameterizedTest
  @MethodSource("finalePlacesMessage")
  void givenFinalePlacesCommand_whenAttemptToMap_shouldReturnPositionAndNickname(final String command) {
    final var expected = List.of(
      Pair.of(1, "mrrockka"),
      Pair.of(2, "ararat"),
      Pair.of(3, "andrei")
    );

    assertThat(finalePlacesMessageMapper.map(command))
      .isEqualTo(expected);
  }

  private static Stream<Arguments> invalidMessage() {
    return Stream.of(
      Arguments.of("/finaleplaces 1@mrrockka, 2@ararat,3@andrei"),
      Arguments.of("/finaleplaces\n@mrrockka @ararat"),
      Arguments.of("/finaleplaces\n")
    );
  }

  @ParameterizedTest
  @MethodSource("invalidMessage")
  void givenInvalidFinalePlacesMessage_whenMapAttempt_shouldThrowException(final String message) {
    assertThatThrownBy(() -> finalePlacesMessageMapper.map(message))
      .isInstanceOf(InvalidMessageFormatException.class);
  }

  @Builder
  private record FinalePlacesArgument(MessageMetadata metadata, Map<Integer, TelegramPerson> result,
                                      Class<? extends BusinessException> exception) {}

  private static Stream<Arguments> finalePlacesMessageWithMentions() {
    return Stream.of(
      Arguments.of(
        FinalePlacesArgument.builder()
          .metadata(
            MessageMetadataCreator.domain(metadata -> metadata
              .command("""
                         /finaleplaces
                         1 @mrrockka
                         2 @ararat
                         3 @andrei
                         """)
              .entities(List.of(
                MessageEntityCreator.domainMention("@mrrockka"),
                MessageEntityCreator.domainMention("@ararat"),
                MessageEntityCreator.domainMention("@andrei")
              ))
            )).result(Map.of(
            1, TelegramPersonCreator.domain("mrrockka"),
            2, TelegramPersonCreator.domain("ararat"),
            3, TelegramPersonCreator.domain("andrei")
          )).build()
      ),
      Arguments.of(
        FinalePlacesArgument.builder()
          .metadata(
            MessageMetadataCreator.domain(metadata -> metadata
              .command("""
                         /finaleplaces
                         1 - @mrrockka
                         2 -@ararat
                         3-@andrei
                         """)
              .entities(List.of(
                MessageEntityCreator.domainMention("@mrrockka"),
                MessageEntityCreator.domainMention("@ararat"),
                MessageEntityCreator.domainMention("@andrei")
              ))
            )).result(Map.of(
            1, TelegramPersonCreator.domain("mrrockka"),
            2, TelegramPersonCreator.domain("ararat"),
            3, TelegramPersonCreator.domain("andrei")
          )).build()
      ),
      Arguments.of(
        FinalePlacesArgument.builder()
          .metadata(
            MessageMetadataCreator.domain(metadata -> metadata
              .command("""
                         /finaleplaces
                         1 @mrrockka, 2. @ararat,3- @andrei
                         """)
              .entities(List.of(
                MessageEntityCreator.domainMention("@mrrockka"),
                MessageEntityCreator.domainMention("@ararat"),
                MessageEntityCreator.domainMention("@andrei")
              ))
            )).result(Map.of(
            1, TelegramPersonCreator.domain("mrrockka"),
            2, TelegramPersonCreator.domain("ararat"),
            3, TelegramPersonCreator.domain("andrei")
          )).build()
      ),
      Arguments.of(
        FinalePlacesArgument.builder()
          .metadata(
            MessageMetadataCreator.domain(metadata -> metadata
              .command("""
                         /finaleplaces
                         1= @mrrockka, 2. @ararat,
                         3: @andrei
                         """)
              .entities(List.of(
                MessageEntityCreator.domainMention("@mrrockka"),
                MessageEntityCreator.domainMention("@ararat"),
                MessageEntityCreator.domainMention("@andrei")
              ))
            )).result(Map.of(
            1, TelegramPersonCreator.domain("mrrockka"),
            2, TelegramPersonCreator.domain("ararat"),
            3, TelegramPersonCreator.domain("andrei")
          )).build()
      ),
      Arguments.of(
        FinalePlacesArgument.builder()
          .metadata(
            MessageMetadataCreator.domain(metadata -> metadata
              .command("/finaleplaces 1 @mrrockka, 2. @ararat,3- @andrei")
              .entities(List.of(
                MessageEntityCreator.domainMention("@mrrockka"),
                MessageEntityCreator.domainMention("@ararat"),
                MessageEntityCreator.domainMention("@andrei")
              ))
            )).result(Map.of(
            1, TelegramPersonCreator.domain("mrrockka"),
            2, TelegramPersonCreator.domain("ararat"),
            3, TelegramPersonCreator.domain("andrei")
          )).build()
      )
    );
  }

  @ParameterizedTest
  @MethodSource("finalePlacesMessageWithMentions")
  void givenFinalePlacesMetadata_whenAttemptToMap_shouldReturnPositionAndTelegramPerson(
    final FinalePlacesArgument argument) {
    assertThat(finalePlacesMessageMapper.map(argument.metadata()))
      .usingRecursiveComparison()
      .ignoringActualNullFields()
      .ignoringFieldsMatchingRegexes(".*id", ".*chatId")
      .isEqualTo(argument.result());
  }

  private static Stream<Arguments> invalidMessageWithMentions() {
    return Stream.of(
//      case 1 - finale places text does not meet regex places
      Arguments.of(
        FinalePlacesArgument.builder()
          .metadata(
            MessageMetadataCreator.domain(metadata -> metadata
              .command("/finaleplaces 1@mrrockka, 2@ararat,3@andrei")
              .entities(List.of(
                MessageEntityCreator.domainMention("@mrrockka"),
                MessageEntityCreator.domainMention("@ararat"),
                MessageEntityCreator.domainMention("@andrei")
              ))
            )).exception(InvalidMessageFormatException.class)
          .build()),
//      case 2 - finale places text has no positions
      Arguments.of(
        FinalePlacesArgument.builder()
          .metadata(
            MessageMetadataCreator.domain(metadata -> metadata
              .command("/finaleplaces\n@mrrockka @ararat")
              .entities(List.of(
                MessageEntityCreator.domainMention("@mrrockka"),
                MessageEntityCreator.domainMention("@ararat")
              ))
            )).exception(InvalidMessageFormatException.class)
          .build()),
//      case 3 - finale places text is ok but mentions size is less than positions
      Arguments.of(
        FinalePlacesArgument.builder()
          .metadata(
            MessageMetadataCreator.domain(metadata -> metadata
              .command("/finaleplaces 1 @mrrockka, 2 @ararat, 3 @andrei")
              .entities(List.of(
                MessageEntityCreator.domainMention("@mrrockka"),
                MessageEntityCreator.domainMention("@ararat")
              ))
            )).exception(FinalePlacesDoNotMatchMentionsSizeException.class)
          .build()),
//      case 4 - finale places text is ok but mentions size is more than positions
      Arguments.of(
        FinalePlacesArgument.builder()
          .metadata(
            MessageMetadataCreator.domain(metadata -> metadata
              .command("/finaleplaces 1 @mrrockka, 2 @ararat, 3 @andrei")
              .entities(List.of(
                MessageEntityCreator.domainMention("@mrrockka"),
                MessageEntityCreator.domainMention("@ararat"),
                MessageEntityCreator.domainMention("@andrei"),
                MessageEntityCreator.domainMention("@okmasdfoakmsdf")
              ))
            )).exception(FinalePlacesDoNotMatchMentionsSizeException.class)
          .build()),
//      case 5 - finale places text is ok and mentions size is same but mentions contain different nickname
      Arguments.of(
        FinalePlacesArgument.builder()
          .metadata(
            MessageMetadataCreator.domain(metadata -> metadata
              .command("/finaleplaces 1 @mrrockka, 2 @ararat, 3 @andrei")
              .entities(List.of(
                MessageEntityCreator.domainMention("@mrrockka"),
                MessageEntityCreator.domainMention("@ararat"),
                MessageEntityCreator.domainMention("@okmasdfoakmsdf")
              ))
            )).exception(FinalPlaceContainsNicknameOfNonExistingPlayerException.class)
          .build())
    );
  }

  @ParameterizedTest
  @MethodSource("invalidMessageWithMentions")
  void givenInvalidFinalePlacesMetadata_whenMapAttempt_shouldThrowException(final FinalePlacesArgument argument) {
    assertThatThrownBy(() -> finalePlacesMessageMapper.map(argument.metadata()))
      .isInstanceOf(argument.exception());
  }
}