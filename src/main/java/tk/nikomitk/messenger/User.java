package tk.nikomitk.messenger;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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

    @JsonIgnore
    public String getJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode messageNode = objectMapper.valueToTree(this);
        try {
            return objectMapper.writer().writeValueAsString(messageNode);
        } catch (Exception e) {
            return "not availible";
        }
    }

}