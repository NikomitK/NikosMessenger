package tk.nikomitk.gui.login;

import lombok.Getter;
import tk.nikomitk.Main;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SignUpPanel extends JPanel {

    private JPanel mainPanel;
    private JLabel usernameLabel;
    private JTextField usernameField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JLabel repeatPasswordLabel;
    private JPasswordField repeatPasswordField;
    private JPanel checkBoxPanel;
    private JCheckBox rememberMeBox;
    private JCheckBox showPasswordBox;
    private JPanel buttonPanel;
    @Getter
    private JButton signUpButton;
    private JButton switchToLogInButton;

    public SignUpPanel(ActionListener switchToLogIn, ActionListener signUpListener) {
        initComponents(switchToLogIn, signUpListener);
    }

    private void initComponents(ActionListener switchToLogIn, ActionListener signUpListener) {
        setLayout(new GridLayout());
        mainPanel = new JPanel(new GridLayout(8, 1));
        add(mainPanel);

        usernameLabel = new JLabel("    Username");
        mainPanel.add(usernameLabel);

        usernameField = new JTextField();
        mainPanel.add(usernameField);

        passwordLabel = new JLabel("    Password");
        mainPanel.add(passwordLabel);

        passwordField = new JPasswordField();
        mainPanel.add(passwordField);

        repeatPasswordLabel = new JLabel("  Repeat Password");
        mainPanel.add(repeatPasswordLabel);

        repeatPasswordField = new JPasswordField();
        mainPanel.add(repeatPasswordField);

        checkBoxPanel = new JPanel(new GridLayout(1, 2));
        mainPanel.add(checkBoxPanel);

        rememberMeBox = new JCheckBox();
        rememberMeBox.setText("Remember me!");
        rememberMeBox.addChangeListener(e -> Main.settings.setRememberMe(rememberMeBox.isSelected()));
        checkBoxPanel.add(rememberMeBox);

        showPasswordBox = new JCheckBox();
        showPasswordBox.setText("Show password!");
        checkBoxPanel.add(showPasswordBox);


        buttonPanel = new JPanel(new GridLayout(1, 2));
        mainPanel.add(buttonPanel);

        signUpButton = new JButton("Sign me in!");
        signUpButton.setFocusable(false);
        signUpButton.putClientProperty("JButton.buttonType", "roundRect");
        signUpButton.addActionListener(signUpListener);
        buttonPanel.add(signUpButton);

        switchToLogInButton = new JButton("Switch to log in!");
        switchToLogInButton.putClientProperty("JButton.buttonType", "roundRect");
        switchToLogInButton.addActionListener(switchToLogIn);
        buttonPanel.add(switchToLogInButton);
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return passwordField.getText();
    }

    public String getRepeatedPassword() {
        return repeatPasswordField.getText();
    }

    public void passwordsDoNotMatch() {
        passwordField.putClientProperty("JComponent.outline", Color.red);
        repeatPasswordField.putClientProperty("JComponent.outline", Color.red);
        this.revalidate();
        JOptionPane.showMessageDialog(this, "Passwords do not match!");
    }

}
