package tk.nikomitk.gui.main.chatlist;

import net.miginfocom.swing.MigLayout;
import tk.nikomitk.Main;
import tk.nikomitk.gui.main.MainGui;

import javax.swing.*;
import java.awt.*;

public class OptionsPanel extends JPanel {

    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel centerPanel;
    private JPanel bottomPanel;
    private JLabel einstellungsLabel;
    private JButton returnButton;
    private JLabel rememberMeLabel;
    private JCheckBox rememberMeBox;
    private JLabel darkmodeLabel;
    private JCheckBox darkmodeBox;
    private JLabel customserverLabel;
    private JCheckBox customserverBox;
    private JLabel serveradressLabel;
    private JTextField serveradressField;
    private JLabel serverportLabel;
    private JTextField serverportField;
    private JLabel uidebugLabel;
    private JCheckBox uidebugBox;
    private JButton saveButton;
    private Font labelFont;
    private String gapConstant;

    public OptionsPanel() {
        createLook();
        initComponents();
    }

    private void createLook() {
        setLayout(new GridLayout());
        setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.lightGray));
        setPreferredSize(new Dimension(400, 10000));
        setBackground(Main.getColor("PanelBackground"));
        labelFont = Main.getFont("Message").deriveFont(Font.PLAIN);
        gapConstant = "gapleft 250px,";
    }

    private void initComponents() {
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);
        add(mainPanel);

        topPanel = new JPanel(new BorderLayout());
        topPanel.setMinimumSize(new Dimension(200, 45));
        topPanel.setPreferredSize(new Dimension(400, 45));
        topPanel.setOpaque(false);
        mainPanel.add(topPanel, BorderLayout.PAGE_START);

        // TODO as jlabel idk
        returnButton = new BigButton(Main.getSymbol("LeftArrow"));
        returnButton.setPreferredSize(new Dimension(50, 31));
        returnButton.addActionListener(e -> MainGui.closeSettings());
        topPanel.add(returnButton, BorderLayout.LINE_START);

        einstellungsLabel = new JLabel("Einstellungen");
        einstellungsLabel.setFont(Main.getFont("Special").deriveFont(Font.BOLD));
        einstellungsLabel.setAlignmentY(TOP_ALIGNMENT);
        topPanel.add(einstellungsLabel, BorderLayout.CENTER);

        mainPanel.add(new JPanel().add(new JLabel("     ")), BorderLayout.LINE_START);
        centerPanel = new JPanel(new MigLayout("gapy 15", "grow, fill", ""));
        centerPanel.setPreferredSize(new Dimension(400, 10000));
        centerPanel.setOpaque(false);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        bottomPanel = new JPanel();
        bottomPanel.setMinimumSize(new Dimension(200, 150));
        bottomPanel.setPreferredSize(new Dimension(400, 130));
        bottomPanel.setOpaque(false);
        mainPanel.add(bottomPanel, BorderLayout.PAGE_END);

        rememberMeLabel = new JLabel("Remember me:");
        rememberMeLabel.setFont(labelFont);
        centerPanel.add(rememberMeLabel);
        rememberMeBox = new JCheckBox();
        rememberMeBox.setSelected(Main.settings.isRememberMe());
        centerPanel.add(rememberMeBox, gapConstant + "wrap");

        darkmodeLabel = new JLabel("Darkmode:");
        darkmodeLabel.setFont(labelFont);
        centerPanel.add(darkmodeLabel);
        darkmodeBox = new JCheckBox();
        darkmodeBox.setSelected(Main.settings.isDarkmode());
        centerPanel.add(darkmodeBox, gapConstant + "wrap");

        customserverLabel = new JLabel("Custom server:");
        customserverLabel.setFont(labelFont);
        centerPanel.add(customserverLabel);
        customserverBox = new JCheckBox();
        customserverBox.setSelected(Main.settings.isCustomServer());
        customserverBox.addChangeListener(e -> {
            if (customserverBox.isSelected()) {
                serveradressField.setEnabled(true);
                serverportField.setEnabled(true);
            } else {
                serveradressField.setEnabled(false);
                serveradressField.setText("nikosmessenger.ddns.net");
                serverportField.setEnabled(false);
                serverportField.setText("" + 6969);
            }
        });
        centerPanel.add(customserverBox, gapConstant + "wrap");

        serveradressLabel = new JLabel("Server address:");
        serveradressLabel.setFont(labelFont);
        centerPanel.add(serveradressLabel, "growx 0");
        serveradressField = new JTextField(Main.settings.getServerAdress());
        serveradressField.setFont(labelFont);
        serveradressField.setEnabled(customserverBox.isSelected());
        centerPanel.add(serveradressField, "wrap");

        serverportLabel = new JLabel("Server port:");
        serverportLabel.setFont(labelFont);
        centerPanel.add(serverportLabel);
        serverportField = new JTextField(Main.settings.getServerPort() + "");
        serverportField.setFont(labelFont);
        serverportField.setEnabled(customserverBox.isSelected());
        centerPanel.add(serverportField, "wrap");

        uidebugLabel = new JLabel("UI Debug:");
        uidebugLabel.setFont(labelFont);
        centerPanel.add(uidebugLabel);
        uidebugBox = new JCheckBox();
        uidebugBox.setSelected(Main.settings.isUIDebug());
        centerPanel.add(uidebugBox, gapConstant + "wrap");

        saveButton = new JButton("Save!");
        saveButton.setFont(labelFont);
        saveButton.setFocusable(false);
        saveButton.addActionListener(e -> {
            Main.settings.setRememberMe(rememberMeBox.isSelected());
            Main.settings.setDarkmode(darkmodeBox.isSelected());
            Main.settings.setCustomServer(customserverBox.isSelected());
            Main.settings.setServerAdress(serveradressField.getText());
            Main.settings.setServerPort(Integer.parseInt(serverportField.getText()));
            Main.settings.setUIDebug(uidebugBox.isSelected());
            Main.settings.safe();
        });
        centerPanel.add(saveButton);
    }


}
