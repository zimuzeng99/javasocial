package socialnetwork;

import socialnetwork.domain.Board;
import socialnetwork.domain.BoardCoarse;
import socialnetwork.domain.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class User extends Thread {

  private static final AtomicInteger nextId = new AtomicInteger(0);

  protected final SocialNetwork socialNetwork;
  private final int id;
  private final String name;
  private final Random random = new Random();
  private final Board board;

  public User(String username, SocialNetwork socialNetwork) {
    this.name = username;
    this.id = User.nextId.getAndIncrement();
    this.socialNetwork = socialNetwork;
    board = new BoardCoarse();
    socialNetwork.register(this, board);
  }

  public Board getBoard() {
    return board;
  }

  public int getUserId() {
    return id;
  }

  @Override
  public void run() {
    synchronized (socialNetwork) {
      Set<User> allUsers = socialNetwork.getAllUsers();
      List<User> recipients = new ArrayList<>();
      for (User user : allUsers) {
        if (random.nextInt() % 2 == 0) {
          recipients.add(user);
        }
      }

      socialNetwork.postMessage(this, recipients, "Hello my friends!");

      List<Message> messages = socialNetwork.userBoard(this).getBoardSnapshot();
      for (Message msg : messages) {
        if (random.nextInt() % 2 == 0) {
          socialNetwork.deleteMessage(msg);
        }
      }
    }

  }

  private void lookAround() {

  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", name='" + name + '\'' +
        '}';
  }

  @Override
  public int hashCode() {
    return id;
  }
}
