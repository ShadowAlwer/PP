/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.awt.image.ImageObserver.WIDTH;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import org.graphstream.graph.Node;
import org.graphstream.ui.swingViewer.DefaultView;

/**
 *
 * @author Kamil Sowa
 */
public class MyFrame extends JFrame{

    Jobs jobs;
    public MyFrame(DefaultView view, Jobs jobs) {
        
        this.jobs=jobs;
        setPreferredSize(new Dimension(600, 600));
        setLayout(new BorderLayout());
        add(view,BorderLayout.LINE_START);
        JButton myButton = new JButton("Button");
        myButton.addActionListener(e -> System.out.println("Hello, World"));
        add(myButton,BorderLayout.PAGE_END);
        
        
        
        String[] items = {"One", "Two", "Three", "Four", "Five"};
       JComboBox combo = new JComboBox(items);
        JTextField field1 = new JTextField("");
        JTextField field2 = new JTextField("");
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 0;
        panel.add(field1,c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        panel.add(new JLabel("Job Name:"),c);
         c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 1;
        panel.add(field2,c);
         c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 1;
        panel.add(new JLabel("Job Time:"),c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 2;
        panel.add(new JLabel("Depends:"),c);
        
        final JPopupMenu menu = new JPopupMenu();
        ArrayList<JMenuItem> listOfCheckBox=new ArrayList<>();
        
        
         final JButton buttonMultipleChoice = new JButton("List of nodes");
        buttonMultipleChoice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!menu.isVisible()) {
                    Point p = buttonMultipleChoice.getLocationOnScreen();
                    menu.setInvoker(buttonMultipleChoice);
                    menu.setLocation((int) p.getX(),
                            (int) p.getY() + buttonMultipleChoice.getHeight());
                    menu.setVisible(true);
                } else {
                    menu.setVisible(false);
                }

            }
        });
        
        
        for (Node node : jobs.getGraph()) {
            JMenuItem item = new JCheckBoxMenuItem(node.getId());
            menu.add(item);
            listOfCheckBox.add(item);
            item.addActionListener(new OpenAction(menu, buttonMultipleChoice));
        }
       

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 2;
        panel.add(buttonMultipleChoice,c);
        
        
        ArrayList<String> deps=new ArrayList<>();
       
        
        final JButton buttonSumbmit = new JButton("Submit");
        buttonSumbmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                 for (JMenuItem jMenuItem : listOfCheckBox) {
                    JCheckBoxMenuItem checkbox=(JCheckBoxMenuItem) jMenuItem;
                    if (checkbox.getState()){
                        Object[] tab=checkbox.getSelectedObjects();
                        deps.add( (String) tab[0]);
                    }
                }
                jobs.addJob(deps, Long.parseLong( field2.getText()), field1.getText());
                deps.clear();
                
                JMenuItem item = new JCheckBoxMenuItem(field1.getText());
                menu.add(item);
                listOfCheckBox.add(item);
                item.addActionListener(new OpenAction(menu, buttonMultipleChoice));
                
                
            }
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 3;
        panel.add(buttonSumbmit,c);
        
        
        
        getContentPane().add(panel,BorderLayout.LINE_END);
        
        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
     }

      

    private  JScrollPane addingTable(JScrollPane scrollPane) {
        String[] columnNames = {"First Name",
                        "Last Name",
                        "Vegetarian"};


        Job job1 = (Job) jobs.getGraph().getNode("Job1").getAttribute("J");
        Job job2 = (Job) jobs.getGraph().getNode("Job2").getAttribute("J");
        Job job3 = (Job) jobs.getGraph().getNode("Job3").getAttribute("J");
        Object[][] data = {
            {"Job1",job1.getExecutionTime(),
             job1.getDepends()},
            {"Job2",job2.getExecutionTime(),
             job2.getDepends()},
            {"Job3",job3.getExecutionTime(),
             job3.getDepends()},
        };

        JTable table = new JTable(data, columnNames);
        
        scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        return scrollPane;

    }
    
    private static class OpenAction implements ActionListener {

        private final JPopupMenu menu;
        private final JButton button;

        private OpenAction(JPopupMenu menu, JButton button) {
            this.menu = menu;
            this.button = button;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            menu.show(button, 0, button.getHeight());
        }
    }
        
}
        

