import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Stack;

public class StackGame extends JFrame {
    private final Stack<String> stack = new Stack<>();
    private final JPanel stackVisualPanel = new JPanel();
    private final JTextArea logArea = new JTextArea();
    private int score = 0;

    public StackGame(String username) {
        setTitle("Stack Challenge - DSA Game Suite");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Introduction first
        showIntro();

        // Background panel
        JPanel bgPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setPaint(new GradientPaint(0, 0, new Color(58, 123, 213), getWidth(), getHeight(), new Color(0, 210, 255)));
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        bgPanel.setLayout(new BorderLayout(20, 20));
        bgPanel.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));

        // Top - Title
        JLabel title = new JLabel("📚 Stack Challenge - Push, Pop, Peek!");
        title.setFont(new Font("Segoe UI", Font.BOLD, 34));
        title.setForeground(Color.WHITE);
        bgPanel.add(title, BorderLayout.NORTH);

        // Center panel with stack & log
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 30, 30));
        centerPanel.setOpaque(false);

        // Stack visual panel
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setOpaque(false);

        JLabel stackLabel = new JLabel("🧱 Stack Visualizer", JLabel.CENTER);
        stackLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        stackLabel.setForeground(Color.WHITE);

        stackVisualPanel.setLayout(new BoxLayout(stackVisualPanel, BoxLayout.Y_AXIS));
        stackVisualPanel.setBackground(Color.WHITE);
        JScrollPane visualScroll = new JScrollPane(stackVisualPanel);

        leftPanel.add(stackLabel, BorderLayout.NORTH);
        leftPanel.add(visualScroll, BorderLayout.CENTER);

        // Log panel
        logArea.setEditable(false);
        logArea.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        JScrollPane logScroll = new JScrollPane(logArea);
        logScroll.setBorder(BorderFactory.createTitledBorder("📄 Action Log"));

        centerPanel.add(leftPanel);
        centerPanel.add(logScroll);

        // Buttons
        JTextField inputField = new JTextField(10);
        inputField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        JButton pushBtn = createStyledButton("Push");
        JButton popBtn = createStyledButton("Pop");
        JButton peekBtn = createStyledButton("Peek");
        JButton finishBtn = createStyledButton("Finish");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(new JLabel("Enter value: "));
        buttonPanel.add(inputField);
        buttonPanel.add(pushBtn);
        buttonPanel.add(popBtn);
        buttonPanel.add(peekBtn);
        buttonPanel.add(finishBtn);

        // Add to layout
        bgPanel.add(centerPanel, BorderLayout.CENTER);
        bgPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(bgPanel);

        // Actions
        pushBtn.addActionListener(e -> {
            resetVisualHighlights();
            String val = inputField.getText().trim();
            if (!val.isEmpty()) {
                stack.push(val);
                addToStackVisualizer(val);
                logArea.append("✅ Pushed: " + val + "\n");
                inputField.setText("");
                score += 2;
            } else {
                logArea.append("⚠️ Enter a value to push.\n");
            }
        });

        popBtn.addActionListener(e -> {
            resetVisualHighlights();
            if (!stack.isEmpty()) {
                String popped = stack.pop();
                removeFromStackVisualizer();
                logArea.append("🗑️ Popped: " + popped + "\n");
                score += 1;
            } else {
                logArea.append("❌ Stack is empty! Cannot pop.\n");
            }
        });

        peekBtn.addActionListener(e -> {
            resetVisualHighlights();
            if (!stack.isEmpty()) {
                String peek = stack.peek();
                logArea.append("👀 Top Element: " + peek + "\n");
                score += 1;

                if (stackVisualPanel.getComponentCount() > 0) {
                    Component topComponent = stackVisualPanel.getComponent(0);
                    topComponent.setBackground(new Color(255, 255, 150)); // light yellow
                }
            } else {
                logArea.append("❌ Stack is empty! Nothing to peek.\n");
            }
        });

        finishBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(
                    this,
                    "🎉 Great job, " + username + "!\n\n" +
                            "📚 What you learned:\n" +
                            "- Stack follows Last In First Out (LIFO).\n" +
                            "- Push adds elements to the top.\n" +
                            "- Pop removes from the top.\n" +
                            "- Peek shows the top without removing.\n\n" +
                            "Keep practicing and mastering stacks!",
                    "Session Summary",
                    JOptionPane.INFORMATION_MESSAGE
            );
            dispose();
            new Home(username);
        });


        setVisible(true);
    }

    private void addToStackVisualizer(String value) {
        JLabel label = new JLabel(value, JLabel.CENTER);
        label.setOpaque(true);
        label.setBackground(new Color(255, 255, 255));
        label.setForeground(new Color(58, 123, 213));
        label.setFont(new Font("Segoe UI", Font.BOLD, 18));
        label.setBorder(BorderFactory.createLineBorder(new Color(58, 123, 213)));
        label.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        stackVisualPanel.add(label, 0);
        stackVisualPanel.revalidate();
        stackVisualPanel.repaint();
    }

    private void removeFromStackVisualizer() {
        if (stackVisualPanel.getComponentCount() > 0) {
            stackVisualPanel.remove(0);
            stackVisualPanel.revalidate();
            stackVisualPanel.repaint();
        }
    }

    private void resetVisualHighlights() {
        for (Component comp : stackVisualPanel.getComponents()) {
            comp.setBackground(Color.WHITE);
        }
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
                "📚 Welcome to the Stack Challenge!\n\n" +
                        "What is a Stack?\n" +
                        "- A linear data structure that works on LIFO (Last In First Out).\n\n" +
                        "Main Operations:\n" +
                        "🔹 Push: Add item to the top\n" +
                        "🔹 Pop: Remove the top item\n" +
                        "🔹 Peek: View the top item without removing\n\n" +
                        "Why are Stacks important?\n" +
                        "- Used in Undo operations, recursion, browser history, etc.\n\n" +
                        "🎯 Your Goal:\n" +
                        "- Use push, pop, and peek correctly to manage your stack.\n" +
                        "- Learn how elements flow through stack operations.\n\n" +
                        "Click OK to start playing!"
        );
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
                "📖 Stack Introduction",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StackGame("Gayatri"));
    }
}
