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

        ArrayList<String> arr1 = new ArrayList<>();
        arr1.add("Job1");
        ArrayList<String> arr12 = new ArrayList<>();
        arr12.add("Job1");
        arr12.add("Job2");
        ArrayList<String> arr23 = new ArrayList<>();
        arr23.add("Job2");
        arr23.add("Job3");
        ArrayList<String> arr278 = new ArrayList<>();
        arr278.add("Job2");
        arr278.add("Job7");
        arr278.add("Job8");

        jobs.addJob(null, 5, "Job1");
        jobs.addJob(arr1, 2, "Job2");
        jobs.addJob(null, 3, "Job3");
        jobs.addJob(null, 1, "Job4");
        jobs.addJob(arr23, 6, "Job5");
        jobs.addJob(null, 5, "Job6");
        jobs.addJob(arr23, 2, "Job7");
        jobs.addJob(arr1, 3, "Job8");
        jobs.addJob(null, 1, "Job9");
        jobs.addJob(arr278, 6, "Job10");

        Viewer viewer = jobs.getViewer();
        DefaultView view = (DefaultView) viewer.addDefaultView(false);
        view.setPreferredSize(new Dimension(400, 400));

        EventQueue.invokeLater(() -> {
            MyFrame myFrame = new MyFrame(view, jobs);
        });

        viewer.enableAutoLayout();

    }
}
