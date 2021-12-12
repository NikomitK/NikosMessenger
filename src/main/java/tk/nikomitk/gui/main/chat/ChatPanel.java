package tk.nikomitk.gui.main.chat;

import tk.nikomitk.messenger.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ChatPanel extends JPanel {
    // TODO for textfield to chat maybe custom button in custom textfield.
    // TODO maybe miglayout, messagefield resizable
    // TODO second constructor for chats that already exist, maybe jsonnode as parameter, unsure how chats will be stored
    // TODO define color schemes in properties, messages, background, top-bar, etc

    private User chatPartner;
    private JPanel mainPanel;
    private JPanel chatPanel;
    private JPanel messagePanel;
    private JTextField messageField;
    private JButton sendButton;
    private List<MessageContainer> messages; //maybe don't need this

    public ChatPanel(User chatPartner){
        this.chatPartner = chatPartner;
        mainPanel = new JPanel(new BorderLayout());

    }

}
