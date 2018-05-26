package pp;

import java.util.ArrayList;

/**
 *
 * @author Osveron
 */
public class Machine {

    class Task {

        public final Job job;
        public final long startTime;

        Task(Job j, long s) {
            this.job = j;
            this.startTime = s;            
        }
    }

    private final ArrayList<Task> workQueue;
    private final ArrayList<String> properties;
  
    Machine() {
        workQueue = new ArrayList<>();
        this.properties=new ArrayList<>();
    }

    public ArrayList<Task> getWorkQueue() {
        return workQueue;
    }

    public void addTask(Job job, long time) {
        workQueue.add(new Task(job, time));
    }

    public long getEndTime() {
        if (workQueue.isEmpty()) {
            return 0;
        }
        Task last = workQueue.get(workQueue.size() - 1);
        return last.startTime + last.job.getExecutionTime();
    }
    
    public void addProperty(String property){
        properties.add(property);
        
     }
    
    public void removeProperty(String property){
        properties.remove(property);
    }
    
    public ArrayList<String> getProperties(){
        return properties;
    }
    
}
