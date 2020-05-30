package orders.view.panels;

import orders.controller.queries.UserController;
import orders.model.entities.User;
import orders.view.renderers.UserRenderer;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

public class UsersPanel extends JPanel {
    private Box bigBox;

    public UsersPanel(User user, Box parent) {

        DefaultListModel<User> users = new DefaultListModel<>();
        users.addAll(UserController.getAll());
        JList<User> userList = new JList<>(users);
        userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        userList.setLayoutOrientation(JList.VERTICAL);
        userList.setVisibleRowCount(-1);
        JScrollPane listScroller = new JScrollPane(userList);
        TitledBorder border = BorderFactory.createTitledBorder("Список пользователей");
        border.setTitleJustification(TitledBorder.CENTER);
        border.setTitleFont(new Font(border.getTitleFont().getFontName(), Font.BOLD, 20));
        listScroller.setBorder(border);
        listScroller.setPreferredSize(new Dimension(450, 550));
        userList.setCellRenderer(new UserRenderer());
        listScroller.setAlignmentX(Component.LEFT_ALIGNMENT);

        UsersPanel up = this;
        userList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                User selectedUser = users.get(userList.getSelectedIndex());
                if(selectedUser.getId()!=user.getId()) {
                    ProfilePanel profilePanel = new ProfilePanel(selectedUser, parent);
                    up.setVisible(false);
                    parent.add(profilePanel);
                }
            }
        });

        bigBox = Box.createVerticalBox();
        bigBox.add(listScroller);
        bigBox.setVisible(true);
        this.add(bigBox);
    }

}
