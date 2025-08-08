import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URI;

public class StackConcept extends JFrame {
    private final String username;

    public StackConcept(String username) {
        this.username = username;

        setTitle("📘 Stack - Visual Learning");
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

        JLabel title = new JLabel("📘 Let's Explore Stacks!", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 38));
        title.setForeground(new Color(0, 102, 204));

        topPanel.add(backButton, BorderLayout.WEST);
        topPanel.add(title, BorderLayout.CENTER);
        main.add(topPanel, BorderLayout.NORTH);

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBackground(new Color(240, 248, 255));
        center.setBorder(BorderFactory.createEmptyBorder(20, 50, 50, 50));

        center.add(makeCard("📦 What is a Stack?", """
            <b>A Stack</b> is a linear data structure that follows the <b>LIFO</b> (Last In First Out) principle.<br><br>
            🔹 Elements are <b>pushed</b> onto the top and <b>popped</b> from the top<br>
            🔹 Think of a stack of plates 🍽️ — you add and remove from the top!<br><br>
            <i>Last in, first out!</i>
        """));

        center.add(Box.createVerticalStrut(20));
        center.add(makeStackDiagram());

        center.add(Box.createVerticalStrut(20));
        center.add(makeCard("⚙️ Stack Operations & Time Complexity", """
            <b>Push (add)</b>: O(1) ✅<br>
            <b>Pop (remove)</b>: O(1) ✅<br>
            <b>Peek (top)</b>: O(1) ✅<br><br>
            <b>✅ Advantages:</b><br>
            - Simple and efficient<br><br>
            <b>❌ Disadvantages:</b><br>
            - Limited access (only top element visible)
        """));

        center.add(Box.createVerticalStrut(20));
        center.add(makeCard("🎯 Fun Example", """
            Let’s push elements:<br>
            <span style='font-size:18px;'><code>stack.push(5);<br>
            stack.push(10);<br>
            stack.push(15);</code></span><br><br>
            
            🧒 You: "What’s at the top?"<br>
            💻 Computer: <b>15</b><br>
            🧒 You: "Pop!"<br>
            💻 Computer removes <b>15</b>, now top is <b>10</b>
        """));

        center.add(Box.createVerticalStrut(20));
        center.add(makeCard("🚀 Where Stacks Are Used", """
            ✅ Function Call Stack<br>
            ✅ Undo Mechanism in Editors<br>
            ✅ Browser History<br>
            ✅ Expression Evaluation<br>
            ✅ Syntax Parsing
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

    private JPanel makeStackDiagram() {
        JPanel diagramPanel = new JPanel(new GridLayout(1, 2, 50, 0));
        diagramPanel.setBackground(new Color(240, 248, 255));

        // PUSH STACK
        JPanel pushPanel = new JPanel();
        pushPanel.setLayout(new BoxLayout(pushPanel, BoxLayout.Y_AXIS));
        pushPanel.setBackground(new Color(240, 248, 255));
        pushPanel.setBorder(BorderFactory.createTitledBorder("Push"));

        JPanel pushStackContainer = new JPanel();
        pushStackContainer.setLayout(new BoxLayout(pushStackContainer, BoxLayout.Y_AXIS));
        pushStackContainer.setBackground(new Color(240, 248, 255));
        pushStackContainer.setAlignmentX(Component.CENTER_ALIGNMENT);

        pushStackContainer.add(createStackNode("C"));
        pushStackContainer.add(Box.createVerticalStrut(5));
        pushStackContainer.add(createStackNode("B"));
        pushStackContainer.add(Box.createVerticalStrut(5));
        pushStackContainer.add(createStackNode("A"));

        pushPanel.add(pushStackContainer);
        pushPanel.add(Box.createVerticalStrut(10));

        JLabel pushedInfo = new JLabel("⬇ Pushed element: C");
        pushedInfo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        pushedInfo.setForeground(new Color(0, 102, 153));
        pushedInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        pushPanel.add(pushedInfo);

        // POP STACK
        JPanel popPanel = new JPanel();
        popPanel.setLayout(new BoxLayout(popPanel, BoxLayout.Y_AXIS));
        popPanel.setBackground(new Color(240, 248, 255));
        popPanel.setBorder(BorderFactory.createTitledBorder("Pop"));

        JPanel popStackContainer = new JPanel();
        popStackContainer.setLayout(new BoxLayout(popStackContainer, BoxLayout.Y_AXIS));
        popStackContainer.setBackground(new Color(240, 248, 255));
        popStackContainer.setAlignmentX(Component.CENTER_ALIGNMENT);

        popStackContainer.add(createStackNode("B"));
        popStackContainer.add(Box.createVerticalStrut(5));
        popStackContainer.add(createStackNode("A"));

        popPanel.add(popStackContainer);
        popPanel.add(Box.createVerticalStrut(10));

        JLabel poppedInfo = new JLabel("⬆ Popped element: C");
        poppedInfo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        poppedInfo.setForeground(new Color(204, 102, 0));
        poppedInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        popPanel.add(poppedInfo);

        diagramPanel.add(pushPanel);
        diagramPanel.add(popPanel);

        return diagramPanel;
    }

    private JPanel createStackNode(String value) {
        JPanel node = new JPanel();
        node.setPreferredSize(new Dimension(80, 50));
        node.setMaximumSize(new Dimension(80, 50));
        node.setBackground(Color.WHITE);
        node.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 102, 153), 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        JLabel label = new JLabel(value);
        label.setFont(new Font("Segoe UI", Font.BOLD, 20));
        label.setForeground(new Color(0, 102, 153));
        node.add(label);
        return node;
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

        JButton btn = new JButton("▶️ Watch Stack Animation");
        btn.setFont(new Font("Segoe UI", Font.BOLD, 20));
        btn.setBackground(new Color(0, 153, 76));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(500, 50));

        btn.addActionListener((ActionEvent e) -> {
            try {
                Desktop.getDesktop().browse(new URI("https://youtu.be/lhhyE7NVcbg?si=FXAGVHATO4nUNXzv"));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Unable to open video.");
            }
        });

        buttonPanel.add(btn);
        return buttonPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StackConcept("Guest"));
    }
}
