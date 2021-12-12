package tk.nikomitk.gui.utils;

import lombok.experimental.UtilityClass;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

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

}
