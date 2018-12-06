import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.List;

public class Test {
    static HashMap<Process,List<segment>> segmentMap = new HashMap<>(); //所有进程和其对应的段
    static HashMap<Process,List<SegmentTable>> segmenttablemap = new HashMap<>();   //所有进程的段表
    static List<Memory> memoryList = new ArrayList<>();    //物理内存

    public static void main(String [] args) throws InvocationTargetException, InterruptedException{
        Scanner input = new Scanner(System.in);
        Implement implement = Implement.getImplement();
        int caozuo;
        //初始化内存情况
        Memory memory1 = new Memory(0,20,"OS");
        Memory memory2 = new Memory(20,100,"free");
        Memory memory3 = new Memory(120,80,"me");
        Memory memory4 = new Memory(200,200,"free");
        Memory memory5 = new Memory(400,100,"he");
        Memory memory6 = new Memory(500,100,"free");
        memoryList.add(memory1);
        memoryList.add(memory2);
        memoryList.add(memory3);
        memoryList.add(memory4);
        memoryList.add(memory5);
        memoryList.add(memory6);
        Frame frame1 = new Frame(memoryList);


        //基本段式存储管理
        do{
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    updateJframe(frame1);
                }
            });
            implement.Menu();
            System.out.print("请输入操作！");
            caozuo = input.nextInt();
            Process pro = null;
            switch (caozuo) {
                case 1:
                    pro = Process.CreateProcess();
                    implement.DivideWorkToSegment(pro,segmentMap);
                    break;
                case 2:
                    segment.displaySegment(segmentMap);
                    System.out.println("---");
                    break;
                case 3:
                    segmenttablemap = implement.Distribute(segmentMap,memoryList,segmenttablemap);
                    Memory.display(memoryList);   //显示内存情况
                    SegmentTable.displaySegmentTableMap(segmenttablemap);
                    System.out.println("---");
                    break;
                case 4:
                    implement.RecoverMermory(memoryList,segmentMap,segmenttablemap);
                    Memory.display(memoryList);
                    System.out.println("---");
                    break;
                case 5:SegmentTable.displaySegmentTableMap(segmenttablemap);break;
                case 6:
                    int PhysicalAddress = implement.searchAddress(segmentMap,segmenttablemap);
                    System.out.printf("*****************物理地址为：%d\n",PhysicalAddress);
                    break;
                default:System.out.println("输入有误！");break;

            }


        }while(caozuo!=0);
        System.exit(0);
    }

    /**
     * 刷新界面
     */
    public static void updateJframe(Frame frame1){
        frame1.pane2.removeAll();
        frame1.pane2.repaint();
        frame1.textArea1.removeAll();
        frame1.textArea1.repaint();
        for (int i = 0; i < memoryList.size(); i++) {
            Memory m = memoryList.get(i);
            JLabel jLabel = new JLabel(m.status+"("+m.InitialAddress+")", JLabel.CENTER);
            jLabel.setBorder(BorderFactory.createLineBorder(Color.red));
//            jLabel.setFont(new Font("宋体", Font.BOLD, 20));
            jLabel.setOpaque(true);
            jLabel.setBounds(0, m.InitialAddress, 300, m.length);
            if (m.status.equals("free")) {
                jLabel.setBackground(Color.CYAN);
            } else {

                jLabel.setBackground(Color.orange);
            }
            Frame.pane2.add(jLabel);

        }

        for(Map.Entry<Process,List<SegmentTable>> entry:segmenttablemap.entrySet()){
            Process pro = entry.getKey();
            frame1.textArea1.append("\n");
            frame1.textArea1.append("进程:"+pro.ProcessName+"的段表如下\n");
//            System.out.printf("进程：%s 的段表如下\n",pro.ProcessName);
            List<SegmentTable> segmentTableList = entry.getValue();
            for (int i=0;i<segmentTableList.size();i++){
                SegmentTable segmentTable = segmentTableList.get(i);
                frame1.textArea1.append("段号："+segmentTable.segmentNumber+"   段长："+segmentTable.segmentLength+"  基址："+segmentTable.address+"\n");
//                System.out.printf("段号：%d     段长：%d      基址：%d\n",segmentTable.segmentNumber,segmentTable.segmentLength,segmentTable.address);
            }
        }

        frame1.pane2.setVisible(false);
        frame1.pane2.setVisible(true);
        frame1.textArea1.setVisible(false);
        frame1.textArea1.setVisible(true);
    }



}
