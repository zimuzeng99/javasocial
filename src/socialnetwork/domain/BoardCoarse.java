package socialnetwork.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BoardCoarse implements Board {

  private final int INITIAL_SIZE = 10;
  private Message[] messages = new Message[INITIAL_SIZE];
  private int size = 0;

  public boolean addMessage(Message message) {
    synchronized (this) {
      if (!contains(message.getMessageId())) {
        if (message.getMessageId() >= messages.length) {
          resize();
        }
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
    Message[] newArray = new Message[messages.length + INITIAL_SIZE];
    for (int i = 0; i < messages.length; i++) {
      newArray[i] = messages[i];
    }
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
