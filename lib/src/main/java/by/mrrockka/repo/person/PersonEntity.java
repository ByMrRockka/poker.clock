package by.mrrockka.repo.person;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@EqualsAndHashCode
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PersonEntity {
  @NonNull
  UUID id;
  String firstname;
  String lastname;
  String nickname;

  @Builder(builderMethodName = "personBuilder")
  public PersonEntity(@NonNull final UUID id, final String firstname, final String lastname, final String nickname) {
    this.id = id;
    this.firstname = firstname;
    this.lastname = lastname;
    this.nickname = nickname;
  }
}
