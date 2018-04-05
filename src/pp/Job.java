package pp;

import java.util.ArrayList;

/**
 *
 * @author Osveron
 */
public class Job {
  private final ArrayList<Job> depends;
  private final long executionTime;
  
  public Job(ArrayList<Job> d, long e) {
    this.depends = d;
    this.executionTime = e;
  }
  
  public ArrayList<Job> getDepends() {
    return this.depends;
  }
  
  public long getExecutionTime() {
    return this.executionTime;
  }
  
  public void addDependency(Job job){
   depends.add(job);    
  }
  
  public void removeDependency(Job job){
   depends.remove(job);
  }
  
}
