package tk.nikomitk.gui.main.chat;

import tk.nikomitk.gui.utils.UtilMethods;
import tk.nikomitk.messenger.User;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

// TODO maybe "last seen" and "typing"
public class ChatpartnerBar extends JPanel {

    private final JPanel mainPanel;
    private final Icon profilePicture;
    private final JLabel imageLabel; // icon is no JComponent, can't be added to panel
    private String status; // TODO implement this maybe thread for ... "animation"
    private final User chatPartner;

    // TODO check if chatPartner is spelled the same everywhere
    // TODO remove exception?
    public ChatpartnerBar(User chatPartner) throws IOException {
        this.chatPartner = chatPartner;
        setLayout(new GridLayout());
        mainPanel = new JPanel(new BorderLayout());

        profilePicture = UtilMethods.loadProfileIcon(this.chatPartner.getProfilePicture());
        imageLabel = new JLabel(profilePicture);
        mainPanel.add(imageLabel, BorderLayout.WEST);
    }

}
