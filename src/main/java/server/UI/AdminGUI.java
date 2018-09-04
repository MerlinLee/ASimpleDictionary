package server.UI;

import javax.swing.*;

public class AdminGUI {
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;

    public AdminGUI() {
        createUIComponents();
    }

    public JPanel getPanel1() {
        return panel1;
    }

    public void setPanel1(JPanel panel1) {
        this.panel1 = panel1;
    }

    private JPanel panel1;

    private void createUIComponents() {
        // TODO: place custom component creation code here
        panel1 = new JPanel();
        button1 = new JButton();
        button2= new JButton();
        button3= new JButton();
        button4= new JButton();
    }

}
