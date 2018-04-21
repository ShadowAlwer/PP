package pp;

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

        ArrayList<String> arr = new ArrayList<>();
        arr.add("Job1");

        jobs.addJob(null, 5, "Job1");
        jobs.addJob(arr, 2, "Job2");
        jobs.addJob(arr, 3, "Job3");
        jobs.addJob(null, 1, "Job4");
        jobs.addJob(arr, 6, "Job5");

        Viewer viewer = jobs.getViewer();
        DefaultView view = (DefaultView) viewer.addDefaultView(false);
        view.setPreferredSize(new Dimension(400, 400));

        ArrayList<Machine> machines = new ArrayList<>();
        Machine machine = new Machine();
        machines.add(machine);
        machine = new Machine();
        machines.add(machine);
        machine = new Machine();
        machines.add(machine);
        Scheduler sheduler = new Scheduler();
        sheduler.simpleAlgorithm(jobs, machines);

        EventQueue.invokeLater(() -> {
            MyFrame myFrame = new MyFrame(view, jobs, machines);
        });

        viewer.enableAutoLayout();

    }
}
