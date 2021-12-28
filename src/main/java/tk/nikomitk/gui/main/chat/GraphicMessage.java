package tk.nikomitk.gui.main.chat;

import com.formdev.flatlaf.extras.components.FlatButton;
import net.miginfocom.swing.MigLayout;
import tk.nikomitk.Main;
import tk.nikomitk.gui.main.MainGui;
import tk.nikomitk.messenger.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// TODO maybe as textpane, but text not centerable
// TODO when finished, reaction-emoji like discord and instagram

// Extends button for shape, didn't want to create a custom weird border
public class GraphicMessage extends FlatButton {

    private final JPanel mainPanel;
    private final JPanel textPanel;
    private final JLabel textLabel;
    private final JPanel rightPanel;
    private final JPanel optionsPanel;
    private final JLabel optionsLabel;
    private final JPanel datePanel;
    private final JLabel dateLabel;
    private final JPopupMenu popupMenu;
    private Message message;

    // TODO maybe rollover"listener" to show options button only when hovered over message (whatsapp web)
    // TODO maybe enable button to select
    public GraphicMessage(Message message) {
        this.message = message;
        createLook();

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setMaximumSize(new Dimension(300, 10000));
        mainPanel.setOpaque(false);
        add(mainPanel, BorderLayout.CENTER);

        textPanel = new JPanel(new MigLayout());
        textPanel.setOpaque(false);
        mainPanel.add(textPanel, BorderLayout.CENTER);

        textLabel = new JLabel();
        textLabel.setMaximumSize(new Dimension(300, 10000));
        textLabel.setFont(Main.getFont("Message"));
        textLabel.setForeground(Main.getColor("TextColor"));
        // html tags to add auto-newline to label, better way?
        textLabel.setText("<html><p>" + this.message.getText() + "</p></html>");
        textPanel.add(textLabel, "push, dock west");

        rightPanel = new JPanel(new BorderLayout());
        rightPanel.setOpaque(false);
        mainPanel.add(rightPanel, BorderLayout.EAST);
        // I hate layouts
        optionsPanel = new JPanel(new BorderLayout());
        optionsPanel.setOpaque(false);
        rightPanel.add(optionsPanel, BorderLayout.PAGE_START);

        optionsLabel = new JLabel("<html>&#8285;</html>");
        optionsLabel.setFont(Main.getFont("Message"));
        optionsLabel.setForeground(Main.getColor("TextColor"));
        optionsLabel.setOpaque(false);
        optionsLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                popupMenu.show(e.getComponent(), e.getX(), e.getY());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO necessary?
                super.mouseReleased(e);
            }
        });
        optionsPanel.add(optionsLabel, BorderLayout.LINE_END);

        datePanel = new JPanel(new BorderLayout());
        datePanel.setOpaque(false);
        rightPanel.add(datePanel, BorderLayout.PAGE_END);

        dateLabel = new JLabel(this.message.getTime());
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        dateLabel.setForeground(Main.getColor("TextColor"));
        datePanel.add(dateLabel, BorderLayout.LINE_END);

        // TODO menu items copy, answer, react, edit, delete etc. maybe in extra function
        popupMenu = createPopup();
    }

    private void createLook() {
        setEnabled(false);
        setLayout(new BorderLayout());
        setBorderPainted(false);
        // if preferred size is set, messages won't resize
        setMinimumSize(new Dimension(40, 35));
        setMaximumSize(new Dimension(300, 10000));
    }

    private JPopupMenu createPopup(){
        // TODO complete this crap
        JPopupMenu thisPopup = new JPopupMenu();

        JMenuItem copyItem = new JMenuItem("copy");
        copyItem.addActionListener(e -> {
            System.out.println("copy");
        });
//        thisPopup.add(copyItem);

        JMenuItem answerItem = new JMenuItem("answer");
        answerItem.addActionListener(e -> {
            System.out.println("answer");
        });
//        thisPopup.add(answerItem);

        JMenuItem reactItem = new JMenuItem("react");
        reactItem.addActionListener(e -> {
            System.out.println("react");
        });
//        thisPopup.add(reactItem);

        JMenuItem editItem = new JMenuItem("edit");
        editItem.addActionListener(e -> {
            String newText = JOptionPane.showInputDialog("");
            if(newText == null) return;
            editMessage(newText);
        });
        thisPopup.add(editItem);

        JMenuItem deleteItem = new JMenuItem("delete");
        deleteItem.addActionListener(e -> {
            System.out.println("delete");
            MainGui.deleteMessage(message);
        });
        thisPopup.add(deleteItem);
        return thisPopup;
    }

    public void editMessage(String newText){
        message.setText(newText);
        textLabel.setText("<html><p>" + this.message.getText() + "</p></html>");
    }

}
