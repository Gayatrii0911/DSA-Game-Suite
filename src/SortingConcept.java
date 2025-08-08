import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URI;

public class SortingConcept extends JFrame {
    private final String username;

    public SortingConcept(String username) {
        this.username = username;

        setTitle("📘 Sorting - Visual Learning");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(new Color(245, 250, 255));

        // Top panel with Back button and Title
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(245, 250, 255));
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JButton backButton = new JButton("← Back");
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        backButton.setForeground(new Color(0, 102, 204));
        backButton.setBackground(new Color(245, 250, 255));
        backButton.setBorderPainted(false);
        backButton.setFocusPainted(false);
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> {
            dispose();
            new Home(username);
        });

        JLabel title = new JLabel("📘 Let's Explore Sorting!", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 38));
        title.setForeground(new Color(0, 102, 204));

        topPanel.add(backButton, BorderLayout.WEST);
        topPanel.add(title, BorderLayout.CENTER);
        main.add(topPanel, BorderLayout.NORTH);

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBackground(new Color(245, 250, 255));
        center.setBorder(BorderFactory.createEmptyBorder(20, 50, 50, 50));

        center.add(makeCard("📦 What is Sorting?", """
            <b>Sorting</b> is the process of arranging data in a particular order — ascending 🔼 or descending 🔽.<br><br>
            🔹 Helps in searching and organizing data efficiently<br>
            🔹 Common sorting algorithms: <b>Bubble, Selection, Insertion, Merge, Quick</b><br><br>
            <i>Think of sorting like arranging books by height or name!</i>
        """));

        center.add(Box.createVerticalStrut(20));
        center.add(makeSortingDiagram());

        center.add(Box.createVerticalStrut(20));
        center.add(makeCard("⚙️ Sorting Operations & Time Complexity", """
            <b>Bubble Sort</b>: O(n²) ❌<br>
            <b>Insertion Sort</b>: O(n²) ❌<br>
            <b>Merge Sort</b>: O(n log n) ✅<br>
            <b>Quick Sort</b>: O(n log n) ✅ (Best & Avg), O(n²) (Worst)<br><br>
            <b>✅ Advantages:</b><br>
            - Organizes data for easy access<br>
            - Essential for binary search and database queries
        """));

        center.add(Box.createVerticalStrut(20));
        center.add(makeCard("🎯 Fun Example", """
            Let’s sort: [5, 3, 8, 1, 2]<br>
            <span style='font-size:18px;'><code>
            Step 1: Compare 5 and 3 → swap<br>
            Step 2: Compare 5 and 8 → OK<br>
            Step 3: Compare 8 and 1 → swap<br>
            ... continue till sorted!
            </code></span>
        """));

        center.add(Box.createVerticalStrut(20));
        center.add(makeCard("🚀 Where Sorting Is Used", """
            ✅ Database indexing<br>
            ✅ Leaderboards & Rankings<br>
            ✅ File systems<br>
            ✅ E-commerce product sorting<br>
            ✅ Data visualization & reports
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

    private JPanel makeSortingDiagram() {
        JPanel diagramPanel = new JPanel(new GridLayout(1, 2, 50, 0));
        diagramPanel.setBackground(new Color(245, 250, 255));

        // Unsorted
        JPanel unsortedPanel = new JPanel();
        unsortedPanel.setLayout(new BoxLayout(unsortedPanel, BoxLayout.Y_AXIS));
        unsortedPanel.setBackground(new Color(245, 250, 255));
        unsortedPanel.setBorder(BorderFactory.createTitledBorder("Unsorted"));

        JPanel unsortedBars = new JPanel();
        unsortedBars.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        unsortedBars.setBackground(new Color(245, 250, 255));
        int[] unsortedValues = {40, 10, 70, 20, 50};
        for (int val : unsortedValues) {
            unsortedBars.add(createBar(val, false));
        }
        unsortedPanel.add(unsortedBars);

        // Sorted
        JPanel sortedPanel = new JPanel();
        sortedPanel.setLayout(new BoxLayout(sortedPanel, BoxLayout.Y_AXIS));
        sortedPanel.setBackground(new Color(245, 250, 255));
        sortedPanel.setBorder(BorderFactory.createTitledBorder("Sorted"));

        JPanel sortedBars = new JPanel();
        sortedBars.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        sortedBars.setBackground(new Color(245, 250, 255));
        int[] sortedValues = {10, 20, 40, 50, 70};
        for (int val : sortedValues) {
            sortedBars.add(createBar(val, true));
        }
        sortedPanel.add(sortedBars);

        diagramPanel.add(unsortedPanel);
        diagramPanel.add(sortedPanel);

        return diagramPanel;
    }

    private JPanel createBar(int height, boolean sorted) {
        JPanel bar = new JPanel();
        bar.setPreferredSize(new Dimension(30, height + 20));
        bar.setBackground(sorted ? new Color(0, 153, 76) : new Color(204, 102, 0));
        bar.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JLabel label = new JLabel(String.valueOf(height));
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(Color.WHITE);
        bar.add(label);

        return bar;
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

        JTextPane bodyPane = new JTextPane();
        bodyPane.setContentType("text/html");
        bodyPane.setText("<html><body style='font-family:Segoe UI; font-size:17px; color:#111;'>"
                + htmlBody + "</body></html>");
        bodyPane.setEditable(false);
        bodyPane.setOpaque(false);
        bodyPane.setFocusable(false);
        bodyPane.setBorder(null);
        bodyPane.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(head);
        card.add(Box.createVerticalStrut(10));
        card.add(bodyPane);
        return card;
    }

    private JPanel makeVideoButton() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(245, 250, 255));

        JButton btn = new JButton("▶️ Watch Sorting Animation");
        btn.setFont(new Font("Segoe UI", Font.BOLD, 20));
        btn.setBackground(new Color(0, 153, 76));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(500, 50));

        btn.addActionListener((ActionEvent e) -> {
            try {
                Desktop.getDesktop().browse(new URI("https://youtu.be/nmhjrI-aW5o?si=KtwUzVDtu9nQWUQl"));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Unable to open video.");
            }
        });

        buttonPanel.add(btn);
        return buttonPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SortingConcept("Guest"));
    }
}
