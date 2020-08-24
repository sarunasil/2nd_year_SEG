package view;

import controller.LFController;
import controller.MainController;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LandingFrameClient extends JFrame {
    JTabbedPane tabPane;
    JButton showCampaign;
    JButton manual;
    JLabel welcomeText;
    JPanel menuPanel;
    JPanel fillerPanel1;
    JPanel fillerPanel2;
    JComboBox<String> campaignList;
    MainController mainController;
    LFController lfController;
    // Map<String, String> stats;

    LandingFrameClient(MainController mainController) {
        super("Ad Campaign Tool");
        this.mainController = mainController;
        this.lfController = (LFController) mainController.getController("LFController");
        // this.stats = new HashMap<>();
        setVisible(true);
        setSize(800, 730);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    void initialize() {

        tabPane = new JTabbedPane();

        setContentPane(tabPane);

        welcomeText = new JLabel("Welcome, " + lfController.getUsername(), SwingConstants.CENTER);
        welcomeText.setForeground(Color.black);
        welcomeText.setFont(new Font("Verdana", Font.ITALIC, 30));


        campaignList = new JComboBox<>();
        campaignList.addItem("Select a campaign");
        campaignList.setSelectedItem("Select a campaign");
        campaignList.setFont(new Font("Verdana", Font.BOLD, 20));

        for (String campaign : lfController.getCampaignNames()) {
            campaignList.addItem(campaign);
        }

        menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(2, 1, 0, 10));


        showCampaign = new JButton("Show Selected Campaign");
        showCampaign.setFont(new Font("Verdana", Font.BOLD, 20));


        manual = new JButton("Manual/ Sprint 2");

        manual.setFont(new Font("Verdana", Font.BOLD, 20));
        manual.addActionListener(e -> {

            StringBuilder sb = new StringBuilder();
            try {
                File file = new File("./resources/manual.txt");

               Scanner input = new Scanner(file);



                while (input.hasNextLine()) {
                    sb.append(input.nextLine()+"\n");

                }
                input.close();

            } catch (Exception ex) {
                ex.printStackTrace();
            }


            JFrame manualFrame = new JFrame("Manual");
            manualFrame.setLayout(new GridLayout(1, 1));
            JTextArea explanation = new JTextArea(sb.toString());
            explanation.setEditable(false);
            explanation.setFont(new Font("", Font.BOLD, 14));
            manualFrame.add(explanation);
            manualFrame.setSize(1000, 400);
            manualFrame.setVisible(true);
            manualFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        });


        JPanel outerFillerPanel = new JPanel();

        outerFillerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 10));


        fillerPanel1 = new JPanel();
        fillerPanel1.setLayout(new GridLayout(1, 1));
        fillerPanel1.add(campaignList);

        fillerPanel2 = new JPanel();
        fillerPanel2.setLayout(new GridLayout(2, 1, 0, 30));
        fillerPanel2.add(showCampaign);
        fillerPanel2.add(manual);

        outerFillerPanel.add(fillerPanel1);
        outerFillerPanel.add(fillerPanel2);

        menuPanel.add(welcomeText, BorderLayout.NORTH);
        menuPanel.add(outerFillerPanel, BorderLayout.CENTER);

        tabPane.addTab("Welcome", menuPanel);

        showCampaign.addActionListener(e -> {
//            menuController.getCampaign((String) campaignList.getSelectedItem()); // when model is implemented

            JTabbedPane nestedTabs = new JTabbedPane();
            JPanel campaignLayout = new JPanel();
            campaignLayout.setLayout(new BorderLayout(0, 35));


            JTable table = new JTable();


            table.setModel(new DefaultTableModel(11, 2) {
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            });

            table.setFont(new Font("", Font.BOLD, 18));
            table.setRowHeight(35);

            HashMap<String, String> map;
            lfController.loadCampaign((String) campaignList.getSelectedItem());
            this.addStats(map = new HashMap<>(), (String) campaignList.getSelectedItem());

            // table.setValueAt("Stat", 0, 0);
            // table.setValueAt("Value", 0, 1);

            table.setValueAt("Impressions", 0, 0);
            table.setValueAt("Clicks", 1, 0);
            table.setValueAt("Uniques", 2, 0);
            table.setValueAt("Bounces", 3, 0);
            table.setValueAt("Conversions", 4, 0);
            table.setValueAt("Cost", 5, 0);
            table.setValueAt("CTR", 6, 0);
            table.setValueAt("CPA", 7, 0);
            table.setValueAt("CPC", 8, 0);
            table.setValueAt("CPM", 9, 0);
            table.setValueAt("Bounce rate", 10, 0);

            for (int j = 0; j < 11; j++) {
                table.setValueAt(map.get(table.getValueAt(j, 0)), j, 1);
            }

            setTableAlignment(SwingConstants.CENTER, table);

            JPanel campaignMenu = new JPanel();

            campaignMenu.setBorder(new BevelBorder(BevelBorder.LOWERED));

            JButton button1 = new JButton("Sprint 2");
            JButton button2 = new JButton("Sprint 2");
            JButton button3 = new JButton("Sprint 2");
            JButton button4 = new JButton("Sprint 2");
            JButton button5 = new JButton("Sprint 2");
            button1.setEnabled(false);
            button2.setEnabled(false);
            button3.setEnabled(false);
            button4.setEnabled(false);
            button5.setEnabled(false);


            Font font = new Font("Verdana", Font.BOLD, 17);

            button1.setFont(font);
            button2.setFont(font);
            button3.setFont(font);
            button4.setFont(font);
            button5.setFont(font);

            campaignMenu.add(button1);
            campaignMenu.add(button2);
            campaignMenu.add(button3);
            campaignMenu.add(button4);
            campaignMenu.add(button5);

            JPanel welcomeMsgPanel = new JPanel();
            welcomeMsgPanel.setLayout(new GridLayout(2, 1));
            welcomeMsgPanel.add(Box.createHorizontalGlue());

            JLabel welcomeMsg = new JLabel("Main statistics about campaign", SwingConstants.CENTER);

            welcomeMsg.setFont(new Font("", Font.BOLD, 25));
            welcomeMsgPanel.add(welcomeMsg);
            campaignLayout.add(welcomeMsgPanel, BorderLayout.NORTH);

            String[] tableHeaders = {"Stat", "Value"};
            ((DefaultTableModel) table.getModel()).setColumnIdentifiers(tableHeaders);
            table.getTableHeader().setFont(new Font("", Font.ITALIC, 15));
            table.getTableHeader().setReorderingAllowed(false);


            campaignLayout.add(new JScrollPane(table), BorderLayout.CENTER);

            campaignLayout.add(campaignMenu, BorderLayout.SOUTH);


            addModifiedTab(nestedTabs, campaignLayout, "Main Stats");
            addModifiedTab(tabPane, nestedTabs, (String) campaignList.getSelectedItem());

        });


    }

    void addModifiedTab(JTabbedPane tabbedPane, JComponent addComponent, String title) {

        tabbedPane.addTab(title, addComponent);
        int pos = tabbedPane.indexOfComponent(addComponent);


        FlowLayout layoutForTab = new FlowLayout(FlowLayout.CENTER, 5, 0);


        JPanel panelForTab = new JPanel(layoutForTab);
        panelForTab.setOpaque(false);


        JLabel titleLabel = new JLabel(title);

        JButton btnClose = new JButton();
        btnClose.setOpaque(false);

        ImageIcon img = new ImageIcon("closeTabButton.png");
        btnClose.setIcon(img);


        btnClose.setBorder(null);


        btnClose.setFocusable(false);

        panelForTab.add(titleLabel);
        panelForTab.add(btnClose);

        panelForTab.setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));


        tabbedPane.setTabComponentAt(pos, panelForTab);

        ActionListener listener = e -> {
            if (tabbedPane.getTabCount() == 1) {
                tabbedPane.getParent().remove(tabbedPane);
            } else tabbedPane.remove(addComponent);
        };

        btnClose.addActionListener(listener);
        //tabbedPane.setSelectedComponent(addComponent);

    }

    private void setTableAlignment(int alignment, JTable table) {
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(alignment);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.setDefaultRenderer(table.getColumnClass(i), renderer);
        }
        table.updateUI();
    }

    private Map<String, String> addStats(Map<String, String> map, String campaignName) {

        map.put("Impressions", String.valueOf(lfController.getNumberOfImpressions(campaignName)));
        map.put("Clicks", String.valueOf(lfController.getNumberOfClicks(campaignName)));
        map.put("Uniques", String.valueOf(lfController.getNumberOfUniques(campaignName)));
        map.put("Bounces", String.valueOf(lfController.getNumberOfBounces(campaignName)));
        map.put("Conversions", String.valueOf(lfController.getNumberOfConversions(campaignName)));
        map.put("Cost", String.valueOf(lfController.getTotalCost(campaignName)));
        map.put("CTR", String.valueOf(lfController.getCtr(campaignName)));
        map.put("CPA", String.valueOf(lfController.getCpa(campaignName)));
        map.put("CPC", String.valueOf(lfController.getCpc(campaignName)));
        map.put("CPM", String.valueOf(lfController.getCpm(campaignName)));
        map.put("Bounce rate", String.valueOf(lfController.getBounceRate(campaignName)));

        return map;
    }


}
