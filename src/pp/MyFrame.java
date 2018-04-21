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
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import org.graphstream.graph.Node;
import org.graphstream.ui.swingViewer.DefaultView;
import static pp.ConstansInterface.NUMBER_OF_TICKS;

/**
 *
 * @author Kamil Sowa
 */
public class MyFrame extends JFrame {

    ArrayList<Machine> machines;

    Jobs jobs;
    JComboBox comboBoxToDelete;
    JTextField fieldJobName = new JTextField("");
    JTextField fieldJobTime = new JTextField("");
    private JComboBox comboBoxFirstNode;
    private JComboBox comboBoxSecondNode;
    private JPopupMenu menu;
    private ArrayList<JMenuItem> listOfCheckBox;
    private JFrame me = this;
    private BarChart barChart;

    public void setMachines(ArrayList<Machine> machines) {
        this.machines = machines;
    }

    public MyFrame(DefaultView view, Jobs jobs, ArrayList<Machine> machines) {

        this.jobs = jobs;
        this.machines = machines;
        setPreferredSize(new Dimension(1500, 1000));
        setLayout(new BorderLayout());
        add(view, BorderLayout.LINE_START);

        JPanel panel3 = new JPanel(new GridBagLayout());
        JPanel panel4 = new JPanel(new GridBagLayout());
        JPanel panelX1 = new JPanel(new GridBagLayout());

        JPanel panelX2 = new JPanel(new GridBagLayout());

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

        ArrayList<Machine> machines2 = new ArrayList<>();
        Machine machine = new Machine();
        machine.addTask(new Job(null, 5, "Job1"), 1);
        machine.addTask(new Job(null, 3, "Job2"), 8);
        machines2.add(machine);
        machine = new Machine();
        machine.addTask(new Job(null, 3, "Job3"), 1);
        machine.addTask(new Job(null, 4, "Job4"), 4);
        machine.addTask(new Job(null, 4, "Job4"), 8);
        machines2.add(machine);
        barChart = showBarCharts(machines2);
        //getContentPane().add(barChart, BorderLayout.CENTER);

        final JButton prz = new JButton("Show graph");

        prz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                barChart.setBars(getValuesFromMachines(machines));
                long height = calculateNeededHeightofYAxis(machines);
                barChart.setyAxis(setAxis(height));
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
                int primaryIncrements = (int) (height / NUMBER_OF_TICKS);
                int secondaryIncrements = (int) (height / NUMBER_OF_TICKS * 2);
                int tertiaryIncrements = (int) (height / NUMBER_OF_TICKS * 2);
                Axis yAxis = new Axis((int) height, 0, primaryIncrements, secondaryIncrements,
                        tertiaryIncrements);
                return yAxis;
            }

            private long calculateNeededHeightofYAxis(ArrayList<Machine> machines) {

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
        getContentPane().add(prz, BorderLayout.PAGE_END);

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
        comboBoxToDelete = new JComboBox(jobsNames.toArray());
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
        comboBoxFirstNode = new JComboBox(jobsNames.toArray());
        comboBoxSecondNode = new JComboBox(jobsNames.toArray());
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
