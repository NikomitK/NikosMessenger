package tk.nikomitk.gui.main.chat;

import com.formdev.flatlaf.extras.components.FlatButton;
import net.miginfocom.swing.MigLayout;
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
    // TODO make editable
    public GraphicMessage(Message message) {
        this.message = message;
        createLook();

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setMaximumSize(new Dimension(300, 10000));
        // TODO opaque for panels necessary?
        mainPanel.setOpaque(false);
        add(mainPanel, BorderLayout.CENTER);

        textPanel = new JPanel(new MigLayout());
        textPanel.setOpaque(false);
        mainPanel.add(textPanel, BorderLayout.CENTER);

        textLabel = new JLabel();
        textLabel.setMaximumSize(new Dimension(300, 10000));
        textLabel.setFont(new Font("Arial", Font.BOLD, 15));
        textLabel.setForeground(Color.black);
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

        optionsLabel = new JLabel("...");
        optionsLabel.setFont(new Font("Arial", Font.BOLD, 15));
        optionsLabel.setForeground(Color.BLACK);
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
        dateLabel.setForeground(Color.black);
        datePanel.add(dateLabel, BorderLayout.LINE_END);

        // TODO menu items copy, answer, react, edit, delete etc. maybe in extra function
        popupMenu = new JPopupMenu();
        popupMenu.add(new JMenuItem("copy"));
        popupMenu.add(new JMenuItem("answer"));
        popupMenu.add(new JMenuItem("react"));
    }

    private void createLook() {
        setEnabled(false);
        setLayout(new BorderLayout());
        // if preferred size is set, messages won't resize
        setMinimumSize(new Dimension(40, 35));
        setMaximumSize(new Dimension(300, 10000));
    }

}
