package tk.nikomitk.gui.main;

import lombok.Getter;
import tk.nikomitk.Main;
import tk.nikomitk.gui.main.chat.ChatPanel;
import tk.nikomitk.gui.main.chatlist.ChatListPanel;
import tk.nikomitk.gui.main.chatlist.OptionsPanel;
import tk.nikomitk.gui.main.chatlist.ProfileBar;
import tk.nikomitk.gui.utils.UtilMethods;
import tk.nikomitk.messenger.Chat;
import tk.nikomitk.messenger.Message;
import tk.nikomitk.messenger.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.List;

public class MainGui {

    @Getter
    private static JFrame frame;
    private static JPanel mainPanel;
    private static JPanel rightPanel;
    private static JPanel leftPanel;
    private static OptionsPanel optionsPanel;
    private static ProfileBar profileBar;
    private static ChatListPanel chatListPanel;
    private static ChatPanel activeChatPanel; // create when chat is opened

    // TODO add initComps to every class ig
    public static void show() {
        // TODO maybe tabbed pane without visible tabs, to switch to options
        createFrame();
        initComponents();
        frame.revalidate();
    }

    private static void createFrame() {
        frame = new JFrame();
        frame.setTitle("Niko's Messenger");
        frame.setLayout(new GridLayout());
        frame.setSize(1000, 800);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (activeChatPanel != null) {
                    Main.saveChat(activeChatPanel.getChat());
                }
            }
        });
    }

    private static void initComponents() {
        mainPanel = new JPanel(new BorderLayout());
        frame.add(mainPanel);

        leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.lightGray));
        mainPanel.add(leftPanel, BorderLayout.LINE_START);

        optionsPanel = new OptionsPanel();

        profileBar = new ProfileBar();
        leftPanel.add(profileBar, BorderLayout.PAGE_START);

        chatListPanel = new ChatListPanel();
        createChatListings(Main.getChats());
        leftPanel.add(chatListPanel, BorderLayout.CENTER);

        rightPanel = new JPanel(new GridLayout());
        Color backgroundColor = Main.getColor("ChatBackground");
        rightPanel.setBackground(backgroundColor);
        mainPanel.add(rightPanel, BorderLayout.CENTER);
    }

    private static void createChatListings(List<Chat> chatList) {
        if (chatList.isEmpty()) return;
        for (Chat c : chatList) {
            User u = Main.getUserFromServer(c.getUserName());
            List<Message> messageList = c.getMessages();
            if (u == null) {
                u = new User("not available", 2);
                chatListPanel.addChatListing(u, "This user doesn't exist (anymore)!");
                continue;
            } else if (messageList.isEmpty()) {
                chatListPanel.addChatListing(u, "Nothing here yet");
                continue;
            }
            Message lastMessage = messageList.get(messageList.size() - 1);
            chatListPanel.addChatListing(u, lastMessage.getText());
        }
    }

    public static void openSettings() {
        chatListPanel.setVisible(false);
        leftPanel.add(optionsPanel);
        leftPanel.remove(chatListPanel);
        optionsPanel.setVisible(true);
    }

    public static void closeSettings() {
        optionsPanel.setVisible(false);
        leftPanel.add(chatListPanel);
        leftPanel.remove(optionsPanel);
        chatListPanel.setVisible(true);
    }

    public static void openChat(String username) throws IOException {
        User thisUser = Main.getUserFromServer(username);
        if (rightPanel.getComponentCount() > 0) {
            activeChatPanel.saveChat();
            rightPanel.remove(activeChatPanel);
        }
        activeChatPanel = new ChatPanel(thisUser);
        rightPanel.add(activeChatPanel);
        rightPanel.revalidate();
        chatListPanel.addChatListing(thisUser, "Nothing to see!");
    }

    public static void reopenChat(User user) throws IOException {
        if (rightPanel.getComponentCount() > 0) {
            activeChatPanel.saveChat();
            rightPanel.remove(activeChatPanel);
        }
        activeChatPanel = new ChatPanel(user);
        rightPanel.add(activeChatPanel);
        rightPanel.revalidate();
    }

    public static void newMessage(Message message) {
        if (activeChatPanel != null && activeChatPanel.getChatPartner().getUserName().equals(message.getSender())) {
            activeChatPanel.addForeignMessage(message);
            rightPanel.revalidate();
        }
        chatListPanel.changeLastMessage(message);
        chatListPanel.revalidate();
    }

    public static void deleteMessage(Message message) {
        activeChatPanel.deleteMessage(message);
        try {
            Message lastMessage = Main.getChat(UtilMethods.getChatPartner(message).getUserName()).getLastMessage();
            chatListPanel.changeLastMessage(lastMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
