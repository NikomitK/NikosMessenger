package tk.nikomitk.gui;

import org.junit.jupiter.api.Test;
import tk.nikomitk.gui.login.LoginScreen;

class LoginScreenTest {

    @Test
    void loginTest() throws InterruptedException {
        LoginScreen loginScreen = new LoginScreen(true);
        while(true){
            Thread.sleep(10000);
        }
    }

}