import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URI;

public class QueueConcept extends JFrame {
    private final String username;

    public QueueConcept(String username) {
        this.username = username;

        setTitle("📘 Queue - Visual Learning");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(new Color(240, 248, 255));

        // Top panel with Back button and Title
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
            dispose();
            new Home(username);
        });

        JLabel title = new JLabel("📘 Let's Explore Queues!", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 38));
        title.setForeground(new Color(0, 102, 204));

        topPanel.add(backButton, BorderLayout.WEST);
        topPanel.add(title, BorderLayout.CENTER);
        main.add(topPanel, BorderLayout.NORTH);

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBackground(new Color(240, 248, 255));
        center.setBorder(BorderFactory.createEmptyBorder(20, 50, 50, 50));

        center.add(makeCard("📦 What is a Queue?", """
            <b>A Queue</b> is a linear data structure that follows the <b>FIFO</b> (First In First Out) principle.<br><br>
            🔹 Elements are added at the <b>rear</b> and removed from the <b>front</b><br>
            🔹 Think of it like a line at a ticket counter 🎟️<br><br>
            <i>First come, first served!</i>
        """));

        center.add(Box.createVerticalStrut(20));
        center.add(makeQueueDiagram());

        center.add(Box.createVerticalStrut(20));
        center.add(makeCard("⚙️ Queue Operations & Time Complexity", """
            <b>Enqueue (add)</b>: O(1) ✅<br>
            <b>Dequeue (remove)</b>: O(1) ✅<br>
            <b>Peek (front)</b>: O(1) ✅<br><br>
            <b>✅ Advantages:</b><br>
            - Simple and fast insert/remove operations<br><br>
            <b>❌ Disadvantages:</b><br>
            - Fixed size (for arrays)<br>
            - No random access
        """));

        center.add(Box.createVerticalStrut(20));
        center.add(makeCard("🎯 Fun Example", """
            Let’s enqueue elements:<br>
            <span style='font-size:18px;'><code>queue.add(10);<br>
            queue.add(20);<br>
            queue.add(30);</code></span><br><br>
            
            🧒 You: "Who's first?"<br>
            💻 Computer: <b>10</b> is at the front<br>
            🧒 You: "Dequeue!"<br>
            💻 Computer removes <b>10</b>, now front is <b>20</b>
        """));

        center.add(Box.createVerticalStrut(20));
        center.add(makeCard("🚀 Where Queues Are Used", """
            ✅ Task Scheduling<br>
            ✅ Printer Jobs 🖨️<br>
            ✅ Keyboard Input Buffer<br>
            ✅ Call Center Line ☎️<br>
            ✅ OS Process Management
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

    private JPanel makeQueueDiagram() {
        JPanel diagramPanel = new JPanel();
        diagramPanel.setLayout(new BoxLayout(diagramPanel, BoxLayout.Y_AXIS));
        diagramPanel.setBackground(new Color(240, 248, 255));
        diagramPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel row = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        row.setBackground(new Color(240, 248, 255));

        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 90, 0));
        labelPanel.setBackground(new Color(240, 248, 255));

        String[] values = {"10", "20", "30", "40"};
        for (int i = 0; i < values.length; i++) {
            row.add(createQueueNode(values[i]));

            if (i == 0) {
                labelPanel.add(createMarker("⬇ Front"));
            } else if (i == values.length - 1) {
                labelPanel.add(createMarker("⬇ Rear"));
            } else {
                labelPanel.add(Box.createRigidArea(new Dimension(90, 20)));
            }

            if (i < values.length - 1) {
                row.add(createArrow());
            }
        }

        diagramPanel.add(Box.createVerticalStrut(10));
        diagramPanel.add(row);
        diagramPanel.add(Box.createVerticalStrut(5));
        diagramPanel.add(labelPanel);

        JLabel descLabel = new JLabel("Queue Structure: Elements enter from rear and exit from front (FIFO)");
        descLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        descLabel.setForeground(new Color(80, 80, 80));
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        diagramPanel.add(Box.createVerticalStrut(15));
        diagramPanel.add(descLabel);
        return diagramPanel;
    }

    private JPanel createQueueNode(String value) {
        JPanel nodeBox = new JPanel();
        nodeBox.setLayout(new BoxLayout(nodeBox, BoxLayout.Y_AXIS));
        nodeBox.setBackground(Color.WHITE);
        nodeBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 102, 153), 2),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        nodeBox.setPreferredSize(new Dimension(70, 70));
        nodeBox.setMaximumSize(new Dimension(70, 70));

        JLabel valLabel = new JLabel(value, SwingConstants.CENTER);
        valLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        valLabel.setForeground(new Color(0, 102, 153));
        valLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        nodeBox.add(valLabel);
        return nodeBox;
    }

    private JLabel createArrow() {
        JLabel arrow = new JLabel("→");
        arrow.setFont(new Font("Segoe UI", Font.BOLD, 22));
        arrow.setForeground(new Color(70, 70, 70));
        return arrow;
    }

    private JLabel createMarker(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(new Color(204, 0, 0));
        label.setPreferredSize(new Dimension(70, 20));
        return label;
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
        buttonPanel.setBackground(new Color(240, 248, 255));

        JButton btn = new JButton("▶️ Watch Queue Animation");
        btn.setFont(new Font("Segoe UI", Font.BOLD, 20));
        btn.setBackground(new Color(0, 153, 76));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(500, 50));

        btn.addActionListener((ActionEvent e) -> {
            try {
                Desktop.getDesktop().browse(new URI("https://youtu.be/ypJwoz_SXTo?si=XgQYIxcKZCqVtE8w"));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Unable to open video.");
            }
        });

        buttonPanel.add(btn);
        return buttonPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new QueueConcept("Guest"));
    }
}
