package tk.nikomitk;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.FlatInspector;
import lombok.Getter;
import tk.nikomitk.gui.login.LoginScreen;
import tk.nikomitk.gui.main.MainGui;
import tk.nikomitk.gui.utils.UtilMethods;
import tk.nikomitk.messenger.Chat;
import tk.nikomitk.messenger.Message;
import tk.nikomitk.messenger.Request;
import tk.nikomitk.messenger.User;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;


// This will probably never get encryption cause I hate programming
public class Main {

    public static Settings settings;
    @Getter
    private static String mode;
    private static Properties props;
    @Getter
    private static User user;
    private static File chatStore;
    @Getter
    private static List<Chat> chats;
    private static Socket outputSocket;
    private static ObjectMapper objectMapper;
    private static String server;
    private static int port;
    private static boolean loggedIn;

    // TODO sort chatlistings after most recent
    public static void main(String[] args) throws IOException, InterruptedException {
        initSettings();
        objectMapper = new ObjectMapper();
        loadChats();
        loggedIn = false;
        new LoginScreen(settings.isRememberMe());
        while (!loggedIn) {
            System.out.println("Dings!, forgot how to do this better, not worth to look up");
            Thread.sleep(2000);
        }
        MainGui.show();
        while (true) {
            Thread.sleep(5000);
            try {
                if (user.getUserName() != null && !user.getUserName().equals("")) {
                    String response = sendToSocket(new String[]{"request", user.getUserName()});
                    if (response == null) continue;
                    JsonNode responseNode = objectMapper.readTree(response);
                    Message[] newMessages = objectMapper.treeToValue(responseNode, Message[].class);
                    refreshChats(newMessages);
                }
            } catch (Exception e) {
                e.printStackTrace();
                // without this, the messages won't be refreshed after error
            }
        }
    }

    public static Color getColor(String property) {
        return Color.decode(props.getProperty(getMode() + property));
    }

    public static Font getFont(String property) {
        String fontName = props.getProperty(property + "FontName");
        int fontStyle = Integer.parseInt(props.getProperty(property + "FontStyle"));
        int fontSize = Integer.parseInt(props.getProperty(property + "FontSize"));
        return new Font(fontName, fontStyle, fontSize);
    }

    public static String getSymbol(String property) {
        return props.getProperty(property);
    }

    private static void initSettings() {
        File settingsFile = new File(UtilMethods.getWorkingDirectory() + "settings.json");
        try {
            if (settingsFile.exists()) {
                settings = new ObjectMapper().readValue(settingsFile, Settings.class);
            } else {
                settings = new Settings();
            }
        } catch (Exception e) {
            e.printStackTrace();
            settings = new Settings();
        }
        if (settings.isDarkmode()) {
            FlatDarculaLaf.setup();
        } else FlatLightLaf.setup();
        server = settings.getServerAdress();
        port = settings.getServerPort();
        if (settings.isUIDebug()) FlatInspector.install("ctrl shift alt X");
        props = UtilMethods.loadProps();
        if (settings.isDarkmode()) {
            mode = "[dark]";
        } else {
            mode = "[light]";
        }
    }

    private static void refreshChats(Message[] messages) throws IOException {
        boolean exists = false;
        for (Message m : messages) {
            if (!userIsBlocked(m.getSender())) {
                for (Chat c : chats) {
                    if (c.getUserName().equals(m.getSender())) {
                        c.addMessage(m);
                        saveChat(c);
                        exists = true;
                        break;
                    }
                }
            }
            if (!exists) {
                Chat thisChat = getChat(m.getSender());
                chats.add(thisChat);
                thisChat.addMessage(m);
                saveChat(thisChat);
            }
            MainGui.newMessage(m);
        }
    }

    public static boolean userIsBlocked(String username) {
        // untested, probably never gonna happen
        if (settings.getBlockedUsers().isEmpty()) return false;
        for (String blockedUser : settings.getBlockedUsers()) {
            if (blockedUser.equals(username)) return true;
        }
        return false;
    }

    public static String sendToSocket(String[] request) throws IOException {
        String outputString = Request.getRequestJson(request);
        outputSocket = new Socket(server, port);
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(outputSocket.getInputStream()));
        PrintWriter pw = new PrintWriter(outputSocket.getOutputStream(), true);
        pw.println(outputString);
        return inputReader.readLine();
    }

    public static boolean authenticate(String username, String password) {
        try {
            String response = Main.sendToSocket(new String[]{"login", username, password});
            if (response.contains("true")) {
                loggedIn = true;
                user = getUserFromServer(username);
                settings.setUsername(username);
                settings.setPassword(password);
                settings.safe();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        JOptionPane.showMessageDialog(MainGui.getFrame(), "Sry hat nich geklappt");
        return false;
    }

    public static String signUp(String username, String password) {
        if (getUserFromServer(username) != null) return "not available";
        try {
            String response = Main.sendToSocket(new String[]{"signup", username, password});
            if (response.contains("true")) {
                loggedIn = true;
                user = getUserFromServer(username);
                settings.setUsername(username);
                settings.setPassword(password);
                settings.safe();
                return "true";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }

    public static void sendMessageToSocket(Message message) {
        try {
            String[] messageJson = message.toOutputStringArray();
            String outputString = Request.getRequestJson(messageJson);
            outputSocket = new Socket(server, port);
            outputSocket.setSoTimeout(3000);
            outputSocket.getOutputStream().write(outputString.getBytes(StandardCharsets.UTF_8));
            outputSocket.getOutputStream().flush();
            outputSocket.close();
            outputSocket = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static User getUserFromServer(String userName) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode userNode = objectMapper.readTree(sendToSocket(new String[]{"user", userName}));
            return objectMapper.treeToValue(userNode, User.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // get's called when another chat is opened
    public static void saveChat(Chat chat) {
        boolean found = false;
        for (Chat c : chats) {
            if (c.getUserName().equals(chat.getUserName())) {
                c = chat;
                found = true;
                break;
            }
        }
        if (!found) chats.add(chat);
        storeChat(chat);
    }

    private static void storeChat(Chat chat) {
        try {
            objectMapper.writeValue(new File(UtilMethods.getWorkingDirectory() + "chats/" + chat.getUserName() + ".json"), chat);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadChats() throws IOException {
        chatStore = new File(UtilMethods.getWorkingDirectory() + "chats/");
        chatStore.mkdir();
        chats = new ArrayList<>();
        if (chatStore.listFiles() == null || Objects.requireNonNull(chatStore.listFiles()).length == 0) return;
        for (File f : Objects.requireNonNull(chatStore.listFiles())) {
            Chat tempChat = objectMapper.readValue(f, Chat.class);
            chats.add(tempChat);
        }
    }

    public static Chat getChat(String username) throws IOException {
        if (chats.isEmpty()) {
            new File(UtilMethods.getWorkingDirectory() + "chats/" + username + ".json").createNewFile();
            return new Chat(username);
        }
        for (Chat c : chats) {
            if (c.getUserName().equals(username)) return c;
        }
        Chat thisChat = new Chat(username);
        new File(UtilMethods.getWorkingDirectory() + "chats/" + username + ".json").createNewFile();
        return thisChat;
    }

}
