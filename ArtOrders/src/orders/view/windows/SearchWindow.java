package orders.view.windows;

import orders.controller.queries.ArtOrderController;
import orders.controller.queries.OrderApplicationController;
import orders.model.entities.OrderApplication;
import orders.model.entities.User;
import orders.view.renderers.OrderApplicationRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SearchWindow {

    private JFrame jFrame;

    private Box mainBox;

    private JPanel filterPanel, resultPanel;

    private JCheckBox typeFree, typeDescription;
    private JCheckBox styleDigital, styleTraditional, style3D, styleAnimation, styleOther;

    private JLabel costLabel;
    private JTextField costField;
    private JButton searchButton, applyButton;

    public SearchWindow(User user) {
        jFrame = new JFrame();
        jFrame.setBounds(100, 100, 340, 300);
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jFrame.setResizable(false);
        jFrame.setTitle("Поиск");

        Container pane = jFrame.getContentPane();
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

        filterPanel = new JPanel();
        JPanel typePanel = new JPanel();
        typePanel.setLayout(new BoxLayout(typePanel, BoxLayout.Y_AXIS));
        typePanel.setBorder(BorderFactory.createTitledBorder("Тип"));
        typeFree = new JCheckBox("Свободный");
        typeDescription = new JCheckBox("По описанию");
        typePanel.add(typeFree);
        typePanel.add(typeDescription);

        JPanel stylePanel = new JPanel();
        stylePanel.setLayout(new BoxLayout(stylePanel, BoxLayout.Y_AXIS));
        stylePanel.setBorder(BorderFactory.createTitledBorder("Стиль"));
        styleDigital = new JCheckBox("Цифровой");
        styleTraditional = new JCheckBox("Традиционный");
        style3D = new JCheckBox("3D");
        styleAnimation = new JCheckBox("Анимация");
        styleOther = new JCheckBox("Другое");
        stylePanel.add(styleDigital);
        stylePanel.add(styleTraditional);
        stylePanel.add(style3D);
        stylePanel.add(styleAnimation);
        stylePanel.add(styleOther);

        JPanel costPanel = new JPanel();
        costLabel = new JLabel("Стоимость до");
        costField = new JTextField(3);
        costPanel.add(costLabel);
        costPanel.add(costField);

        searchButton = new JButton("Поиск");

        GridBagLayout gridBagLayout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.weighty = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.HORIZONTAL;
        gridBagLayout.setConstraints(filterPanel, c);

        filterPanel.setLayout(gridBagLayout);
        filterPanel.add(typePanel, c);
        c.gridy = 1;
        filterPanel.add(stylePanel, c);
        c.gridy = 2;
        filterPanel.add(costPanel, c);
        c.gridy = 3;
        filterPanel.add(searchButton, c);
        filterPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        resultPanel = new JPanel();
        resultPanel.setLayout(gridBagLayout);
        resultPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        ArrayList<OrderApplication> orderAppsFromDB = OrderApplicationController.getAll();

        DefaultListModel<OrderApplication> orders = new DefaultListModel<>();
        orders.addAll(orderAppsFromDB);

        JList<OrderApplication> orderList = new JList<>(orders);
        orderList.setLayoutOrientation(JList.VERTICAL);
        orderList.setVisibleRowCount(-1);
        orderList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScroller = new JScrollPane(orderList);
        listScroller.setPreferredSize(new Dimension(350, 250));
        orderList.setCellRenderer(new OrderApplicationRenderer());

        c.gridy = 0;
        resultPanel.add(listScroller, c);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                boolean[] values = {typeFree.isSelected(), typeDescription.isSelected(), styleDigital.isSelected(), styleTraditional.isSelected(), style3D.isSelected(), styleAnimation.isSelected(), styleOther.isSelected()};
                int maxCost = Integer.MAX_VALUE;
                if (!costField.getText().equals("")) {
                    maxCost = Integer.parseInt(costField.getText());
                }
                ArrayList<OrderApplication> list = OrderApplicationController.formQuery(values, maxCost);
                orders.removeAllElements();
                orders.addAll(list);
            }
        });

        applyButton = new JButton("Откликнуться");
        c.anchor = GridBagConstraints.WEST;
        c.gridy = 1;
        resultPanel.add(applyButton, c);

        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int index = orderList.getSelectedIndex();
                if (index != -1) {
                    ArtOrderController.apply(user.getId(), orders.get(index).getArtist().getId());
                }
            }
        });

        mainBox = Box.createHorizontalBox();
        mainBox.add(filterPanel);
        mainBox.add(resultPanel);
        jFrame.setContentPane(mainBox);
        jFrame.pack();
        jFrame.setResizable(false);
        jFrame.setVisible(true);
    }

}

