package tk.nikomitk.gui.main.chatlist;

import net.miginfocom.swing.MigLayout;
import tk.nikomitk.messenger.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class ChatListPanel extends JPanel {

    private final JPanel mainPanel;
    private final JScrollPane scrollPane;
    private final JPanel contentPanel;
    private final List<ChatListing> openChatList;

    public ChatListPanel() {
        openChatList = new ArrayList<>();
        setLayout(new GridLayout());

        mainPanel = new JPanel(new GridLayout());
        add(mainPanel);

        // documentation for miglayout is terrible, but it's the easiest way
        contentPanel = new JPanel(new MigLayout("", "[grow,fill]"));
        scrollPane = new JScrollPane(contentPanel);
        mainPanel.add(scrollPane);
    }

    public void addChatListing(User chatPartner, String lastMessage) {
        try {
            ChatListing tempListing = new ChatListing(chatPartner, lastMessage);
            openChatList.add(tempListing);
            contentPanel.add(tempListing, "wrap");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeNickname(User chatPartner, String nickname) {
        // Maybe I don't need this cause I make the change button on the ChatListing
        // !!But if I implement self-set nicknames, this could be of use!!
        for (ChatListing c : openChatList) {
            if (c.changeNickname(chatPartner, nickname)) return;
        }
    }

    public void changeLastMessage(User chatPartner, String message) {
        // Call this when the messages get updated and there are new ones
        for (ChatListing c : openChatList) {
            // returns a boolean if the criteria fit
            if (c.changeLastMessage(chatPartner, message)) return;
        }
    }

    private void createChatlisting() {
        // TODO add a button with the same size of a chatlisting at the bottom to start a new chat

    }

}
