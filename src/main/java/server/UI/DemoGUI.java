package server.UI;

import org.apache.log4j.Logger;
import server.network.controllers.ServerController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DemoGUI {
    private static Logger logger = Logger.getLogger(DemoGUI.class);

    public JFrame getjFrame() {
        return jFrame;
    }

    public void setjFrame(JFrame jFrame) {
        this.jFrame = jFrame;
    }

    private JFrame jFrame;
    public DemoGUI() {
        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ServerController serverController = ServerController.getServerInstance();
            }
        });
        btnMore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrame.getContentPane().add(new AdminGUI().getPanel1());
                jFrame.validate();
            }
        });
    }

    public static void main(String[] args) {
        DemoGUI demoGUI = new DemoGUI();
        demoGUI.setjFrame(new JFrame("DemoGUI"));
        demoGUI.getjFrame().setContentPane(new DemoGUI().panel1);
        demoGUI.getjFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        demoGUI.getjFrame().pack();
        demoGUI.getjFrame().setVisible(true);

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        panel1 = new JPanel();
        searchBtn = new JButton();
        btnMore = new JButton();
        textField_word = new JTextField();

    }
    private JPanel panel1;
    private JButton searchBtn;
    private JTextField textField_word;
    private JButton btnMore;
}
