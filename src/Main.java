import network.TClientProcessor;

/**
 * Created by Tony on 2017/6/24.
 */
public class Main {
    public static void main(String[] args){
        TClientProcessor tcp=new TClientProcessor("127.0.0.1g",13244);
        gui.login.LoginGUI w = new gui.login.LoginGUI();//父窗口
        w.jframe.setVisible(true);
    }
}
