package tk.nikomitk.gui.main.chatlist;

import com.formdev.flatlaf.extras.components.FlatButton;

import javax.swing.*;
import java.awt.*;

// TODO align text
public class BigButton extends JButton {

    public BigButton(String text){
        createLook();
        setText("<html><font size=\"+4\"><b>" + text +"</b></font></html>");
    }

    public BigButton(String text, boolean opaque){
        createLook();
        setOpaque(opaque);
        setContentAreaFilled(opaque);
        setText("<html><font size=\"+4\"><b>" + text +"</b></font></html>");
    }

    public BigButton(String text, boolean opaque, boolean round){
        createLook();
        setOpaque(opaque);
        setContentAreaFilled(opaque);
        if(round) putClientProperty("JButton.buttonType", "roundRect");
        setText("<html><font size=\"+4\"><b>" + text +"</b></font></html>");
    }

    public BigButton(String text, boolean opaque, boolean round,  int additionalSize){
        createLook();
        setOpaque(opaque);
        setContentAreaFilled(opaque);
        if(round) putClientProperty("JButton.buttonType", "roundRect");
        setText("<html><font size=\"+" + additionalSize + "\"><p align=\"left\">" + text +"<p></font></html>");
    }

    public BigButton(String text, boolean opaque, boolean round, boolean bold,  int additionalSize){
        createLook();
        setOpaque(opaque);
        setContentAreaFilled(opaque);
        if(round) putClientProperty("JButton.buttonType", "roundRect");
        setText("<html><font size=\"+" + additionalSize + "\"><p>" + text +"</p></font></html>");
        if(bold) setText("<html><font size=\"+" + additionalSize + "\"><b>" + text +"</b></font></html>");
    }

    private void createLook(){
        setMinimumSize(new Dimension(60, 30));
        setPreferredSize(new Dimension(55, 55));
        setOpaque(false);
        setContentAreaFilled(false);
    }

}
