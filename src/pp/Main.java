package pp;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
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
    Jobs jobs = new Jobs();
    
    ArrayList<String> arr = new ArrayList();
    arr.add("Job1");
    jobs.addJob(null, 5, "Job1");    
    jobs.addJob(arr, 10, "Job2");
    jobs.addJob(arr, 15, "Job3");
    
    Viewer viewer = jobs.getViewer();

    JFrame myJFrame = new JFrame();
    myJFrame.setPreferredSize(new Dimension(600, 600));

    DefaultView view = (DefaultView) viewer.addDefaultView(false);
    view.setPreferredSize(new Dimension(400, 400));
    myJFrame.setLayout(new FlowLayout());
    myJFrame.add(view);

    JButton myButton = new JButton("Button");
    myButton.addActionListener(e -> System.out.println("Hello, World"));
    myJFrame.add(myButton);

    myJFrame.pack();
    myJFrame.setVisible(true);
    myJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    viewer.enableAutoLayout();
  }

}
