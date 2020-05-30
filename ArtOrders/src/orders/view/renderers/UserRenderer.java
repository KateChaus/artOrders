package orders.view.renderers;

import orders.model.entities.User;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class UserRenderer extends JPanel implements ListCellRenderer {
    JPanel pane = new JPanel();

    public UserRenderer() {
        add(pane);
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        User user = (User) value;

        pane.removeAll();
        pane.setLayout(new BoxLayout(pane, BoxLayout.X_AXIS));
        pane.setPreferredSize(new Dimension(400,150));

        JLabel picLabel = new JLabel("No avatar");
        try {
            BufferedImage avatar = ImageIO.read(new File(user.getAvatar()));
            int height = 150;
            int width = (int) (avatar.getWidth() * (height * 1.0 / avatar.getHeight()));
            BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = newImage.createGraphics();
            g.drawImage(avatar, 0, 0, width, height, null);
            g.dispose();

            picLabel = new JLabel(new ImageIcon(newImage));
        } catch (IOException e) {
        }

        JLabel username = new JLabel(user.getLogin());
        username.setFont(new Font(username.getFont().getFontName(), Font.BOLD, 20));

        pane.add(picLabel);
        pane.add(Box.createHorizontalStrut(20));
        pane.add(username);

        return this;
    }
}
