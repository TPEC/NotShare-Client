package gui.login;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import network.TClientProcessor;
import  org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.utils.SubstanceConstants;
import  org.jvnet.substance.watermark.*;
import gui.MainWindow.MainGUI;

/**
 * Created by michelle on 2017/6/21.
 */
public class LoginGUI  extends JFrame {
    public JFrame jframe;
    JPanel panel;
    JLabel titleLogin, userLabel, pwdLabel;
    JTextField userName;
    JPasswordField password;
    JButton loginButton, cancelButton;
    Font font=new Font("黑体",Font.BOLD,36);

    public LoginGUI(){
        panel = new JPanel();
        titleLogin = new JLabel("登录");
        userLabel = new JLabel("用户名");
        pwdLabel = new JLabel("密码");
        userName = new JTextField();
        password = new JPasswordField();
        loginButton = new JButton();
        cancelButton = new JButton();

        showLogin();
    }
    public void showLogin() {

        panel.setLayout(null);
        panel.setBounds(0, 0, 490, 360);

        titleLogin.setBounds(panel.getSize().width / 2 - 40, 15, 300, 40);
        titleLogin.setFont(font);
        userLabel.setBounds(100, 100, 75, 30);
        pwdLabel.setBounds(100, 140, 75, 30);

        userName.setBounds(185, 100, 200, 25);
        password.setBounds(185, 140, 200, 25);

        loginButton.setText("登录");
        loginButton.setBounds(160, 251, 80, 30);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    g
                    if ((userName.getText().equals("admin"))&&(password.getText().equals("admin"))){
                        MainGUI w = new MainGUI();//父窗口
                        w.f.setVisible(true);
                        //new MainGUI().setVisible(true);
                        jframe.setVisible(false);
                    }else {
                        JOptionPane.showMessageDialog(jframe, "用户名或密码错误", "请重新输入",
                                JOptionPane.WARNING_MESSAGE);
                        userName.setText("");
                        password.setText("");
                    }
                }catch(Exception e2){
                    System.out.println(e2);
                }
            }
        });

        cancelButton.setText("取消");
        cancelButton.setBounds(280, 251, 80, 30);
        cancelButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        panel.add(titleLogin);
        panel.add(userLabel);
        panel.add(userName);
        panel.add(pwdLabel);
        panel.add(password);
        panel.add(loginButton);
        panel.add(cancelButton);

        panel.setOpaque(false);

        jframe = new JFrame();
        jframe.getRootPane().putClientProperty(SubstanceLookAndFeel.WATERMARK_PROPERTY,true);
        jframe.setBounds(500, 200, 490, 360);
        jframe.add(panel);
        jframe.setResizable(false); //禁止最大化
        jframe.setVisible(true);

    }
    static class MySkin extends org.jvnet.substance.skin.MistAquaSkin{
        public MySkin(){
            super();
            SubstanceImageWatermark imageWatermark = new SubstanceImageWatermark(this.getClass().getResourceAsStream("background.png"));
            imageWatermark.setOpacity(0.7f);
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
                    LoginGUI test = new LoginGUI();
                    //test.showLogin();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }
}

