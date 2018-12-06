import java.util.List;

/**
 *物理内存
 */
public class Memory {
    public int InitialAddress;
    public int length;
    public String status;
    public Memory(){

    }

    public Memory(int InitialAddress, int length, String status) {
        this.InitialAddress = InitialAddress;
        this.length = length;
        this.status = status;
    }

    /**
     * 显示内存的使用情况
     * @param memoryList
     */
    public static void display(List<Memory> memoryList){
        for (int i = 0; i < memoryList.size(); i++) {
            Memory m = memoryList.get(i);
            System.out.printf("第 %d 块内存       起始地址为：%d            长度为：%d         使用状态为：%s\n",i,m.InitialAddress,m.length,m.status);
        }
    }

}
