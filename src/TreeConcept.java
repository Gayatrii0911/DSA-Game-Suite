import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URI;

public class TreeConcept extends JFrame {
    private String username;

    public TreeConcept(String username) {
        this.username = username;

        setTitle("🌳 Tree - Visual Learning");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(new Color(240, 248, 255));

        // Top Panel with Back Button and Title
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

        // Center title panel with FlowLayout for perfect centering
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        titlePanel.setBackground(new Color(240, 248, 255));

        JLabel title = new JLabel("🌳 Let's Explore Trees!");
        title.setFont(new Font("Segoe UI", Font.BOLD, 38));
        title.setForeground(new Color(0, 102, 204));
        titlePanel.add(title);

// Add components to top panel
        topPanel.add(backButton, BorderLayout.WEST);
        topPanel.add(titlePanel, BorderLayout.CENTER);

// Invisible dummy panel on the right to balance layout
        topPanel.add(Box.createHorizontalStrut(backButton.getPreferredSize().width), BorderLayout.EAST);

        main.add(topPanel, BorderLayout.NORTH);

        // Center Content
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBackground(new Color(240, 248, 255));
        center.setBorder(BorderFactory.createEmptyBorder(20, 50, 50, 50));

        center.add(makeCard("🌲 What is a Tree?", """
            <b>A tree</b> is a hierarchical data structure with nodes connected by edges.<br><br>
            🔹 Has one root node (topmost)<br>
            🔹 Each node can have zero or more child nodes<br>
            🔹 Nodes without children are called leaves<br><br>
            <i>Think of it like a family tree 👨‍👩‍👧‍👦 or organizational chart!</i>
        """));

        center.add(Box.createVerticalStrut(20));
        center.add(makeTreeDiagram());

        center.add(Box.createVerticalStrut(20));
        center.add(makeCard("⚙️ Tree Operations & Time Complexity", """
            <b>Search</b>: O(n) for general trees, O(log n) for balanced BSTs<br>
            <b>Insertion</b>: O(n) for general trees, O(log n) for BSTs<br>
            <b>Deletion</b>: O(n) for general trees, O(log n) for BSTs<br>
            <b>Traversal</b>: O(n) for all nodes<br><br>
            <b>✅ Advantages:</b><br>
            - Hierarchical data representation<br>
            - Fast search in balanced trees<br>
            - Efficient insertion/deletion in BSTs<br><br>
            <b>❌ Disadvantages:</b><br>
            - More complex than linear structures<br>
            - Unbalanced trees degrade performance
        """));

        center.add(Box.createVerticalStrut(20));
        center.add(makeCard("🎯 Tree Terminology", """
            <b>Root</b>: Topmost node (50 in our example)<br>
            <b>Parent</b>: Immediate ancestor of a node<br>
            <b>Child</b>: Immediate descendant of a node<br>
            <b>Leaf</b>: Node with no children (20, 40, 60, 80)<br>
            <b>Height</b>: Longest path from root to leaf<br>
            <b>Depth</b>: Distance from root to node
        """));

        center.add(Box.createVerticalStrut(20));
        center.add(makeCard("🚀 When to Use Trees", """
            ✅ Representing hierarchical data (file systems)<br>
            ✅ Efficient searching (BSTs, AVL trees)<br>
            ✅ Auto-completion (Trie)<br>
            ✅ Network routing (Spanning trees)<br>
            ✅ Game AI (Decision trees)<br><br>
            <b>Common Types:</b><br>
            - Binary Trees<br>
            - Binary Search Trees (BST)<br>
            - AVL Trees (self-balancing)<br>
            - Tries (prefix trees)<br>
            - B-Trees (databases)
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

    private JPanel makeTreeDiagram() {
        JPanel diagramPanel = new JPanel(new BorderLayout());
        diagramPanel.setBackground(new Color(240, 248, 255));

        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setBackground(new Color(240, 248, 255));

        JPanel treePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int[][] positions = {
                        {250, 50}, {150, 150}, {350, 150},
                        {75, 250}, {225, 250}, {275, 250}, {425, 250}
                };

                g2.setColor(new Color(100, 100, 100));
                g2.setStroke(new BasicStroke(3));

                drawArrow(g2, positions[0][0]+25, positions[0][1]+50, positions[1][0]+25, positions[1][1]);
                drawArrow(g2, positions[0][0]+25, positions[0][1]+50, positions[2][0]+25, positions[2][1]);
                drawArrow(g2, positions[1][0]+25, positions[1][1]+50, positions[3][0]+25, positions[3][1]);
                drawArrow(g2, positions[1][0]+25, positions[1][1]+50, positions[4][0]+25, positions[4][1]);
                drawArrow(g2, positions[2][0]+25, positions[2][1]+50, positions[5][0]+25, positions[5][1]);
                drawArrow(g2, positions[2][0]+25, positions[2][1]+50, positions[6][0]+25, positions[6][1]);

                String[] values = {"50", "30", "70", "20", "40", "60", "80"};
                for (int i = 0; i < positions.length; i++) {
                    drawTreeNode(g2, positions[i][0], positions[i][1], values[i]);
                }
            }

            private void drawArrow(Graphics2D g2, int x1, int y1, int x2, int y2) {
                g2.drawLine(x1, y1, x2, y2);
                int arrowSize = 10;
                double angle = Math.atan2(y2 - y1, x2 - x1);
                g2.drawLine(x2, y2,
                        (int)(x2 - arrowSize * Math.cos(angle - Math.PI/6)),
                        (int)(y2 - arrowSize * Math.sin(angle - Math.PI/6)));
                g2.drawLine(x2, y2,
                        (int)(x2 - arrowSize * Math.cos(angle + Math.PI/6)),
                        (int)(y2 - arrowSize * Math.sin(angle + Math.PI/6)));
            }

            private void drawTreeNode(Graphics2D g2, int x, int y, String value) {
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(x, y, 50, 50, 10, 10);
                g2.setColor(new Color(0, 102, 204));
                g2.setStroke(new BasicStroke(3));
                g2.drawRoundRect(x, y, 50, 50, 10, 10);

                g2.setColor(new Color(0, 102, 204));
                Font font = new Font("Segoe UI", Font.BOLD, 16);
                g2.setFont(font);
                FontMetrics fm = g2.getFontMetrics();
                int textWidth = fm.stringWidth(value);
                int textHeight = fm.getHeight();
                g2.drawString(value, x + 25 - textWidth / 2, y + 25 + textHeight / 4);
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(500, 350);
            }
        };
        treePanel.setOpaque(false);

        centerWrapper.add(treePanel);
        diagramPanel.add(centerWrapper, BorderLayout.CENTER);

        JLabel descLabel = new JLabel("Binary Search Tree Structure: Each node has at most 2 children (left < parent < right)",
                SwingConstants.CENTER);
        descLabel.setFont(new Font("Segoe UI", Font.ITALIC, 16));
        descLabel.setForeground(new Color(80, 80, 80));
        descLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        diagramPanel.add(descLabel, BorderLayout.SOUTH);

        return diagramPanel;
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
        head.setForeground(new Color(0, 102, 204));
        head.setAlignmentX(Component.LEFT_ALIGNMENT);
        head.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        JTextPane bodyPane = new JTextPane();
        bodyPane.setContentType("text/html");
        bodyPane.setText("""
            <html><head><style>
            body { font-family: 'Segoe UI'; font-size: 17px; color: #111; }
            code { background-color: #f5f5f5; padding: 2px 4px; font-family: Consolas, monospace; font-size: 16px; border-radius: 4px; color: #c7254e; }
            b { color: #0066cc; }
            </style></head><body>""" + htmlBody + "</body></html>");
        bodyPane.setEditable(false);
        bodyPane.setOpaque(false);
        bodyPane.setBorder(null);
        bodyPane.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(head);
        card.add(bodyPane);
        return card;
    }

    private JPanel makeVideoButton() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(240, 248, 255));

        JButton btn = new JButton("▶️ Watch Tree Data Structure Animation");
        btn.setFont(new Font("Segoe UI", Font.BOLD, 20));
        btn.setBackground(new Color(0, 153, 76));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(500, 50));

        btn.addActionListener((ActionEvent e) -> {
            try {
                Desktop.getDesktop().browse(new URI("https://youtu.be/AylOfzYJ2qE?si=eBsj6lLPEsluhn4P"));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Unable to open video.");
            }
        });

        buttonPanel.add(btn);
        return buttonPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TreeConcept("Guest"));
    }
}
