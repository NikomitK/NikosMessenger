package tk.nikomitk.gui.main.chatlist;

import tk.nikomitk.Main;
import tk.nikomitk.gui.main.MainGui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class AddListingButton extends JButton {

    public AddListingButton() {
        createLook();
        setText("<html><font size=\"+4\"><b>+</b></font></html>");
        addActionListener(e -> {
            String userName = JOptionPane.showInputDialog("Username?");
            if (userName == null || userName.equals("") || userName.equals(Main.getUser().getUserName())) return;
            try {
                MainGui.openChat(userName);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }

    private void createLook() {
        setMinimumSize(new Dimension(20, 20));
        setPreferredSize(new Dimension(40, 40));
        setMaximumSize(new Dimension(50, 50));
        setOpaque(false);
        setContentAreaFilled(false);
        setToolTipText("Add a new Chat!");
    }


}
