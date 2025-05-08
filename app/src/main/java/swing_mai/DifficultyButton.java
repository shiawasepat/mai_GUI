package swing_mai;

import javax.swing.*;
import java.awt.*;

public abstract class DifficultyButton extends MaiButton {
    public DifficultyButton(String text) {
        super(text);
        setAlignmentX(Component.LEFT_ALIGNMENT);
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        setBackground(new Color(60, 65, 80));
        setFont(new Font("Impact", Font.BOLD, 16));
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }
}
