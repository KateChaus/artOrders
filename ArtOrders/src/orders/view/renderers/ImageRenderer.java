package orders.view.renderers;

import orders.model.entities.ArtOrder;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageRenderer extends JPanel implements ListCellRenderer {

    JPanel pane = new JPanel();

    public ImageRenderer() {
        add(pane);
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        ArtOrder artOrder = (ArtOrder) value;
        try {
            pane.removeAll();
            pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

            BufferedImage image = ImageIO.read(new File(artOrder.getImage()));

            int width = 300;
            int height = (int) (image.getHeight() * (width * 1.0 / image.getWidth()));
            BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = newImage.createGraphics();
            g.drawImage(image, 0, 0, width, height, null);
            g.dispose();

            JLabel picLabel = new JLabel(new ImageIcon(newImage));

            JLabel nameLabel = new JLabel("Художник: " + artOrder.getOrderApplication().getArtist().getLogin());
            nameLabel.setFont(new Font(nameLabel.getFont().getFontName(), Font.BOLD, 15));

            pane.add(picLabel);
            pane.add(nameLabel);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }
}
