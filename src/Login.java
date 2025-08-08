import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Login extends JFrame {
    public static String loggedInUsername;

    public Login() {
        setTitle("Login - DSA Game Suite");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Fullscreen
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Gradient background panel
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
        Font titleFont = new Font("Segoe UI", Font.BOLD, 48);
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 20);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 18);
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 18);

        // Components
        JLabel title = new JLabel("Login to DSA Game Suite");
        title.setFont(titleFont);
        title.setForeground(Color.WHITE);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(labelFont);
        userLabel.setForeground(Color.WHITE);

        JTextField userField = new JTextField(20);
        userField.setFont(fieldFont);
        userField.setMaximumSize(userField.getPreferredSize());

        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(labelFont);
        passLabel.setForeground(Color.WHITE);

        JPasswordField passField = new JPasswordField(20);
        passField.setFont(fieldFont);
        passField.setMaximumSize(passField.getPreferredSize());

        JButton loginBtn = new JButton("Login");
        JButton backBtn = new JButton("Back");

        // Button styling
        Color primaryColor = new Color(255, 255, 255);
        Color textColor = new Color(58, 123, 213);

        loginBtn.setBackground(primaryColor);
        loginBtn.setForeground(textColor);
        loginBtn.setFont(buttonFont);
        loginBtn.setFocusPainted(false);

        backBtn.setBackground(primaryColor);
        backBtn.setForeground(textColor);
        backBtn.setFont(buttonFont);
        backBtn.setFocusPainted(false);

        // Layout setup
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        bgPanel.add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        bgPanel.add(userLabel, gbc);
        gbc.gridx = 1;
        bgPanel.add(userField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        bgPanel.add(passLabel, gbc);
        gbc.gridx = 1;
        bgPanel.add(passField, gbc);

        // Create a button row panel
        JPanel buttonRow = new JPanel();
        buttonRow.setOpaque(false);
        buttonRow.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 0));
        buttonRow.add(loginBtn);
        buttonRow.add(backBtn);

// Add to GridBagLayout
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        bgPanel.add(buttonRow, gbc);


        add(bgPanel);

        // Button actions
        loginBtn.addActionListener(e -> {
            String url = "jdbc:mysql://localhost:3306/game"; // Change to your DB name
            String uname = userField.getText();
            String pass = new String(passField.getPassword());

            try (Connection con = DriverManager.getConnection(url, "root", "Gayatri@09")) {
                String sql = "SELECT * FROM users WHERE username=? AND password=?";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1, uname);
                pst.setString(2, pass);

                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    loggedInUsername = uname;
                    JOptionPane.showMessageDialog(this, "Login Successful!");
                    new Home(uname); // or HomePage(uname)
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid username or password");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        backBtn.addActionListener(e -> {
            new WelcomePage(); // go back to welcome screen
            dispose();
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Login::new);
    }
}
