package tk.nikomitk.messenger;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class Request {

    private final String type;
    private String username;
    private String hashedPassword;
    private String encryptedText;

    private Request(String... type) {
        this.type = type[0];
        switch (type[0]) {
            case "request":
                thisIsAMessageRequest(type);
                break;
            case "login":
                thisIsALogIn(type);
                break;
            case "signup":
                thisIsASignUp(type);
                break;
            case "user":
                thisIsAUserRequest(type);
                break;
            case "message":
                thisIsAMessage(type);
                break;
        }
    }

    // TODO encrypt everything except for type
    public static String getRequestJson(String... type) throws JsonProcessingException {
        Request request = new Request(type);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode requestNode = objectMapper.valueToTree(request);
        return objectMapper.writer().writeValueAsString(requestNode);
    }

    private void thisIsAMessageRequest(String[] request) {
        // {"request", "this users name"}
        this.username = request[1];
    }

    private void thisIsALogIn(String[] login) {
        // {"login", "attempted username", "attempted hashed password"}
        this.username = login[1];
        this.hashedPassword = login[2];
    }

    private void thisIsASignUp(String[] signUp) {
        // {"signup", "free username", "password"}
        this.username = signUp[1];
        this.hashedPassword = signUp[2];
    }

    private void thisIsAUserRequest(String[] user) {
        // {"user", "username to get information for"}
        this.username = user[1];
    }

    private void thisIsAMessage(String[] message) {
        // {"message", "person the message is for", "encrypted message with other meta-info"}
        this.username = message[1]; // receiver
        this.encryptedText = message[2];
    }
}
