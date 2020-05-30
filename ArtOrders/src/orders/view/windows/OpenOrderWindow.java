package orders.view.windows;

import orders.controller.queries.OrderApplicationController;
import orders.model.entities.User;
import orders.model.enums.OrderStyle;
import orders.model.enums.OrderType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//ОТКРЫТИЕ ЗАЯВКИ



public class OpenOrderWindow {
    private JFrame jFrame;
    private JPanel mainBox;
    private JLabel typeLabel, styleLabel, descriptionLabel;
    private JRadioButton typeFree, typeDescription;
    private JRadioButton styleDigital, styleTraditional, style3D, styleAnimation, styleOther;

    private ButtonGroup type;
    private ButtonGroup style;

    private JLabel slotsLabel, costLabel;
    private JTextField slotsField, costField;
    private JTextArea descriptionField;
    private JButton openButton;

    public OpenOrderWindow(User user) {
        jFrame = new JFrame();
        jFrame.setBounds(100, 100, 340, 300);
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jFrame.setResizable(false);
        jFrame.setTitle("Открыть заявки");

        Container pane = jFrame.getContentPane();
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

        JPanel typePanel = new JPanel();
        typePanel.setLayout(new BoxLayout(typePanel, BoxLayout.Y_AXIS));
        typePanel.setBorder(BorderFactory.createTitledBorder("Тип"));
        typeFree = new JRadioButton("Свободный");
        typeFree.setSelected(true);
        typeDescription = new JRadioButton("По описанию");
        type = new ButtonGroup();
        type.add(typeFree);
        type.add(typeDescription);
        typePanel.add(typeFree);
        typePanel.add(typeDescription);

        JPanel stylePanel = new JPanel();
        stylePanel.setLayout(new BoxLayout(stylePanel, BoxLayout.Y_AXIS));
        stylePanel.setBorder(BorderFactory.createTitledBorder("Стиль"));
        styleDigital = new JRadioButton("Цифровой");
        styleDigital.setSelected(true);
        styleTraditional = new JRadioButton("Традиционный");
        style3D = new JRadioButton("3D");
        styleAnimation = new JRadioButton("Анимация");
        styleOther = new JRadioButton("Другое");
        style = new ButtonGroup();
        style.add(styleDigital);
        style.add(styleTraditional);
        style.add(style3D);
        style.add(styleAnimation);
        style.add(styleOther);
        stylePanel.add(styleDigital);
        stylePanel.add(styleTraditional);
        stylePanel.add(style3D);
        stylePanel.add(styleAnimation);
        stylePanel.add(styleOther);

        JPanel slotPanel = new JPanel();
        slotsLabel = new JLabel("Слотов");
        slotsField = new JTextField("10");
        slotPanel.add(slotsLabel);
        slotPanel.add(slotsField);

        JPanel costPanel = new JPanel();
        costLabel = new JLabel("Стоимость");
        costField = new JTextField("100");
        costPanel.add(costLabel);
        costPanel.add(costField);

        slotsLabel.setPreferredSize(costLabel.getPreferredSize());

        descriptionField = new JTextArea(5, 15);
        descriptionField.setBorder(BorderFactory.createTitledBorder("Описание"));
        descriptionField.setOpaque(false);
        descriptionField.setWrapStyleWord(true);

        openButton = new JButton("Открыть заявку");
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                OrderType orderType = OrderType.DESCRIPTION;
                OrderStyle orderStyle = OrderStyle.DIGITAL;
                int slotNumber = Integer.valueOf(slotsField.getText());
                String costValue = costField.getText();
                String info = descriptionField.getText();
                if (typeFree.isSelected()) {
                    orderType = OrderType.FREE;
                }

                if (styleTraditional.isSelected()) {
                    orderStyle = OrderStyle.TRADITIONAL;
                }
                if (style3D.isSelected()) {
                    orderStyle = OrderStyle.THREE_DIMENSIONAL;
                }
                if (styleAnimation.isSelected()) {
                    orderStyle = OrderStyle.ANIMATION;
                }
                if (styleOther.isSelected()) {
                    orderStyle = OrderStyle.OTHER;
                }
                OrderApplicationController.placeOrder(orderType, orderStyle, info, costValue, slotNumber, user);
            }
        });

        mainBox = new JPanel();
        GridBagLayout gridBagLayout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        c.weightx=1;
        c.weighty=1;
        c.gridx=0;
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.BOTH;
        gridBagLayout.setConstraints(mainBox, c);
        mainBox.setLayout(gridBagLayout);
        mainBox.add(typePanel,c);
        c.gridy = 1;
        mainBox.add(stylePanel,c);
        c.gridy = 2;
        c.fill = GridBagConstraints.VERTICAL;
        c.anchor = GridBagConstraints.WEST;
        mainBox.add(slotPanel,c);
        c.gridy = 3;
        mainBox.add(costPanel,c);
        c.fill = GridBagConstraints.BOTH;
        c.gridy = 4;
        mainBox.add(descriptionField,c);
        c.gridy = 5;
        mainBox.add(openButton,c);
        mainBox.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        jFrame.setContentPane(mainBox);
        jFrame.pack();
        jFrame.setResizable(false);
        jFrame.setVisible(true);
    }
}
