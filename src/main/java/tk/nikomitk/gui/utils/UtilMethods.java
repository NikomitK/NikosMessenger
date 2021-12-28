package tk.nikomitk.gui.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.experimental.UtilityClass;
import tk.nikomitk.Main;
import tk.nikomitk.messenger.Message;
import tk.nikomitk.messenger.User;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

@UtilityClass
public class UtilMethods {

    public URL getImageUrl(int index) {
        return UtilMethods.class.getResource("/images/icon" + index + ".jpg");
    }

    public BufferedImage loadImage(URL url) throws IOException {
        return ImageIO.read(url);
    }

    public BufferedImage loadProfilePicture(int index) throws IOException {
        return ImageIO.read(getImageUrl(index));
    }

    public ImageIcon loadProfileIcon(int index) throws IOException {
        return new ImageIcon(loadProfilePicture(index));
    }

    public BufferedImage loadRoundImage(URL url) throws IOException {
        BufferedImage input = ImageIO.read(url);
        BufferedImage output = new BufferedImage(input.getWidth(), input.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = output.createGraphics();
        g2.setComposite(AlphaComposite.Src);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // TODO input background color (and refresh when changed)
        g2.setColor(Color.WHITE);
        g2.fill(new RoundRectangle2D.Float(0, 0, input.getWidth(), input.getHeight(), 999, 999));
        g2.setComposite(AlphaComposite.SrcAtop);
        g2.drawImage(input, 0, 0, null);
        g2.dispose();
        return output;
    }

    public BufferedImage loadRoundProfilePicture(int index) throws IOException {
        // there would've been an easier way, but it should be prettier with this method
        BufferedImage input = loadProfilePicture(index);
        BufferedImage output = new BufferedImage(input.getWidth(), input.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = output.createGraphics();
        g2.setComposite(AlphaComposite.Src);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // I have no idea why the setColor does nothing but I don't care
        g2.setColor(Color.GREEN);
        g2.fill(new RoundRectangle2D.Float(0, 0, input.getWidth(), input.getHeight(), 999, 999));
        g2.setComposite(AlphaComposite.SrcAtop);
        g2.drawImage(input, 0, 0, null);
        g2.dispose();
        return output;
    }

    public ImageIcon loadRoundProfileIcon(int index) throws IOException {
        return new ImageIcon(loadRoundProfilePicture(index));
    }

    public User getChatPartner(Message message) {
        try {
            System.out.println(message.toJson());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if (message.getSender().equals(Main.getUser().getUserName()))
            return Main.getUserFromServer(message.getReceiver());
        return Main.getUserFromServer(message.getSender());
    }

    public Properties loadProps() {
        Properties props = new Properties();
        try {
            props.load(UtilMethods.class.getResourceAsStream("/UIProps.properties"));
            return props;
        } catch (Exception e) {
            e.printStackTrace();
            return props;
        }
    }

    public String getWorkingDirectory() {
        String workingDirectory;
        String OS = (System.getProperty("os.name")).toUpperCase();
        if (OS.contains("WIN")) {
            workingDirectory = System.getenv("AppData");
            workingDirectory += "\\NikosMessenger\\";
        } else {
            // don't have a linux/macos machine to test
            workingDirectory = System.getProperty("user.home");
            workingDirectory += "/Library/Application Support";
        }
        new File(workingDirectory).mkdir();
        return workingDirectory;
    }
}
