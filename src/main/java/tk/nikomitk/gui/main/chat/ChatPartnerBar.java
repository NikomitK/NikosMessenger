package tk.nikomitk.gui.main.chat;

import net.miginfocom.swing.MigLayout;
import tk.nikomitk.Main;
import tk.nikomitk.gui.utils.UtilMethods;
import tk.nikomitk.messenger.User;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

// TODO maybe "last seen" and "typing"
public class ChatPartnerBar extends JPanel {

    private final User chatPartner;
    private JPanel mainPanel;
    private Icon profilePicture;
    private JPanel imagePanel;
    private JLabel imageLabel; // icon is no JComponent, can't be added to panel
    private JPanel centerPanel;
    private JLabel userNameLabel;
    private String status; // TODO implement this

    // TODO check if chatPartner is spelled the same everywhere
    // TODO catch exception?
    public ChatPartnerBar(User chatPartner) throws IOException {
        this.chatPartner = chatPartner;
        createLook();
        initComponents();
    }

    private void createLook() {
        setLayout(new GridLayout());
    }

    private void initComponents() throws IOException {
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Main.getColor("BarBackground"));
        add(mainPanel);

        imagePanel = new JPanel(new BorderLayout());
        imagePanel.setOpaque(false);
        imagePanel.add(new JLabel("  "), BorderLayout.LINE_START);
        mainPanel.add(imagePanel, BorderLayout.LINE_START);

        profilePicture = UtilMethods.loadRoundProfileIcon(this.chatPartner.getProfilePicture());
        imageLabel = new JLabel(profilePicture);
        imagePanel.add(imageLabel, BorderLayout.CENTER);

        centerPanel = new JPanel(new MigLayout("", "[grow,fill]"));
        centerPanel.setOpaque(false);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        userNameLabel = new JLabel(chatPartner.getNickName());
        userNameLabel.setFont(Main.getFont("Special"));
        centerPanel.add(userNameLabel);
    }

    public void changeUserName() {

    }

}
