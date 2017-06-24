import gui.login.LoginGUI;
import network.TClientProcessor;

/**
 * Created by Tony on 2017/6/24.
 */
public class Main {
    public static void main(String[] args){
        TClientProcessor.getInstance().init("127.0.0.1g",13244);
        LoginGUI w = LoginGUI.getInstance();//父窗口
        w.jframe.setVisible(true);
    }
}
