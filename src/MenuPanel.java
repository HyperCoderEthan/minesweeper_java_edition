import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class MenuPanel extends JPanel{
    JButton easy, medium, hard;
    GridBagConstraints locator;
    Color color1, color2;

    public MenuPanel() {
        setLayout(new GridBagLayout());

        easy = new JButton("easy");
        easy.setBackground(Color.GREEN);
        easy.setFont(new Font("Comic Sans MS", Font.PLAIN, 17));
        medium = new JButton("medium");
        hard = new JButton("7th layer of hell");
        hard.setBackground(Color.RED);

        easy.setActionCommand("e");
        medium.setActionCommand("m");
        hard.setActionCommand("h");

        locator = new GridBagConstraints();
        locator.insets = new Insets(20, 20, 20, 20);
        locator.gridx = 0;
        locator.gridy = 0;
        ImageIcon titleImg = new ImageIcon("resources/MINESWEEPER LOGO.png");
        JLabel title = new JLabel(titleImg);
        add(title, locator);

        locator.gridy = 1;
        add(easy, locator);
        locator.gridy = 2;
        add(medium, locator);
        locator.gridy = 3;
        add(hard, locator);

        color1 = new Color(167, 93, 93);
        color2 = new Color(255, 195, 161);
    }

    public void addActionListener(ActionListener e) {
        easy.addActionListener(e);
        medium.addActionListener(e);
        hard.addActionListener(e);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage bg = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = bg.createGraphics();
        graphics.setPaint(new GradientPaint(0, 0, color2, getWidth(), getHeight(), color1));
        graphics.fillRect(0, 0, getWidth(), getHeight());
        g.drawImage(bg, 0, 0, this);
    }
}
