package view;

import controller.*;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame {

    JPanel mainPanel;
    JButton loginButton;
    JLabel welcomeText;
    JPanel menuPanel;

    LoginController controller;

    public void setController(LoginController controller) {
        this.controller = controller;
    }

    View() {
        super("Ad Campaign Tool");

        setVisible(true);
        setSize(700, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }


    void initialize() {
        mainPanel = new JPanel();
        setContentPane(mainPanel);

        mainPanel.setLayout(new BorderLayout(0, 30));

        welcomeText = new JLabel("Welcome to Ad Campaign Tool \n", SwingConstants.CENTER);
        welcomeText.setFont(new Font("Verdana", Font.BOLD, 30));

        mainPanel.add(welcomeText, BorderLayout.NORTH);


        mainPanel.add(new JLabel(new ImageIcon("adpic.png")), BorderLayout.CENTER);

        menuPanel = new JPanel();


        loginButton = new JButton("LOGIN");
        loginButton.setFont(new Font("", Font.BOLD, 20));
        loginButton.addActionListener(e -> {
            new Login(this, controller).initialize();
            dispose();
        });


        menuPanel.add(loginButton);

        mainPanel.add(menuPanel, BorderLayout.SOUTH);


    }
}
