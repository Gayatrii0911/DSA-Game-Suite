import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Signup extends JFrame {

    public Signup(String username) {
        setTitle("Signup - DSA Game Suite");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Fullscreen
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Background Panel with Gradient
        JPanel bgPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                int w = getWidth();
                int h = getHeight();
                Color c1 = new Color(58, 123, 213);
                Color c2 = new Color(0, 210, 255);
                GradientPaint gp = new GradientPaint(0, 0, c1, w, h, c2);
                g2.setPaint(gp);
                g2.fillRect(0, 0, w, h);
            }
        };
        bgPanel.setLayout(new GridBagLayout());

        // Fonts
        Font titleFont = new Font("Segoe UI", Font.BOLD, 42);
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 20);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 18);
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 18);

        // Components
        JLabel title = new JLabel("Create Your Account");
        title.setFont(titleFont);
        title.setForeground(Color.WHITE);

        JLabel l1 = new JLabel("Username:");
        l1.setFont(labelFont);
        l1.setForeground(Color.WHITE);
        JTextField t1 = new JTextField(username, 20);
        t1.setFont(fieldFont);

        JLabel l2 = new JLabel("Password:");
        l2.setFont(labelFont);
        l2.setForeground(Color.WHITE);
        JPasswordField t2 = new JPasswordField(20);
        t2.setFont(fieldFont);

        JLabel l3 = new JLabel("Confirm Password:");
        l3.setFont(labelFont);
        l3.setForeground(Color.WHITE);
        JPasswordField t3 = new JPasswordField(20);
        t3.setFont(fieldFont);

        JButton b1 = new JButton("Sign Up");
        JButton b2 = new JButton("Back");

        // Button styling
        Color btnColor = Color.WHITE;
        Color textColor = new Color(58, 123, 213);

        b1.setBackground(btnColor);
        b1.setForeground(textColor);
        b1.setFont(buttonFont);
        b1.setFocusPainted(false);

        b2.setBackground(btnColor);
        b2.setForeground(textColor);
        b2.setFont(buttonFont);
        b2.setFocusPainted(false);

        // Layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        bgPanel.add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        bgPanel.add(l1, gbc);
        gbc.gridx = 1;
        bgPanel.add(t1, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        bgPanel.add(l2, gbc);
        gbc.gridx = 1;
        bgPanel.add(t2, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        bgPanel.add(l3, gbc);
        gbc.gridx = 1;
        bgPanel.add(t3, gbc);

        // Button Row: Sign Up and Back side-by-side aligned to text fields
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0)); // Left aligned
        btnPanel.setOpaque(false); // Transparent background
        btnPanel.add(b1);
        btnPanel.add(b2);

        gbc.gridx = 1; // aligned with input fields
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        bgPanel.add(btnPanel, gbc);




        add(bgPanel);

        // Save logic
        b1.addActionListener(a -> {
            String user = t1.getText().trim();
            String pass = new String(t2.getPassword());
            String confirm = new String(t3.getPassword());

            if (user.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username cannot be empty");
            } else if (pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Password cannot be empty");
            } else if (!pass.equals(confirm)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match");
            } else {
                try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/game", "root", "Gayatri@09")) {
                    String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
                    PreparedStatement pst = con.prepareStatement(sql);
                    pst.setString(1, user);
                    pst.setString(2, pass);
                    pst.executeUpdate();

                    JOptionPane.showMessageDialog(this, "Signup Successful!");
                    new Details(user); // Go to details filling page
                    dispose();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, e.getMessage());
                }
            }
        });

        // Back
        b2.addActionListener(a -> {
            new WelcomePage(); // Go back to welcome screen
            dispose();
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Signup(""));
    }
}

