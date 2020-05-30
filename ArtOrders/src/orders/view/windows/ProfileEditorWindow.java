package orders.view.windows;

import orders.controller.queries.GalleryController;
import orders.controller.queries.UserController;
import orders.controller.utils.Hasher;
import orders.model.entities.Gallery;
import orders.model.entities.User;
import orders.view.renderers.GalleryRenderer;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

//ОКНО РЕДАКТИРОВАНИЯ ПРОФИЛЯ//

public class ProfileEditorWindow {

    private JFrame jFrame;
    private JLabel usernameLabel;
    private JCheckBox isArtistCheckBox;
    private JLabel infoLabel;
    private JTextArea infoTextField;
    private JLabel oldPasswordLabel, newPasswordLabel, confirmPasswordLabel;
    private JPasswordField oldPasswordField, newPasswordField, confirmPasswordField;
    private JButton editButton;
    private Box oldPasswordBox, newPasswordBox, confirmPasswordBox;
    private Box mainBox;

    public ProfileEditorWindow(User user, MainWindow mainWindow) {
        jFrame = new JFrame();
        jFrame.setBounds(100, 100, 340, 500);
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jFrame.setResizable(false);
        jFrame.setTitle("Редактировать профиль");

        Container pane = jFrame.getContentPane();
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

        usernameLabel = new JLabel("  " + user.getLogin());

        JLabel picLabel = new JLabel("No avatar");
        try {
            BufferedImage avatar = ImageIO.read(new File(user.getAvatar()));
            int height = 80;
            int width = (int) (avatar.getWidth() * (height * 1.0 / avatar.getHeight()));
            BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = newImage.createGraphics();
            g.drawImage(avatar, 0, 0, width, height, null);
            g.dispose();

            picLabel = new JLabel(new ImageIcon(newImage));
        } catch (IOException e) {
            System.out.println("No avatar found, using default");
        }


        picLabel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {

                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "images", "png", "jpg");
                fileChooser.setFileFilter(filter);
                int returnVal = fileChooser.showSaveDialog(jFrame);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    String fileName = fileChooser.getSelectedFile().getPath();
                    File file = new File(fileName);

                    String extension = "";
                    int i = fileName.lastIndexOf('.');
                    extension = fileName.substring(i + 1);

                    Path originalPath = file.toPath();
                    String copiedPath = "images/avatars/" + user.getId() + "." + extension;
                    Path copied = Paths.get(copiedPath);
                    try {
                        Files.copy(originalPath, copied, StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    user.setAvatar(copiedPath);
                    UserController.setAvatar(user);
                    mainWindow.refresh();
                }
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
            }
        });

        isArtistCheckBox = new JCheckBox("Художник");
        if (user.isArtist()) {
            isArtistCheckBox.setSelected(true);
        }

        infoTextField = new JTextArea();
        infoTextField.setLineWrap(true);
        infoTextField.setWrapStyleWord(true);
        infoTextField.setText(user.getInfo());
        TitledBorder border = BorderFactory.createTitledBorder("Информация");
        border.setTitleJustification(TitledBorder.CENTER);
        infoTextField.setBorder(border);
        infoTextField.setOpaque(false);
        infoTextField.setPreferredSize(new Dimension(infoTextField.getPreferredSize().width, 100));

        DefaultListModel<Gallery> galleries = new DefaultListModel<>();
        galleries.addAll(GalleryController.getGalleries(user));

        JList galleryList = new JList(galleries);
        galleryList.setLayoutOrientation(JList.VERTICAL);
        galleryList.setVisibleRowCount(-1);
        JScrollPane listScroller = new JScrollPane(galleryList);
        listScroller.setPreferredSize(new Dimension(200, 100));
        galleryList.setCellRenderer(new GalleryRenderer());
        listScroller.getViewport().getView().setBackground(SystemColor.control);

        Box textFieldBox = Box.createHorizontalBox();
        JTextField siteField = new JTextField("Сайт");
        JTextField linkField = new JTextField("http:/");
        textFieldBox.add(siteField);
        textFieldBox.add(linkField);

        Box buttonsBox = Box.createHorizontalBox();
        JButton add = new JButton("Добавить");
        JButton delete = new JButton("Удалить");
        buttonsBox.add(add);
        buttonsBox.add(delete);

        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String site = siteField.getText();
                String link = linkField.getText();
                GalleryController.addGallery(site, link, user);
                galleries.removeAllElements();
                galleries.addAll(GalleryController.getGalleries(user));
                mainWindow.refresh();
            }
        });

        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int index = galleryList.getSelectedIndex();
                GalleryController.remove(galleries.get(index));
                galleries.remove(index);
                mainWindow.refresh();
            }
        });

        oldPasswordBox = Box.createHorizontalBox();
        oldPasswordLabel = new JLabel("Пароль: ");
        oldPasswordField = new JPasswordField(10);
        oldPasswordBox.add(oldPasswordLabel);
        oldPasswordBox.add(Box.createHorizontalStrut(6));
        oldPasswordBox.add(oldPasswordField);

        newPasswordBox = Box.createHorizontalBox();
        newPasswordLabel = new JLabel("Новый пароль: ");
        newPasswordField = new JPasswordField(10);
        newPasswordBox.add(newPasswordLabel);
        newPasswordBox.add(Box.createHorizontalStrut(6));
        newPasswordBox.add(newPasswordField);

        confirmPasswordBox = Box.createHorizontalBox();
        confirmPasswordLabel = new JLabel("Подтвердите пароль: ");
        confirmPasswordField = new JPasswordField(10);
        confirmPasswordBox.add(confirmPasswordLabel);
        confirmPasswordBox.add(Box.createHorizontalStrut(6));
        confirmPasswordBox.add(confirmPasswordField);

        oldPasswordLabel.setPreferredSize(confirmPasswordLabel.getPreferredSize());
        newPasswordLabel.setPreferredSize(confirmPasswordLabel.getPreferredSize());

        editButton = new JButton("Редактировать");

        mainBox = Box.createVerticalBox();
        mainBox.setBorder(new EmptyBorder(20, 20, 20, 20));
        Box box = Box.createHorizontalBox();
        box.add(picLabel);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        panel.add(usernameLabel);
        panel.add(isArtistCheckBox);
        box.add(panel);
        mainBox.add(box);
        mainBox.add(Box.createVerticalStrut(12));
        mainBox.add(infoTextField);
        mainBox.add(Box.createVerticalStrut(12));
        mainBox.add(listScroller);
        mainBox.add(textFieldBox);
        mainBox.add(buttonsBox);
        mainBox.add(Box.createVerticalStrut(12));
        mainBox.add(oldPasswordBox);
        mainBox.add(Box.createVerticalStrut(12));
        mainBox.add(newPasswordBox);
        mainBox.add(Box.createVerticalStrut(12));
        mainBox.add(confirmPasswordBox);
        mainBox.add(Box.createVerticalStrut(12));
        mainBox.add(editButton);

        jFrame.setContentPane(mainBox);
        jFrame.pack();
        jFrame.setResizable(false);
        jFrame.setVisible(true);

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                user.setArtist(isArtistCheckBox.isSelected());
                user.setInfo(infoTextField.getText());
                UserController.editProfile(user);
                if (!String.valueOf(newPasswordField.getPassword()).equals("")) {
                    String pass1 = Hasher.getMd5(String.valueOf(newPasswordField.getPassword()));
                    String pass2 = Hasher.getMd5(String.valueOf(confirmPasswordField.getPassword()));
                    System.out.println(pass1);
                    System.out.println(pass2);
                    if (pass1.equals(pass2)) {
                        if (user.getPassword().equals(Hasher.getMd5(String.valueOf(oldPasswordField.getPassword())))) {
                            user.setPassword(pass1);
                            UserController.changePassword(user);
                            JOptionPane.showMessageDialog(jFrame, "Пароль успешно изменён");
                        } else {
                            JOptionPane.showMessageDialog(jFrame, "Неверный пароль");
                        }
                    } else {
                        JOptionPane.showMessageDialog(jFrame, "Пароли не совпадают");
                    }
                }
                mainWindow.refresh();
            }
        });

    }
}
