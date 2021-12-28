package tk.nikomitk.messenger;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tk.nikomitk.Main;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Message {
    private String sender;
    private String receiver;
    private String text;
    private String sentDate;
    private String type;

    public Message(String input, String receiver) {
        this.receiver = receiver;
        text = input;
        this.sender = Main.getUser().getUserName();
        sentDate = new SimpleDateFormat("dd/MM/yy-HH:mm").format(new Date());
    }

    public Message(String text, User receiver) {
        // probably unnecessary
        this.sender = Main.getUser().getUserName();
        this.receiver = receiver.getUserName();
        this.text = text;
        this.sentDate = new SimpleDateFormat("dd/MM/yy-HH:mm").format(new Date());
    }

    @JsonIgnore
    public String getTime() {
        System.out.println(sentDate);
        return sentDate.split("-")[1];
    }

    // idk how to turn this into an outputstream
    public String toJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode messageNode = objectMapper.valueToTree(this);
        return objectMapper.writer().writeValueAsString(messageNode);
    }

    public String[] toOutputStringArray() throws JsonProcessingException {
        // TODO encrypt
        return new String[]{"message", receiver, toJson()};
    }
}
