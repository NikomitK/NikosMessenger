package tk.nikomitk.gui.main.chatlist;

import lombok.Getter;
import net.miginfocom.swing.MigLayout;
import tk.nikomitk.Main;
import tk.nikomitk.gui.main.MainGui;
import tk.nikomitk.gui.utils.UtilMethods;
import tk.nikomitk.messenger.Message;
import tk.nikomitk.messenger.User;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ChatListing extends JButton {

    @Getter
    private final User chatPartner;
    private final String lastMessageString;
    private JPanel mainPanel;
    private JPanel imagePanel;
    private Icon profilePicture;
    private JLabel imageLabel;
    private JPanel textPanel;
    private JLabel chatPartnerLabel;
    private JLabel lastMessageLabel;
    private int unreadMessages; // To be implemented

    public ChatListing(User chatPartner, String lastMessageString) throws IOException {
        this.chatPartner = chatPartner;
        this.lastMessageString = lastMessageString;
        setLook();
        initComponents();
    }

    private void setLook() {
        setLayout(new GridLayout());
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Main.getColor("ButtonSeparator")));
        setContentAreaFilled(false);
        setOpaque(false);
        setMinimumSize(new Dimension(220, 70));
        setMaximumSize(new Dimension(1000, 70)); // don't wanna mess with the layout to disable growy
    }

    private void initComponents() throws IOException {
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);
        add(mainPanel);

        imagePanel = new JPanel(new GridLayout());
        imagePanel.setOpaque(false);
        mainPanel.add(imagePanel, BorderLayout.WEST);

        profilePicture = UtilMethods.loadRoundProfileIcon(chatPartner.getProfilePicture());
        imageLabel = new JLabel(profilePicture);
        imagePanel.add(imageLabel);

        textPanel = new JPanel(new MigLayout("", "[grow,fill]"));
        textPanel.setOpaque(false);
        mainPanel.add(textPanel, BorderLayout.CENTER);
        chatPartnerLabel = new JLabel("  " + chatPartner.getNickName());
        chatPartnerLabel.setOpaque(false);
        chatPartnerLabel.setFont(Main.getFont("Special"));
        chatPartnerLabel.setToolTipText(chatPartner.getUserName());
        textPanel.add(chatPartnerLabel, "gapy 10, wrap, dock north");

        // idk but I need a space more because it starts a bit earlier than the name
        lastMessageLabel = new JLabel("   " + this.lastMessageString);
        lastMessageLabel.setMaximumSize(new Dimension(300, 15));
        lastMessageLabel.setOpaque(false);
        textPanel.add(lastMessageLabel);

        addActionListener(e -> {
            try {
                MainGui.reopenChat(chatPartner);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    public boolean changeLastMessage(User chatPartner, Message message) {
        if (this.chatPartner.getUserName().equals(chatPartner.getUserName())) {
            addLastMessage(message.getText());
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
        lastMessageLabel.setText("   " + message);
    }

}
