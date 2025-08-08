import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.Queue;

public class QueueGame extends JFrame {
    private final Queue<String> queue = new LinkedList<>();
    private final JPanel queuePanel = new JPanel();
    private final JTextArea logArea = new JTextArea();
    private int score = 0;

    public QueueGame(String username) {
        setTitle("Queue Simulator - DSA Game Suite");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        showIntro(); // Display intro popup first

        // Gradient background
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
        JLabel title = new JLabel("🚎 Queue Simulator - Enqueue, Dequeue, Peek");
        title.setFont(new Font("Segoe UI", Font.BOLD, 34));
        title.setForeground(Color.WHITE);
        bgPanel.add(title, BorderLayout.NORTH);

        // Center layout
        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 20, 20));
        centerPanel.setOpaque(false);

        // Queue visual panel
        JPanel visualPanel = new JPanel(new BorderLayout());
        visualPanel.setOpaque(false);

        JLabel queueLabel = new JLabel("🚶 Queue View (Front → Rear)", JLabel.CENTER);
        queueLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        queueLabel.setForeground(Color.WHITE);

        queuePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
        queuePanel.setBackground(Color.WHITE);
        JScrollPane visualScroll = new JScrollPane(queuePanel);

        visualPanel.add(queueLabel, BorderLayout.NORTH);
        visualPanel.add(visualScroll, BorderLayout.CENTER);

        // Log panel
        logArea.setEditable(false);
        logArea.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        JScrollPane logScroll = new JScrollPane(logArea);
        logScroll.setBorder(BorderFactory.createTitledBorder("📄 Action Log"));

        centerPanel.add(visualPanel);
        centerPanel.add(logScroll);

        // Bottom controls
        JTextField inputField = new JTextField(10);
        inputField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        JButton enqueueBtn = createStyledButton("Enqueue");
        JButton dequeueBtn = createStyledButton("Dequeue");
        JButton peekBtn = createStyledButton("Peek");
        JButton finishBtn = createStyledButton("Finish");

        JPanel controlPanel = new JPanel();
        controlPanel.setOpaque(false);
        controlPanel.setLayout(new FlowLayout());
        controlPanel.add(new JLabel("Enter value: "));
        controlPanel.add(inputField);
        controlPanel.add(enqueueBtn);
        controlPanel.add(dequeueBtn);
        controlPanel.add(peekBtn);
        controlPanel.add(finishBtn);

        // Add to main layout
        bgPanel.add(centerPanel, BorderLayout.CENTER);
        bgPanel.add(controlPanel, BorderLayout.SOUTH);
        add(bgPanel);

        // Button actions
        enqueueBtn.addActionListener(e -> {
            String val = inputField.getText().trim();
            if (!val.isEmpty()) {
                queue.add(val);
                addToQueueVisualizer(val);
                logArea.append("✅ Enqueued: " + val + "\n");
                inputField.setText("");
                score += 2;
            } else {
                logArea.append("⚠️ Enter a value to enqueue.\n");
            }
        });

        dequeueBtn.addActionListener(e -> {
            if (!queue.isEmpty()) {
                String removed = queue.poll();
                removeFromQueueVisualizer();
                logArea.append("🚪 Dequeued: " + removed + "\n");
                score += 1;
            } else {
                logArea.append("❌ Queue is empty! Cannot dequeue.\n");
            }
        });

        peekBtn.addActionListener(e -> {
            if (!queue.isEmpty()) {
                String peek = queue.peek();
                logArea.append("👀 Front Element: " + peek + "\n");
                highlightFrontElement();
                score += 1;
            } else {
                logArea.append("❌ Queue is empty! Nothing to peek.\n");
            }
        });

        finishBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(
                    this,
                    "🎉 Great job, " + username + "!\n\n" +
                            "📚 What you learned:\n" +
                            "- Queue follows First In First Out (FIFO).\n" +
                            "- Enqueue adds to the rear.\n" +
                            "- Dequeue removes from the front.\n" +
                            "- Peek shows the front without removing.\n\n" +
                            "Keep practicing and mastering queues!",
                    "Session Summary",
                    JOptionPane.INFORMATION_MESSAGE
            );
            dispose();
            new Home(username);
        });


        setVisible(true);
    }

    private void addToQueueVisualizer(String value) {
        JLabel label = createQueueLabel(value);
        queuePanel.add(label);
        queuePanel.revalidate();
        queuePanel.repaint();
    }

    private void removeFromQueueVisualizer() {
        if (queuePanel.getComponentCount() > 0) {
            queuePanel.remove(0);
            queuePanel.revalidate();
            queuePanel.repaint();
        }
    }

    private void highlightFrontElement() {
        if (queuePanel.getComponentCount() > 0) {
            Component front = queuePanel.getComponent(0);
            front.setBackground(new Color(255, 255, 180)); // yellow
        }
    }

    private JLabel createQueueLabel(String value) {
        JLabel label = new JLabel(value, JLabel.CENTER);
        label.setOpaque(true);
        label.setBackground(Color.WHITE);
        label.setForeground(new Color(58, 123, 213));
        label.setFont(new Font("Segoe UI", Font.BOLD, 18));
        label.setBorder(BorderFactory.createLineBorder(new Color(58, 123, 213)));
        label.setPreferredSize(new Dimension(80, 40));
        return label;
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
                "🎓 Welcome to the Queue Simulator!\n\n" +
                        "What is a Queue?\n" +
                        "- A linear structure that works on FIFO (First In First Out).\n\n" +
                        "Main Operations:\n" +
                        "🔹 Enqueue: Add item to the rear\n" +
                        "🔹 Dequeue: Remove item from the front\n" +
                        "🔹 Peek: View front without removing\n\n" +
                        "Why are Queues important?\n" +
                        "- Used in task scheduling, print queues, and OS processes.\n\n" +
                        "🎯 Your Goal:\n" +
                        "- Practice these operations visually.\n" +
                        "- Understand the flow of elements through the queue.\n\n" +
                        "Click OK to begin!"
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
                "📖 Queue Introduction",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new QueueGame("Gayatri"));
    }
}
