import gui.login.LoginGUI;
import network.TClientProcessor;

/**
 * Created by Tony on 2017/6/24.
 */
public class Main {
    public static void main(String[] args){
        LoginGUI w = LoginGUI.getInstance();//父窗口
        w.jframe.setVisible(true);
        TClientProcessor.getInstance().init("127.0.0.1",13244);
    }
}
