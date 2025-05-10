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

        contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(0, 4, 15, 15)); // 4 columns, any number of rows, 15px gaps
        contentPanel.setBackground(new Color(40, 45, 60));
        
        // Wrap contentPanel in another panel to handle alignment
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBackground(new Color(40, 45, 60));
        wrapperPanel.add(contentPanel, BorderLayout.NORTH);
        
        // Add padding around the content
        wrapperPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JScrollPane scrollPane = new JScrollPane(wrapperPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(32);
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
        btn.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 16));
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

    // Initialize song data
    private void initializeSongData() {
        // Initialize all difficulty lists including Re:MASTER
        String[] difficulties = {"15.0", "14.9", "14.8", "14.7", "14.6", 
                                "14.5", "14.4", "14.3", "14.2", "14.1", "14.0"};
        for (String diff : difficulties) {
            difficultyMap.put(diff, new ArrayList<>());
        }

        addSong("系ぎて (Re:MASTER)", "15.0", "Tsunagite.png");
        addSong("系ぎて", "14.8", "Tsunagite.png");
        addSong("sølips", "14.9", "solips.png");
        addSong("PANDORA PARADOXXX (Re:MASTER)", "15.0", "PANDORA_PARADOXXX.png");
        addSong("PANDORA PARADOXXX", "14.9", "PANDORA_PARADOXXX.png");
        addSong("REX LUNATiCA", "14.8", "REX_LUNATiCA.png");
        addSong("Vallista", "14.0", "Vallista.png");
        addSong("INFiNiTE ENERZY -Overdose-", "14.0", "INFiNiTE_ENERZY_-Overdoze-.png");
        addSong("Elemental Ethnic", "14.0", "Elemental_Ethnic.png");
        addSong("Trrricksters!!", "14.3", "Trrricksters!!.png");
        addSong("Alea jacta est!", "14.4", "Alea_jacta_est!.png");
        addSong("Alea jacta est! (Re:MASTER)", "14.8", "Alea_jacta_est!.png");
        addSong("HERA", "14.3", "HERA.png");
        addSong("Destr0yer", "14.2", "Destr0yer.png");
        addSong("エータ・ベータ・イータ", "14.1", "Eta_beta_eta.png");
        addSong("raputa", "14.9", "raputa.png");
        addSong("Latent Kingdom", "14.9", "Latent_Kingdom.png");
        addSong("雨露霜雪", "14.7", "Urosousetsu.png");
        addSong("宙天", "14.9", "Chuuten.png");
        addSong("GIGANTØMAKHIA", "14.7", "GIGANTOMAKHIA.png");
        addSong("AMAZING MIGHTYYYY!!!!", "14.7", "AMAZING_MIGHTYYYY!!!!.png");
        addSong("RondeauX of RagnaroQ", "14.5", "RondeauX_of_RagnaroQ.png");
        addSong("Ai C", "14.0", "Ai_C.png");
        addSong("WE'RE BACK!!", "14.0", "WE'RE_BACK!!.png");
        addSong("Mutation", "14.7", "Mutation.png");
        addSong("Horoscope Express", "14.0", "Horoscope_Express.png");
        addSong("QZKago Requiem", "14.7", "QZKago_Requiem.png");
        addSong("QZKago Requiem (Re:MASTER)", "14.9", "QZKago_Requiem.png");
        addSong("Luminaria", "14.0", "Luminaria.png");
        addSong("Starry Colors", "14.0", "Starry_Colors.png");
        addSong("火炎地獄", "14.1", "Kaen_jigoku.png");
        addSong("Trick tear", "14.4", "Trick_tear.png");
        addSong("CO5M1C R4ILR0AD", "14.4", "CO5M1C_R4ILR0AD.png");
        addSong("ほしぞらスペクタルク", "14.4", "Hoshizora_spectacle.png");
        addSong("BreaK! BreaK! BreaK!", "14.6", "BreaK!_BreaK!_BreaK!.png");
        addSong("VERTeX (rintaro soma deconstructed remix) ", "14.6", "VERTeX_(rintaro_soma_deconstructed_remix).png");
        addSong("氷滅の135小節", "14.8", "Hyoumetsu_no_135_shousetsu.png");
        
    }

    // Inner class to hold song details
    private static class Song {
        String artist, bpm, designer, tap, hold, slide, touch, breaks;
        
        Song(String artist, String bpm, String designer, String tap, String hold, String slide, String touch, String breaks) {
            this.artist = artist;
            this.bpm = bpm;
            this.designer = designer;
            this.tap = tap;
            this.hold = hold;
            this.slide = slide;
            this.touch = touch;
            this.breaks = breaks;
        }
    }
    
        private void showSongDetails(String title) {
            Map<String, Song> songDetails = new HashMap<>();
            songDetails.put("系ぎて (Re:MASTER)", new Song("rintaro soma", "88", "BEYOND THE MEMORIES", "1100", "40", "70", "90", "100"));
            songDetails.put("系ぎて", new Song("rintaro soma", "88", "maimai TEAM DX", "862", "59", "73", "118", "88"));
            songDetails.put("PANDORA PARADOXXX (Re:MASTER)", new Song("Sakuzyo", "150", "PANDORA PARADOXXX", "1165", "72", "90", "-", "15"));
            songDetails.put("PANDORA PARADOXXX", new Song("Sakuzyo", "150", "PANDORA BOXXX", "1017", "98", "117", "-", "77"));
            songDetails.put("REX LUNATiCA", new Song("Kai", "230", "BELiZHEL", "826", "49", "123", "29", "76"));
            songDetails.put("Vallista", new Song("Sakuzyo", "180", "Luxizhel", "607", "25", "41", "53", "39"));
            songDetails.put("Starry Colors", new Song("BlackY feat.Risa Yuzuki", "177", "ロシェ@ペンギン", "674", "60", "99", "78", "19"));
            songDetails.put("INFiNiTE ENERZY -Overdose-", new Song("Reku Mochizuki", "180", "Jack vs サファ太", "692", "40", "51", "4", "62"));
            songDetails.put("Alea jacta est!", new Song("BlackY fused with WAiKURO", "162", "Carpe diem＊ HAN∀BI", "793", "51", "82", "-", "84"));
            songDetails.put("Alea jacta est! (Re:MASTER)", new Song("BlackY fused with WAiKURO", "162", "小鳥遊さん fused with Phoenix", "906", "83", "77", "-", "71"));
            songDetails.put("raputa", new Song("TJ.hangneil", "339", "project_raputa", "992", "116", "102", "29", "77"));
            songDetails.put("RondeauX of RagnaroQ", new Song("Morrigan feat.Lily and 結城碧", "185", "Jack vs サファ太", "692", "40", "51", "4", "62"));
            songDetails.put("BREaK! BREaK! BREaK!", new Song("HiTECH NINJA vs Cranky", "165", "Jサファ太 vs -ZONE- SaFaRi", "797", "76", "121", "62", "50"));
            songDetails.put("VERTeX (rintaro soma deconstructed remix)", new Song("Hiro/rintaro soma", "158", "rinato soma", "659", "32", "89", "85", "71"));
            songDetails.put("エータ・ベータ・イータ", new Song("ルゼ", "180", "Jack vs サファ太", "692", "40", "51", "4", "62"));
            songDetails.put("火炎地獄", new Song("山根ミチル", "216", "はっぴ", "356", "17", "72", "309", "70"));
            songDetails.put("sølips", new Song("rintaro soma", "199", "7.3GHz -Før The Legends-", "600", "100", "120", "80", "73"));
            songDetails.put("Latent Kingdom", new Song("t+pazolite", "201", "Safata.GHz", "984", "45", "124", "64", "154"));
            songDetails.put("WE'RE BACK!!", new Song("Zekk", "185", "Luxizhel", "730", "78", "89", "32", "32"));
            songDetails.put("YURUSHITE", new Song("t+pazolite", "270", "小鳥遊さん", "801", "86", "94", "38", "35"));
            songDetails.put("氷滅の135小節", new Song("大国奏音", "205", "BELiZHEL vs 7.3GHz", "802", "33", "130", "25", "135"));
            songDetails.put("GIGANTØMAKHIA", new Song("BlackYooh vs. siromaru", "190", "KOP3rd with 翡翠マナ", "883", "37", "98", "31", "100"));
            songDetails.put("YURUSHITE", new Song("t+pazolite", "270", "小鳥遊さん", "801", "86", "94", "38", "35"));
            songDetails.put("雨露霜雪", new Song("かねこちはる vs t+pazolite", "250", "サファ太 vs Luxizhel", "754", "95", "102", "124", "146"));
            songDetails.put("宙天", new Song("t+pazolite vs かねこちはる", "125", "BELiZHEL vs Safari", "725", "55", "89", "107", "82"));
    
            Song song = songDetails.getOrDefault(title, new Song("Unknown Artist", "999", "Unknown Designer", "999", "999", "999", "999", "999"));
    
            JDialog detailsDialog = new JDialog(this, title, true);
            detailsDialog.setSize(400, 300);
            detailsDialog.setLocationRelativeTo(this);
    
            JPanel detailsPanel = new JPanel();
            detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
            detailsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            detailsPanel.setBackground(new Color(40, 45, 60));
    
            String songInfo = String.format("""
                Title: %s
                Artist: %s
                BPM: %s
                
                Designer: %s
                Notes:
                TAP: %s
                HOLD: %s
                SLIDE: %s
                TOUCH: %s
                BREAK: %s
                """, title, song.artist, song.bpm, song.designer, song.tap, song.hold, song.slide, song.touch, song.breaks);
    
            JTextArea textArea = new JTextArea(songInfo);
            textArea.setEditable(false);
            textArea.setBackground(new Color(40, 45, 60));
            textArea.setForeground(Color.WHITE);
            textArea.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 14));
            detailsPanel.add(textArea);
    
            detailsDialog.add(detailsPanel);
            detailsDialog.setVisible(true);
        }
        
    // Custom button classes
    public class DifficultyButton extends JButton {
        public DifficultyButton(String difficulty) {
            super(difficulty);
            setAlignmentX(Component.LEFT_ALIGNMENT);
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
            setForeground(Color.WHITE);
            setBackground(new Color(60, 65, 80));
            setFont(new Font("Segoe UI", Font.BOLD, 16));
            setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            setFocusPainted(false);
        }
    }

    public class StandardDifficultyButton extends DifficultyButton {
        public StandardDifficultyButton(String difficulty) {
            super(difficulty);
        }
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
            titleLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 12));
            infoPanel.add(titleLabel, BorderLayout.NORTH);

            JLabel diffLabel = new JLabel(difficulty, SwingConstants.CENTER);
            diffLabel.setForeground(getDifficultyColor(difficulty));
            diffLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
            infoPanel.add(diffLabel, BorderLayout.SOUTH);

            // Add mouse listener for click events
            addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    showSongDetails(title);
                }
            });
            setCursor(new Cursor(Cursor.HAND_CURSOR));

            add(infoPanel, BorderLayout.SOUTH);
        }

        private Color getDifficultyColor(String difficulty) {
        if (difficulty.equals("15.0")) return new Color(255, 100, 100);
        
        if (difficulty.matches("14\\.\\d")) {
            double val = Double.parseDouble(difficulty);
            float ratio = (float) ((val - 14.0) / 1.0); // from 14.0 to 14.9
            return new Color(
                (int)(255 * ratio),
                (int)(175 + 50 * (1 - ratio)),
                100
            ); // Gradual orange-red gradient
        }
        return new Color(255, 255, 255); // Default color
    }}


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