package by.mrrockka.creator;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.EntityType;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;

import java.util.List;
import java.util.function.Consumer;

import static java.util.Objects.nonNull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageCreator {


  public static Message message(String text) {
    return message((message) -> message.setText(text));
  }

  public static Message message(Consumer<Message> messageConsumer) {
    final var entity = new MessageEntity();
    entity.setOffset(0);
    entity.setType(EntityType.BOTCOMMAND);

    final var message = new Message();
    message.setChat(ChatCreator.chat());
    message.setEntities(List.of(entity));
    message.setFrom(UserCreator.user());

    if (nonNull(messageConsumer)) {
      messageConsumer.accept(message);
    }

    return message;
  }

}