package by.mrrockka.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CommandRegexConstants {
  public static final String TELEGRAM_NAME_REGEX = "@([A-z0-9_-]{5,})";
  public static final String DELIMITER_REGEX = "([. :\\-=]{1,3})";
  public static final String USERNAME_REPLACE_REGEX = "@me([\n \t\r\b]*)";
  public static final String BOT_NAME_REPLACE_REGEX = "@pokerclc_bot";
}