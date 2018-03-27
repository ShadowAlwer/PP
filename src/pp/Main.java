package pp;

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import org.graphstream.graph.implementations.DefaultGraph;
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
    final DefaultGraph graph = new DefaultGraph("Graph");
    graph.setStrict(false);
    Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
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

    graph.addNode("A");
    graph.addNode("B");
    graph.addNode("C");
    graph.addEdge("AB", "A", "B");
    graph.addEdge("BC", "B", "C");
    graph.addEdge("CA", "C", "A");
  }

}
