package tk.nikomitk.gui.main.chatlist;

import tk.nikomitk.gui.utils.UtilMethods;
import tk.nikomitk.messenger.User;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ChatListing extends JButton {

    private final String lastMessageString;
    private final User chatPartner;
    private final JPanel mainPanel;
    private final JPanel imagePanel;
    private final Icon profilePicture;
    private final JLabel imageLabel;
    private final JPanel textPanel;
    private final JLabel chatPartnerLabel;
    private final JLabel lastMessageLabel;
    private int unreadMessages;

    public ChatListing(User chatPartner, String lastMessageString) throws IOException {
        this.chatPartner = chatPartner;
        this.lastMessageString = lastMessageString;


        setLayout(new GridLayout());
        setMinimumSize(new Dimension(220, 60));
        setMaximumSize(new Dimension(10000, 60)); // don't wanna mess with the layout to disable growy

        mainPanel = new JPanel(new BorderLayout());
        add(mainPanel);

        imagePanel = new JPanel(new GridLayout());
        mainPanel.add(imagePanel, BorderLayout.WEST);

        profilePicture = UtilMethods.loadProfileIcon(chatPartner.getProfilePicture());
        imageLabel = new JLabel(profilePicture);
        imageLabel.putClientProperty("JComponent.roundRect", true); // TODO make round, this is not working, maybe round button?
        imagePanel.add(imageLabel);

        textPanel = new JPanel(new GridLayout(2, 1));
        mainPanel.add(textPanel, BorderLayout.CENTER);
        //TODO die texte näher zueinander machen
        chatPartnerLabel = new JLabel("  " + chatPartner.getNickName());
        chatPartnerLabel.setFont(chatPartnerLabel.getFont().deriveFont(Font.BOLD, 15)); // TODO check if setFont is necessary
        chatPartnerLabel.setToolTipText(chatPartner.getUserName());
        textPanel.add(chatPartnerLabel);

        lastMessageLabel = new JLabel("  " + this.lastMessageString);
        textPanel.add(lastMessageLabel);

    }

    public boolean changeLastMessage(User chatPartner, String message) {
        if (this.chatPartner.getUserName().equals(chatPartner.getUserName())) {
            addLastMessage(message);
            notifyMessage();
            return true;
        }
        return false;
    }

    public boolean changeNickname(User chatPartner, String nickname) {
        // TODO maybe these option dots on listing to change nickname
        if (this.chatPartner.getUserName().equals(chatPartner.getUserName())) {
            this.chatPartner.setNickName(nickname);
            chatPartnerLabel.setText(nickname);
            return true;
        }
        return false;
    }


    private void notifyMessage() {
        // TODO implement notification symbol, maybe with Borderlayout.east, increase unread messages, maybe pop sound
    }

    private void addLastMessage(String message) {
        lastMessageLabel.setText("  " + message);
    }

}
