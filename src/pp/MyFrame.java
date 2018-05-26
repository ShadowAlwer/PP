package pp;

import com.bluewares.Axis;
import com.bluewares.Bar;
import com.bluewares.BarChart;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import org.graphstream.graph.Node;
import org.graphstream.ui.swingViewer.DefaultView;
import static pp.ConstansInterface.NUMBER_OF_TICKS;


/**
 *
 * @author Kamil Sowa
 */
public class MyFrame extends JFrame implements ConstansInterface{

    private Scheduler scheduler;
    private Jobs jobs;
    private JComboBox<String> comboBoxToDelete;
    private JComboBox<String> comboBoxToSelect;
    private JComboBox<String> comboBoxAlgorithm;
    private Job choosenJob;
    private JTextField fieldJobName = new JTextField("");
    private JTextField fieldJobTime = new JTextField("");
    private JTextField fieldNumberOfMachines = new JTextField("");
    private JComboBox<String> comboBoxFirstNode;
    private JComboBox<String> comboBoxSecondNode;
    private JPopupMenu menu;
    private ArrayList<JMenuItem> listOfCheckBox;
    private JFrame me = this;
    private BarChart barChart;
    private JTextArea infoTextArea;
    private JScrollPane scroll;
    private JTextField fieldJobMachineProperties;
    private JComboBox<String> comboBoxProperties;
    private JRadioButton radioButtonMachines;
    private JRadioButton radioButtonJobs;
    private ButtonGroup buttonGroupProperties;
    

    public MyFrame(DefaultView view, Jobs jobs) {
        this.jobs = jobs;
        this.scheduler = new Scheduler(jobs.getJobs(), 3);
        setPreferredSize(new Dimension(1500, 1000));
        setLayout(new BorderLayout());
        add(view, BorderLayout.LINE_START);

        JPanel panel3 = new JPanel(new GridBagLayout());        
        JPanel panelX1 = new JPanel(new GridBagLayout());
        JPanel panelX2 = new JPanel(new GridBagLayout());
        JPanel panelX3 = new JPanel(new GridBagLayout());
        JPanel panelX4 = new JPanel(new GridBagLayout());
        JPanel panelInfo = new JPanel(new GridBagLayout());
        JPanel panelAlgorithm = new JPanel(new GridBagLayout());
        

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.LINE_END;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        panel3.add(setAddingNode(), c);

        panelX1.add(new JLabel("--------------------------------------------------------"));
        c.fill = GridBagConstraints.LINE_END;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 1;
        panel3.add(panelX1, c);

        c.fill = GridBagConstraints.LINE_END;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 2;
        panel3.add(setDeletingNode(), c);

        panelX2.add(new JLabel("--------------------------------------------------------"));
        c.fill = GridBagConstraints.LINE_END;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 3;
        panel3.add(panelX2, c);

        c.fill = GridBagConstraints.LINE_END;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 4;
        panel3.add(addNewEdge(), c);

        panelX3.add(new JLabel("--------------------------------------------------------"));
        c.fill = GridBagConstraints.LINE_END;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 5;
        panel3.add(panelX3, c);
        //
        barChart = showBarCharts(scheduler.getMachines());
 

        c.fill = GridBagConstraints.LINE_END;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 6;
        panel3.add(setMachinesNumberInScheduler(), c);

        panelX4.add(new JLabel("--------------------------------------------------------"));
        c.fill = GridBagConstraints.LINE_END;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 7;
        panel3.add(panelX4,c);
        
        c.fill = GridBagConstraints.LINE_END;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 8;
        panel3.add(setJobMachinePropertieses(),c);
        
        panelInfo.add(new JLabel("--------------------------------------------------------"));
        c.fill = GridBagConstraints.LINE_END;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 9;
        panel3.add(panelInfo,c);
        
         c.fill = GridBagConstraints.LINE_END;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 10;
        panel3.add(informationAboutJobs(), c);
        // Algorithm panel
         panelAlgorithm.add(new JLabel("--------------------------------------------------------"));
        c.fill = GridBagConstraints.LINE_END;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 11;
        panel3.add(panelAlgorithm,c);
        
         c.fill = GridBagConstraints.LINE_END;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 12;
        panel3.add(algorithmPicker(), c);
       
        getContentPane().add(showGraphButtonTask(), BorderLayout.PAGE_END);

        getContentPane().add(panel3, BorderLayout.LINE_END);
              
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
        c.gridwidth = 2;
        c.gridy = 0;
        panel.add(newLabelWithFont("Adding New Node: ", 18), c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridwidth = 1;
        c.gridy = 1;
        panel.add(fieldJobName, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 1;
        panel.add(new JLabel("Job Name:"), c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 2;
        panel.add(fieldJobTime, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 2;
        panel.add(new JLabel("Job Time:"), c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 3;
        panel.add(new JLabel("Depends:"), c);

        menu = new JPopupMenu();
        listOfCheckBox = new ArrayList<>();

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
        panel.add(buttonMultipleChoice, c);

        ArrayList<String> deps = new ArrayList<>();

        final JButton buttonSumbmit = new JButton("Submit");
        buttonSumbmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                for (JMenuItem jMenuItem : listOfCheckBox) {
                    JCheckBoxMenuItem checkbox = (JCheckBoxMenuItem) jMenuItem;
                    if (checkbox.getState()) {
                        Object[] tab = checkbox.getSelectedObjects();
                        deps.add((String) tab[0]);
                    }
                }

                try {
                    jobs.addJob(deps, Long.parseLong(fieldJobTime.getText()), fieldJobName.getText());
                    JMenuItem item = new JCheckBoxMenuItem(fieldJobName.getText());
                    menu.add(item);
                    listOfCheckBox.add(item);
                    item.addActionListener(new OpenAction(menu, buttonMultipleChoice));

                    comboBoxToDelete.addItem(fieldJobName.getText());
                    comboBoxFirstNode.addItem(fieldJobName.getText());
                    comboBoxSecondNode.addItem(fieldJobName.getText());
                } catch (NumberFormatException ex) {
                    System.err.println("NumberFormatException " + ex.getMessage());

                    JOptionPane.showMessageDialog(me,
                            "Job time must be a long number for example:1 ",
                            "Inane error",
                            JOptionPane.ERROR_MESSAGE);

                } finally {
                    deps.clear();
                }

            }
        });

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 0;
        c.gridwidth = 2;
        c.gridy = 4;
        panel.add(buttonSumbmit, c);

        // getContentPane().add(panel,BorderLayout.LINE_END);
        return panel;

    }

    private JPanel setDeletingNode() {
        JPanel panel2 = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.SOUTH;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridwidth = 2;
        c.gridy = 0;
        panel2.add(newLabelWithFont("Remove Node: ", 18), c);

        ArrayList<String> jobsNames = new ArrayList<>();
        for (Node node : jobs.getGraph()) {
            jobsNames.add(node.getId());
        }

        comboBoxToDelete = new JComboBox<String>(jobsNames.toArray(new String[jobsNames.size()]));
        //petList.setSelectedIndex(4);
        c.fill = GridBagConstraints.SOUTH;
        c.weightx = 0.5;
        c.gridx = 2;
        c.gridwidth = 1;
        c.gridy = 0;
        panel2.add(comboBoxToDelete, c);

        final JButton buttonDeleteNode = new JButton("Delete");
        buttonDeleteNode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                jobs.removeJob((String) comboBoxToDelete.getSelectedItem());

                menu.remove(comboBoxToDelete.getSelectedIndex());
                listOfCheckBox.remove(comboBoxToDelete.getSelectedIndex());

                comboBoxFirstNode.removeItem(comboBoxToDelete.getSelectedItem());
                comboBoxSecondNode.removeItem(comboBoxToDelete.getSelectedItem());
                comboBoxToSelect.removeItem(comboBoxToDelete.getSelectedItem());
                comboBoxToDelete.removeItem(comboBoxToDelete.getSelectedItem());
                

            }
        });

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridwidth = 2;
        c.gridy = 1;
        panel2.add(buttonDeleteNode, c);
        return panel2;

        // getContentPane().add(panel2,BorderLayout.CENTER);
    }

    private JPanel addNewEdge() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.WEST;
        c.weightx = 0.5;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 0;
        panel.add(newLabelWithFont("Adding New Edge: ", 18), c);

        ArrayList<String> jobsNames = new ArrayList<>();
        for (Node node : jobs.getGraph()) {
            jobsNames.add(node.getId());
        }

        // Arrays.copyOf(jobs.getGraph(), jobsNames.size(), String[].class);
        comboBoxFirstNode = new JComboBox<String>(jobsNames.toArray(new String[jobsNames.size()]));
        comboBoxSecondNode = new JComboBox<String>(jobsNames.toArray(new String[jobsNames.size()]));
        //petList.setSelectedIndex(4);
        c.fill = GridBagConstraints.LINE_START;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridwidth = 1;
        c.gridy = 1;
        panel.add(comboBoxFirstNode, c);
        c.fill = GridBagConstraints.LINE_START;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 1;
        panel.add(new JLabel("   Depends on:  "), c);
        c.fill = GridBagConstraints.SOUTH;
        c.weightx = 0.5;
        c.gridx = 2;
        c.gridy = 1;
        panel.add(comboBoxSecondNode, c);

        final JButton buttonAddEdge = new JButton("Add Edge");
        buttonAddEdge.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                jobs.addDependency((String) comboBoxFirstNode.getSelectedItem(), (String) comboBoxSecondNode.getSelectedItem());

            }
        });

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridwidth = 3;
        c.gridy = 2;
        panel.add(buttonAddEdge, c);

        final JButton buttonRemoveEdge = new JButton("Remove Edge");
        buttonRemoveEdge.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                jobs.removeDependency((String) comboBoxFirstNode.getSelectedItem(), (String) comboBoxSecondNode.getSelectedItem());

            }
        });

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridwidth = 3;
        c.gridy = 3;
        panel.add(buttonRemoveEdge, c);

        return panel;

    }

    private BarChart showBarCharts(ArrayList<Machine> machines) {

        ArrayList<ArrayList<Bar>> values = new ArrayList<ArrayList<Bar>>();
        int i = 0;
        int j = 0;
        for (Machine machine1 : machines) {
            values.add(new ArrayList<Bar>());
            for (Machine.Task task : machine1.getWorkQueue()) {
                Color color = (j % 2 == 0) ? Color.RED : Color.BLUE;
                values.get(i).add(new Bar((int) task.job.getExecutionTime(), color, task.job.getID(), (int) task.startTime));
                j++;
            }
            i++;
            j = 0;
        }

        int primaryIncrements = 2;
        int secondaryIncrements = 1;
        int tertiaryIncrements = 1;
        Axis yAxis = new Axis(15, 0, primaryIncrements, secondaryIncrements,
                tertiaryIncrements);

        return new BarChart(values, yAxis);
    }

    private Component newLabelWithFont(String remove_Node_, int i) {
        JLabel jLabel = new JLabel();
        jLabel.setText(remove_Node_);
        jLabel.setFont(new Font("Callibri", Font.PLAIN, i));
        return jLabel;
    }

    private JButton showGraphButtonTask() {
        barChart = showBarCharts(scheduler.getMachines());
        //getContentPane().add(barChart, BorderLayout.CENTER);

        final JButton prz = new JButton("Show graph");
        prz.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ALGORITHM pickedAlgorithm = ALGORITHM.valueOf(comboBoxAlgorithm.getSelectedItem().toString());
                switch (pickedAlgorithm) {
                    case FIRST:
                        scheduler.Algorithm1();
                        break;
                    case SECOND:
                        scheduler.Algorithm2();
                        break;
                    case THIRD:
                        scheduler.Algorithm3();
                        break;
                    case FOURTH:
                        scheduler.Algorithm4();
                        break;
                }
                        barChart.setBars(getValuesFromMachines(scheduler.getMachines()));
                        long height = calculateNeededHeightofXAxis(scheduler.getMachines());
                        barChart.setxAxis(setAxis(height));
                        barChart.repaint();
                        getContentPane().add(barChart, BorderLayout.CENTER);
                        SwingUtilities.updateComponentTreeUI(me);
            }

            private ArrayList<ArrayList<Bar>> getValuesFromMachines(ArrayList<Machine> machines) {
                ArrayList<ArrayList<Bar>> values = new ArrayList<ArrayList<Bar>>();
                int i = 0;
                int j = 0;
                for (Machine machine1 : machines) {
                    values.add(new ArrayList<Bar>());
                    for (Machine.Task task : machine1.getWorkQueue()) {
                        Color color = (j % 2 == 0) ? Color.RED : Color.BLUE;
                        values.get(i).add(new Bar((int) task.job.getExecutionTime(), color, task.job.getID(), (int) task.startTime));
                        j++;
                    }
                    i++;
                    j = 0;
                }
                return values;
            }

            private Axis setAxis(long height) {
                height += 2;
                int primaryIncrements = (int) (height / NUMBER_OF_TICKS) ==0 ? 1:(int) (height / NUMBER_OF_TICKS) ;
                int secondaryIncrements = (int) (height / NUMBER_OF_TICKS * 2) ==0 ? 1:(int) (height / NUMBER_OF_TICKS*2);
                int tertiaryIncrements = (int) (height / NUMBER_OF_TICKS * 2)==0 ? 1:(int) (height / NUMBER_OF_TICKS*2);
                Axis yAxis = new Axis((int) height, 0, primaryIncrements, secondaryIncrements,
                        tertiaryIncrements);
                return yAxis;
            }

            private long calculateNeededHeightofXAxis(ArrayList<Machine> machines) {

                Comparator<? super Machine> comparator = (Machine o1, Machine o2) -> {
                    if (o1.getEndTime() < o2.getEndTime()) {
                        return -1;
                    } else {
                        return 1;
                    }
                };

                return machines
                        .stream()
                        .max(comparator)
                        .get()
                        .getEndTime();

            }

        });
        return prz;
    }

    private Component setMachinesNumberInScheduler() {
        fieldNumberOfMachines = new JTextField("");
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridwidth = 1;
        c.gridy = 0;
        panel.add(newLabelWithFont("Write number of machines: ", 18), c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridwidth = 1;
        c.gridy = 1;
        panel.add(fieldNumberOfMachines, c);

        final JButton buttonSetNumberOfMachines = new JButton("Submit");
        buttonSetNumberOfMachines.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (Integer.parseInt(fieldNumberOfMachines.getText()) > 10 || Integer.parseInt(fieldNumberOfMachines.getText()) < 1) {
                        throw new NumberFormatException();
                    }
                    scheduler.setMachinesCount(Integer.parseInt(fieldNumberOfMachines.getText()));

                } catch (NumberFormatException ex) {
                    System.err.println("NumberFormatException " + ex.getMessage());

                    JOptionPane.showMessageDialog(me,
                            "Machine count must be a number from 1 to 10 ",
                            "Inane error",
                            JOptionPane.ERROR_MESSAGE);

                }

            }
        });

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridwidth = 2;
        c.gridy = 2;
        panel.add(buttonSetNumberOfMachines, c);

        return panel;
    }
    
     private Component setJobMachinePropertieses() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        fieldJobMachineProperties= new JTextField(""); 
        radioButtonJobs=new JRadioButton("Jobs");
        radioButtonJobs.setSelected(true);
        radioButtonMachines= new JRadioButton("Machines");
               
        radioButtonJobs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
               comboBoxProperties.removeAllItems();
               for(String n: getJobsNames())
               comboBoxProperties.addItem(n);
            }
        });
        
        radioButtonMachines.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                comboBoxProperties.removeAllItems();
                for(int i=0; i<scheduler.getMachines().size(); i++)
                    comboBoxProperties.addItem("M"+(i+1));            
            }
        });
        
              
         final JButton buttonSubmitNode = new JButton("Submit");
        buttonSubmitNode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                
                if(!fieldJobMachineProperties.getText().equals("")){
               //add Properties
               if(radioButtonJobs.isEnabled()){
               //add to job
               jobs.findJobByID((String)comboBoxProperties.getSelectedItem()).addProperty(fieldJobMachineProperties.getText());
               }
               else{
                    scheduler.getMachines().get(comboBoxProperties.getSelectedIndex()).addProperty(fieldJobMachineProperties.getText());
               }
            }}
        });
             
        comboBoxProperties= new JComboBox<>(getJobsNames());
        
        buttonGroupProperties= new ButtonGroup();
        buttonGroupProperties.add(radioButtonJobs);
        buttonGroupProperties.add(radioButtonMachines);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridwidth = 1;
        c.gridy = 0;
        panel.add(newLabelWithFont("Add properties to:  ", 18), c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridwidth = 1;
        c.gridy = 1;
        panel.add(radioButtonJobs,c);
        
         c.fill = GridBagConstraints.EAST;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridwidth = 1;
        c.gridy = 1;
        panel.add(radioButtonMachines,c);
        
        c.fill = GridBagConstraints.LINE_START;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridwidth = 2;
        c.gridy = 2;
        panel.add(comboBoxProperties,c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridwidth = 2;
        c.gridy = 3;
        panel.add(fieldJobMachineProperties,c);
        
         c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridwidth = 2;
        c.gridy = 4;
        panel.add(buttonSubmitNode,c);
        
        
        return panel;
    }

    private Component informationAboutJobs() {
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        infoTextArea = new JTextArea(3, 15);
        infoTextArea.setFont(new Font(Font.SANS_SERIF,Font.PLAIN, 18));
        infoTextArea.setEditable(false);
        infoTextArea.setLineWrap(true);
        infoTextArea.setWrapStyleWord(true);
        scroll = new JScrollPane(infoTextArea);
        
        c.fill = GridBagConstraints.SOUTH;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridwidth = 2;
        c.gridy = 0;
        panel.add(newLabelWithFont("Select node: ", 18), c);

        comboBoxToSelect = new JComboBox<>(getJobsNames());
        //petList.setSelectedIndex(4);
        c.fill = GridBagConstraints.SOUTH;
        c.weightx = 0.5;
        c.gridx = 2;
        c.gridwidth = 1;
        c.gridy = 0;
        panel.add(comboBoxToSelect, c);

        final JButton buttonSubmitNode = new JButton("Submit");
        buttonSubmitNode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               choosenJob = jobs.findJobByID((String) comboBoxToSelect.getSelectedItem());
                if(choosenJob!=null)
                {
                infoTextArea.setText("");
                infoTextArea.append("Job ID: "+choosenJob.getID()+"\n");
                infoTextArea.append("Time: "+choosenJob.getExecutionTime()+"\n");
                
                if(choosenJob.getDependsName().toArray().length == 0) {
                                          
                        infoTextArea.append("Dependencies: None");
                    } else {                       
                        infoTextArea.append("Dependencies: ");
                        for(String jobID: choosenJob.getDependsName())
                            infoTextArea.append(jobID+" ");
                        
                    }
                infoTextArea.append("\nProperties: ");
                if(choosenJob.getProperties().size()==0){
                infoTextArea.append("None");
                } else{
                for(String property: choosenJob.getProperties())
                    infoTextArea.append("\n"+property);
                }
                }
            }
        });

        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridwidth = 2;
        c.gridy = 1;
        panel.add(buttonSubmitNode, c);
                 
        
       
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridwidth = 2;
        c.gridy = 3;
        panel.add(scroll,c);
        
        return panel;
    }

    private Component algorithmPicker() {
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        c.fill = GridBagConstraints.SOUTH;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridwidth = 2;
        c.gridy = 0;
        panel.add(newLabelWithFont("Select algorithm: ", 18), c);
       
        ArrayList<String> algorithmNames = new ArrayList<>();
        for (ALGORITHM node : ALGORITHM.values()) {
            algorithmNames.add(node.name());
        }

        comboBoxAlgorithm = new JComboBox<>(algorithmNames.toArray(new String[algorithmNames.size()]));
        //petList.setSelectedIndex(4);
        c.fill = GridBagConstraints.SOUTH;
        c.weightx = 0.5;
        c.gridx = 2;
        c.gridwidth = 1;
        c.gridy = 0;
        panel.add(comboBoxAlgorithm, c);

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
   
   
   private String[] getJobsNames(){
   
       ArrayList<String> jobsNames = new ArrayList<>();
        for (Node node : jobs.getGraph()) {
            jobsNames.add(node.getId());
        }
        return jobsNames.toArray(new String[jobsNames.size()]);
   }

}
