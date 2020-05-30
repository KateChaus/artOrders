package orders.view.windows;

import orders.controller.queries.UserController;
import orders.controller.utils.Hasher;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterWindow {

    private JFrame jFrame;

    private Box loginBox, passwordBox, confirmBox;
    private Box buttonsBox;
    private Box mainBox;

    private JLabel loginLabel, passwordLabel, confirmLabel;

    private JTextArea infoTextField;
    private JTextField loginTextField;
    private JPasswordField passwordField;
    private JPasswordField confirmField;

    private JButton registerButton;

    private JCheckBox isArtistCheckBox;

    public RegisterWindow() {
        jFrame = new JFrame();
        jFrame.setBounds(100, 100, 340, 500);
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jFrame.setResizable(false);
        jFrame.setTitle("Регистрация");

        Container pane = jFrame.getContentPane();
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

        loginBox = Box.createHorizontalBox();
        loginLabel = new JLabel("Логин: ");
        loginTextField = new JTextField(10);
        loginBox.add(loginLabel);
        loginBox.add(Box.createHorizontalStrut(6));
        loginBox.add(loginTextField);

        passwordBox = Box.createHorizontalBox();
        passwordLabel = new JLabel("Пароль: ");
        passwordField = new JPasswordField(10);
        passwordBox.add(passwordLabel);
        passwordBox.add(Box.createHorizontalStrut(6));
        passwordBox.add(passwordField);

        confirmBox = Box.createHorizontalBox();
        confirmLabel = new JLabel("Подтвердите пароль: ");
        confirmField = new JPasswordField(10);
        confirmBox.add(confirmLabel);
        confirmBox.add(Box.createHorizontalStrut(6));
        confirmBox.add(confirmField);

        infoTextField = new JTextArea();
        infoTextField.setWrapStyleWord(true);
        infoTextField.setLineWrap(true);
        infoTextField.setPreferredSize(new Dimension(infoTextField.getSize().width,100));
        infoTextField.setOpaque(false);
        TitledBorder border = BorderFactory.createTitledBorder("Информация");
        border.setTitleJustification(TitledBorder.CENTER);
        infoTextField.setBorder(border);

        isArtistCheckBox = new JCheckBox("Художник");

        buttonsBox = Box.createHorizontalBox();
        registerButton = new JButton("Регистрация");

        buttonsBox.add(registerButton);
        loginLabel.setPreferredSize(passwordLabel.getPreferredSize());

        mainBox = Box.createVerticalBox();
        mainBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainBox.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainBox.add(loginBox);
        mainBox.add(Box.createVerticalStrut(12));
        mainBox.add(passwordBox);
        mainBox.add(Box.createVerticalStrut(12));
        mainBox.add(confirmBox);
        mainBox.add(Box.createVerticalStrut(20));
        mainBox.add(infoTextField);
        mainBox.add(Box.createVerticalStrut(6));
        mainBox.add(isArtistCheckBox);
        mainBox.add(Box.createVerticalStrut(20));
        mainBox.add(buttonsBox);

        jFrame.setContentPane(mainBox);
        jFrame.pack();
        jFrame.setResizable(false);
        jFrame.setVisible(true);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String login = loginTextField.getText();
                String password = Hasher.getMd5(String.valueOf(passwordField.getPassword()));
                String confirmPassword = Hasher.getMd5(String.valueOf(confirmField.getPassword()));
                String info = infoTextField.getText();
                boolean isArtist = isArtistCheckBox.isSelected();
                if (password.equals(confirmPassword)) {
                    UserController.register(login, password, info, isArtist);
                    jFrame.dispose();
                }
                else{
                    JOptionPane.showMessageDialog(jFrame, "Пароли не совпадают");

                }
            }
        });
    }
}

