package by.mrrockka.repo.person;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import static by.mrrockka.repo.person.PersonColumnNames.*;

@Component
public class PersonEntityRowMapper implements RowMapper<PersonEntity> {
  @Override
  public PersonEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
    return PersonEntity.builder()
      .id(UUID.fromString(rs.getString(ID)))
      .chatId(rs.getString(CHAT_ID))
      .telegram(rs.getString(TELEGRAM))
      .firstname(rs.getString(FIRST_NAME))
      .lastname(rs.getString(LAST_NAME))
      .build();

  }

}

