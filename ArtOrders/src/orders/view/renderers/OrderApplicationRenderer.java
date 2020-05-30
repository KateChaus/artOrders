package orders.view.renderers;

import orders.model.entities.OrderApplication;

import javax.swing.*;
import java.awt.*;

public class OrderApplicationRenderer extends JPanel implements ListCellRenderer {
    JPanel pane = new JPanel();

    public OrderApplicationRenderer() {
        this.setBorder(BorderFactory.createLineBorder(new Color(225, 225, 225)));
        add(pane);
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        OrderApplication app = (OrderApplication) value;
        if (isSelected) {
            this.setBackground(new Color(225, 225, 225));
        } else {
            this.setBackground(SystemColor.control);
        }
        pane.removeAll();
        pane.setPreferredSize(new Dimension(300, 150));

        JLabel artist = new JLabel("Художник: " + app.getArtist().getLogin());
        artist.setFont(new Font(artist.getFont().getFontName(), Font.BOLD, 15));

        JTextArea description = new JTextArea(app.getInfo());
        description.setBorder(BorderFactory.createTitledBorder("Описание"));
        description.setOpaque(false);

        JLabel type = new JLabel(app.getType().toString());
        JLabel style = new JLabel(app.getStyle().toString());
        JLabel cost = new JLabel("Стоимость: " + app.getCost());
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 3));
        panel.setOpaque(false);
        type.setHorizontalAlignment(JLabel.CENTER);
        style.setHorizontalAlignment(JLabel.CENTER);
        cost.setHorizontalAlignment(JLabel.CENTER);

        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panel.add(type);
        panel.add(style);
        panel.add(cost);

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

        pane.setOpaque(false);

        return this;
    }
}
