package tk.nikomitk.gui.main.chat;

import com.formdev.flatlaf.FlatDarculaLaf;
import net.miginfocom.swing.MigLayout;
import tk.nikomitk.messenger.Message;

import javax.swing.*;
import java.awt.*;

// TODO make message accessible for editing
// TODO selectable colors (and choose better default colors)
public class MessageContainer extends JPanel {

    public MessageContainer(Message message, boolean isFromMe) {

        setMinimumSize(new Dimension(40, 35));
        setMaximumSize(new Dimension(10000, 10000));
        FlatDarculaLaf.setup();
        // Still no clue how to use this layout
        setLayout(new MigLayout("", "[grow,fill]"));
        Color color = Color.decode("#444444");
        String orientation = "dock west";
        if (isFromMe) {
            color = Color.decode("#2A474B");
            orientation = "dock east";
        }
        UIManager.put("Button.disabledBackground", color);
        GraphicMessage thisMessageLabel = new GraphicMessage(message);
        add(thisMessageLabel, orientation);
    }

}
