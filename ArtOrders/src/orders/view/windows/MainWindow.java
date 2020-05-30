package orders.view.windows;

import orders.model.entities.User;
import orders.view.panels.OrdersPanel;
import orders.view.panels.ProfilePanel;
import orders.view.panels.UsersPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/*ГЛАВНОЕ ОКНО ПРОГРАММЫ*/

public class MainWindow {

    private JFrame jFrame;

    private JMenuBar menu;
    private Box mainBox;
    private Box contentBox;
    private ProfilePanel profilePanel;
    private OrdersPanel ordersPanel;
    private UsersPanel usersPanel;
    private User user;

    public MainWindow(User user) {
        jFrame = new JFrame();
        jFrame.setBounds(100, 100, 340, 300);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setResizable(false);
        this.user = user;
        jFrame.setTitle(user.getLogin());
        Container pane = jFrame.getContentPane();
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        refresh();
    }

    public void refresh() {
        menu = new JMenuBar();
        menu.setMaximumSize(new Dimension(500, 50));
        JMenu homeMenu = new JMenu();
        homeMenu.setIcon(new ImageIcon("images\\icons\\home.png"));
        menu.add(Box.createHorizontalStrut(30));
        menu.add(homeMenu);

        JMenu profileMenu = new JMenu("Профиль");
        profileMenu.setIcon(new ImageIcon("images\\icons\\user.png"));
        menu.add(Box.createHorizontalStrut(20));
        menu.add(profileMenu);

        JMenuItem editProfile = new JMenuItem("Редактировать профиль");
        profileMenu.add(editProfile);
        JMenuItem logout = new JMenuItem("Выход"); //выкинет на страницу логина
        profileMenu.add(logout);
        profileMenu.addSeparator();
        JMenuItem exit = new JMenuItem("Завершить работу"); //закроет программу вообще
        profileMenu.add(exit);

        JMenu usersMenu = new JMenu("Пользователи");
        usersMenu.setIcon(new ImageIcon("images\\icons\\users.png"));
        menu.add(Box.createHorizontalStrut(20));
        menu.add(usersMenu);

        JMenu ordersMenu = new JMenu("Заказы");
        ordersMenu.setIcon(new ImageIcon("images\\icons\\orders.png"));
        menu.add(Box.createHorizontalStrut(20));
        menu.add(ordersMenu);
        JMenuItem orderStatus = new JMenuItem("Состояние заказов");
        ordersMenu.add(orderStatus);
        JMenuItem openOrder = new JMenuItem("Открыть заявки");
        if (user.isArtist()) {
            ordersMenu.add(openOrder);
        }
        JMenuItem search = new JMenuItem("Найти заявки");
        ordersMenu.add(search);

        menu.add(Box.createHorizontalStrut(30));

        contentBox = Box.createVerticalBox();

        ProfilePanel profilePanel = new ProfilePanel(user,contentBox);
        OrdersPanel ordersPanel = new OrdersPanel(user);
        UsersPanel usersPanel = new UsersPanel(user, contentBox);
        profilePanel.setVisible(true);
        ordersPanel.setVisible(false);
        usersPanel.setVisible(false);

        homeMenu.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                refresh();
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

        MainWindow mainWindow = this;
        editProfile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ProfileEditorWindow profileEditorWindow = new ProfileEditorWindow(user, mainWindow);
            }
        });

        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                LoginWindow loginWindow = new LoginWindow();
                jFrame.dispose();
            }
        });

        usersMenu.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                contentBox.removeAll();
                contentBox.add(profilePanel);
                contentBox.add(ordersPanel);
                contentBox.add(usersPanel);
                profilePanel.setVisible(false);
                ordersPanel.setVisible(false);
                usersPanel.setVisible(true);
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

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });

        orderStatus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                contentBox.removeAll();
                contentBox.add(profilePanel);
                contentBox.add(ordersPanel);
                contentBox.add(usersPanel);
                profilePanel.setVisible(false);
                ordersPanel.setVisible(true);
                usersPanel.setVisible(false);
            }
        });

        openOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                OpenOrderWindow openOrderWindow = new OpenOrderWindow(user);
            }
        });

        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                SearchWindow searchWindow = new SearchWindow(user);
            }
        });

        mainBox = Box.createVerticalBox();
        mainBox.add(menu);
        contentBox.add(profilePanel);
        contentBox.add(usersPanel);
        contentBox.add(ordersPanel);
        mainBox.add(contentBox);

        jFrame.setContentPane(mainBox);
        jFrame.pack();
        jFrame.setResizable(false);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }
}
