import javax.swing.*;
import java.awt.*;

public class WelcomePage extends JFrame {

    public WelcomePage() {
        setTitle("Welcome - DSA Game Suite");

        // Make the window fullscreen
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(false); // Keep title bar
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Create a panel with custom background
        JPanel backgroundPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(58, 123, 213);
                Color color2 = new Color(0, 210, 255);
                int w = getWidth();
                int h = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, color1, w, h, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        backgroundPanel.setLayout(new BoxLayout(backgroundPanel, BoxLayout.Y_AXIS));

        // Title
        JLabel titleLabel = new JLabel("DSA Game Suite", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 60));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Subtitle
        JLabel subtitle = new JLabel("Play & Learn Data Structures and Algorithms", SwingConstants.CENTER);
        subtitle.setFont(new Font("Segoe UI", Font.ITALIC, 26));
        subtitle.setForeground(Color.WHITE);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Buttons
        JButton loginBtn = new JButton("Login");
        JButton signupBtn = new JButton("Signup");

        loginBtn.setPreferredSize(new Dimension(200, 50));
        signupBtn.setPreferredSize(new Dimension(200, 50));

        Font btnFont = new Font("Segoe UI", Font.PLAIN, 20);
        loginBtn.setFont(btnFont);
        signupBtn.setFont(btnFont);

        // Style the buttons
        loginBtn.setBackground(new Color(255, 255, 255));
        loginBtn.setForeground(new Color(58, 123, 213));
        loginBtn.setFocusPainted(false);

        signupBtn.setBackground(new Color(255, 255, 255));
        signupBtn.setForeground(new Color(58, 123, 213));
        signupBtn.setFocusPainted(false);

        // Buttons panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 20));
        buttonPanel.add(loginBtn);
        buttonPanel.add(signupBtn);

        // Centering layout
        backgroundPanel.add(Box.createVerticalGlue());
        backgroundPanel.add(titleLabel);
        backgroundPanel.add(Box.createVerticalStrut(20));
        backgroundPanel.add(subtitle);
        backgroundPanel.add(Box.createVerticalStrut(50));
        backgroundPanel.add(buttonPanel);
        backgroundPanel.add(Box.createVerticalGlue());

        add(backgroundPanel);

        // Button Actions
        loginBtn.addActionListener(e -> {
            dispose();
            new Login();
        });

        signupBtn.addActionListener(e -> {
            dispose();
            new Signup("");
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(WelcomePage::new);
    }
}
