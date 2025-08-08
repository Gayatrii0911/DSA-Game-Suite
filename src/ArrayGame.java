import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.Timer;
import java.util.List;



public class ArrayGame extends JFrame {
    private String username;
    private JPanel gridPanel;
    private JTextArea logArea;
    private JLabel timerLabel, arrayDisplayLabel;
    private JButton firstBtn = null, secondBtn = null;
    private int[] arrayValues;
    private int correctMatches = 0;
    private int attempts = 0;
    private Timer gameTimer;
    private int timeLeft = 60;
    private Map<JButton, String> buttonMap = new HashMap<>();

    public ArrayGame(String username) {
        this.username = username;
        setTitle("🧠 Array Memory Game - Player: " + username);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        showIntro();

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 255));

        // Top panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);

        // Left: timer
        JPanel timerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        timerPanel.setBackground(Color.WHITE);
        timerLabel = new JLabel("⏱ Time: 60s");
        timerLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        timerPanel.add(timerLabel);

        // Right: finish button
        JButton finishBtn = new JButton("✅ Finish");
        styleButton(finishBtn);
        finishBtn.addActionListener(e -> {
            endGame();
            dispose();
            new Home(username);
        });

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setBackground(Color.WHITE);
        rightPanel.add(finishBtn);

        // Center: Array Display
        StringBuilder arrayStr = new StringBuilder("📊 Array: [ ");
        for (int i = 0; i < arrayValues.length; i++) {
            arrayStr.append(arrayValues[i]);
            if (i < arrayValues.length - 1) arrayStr.append(", ");
        }
        arrayStr.append(" ]");
        arrayDisplayLabel = new JLabel(arrayStr.toString());
        arrayDisplayLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        arrayDisplayLabel.setForeground(new Color(0, 0, 128));
        arrayDisplayLabel.setHorizontalAlignment(SwingConstants.CENTER);

        topPanel.add(timerPanel, BorderLayout.WEST);
        topPanel.add(arrayDisplayLabel, BorderLayout.CENTER);
        topPanel.add(rightPanel, BorderLayout.EAST);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Center grid
        gridPanel = new JPanel(new GridLayout(4, 4, 15, 15));
        gridPanel.setBackground(new Color(240, 248, 255));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));
        mainPanel.add(gridPanel, BorderLayout.CENTER);

        // Right log
        logArea = new JTextArea(6, 30);
        logArea.setEditable(false);
        logArea.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        JScrollPane logScroll = new JScrollPane(logArea);
        logScroll.setBorder(BorderFactory.createTitledBorder("📜 Action Log"));
        mainPanel.add(logScroll, BorderLayout.EAST);

        add(mainPanel);
        setVisible(true);

        generateGrid();
        startTimer();
    }

    private void showIntro() {
        arrayValues = new int[8];
        Random rand = new Random();
        for (int i = 0; i < 8; i++) {
            arrayValues[i] = rand.nextInt(90) + 10;
        }

        StringBuilder arrayDisplay = new StringBuilder("arr = [ ");
        for (int i = 0; i < arrayValues.length; i++) {
            arrayDisplay.append(arrayValues[i]);
            if (i < arrayValues.length - 1) arrayDisplay.append(", ");
        }
        arrayDisplay.append(" ]");

        JTextArea intro = new JTextArea(
                "🎮 Welcome to the Array Memory Game!\n\n" +
                        "👉 Indexing in arrays starts from 0.\n\n" +
                        "📊 Here's the array you'll be working with:\n" +
                        arrayDisplay.toString() + "\n\n" +
                        "🎯 Your goal is to match the index with the value at that index.\n" +
                        "For example: if arr[3] = 56, match 'Index: 3' with 'Value: 56'.\n\n" +
                        "- You have 60 seconds to find all 8 pairs.\n" +
                        "- Click two buttons: one should say 'Index: x', the other 'Value: y'.\n" +
                        "- If arr[x] == y, it’s a ✅ correct match!\n" +
                        "- Green = correct, Red = incorrect.\n\n" +
                        "👀 Memorize the array above and get ready to play!"
        );
        intro.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        intro.setWrapStyleWord(true);
        intro.setLineWrap(true);
        intro.setEditable(false);
        intro.setMargin(new Insets(20, 20, 20, 20));
        intro.setBackground(Color.WHITE);
        intro.setForeground(Color.DARK_GRAY);

        JScrollPane scroll = new JScrollPane(intro);
        scroll.setPreferredSize(new Dimension(600, 350));
        JOptionPane.showMessageDialog(this, scroll, "📘 Array Game Introduction", JOptionPane.INFORMATION_MESSAGE);
    }

    private void generateGrid() {
        gridPanel.removeAll();
        List<String> items = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            items.add("Index: " + i);
            items.add("Value: " + arrayValues[i]);
        }

        Collections.shuffle(items);

        for (String val : items) {
            JButton btn = new JButton("❓");
            btn.setFont(new Font("Segoe UI", Font.BOLD, 18));
            btn.setFocusPainted(false);
            btn.setBackground(new Color(204, 229, 255));
            buttonMap.put(btn, val);
            btn.addActionListener(e -> handleClick(btn));
            gridPanel.add(btn);
        }

        gridPanel.revalidate();
        gridPanel.repaint();
    }

    private void handleClick(JButton btn) {
        if (firstBtn != null && secondBtn != null) return;

        btn.setText(buttonMap.get(btn));

        if (firstBtn == null) {
            firstBtn = btn;
        } else if (secondBtn == null && btn != firstBtn) {
            secondBtn = btn;
            checkMatch();
        }
    }

    private void checkMatch() {
        String val1 = buttonMap.get(firstBtn);
        String val2 = buttonMap.get(secondBtn);
        attempts++;

        if (isMatchingPair(val1, val2)) {
            firstBtn.setBackground(Color.GREEN);
            secondBtn.setBackground(Color.GREEN);
            firstBtn.setEnabled(false);
            secondBtn.setEnabled(false);
            correctMatches++;
            log("✅ Match: " + val1 + " <-> " + val2);
            if (correctMatches == 8) {
                endGame();
                dispose();
                new Home(username);
            }
        } else {
            firstBtn.setBackground(Color.PINK);
            secondBtn.setBackground(Color.PINK);
            log("❌ Wrong: " + val1 + " <-> " + val2);
            Timer delay = new Timer(800, e -> {
                firstBtn.setText("❓");
                secondBtn.setText("❓");
                firstBtn.setBackground(new Color(204, 229, 255));
                secondBtn.setBackground(new Color(204, 229, 255));
                firstBtn = null;
                secondBtn = null;
            });
            delay.setRepeats(false);
            delay.start();
            return;
        }

        firstBtn = null;
        secondBtn = null;
    }

    private boolean isMatchingPair(String a, String b) {
        if (a.startsWith("Index") && b.startsWith("Value")) {
            int index = Integer.parseInt(a.split(": ")[1]);
            int value = Integer.parseInt(b.split(": ")[1]);
            return arrayValues[index] == value;
        } else if (b.startsWith("Index") && a.startsWith("Value")) {
            return isMatchingPair(b, a);
        }
        return false;
    }

    private void startTimer() {
        gameTimer = new Timer(1000, e -> {
            timeLeft--;
            timerLabel.setText("⏱ Time: " + timeLeft + "s");
            if (timeLeft == 0) {
                gameTimer.stop();
                endGame();
                dispose();
                new Home(username);
            }
        });
        gameTimer.start();
    }

    private void endGame() {
        if (gameTimer != null) gameTimer.stop();

        StringBuilder sb = new StringBuilder();
        sb.append("🎯 Game Over!\n\n");
        sb.append("Player: ").append(username).append("\n");
        sb.append("Correct Matches: ").append(correctMatches).append(" / 8\n");
        sb.append("Total Attempts: ").append(attempts).append("\n\n");
        sb.append("💡 What You Learned:\n");
        sb.append("- Arrays store values in indexed positions\n");
        sb.append("- Indexing starts at 0\n");
        sb.append("- Access via arr[i] logic\n");
        sb.append("- Improves recall and memory with DSA\n");

        JOptionPane.showMessageDialog(this, sb.toString(), "🏁 Summary", JOptionPane.INFORMATION_MESSAGE);
    }

    private void log(String msg) {
        logArea.append(msg + "\n");
    }

    private void styleButton(JButton btn) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setBackground(Color.WHITE);
        btn.setForeground(new Color(58, 123, 213));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ArrayGame("TestUser"));
    }
}
