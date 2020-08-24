package view;

import controller.AddCampaignController;
import controller.LFController;
import controller.MainController;
import users.Admin;

import javax.swing.*;
import java.awt.*;

public class LandingFrameAdmin extends LandingFrameClient {

    JComboBox<String> adminList;
    JComboBox<String> clientList;

    public LandingFrameAdmin(MainController mainController) {
        super(mainController);
        this.mainController = mainController;
        this.lfController = (LFController) mainController.getController("LFController");
        // this.stats = new HashMap<>();
        setVisible(true);
        setSize(800, 730);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    @Override
    public void initialize() {
        super.initialize();

        fillerPanel1.setLayout(new GridLayout(2, 1, 0, 50));
        fillerPanel1.add(showCampaign);


        fillerPanel2.setLayout(new GridLayout(4, 1, 0, 30));

        JButton newCampaign = new JButton("Load New Campaign");
        newCampaign.setFont(new Font("Verdana", Font.BOLD, 20));

        JButton clients = new JButton("Client List");
        clients.setFont(new Font("Verdana", Font.BOLD, 20));

        JButton admins = new JButton("Admin List/ Sprint 2");
        admins.setFont(new Font("Verdana", Font.BOLD, 20));

        fillerPanel2.add(clients);
        fillerPanel2.add(admins);
        fillerPanel2.add(newCampaign);

        clientList = new JComboBox<>();


        adminList = new JComboBox<>();
        adminList.addItem("Select Admin");
        adminList.setSelectedItem("Select Admin");

        newCampaign.addActionListener(e -> {
            new AddCampaignFrame(new AddCampaignController(lfController.getUser()), campaignList, clientList).initialize();

        });

        admins.setEnabled(false);
        admins.addActionListener(e -> {
            boolean tabAlreadyOpen = false;
            for (int i = 0; i < tabPane.getTabCount(); i++) {

                if (tabPane.getTitleAt(i).equals("Admins")) {
                    tabPane.setSelectedIndex(i);
                    tabAlreadyOpen = true;
                    break;
                }
            }
            if (tabAlreadyOpen) {
            } else {
                JPanel adminPanel;
                adminPanel = new JPanel();
                adminPanel.setLayout(new GridLayout(2, 1, 0, 10));

                JLabel welcomeMsg = new JLabel("View, add or remove Admins", SwingConstants.CENTER);
                welcomeMsg.setFont(new Font("Verdana", Font.BOLD, 25));
                adminPanel.add(welcomeMsg);


                adminList.setFont(new Font("Verdana", Font.BOLD, 20));

                for (String campaign : lfController.getUsers(1)) {
                    adminList.addItem(campaign);
                }

                JPanel panel1 = new JPanel();
                panel1.setLayout(new GridLayout(1, 1));
                panel1.add(adminList);

                JPanel panel2 = new JPanel();
                panel2.setLayout(new GridLayout(2, 1, 0, 30));

                JButton addAdmin = new JButton("Add Admin");
                addAdmin.setFont(new Font("Verdana", Font.BOLD, 20));
                panel2.add(addAdmin);

                addAdmin.addActionListener(e1 -> {
                    new AddAdminFrame(adminList).initialize();
                });

                JButton removeAdmin = new JButton("Remove Selected Admin/ Sprint 2");
                removeAdmin.setEnabled(false);
                removeAdmin.setFont(new Font("Verdana", Font.BOLD, 20));
                panel2.add(removeAdmin);


                JPanel panel3 = new JPanel();
                panel3.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 10));
                panel3.add(panel1);
                panel3.add(panel2);

                adminPanel.add(panel3);

                addModifiedTab(tabPane, adminPanel, "Admins");
            }
        });


        clients.addActionListener(e -> {

            boolean tabAlreadyOpen = false;
            for (int i = 0; i < tabPane.getTabCount(); i++) {

                if (tabPane.getTitleAt(i).equals("Clients")) {
                    tabPane.setSelectedIndex(i);
                    tabAlreadyOpen = true;
                    break;
                }
            }

            if (tabAlreadyOpen) {

            } else {

                JPanel clientPanel = new JPanel();
                clientPanel.setLayout(new GridLayout(2, 1, 0, 10));

                JLabel welcomeMsg = new JLabel("View, add or remove clients", SwingConstants.CENTER);
                welcomeMsg.setFont(new Font("Verdana", Font.BOLD, 25));
                clientPanel.add(welcomeMsg);

                clientList.removeAllItems();
                clientList.setFont(new Font("Verdana", Font.BOLD, 20));
                clientList.addItem("Select client");
                clientList.setSelectedItem("Select client");


                for (String campaign : lfController.getUsers(0)) {
                    clientList.addItem(campaign);
                }

                JPanel panel1 = new JPanel();
                panel1.setLayout(new GridLayout(1, 1));
                panel1.add(clientList);


                JPanel panel2 = new JPanel();
                panel2.setLayout(new GridLayout(2, 1, 0, 30));

                JButton addClient = new JButton("Add Client");
                addClient.setFont(new Font("Verdana", Font.BOLD, 20));
                panel2.add(addClient);

                addClient.addActionListener(e12 -> {
                    new AddClientFrame(clientList, campaignList, (Admin) lfController.getUser()).initialize();
                });

                JButton removeClient = new JButton("Remove Selected client/ Sprint2");
                removeClient.setFont(new Font("Verdana", Font.BOLD, 20));
                removeClient.setEnabled(false);
                panel2.add(removeClient);


                JPanel panel3 = new JPanel();
                panel3.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 10));
                panel3.add(panel1);
                panel3.add(panel2);

                clientPanel.add(panel3);

                addModifiedTab(tabPane, clientPanel, "Clients");
            }
        });
    }
}
