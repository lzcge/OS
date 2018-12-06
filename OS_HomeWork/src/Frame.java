import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Frame extends JFrame implements Runnable{

    JFrame frame = new JFrame();
    JPanel panel = new JPanel();
    static JPanel pane2 = new JPanel();
    JPanel pane3 = new JPanel();
    JButton button1 ;
    JButton button2 ;
    JButton button3 ;
    JButton button4 ;
    JButton button5;
    JTextArea textArea1;


    public void run(){

    }

    public Frame(List<Memory> memoryList){

        textArea1 = new JTextArea(40,30);
        frame.setBounds(400,200,200,200);
        frame.setSize(800,800);
        frame.setLayout(null);
        pane2.setLayout(null);
        panel.setBounds(0,0,400,700);
        pane2.setBounds(420,0,300,700);
        pane3.setBounds(0,700,800,50);
        for (int i = 0; i < memoryList.size(); i++) {
            Memory m = memoryList.get(i);
            JLabel jLabel = new JLabel(m.status+"("+m.InitialAddress+")", JLabel.CENTER);
            jLabel.setFont(new Font("宋体", Font.BOLD, 20));
            jLabel.setOpaque(true);
            jLabel.setBounds(0, m.InitialAddress, 300, m.length);
            if (m.status.equals("free")) {
                jLabel.setBackground(Color.CYAN);
            } else {

                jLabel.setBackground(Color.orange);
            }
            pane2.add(jLabel);

        }

//        for(Map.Entry<Process,List<SegmentTable>> entry:segmenttablemap.entrySet()){
//            Process pro = entry.getKey();
//            System.out.printf("进程：%s 的段表如下\n",pro.ProcessName);
//            List<SegmentTable> segmentTableList = entry.getValue();
//            for (int i=0;i<segmentTableList.size();i++){
//                SegmentTable segmentTable = segmentTableList.get(i);
//                System.out.printf("段号：%d     段长：%d      基址：%d\n",segmentTable.segmentNumber,segmentTable.segmentLength,segmentTable.address);
//            }
//        }

        panel.add(new JScrollPane(textArea1));
        frame.add(panel);
        frame.add(pane2);
        frame.add(pane3);
        frame.setVisible(true);

    }




}
