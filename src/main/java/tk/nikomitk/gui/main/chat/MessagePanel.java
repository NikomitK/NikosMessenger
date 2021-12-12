package tk.nikomitk.gui.main.chat;

import net.miginfocom.swing.MigLayout;
import tk.nikomitk.gui.utils.SmartScroller;
import tk.nikomitk.messenger.Message;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

//TODO selectable background background
public class MessagePanel extends JPanel {

    private final JPanel mainPanel;
    private final JPanel contentPanel;
    private final JScrollPane scrollPane;
    private final List<MessageContainer> messages;

    public MessagePanel() {
        setLayout(new GridLayout());
        mainPanel = new JPanel(new GridLayout());
        add(mainPanel);

        contentPanel = new JPanel(new MigLayout("", "[grow,fill]"));
        scrollPane = new JScrollPane(contentPanel);
        new SmartScroller(scrollPane); // see comments in class
        mainPanel.add(scrollPane);
        messages = new ArrayList<>();
    }

    public void addMessage(Message message, boolean isFromMe) {
        MessageContainer thisMessageContainer = new MessageContainer(message, isFromMe);
        messages.add(thisMessageContainer);
        contentPanel.add(thisMessageContainer, "wrap");
        contentPanel.revalidate();
        // If this doesnt work, revalidate in chatpanel
    }

}
