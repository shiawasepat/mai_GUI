package swing_mai;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SidebarButton extends JButton {
    private final SidebarListener listener;
    
    public SidebarButton(String text, SidebarListener listener) {
        super(text);
        this.listener = listener;
        
        setAlignmentX(Component.LEFT_ALIGNMENT);
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        setForeground(Color.WHITE);
        setBackground(new Color(60, 65, 80));
        setFont(new Font("Segoe UI", Font.BOLD, 16));
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(10, 20, 10, 20),
            BorderFactory.createLineBorder(new Color(60, 65, 80), 1)
        ));
        setFocusPainted(false);
        
        addActionListener(e -> {
            this.listener.buttonSelected(text);
        });
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!getBackground().equals(new Color(80, 85, 100))) {
                    setBackground(new Color(70, 75, 90));
                    setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createEmptyBorder(10, 20, 10, 20),
                        BorderFactory.createLineBorder(new Color(100, 150, 255), 1)
                    ));
                }
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                if (!getBackground().equals(new Color(80, 85, 100))) {
                    setBackground(new Color(60, 65, 80));
                    setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createEmptyBorder(10, 20, 10, 20),
                        BorderFactory.createLineBorder(new Color(60, 65, 80), 1)
                    ));
                }
            }
        });
    }
}
