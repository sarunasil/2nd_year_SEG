package view;

import controller.LoginController;
import controller.MainController;
import users.Admin;
import users.Client;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;


public class Login extends JDialog {

    private JTextField name;
    private JPasswordField password;
    private JLabel enterUser;
    private JLabel enterPassword;
    private JButton login;
    private JButton cancel;
    private Frame parent;
    private LoginController loginController;


    public Login(Frame parent, LoginController controller) {
        super(parent, "Login", true);
        this.parent = parent;
        this.loginController = controller;
    }

    public void initialize() {

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.fill = GridBagConstraints.HORIZONTAL;

        enterUser = new JLabel("Username: ");
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        panel.add(enterUser, constraints);

        name = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        panel.add(name, constraints);

        enterPassword = new JLabel("Password: ");
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        panel.add(enterPassword, constraints);

        password = new JPasswordField(20);
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        panel.add(password, constraints);
        panel.setBorder(new LineBorder(Color.GRAY));

        login = new JButton("Login");

        login.addActionListener(e -> {
            if (name.getText().equals("") || password.getPassword().equals("")) {
                JOptionPane.showMessageDialog(this, "Please enter username and password");
            }else{
            int user = loginController.verify(name.getText(), password.getPassword());
            // System.out.println(user);
            if (user > -1) {
                dispose();
                parent.dispose();

                if (user == 0) {
                    Client cl = new Client(name.getText(), "");
                    new LandingFrameClient(new MainController(cl)).initialize();
                } else if (user == 1) {
                    Admin ad = new Admin(name.getText(), "");
                    new LandingFrameAdmin(new MainController(ad)).initialize();

                }


                //Stoyan Botev testing frames below
//                Client cl = new Client(name.getText(), "");
//                new LandingFrameClient(new MainController(cl)).initialize();
            }
        }
        });

        cancel = new JButton("Cancel");
        cancel.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel();

        buttonPanel.add(login);
        buttonPanel.add(cancel);

        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.PAGE_END);

        pack();
        setLocationRelativeTo(parent);
        setResizable(false);
        setVisible(true);
    }

}