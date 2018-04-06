package pp;

import com.bluewares.BarChart;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.util.ArrayList;
import org.graphstream.ui.swingViewer.DefaultView;
import org.graphstream.ui.view.Viewer;

/**
 *
 * @author Osveron
 */
public class Main {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    System.setProperty("gs.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
    Jobs jobs = new Jobs();
    jobs.getGraph().addAttribute("ui.stylesheet", "url(src/css/styles.css)");

    ArrayList<String> arr = new ArrayList();
    arr.add("Job1");

    jobs.addJob(null, 5, "Job1");
    jobs.addJob(arr, 10, "Job2"); 
    jobs.addJob(arr, 15, "Job3");

    jobs.addDependency("Job2", "Job3");
    jobs.removeDependency("Job2", "Job3");

    Viewer viewer = jobs.getViewer();
    DefaultView view = (DefaultView) viewer.addDefaultView(false);
    view.setPreferredSize(new Dimension(400, 400));

    
    
    ArrayList<Machine> machines = new ArrayList<>();
    Machine machine=new Machine();
        machine.addTask(new Job(null, 5, "Job1"), 1);
        machine.addTask(new Job(null, 3, "Job2"), 8);
        machines.add(machine);
      machine=new Machine();
        machine.addTask(new Job(null, 3, "Job3"), 1);
        machine.addTask(new Job(null, 4, "Job4"), 4);
        machine.addTask(new Job(null, 4, "Job4"), 8);
        machines.add(machine);
    
    
    
    
    EventQueue.invokeLater(new Runnable() {
      @Override
      public void run() {
        new MyFrame(view, jobs,machines);
      }
    });

    viewer.enableAutoLayout();

  }
}
