import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 段表
 */
public class SegmentTable {
    public int segmentNumber;
    public int segmentLength;
    public int address;

    public SegmentTable(int segmentNumber, int segmentLength, int address) {
        this.segmentNumber = segmentNumber;
        this.segmentLength = segmentLength;
        this.address = address;
    }


    /**
     * 打印所有的段表信息
     */
    public static void displaySegmentTableMap(HashMap<Process,List<SegmentTable>> segmenttablemap){
        for(Map.Entry<Process,List<SegmentTable>> entry:segmenttablemap.entrySet()){
            Process pro = entry.getKey();
            System.out.printf("进程：%s 的段表如下\n",pro.ProcessName);
            List<SegmentTable> segmentTableList = entry.getValue();
            for (int i=0;i<segmentTableList.size();i++){
                SegmentTable segmentTable = segmentTableList.get(i);
                System.out.printf("段号：%d     段长：%d      基址：%d\n",segmentTable.segmentNumber,segmentTable.segmentLength,segmentTable.address);
            }
        }
    }
}
