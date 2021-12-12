package tk.nikomitk.messenger;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

// TODO copy most of prototype message class
@Data
public class Message {
    private String sender;
    private String receiver;
    private String text;
    private String sentDate;
    private String type;

    // INPUT NEEDS A :, probably useless now
    public Message(String input, String username) {
        String[] test = input.split("[.:]");
        type = text;
        receiver = test[0];
        text = test[1];
        this.sender = username;
        sentDate = new SimpleDateFormat("dd/MM/yy HH:mm").format(new Date());
    }

    public String getTime() {
        return sentDate.split(" ")[1];
    }
}
