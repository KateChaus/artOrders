package orders.view.panels;

import orders.controller.queries.ArtOrderController;
import orders.model.entities.ArtOrder;
import orders.model.entities.User;
import orders.view.renderers.OrderRenderer;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;


public class OrdersPanel extends JPanel {

    private JTabbedPane tabs;
    JPanel outbox, processed, completedFor;
    JPanel inbox, accepted, completedBy;

    private Box mainBox;

    public OrdersPanel(User user) {
        tabs = new JTabbedPane();
        inbox = new JPanel();
        outbox = new JPanel();
        processed = new JPanel();
        accepted = new JPanel();
        completedBy = new JPanel();
        completedFor = new JPanel();

        tabs.addTab("Исходящие", outbox);
        renderOut(user);

        tabs.addTab("В процессе", processed);
        renderProcess(user);

        tabs.addTab("Завершены", completedFor);
        renderFinished(user);

        if (user.isArtist()) {
            tabs.setFont(new Font(tabs.getFont().getFontName(), Font.PLAIN, 10));
            tabs.addTab("Входящие", inbox);
            System.out.println("HERE");
            renderIn(user);
            tabs.addTab("Приняты", accepted);
            renderAccepted(user);
            tabs.addTab("Исполнены", completedBy);
            renderDrawn(user);
        }

        tabs.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        this.add(tabs);

    }

    private void renderOut(User user){
        outbox.removeAll();
        ArrayList<ArtOrder> ordersFromDB = ArtOrderController.outOrders(user);

        DefaultListModel<ArtOrder> orders = new DefaultListModel<>();
        orders.addAll(ordersFromDB);

        JList<ArtOrder> orderList = new JList<>(orders);
        orderList.setLayoutOrientation(JList.VERTICAL);
        orderList.setVisibleRowCount(-1);
        orderList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScroller = new JScrollPane(orderList);
        listScroller.setPreferredSize(new Dimension(450, 500));
        orderList.setCellRenderer(new OrderRenderer(false));
        listScroller.getViewport().getView().setBackground(SystemColor.control);

        JButton remove = new JButton("Отменить");
        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int index = orderList.getSelectedIndex();
                ArtOrderController.remove(orders.get(index));
                orders.remove(index);
            }
        });

        Box bigBox = Box.createVerticalBox();
        bigBox.add(listScroller);
        bigBox.add(remove);
        bigBox.setVisible(true);
        outbox.add(bigBox);
    }

    private void renderProcess(User user){
        processed.removeAll();
        ArrayList<ArtOrder> ordersFromDB = ArtOrderController.processOrders(user);
        DefaultListModel<ArtOrder> orders = new DefaultListModel<>();
        orders.addAll(ordersFromDB);

        JList<ArtOrder> orderList = new JList<>(orders);
        orderList.setLayoutOrientation(JList.VERTICAL);
        orderList.setVisibleRowCount(-1);
        orderList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScroller = new JScrollPane(orderList);
        listScroller.setPreferredSize(new Dimension(450, 500));
        orderList.setCellRenderer(new OrderRenderer(false));
        listScroller.getViewport().getView().setBackground(SystemColor.control);

        JButton finish = new JButton("Завершить");
        finish.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int index = orderList.getSelectedIndex();
                ArtOrderController.finish(orders.get(index));
                orders.remove(index);
                renderFinished(user);
            }
        });

        Box bigBox = Box.createVerticalBox();
        bigBox.add(listScroller);
        bigBox.add(finish);
        bigBox.setVisible(true);
        processed.add(bigBox);

    }

    private void renderFinished(User user){
        completedFor.removeAll();
        ArrayList<ArtOrder>  ordersFromDB = ArtOrderController.completedOrders(user);
        DefaultListModel<ArtOrder> orders = new DefaultListModel<>();
        orders.addAll(ordersFromDB);

        JList<ArtOrder> orderList = new JList<>(orders);
        orderList.setLayoutOrientation(JList.VERTICAL);
        orderList.setVisibleRowCount(-1);
        orderList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScroller = new JScrollPane(orderList);
        listScroller.setPreferredSize(new Dimension(450, 500));
        orderList.setCellRenderer(new OrderRenderer(false));
        listScroller.getViewport().getView().setBackground(SystemColor.control);

        Box bigBox = Box.createVerticalBox();
        bigBox.add(listScroller);
        bigBox.setVisible(true);
        completedFor.add(bigBox);
    }


    private void renderIn(User user){
        inbox.removeAll();
        ArrayList<ArtOrder> ordersFromDB = ArtOrderController.inOrders(user);

        DefaultListModel<ArtOrder> orders = new DefaultListModel<>();
        orders.addAll(ordersFromDB);

        JList<ArtOrder> orderList = new JList<>(orders);
        orderList.setLayoutOrientation(JList.VERTICAL);
        orderList.setVisibleRowCount(-1);
        orderList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScroller = new JScrollPane(orderList);
        listScroller.setPreferredSize(new Dimension(450, 500));
        orderList.setCellRenderer(new OrderRenderer(true));
        listScroller.getViewport().getView().setBackground(SystemColor.control);

        JButton decline = new JButton("Отклонить");
        decline.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int index = orderList.getSelectedIndex();
                ArtOrderController.remove(orders.get(index));
                orders.remove(index);
            }
        });

        JButton accept = new JButton("Принять");
        accept.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int index = orderList.getSelectedIndex();
                ArtOrderController.accept(orders.get(index));
                orders.remove(index);
                renderAccepted(user);
            }
        });

        Box bigBox = Box.createVerticalBox();
        bigBox.add(listScroller);
        Box miniBox = Box.createHorizontalBox();
        miniBox.add(decline);
        miniBox.add(accept);
        bigBox.add(miniBox);
        bigBox.setVisible(true);
        inbox.add(bigBox);
    }

    private void renderAccepted(User user){
        accepted.removeAll();
        ArrayList<ArtOrder> ordersFromDB = ArtOrderController.acceptedOrders(user);
        DefaultListModel<ArtOrder> orders = new DefaultListModel<>();
        orders.addAll(ordersFromDB);

        JList<ArtOrder> orderList = new JList<>(orders);
        orderList.setLayoutOrientation(JList.VERTICAL);
        orderList.setVisibleRowCount(-1);
        orderList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScroller = new JScrollPane(orderList);
        listScroller.setPreferredSize(new Dimension(450, 500));
        orderList.setCellRenderer(new OrderRenderer(true));
        listScroller.getViewport().getView().setBackground(SystemColor.control);

        JButton upload = new JButton("Загрузить изображение");
        upload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int index = orderList.getSelectedIndex();

                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "images", "png","jpg");
                fileChooser.setFileFilter(filter);
                int returnVal = fileChooser.showSaveDialog(upload);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    String fileName = fileChooser.getSelectedFile().getPath();
                    File file = new File(fileName);

                    String extension = "";
                    int i = fileName.lastIndexOf('.');
                    extension = fileName.substring(i+1);

                    Path originalPath = file.toPath();
                    String copiedPath = "images/arts/"+orders.get(index).getId()+"."+extension;
                    Path copied = Paths.get(copiedPath);
                    try {
                        Files.copy(originalPath, copied, StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    orders.get(index).setImage(copiedPath);
                    ArtOrderController.changeImage(orders.get(index));
                }
            }
        });

        Box bigBox = Box.createVerticalBox();
        bigBox.add(listScroller);
        bigBox.add(upload);
        bigBox.setVisible(true);
        accepted.add(bigBox);

    }

    private void renderDrawn(User user){
        completedBy.removeAll();
        ArrayList<ArtOrder>  ordersFromDB = ArtOrderController.drawnOrders(user);
        DefaultListModel<ArtOrder> orders = new DefaultListModel<>();
        orders.addAll(ordersFromDB);

        JList<ArtOrder> orderList = new JList<>(orders);
        orderList.setLayoutOrientation(JList.VERTICAL);
        orderList.setVisibleRowCount(-1);
        orderList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScroller = new JScrollPane(orderList);
        listScroller.setPreferredSize(new Dimension(450, 500));
        orderList.setCellRenderer(new OrderRenderer(true));
        listScroller.getViewport().getView().setBackground(SystemColor.control);

        Box bigBox = Box.createVerticalBox();
        bigBox.add(listScroller);
        bigBox.setVisible(true);
        completedBy.add(bigBox);
    }
}
