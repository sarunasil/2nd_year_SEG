package view;

import javax.swing.*;
import javax.swing.border.SoftBevelBorder;
import java.awt.*;

public class AddAdminFrame extends JFrame {

    JPanel mainPanel;
    JComboBox<String> adminList;

    AddAdminFrame(JComboBox<String> adminList) {
        super("Add new admin");
        this.adminList = adminList;
        setVisible(true);
        setSize(600, 600);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    void initialize() {
        mainPanel = new JPanel();
        getContentPane().add(mainPanel);
        //mainPanel.setLayout(new GridLayout(6,3,0,70));
        mainPanel.setLayout(new BorderLayout());

        JLabel welcomeMsg = new JLabel("Add admin to system", SwingConstants.CENTER);
        welcomeMsg.setFont(new Font("", Font.BOLD, 20));

        JLabel enterUserName = new JLabel("Enter username:", SwingConstants.CENTER);
        JLabel enterPassword = new JLabel("Enter password:", SwingConstants.CENTER);

        Font font = new Font("", Font.BOLD, 20);
        enterUserName.setFont(font);
        enterPassword.setFont(font);

        JTextField username = new JTextField();
        JTextField password = new JTextField();

        username.setFont(font);
        password.setFont(font);

        JButton addAdmin = new JButton("Add Admin");
        addAdmin.setFont(new Font("",Font.BOLD,20));

        JPanel centralPanel = new JPanel();
        centralPanel.setLayout(new GridLayout(9,3,0,0));

        centralPanel.add(Box.createHorizontalGlue());
        centralPanel.add(Box.createHorizontalGlue());
        centralPanel.add(Box.createHorizontalGlue());

        centralPanel.add(Box.createHorizontalGlue());
         centralPanel.add(enterUserName);
        centralPanel.add(Box.createHorizontalGlue());

        centralPanel.add(Box.createHorizontalGlue());
        centralPanel.add(Box.createHorizontalGlue());
        centralPanel.add(Box.createHorizontalGlue());

        centralPanel.add(Box.createHorizontalGlue());
         centralPanel.add(username);
        centralPanel.add(Box.createHorizontalGlue());

        centralPanel.add(Box.createHorizontalGlue());
        centralPanel.add(Box.createHorizontalGlue());
        centralPanel.add(Box.createHorizontalGlue());


        centralPanel.add(Box.createHorizontalGlue());
        centralPanel.add(enterPassword);
        centralPanel.add(Box.createHorizontalGlue());

        centralPanel.add(Box.createHorizontalGlue());
        centralPanel.add(Box.createHorizontalGlue());
        centralPanel.add(Box.createHorizontalGlue());

        centralPanel.add(Box.createHorizontalGlue());
         centralPanel.add(password);
        centralPanel.add(Box.createHorizontalGlue());

        centralPanel.add(Box.createHorizontalGlue());
        centralPanel.add(Box.createHorizontalGlue());
        centralPanel.add(Box.createHorizontalGlue());


        centralPanel.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));

        JPanel addAdminFillerPanel = new JPanel();
        addAdminFillerPanel.setLayout(new GridLayout(1,3,10,0));
        addAdminFillerPanel.add(Box.createHorizontalBox());
        addAdminFillerPanel.add(addAdmin);
        addAdminFillerPanel.add(Box.createHorizontalBox());


        mainPanel.add(welcomeMsg,BorderLayout.NORTH);
        mainPanel.add(centralPanel,BorderLayout.CENTER);
        mainPanel.add(addAdminFillerPanel,BorderLayout.SOUTH);


        addAdmin.addActionListener(e -> {
            if (username.getText().equals("") || password.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Please fill in both fields.");
            } else {
                adminList.addItem(username.getText());

                //Ivo, I summon you.

                dispose();
            }
        });
    }

}
