package tk.nikomitk.gui.login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LogInPanel extends JPanel {

    private JPanel mainPanel;
    private JLabel usernameLabel;
    private JTextField usernameField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JPanel checkBoxPanel;
    private JCheckBox rememberMeBox;
    private JCheckBox showPasswordBox;
    private JPanel buttonPanel;
    private JButton loginButton;
    private JButton switchToSignUpButton;

    // TODO listener for log in
    public LogInPanel(ActionListener switchToSignUp, ActionListener logInListener){
        setLayout(new GridLayout());
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
        mainPanel.add(checkBoxPanel);

        rememberMeBox = new JCheckBox();
        rememberMeBox.setText("Remember me!");
        checkBoxPanel.add(rememberMeBox);

        showPasswordBox = new JCheckBox();
        showPasswordBox.setText("Show password!");
        checkBoxPanel.add(showPasswordBox);


        buttonPanel = new JPanel(new GridLayout(1,2));
        mainPanel.add(buttonPanel);

        loginButton = new JButton("Log me in!");
        loginButton.putClientProperty("JButton.buttonType", "roundRect");
        loginButton.addActionListener(logInListener);
        buttonPanel.add(loginButton);

        switchToSignUpButton = new JButton("Switch to sign up!");
        switchToSignUpButton.putClientProperty("JButton.buttonType", "roundRect");
        switchToSignUpButton.addActionListener(switchToSignUp);
        buttonPanel.add(switchToSignUpButton);
    }

}
