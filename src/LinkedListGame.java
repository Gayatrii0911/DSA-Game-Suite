import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;



public class LinkedListGame extends JFrame {
    private JPanel listPanel;
    private JTextArea logArea;
    private final java.util.List<Node> nodes = new ArrayList<>();
    private String username;

    class Node {
        int data;
        JLabel nodeLabel;
        Node(int data) {
            this.data = data;
            this.nodeLabel = createNodeLabel(data);
        }
    }

    public LinkedListGame(String username) {
        this.username = username;
        setTitle("🔗 Linked List Visual Game - Player: " + username);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        showIntro();

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField dataField = new JTextField(10);
        JButton addBtn = new JButton("➕ Add Node");
        JButton delBtn = new JButton("❌ Delete Node");
        JButton revBtn = new JButton("🔁 Reverse");
        JButton resetBtn = new JButton("🔄 Reset");
        JButton finishBtn = new JButton("✅ Finish");

        styleButton(addBtn);
        styleButton(delBtn);
        styleButton(revBtn);
        styleButton(resetBtn);
        styleButton(finishBtn);

        controlPanel.add(new JLabel("Enter Data:"));
        controlPanel.add(dataField);
        controlPanel.add(addBtn);
        controlPanel.add(delBtn);
        controlPanel.add(revBtn);
        controlPanel.add(resetBtn);
        controlPanel.add(finishBtn);

        mainPanel.add(controlPanel, BorderLayout.NORTH);

        listPanel = new JPanel(null);
        listPanel.setBackground(Color.WHITE);
        listPanel.setPreferredSize(new Dimension(1000, 400));
        JScrollPane scrollPane = new JScrollPane(listPanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        logArea = new JTextArea(10, 25);
        logArea.setEditable(false);
        logArea.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        logArea.setForeground(Color.DARK_GRAY);
        JScrollPane logScroll = new JScrollPane(logArea);
        logScroll.setBorder(BorderFactory.createTitledBorder("📃 Action Log"));
        mainPanel.add(logScroll, BorderLayout.EAST);

        addBtn.addActionListener(e -> {
            try {
                int val = Integer.parseInt(dataField.getText().trim());
                addNode(val);
                dataField.setText("");
            } catch (Exception ex) {
                log("❗ Enter valid integer");
            }
        });

        delBtn.addActionListener(e -> {
            try {
                int val = Integer.parseInt(dataField.getText().trim());
                deleteNode(val);
                dataField.setText("");
            } catch (Exception ex) {
                log("❗ Enter valid integer to delete");
            }
        });

        revBtn.addActionListener(e -> reverseList());
        resetBtn.addActionListener(e -> resetList());

        finishBtn.addActionListener(e -> {
            showSummary();
            dispose();
            new Home(username); // ✅ Navigate back to home
        });

        add(mainPanel);
        setVisible(true);
    }

    private void showIntro() {
        JTextArea intro = new JTextArea(
                "📚 Welcome " + username + " to the Linked List Game!\n\n" +
                        "This visualizer helps you learn how a Linked List works:\n" +
                        "- A Linked List is a chain of nodes, each holding data and a link to the next.\n" +
                        "- Use 'Add' to insert nodes at the end.\n" +
                        "- 'Delete' removes a node by value.\n" +
                        "- 'Reverse' flips the entire chain.\n" +
                        "- 'Reset' clears the list.\n\n" +
                        "Observe the arrows and memory-style links.\n" +
                        "Let's build and explore a Linked List!"
        );
        intro.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        intro.setWrapStyleWord(true);
        intro.setLineWrap(true);
        intro.setEditable(false);
        intro.setMargin(new Insets(10, 10, 10, 10));
        intro.setBackground(Color.WHITE);
        intro.setForeground(Color.DARK_GRAY);

        JScrollPane scroll = new JScrollPane(intro);
        scroll.setPreferredSize(new Dimension(600, 300));

        JOptionPane.showMessageDialog(this, scroll, "🎓 Linked List Introduction", JOptionPane.INFORMATION_MESSAGE);
    }

    private JLabel createNodeLabel(int data) {
        JLabel lbl = new JLabel("<html><center>" + data + "<br/>@" + Integer.toHexString(data * 13) + "</center></html>");
        lbl.setOpaque(true);
        lbl.setBackground(new Color(173, 216, 230));
        lbl.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        lbl.setHorizontalAlignment(SwingConstants.CENTER);
        lbl.setVerticalAlignment(SwingConstants.CENTER);
        lbl.setSize(80, 60);
        return lbl;
    }

    private void addNode(int data) {
        Node newNode = new Node(data);
        nodes.add(newNode);
        drawList();
        log("✅ " + username + " added node " + data);
    }

    private void deleteNode(int data) {
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).data == data) {
                nodes.remove(i);
                drawList();
                log("🗑️ " + username + " deleted node " + data);
                return;
            }
        }
        log("⚠️ Node " + data + " not found.");
    }

    private void reverseList() {
        java.util.Collections.reverse(nodes);
        drawList();
        log("🔁 Linked List reversed by " + username);
    }

    private void resetList() {
        nodes.clear();
        drawList();
        log("🔄 List reset by " + username);
    }

    private void showSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append("🏆 Session Summary\n\n");
        summary.append("Player: ").append(username).append("\n");
        summary.append("Total Nodes: ").append(nodes.size()).append("\n");
        summary.append("Current List: ");
        for (Node node : nodes) {
            summary.append(node.data).append(" → ");
        }
        summary.append("null\n\n");
        summary.append("What You Learned:\n");
        summary.append("- How a linked list is structured\n");
        summary.append("- How to add/delete nodes\n");
        summary.append("- How to reverse a list visually\n");
        summary.append("- Importance of NULL termination");

        JOptionPane.showMessageDialog(this, summary.toString(), "✅ Summary", JOptionPane.INFORMATION_MESSAGE);
    }

    private void drawList() {
        listPanel.removeAll();
        int x = 20, y = 100;
        for (int i = 0; i < nodes.size(); i++) {
            Node node = nodes.get(i);
            node.nodeLabel.setLocation(x, y);
            listPanel.add(node.nodeLabel);
            if (i < nodes.size() - 1) {
                JLabel arrow = new JLabel("→");
                arrow.setFont(new Font("Arial", Font.BOLD, 28));
                arrow.setSize(40, 40);
                arrow.setLocation(x + 90, y + 10);
                listPanel.add(arrow);
                x += 130;
            } else {
                JLabel nullLbl = new JLabel("null");
                nullLbl.setFont(new Font("Arial", Font.PLAIN, 18));
                nullLbl.setSize(50, 30);
                nullLbl.setLocation(x + 90, y + 15);
                listPanel.add(nullLbl);
            }
        }
        listPanel.revalidate();
        listPanel.repaint();
    }

    private void log(String msg) {
        logArea.append(msg + "\n");
    }

    // ✅ Uniform button style like other games
    private void styleButton(JButton btn) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setBackground(Color.WHITE);
        btn.setForeground(new Color(58, 123, 213));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
    }

    // Main method for testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LinkedListGame("TestUser"));
    }
}
