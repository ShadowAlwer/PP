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
import java.awt.PopupMenu;
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
    JComboBox comboBoxToDelete;
    JTextField fieldJobName = new JTextField("");
    JTextField fieldJobTime = new JTextField("");
    private JComboBox comboBoxFirstNode;
    private JComboBox comboBoxSecondNode;
    public MyFrame(DefaultView view, Jobs jobs) {
        
        this.jobs=jobs;
        setPreferredSize(new Dimension(1000, 1000));
        setLayout(new BorderLayout());
        add(view,BorderLayout.LINE_START);
        JButton myButton = new JButton("Button");
        myButton.addActionListener(e -> System.out.println("Hello, World"));
        add(myButton,BorderLayout.PAGE_END);
        
        JPanel panel3 = new JPanel(new GridBagLayout());
        JPanel panel4 = new JPanel(new GridBagLayout());
         JPanel panelX1 = new JPanel(new GridBagLayout());
        
         JPanel panelX2 = new JPanel(new GridBagLayout());
         
         
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        panel3.add(setAddingNode(),c);
        
        
        panelX1.add(new JLabel("--------------------------------------------------------"));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 1;
        panel3.add(panelX1,c);
        
        
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 2;
        panel3.add(setDeletingNode(),c);
        
        
        panelX2.add(new JLabel("--------------------------------------------------------"));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 3;
        panel3.add(panelX2,c);
        
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 4;
        panel3.add(addNewEdge(),c);
        
        
        getContentPane().add(panel3,BorderLayout.LINE_END);
        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
     }

    private JPanel setAddingNode() {
        fieldJobName = new JTextField("");
        fieldJobTime = new JTextField("");
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        
        
        
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        panel.add(new JLabel("Adding New Nodes:"),c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 1;
        panel.add(fieldJobName,c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 1;
        panel.add(new JLabel("Job Name:"),c);
         c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 2;
        panel.add(fieldJobTime,c);
         c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 2;
        panel.add(new JLabel("Job Time:"),c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 3;
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
        c.gridy = 3;
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
                jobs.addJob(deps, Long.parseLong( fieldJobTime.getText()), fieldJobName.getText());
                deps.clear();
                
                JMenuItem item = new JCheckBoxMenuItem(fieldJobName.getText());
                menu.add(item);
                listOfCheckBox.add(item);
                item.addActionListener(new OpenAction(menu, buttonMultipleChoice));
                comboBoxToDelete.addItem(fieldJobName.getText());
                comboBoxFirstNode.addItem(fieldJobName.getText());
                comboBoxSecondNode.addItem(fieldJobName.getText());
                
            }
        });
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 4;
        panel.add(buttonSumbmit,c);
        
       // getContentPane().add(panel,BorderLayout.LINE_END);
        
        return panel;
        
    }

    private JPanel setDeletingNode() {
        JPanel panel2 = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        
        
        c.fill = GridBagConstraints.SOUTH;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 5;
        panel2.add(new JLabel("Remove Node:"),c);
        
        ArrayList<String>  jobsNames=new ArrayList<>();
        for (Node node : jobs.getGraph()) {
            jobsNames.add(node.getId());
        }
        comboBoxToDelete = new JComboBox(jobsNames.toArray());
        //petList.setSelectedIndex(4);
        c.fill = GridBagConstraints.SOUTH;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 5;
        panel2.add(comboBoxToDelete,c);
        
        final JButton buttonDeleteNode= new JButton("Delete");
        buttonDeleteNode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               
                jobs.removeJob( (String) comboBoxToDelete.getSelectedItem());
                
            }
        });
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 6;
        panel2.add(buttonDeleteNode,c);
        return panel2;
        
       // getContentPane().add(panel2,BorderLayout.CENTER);
    }

    private JPanel addNewEdge() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        c.fill = GridBagConstraints.SOUTH;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        panel.add(new JLabel("Adding New Edge:"),c);
        
        ArrayList<String>  jobsNames=new ArrayList<>();
        for (Node node : jobs.getGraph()) {
            jobsNames.add(node.getId());
        }
        comboBoxFirstNode = new JComboBox(jobsNames.toArray());
        comboBoxSecondNode = new JComboBox(jobsNames.toArray());
        //petList.setSelectedIndex(4);
        c.fill = GridBagConstraints.SOUTH;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 0;
        panel.add(comboBoxFirstNode,c);
        c.fill = GridBagConstraints.SOUTH;
        c.weightx = 0.5;
        c.gridx = 2;
        c.gridy = 0;
        panel.add(comboBoxSecondNode,c);
        
        final JButton buttonAddEdge= new JButton("Add Edge");
        buttonAddEdge.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               
                jobs.addEdge((String) comboBoxFirstNode.getSelectedItem(),(String) comboBoxSecondNode.getSelectedItem());
                
            }
        });
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 1;
        panel.add(buttonAddEdge,c);
        
        
        final JButton buttonRemoveEdge= new JButton("Remove Edge");
        buttonRemoveEdge.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               
                jobs.removeEdge((String) comboBoxFirstNode.getSelectedItem(),(String) comboBoxSecondNode.getSelectedItem());
                
            }
        });
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 2;
        panel.add(buttonRemoveEdge,c);
        
        
        
        return panel;
        
        
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
        

