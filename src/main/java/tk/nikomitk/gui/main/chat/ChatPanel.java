package tk.nikomitk.gui.main.chat;

import lombok.Getter;
import tk.nikomitk.Main;
import tk.nikomitk.gui.main.MainGui;
import tk.nikomitk.messenger.Chat;
import tk.nikomitk.messenger.Message;
import tk.nikomitk.messenger.User;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class ChatPanel extends JPanel {

    @Getter
    private final User chatPartner;
    @Getter
    private final Chat chat;
    private JPanel mainPanel;
    private ChatPartnerBar chatPartnerBar;
    private MessagePanel messagePanel;
    private MessageBar messageBar;
    private List<MessageContainer> messages;

    public ChatPanel(User chatPartner) throws IOException {
        this.chatPartner = chatPartner;
        createLook();
        initComponents();
        chat = Main.getChat(this.chatPartner.getUserName());
        createHistory(chat);
    }

    private void createLook() {
        setLayout(new GridLayout());
    }

    private void initComponents() throws IOException {
        mainPanel = new JPanel(new BorderLayout());
        add(mainPanel);

        chatPartnerBar = new ChatPartnerBar(chatPartner);
        mainPanel.add(chatPartnerBar, BorderLayout.PAGE_START);

        messagePanel = new MessagePanel();
        mainPanel.add(messagePanel, BorderLayout.CENTER);

        messageBar = new MessageBar(this);
        mainPanel.add(messageBar, BorderLayout.PAGE_END);
    }

    public void addMyMessage(String text) {
        Message message = new Message(text, chatPartner);
        messagePanel.addMessage(message, true);
        Main.sendMessageToSocket(message);
        chat.addMessage(message);
        MainGui.newMessage(message);
    }

    public void addForeignMessage(Message message) {
        messagePanel.addMessage(message, false);
    }

    public void deleteMessage(Message message) {
        chat.deleteMessage(message);
        saveChat();
        messagePanel.deleteMessage(message);
        messagePanel.revalidate();
    }

    private void createHistory(Chat chat) {
        for (Message m : chat.getMessages()) {
            messagePanel.addMessage(m, m.getSender().equals(Main.getUser().getUserName()));
        }
    }

    public void saveChat() {
        Main.saveChat(chat);
    }

}
