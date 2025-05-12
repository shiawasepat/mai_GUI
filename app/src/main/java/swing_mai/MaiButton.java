package swing_mai;

import javax.swing.*;
import java.awt.*;

public class MaiButton extends JButton {
    private boolean isSelected = false;
    private Timer glowTimer;
    private float glowIntensity = 0.0f;
    private static final float GLOW_STEP = 0.1f;

    public MaiButton(String text) {
        super(text);
        setForeground(Color.WHITE);
        setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 16));
        setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        setFocusPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        glowTimer = new Timer(50, e -> {
            if (isSelected) {
                glowIntensity = Math.min(1.0f, glowIntensity + GLOW_STEP);
            } else {
                glowIntensity = Math.max(0.0f, glowIntensity - GLOW_STEP);
            }
            repaint();
        });

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (!isSelected) setBackground(getBackground().brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (!isSelected) setBackground(getBackground());
            }
        });
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
        if (!glowTimer.isRunning()) {
            glowTimer.start();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        
        // Enable antialiasing
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw glow effect if selected
        if (glowIntensity > 0) {
            Color glowColor = new Color(1f, 1f, 1f, glowIntensity * 1f);
            g2d.setColor(glowColor);
            for (int i = 0; i < 5; i++) {
                g2d.fillRoundRect(i, i, getWidth() - 2*i, getHeight() - 2*i, 10, 10);
            }
        }

        super.paintComponent(g2d);
        g2d.dispose();
    }
}