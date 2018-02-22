package socialnetwork.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BoardCoarse implements Board {

  private final int INITIAL_SIZE = 50;
  private Message[] messages = new Message[INITIAL_SIZE];
  private int size = 0;

  public boolean addMessage(Message message) {
    synchronized (this) {
      while (message.getMessageId() >= messages.length) {
        resize();
      }

      if (!contains(message.getMessageId())) {

        size++;
        messages[message.getMessageId()] = message;
        return true;
      }
      else {
        return false;
      }
    }
  }

  private void resize() {
    Message[] newArray = new Message[2 * messages.length];
    for (int i = 0; i < messages.length; i++) {
      newArray[i] = messages[i];
    }
    messages = newArray;
  }

  public int size() {
    return size;
  }

  private boolean contains(int id) {
    return messages[id] != null;
  }

  public boolean deleteMessage(Message message) {
    synchronized (this) {
      if (messages[message.getMessageId()] != null) {
        messages[message.getMessageId()] = null;
        size--;
        return true;
      }
      else {
        return false;
      }
    }
  }

  public List<Message> getBoardSnapshot() {
    synchronized (this) {
      List<Message> list = new ArrayList<>();
      for (int i = messages.length - 1; i >= 0; i--) {
        if (messages[i] != null) {
          list.add(messages[i]);
        }
      }

      return list;
    }
  }
}
