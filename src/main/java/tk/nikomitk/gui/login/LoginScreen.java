package tk.nikomitk.gui.login;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginScreen extends JFrame {

    private LogInPanel logInPanel;
    private JPanel signUpPanel;

    public LoginScreen(boolean rememberMe){
        // TODO switch with a button
        setDarkTheme(true);
        initGui();
        setGuiOptions();
        setVisible(true);
    }

    private void authenticate() {
        // TODO send hashed password to server
    }

    private void setGuiOptions(){
        // TODO icon
//        setSize(450,270); unsure with the size
        setSize(350,230);
        setResizable(false);
        setTitle("Log in!");
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
        setLayout(new GridLayout());

        ActionListener switchToSignUp = e -> {
            logInPanel.setVisible(false);
            add(signUpPanel);
            remove(logInPanel);
            signUpPanel.setVisible(true);
            setSize(350, 300);
            setTitle("Sign up!");
        };
        ActionListener logInListener = e -> authenticate();
        logInPanel = new LogInPanel(switchToSignUp, logInListener);
        add(logInPanel);

        ActionListener switchToLogIn = e -> {
            signUpPanel.setVisible(false);
            logInPanel.setVisible(true);
            add(logInPanel);
            remove(signUpPanel);
            setSize(350,230);
            setTitle("Log in!");
        };
        ActionListener signUpListener = e -> signUp();
        signUpPanel = new SignUpPanel(switchToLogIn, signUpListener);
//        signUpPanel.setVisible();
        System.out.println("INIT DONE...");
    }

    private void signUp(){

    }

    private void switchPanels(){
        add(signUpPanel);
    }

}
