package server;

import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DemoGUI {
    private static Logger logger = Logger.getLogger(DemoGUI.class);
    public DemoGUI() {
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ServerController serverController = ServerController.getServerInstance();
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("DemoGUI");
        frame.setContentPane(new DemoGUI().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


    private JPanel panel1;
    private JButton button1;
    private JButton button2;
}
