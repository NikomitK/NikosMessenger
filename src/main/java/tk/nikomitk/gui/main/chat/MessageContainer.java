package tk.nikomitk.gui.main.chat;

import com.formdev.flatlaf.FlatDarculaLaf;
import net.miginfocom.swing.MigLayout;
import tk.nikomitk.Main;
import tk.nikomitk.messenger.Message;

import javax.swing.*;
import java.awt.*;

// TODO make message accessible for editing
// TODO selectable colors (and choose better default colors, maybe light-blue), (custom)properties for enemycolor and own color
public class MessageContainer extends JPanel {

    private final GraphicMessage graphicMessage;
    private final Message message;

    public MessageContainer(Message message, boolean isFromMe) {
        createLook();
        FlatDarculaLaf.setup();
        this.message = message;
        // Still no clue how to use this layout
        String fromWho = "EMB";
        String orientation = "dock west";
        if (isFromMe) {
            fromWho = "FMB";
            orientation = "dock east";
        }
        Color color = Main.getColor(fromWho);
        UIManager.put("Button.disabledBackground", color);
        graphicMessage = new GraphicMessage(message);
        add(graphicMessage, orientation);
    }

    private void createLook() {
        UIManager.put("Button.arc", 25);
        setMinimumSize(new Dimension(40, 35));
        setMaximumSize(new Dimension(10000, 10000));
        setLayout(new MigLayout("", "[grow,fill]"));
        setOpaque(false);
    }

    public void changeColor(Color color) {
        // only used in test for now
        UIManager.put("Button.disabledBackground", color);
        SwingUtilities.updateComponentTreeUI(this);
    }

    public boolean isEqual(Message message) {
        return message.equals(this.message);
    }

}
