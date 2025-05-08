package swing_mai;

import javax.swing.*;
import java.awt.*;

public abstract class MaiButton extends JButton {
    public MaiButton(String text) {
        super(text);
        setFocusPainted(false);
        setFont(new Font("Impact", Font.BOLD, 12));
        setForeground(Color.WHITE);
        setBorderPainted(false);
    }
}