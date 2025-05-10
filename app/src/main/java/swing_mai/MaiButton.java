package swing_mai;

import javax.swing.*;
import java.awt.*;

// Custom button class for the sidebar
public class MaiButton extends JButton {
    public MaiButton(String text) {
        super(text);
        setForeground(Color.WHITE);
        setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 16));
        setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        setFocusPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setBackground(getBackground().brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                setBackground(getBackground().darker());
            }
        });
    }
}