package orders.view.renderers;

import orders.model.entities.ArtOrder;
import orders.model.enums.OrderStatus;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class OrderRenderer extends JPanel implements ListCellRenderer {
    JPanel pane = new JPanel();
    private boolean isIn;

    public OrderRenderer(boolean isIn) {
        this.isIn = isIn;
        this.setBorder(BorderFactory.createLineBorder(new Color(225, 225, 225)));
        add(pane);
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        ArtOrder order = (ArtOrder) value;
        try {
            pane.removeAll();
            pane.setPreferredSize(new Dimension(400, 150));
            if (isSelected) {
                this.setBackground(new Color(225, 225, 225));
            } else {
                this.setBackground(SystemColor.control);
            }


            JLabel artist;
            if (isIn) {
                artist = new JLabel("Заказчик: " + order.getCustomer().getLogin());
            } else {
                artist = new JLabel("Художник: " + order.getOrderApplication().getArtist().getLogin());
            }
            JLabel type = new JLabel("Тип: " + order.getOrderApplication().getType().toString());
            JLabel style = new JLabel("Стиль: " + order.getOrderApplication().getStyle().toString());
            JLabel cost = new JLabel("Стоимость: " + order.getOrderApplication().getCost());
            JTextArea description = new JTextArea(order.getOrderApplication().getInfo());
            description.setBorder(BorderFactory.createTitledBorder("Описание"));
            description.setOpaque(false);

            artist.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
            artist.setFont(new Font(artist.getFont().getFontName(), Font.BOLD, 15));

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(0, 3));
            panel.setOpaque(false);
            type.setHorizontalAlignment(JLabel.CENTER);
            style.setHorizontalAlignment(JLabel.CENTER);
            cost.setHorizontalAlignment(JLabel.CENTER);

            panel.add(style);
            panel.add(cost);
            panel.add(type);
            panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

            GridBagLayout gridBagLayout = new GridBagLayout();
            GridBagConstraints c = new GridBagConstraints();
            c.weightx = 0;
            c.weighty = 0;
            c.anchor = GridBagConstraints.CENTER;
            c.fill = GridBagConstraints.CENTER;

            gridBagLayout.setConstraints(pane, c);
            pane.setLayout(gridBagLayout);
            c.gridy = 0;
            pane.add(artist, c);
            c.gridy = 1;
            c.fill = GridBagConstraints.HORIZONTAL;

            pane.add(panel, c);
            c.gridy = 2;
            c.gridx = 0;
            c.weighty = 1;
            c.weightx = 1;
            c.fill = GridBagConstraints.BOTH;
            pane.add(description, c);

            if (!order.getOrderStatus().equals(OrderStatus.APPLIED)) {
                BufferedImage image = ImageIO.read(new File(order.getImage()));

                int height = 300;
                int width = (int) (image.getWidth() * (height * 1.0 / image.getHeight()));
                BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics g = newImage.createGraphics();
                g.drawImage(image, 0, 0, width, height, null);
                g.dispose();

                JLabel picLabel = new JLabel(new ImageIcon(newImage));
                c.gridy = 3;

                pane.add(picLabel, c);
                pane.setPreferredSize(new Dimension(400, 450));
            }
            pane.setOpaque(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }
}
