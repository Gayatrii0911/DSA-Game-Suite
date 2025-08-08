import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class TreeGame extends JFrame {
    private TreeNode root;
    private final List<String> userPath = new ArrayList<>();
    private final JTextArea logArea = new JTextArea();
    private final List<JButton> nodeButtons = new ArrayList<>();
    private String traversalMode = "BFS"; // or "DFS"

    public TreeGame (String username) {
        setTitle("Binary Tree Traversal - DSA Game Suite");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        showIntro();

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

        JLabel title = new JLabel("🌳 Binary Tree Traversal Challenge", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 34));
        title.setForeground(Color.WHITE);
        bgPanel.add(title, BorderLayout.NORTH);

        root = generateTree();
        JPanel treePanel = createTreeUI(root);

        logArea.setEditable(false);
        logArea.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        JScrollPane logScroll = new JScrollPane(logArea);
        logScroll.setBorder(BorderFactory.createTitledBorder("📄 Action Log"));

        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 30, 30));
        centerPanel.setOpaque(false);
        centerPanel.add(treePanel);
        centerPanel.add(logScroll);
        bgPanel.add(centerPanel, BorderLayout.CENTER);

        JButton dfsBtn = createStyledButton("Play DFS");
        JButton bfsBtn = createStyledButton("Play BFS");
        JButton checkBtn = createStyledButton("Check");
        JButton resetBtn = createStyledButton("Reset");
        JButton finishBtn = createStyledButton("Finish");

        JPanel btnPanel = new JPanel();
        btnPanel.setOpaque(false);
        btnPanel.setLayout(new FlowLayout());
        btnPanel.add(dfsBtn);
        btnPanel.add(bfsBtn);
        btnPanel.add(checkBtn);
        btnPanel.add(resetBtn);
        btnPanel.add(finishBtn);

        bgPanel.add(btnPanel, BorderLayout.SOUTH);
        add(bgPanel);

        dfsBtn.addActionListener(e -> {
            traversalMode = "DFS";
            logArea.append("\n🔁 Switched to DFS mode\n");
        });

        bfsBtn.addActionListener(e -> {
            traversalMode = "BFS";
            logArea.append("\n🔁 Switched to BFS mode\n");
        });

        checkBtn.addActionListener(e -> checkTraversal());
        resetBtn.addActionListener(e -> resetUserPath());

        finishBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                    "🎉 Well done, " + username + "!\n\n" +
                            "You learned DFS & BFS tree traversal orders.\nKeep exploring more DSA concepts!",
                    "Session Summary",
                    JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new Home(username);
        });

        setVisible(true);
    }

    private void checkTraversal() {
        List<String> correct = traversalMode.equals("BFS") ? bfs(root) : dfs(root);
        logArea.append("\n🔍 Expected: " + correct + "\n");
        logArea.append("🧠 Your Path: " + userPath + "\n");

        for (int i = 0; i < userPath.size(); i++) {
            String userVal = userPath.get(i);
            String expectedVal = i < correct.size() ? correct.get(i) : null;

            for (JButton btn : nodeButtons) {
                if (btn.getText().equals(userVal)) {
                    if (userVal.equals(expectedVal)) {
                        btn.setBackground(new Color(144, 238, 144)); // light green
                    } else {
                        btn.setBackground(new Color(255, 102, 102)); // light red
                    }
                    break;
                }
            }
        }

        if (userPath.equals(correct)) {
            logArea.append("✅ Correct traversal!\n");
        } else {
            logArea.append("❌ Incorrect traversal. Red = wrong order.\n");
        }

        for (JButton btn : nodeButtons) {
            btn.setEnabled(false);
        }
    }

    private void resetUserPath() {
        userPath.clear();
        logArea.append("\n🔁 Reset traversal\n");
        for (JButton btn : nodeButtons) {
            btn.setEnabled(true);
            btn.setBackground(Color.WHITE);
        }
    }

    private JPanel createTreeUI(TreeNode root) {
        JPanel panel = new JPanel(new GridLayout(3, 1));
        panel.setOpaque(false);

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        for (int level = 0; level < 3; level++) {
            JPanel levelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
            levelPanel.setOpaque(false);
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (node == null) {
                    levelPanel.add(new JLabel(" "));
                    queue.add(null);
                    queue.add(null);
                    continue;
                }

                JButton btn = new JButton(String.valueOf(node.val));
                btn.setFont(new Font("Segoe UI", Font.BOLD, 20));
                btn.setBackground(Color.WHITE);
                btn.setFocusPainted(false);
                btn.setPreferredSize(new Dimension(60, 40));
                btn.addActionListener(e -> {
                    if (!userPath.contains(btn.getText())) {
                        userPath.add(btn.getText());
                        logArea.append("Clicked: " + btn.getText() + "\n");
                        btn.setBackground(new Color(173, 216, 230));
                    }
                });

                nodeButtons.add(btn);
                levelPanel.add(btn);
                queue.add(node.left);
                queue.add(node.right);
            }

            panel.add(levelPanel);
        }
        return panel;
    }

    private List<String> dfs(TreeNode node) {
        List<String> result = new ArrayList<>();
        if (node == null) return result;
        result.add(node.val);
        result.addAll(dfs(node.left));
        result.addAll(dfs(node.right));
        return result;
    }

    private List<String> bfs(TreeNode root) {
        List<String> result = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node != null) {
                result.add(node.val);
                queue.add(node.left);
                queue.add(node.right);
            }
        }
        return result;
    }

    private TreeNode generateTree() {
        TreeNode a = new TreeNode("A");
        TreeNode b = new TreeNode("B");
        TreeNode c = new TreeNode("C");
        TreeNode d = new TreeNode("D");
        TreeNode e = new TreeNode("E");
        TreeNode f = new TreeNode("F");
        TreeNode g = new TreeNode("G");
        a.left = b; a.right = c;
        b.left = d; b.right = e;
        c.left = f; c.right = g;
        return a;
    }

    private JButton createStyledButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btn.setBackground(Color.WHITE);
        btn.setForeground(new Color(58, 123, 213));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return btn;
    }

    private void showIntro() {
        JTextArea intro = new JTextArea(
                "🌳 Welcome to Binary Tree Traversal!\n\n" +
                        "What is a Binary Tree?\n" +
                        "- Each node has at most two children (left and right).\n\n" +
                        "Types of Traversals:\n" +
                        "🔹 BFS (Level-order): Visit nodes level-by-level.\n" +
                        "🔹 DFS (Pre-order): Visit root, then left, then right.\n\n" +
                        "🎯 Your Goal:\n" +
                        "- Click nodes in the correct BFS or DFS order.\n" +
                        "- Learn how trees are traversed in different ways!\n\nClick OK to begin!");
        intro.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        intro.setWrapStyleWord(true);
        intro.setLineWrap(true);
        intro.setEditable(false);
        intro.setMargin(new Insets(10, 10, 10, 10));
        intro.setBackground(Color.WHITE);
        intro.setForeground(Color.DARK_GRAY);

        JScrollPane scroll = new JScrollPane(intro);
        scroll.setPreferredSize(new Dimension(600, 400));

        JOptionPane.showMessageDialog(
                this,
                scroll,
                "📖 Binary Tree Traversal Introduction",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TreeGame ("Gayatri"));
    }

    static class TreeNode {
        String val;
        TreeNode left, right;
        TreeNode(String val) {
            this.val = val;
        }
    }
}
