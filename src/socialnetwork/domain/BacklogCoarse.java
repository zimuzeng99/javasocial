package socialnetwork.domain;

import javax.swing.text.html.Option;
import java.util.Optional;

public class BacklogCoarse implements Backlog {

  private final int INITIAL_SIZE = 10;
  private Task[] tasks = new Task[INITIAL_SIZE];
  private int size = 0;

  public boolean add(Task task) {
    synchronized (this) {
      if (!contains(task.getId())) {
        if (task.getId() >= tasks.length) {
          resize();
        }
        size++;
        tasks[task.getId()] = task;
        return true;
      }
      else {
        return false;
      }
    }
  }

  public Optional<Task> getNextTaskToProcess() {
    synchronized (this) {
      if (size == 0) {
        return Optional.empty();
      }
      else {
        for (int i = 0; i < tasks.length; i++) {
          if (tasks[i] != null) {
            Task task = tasks[i];
            tasks[i] = null;
            size--;
            return Optional.of(task);
          }
        }

      }
      return Optional.empty();
    }
  }

  private void resize() {
    Task[] newArray = new Task[tasks.length + INITIAL_SIZE];
    for (int i = 0; i < tasks.length; i++) {
      newArray[i] = tasks[i];
    }
  }

  public int numberOfTasksInTheBacklog() {
    return size;
  }



  private boolean contains(int id) {
    return tasks[id] != null;
  }

}
