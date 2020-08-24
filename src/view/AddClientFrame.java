package view;

import controller.AddClientFrameController;
import users.Admin;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.SoftBevelBorder;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class AddClientFrame extends JFrame {

    JComboBox<String> campaignList;
    JComboBox<String> assignThese;
    JComboBox<String> clientList;
    JPanel moveButtonsHolder;
    JButton moveRight;
    JButton moveLeft;
    JPanel southernPanel;
    JPanel mainPanel;
    JPanel menuPanel;
    AddClientFrameController controller;
    JComboBox<String> thisFrameCampaignList;


    AddClientFrame(JComboBox<String> clientList,JComboBox<String> campaignList, Admin admin) {
        super("Add new client");
        this.clientList = clientList;
        this.campaignList = campaignList;
        this.controller = new AddClientFrameController(admin);
        setVisible(true);
        setSize(900, 600);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    void initialize() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(0, 50));
        setContentPane(mainPanel);

        JPanel welcomeMsgPanel = new JPanel();
        welcomeMsgPanel.setLayout(new GridLayout(2, 1));
        welcomeMsgPanel.add(Box.createVerticalGlue());
        JLabel welcomeMsg = new JLabel("Register a client and assign campaigns", SwingConstants.CENTER);
        welcomeMsg.setFont(new Font("Verdana", Font.BOLD, 25));
        welcomeMsgPanel.add(welcomeMsg);
        mainPanel.add(welcomeMsgPanel, BorderLayout.NORTH);



        thisFrameCampaignList = new JComboBox<>();
        thisFrameCampaignList.setFont(new Font("Verdana", Font.BOLD, 20));

        for(int i = 0; i<campaignList.getItemCount(); i++){
            thisFrameCampaignList.addItem(campaignList.getItemAt(i));
        }

        assignThese = new JComboBox<>();
        assignThese.setFont(new Font("Verdana", Font.BOLD, 17));
        assignThese.addItem("Assign these campaigns:");
        assignThese.setSelectedItem("Assign these campaigns");

        moveButtonsHolder = new JPanel();
        moveButtonsHolder.setLayout(new GridLayout(2, 1, 0, 20));

        moveRight = new JButton(">>>");
        moveRight.setFont(new Font("Verdana", Font.BOLD, 14));


        Set<String> alreadyAdded = new HashSet<>();

        moveRight.addActionListener(e -> {
            if (thisFrameCampaignList.getSelectedItem().equals("Select a campaign")) {
                JOptionPane.showMessageDialog(this, "Invalid selection.");
            } else if (alreadyAdded.contains(thisFrameCampaignList.getSelectedItem())) {
                JOptionPane.showMessageDialog(this, "Campaign already added.");
            } else {
                assignThese.addItem((String) thisFrameCampaignList.getSelectedItem());
                alreadyAdded.add((String) thisFrameCampaignList.getSelectedItem());
            }
        });


        moveLeft = new JButton("<<<");
        moveLeft.setFont(new Font("Verdana", Font.BOLD, 14));

        moveLeft.addActionListener(e -> {
            if (assignThese.getSelectedItem().equals("Assign these campaigns:")) {
                JOptionPane.showMessageDialog(this, "Invalid selection.");
            } else {
                alreadyAdded.remove(assignThese.getSelectedItem());
                assignThese.removeItem(assignThese.getSelectedItem());

            }
        });


        moveButtonsHolder.add(moveRight);
        moveButtonsHolder.add(moveLeft);



        southernPanel = new JPanel();
        southernPanel.setLayout(new GridLayout(1, 3, 30, 0));
        southernPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));


        southernPanel.add(thisFrameCampaignList);
        southernPanel.add(moveButtonsHolder);
        southernPanel.add(assignThese);


        mainPanel.add(southernPanel, BorderLayout.SOUTH);

        menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(5, 3, 0, 25));

        JLabel enterName = new JLabel("Enter username:", SwingConstants.CENTER);
        enterName.setFont(new Font("Verdana", Font.BOLD, 22));
        JLabel enterPass = new JLabel("Enter password:", SwingConstants.CENTER);
        enterPass.setFont(new Font("Verdana", Font.BOLD, 22));

        JTextField name = new JTextField();
        name.setFont(new Font("Verdana", Font.BOLD, 17));
        JPasswordField password = new JPasswordField();
        password.setFont(new Font("Verdana", Font.BOLD, 17));

        JButton addClient = new JButton("Add Client");
        addClient.setFont(new Font("Verdana", Font.BOLD, 17));

        addClient.addActionListener(e -> {
            if(name.getText().equals("") || password.getPassword().equals("")){
                JOptionPane.showMessageDialog(this, "Please enter username and password");
            }//else if(assignThese.getItemCount() == 1){
              //  JOptionPane.showMessageDialog(this, "No campaigns chosen.");

          //  }
            else {
                if (controller.submit(name.getText(), password.getPassword())) {
                    clientList.addItem(name.getText());
                    clientList.revalidate();
                    dispose();
                }
            }
        });

        menuPanel.add(Box.createHorizontalGlue());
        menuPanel.add(enterName);
        menuPanel.add(Box.createHorizontalGlue());
        menuPanel.add(Box.createHorizontalGlue());
        menuPanel.add(name);
        menuPanel.add(Box.createHorizontalGlue());
        menuPanel.add(Box.createHorizontalGlue());
        menuPanel.add(enterPass);
        menuPanel.add(Box.createHorizontalGlue());
        menuPanel.add(Box.createHorizontalGlue());
        menuPanel.add(password);
        menuPanel.add(Box.createHorizontalGlue());
        menuPanel.add(Box.createHorizontalGlue());
        menuPanel.add(addClient);
        menuPanel.add(Box.createHorizontalGlue());

        menuPanel.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));

        mainPanel.add(menuPanel, BorderLayout.CENTER);


        assignThese.setEnabled(false);
        moveLeft.setEnabled(false);
        moveRight.setEnabled(false);
        thisFrameCampaignList.setEnabled(false);


    }
}
