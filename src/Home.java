import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Home extends JFrame {

    public Home(String username) {
        setTitle("DSA Game Suite - Home");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel bgPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(58, 123, 213),
                        getWidth(), getHeight(), new Color(0, 210, 255));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        bgPanel.setLayout(new BorderLayout(20, 20));
        bgPanel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        JLabel welcome = new JLabel("Welcome, " + username + " 👋");
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 36));
        welcome.setForeground(Color.WHITE);
        topPanel.add(welcome, BorderLayout.WEST);

        // Add Take Quiz button to the top right
        JButton takeQuizButton = new JButton("Take Quiz 🧠");
        takeQuizButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        takeQuizButton.setBackground(new Color(255, 215, 0)); // Gold color
        takeQuizButton.setForeground(Color.BLACK);
        takeQuizButton.setFocusPainted(false);
        takeQuizButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 165, 0), 2),
                BorderFactory.createEmptyBorder(8, 20, 8, 20)));
        takeQuizButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        takeQuizButton.addActionListener(e -> {
            // Close current window
            dispose();

            // Open new window on the EDT
            SwingUtilities.invokeLater(() -> {
                TakeQuizPage quizPage = new TakeQuizPage(username);
                quizPage.setVisible(true);
            });
        });
        topPanel.add(takeQuizButton, BorderLayout.EAST);

        JPanel cardGrid = new JPanel(new GridLayout(2, 3, 30, 30));
        cardGrid.setOpaque(false);

        cardGrid.add(createCard("Sorting Visualizer", "Visualize Bubble, Quick, and Merge sorts.",
                () -> new SortingGame(username),
                () -> new SortingConcept(username)));

        cardGrid.add(createCard("Stack Challenge", "Push and pop from stacks, test your LIFO skills!",
                () -> new StackGame(username),
                () -> new StackConcept(username)));

        cardGrid.add(createCard("Queue Simulator", "Learn FIFO with real-time queue simulation.",
                () -> new QueueGame(username),
                () -> new QueueConcept(username)));

        cardGrid.add(createCard("Binary Tree Traversal", "Explore Inorder, Preorder, Postorder traversals.",
                () -> new TreeGame(username),
                () -> new TreeConcept(username)));

        cardGrid.add(createCard("Linked List", "Add, delete & reverse nodes visually!",
                () -> new LinkedListGame(username),
                () -> new LinkedListConcept(username)));

        cardGrid.add(createCard("Array Memory Game", "Test your memory with array element challenges.",
                () -> new ArrayGame(username),
                () -> new ArrayConcept(username)));

        JLabel footer = new JLabel("🎮 Keep learning DSA the fun way | © 2025 DSA Game Suite", JLabel.CENTER);
        footer.setFont(new Font("Segoe UI", Font.ITALIC, 16));
        footer.setForeground(Color.WHITE);

        bgPanel.add(topPanel, BorderLayout.NORTH);
        bgPanel.add(cardGrid, BorderLayout.CENTER);
        bgPanel.add(footer, BorderLayout.SOUTH);
        add(bgPanel);
        setVisible(true);
    }

    private JPanel createCard(String title, String desc, Runnable playAction, Runnable learnAction) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 255), 2),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(new Color(40, 40, 90));

        JTextArea description = new JTextArea(desc);
        description.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        description.setWrapStyleWord(true);
        description.setLineWrap(true);
        description.setEditable(false);
        description.setOpaque(false);
        description.setFocusable(false);
        description.setForeground(Color.DARK_GRAY);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 10, 5));
        buttonPanel.setOpaque(false);

        JButton playButton = new JButton("▶ Play Now");
        playButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        playButton.setBackground(new Color(100, 180, 255));
        playButton.setForeground(Color.WHITE);
        playButton.setFocusPainted(false);
        playButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        playButton.addActionListener(e -> playAction.run());

        JButton learnButton = new JButton("📘 Learn Concept");
        learnButton.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        learnButton.setBackground(new Color(230, 240, 255));
        learnButton.setForeground(new Color(30, 60, 100));
        learnButton.setFocusPainted(false);
        learnButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        learnButton.addActionListener(e -> learnAction.run());

        buttonPanel.add(playButton);
        buttonPanel.add(learnButton);

        card.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                card.setBackground(new Color(245, 245, 255));
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(100, 180, 255), 3),
                        BorderFactory.createEmptyBorder(20, 20, 20, 20)));
            }

            public void mouseExited(MouseEvent e) {
                card.setBackground(Color.WHITE);
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(200, 200, 255), 2),
                        BorderFactory.createEmptyBorder(20, 20, 20, 20)));
            }
        });

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(description, BorderLayout.CENTER);
        card.add(buttonPanel, BorderLayout.SOUTH);
        return card;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Home("Gayatri"));
    }
}