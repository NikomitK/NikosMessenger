package tk.nikomitk.gui.login;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLightLaf;
import tk.nikomitk.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


// Did login and signup as different panels because it's easier to deal with actionlisteners, button texts etc.
public class LoginScreen extends JFrame {

    private LogInPanel logInPanel;
    private SignUpPanel signUpPanel;

    public LoginScreen(boolean rememberMe){
        initComponents();
        setGuiOptions();
        setVisible(true);
        authfromRemembered(rememberMe);
    }

    private void authfromRemembered(boolean rememberMe){
        if(!rememberMe) return;
        if(Main.authenticate(Main.settings.getUsername(), Main.settings.getPassword())) this.dispose();
    }

    private void setGuiOptions(){
        // TODO icon
//        setSize(450,270); unsure with the size
        setSize(350,230);
        setResizable(false);
        setTitle("Log in!");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initComponents(){
        UIManager.put("TextComponent.arc", 999);
        setLayout(new GridLayout());

        ActionListener switchToSignUp = e -> {
            logInPanel.setVisible(false);
            add(signUpPanel);
            remove(logInPanel);
            signUpPanel.setVisible(true);
            switchDefaultButton(false);
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
            switchDefaultButton(true);
            setSize(350,230);
            setTitle("Log in!");
        };
        ActionListener signUpListener = e -> signUp();
        signUpPanel = new SignUpPanel(switchToLogIn, signUpListener);
        switchDefaultButton(true);
        System.out.println("INIT DONE...");
    }

    private void signUp(){
        String username = signUpPanel.getUsername();
        String password = signUpPanel.getPassword();
        String repeatedPassword = signUpPanel.getRepeatedPassword();
        if(!password.equals(repeatedPassword)) {
            signUpPanel.passwordsDoNotMatch();
            return;
        }
        String response = Main.signUp(username, password);
        if(response.contains("true")) {
            this.dispose();
        } else if(response.contains("not available")) {
            JOptionPane.showMessageDialog(this, "This username is not available!");
        } else {
            JOptionPane.showMessageDialog(this, "Some error happened, idk");
        }
    }

    private void authenticate() {
        // TODO send hashed password to server
        if(Main.authenticate(logInPanel.getUsername(), logInPanel.getPassword())) this.dispose();
    }

    private void switchDefaultButton(boolean toLogin){
        if(toLogin) getRootPane().setDefaultButton(logInPanel.getLoginButton());
        if(!toLogin) getRootPane().setDefaultButton(signUpPanel.getSignUpButton());
        this.revalidate();
    }

}
