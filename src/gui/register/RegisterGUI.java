package gui.register;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Font;
import java.sql.ResultSet;

import gui.MainWindow.MainGUI;
import gui.login.LoginGUI;

import network.TClientProcessor;
import  org.jvnet.substance.SubstanceLookAndFeel;

import org.jvnet.substance.fonts.DefaultGnomeFontPolicy;
import org.jvnet.substance.utils.SubstanceConstants;
import  org.jvnet.substance.watermark.*;
import org.omg.CORBA.TCKind;

/**
 * Created by michelle on 2017/6/22.
 */
public class RegisterGUI {
    public JFrame frame;
    TClientProcessor tcp= TClientProcessor.getInstance();
    JTextField userName;
    JTextField emailAddr;
    JPasswordField password;
    JPasswordField pwdConfirm;
    JButton registerButton,loginButton, cancelButton;

    public void showRegister() {
        JPanel panel;
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, 600, 400);

        JLabel titleLogin, userLabel, pwdLabel1, pwdLabel2, emailLabel;

        titleLogin = new JLabel("注册");
        titleLogin.setBounds(panel.getSize().width / 2 - 40, 20, 300, 40);
        Font font=new Font("黑体",Font.BOLD,36);
        titleLogin.setFont(font);
        userLabel = new JLabel("用户名");
        userLabel.setBounds(160, 100, 75, 30);
        emailLabel = new JLabel("电子邮箱");
        emailLabel.setBounds(160, 140, 75, 30);
        pwdLabel1 = new JLabel("密码");
        pwdLabel1.setBounds(160, 180, 75, 30);
        pwdLabel2 = new JLabel("确认密码");
        pwdLabel2.setBounds(160, 220, 75, 30);

        userName = new JTextField();
        userName.setBounds(265, 100, 200, 25);
        emailAddr = new JTextField();
        emailAddr.setBounds(265, 140, 200, 25);
        password = new JPasswordField();
        password.setBounds(265, 180, 200, 25);
        pwdConfirm = new JPasswordField();
        pwdConfirm.setBounds(265, 220, 200, 25);
        registerButton = new JButton();
        registerButton.setText("注册");
        registerButton.setBounds(150, 260, 80, 30);
        loginButton = new JButton();
        loginButton.setText("已有账户? 登录");
        loginButton.setBounds(240, 260, 135, 30);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!password.getText().equals("")) {
                        if (password.getText().equals(pwdConfirm.getText())) {
                            tcp.register(userName.getText(),password.getText());
                            //应该这里将用户注册信息写入数据库。
                            //待添加
//                            MainGUI w = new MainGUI();//父窗口
//                            w.f.setVisible(true);
//                            frame.setVisible(false);
                        } else {
                            JOptionPane.showMessageDialog(frame, "两次密码不一致", "请重新输入",
                                    JOptionPane.WARNING_MESSAGE);
                            userName.setText("");
                            password.setText("");
                            pwdConfirm.setText("");
                        }
                    }else {
                        JOptionPane.showMessageDialog(frame, "用户名/密码不可为空！", "请重新输入",
                                JOptionPane.WARNING_MESSAGE);
                        userName.setText("");
                        password.setText("");
                        pwdConfirm.setText("");
                    }
                } catch (Exception e2) {
                    System.out.println(e2);
                }
            }
        });
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                  /*  LoginGUI login;
                    login = new LoginGUI();
                    login.jframe.setVisible(true);
                    frame.setVisible(false);
                    */
                    gui.login.LoginGUI w = LoginGUI.getInstance();//父窗口
                    w.jframe.setVisible(true);
                    //new MainGUI().setVisible(true);
                    frame.setVisible(false);
                } catch (Exception e2) {
                    System.out.println(e2);
                }
            }
        });

        cancelButton = new JButton();
        cancelButton.setText("取消");
        cancelButton.setBounds(385, 260, 80, 30);
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        panel.add(titleLogin);
        panel.add(userLabel);
        panel.add(userName);
        panel.add(emailLabel);
        panel.add(emailAddr);
        panel.add(password);
        panel.add(pwdConfirm);
        panel.add(pwdLabel1);
        panel.add(pwdLabel2);
        panel.add(loginButton);
        panel.add(cancelButton);
        panel.add(registerButton);
        panel.setOpaque(false);

        frame = new JFrame();
        frame.getRootPane().putClientProperty(SubstanceLookAndFeel.WATERMARK_PROPERTY,true);
        frame.setBounds(500, 200, 600, 400);
        frame.add(panel);
        frame.setResizable(false); //禁止最大化
        frame.setVisible(true);

    }
    static class MySkin extends org.jvnet.substance.skin.MistAquaSkin{
        public MySkin(){
            super();
            SubstanceImageWatermark imageWatermark = new SubstanceImageWatermark(this.getClass().getResourceAsStream("background.png"));
            imageWatermark.setOpacity(0.5f);
            imageWatermark.setKind(SubstanceConstants.ImageWatermarkKind.APP_CENTER);
            //imageWatermark.setKind(SubstanceConstants.ImageWatermarkKind.SCREEN_CENTER_SCALE);
            super.watermark = imageWatermark;
        }
    }


    public static void main(String args[]) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    SubstanceLookAndFeel.setSkin(new MySkin());
                    SubstanceLookAndFeel.setCurrentTheme(new org.jvnet.substance.theme.SubstanceSepiaTheme());
                    SubstanceLookAndFeel.setCurrentBorderPainter(new org.jvnet.substance.border.FlatBorderPainter());
                    SubstanceLookAndFeel.setCurrentButtonShaper(new org.jvnet.substance.button.StandardButtonShaper());
                    //SubstanceLookAndFeel.setCurrentGradientPainter(new org.jvnet.substance.painter.SimplisticGradientPainter());
                    SubstanceLookAndFeel.setCurrentTitlePainter(new org.jvnet.substance.title.FlatTitlePainter());
                    RegisterGUI test = new RegisterGUI();
                    test.showRegister();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

    public void CMD(String[] args){
        switch (Integer.valueOf(args[0])){
            case 0://register
                if (args[1].equals("s")){
                    JOptionPane.showMessageDialog(frame, "注册成功！", "注册",
                            JOptionPane.INFORMATION_MESSAGE);
                    LoginGUI.getInstance().jframe.setVisible(true);
                    frame.setVisible(false);
                }else {
                    JOptionPane.showMessageDialog(frame, "注册失败！", "注册",
                            JOptionPane.WARNING_MESSAGE);
                    userName.setText("");
                    password.setText("");
                    pwdConfirm.setText("");
                }
                break;
        }
    }

    private RegisterGUI(){
        SubstanceLookAndFeel.setSkin(new MySkin());
        SubstanceLookAndFeel.setCurrentTheme(new org.jvnet.substance.theme.SubstanceSepiaTheme());
        SubstanceLookAndFeel.setCurrentBorderPainter(new org.jvnet.substance.border.FlatBorderPainter());
        SubstanceLookAndFeel.setCurrentButtonShaper(new org.jvnet.substance.button.StandardButtonShaper());
        //SubstanceLookAndFeel.setCurrentGradientPainter(new org.jvnet.substance.painter.SimplisticGradientPainter());
        SubstanceLookAndFeel.setCurrentTitlePainter(new org.jvnet.substance.title.FlatTitlePainter());
        showRegister();
        frame.setVisible(false);
    }

    private static final RegisterGUI registerGUI=new RegisterGUI();

    public static RegisterGUI getInstance(){
        return registerGUI;
    }
}
