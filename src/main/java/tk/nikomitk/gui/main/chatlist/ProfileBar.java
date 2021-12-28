package tk.nikomitk.gui.main.chatlist;

import net.miginfocom.swing.MigLayout;
import tk.nikomitk.Main;
import tk.nikomitk.gui.main.MainGui;
import tk.nikomitk.gui.utils.UtilMethods;

import javax.swing.*;
import java.awt.*;

public class ProfileBar extends JPanel {

    private JPanel mainPanel;
    private Icon profilePicture;
    private JPanel imagePanel;
    private JLabel imageLabel; // icon is no JComponent, can't be added to panel
    private JPanel centerPanel;
    private JLabel userNameLabel;
    private JPanel rightPanel;
    private AddListingButton addListingButton;
    private BigButton settingsButton;

    public ProfileBar() {
        createLook();
        initComponents();
    }

    private void createLook() {
        setLayout(new GridLayout());
        setMinimumSize(new Dimension(10, 50));
    }

    private void initComponents() {
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Main.getColor("BarBackground"));
        add(mainPanel);

        imagePanel = new JPanel(new BorderLayout());
        imagePanel.setOpaque(false);
        imagePanel.add(new JLabel("  "), BorderLayout.LINE_START);
        mainPanel.add(imagePanel, BorderLayout.LINE_START);

        try {
            profilePicture = UtilMethods.loadRoundProfileIcon(Main.getUser().getProfilePicture());
            imageLabel = new JLabel(profilePicture);
            imagePanel.add(imageLabel, BorderLayout.CENTER);
        } catch (Exception e) {
            e.printStackTrace();
        }

        centerPanel = new JPanel(new MigLayout("", "[grow,fill]"));
        centerPanel.setOpaque(false);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        userNameLabel = new JLabel(Main.getUser().getNickName());
        userNameLabel.setFont(Main.getFont("Special"));
        centerPanel.add(userNameLabel);

        rightPanel = new JPanel(new FlowLayout());
        rightPanel.setMaximumSize(new Dimension(20, 100));
        rightPanel.setMinimumSize(new Dimension(20, 100));
        rightPanel.setOpaque(false);
        mainPanel.add(rightPanel, BorderLayout.LINE_END);

        // TODO make this a property
        settingsButton = new BigButton("&#8285;");
        settingsButton.addActionListener(e -> {
            MainGui.openSettings();
        });
        rightPanel.add(settingsButton);

        addListingButton = new AddListingButton();
        rightPanel.add(addListingButton);
        rightPanel.add(new JLabel("     "));
    }
}
