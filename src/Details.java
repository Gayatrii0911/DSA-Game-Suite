import javax.swing.*;
import java.awt.*;
import java.sql.*;

class Details extends JFrame {
    private String username;

    Details(String username) {
        this.username = username;

        setTitle("Signup Details - DSA Game Suite");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Fullscreen
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Gradient background
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
        JLabel title = new JLabel("Enter Your Details");
        title.setFont(titleFont);
        title.setForeground(Color.WHITE);

        JTextField t1 = new JTextField(20); // Name
        JTextField t2 = new JTextField(20); // Age
        JTextField t3 = new JTextField(20); // Address
        JTextField t4 = new JTextField(20); // Phone
        JTextField t5 = new JTextField(20); // Email

        JLabel l1 = new JLabel("Name:");
        JLabel l2 = new JLabel("Age:");
        JLabel l3 = new JLabel("Address:");
        JLabel l4 = new JLabel("Phone:");
        JLabel l5 = new JLabel("Email:");

        JLabel[] labels = {l1, l2, l3, l4, l5};
        JTextField[] fields = {t1, t2, t3, t4, t5};

        for (JLabel label : labels) {
            label.setFont(labelFont);
            label.setForeground(Color.WHITE);
        }
        for (JTextField field : fields) {
            field.setFont(fieldFont);
        }

        JButton b1 = new JButton("Submit");
        JButton b2 = new JButton("Back");

        b1.setFont(buttonFont);
        b2.setFont(buttonFont);
        b1.setFocusPainted(false);
        b2.setFocusPainted(false);
        b1.setBackground(Color.WHITE);
        b2.setBackground(Color.WHITE);
        b1.setForeground(new Color(58, 123, 213));
        b2.setForeground(new Color(58, 123, 213));

        // Layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        bgPanel.add(title, gbc);

        gbc.gridwidth = 1;

        String[] labelNames = {"Name:", "Age:", "Address:", "Phone:", "Email:"};

        for (int i = 0; i < labels.length; i++) {
            gbc.gridy++;
            gbc.gridx = 0;
            bgPanel.add(labels[i], gbc);
            gbc.gridx = 1;
            bgPanel.add(fields[i], gbc);
        }

        // Button panel aligned with fields
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        btnPanel.setOpaque(false);
        btnPanel.add(b1);
        btnPanel.add(b2);

        gbc.gridx = 1;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.WEST;
        bgPanel.add(btnPanel, gbc);

        add(bgPanel);

        // Submit Logic
        b1.addActionListener(e -> {
            try {
                String name = t1.getText().trim();
                int age = Integer.parseInt(t2.getText().trim());
                String address = t3.getText().trim();
                String phone = t4.getText().trim();
                String email = t5.getText().trim();

                if (age < 18) {
                    JOptionPane.showMessageDialog(this, "Age must be 18 or above.");
                    return;
                }
                if (phone.length() != 10 || !phone.matches("\\d+")) {
                    JOptionPane.showMessageDialog(this, "Phone number must be 10 digits.");
                    return;
                }

                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/game", "root", "Gayatri@09");
                PreparedStatement pst = con.prepareStatement("UPDATE users SET name=?, age=?, address=?, phone=?, email=? WHERE username=?");
                pst.setString(1, name);
                pst.setInt(2, age);
                pst.setString(3, address);
                pst.setString(4, phone);
                pst.setString(5, email);
                pst.setString(6, username);
                pst.executeUpdate();

                JOptionPane.showMessageDialog(this, "Details Submitted Successfully");
                new Login();
                dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        // Back
        b2.addActionListener(e -> {
            new Signup(username);
            dispose();
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Details("Gayatri"));
    }
}
