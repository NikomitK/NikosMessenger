package tk.nikomitk.gui.main.chat;

import tk.nikomitk.Main;
import tk.nikomitk.gui.main.MainGui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

// TODO find better name
// TODO maybe emoji/attachment buttons
// TODO maybe part of mainGui, not chatpanel, just hidden
public class MessageBar extends JPanel {

    private JPanel mainPanel;
    private JTextField messageField;
    private JButton sendButton;

    public MessageBar(ChatPanel chatPanel) {
        createLook();
        initComponents(chatPanel);
    }

    private void createLook() {
        setLayout(new GridLayout());
        setPreferredSize(new Dimension(1000, 50));
        setBackground(Main.getColor("ChatBackground"));
    }

    private void initComponents(ChatPanel chatPanel) {
        mainPanel = new JPanel(new BorderLayout()); // maybe flowLayout
        mainPanel.setOpaque(false);
        add(mainPanel);

        messageField = new JTextField();
        messageField.putClientProperty("JComponent.roundRect", true);
        messageField.grabFocus();
        messageField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (messageField.getText().length() == 0) mainPanel.remove(sendButton);

                else {
                    mainPanel.add(sendButton, BorderLayout.LINE_END);
                    MainGui.getFrame().getRootPane().setDefaultButton(sendButton);
                }
                mainPanel.revalidate();
            }
        });
        mainPanel.add(messageField, BorderLayout.CENTER);

        sendButton = new JButton("Send!");
        sendButton.putClientProperty("JButton.buttonType", "roundRect");
        sendButton.addActionListener(e -> {
            chatPanel.addMyMessage(messageField.getText());
            messageField.setText("");
        });
    }
}
