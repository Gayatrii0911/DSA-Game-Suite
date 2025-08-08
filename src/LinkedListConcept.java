import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URI;

public class LinkedListConcept extends JFrame {
    private String username;

    public LinkedListConcept(String username) {
        this.username = username;

        setTitle("📘 Linked List - Visual Learning");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(new Color(240, 248, 255));

        // 🔙 Back button with Title Panel
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

        JLabel title = new JLabel("📘 Let's Explore Linked Lists!", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 38));
        title.setForeground(new Color(0, 102, 204));

        topPanel.add(backButton, BorderLayout.WEST);
        topPanel.add(title, BorderLayout.CENTER);

        main.add(topPanel, BorderLayout.NORTH);

        // Center section
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBackground(new Color(240, 248, 255));
        center.setBorder(BorderFactory.createEmptyBorder(20, 50, 50, 50));

        center.add(makeCard("📦 What is a Linked List?", """
            <b>A linked list</b> is a linear data structure where elements are linked using pointers.<br><br>
            🔹 Each element (node) contains data and a reference to the next node<br>
            🔹 Dynamic size - grows/shrinks as needed<br>
            🔹 No random access - must traverse from head<br><br>
            <i>Think of it as a treasure hunt 🏴‍☠️ — each clue points to the next location!</i>
        """));

        center.add(Box.createVerticalStrut(20));
        center.add(makeLinkedListDiagram());

        center.add(Box.createVerticalStrut(20));
        center.add(makeCard("⚙️ Linked List Operations & Time Complexity", """
            <b>Access (by index)</b>: O(n) 🔍<br>
            <b>Search</b>: O(n)<br>
            <b>Insertion at head</b>: O(1) ✅<br>
            <b>Insertion at tail</b>: O(1) with tail pointer ✅<br>
            <b>Deletion</b>: O(n) (must find node first)<br><br>
            <b>✅ Advantages:</b><br>
            - Dynamic size<br>
            - Efficient insertions/deletions at ends<br><br>
            <b>❌ Disadvantages:</b><br>
            - No random access<br>
            - Extra memory for pointers
        """));

        center.add(Box.createVerticalStrut(20));
        center.add(makeCard("🎯 Fun & Friendly Example", """
            Let's create a linked list:<br>
            <code>Node head = new Node(10);<br>
            head.next = new Node(20);<br>
            head.next.next = new Node(30);</code><br><br>
            
            🧒 You: "What's the 2nd value?"<br>
            💻 Computer: Traverses from head → next → finds <b>20</b><br><br>
            
            🔧 You: "Add 15 at the beginning!"<br>
            💻 Computer: Creates new node pointing to head<br>
            Now the list is: <b>15 → 10 → 20 → 30</b><br><br>
            
            🎉 That's how linked lists grow dynamically!
        """));

        center.add(Box.createVerticalStrut(20));
        center.add(makeCard("🚀 When to Use Linked Lists", """
            ✅ Need frequent insertions/deletions at ends<br>
            ✅ Don't know size in advance<br>
            ✅ Don't need random access<br><br>
            <b>Use cases:</b><br>
            - Implementing stacks/queues 🥞<br>
            - Music playlists (next/prev track) 🎵<br>
            - Browser history (back/forward) 🌐<br>
            - Memory management systems 💾
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

    private JPanel makeLinkedListDiagram() {
        JPanel diagramPanel = new JPanel();
        diagramPanel.setLayout(new BoxLayout(diagramPanel, BoxLayout.Y_AXIS));
        diagramPanel.setBackground(new Color(240, 248, 255));
        diagramPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel nodesPanel = new JPanel();
        nodesPanel.setLayout(new BoxLayout(nodesPanel, BoxLayout.X_AXIS));
        nodesPanel.setBackground(new Color(240, 248, 255));
        nodesPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        Object[][] nodes = {
                {"Head", "10", "0x1008"},
                {"0x1008", "20", "0x1010"},
                {"0x1010", "30", "0x1018"},
                {"0x1018", "40", "null"}
        };

        nodesPanel.add(createPointerPanel("head", "0x1000"));
        nodesPanel.add(Box.createHorizontalStrut(15));

        for (int i = 0; i < nodes.length; i++) {
            nodesPanel.add(createNodeBox(nodes[i][0].toString(), nodes[i][1].toString(), nodes[i][2].toString()));
            if (i < nodes.length - 1) {
                nodesPanel.add(createArrow());
            }
        }

        nodesPanel.add(Box.createHorizontalStrut(15));
        nodesPanel.add(createPointerPanel("tail", "0x1018"));

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.X_AXIS));
        centerPanel.setBackground(new Color(240, 248, 255));
        centerPanel.add(Box.createHorizontalGlue());
        centerPanel.add(nodesPanel);
        centerPanel.add(Box.createHorizontalGlue());

        diagramPanel.add(Box.createVerticalStrut(20));
        diagramPanel.add(centerPanel);
        diagramPanel.add(Box.createVerticalStrut(20));

        JLabel descLabel = new JLabel("Linked List Structure: Each node contains data and the memory address of the next node",
                SwingConstants.CENTER);
        descLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        descLabel.setForeground(new Color(80, 80, 80));
        diagramPanel.add(descLabel);

        return diagramPanel;
    }

    private JPanel createNodeBox(String address, String value, String nextAddress) {
        JPanel nodeBox = new JPanel();
        nodeBox.setLayout(new BoxLayout(nodeBox, BoxLayout.Y_AXIS));
        nodeBox.setBackground(Color.WHITE);
        nodeBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 102, 153), 2),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        nodeBox.setPreferredSize(new Dimension(120, 100));
        nodeBox.setMaximumSize(new Dimension(120, 100));

        JLabel addrLabel = new JLabel(address, SwingConstants.CENTER);
        addrLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        addrLabel.setForeground(new Color(100, 100, 100));

        JLabel valLabel = new JLabel(value, SwingConstants.CENTER);
        valLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        valLabel.setForeground(new Color(0, 102, 153));

        JLabel nextLabel = new JLabel("next: " + nextAddress, SwingConstants.CENTER);
        nextLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        nextLabel.setForeground(Color.DARK_GRAY);

        nodeBox.add(addrLabel);
        nodeBox.add(Box.createVerticalStrut(5));
        nodeBox.add(valLabel);
        nodeBox.add(Box.createVerticalStrut(5));
        nodeBox.add(nextLabel);

        return nodeBox;
    }

    private JPanel createPointerPanel(String label, String address) {
        JPanel pointerPanel = new JPanel();
        pointerPanel.setLayout(new BoxLayout(pointerPanel, BoxLayout.Y_AXIS));
        pointerPanel.setBackground(new Color(240, 248, 255));
        pointerPanel.setAlignmentY(Component.CENTER_ALIGNMENT);

        JLabel labelLbl = new JLabel(label, SwingConstants.CENTER);
        labelLbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
        labelLbl.setForeground(new Color(204, 0, 0));

        JLabel addrLbl = new JLabel(address, SwingConstants.CENTER);
        addrLbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        addrLbl.setForeground(new Color(100, 100, 100));

        pointerPanel.add(labelLbl);
        pointerPanel.add(addrLbl);

        return pointerPanel;
    }

    private JLabel createArrow() {
        JLabel arrow = new JLabel("→", SwingConstants.CENTER);
        arrow.setFont(new Font("Segoe UI", Font.BOLD, 28));
        arrow.setForeground(new Color(70, 70, 70));
        arrow.setPreferredSize(new Dimension(30, 30));
        return arrow;
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
        <html><head><style>
        body { font-family: 'Segoe UI'; font-size: 17px; color: #111; }
        code { background: #f5f5f5; padding: 2px 4px; font-family: Consolas; color: #c7254e; }
        b { color: #003366; }
        </style></head><body>""" + htmlBody + "</body></html>");
        bodyPane.setEditable(false);
        bodyPane.setOpaque(false);
        bodyPane.setFocusable(false);
        bodyPane.setBorder(null);
        bodyPane.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(head);
        card.add(bodyPane);
        return card;
    }

    private JPanel makeVideoButton() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(240, 248, 255));

        JButton btn = new JButton("▶️ Watch Linked List Animation");
        btn.setFont(new Font("Segoe UI", Font.BOLD, 20));
        btn.setBackground(new Color(0, 153, 76));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(500, 50));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));

        btn.addActionListener((ActionEvent e) -> {
            try {
                Desktop.getDesktop().browse(new URI("https://youtu.be/MCG7S2fGUeU?si=cv1JS4vp_Q11OCTQ"));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Unable to open video.");
            }
        });

        buttonPanel.add(btn);
        return buttonPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LinkedListConcept("Guest"));
    }
}
