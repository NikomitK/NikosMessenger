package tk.nikomitk.gui;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;

public class LoginScreen extends JFrame {

    private JPanel mainPanel;
    private JLabel usernameLabel;
    private JTextField usernameField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JPanel checkBoxPanel;
    private JCheckBox rememberMeBox;
    private JCheckBox showPasswordBox;
    private JButton loginButton;

    public LoginScreen(boolean rememberMe){
        // TODO sign up as another tab
        setDarkTheme(true);
        initGui();
        setGuiOptions();
        setVisible(true);
    }

    private void authenticate() {
        // TODO send hashed password to server
    }

    private void setGuiOptions(){
        // TODO not resizable, size etc, title, icon
        setSize(450,270);
        setResizable(false);
        setTitle("Log in");
    }

    private void setDarkTheme(boolean darkTheme) {
        if(darkTheme){
            FlatDarculaLaf.setup();
        } else {
            FlatLightLaf.setup();
        }
        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
        SwingUtilities.updateComponentTreeUI(this);

    }

    private void initGui(){
        // TODO initialize components

        UIManager.put("TextComponent.arc", 999);
        UIManager.put("Button.arc", 999); // i have no idea why but this doesn't work anymore, while the line above does

        mainPanel = new JPanel(new GridLayout(6,1));
        add(mainPanel);

        usernameLabel = new JLabel("    Username");
        mainPanel.add(usernameLabel);

        usernameField = new JTextField();
        mainPanel.add(usernameField);

        passwordLabel = new JLabel("    Password");
        mainPanel.add(passwordLabel);

        passwordField = new JPasswordField();
        mainPanel.add(passwordField);

        checkBoxPanel = new JPanel(new GridLayout(1,2));

        rememberMeBox = new JCheckBox();
        rememberMeBox.setText("Remember me!");
        checkBoxPanel.add(rememberMeBox);

        showPasswordBox = new JCheckBox();
        showPasswordBox.setText("Show password!");
        checkBoxPanel.add(showPasswordBox);

        mainPanel.add(checkBoxPanel);

        loginButton = new JButton("Log me in!");
        loginButton.putClientProperty("JButton.buttonType", "roundRect");
        mainPanel.add(loginButton);

        System.out.println("INIT DONE...");
    }
}
