package client.UI;

import client.ClientController;
import com.alibaba.fastjson.JSON;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.log4j.Logger;
import server.network.WordModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.concurrent.*;
/**
 * UI
 *
 * @author MINGFENG LI
 * @date 06/09/2018
 */
public class ClientUI implements ItemListener, ActionListener {
    private JPanel cards;
    final static String ADD = "Add Word";
    final static String QUERY = "Search Meaning";
    final static String REMOVE = "Remove Meaning";
    private ThreadFactory threadFactory;
    private ExecutorService pool;
    private JButton btn_query;
    private JButton btn_add;
    private JButton btn_remove;

    public JTextField getjTextField_input() {
        return jTextField_input;
    }

    public void setjTextField_input(JTextField jTextField_input) {
        this.jTextField_input = jTextField_input;
    }

    private JTextField jTextField_input;

    public JTextArea getjTextArea_add_meanings() {
        return jTextArea_add_meanings;
    }

    public void setjTextArea_add_meanings(JTextArea jTextArea_add_meanings) {
        this.jTextArea_add_meanings = jTextArea_add_meanings;
    }

    private JTextArea jTextArea_add_meanings;

    public JTextArea getjTextArea() {
        return jTextArea;
    }

    public void setjTextArea(JTextArea jTextArea) {
        this.jTextArea = jTextArea;
    }

    private JTextArea jTextArea;

    public JTextField getjTextField() {
        return jTextField;
    }

    public void setjTextField(JTextField jTextField) {
        this.jTextField = jTextField;
    }

    private JTextField jTextField;
    private volatile static ClientUI clientUI;
    private static Logger logger = Logger.getLogger(ClientUI.class);
    public static ClientUI getServerInstance() {
        if (clientUI == null) {
            synchronized (ClientUI.class) {
                if (clientUI == null) {
                    clientUI = new ClientUI();
                    logger.info("Create a instance for Client Controller class, Successful.");
                }
            }
        }
        return clientUI;
    }
    public ClientUI() {
        threadFactory = new ThreadFactoryBuilder()
                .setNameFormat("UI-pool-%d").build();
        pool = new ThreadPoolExecutor(1,2,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024),threadFactory,new ThreadPoolExecutor.AbortPolicy());
    }

    public void addComponentToPane(Container pane) {
        JPanel comboBoxPane = new JPanel();
        String comboBoxItems[] = { QUERY,ADD };
        JComboBox cb = new JComboBox(comboBoxItems);
        cb.setEditable(false);
        cb.addItemListener(this);
        comboBoxPane.add(cb);

        //Create the "cards".
        JPanel card2 = new JPanel();
        card2.add(btn_add = new JButton("Add"));
        card2.add(new JScrollPane(jTextArea_add_meanings = new JTextArea("Input meanings here...",3,10)));
        jTextArea_add_meanings.setLineWrap(true);
        jTextArea_add_meanings.setWrapStyleWord(true);
        card2.add(jTextField_input = new JTextField("Input Word",8));
        card2.add(btn_remove = new JButton("Remove"));

        btn_add.addActionListener(this);
        btn_remove.addActionListener(this);
        JPanel card1 = new JPanel();
        JPanel subCard_1 = new JPanel();
        JPanel subCard_2 = new JPanel();
        subCard_2.add(new JScrollPane(jTextArea = new JTextArea(5,20)));
        jTextArea.setLineWrap(true);
        jTextArea.setWrapStyleWord(true);
        subCard_1.add(jTextField = new JTextField("TextField", 8));
        subCard_1.add(btn_query = new JButton("Search"));
        btn_query.addActionListener(this);
        card1.add(subCard_2);
        card1.add(subCard_1);
        //card2.add(jTextArea = new JTextArea());
        //Create the panel that contains the "cards".
        cards = new JPanel(new CardLayout());
        cards.add(card1, QUERY);
        cards.add(card2, ADD);

        pane.add(comboBoxPane, BorderLayout.PAGE_START);
        pane.add(cards, BorderLayout.CENTER);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "Search":
                pool.execute(()->{
                    ClientController.getServerInstance().
                            createModel("QUERY",jTextField.getText());

                });
                break;
            case "Add":
                pool.execute(()->{
                    WordModel wordModel = new WordModel();
                    wordModel.setMeanings(jTextArea_add_meanings.getText());
                    wordModel.setWord(jTextField_input.getText());
                    String info = JSON.toJSONString(wordModel);
                    ClientController.getServerInstance().
                            createModel("ADD",info);
                });
                break;
            case "Remove":
                pool.execute(()->{
                    ClientController.getServerInstance().
                            createModel("REMOVE",jTextField_input.getText());
                });
                break;
            default:
                break;
        }
    }

    @Override
    public void itemStateChanged(ItemEvent event) {
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, (String)event.getItem());
    }

    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Merlin Dictionary");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        ClientUI clientUI = ClientUI.getServerInstance();
        clientUI.addComponentToPane(frame.getContentPane());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void initialUI(){
        /* Use an appropriate Look and Feel */
        try {
//            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        /* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);

        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
