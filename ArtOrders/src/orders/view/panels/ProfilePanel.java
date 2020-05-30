package orders.view.panels;

import orders.controller.queries.ArtOrderController;
import orders.controller.queries.GalleryController;
import orders.model.entities.ArtOrder;
import orders.model.entities.Gallery;
import orders.model.entities.User;
import orders.view.renderers.ImageRenderer;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

//ПАНЕЛЬ ПРОФИЛЯ ПОЛЬЗОВАТЕЛЯ

public class ProfilePanel extends JPanel {

    private Box avatarBox, nameBox;
    private Box avatarandnameBox, infoBox, galleryBox;
    private Box bigBox;
    private JLabel info;

    public ProfilePanel(User user, Box parent) {
        JLabel avatarPicture = new JLabel("No avatar");
        try {
            BufferedImage avatar = ImageIO.read(new File(user.getAvatar()));
            int height = 150;
            int width =(int) (avatar.getWidth() * (height*1.0/avatar.getHeight()));
            BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = newImage.createGraphics();
            g.drawImage(avatar, 0, 0, width, height, null);
            g.dispose();
            avatarPicture = new JLabel(new ImageIcon(newImage));
        } catch (IOException e) {
            System.out.println("No avatar found, using default");
            }


        JLabel usernameLabel = new JLabel(user.getLogin());
        usernameLabel.setFont(new Font(usernameLabel.getFont().getFontName(), Font.BOLD, 20));

        JTextArea infoArea = new JTextArea();
        infoArea.setWrapStyleWord(true);
        infoArea.setLineWrap(true);
        infoArea.setText(user.getInfo());
        infoArea.setEditable(false);
        infoArea.setOpaque(false);
        infoArea.setAlignmentX(JTextArea.CENTER_ALIGNMENT);

        galleryBox = Box.createHorizontalBox();
        galleryBox.setBorder(new EmptyBorder(10, 10, 10, 10));
        if (user.isArtist()) {
            galleryBox.add(new JLabel("★ "));
            ArrayList<Gallery> galleries = GalleryController.getGalleries(user);
            for (Gallery g : galleries) {
                JLabel siteLink = new JLabel(g.getSite());
                galleryBox.add(siteLink);
                siteLink.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent mouseEvent) {
                        try {
                            Desktop.getDesktop().browse(new URL(g.getLink()).toURI());
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void mousePressed(MouseEvent mouseEvent) {}
                    @Override
                    public void mouseReleased(MouseEvent mouseEvent) {}
                    @Override
                    public void mouseEntered(MouseEvent mouseEvent) {}
                    @Override
                    public void mouseExited(MouseEvent mouseEvent) {}
                });
                galleryBox.add(new JLabel(" ★ "));
            }
        }

        JPanel box = new JPanel();
        GridBagLayout gridBagLayout= new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1.0;
        c.weighty = 0;
        c.anchor = GridBagConstraints.CENTER;

        gridBagLayout.setConstraints(box,c);
        box.setLayout(gridBagLayout);
        c.gridy = 0;
        box.add(usernameLabel,c);
        c.gridy = 1;
        box.add(galleryBox,c);
        c.gridy = 2;
        c.weighty = 1;
        c.fill=GridBagConstraints.BOTH;
        box.add(infoArea,c);

        infoBox = Box.createHorizontalBox();
        infoBox.add(avatarPicture);
        infoBox.add(Box.createHorizontalStrut(20));
        infoBox.add(box);

        DefaultListModel<ArtOrder> commissioned = new DefaultListModel<>();
        commissioned.addAll(ArtOrderController.getByCustomer(user));

        JList commissionedList = new JList(commissioned);
        commissionedList.setLayoutOrientation(JList.VERTICAL);
        commissionedList.setVisibleRowCount(-1);
        JScrollPane listScroller = new JScrollPane(commissionedList);
        TitledBorder border = BorderFactory.createTitledBorder("Прошлые заказы");
        border.setTitleJustification(TitledBorder.CENTER);
        border.setTitleFont(new Font(border.getTitleFont().getFontName(), Font.BOLD, 15));
        listScroller.setBorder(border);
        listScroller.setPreferredSize(new Dimension(450, 370));
        listScroller.getViewport().getView().setBackground(SystemColor.control);
        commissionedList.setCellRenderer(new ImageRenderer());



        bigBox = Box.createVerticalBox();
        bigBox.add(Box.createVerticalStrut(20));
        bigBox.add(infoBox);
        bigBox.add(Box.createVerticalStrut(20));
        bigBox.add(listScroller);
        bigBox.setVisible(true);
        this.add(bigBox);
    }
}
