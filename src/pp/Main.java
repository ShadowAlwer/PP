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
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        Jobs jobs = new Jobs();
        jobs.getGraph().addAttribute("ui.stylesheet", "url(src/css/styles.css)");

        //Dane testowe MATEK
        ArrayList<String> arr73 = new ArrayList<>();
        arr73.add("J3");
        arr73.add("J7");
        ArrayList<String> arr42 = new ArrayList<>();
        arr42.add("J4");
        arr42.add("J2");
        ArrayList<String> arrP = new ArrayList<>();
        arrP.add("J2");
        arrP.add("J9");
        ArrayList<String> arr283 = new ArrayList<>();
        arr283.add("J2");
        arr283.add("J8");
        arr283.add("J3");
        ArrayList<String> arr9 = new ArrayList<>();
        arr9.add("J9");
        ArrayList<String> arrY = new ArrayList<>();
        arrY.add("J1");
        arrY.add("J5");
        ArrayList<String> arrG = new ArrayList<>();
        arrG.add("J8");
        ArrayList<String> arrW = new ArrayList<>();
        arrW.add("J6");
        jobs.addJob(null,5,"J4");
        jobs.addJob(null, 2, "J7");
        jobs.addJob(null, 2, "J3");        
        jobs.addJob(null, 2, "J9");
        jobs.addJob(arr73,3,"J2");
        jobs.addJob(arr42, 1, "J1");
        jobs.addJob(arr9, 1, "J8");
        jobs.addJob(arr283, 1, "J5");
        jobs.addJob(arrY, 2, "J6");        
        
        //Dane testowe które już były
//        ArrayList<String> arr1 = new ArrayList<>();
//        arr1.add("Job1");
//        ArrayList<String> arr12 = new ArrayList<>();
//        arr12.add("Job1");
//        arr12.add("Job2");
//        ArrayList<String> arr23 = new ArrayList<>();
//        arr23.add("Job2");
//        arr23.add("Job3");
//        ArrayList<String> arr278 = new ArrayList<>();
//        arr278.add("Job2");
//        arr278.add("Job7");
//        arr278.add("Job8");
//        jobs.addJob(null, 5, "Job1");
//        jobs.addJob(arr1, 2, "Job2");
//        jobs.addJob(null, 3, "Job3");
//        jobs.addJob(null, 1, "Job4");
//        jobs.addJob(arr23, 6, "Job5");
//        jobs.addJob(null, 5, "Job6");
//        jobs.addJob(arr23, 2, "Job7");
//        jobs.addJob(arr1, 3, "Job8");
//        jobs.addJob(null, 1, "Job9");
//        jobs.addJob(arr278, 6, "Job10");

        Viewer viewer = jobs.getViewer();
        DefaultView view = (DefaultView) viewer.addDefaultView(false);
        view.setPreferredSize(new Dimension(400, 400));

        EventQueue.invokeLater(() -> {
            MyFrame myFrame = new MyFrame(view, jobs);
        });

        viewer.enableAutoLayout();

    }
}
