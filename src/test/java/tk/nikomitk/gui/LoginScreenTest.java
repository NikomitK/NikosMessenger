package tk.nikomitk.gui;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginScreenTest {

    @Test
    void loginTest() throws InterruptedException {
        LoginScreen loginScreen = new LoginScreen(true);
        Thread.sleep(10000);
    }

}