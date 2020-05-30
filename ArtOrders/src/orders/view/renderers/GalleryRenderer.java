package orders.view.renderers;

import orders.model.entities.Gallery;

import javax.swing.*;
import java.awt.*;

public class GalleryRenderer extends JPanel implements ListCellRenderer {
    JPanel pane = new JPanel();

    public GalleryRenderer() {
        add(pane);
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Gallery gallery = (Gallery) value;

        pane.removeAll();
        pane.setLayout(new BoxLayout(pane, BoxLayout.X_AXIS));
        if(isSelected){
            this.setBackground(new Color(225,225,225));
        }
        else {
            this.setBackground(SystemColor.control);
        }
        JLabel galleryLabel = new JLabel(gallery.getSite() + ":" + gallery.getLink());

        pane.add(galleryLabel);
        pane.setOpaque(false);
        return this;
    }
}

