package tk.nikomitk.messenger;

import lombok.Data;

@Data
public class User {

    private int profilePicture;
    private String userName;
    private String nickName;
    // TODO maybe MOTD/info/idfk

    public User(String userName, int profilePicture) {
        this.profilePicture = profilePicture;
        this.userName = userName;
        this.nickName = userName;
    }

}