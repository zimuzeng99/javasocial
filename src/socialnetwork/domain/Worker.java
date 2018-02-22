package socialnetwork.domain;

import socialnetwork.User;

import java.util.Optional;

public class Worker extends Thread {

  private final Backlog backlog;
  private boolean interrupted = false;

  public Worker(Backlog backlog) {
    this.backlog = backlog;
  }

  @Override
  public void run() {
    while (!interrupted) {
      Optional<Task> taskOptional = backlog.getNextTaskToProcess();
      if (taskOptional.isPresent()) {
        Task task = taskOptional.get();
        process(task);
      }
      else {
        try {
          Thread.sleep(100);
        }
        catch (InterruptedException e) {}
      }
    }
  }

  public void interrupt() {
    this.interrupted = true;
  }

  public void process(Task task) {
    if (task.getCommand() == Task.Command.POST) {
      task.getBoard().addMessage(task.getMessage());
    }
    else if (task.getCommand() == Task.Command.DELETE) {
      if (!task.getBoard().deleteMessage(task.getMessage())) {
        backlog.add(task);
      }
    }
  }
}
