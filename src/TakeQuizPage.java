import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class TakeQuizPage extends JFrame {
    private List<Question> allQuestions = new ArrayList<>();
    private List<Question> quizQuestions = new ArrayList<>();
    private String username;
    private int currentQuestionIndex = 0;
    private int score = 0;

    // UI Components
    private JLabel titleLabel;
    private JLabel topicLabel;
    private JLabel questionLabel;
    private JButton[] optionButtons = new JButton[4];
    private JLabel scoreLabel;
    private JButton nextButton;

    // Colors
    private final Color PRIMARY_COLOR = new Color(70, 130, 180);
    private final Color SECONDARY_COLOR = new Color(240, 240, 240);
    private final Color CORRECT_COLOR = new Color(0, 153, 0);   // Dark Green
    private final Color WRONG_COLOR = new Color(204, 0, 0);     // Dark Red
    private final Color OPTION_COLOR = new Color(245, 245, 245);

    public TakeQuizPage(String username) {
        this.username = username; // Store the username
        setTitle("DSA Quiz Challenge");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Launch full screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initializeQuestions();
        selectRandomQuestions();
        initializeUI();
        showQuestion(0);
    }

    private void initializeQuestions() {
        allQuestions.add(new Question("Queue",
                "In a circular queue, how do you calculate the next position after last index?",
                new String[]{"(rear+1)%capacity", "rear+1", "rear-1", "rear%capacity"}, 0));

        allQuestions.add(new Question("Queue",
                "Which principle does a Queue follow?",
                new String[]{"FIFO", "LIFO", "Both", "None"}, 0));

        allQuestions.add(new Question("Stack",
                "What is the time complexity of push operation in Stack?",
                new String[]{"O(n)", "O(1)", "O(log n)", "O(n²)"}, 1));

        allQuestions.add(new Question("Stack",
                "Which data structure is used to implement recursion?",
                new String[]{"Queue", "Stack", "Array", "Linked List"}, 1));

        allQuestions.add(new Question("Sorting",
                "What is the worst case time complexity of Quick Sort?",
                new String[]{"O(n)", "O(n log n)", "O(n²)", "O(log n)"}, 2));

        allQuestions.add(new Question("Sorting",
                "Which sorting algorithm has O(n) best case time complexity?",
                new String[]{"Bubble Sort", "Merge Sort", "Insertion Sort", "Selection Sort"}, 2));

        allQuestions.add(new Question("Binary Tree",
                "Which traversal gives sorted order in BST?",
                new String[]{"Preorder", "Inorder", "Postorder", "Level order"}, 1));

        allQuestions.add(new Question("Binary Tree",
                "What is the maximum number of nodes at level l in binary tree?",
                new String[]{"l", "2l", "2^l", "2^(l-1)"}, 3));

        allQuestions.add(new Question("Linked List",
                "What is the time complexity to insert at beginning of singly linked list?",
                new String[]{"O(n)", "O(1)", "O(log n)", "O(n²)"}, 1));

        allQuestions.add(new Question("Linked List",
                "Which pointer exists in doubly linked list but not in singly linked list?",
                new String[]{"Head pointer", "Tail pointer", "Previous pointer", "Next pointer"}, 2));
    }

    private void selectRandomQuestions() {
        Collections.shuffle(allQuestions);
        for (int i = 0; i < 5 && i < allQuestions.size(); i++) {
            quizQuestions.add(allQuestions.get(i));
        }
    }

    private void initializeUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        mainPanel.setBackground(SECONDARY_COLOR);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        titleLabel = new JLabel("DSA Quiz Challenge", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        scoreLabel = new JLabel("Score: 0/" + quizQuestions.size(), SwingConstants.RIGHT);
        scoreLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        scoreLabel.setForeground(Color.WHITE);
        headerPanel.add(scoreLabel, BorderLayout.EAST);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new BorderLayout(20, 20));
        contentPanel.setBackground(SECONDARY_COLOR);

        topicLabel = new JLabel("", SwingConstants.CENTER);
        topicLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        topicLabel.setForeground(PRIMARY_COLOR);
        topicLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        topicLabel.setVisible(false); // Hide topic
        contentPanel.add(topicLabel, BorderLayout.NORTH);

        questionLabel = new JLabel("", SwingConstants.CENTER);
        questionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        questionLabel.setBorder(BorderFactory.createEmptyBorder(0, 50, 30, 50));
        contentPanel.add(questionLabel, BorderLayout.CENTER);

        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(0, 150, 30, 150));
        optionsPanel.setBackground(SECONDARY_COLOR);

        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JButton();
            optionButtons[i].setFont(new Font("Segoe UI", Font.PLAIN, 18));
            optionButtons[i].setBackground(OPTION_COLOR);
            optionButtons[i].setAlignmentX(Component.CENTER_ALIGNMENT);
            optionButtons[i].setMaximumSize(new Dimension(600, 60));
            optionButtons[i].setPreferredSize(new Dimension(600, 60));
            optionButtons[i].setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200)),
                    BorderFactory.createEmptyBorder(10, 20, 10, 20)
            ));
            optionButtons[i].setFocusPainted(false);

            final int index = i;
            optionButtons[i].addActionListener(e -> checkAnswer(index));

            optionsPanel.add(optionButtons[i]);
            optionsPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        }

        contentPanel.add(optionsPanel, BorderLayout.SOUTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(SECONDARY_COLOR);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        nextButton = new JButton("Next Question");
        nextButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        nextButton.setBackground(PRIMARY_COLOR);
        nextButton.setForeground(Color.WHITE);
        nextButton.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));
        nextButton.setFocusPainted(false);
        nextButton.setEnabled(false);
        nextButton.addActionListener(e -> nextQuestion());

        buttonPanel.add(nextButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void showQuestion(int index) {
        Question current = quizQuestions.get(index);
        questionLabel.setText("<html><div style='text-align:center;'>" + current.question + "</div></html>");

        for (int i = 0; i < 4; i++) {
            optionButtons[i].setText(current.options[i]);
            optionButtons[i].setEnabled(true);
            optionButtons[i].setBackground(OPTION_COLOR);
            optionButtons[i].setForeground(Color.BLACK); // Reset font color
        }

        scoreLabel.setText("Score: " + score + "/" + quizQuestions.size());
    }

    private void checkAnswer(int selectedOption) {
        Question current = quizQuestions.get(currentQuestionIndex);

        for (JButton button : optionButtons) {
            button.setEnabled(false);
        }

        optionButtons[current.correctAnswer].setBackground(CORRECT_COLOR);
        optionButtons[current.correctAnswer].setForeground(Color.WHITE);

        if (selectedOption == current.correctAnswer) {
            score++;
            scoreLabel.setText("Score: " + score + "/" + quizQuestions.size());
        } else {
            optionButtons[selectedOption].setBackground(WRONG_COLOR);
            optionButtons[selectedOption].setForeground(Color.WHITE);
        }

        nextButton.setEnabled(true);
    }

    private void nextQuestion() {
        currentQuestionIndex++;
        if (currentQuestionIndex < quizQuestions.size()) {
            showQuestion(currentQuestionIndex);
            nextButton.setEnabled(false);
        } else {
            showResults();
        }
    }

    private void showResults() {
        double percentage = (double) score / quizQuestions.size() * 100;
        String performance;

        if (percentage >= 80) {
            performance = "Excellent! You're a DSA expert!";
        } else if (percentage >= 60) {
            performance = "Good job! Keep practicing!";
        } else if (percentage >= 40) {
            performance = "Not bad, but you can do better!";
        } else {
            performance = "Keep learning! Review the concepts and try again!";
        }

        String message = String.format(
                "<html><div style='text-align:center;font-size:14pt;'>" +
                        "<h2>Quiz Completed!</h2>" +
                        "<p>Your score: <b>%d/%d (%.1f%%)</b></p>" +
                        "<p>%s</p></div></html>",
                score, quizQuestions.size(), percentage, performance
        );

        JOptionPane.showMessageDialog(this, message, "Quiz Results", JOptionPane.INFORMATION_MESSAGE);
        dispose();

        // Launch HomePage (replace 'HomePage' with your actual home class)
        new Home(username).setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TakeQuizPage("").setVisible(true);
        });
    }

    private static class Question {
        String topic;
        String question;
        String[] options;
        int correctAnswer;

        public Question(String topic, String question, String[] options, int correctAnswer) {
            this.topic = topic;
            this.question = question;
            this.options = options;
            this.correctAnswer = correctAnswer;
        }
    }
}
