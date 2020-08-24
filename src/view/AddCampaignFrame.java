package view;

import controller.AddCampaignController;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class AddCampaignFrame extends JFrame {
    JPanel mainPanel;
    JButton addFiles;
    JButton addCampaign;
    JButton clearFiles;
    JPanel southernPanel;
    JPanel centralPanel;
    JPanel innerSouthernPanel;
    JLabel impressionFileSet;
    JLabel clickFileSet;
    JLabel serverFileSet;
    JTextField enterText;
    AddCampaignController contr;
    JComboBox<String> campaignList;
    JComboBox<String> clientList;
    JComboBox<String> assignThese;
    JPanel moveButtonsHolder;
    JButton moveRight;
    JButton moveLeft;
    JComboBox<String> thisFrameClientList;

    public AddCampaignFrame(AddCampaignController contr, JComboBox<String> campaignList, JComboBox<String> clientList) {
        super("Add new Campaign");
        this.contr = contr;
        this.campaignList = campaignList;
        this.clientList = clientList;
        setVisible(true);
        setSize(800, 700);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    void initialize() {
        mainPanel = new JPanel();
        setContentPane(mainPanel);

        JLabel welcomeText = new JLabel("Add a new campaign", SwingConstants.CENTER);
        welcomeText.setForeground(Color.black);
        welcomeText.setFont(new Font("Verdana", Font.BOLD, 40));

        mainPanel.setLayout(new GridLayout(3, 1, 0, 50));
        mainPanel.add(welcomeText);


        southernPanel = new JPanel();
        southernPanel.setLayout(new GridLayout(1, 2, 10, 0));

        innerSouthernPanel = new JPanel();
        innerSouthernPanel.setLayout(new GridLayout(3, 1, 0, 10));

        impressionFileSet = new JLabel("Impression log file not added", SwingConstants.CENTER);
        clickFileSet = new JLabel("Click log file not added", SwingConstants.CENTER);
        serverFileSet = new JLabel("Server log file not added", SwingConstants.CENTER);

        impressionFileSet.setForeground(Color.red);
        clickFileSet.setForeground(Color.red);
        serverFileSet.setForeground(Color.red);

        impressionFileSet.setFont(new Font("Verdana", Font.BOLD, 18));
        clickFileSet.setFont(new Font("Verdana", Font.BOLD, 18));
        serverFileSet.setFont(new Font("Verdana", Font.BOLD, 18));

        innerSouthernPanel.add(impressionFileSet);
        innerSouthernPanel.add(clickFileSet);
        innerSouthernPanel.add(serverFileSet);
        innerSouthernPanel.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));


        addFiles = new JButton("Add log files");
        addFiles.setFont(new Font("Verdana", Font.BOLD, 20));

        clearFiles = new JButton("Clear added files");
        clearFiles.setFont(new Font("Verdana", Font.BOLD, 20));

        addCampaign = new JButton("Add Campaign");
        addCampaign.setFont(new Font("Verdana", Font.BOLD, 20));

        addCampaign.addActionListener(e -> {
            if (enterText.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "No campaign name specified");
            } //else if (assignThese.getItemCount() == 1) {
              //  JOptionPane.showMessageDialog(this, "No campaign owner(s) specified.");
           // }
            else {
                try {
                    if (!enterText.getText().equals("CampaignMaster9000") && !enterText.getText().equals("")) {
                        boolean areUploaded = contr.provideLogFiles(enterText.getText());
                        if (areUploaded) {
                            campaignList.addItem(enterText.getText());
                            campaignList.revalidate();
                        }
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                dispose();

            }

        });

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(3, 1, 0, 10));
        menuPanel.add(addCampaign);
        menuPanel.add(addFiles);
        menuPanel.add(clearFiles);

        southernPanel.add(innerSouthernPanel);
        southernPanel.add(menuPanel);
        southernPanel.setBorder(new BevelBorder(BevelBorder.RAISED));


        centralPanel = new JPanel(new GridLayout(2, 1, 0, 10));

        JLabel enterNameOfCamp = new JLabel("Enter name of Campaign: ");
        enterNameOfCamp.setFont(new Font("Verdana", Font.BOLD, 25));

        JPanel campaignNameHolder = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 0));

        enterText = new JTextField("Example: CampaignMaster9000");
        enterText.setForeground(Color.GRAY);
        enterText.setFont(new Font("", Font.ITALIC, 22));
        enterText.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (!enterText.getText().equals("Example: CampaignMaster9000")) {
                    enterText.setForeground(Color.BLACK);
                    enterText.setFont(new Font("", Font.BOLD, 22));
                } else {
                    enterText.setText("");
                    enterText.setForeground(Color.BLACK);
                    enterText.setFont(new Font("", Font.BOLD, 22));
                }
            }
        });

        campaignNameHolder.add(enterNameOfCamp);
        campaignNameHolder.add(enterText);

        thisFrameClientList = new JComboBox<>();
        thisFrameClientList.setFont(new Font("Verdana", Font.BOLD, 20));

        for(int i = 0; i<clientList.getItemCount(); i++){
            thisFrameClientList.addItem(clientList.getItemAt(i));
        }




        assignThese = new JComboBox<>();
        assignThese.setFont(new Font("Verdana", Font.BOLD, 17));
        assignThese.addItem("Assign to clients:");
        assignThese.setSelectedItem("Assign to clients:");
        assignThese.revalidate();

        moveButtonsHolder = new JPanel();
        moveButtonsHolder.setLayout(new GridLayout(2, 1, 0, 20));

        Set<String> alreadyAdded = new HashSet<>();

        moveRight = new JButton(">>>");
        moveRight.addActionListener(e -> {
            if (thisFrameClientList.getSelectedItem().equals("Select Client")) {
                JOptionPane.showMessageDialog(this, "Invalid selection.");
            } else if (alreadyAdded.contains(thisFrameClientList.getSelectedItem())) {
                JOptionPane.showMessageDialog(this, "Client already added.");
            } else {
                assignThese.addItem((String) thisFrameClientList.getSelectedItem());
                alreadyAdded.add((String) thisFrameClientList.getSelectedItem());
            }
        });
        moveRight.setFont(new Font("Verdana", Font.BOLD, 14));

        moveLeft = new JButton("<<<");
        moveLeft.addActionListener(e -> {
            if (assignThese.getSelectedItem().equals("Assign to clients:")) {
                JOptionPane.showMessageDialog(this, "Invalid selection.");
            } else {
                alreadyAdded.remove(assignThese.getSelectedItem());
                assignThese.removeItem(assignThese.getSelectedItem());

            }
        });

        moveLeft.setFont(new Font("Verdana", Font.BOLD, 14));


        moveButtonsHolder.add(moveRight);
        moveButtonsHolder.add(moveLeft);


        JPanel placeHolder = new JPanel();
        placeHolder.setLayout(new GridLayout(1, 3, 30, 0));
        placeHolder.add(thisFrameClientList);
        placeHolder.add(moveButtonsHolder);
        placeHolder.add(assignThese);

        centralPanel.add(campaignNameHolder);
        centralPanel.add(placeHolder);

        mainPanel.add(centralPanel);
        mainPanel.add(southernPanel);

        mainPanel.revalidate();

        addFiles.addActionListener(e -> loadFiles());

        assignThese.setEnabled(false);
        moveLeft.setEnabled(false);
        moveRight.setEnabled(false);
        thisFrameClientList.setEnabled(false);


    }

    private void loadFiles() {

        JFileChooser jfc = new JFileChooser(new File(".")); //default to home dir

        jfc.setDialogTitle("Choose log files.");

        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jfc.setMultiSelectionEnabled(false);

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Log Files", "csv"); // custom extension
        jfc.setAcceptAllFileFilterUsed(false);
        jfc.addChoosableFileFilter(filter);

        int returnValue = jfc.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {


            File selectedFile = jfc.getSelectedFile();

            if (selectedFile.getName().contains("impression")) {
                impressionFileSet.setForeground(Color.GREEN);
                impressionFileSet.setText("Impression log file added");

                contr.setImpressionFile(selectedFile);

            } else if (selectedFile.getName().contains("click")) {

                clickFileSet.setForeground(Color.GREEN);
                clickFileSet.setText("Click log file added");

                contr.setClickFile(selectedFile);

            } else if (selectedFile.getName().contains("server")) {

                serverFileSet.setForeground(Color.GREEN);
                serverFileSet.setText("Server log file added");

                contr.setServerFile(selectedFile);

            } else JOptionPane.showMessageDialog(this, "Invalid file");
        }
    }
}
