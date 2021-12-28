package tk.nikomitk;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import tk.nikomitk.gui.utils.UtilMethods;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Settings {

    private boolean rememberMe = false;
    private boolean darkmode = true;
    private boolean customServer = false;
    // TODO this can't be accessed without "registering" an account on the normal server, fix
    private String serverAdress = "nikosmessenger.ddns.net"; // ONLY USE THIS IF YOU WANT TO USE YOUR OWN SERVER
    private int serverPort = 6969;
    private List<String> blockedUsers = new ArrayList<>();
    private boolean UIDebug = false;
    private String username;
    private String password; // I KNOW I SHOULD BE SLAPPED FOR DOING THIS BUT THIS PROJECT WAS DONE TO LEARN SOME OTHER THINGS

    public Settings() {

    }

    public void safe() {
        try {
            new ObjectMapper().writeValue(new File(UtilMethods.getWorkingDirectory() + "settings.json"), this);
            System.out.println("safed");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("unsuccessful :C");
        }
    }

    public void setPassword(String password) {
        if (rememberMe) this.password = password;
    }

}
