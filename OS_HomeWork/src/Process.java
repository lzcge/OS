import java.util.Scanner;

/**
 * 进程
 */
public class Process {

    public String ProcessName;  //进程名
    public int ProcessSegmentNumber;  //进程段数
    public int ProcessLength;  //进程长度

    public Process(String processName, int processSegmentNumber, int processLength) {
        ProcessName = processName;
        ProcessSegmentNumber = processSegmentNumber;
        ProcessLength = processLength;
    }
    /**
     * 创建进程
     * @return
     */
    public static  Process CreateProcess(){
        Scanner input = new Scanner(System.in);
        System.out.print("请输入进程名");
        String ProcessName = input.nextLine();
        System.out.print("请输入进程长度");
        int ProcessLength = input.nextInt();
        System.out.print("请输入进程的段数");
        int ProcessSegmentNumber = input.nextInt();
        Process process = new Process(ProcessName,ProcessSegmentNumber,ProcessLength);
        return process;
    }

    /**
     * 显示进程信息
     */
    public static void displayProcess(Process process){
        System.out.printf("进程名：%s      进程长度：%d        进程段数：%d",process.ProcessName,process.ProcessLength,process.ProcessSegmentNumber);
    }
}
