package pp;

import java.util.ArrayList;

/**
 *
 * @author Osveron
 */
public class Machine {

  class Task {
    private Job job;
    private long startTime;
  }
  
  private ArrayList<Task> workQueue;
}
