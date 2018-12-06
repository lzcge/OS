import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 段
 */
public class segment {
    public int SegmentNumber;
    public int SegmentLength;
    public String SegmentName;

    public segment(int segmentNumber, int segmentLength,String segmentName) {
        SegmentNumber = segmentNumber;
        SegmentLength = segmentLength;
        SegmentName = segmentName;
    }

    /**
     * 显示进程段信息
     */
    public static void displaySegment(HashMap<Process, List<segment>> segmentMap){
        for(Map.Entry<Process,List<segment>> entry : segmentMap.entrySet()){
            List<segment> segments = entry.getValue();
            Process p = entry.getKey();
            System.out.printf("进程：%s      进程长度：%d      进程段数：%d\n",p.ProcessName,p.ProcessLength,p.ProcessSegmentNumber);
            for(int i=0;i<segments.size();i++){
                System.out.printf("第 %d 段    段长为：%d    段名为：%s\n",segments.get(i).SegmentNumber,segments.get(i).SegmentLength,segments.get(i).SegmentName);
            }
        }
    }

}
