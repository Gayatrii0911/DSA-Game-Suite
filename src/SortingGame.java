import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;




public class SortingGame extends JFrame {
    private final List<Integer> numbers = new ArrayList<>();
    private final JPanel arrayPanel = new JPanel();
    private final JTextArea logArea = new JTextArea();
    private JButton firstSelected = null;
    private final String username;

    public SortingGame(String username) {
        this.username = username;

        setTitle("Sorting Visualizer - DSA Game Suite");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Gradient background panel
        JPanel bgPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setPaint(new GradientPaint(0, 0, new Color(58, 123, 213),
                        getWidth(), getHeight(), new Color(0, 210, 255)));
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        bgPanel.setLayout(new BorderLayout(20, 20));
        bgPanel.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));

        // Title
        JLabel title = new JLabel("🔄 Sorting Visualizer - Drag & Learn");
        title.setFont(new Font("Segoe UI", Font.BOLD, 34));
        title.setForeground(Color.WHITE);
        bgPanel.add(title, BorderLayout.NORTH);

        // Center panel
        JPanel centerPanel = new JPanel(new BorderLayout(20, 20));
        centerPanel.setOpaque(false);

        arrayPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        arrayPanel.setOpaque(false);
        centerPanel.add(arrayPanel, BorderLayout.NORTH);

        // Log area
        logArea.setRows(6);
        logArea.setEditable(false);
        logArea.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        JScrollPane scrollPane = new JScrollPane(logArea);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        // Buttons
        JButton shuffleBtn = new JButton("🔀 Shuffle");
        JButton checkBtn = new JButton("✅ Check");
        JButton finishBtn = new JButton("✅ Finish");

        styleButton(shuffleBtn);
        styleButton(checkBtn);
        styleButton(finishBtn);

        // Add actions
        shuffleBtn.addActionListener(e -> {
            shuffleNumbers();
            renderArray();
            logArea.setText("🔁 Array shuffled. Try sorting by clicking two elements to swap.\n");
        });

        checkBtn.addActionListener(e -> {
            logArea.append("🔍 Checking order...\n");
            checkAndColor();
        });

        finishBtn.addActionListener(e -> {
            StringBuilder summary = new StringBuilder();
            summary.append("📋 Sorting Summary\n\n");
            summary.append("Player: ").append(username).append("\n");
            summary.append("Final Array Order: ").append(numbers).append("\n\n");
            summary.append("💡 What You Learned:\n");
            summary.append("- Sorting is arranging elements in order.\n");
            summary.append("- In this game, you manually swapped numbers.\n");
            summary.append("- You visualized bubble-sort style comparisons.\n");
            summary.append("- Green indicates sorted pairs, red means swap needed.\n");

            JOptionPane.showMessageDialog(this, summary.toString(), "✅ Summary", JOptionPane.INFORMATION_MESSAGE);

            dispose(); // Close game window
            new Home(username); // Return to home screen
        });


        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);
        buttonPanel.add(shuffleBtn);
        buttonPanel.add(checkBtn);
        buttonPanel.add(finishBtn);

        centerPanel.add(buttonPanel, BorderLayout.SOUTH);
        bgPanel.add(centerPanel, BorderLayout.CENTER);

        add(bgPanel);

        shuffleNumbers();
        showIntroDialog();
        renderArray();
        setVisible(true);
    }

    private void showIntroDialog() {
        JPanel introPanel = new JPanel();
        introPanel.setLayout(new BoxLayout(introPanel, BoxLayout.Y_AXIS));
        introPanel.setBackground(Color.WHITE);
        introPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel heading = new JLabel("🧠 Introduction to Sorting");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 24));
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextArea info = new JTextArea(
                "\nSorting means arranging elements in order (ascending or descending).\n\n" +
                        "👨‍🏫 Why Sorting?\n" +
                        "- Faster search in sorted data.\n" +
                        "- Used in databases, ranking systems, etc.\n\n" +
                        "💡 Bubble Sort Steps:\n" +
                        "1️⃣ Compare two elements\n" +
                        "2️⃣ Swap if left > right\n" +
                        "3️⃣ Repeat until sorted!\n\n" +
                        "🎮 In this game, manually swap elements to sort the array.\n" +
                        "Click any two numbers to swap. Press 'Check' to validate.\n"
        );
        info.setEditable(false);
        info.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        info.setBackground(Color.WHITE);
        info.setWrapStyleWord(true);
        info.setLineWrap(true);

        introPanel.add(heading);
        introPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        introPanel.add(info);

        JScrollPane scroll = new JScrollPane(introPanel);
        scroll.setPreferredSize(new Dimension(600, 450));

        JOptionPane.showMessageDialog(null, scroll, "How Sorting Works", JOptionPane.INFORMATION_MESSAGE);
    }

    private void shuffleNumbers() {
        numbers.clear();
        Set<Integer> set = new LinkedHashSet<>();
        Random rand = new Random();
        while (set.size() < 6) set.add(rand.nextInt(50) + 1);
        numbers.addAll(set);
    }

    private void renderArray() {
        arrayPanel.removeAll();

        for (int i = 0; i < numbers.size(); i++) {
            JButton btn = new JButton(String.valueOf(numbers.get(i)));
            btn.setPreferredSize(new Dimension(80, 60));
            btn.setFont(new Font("Segoe UI", Font.BOLD, 22));
            btn.setFocusPainted(false);
            btn.setBackground(Color.WHITE);
            btn.setForeground(new Color(58, 123, 213));

            int index = i;
            btn.addActionListener(e -> handleSwap(index));

            arrayPanel.add(btn);
        }

        arrayPanel.revalidate();
        arrayPanel.repaint();
    }

    private void handleSwap(int index) {
        if (firstSelected == null) {
            firstSelected = (JButton) arrayPanel.getComponent(index);
            firstSelected.setBackground(new Color(255, 255, 180));
        } else {
            JButton second = (JButton) arrayPanel.getComponent(index);
            if (firstSelected == second) {
                firstSelected.setBackground(Color.WHITE);
                firstSelected = null;
                return;
            }

            int index1 = arrayPanel.getComponentZOrder(firstSelected);
            int index2 = arrayPanel.getComponentZOrder(second);

            Collections.swap(numbers, index1, index2);
            logArea.append("🔄 Swapped " + numbers.get(index2) + " ↔ " + numbers.get(index1) + "\n");

            firstSelected.setBackground(Color.WHITE);
            firstSelected = null;

            renderArray();
        }
    }

    private void checkAndColor() {
        Component[] comps = arrayPanel.getComponents();
        boolean sorted = true;

        for (int i = 0; i < comps.length - 1; i++) {
            JButton btn1 = (JButton) comps[i];
            JButton btn2 = (JButton) comps[i + 1];

            int val1 = Integer.parseInt(btn1.getText());
            int val2 = Integer.parseInt(btn2.getText());

            if (val1 > val2) {
                btn1.setBackground(new Color(255, 102, 102)); // red
                btn2.setBackground(new Color(255, 102, 102));
                logArea.append("❌ " + val1 + " > " + val2 + " — needs swap.\n");
                sorted = false;
            } else {
                btn1.setBackground(new Color(144, 238, 144)); // green
                btn2.setBackground(new Color(144, 238, 144));
                logArea.append("✅ " + val1 + " < " + val2 + " — correct.\n");
            }
        }

        if (sorted) {
            logArea.append("\n🎉 Congratulations! You’ve sorted the array correctly.\n" +
                    "📘 You learned: Sorting means comparing & arranging step-by-step.\n" +
                    "Try shuffling again to reinforce the learning!\n");
        } else {
            logArea.append("🔁 Keep adjusting until the entire array is sorted.\n");
        }
    }

    private void styleButton(JButton btn) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btn.setBackground(Color.WHITE);
        btn.setForeground(new Color(58, 123, 213));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SortingGame("Gayatri"));
    }
}
