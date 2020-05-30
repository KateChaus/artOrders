package orders.view.windows;

import orders.controller.queries.UserController;
import orders.controller.utils.Hasher;
import orders.model.entities.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*ОКНО ЛОГИНА*/

public class LoginWindow {

    private JFrame jFrame;

    private Box loginBox, passwordBox, buttonsBox;
    private Box mainBox;

    private JLabel loginLabel, passwordLabel;

    private JTextField loginTextField;
    private JPasswordField passwordField;

    private JButton loginButton;
    private JButton registerButton;


    public LoginWindow() {
        jFrame = new JFrame();
        jFrame.setBounds(100, 100, 340, 300);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setResizable(false);
        jFrame.setTitle("Вход");

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

        buttonsBox = Box.createHorizontalBox();
        loginButton = new JButton("Войти");
        registerButton = new JButton("Регистрация");
        buttonsBox.add(Box.createHorizontalGlue());
        buttonsBox.add(loginButton);
        buttonsBox.add(Box.createHorizontalStrut(20));
        buttonsBox.add(registerButton);

        loginLabel.setPreferredSize(passwordLabel.getPreferredSize());

        mainBox = Box.createVerticalBox();
        mainBox.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainBox.add(loginBox);
        mainBox.add(Box.createVerticalStrut(12));
        mainBox.add(passwordBox);
        mainBox.add(Box.createVerticalStrut(12));
        mainBox.add(buttonsBox);
        jFrame.setContentPane(mainBox);
        jFrame.pack();
        jFrame.setResizable(false);
        jFrame.setVisible(true);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                String login = loginTextField.getText();
                String password = Hasher.getMd5(String.valueOf(passwordField.getPassword()));
                User user = UserController.login(login, password);
                if (user != null) {
                    MainWindow mainWindow = new MainWindow(user);
                    jFrame.dispose();
                } else {
                    JOptionPane.showMessageDialog(jFrame, "Неверный логин или пароль ");
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                RegisterWindow registerWindow = new RegisterWindow();
            }
        });
    }
}
