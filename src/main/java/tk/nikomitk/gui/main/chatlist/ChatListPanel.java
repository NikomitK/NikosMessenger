package tk.nikomitk.gui.main.chatlist;

import net.miginfocom.swing.MigLayout;
import tk.nikomitk.Main;
import tk.nikomitk.gui.utils.UtilMethods;
import tk.nikomitk.messenger.Message;
import tk.nikomitk.messenger.User;

import javax.swing.*;
import java.awt.*;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;


public class ChatListPanel extends JPanel {

    private final List<ChatListing> openChatList;
    private JPanel mainPanel;
    private JScrollPane scrollPane;
    private JPanel contentPanel;
    private AddListingButton addListingButton;

    public ChatListPanel() {
        openChatList = new ArrayList<>();
        createLook();
        initComponents();
    }

    private void createLook() {
        setLayout(new GridLayout());
        setPreferredSize(new Dimension(400, 10000));
        setBackground(Main.getColor("PanelBackground"));
    }

    private void initComponents() {
        mainPanel = new JPanel(new MigLayout("", "[grow,fill]"));
        Color backgroundColor = Main.getColor("PanelBackground");
        mainPanel.setBackground(backgroundColor);
        add(mainPanel);

        // documentation for miglayout is terrible, but it's the easiest way
        contentPanel = new JPanel(new MigLayout("btt", "[grow,fill]"));
        contentPanel.setOpaque(false);
        scrollPane = new JScrollPane(contentPanel);
        scrollPane.setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setOpaque(false);
        mainPanel.add(scrollPane, "dock north");
    }

    public void addChatListing(User chatPartner, String lastMessage) {
        try {
            ChatListing tempListing = new ChatListing(chatPartner, lastMessage);
            for (int i = 0; i < openChatList.size(); i++) {
                if (openChatList.get(i).getChatPartner().getUserName().equals(chatPartner.getUserName()))
                    throw new InvalidParameterException("Chat already exists");
            }
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

    public void changeLastMessage(Message message) {
        // Call this when the messages get updated and there are new ones
        User chatPartner = UtilMethods.getChatPartner(message);
        for (ChatListing c : openChatList) {
            // returns a boolean if the criteria fit
            if (c.changeLastMessage(chatPartner, message)) return;
        }
        addChatListing(chatPartner, message.getText());
    }

}
