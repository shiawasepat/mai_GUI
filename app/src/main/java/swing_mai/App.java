package swing_mai;

import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App extends JFrame {
    
    private static final String IMAGE_DIR = "app/src/main/java/swing_mai/images/";
    private JPanel contentPanel;
    private Map<String, List<SongCard>> difficultyMap = new HashMap<>();
    private Map<String, JButton> difficultyButtons = new HashMap<>();
    
    public App() {

        setTitle("maimai's Master Difficulty List");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setMinimumSize(new Dimension(800, 600));

        File imgDir = new File(IMAGE_DIR);
        if (!imgDir.exists()) {
        System.err.println("Image directory not found: " + imgDir.getAbsolutePath());
        JOptionPane.showMessageDialog(this,
            "Image directory not found!\nExpected at: " + imgDir.getAbsolutePath(),
            "Configuration Error",
            JOptionPane.ERROR_MESSAGE);
    }
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(30, 35, 45));

        mainPanel.add(createNavigationBar(), BorderLayout.NORTH);
        mainPanel.add(createSidebar(), BorderLayout.WEST);

        contentPanel = new JPanel(new GridLayout(0, 4, 15, 15));
        contentPanel.setBackground(new Color(40, 45, 60));
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Faster scrolling
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        initializeSongData();
        showAllSongs();

        add(mainPanel);
    }
    
    public class NavButton extends MaiButton {
        public NavButton(String text, Color bgColor) {
            super(text);
            setBackground(bgColor);
            setPreferredSize(new Dimension(120, 35));
        }
    }   

    private void initializeSongData() {
        // Initialize all difficulty lists including Re:MASTER
        String[] difficulties = {"15.0", "14.9", "14.8", "14.7", "14.6", 
                                "14.5", "14.4", "14.3", "14.2", "14.1", "14.0"};
        for (String diff : difficulties) {
            difficultyMap.put(diff, new ArrayList<>());
        }

        addSong("Tsunagite (Re:MASTER)", "15.0", "Tsunagite.png");
        addSong("Tsunagite", "14.8", "Tsunagite.png");
        addSong("PANDORA PARADOXXX (Re:MASTER)", "15.0", "PANDORA_PARADOXXX.png");
        addSong("REX LUNATiCA", "14.8", "REX_LUNATiCA.png");
        addSong("Vallista", "14.0", "Vallista.png");
        addSong("INFiNiTE ENERZY -Overdose-", "14.0", "INFiNiTE_ENERZY_-Overdoze-.png");
        addSong("Elemental Ethnic", "14.0", "Elemental_Ethnic.png");
        addSong("Trrricksters!!", "14.3", "Trrricksters!!.png");
        addSong("HERA", "14.3", "HERA.png");
        addSong("Destr0yer", "14.2", "Destr0yer.png");
        addSong("Eta Beta Eta", "14.1", "Eta_beta_eta.png");
        addSong("raputa", "14.9", "raputa.png");
        addSong("Latent Kingdom", "14.9", "Latent_Kingdom.png");
        addSong("Urosousetsu", "14.7", "Urosousetsu.png");
        addSong("GIGANTOMAKHIA", "14.7", "GIGANTOMAKHIA.png");
        addSong("AMAZING MIGHTYYYY!!!!", "14.7", "AMAZING_MIGHTYYYY!!!!.png");
        addSong("RondeauX of RagnaroQ", "14.5", "RondeauX_of_RagnaroQ.png");
        addSong("Ai C", "14.0", "Ai_C.png");
        addSong("Mutation", "14.7", "Mutation.png");
        addSong("QZKago Requiem", "14.7", "QZKago_Requiem.png");
        addSong("Luminaria", "14.0", "Luminaria.png");
    }

    private JPanel createNavigationBar() {
        JPanel navBar = new JPanel(new BorderLayout());
        navBar.setBackground(new Color(35, 35, 35));
        navBar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JLabel title = new JLabel("maimai's Master Difficulty List");
        title.setForeground(new Color(220, 220, 255));
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        navBar.add(title, BorderLayout.WEST);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(new NavButton("Donate", new Color(255, 94, 107)));
        buttonPanel.add(new NavButton("Discord", new Color(88, 101, 242)));
        navBar.add(buttonPanel, BorderLayout.EAST);
        
        return navBar;
    }

    private void addSong(String title, String difficulty, String imageFile) {
        SongCard card = new SongCard(title, difficulty, imageFile);
        difficultyMap.get(difficulty).add(card);
    }
    
    

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(50, 55, 70));
        sidebar.setPreferredSize(new Dimension(250, 0));

        JButton masterBtn = createDifficultyButton("ALL DIFFICULTIES");
        masterBtn.addActionListener(e -> showAllSongs());
        difficultyButtons.put("MASTER", masterBtn); // Fix to support "MASTER" lookup
        sidebar.add(masterBtn);
 
        // Other difficulty buttons
        List<String> standardDifficulties = Arrays.asList(
                "15.0", "14.9", "14.8", "14.7", "14.6",
                "14.5", "14.4", "14.3", "14.2", "14.1", "14.0"
        );
        for (String difficulty : standardDifficulties) {
            JButton btn = createDifficultyButton(difficulty);
            btn.addActionListener(e -> showDifficulty(difficulty));
            difficultyButtons.put(difficulty, btn);
            sidebar.add(btn);
        }

        return sidebar;
    }
    
    private JButton createDifficultyButton(String text) {
        JButton btn = new JButton(text);
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(60, 65, 80));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btn.setFocusPainted(false);
        return btn;
    }

    private void showAllSongs() {
        contentPanel.removeAll();
        resetButtonColors();
        JButton allBtn = difficultyButtons.get("MASTER");
        if (allBtn != null) allBtn.setBackground(new Color(80, 85, 100));

        difficultyMap.values().stream()
                .flatMap(List::stream)
                .forEach(contentPanel::add);

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showDifficulty(String difficulty) {
        contentPanel.removeAll();
        resetButtonColors();
        JButton btn = difficultyButtons.get(difficulty);
        if (btn != null) btn.setBackground(new Color(80, 85, 100));

        difficultyMap.get(difficulty).forEach(contentPanel::add);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void resetButtonColors() {
        difficultyButtons.values().forEach(btn -> 
            btn.setBackground(new Color(60, 65, 80)));
    }

    // SongCard class inside App
    private class SongCard extends JPanel {
        public SongCard(String title, String difficulty, String imageFile) {
            setLayout(new BorderLayout());
            setBackground(new Color(60, 65, 80));
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(getDifficultyColor(difficulty)),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ));
            setPreferredSize(new Dimension(200, 220));
            setMaximumSize(new Dimension(200, 220));

            try {
                // Build the full image path
                String imagePath = IMAGE_DIR + imageFile;
                System.out.println("Loading image from: " + new File(imagePath).getAbsolutePath());
                
                Image jacketImage = ImageIO.read(new File(imagePath))
                    .getScaledInstance(180, 180, Image.SCALE_SMOOTH);
                JLabel imageLabel = new JLabel(new ImageIcon(jacketImage));
                add(imageLabel, BorderLayout.CENTER);
            } catch (IOException e) {
                System.err.println("Failed to load image: " + imageFile);
                JLabel placeholder = new JLabel(title, SwingConstants.CENTER); // Show title if image missing
                placeholder.setForeground(Color.WHITE);
                placeholder.setFont(new Font("Segoe UI", Font.BOLD, 14));
                add(placeholder, BorderLayout.CENTER);
            }

            JPanel infoPanel = new JPanel(new BorderLayout());
            infoPanel.setOpaque(false);

            JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
            titleLabel.setForeground(Color.WHITE);
            titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
            infoPanel.add(titleLabel, BorderLayout.NORTH);

            JLabel diffLabel = new JLabel(difficulty, SwingConstants.CENTER);
            diffLabel.setForeground(getDifficultyColor(difficulty));
            diffLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
            infoPanel.add(diffLabel, BorderLayout.SOUTH);

            add(infoPanel, BorderLayout.SOUTH);
        }

        private Color getDifficultyColor(String difficulty) {
            switch (difficulty) {
                case "15.0": return new Color(255, 100, 100);
                case "14.0": return new Color(255, 175, 100);
                default: return new Color(220, 220, 255);
            }
        }
    }


    
    public class StandardDifficultyButton extends DifficultyButton {
        public StandardDifficultyButton(String difficulty) {
            super(difficulty);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Create images directory if it doesn't exist
            File imgDir = new File(IMAGE_DIR);
            if (!imgDir.exists()) {
                System.out.println("Creating images directory at: " + imgDir.getAbsolutePath());
                imgDir.mkdirs();
            }
            
            // Print where we're looking for images
            System.out.println("Image directory path: " + imgDir.getAbsolutePath());
            System.out.println("Contents: " + Arrays.toString(imgDir.list()));
            
            App app = new App();
            app.setVisible(true);
        });
    }
}