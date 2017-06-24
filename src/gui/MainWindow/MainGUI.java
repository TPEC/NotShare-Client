package gui.MainWindow;
import com.sun.deploy.uitoolkit.UIToolkit;
import org.w3c.dom.events.UIEvent;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by michelle on 2017/6/21.
 */
public class MainGUI extends JFrame {
    public JFrame f;
  //  JPanel p;
    JMenuBar mb;
    JMenu file;
    JMenuItem openNote;
    JMenuItem openDoc;
    JMenuItem saveNote;
    JMenuItem saveDoc;
    JMenuItem shareNote;
    JMenuItem exitItem;
    JMenu edit;
    JMenuItem cutItem;
    JMenuItem copyItem;
    JMenuItem pasteItem;
    JMenu insert;
    JMenuItem insertPic;
    JMenuItem insertRecording;
    JTextArea ta1;// 创建文本域
    //JTextArea ta2;
    FileDialog openDocDia, openNoteDia, saveNoteDia;
    File file1;//定义文件
    DefaultListModel noteListModel;
    JList noteList;
    JScrollPane scrollPane = new JScrollPane();

    public MainGUI() {
        Dimension screenSize =Toolkit.getDefaultToolkit().getScreenSize();
        f = new JFrame("NoteShare");
        f.setLocation(screenSize.width/2-400/2,screenSize.height/2-300/2);
        f.setSize(400,300);
        //f.setSize(600,400);
       // p = new JPanel();
      //  p.setLayout(null);

        mb = new JMenuBar();

        file = new JMenu("文件");
        //newNote = new MenuItem("新建笔记");
        openNote = new JMenuItem("打开笔记");
        openDoc = new JMenuItem("打开文档");
        saveDoc = new JMenuItem("保存文档");
        saveNote = new JMenuItem("保存笔记");
        shareNote = new JMenuItem("共享笔记");
        //exitItem = new MenuItem("关闭", new MenuShortcut(KeyEvent.VK_X));
        exitItem = new JMenuItem("关闭");
        edit = new JMenu("编辑");
        cutItem = new JMenuItem("剪切");
        copyItem = new JMenuItem("复制");
        pasteItem = new JMenuItem("粘贴");

        insert = new JMenu("插入");
        insertPic = new JMenuItem("图片");
        insertRecording = new JMenuItem("录音");

        openDocDia = new FileDialog(f, "打开文档", FileDialog.LOAD);
        openNoteDia = new FileDialog(f, "打开笔记", FileDialog.LOAD);
        saveNoteDia = new FileDialog(f, "保存", FileDialog.SAVE);

        noteListModel = new DefaultListModel();
        noteListModel.addElement("文档/笔记：");
        noteList = new JList(noteListModel);
        ta1 = new JTextArea(50,70);
        //ta2 = new JTextArea(50,100);
        //这里先用JTextArea的类型了。。
        showWindow();
    }
    public void showWindow() {
/*
        p.setBounds(0,0,800,600);
        noteList.setBounds(0,0,300,600);
        ta1.setBounds(310,0,480,600);*/
        //p.setBounds(0, 0, getBounds().width, getBounds().height);
        //noteList.setBounds(0,0,getBounds().width/3,getBounds().height);
        //ta1.setBounds(getBounds().width/3,0,getBounds().width/3*2-10,getBounds().height);
        //p.add(noteList);
       // p.add(ta1);

        GridBagLayout gridbag = new GridBagLayout();
        f.setLayout(gridbag);
        GridBagConstraints c1 = new GridBagConstraints();
        GridBagConstraints c2 = new GridBagConstraints();

        c1.fill = GridBagConstraints.BOTH;
        c1.gridx=0;
        c1.gridy=0;
        c1.gridheight=1;
        c1.gridwidth=1;
        c1.weightx=1.0;//默认值为0.0
        c1.weighty=1.0;//默认值为0.0
        c1.anchor = GridBagConstraints.WEST;
        gridbag.setConstraints(noteList,c1);
        f.add(noteList,c1);

        //noteList.setBackground(Color.pink);
        c2.fill = GridBagConstraints.BOTH;
        c2.gridx=1;
        c2.gridy=0;
        c2.gridwidth=GridBagConstraints.REMAINDER;
        c2.gridheight=GridBagConstraints.REMAINDER;
        c2.anchor=GridBagConstraints.EAST;
        c2.weightx=1.0;//默认值为0.0
        c2.weighty=1.0;
        //gridbag.setConstraints(jp2,c);
        gridbag.setConstraints(ta1,c2);
        f.add(ta1,c2);
        ta1.setBackground(Color.pink);

      /*  c.fill = GridBagConstraints.NONE;
        c.gridx=2;
        c.gridy=0;
        c.gridwidth=GridBagConstraints.REMAINDER;
        c.gridheight=1;
        c.weightx=1.0;//默认值为0.0
        c.weighty=2.0;
        //gridbag.setConstraints(jp2,c);
        p.add(ta2,c);
*/

       // noteList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        f.setJMenuBar(mb);//将此窗体的菜单栏设置为指定的菜单栏。
        mb.add(file);//文件 菜单    //file.add(newNote);
        file.add(openNote);
        file.add(openDoc);
        file.add(saveNote);
        file.add(shareNote);
        file.add(exitItem);
        mb.add(edit);
        edit.add(cutItem);
        edit.add(copyItem);
        edit.add(pasteItem);
        mb.add(insert);
        insert.add(insertPic);
        insert.add(insertRecording);

        myEvent();//加载事件处理
        f.setTitle("NoteShare");
        f.setResizable(true);//设置窗体是否可以调整大小，参数为布尔值
        f.pack();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);

    }
    private void myEvent(){
        /*
        noteList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                openNoteDia.setVisible(true);//显示打开文件对话框

                String dirpath = openNoteDia.getDirectory();//获取打开文件路径并保存到字符串中。
                String fileName = openNoteDia.getFile();//获取打开文件名称并保存到字符串中

                if (dirpath == null || fileName == null)//判断路径和文件是否为空
                    return;
                else
                    ta1.setText(null);//文件不为空，清空原来文件内容。
                file1 = new File(dirpath, fileName);//创建新的路径和名称

                try {
                    noteListModel.addElement(fileName);
                    BufferedReader buff = new BufferedReader(new FileReader(file1));//尝试从文件中读东西
                    String line = null;//变量字符串初始化为空
                    while ((line = buff.readLine()) != null) {
                        ta1.append(line + "\r\n");//显示每一行内容
                    }
                    buff.close();//关闭文件
                } catch (FileNotFoundException e1) {
                    // 抛出文件路径找不到异常
                    e1.printStackTrace();
                } catch (IOException e1) {
                    // 抛出IO异常
                    e1.printStackTrace();
                }
            }
        });
*/

        openNote.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                openNoteDia.setVisible(true);//显示打开文件对话框

                String dirpath = openNoteDia.getDirectory();//获取打开文件路径并保存到字符串中。
                String fileName = openNoteDia.getFile();//获取打开文件名称并保存到字符串中

                if (dirpath == null || fileName == null)//判断路径和文件是否为空
                    return;
                else
                    ta1.setText(null);//文件不为空，清空原来文件内容。
                file1 = new File(dirpath, fileName);//创建新的路径和名称

                try {
                    noteListModel.addElement(fileName);
                    BufferedReader buff = new BufferedReader(new FileReader(file1));//尝试从文件中读东西
                    String line = null;//变量字符串初始化为空
                    while ((line = buff.readLine()) != null) {
                        ta1.append(line + "\r\n");//显示每一行内容
                    }
                    buff.close();//关闭文件
                } catch (FileNotFoundException e1) {
                    // 抛出文件路径找不到异常
                    e1.printStackTrace();
                } catch (IOException e1) {
                    // 抛出IO异常
                    e1.printStackTrace();
                }

            }

        });

        openDoc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                openDocDia.setVisible(true);//显示打开文件对话框

                String dirpath = openDocDia.getDirectory();//获取打开文件路径并保存到字符串中。
                String fileName = openDocDia.getFile();//获取打开文件名称并保存到字符串中

                if (dirpath == null || fileName == null)//判断路径和文件是否为空
                    return;
                else
                    ta1.setText(null);//文件不为空，清空原来文件内容。
                file1 = new File(dirpath, fileName);//创建新的路径和名称

                try {
                    BufferedReader buff = new BufferedReader(new FileReader(file1));//尝试从文件中读东西
                    String line = null;//变量字符串初始化为空
                    while ((line = buff.readLine()) != null) {
                        ta1.append(line + "\r\n");//显示每一行内容
                    }
                    buff.close();//关闭文件
                } catch (FileNotFoundException e1) {
                    // 抛出文件路径找不到异常
                    e1.printStackTrace();
                } catch (IOException e1) {
                    // 抛出IO异常
                    e1.printStackTrace();
                }

            }

        });

        // 保存菜单项监听
        saveNote.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (file1== null) {
                    saveNoteDia.setVisible(true);//显示保存文件对话框
                    String dirpath = saveNoteDia.getDirectory();//获取保存文件路径并保存到字符串中。
                    String fileName = saveNoteDia.getFile();////获取打保存文件名称并保存到字符串中

                    if (dirpath == null || fileName == null)//判断路径和文件是否为空
                        return;//空操作
                    else
                        file1=new File(dirpath,fileName);//文件不为空，新建一个路径和名称
                }
                try {
                    noteListModel.removeElement(file1.getName());//??
                    BufferedWriter buffw = new BufferedWriter(new FileWriter(file1));

                    String text = ta1.getText();//获取文本内容
                    buffw.write(text);//将获取文本内容写入到字符输出流

                    buffw.close();//关闭文件
                    ta1.setText("");
                    JOptionPane.showMessageDialog(null, "保存成功");

                } catch (IOException e1) {
                    //抛出IO异常
                    e1.printStackTrace();
                }




            }

        });

        // 窗体关闭监听
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // TODO Auto-generated method stub
                // super.windowClosing(e);
                System.exit(0);
            }
        });
        // 退出菜单项监听
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }

        });
    }
    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel("org.jvnet.substance.skin.SubstanceBusinessBlackSteelLookAndFeel");//"org.jvnet.substance.skin.SubstanceAutumnLookAndFeel"
                    //BusinessBlueSteel,CremeCoffee,OfficeBlue2007，GreenMagic,EmeraldDusk
                    MainGUI test = new MainGUI();
                    //test.showLogin();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

}


