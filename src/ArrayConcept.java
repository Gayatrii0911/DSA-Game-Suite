import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URI;

public class ArrayConcept extends JFrame {
    private String username;

    public ArrayConcept(String username) {
        this.username = username;

        setTitle("📘 Array - Visual Learning");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(new Color(240, 248, 255));

        // 🔙 Top Panel with Back Button and Title
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(240, 248, 255));
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JButton backButton = new JButton("← Back");
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        backButton.setForeground(new Color(0, 102, 204));
        backButton.setBackground(new Color(240, 248, 255));
        backButton.setBorderPainted(false);
        backButton.setFocusPainted(false);
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> {
            dispose();  // Close current page
            new Home(username);  // Navigate to Home screen
        });

        JLabel title = new JLabel("📘 Let's Explore Arrays!", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 38));
        title.setForeground(new Color(0, 102, 204));

        topPanel.add(backButton, BorderLayout.WEST);
        topPanel.add(title, BorderLayout.CENTER);

        main.add(topPanel, BorderLayout.NORTH);

        // 📘 Content Panel
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBackground(new Color(240, 248, 255));
        center.setBorder(BorderFactory.createEmptyBorder(20, 50, 50, 50));

        center.add(makeCard("📦 What is an Array?", """
            <b>An array</b> is a data structure that holds a fixed-size collection of similar data types.<br><br>
            🔹 Stored in contiguous memory locations<br>
            🔹 Elements accessed via index (e.g., <code>arr[0]</code>)<br>
            🔹 All values are of the same type<br><br>
            <i>Think of it as a row of lockers 🧳 — each locker holds one value and has a unique number!</i>
        """));

        center.add(Box.createVerticalStrut(20));
        center.add(makeDiagram());

        center.add(Box.createVerticalStrut(20));
        center.add(makeCard("⚙️ Array Operations & Time Complexity", """
            <b>Access (by index)</b>: O(1) ✅<br>
            <b>Search</b>: O(n) 🔍<br>
            <b>Insertion</b>: O(n) (need to shift elements)<br>
            <b>Deletion</b>: O(n)<br><br>
            <b>✅ Advantages:</b><br>
            - Fast random access<br>
            - Compact memory usage<br><br>
            <b>❌ Disadvantages:</b><br>
            - Fixed size<br>
            - Insertions & deletions are costly
        """));

        center.add(Box.createVerticalStrut(20));
        center.add(makeCard("🎯 Fun & Friendly Example", """
            Let's say you have an array:<br>
            <code>int[] scores = {10, 20, 30, 40, 50};</code><br><br>
            🧒 You: “What’s the 3rd score?”<br>
            💻 Computer: <code>scores[2] = 30</code><br><br>
            🔧 You: “Change 4th score to 100!”<br>
            💻 Computer: <code>scores[3] = 100</code><br><br>
            Now the array looks like:<br>
            <b>[10, 20, 30, 100, 50]</b><br><br>
            🎉 That’s how easy and quick access is!
        """));

        center.add(Box.createVerticalStrut(20));
        center.add(makeCard("🚀 When to Use Arrays", """
            ✅ You know the number of items<br>
            ✅ Need fast access by index<br>
            ✅ All values are of same type<br><br>
            <b>Use cases:</b><br>
            - Storing game high scores 🎮<br>
            - Holding RGB values in images 🖼️<br>
            - Working with matrices in math 📊<br>
            - Lookup tables and more 🔍
        """));

        center.add(Box.createVerticalStrut(30));
        center.add(makeVideoButton());

        JScrollPane scroll = new JScrollPane(center);
        scroll.setBorder(null);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        main.add(scroll, BorderLayout.CENTER);
        add(main);
        setVisible(true);
    }

    private JPanel makeCard(String heading, String htmlBody) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(20, 25, 25, 25)
        ));
        card.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.setMaximumSize(new Dimension(1000, Integer.MAX_VALUE));

        JLabel head = new JLabel(heading);
        head.setFont(new Font("Segoe UI", Font.BOLD, 24));
        head.setForeground(new Color(0, 102, 153));
        head.setAlignmentX(Component.LEFT_ALIGNMENT);
        head.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        head.setMaximumSize(new Dimension(Integer.MAX_VALUE, head.getPreferredSize().height));

        JTextPane bodyPane = new JTextPane();
        bodyPane.setContentType("text/html");
        bodyPane.setText("""
            <html>
            <head>
                <style>
                    body {
                        font-family: 'Segoe UI', sans-serif;
                        font-size: 17px;
                        color: #111;
                    }
                    code {
                        background-color: #f5f5f5;
                        padding: 2px 4px;
                        font-family: Consolas, monospace;
                        font-size: 16px;
                        border-radius: 4px;
                        color: #c7254e;
                    }
                    b {
                        color: #003366;
                    }
                </style>
            </head>
            <body>""" + htmlBody + "</body></html>");
        bodyPane.setEditable(false);
        bodyPane.setOpaque(false);
        bodyPane.setFocusable(false);
        bodyPane.setBorder(null);
        bodyPane.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(head);
        card.add(bodyPane);
        return card;
    }

    private JPanel makeDiagram() {
        JPanel diagram = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        diagram.setBackground(new Color(240, 248, 255));
        diagram.setMaximumSize(new Dimension(1000, 150));

        int[] values = {10, 20, 30, 40, 50};
        for (int i = 0; i < values.length; i++) {
            JPanel cell = new JPanel();
            cell.setPreferredSize(new Dimension(100, 90));
            cell.setBackground(Color.WHITE);
            cell.setLayout(new BorderLayout());
            cell.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(0, 102, 153), 2, true),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ));

            JLabel value = new JLabel(String.valueOf(values[i]), SwingConstants.CENTER);
            value.setFont(new Font("Segoe UI", Font.BOLD, 24));
            value.setForeground(new Color(0, 102, 153));

            JLabel index = new JLabel("Index " + i, SwingConstants.CENTER);
            index.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            index.setForeground(Color.DARK_GRAY);

            JLabel memory = new JLabel("0x" + (1000 + i * 4), SwingConstants.CENTER);
            memory.setFont(new Font("Segoe UI", Font.ITALIC, 10));
            memory.setForeground(new Color(100, 100, 100));

            cell.add(value, BorderLayout.CENTER);
            cell.add(index, BorderLayout.SOUTH);
            cell.add(memory, BorderLayout.NORTH);
            diagram.add(cell);
        }

        JPanel diagramPanel = new JPanel(new BorderLayout());
        diagramPanel.setBackground(new Color(240, 248, 255));
        diagramPanel.setMaximumSize(new Dimension(900, 80));

        JLabel memoryText = new JLabel("📍 Elements are placed in memory side by side — addresses shown above!", SwingConstants.CENTER);
        memoryText.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        memoryText.setForeground(new Color(80, 80, 80));

        diagramPanel.add(diagram, BorderLayout.CENTER);
        diagramPanel.add(memoryText, BorderLayout.SOUTH);

        return diagramPanel;
    }

    private JPanel makeVideoButton() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(240, 248, 255));

        JButton btn = new JButton("▶️ Watch Fun Animated Video on Arrays");
        btn.setFont(new Font("Segoe UI", Font.BOLD, 20));
        btn.setBackground(new Color(0, 153, 76));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(500, 50));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));

        btn.addActionListener((ActionEvent e) -> {
            try {
                Desktop.getDesktop().browse(new URI("https://youtu.be/3_x_Fb31NLE?si=LLtr9ArFXP7RbMx-"));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Unable to open video.");
            }
        });

        buttonPanel.add(btn);
        return buttonPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ArrayConcept("Guest"); // replace "Guest" with logged-in username if needed
        });
    }
}
