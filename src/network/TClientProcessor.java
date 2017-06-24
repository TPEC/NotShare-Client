package network; /**
 * Created by Irene on 2017/6/23.
 */
import gui.login.LoginGUI;
import gui.register.RegisterGUI;

import java.io.*;
import java.net.Socket;

public class TClientProcessor implements Runnable{
    static final int BUFFER_SIZE=512;
    Socket s;
    Thread t;
    String fs="";
    long fsize;
    boolean flag, fileFlag;
    OutputStream os=null;
    InputStream is=null;
    FileOutputStream fos=null;

    public void init(String host,int port){
        try {
            s=new Socket(host, port);
            os=s.getOutputStream();//传给服务器
            is=s.getInputStream();//服务器传来
        } catch (IOException e) {
            e.printStackTrace();
        }
        t=new Thread(this);
        flag=true;
        fileFlag=false;
        t.start();
    }

    public int send(byte[] buf,int size) {
        if (os != null) {
            try {
                os.write(buf, 0, size);
                os.flush();
                return size;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public void stop(){
        try {
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void register(String userName, String password){
        String str="RGT0";
        str+=userName;
        for(int i=str.length();i<36;i++)
            str+='0';
        str+=password;
        for(int i=str.length();i<BUFFER_SIZE;i++)
            str+='0';
        send(str.getBytes(),BUFFER_SIZE);
    }

    public void getUserInformation(int userID){//获取userID的信息
        String str="GUI0";//获取id的information
        str+=String.valueOf(userID);
        for(int i=str.length();i<BUFFER_SIZE;i++)
            str+='0';
        send(str.getBytes(),BUFFER_SIZE);
    }

    public void getDocInformation(int docID){//获取docID的信息
        String str="GDI0";//获取id的information
        str+=String.valueOf(docID);
        for(int i=str.length();i<BUFFER_SIZE;i++)
            str+='0';
        send(str.getBytes(),BUFFER_SIZE);
    }

    public void getNoteInformation(int noteID){//获取noteID的信息
        String str="GNI0";//获取id的information
        str+=String.valueOf(noteID);
        for(int i=str.length();i<BUFFER_SIZE;i++)
            str+='0';
        send(str.getBytes(),BUFFER_SIZE);
    }

    public void signIn(String userName, String password){
        String str="SGI0";
        str+=userName;
        for(int i=str.length();i<36;i++)
            str+='0';
        str+=password;
        for(int i=str.length();i<BUFFER_SIZE;i++)
            str+='0';
        send(str.getBytes(),BUFFER_SIZE);
    }

    public long sendFile(String path, int type, int docid){//put, type:1-doc, 2-note; 如为doc, docid=-1
        String str="PUT";
        str+=String.valueOf(type);
        long fs=0;
        try {
            File file = new File(path);
            if (file.exists() && file.isFile()) {
                str += String.valueOf(file.length());
                for (int i = str.length(); i < 255; i++)
                    str += '0';
                str += file.getName();
                for (int i = str.length(); i < 500; i++)
                    str += '0';
                if(type==2)
                    str+=String.valueOf(docid);
                for(int i=str.length();i<BUFFER_SIZE;i++)
                    str+='0';
                send(str.getBytes(), BUFFER_SIZE);
                FileInputStream fis=new FileInputStream(file);
                byte[] buf = new byte[BUFFER_SIZE];
                int rs;
                while ((rs = fis.read(buf)) >= 0) {
                    long fs_ = send(buf, rs);
                    if (fs_ <= 0)
                        break;
                    fs += rs;
                }
                System.out.println(fs);
                fis.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            return 0;
        }
    }

    public void getFile(int id){
        String str="GET0";
        str+=String.valueOf(id);
        for(int i=str.length();i<BUFFER_SIZE;i++)
            str+='0';
        send(str.getBytes(), BUFFER_SIZE);
    }

    public void favoritePerson(int id){
        String str="FAP0";
        str+=String.valueOf(id);
        for(int i=str.length();i<BUFFER_SIZE;i++)
            str+='0';
        send(str.getBytes(), BUFFER_SIZE);
    }

    public void favoriteDoc(int id){
        String str="FAD0";
        str+=String.valueOf(id);
        for(int i=str.length();i<BUFFER_SIZE;i++)
            str+='0';
        send(str.getBytes(), BUFFER_SIZE);
    }

    public void favoriteNote(int id){
        String str="FAN0";
        str+=String.valueOf(id);
        for(int i=str.length();i<BUFFER_SIZE;i++)
            str+='0';
        send(str.getBytes(), BUFFER_SIZE);
    }

    public void shareAll(int ID, int type){//type:1-doc, 2-note
        String str="SHA";
        str+=String.valueOf(type);
        str+=String.valueOf(ID);
        for(int i=str.length();i<BUFFER_SIZE;i++)
            str+='0';
        send(str.getBytes(), BUFFER_SIZE);
    }

    public void shareFollowing(int ID, int type){
        String str="SHF";
        str+=String.valueOf(type);
        str+=String.valueOf(ID);
        for(int i=str.length();i<BUFFER_SIZE;i++)
            str+='0';
        send(str.getBytes(), BUFFER_SIZE);
    }

    public void run() {
        byte[] buf=new byte[BUFFER_SIZE];
        long writeLength=0;
        while (flag){
            try {
                int k=is.read(buf);
                if(k>0){
                    String a="";
                    a+=(char)buf[0];
                    a+=(char)buf[1];
                    a+=(char)buf[2];
                    if(fileFlag==false) {
                        switch (a) {
                            case "RGS"://注册成功
                                RegisterGUI.getInstance().CMD(new String[]{"0","s"});
                                break;
                            case "RGF"://注册失败
                                RegisterGUI.getInstance().CMD(new String[]{"0","f"});
                                break;
                            case "SIS"://登录成功
                                LoginGUI.getInstance().CMD(new String[]{"0","s"});
                                break;
                            case "SIF"://登录失败
                                LoginGUI.getInstance().CMD(new String[]{"0","f"});
                                break;
                            case "GET"://客户端的接受
                                if (fos == null) {
                                    fileFlag = true;
                                    String fileName = "";
                                    for (int i = 255; i < buf.length; i++) {
                                        if (buf[i] != 0)
                                            fileName += (char) buf[i];
                                        else
                                            break;
                                    }
                                    File file = new File(fileName);
                                    System.out.println(file.getAbsolutePath());
                                    fos = new FileOutputStream(fileName);
                                    for (int i = 4; i < buf.length; i++) {
                                        if (buf[i] != 0)
                                            fs += buf[i];
                                        else
                                            break;
                                    }
                                    fsize = Integer.valueOf(fs);
                                }
                                break;
                            case "GUI":
                                break;
                            case "GDI":
                                break;
                            case "GNI":
                                break;
                        }
                    }else {
                        if(writeLength<fsize){
                            fos.write(buf,0,k);
                            writeLength+=k;
                            fos.flush();
                        }else {
                            fos.close();
                            fos=null;
                            fileFlag=false;
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static final TClientProcessor TCP=new TClientProcessor();

    private TClientProcessor(){

    }

    public static TClientProcessor getInstance(){
        return TCP;
    }
}