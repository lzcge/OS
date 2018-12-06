import jdk.nashorn.internal.ir.Block;

import java.util.*;

/**
 * 操作实现
 */
public class Implement {

    /**
     * Implement单例工厂
     */
    private Implement(){    }
    private static Implement instance = new Implement();
    public static Implement getImplement(){
        return instance;
    }

    /**
     * 内存分配(首次适应算法)
     * @param segmentMap 所有的进程以及对应的段
     * @param memoryList 内存
     */
    public HashMap<Process,List<SegmentTable>> Distribute(HashMap<Process,List<segment>> segmentMap,List<Memory> memoryList,HashMap<Process,List<SegmentTable>> segmenttablemap){
//        HashMap<Process,List<SegmentTable>> segmenttablemap = new HashMap<>();
        for (Map.Entry<Process,List<segment>> entry:segmentMap.entrySet()){    //得到每个进程中所有段
            Process pro = entry.getKey();
            //已经分配过不再分配该进程
            if(segmenttablemap.containsKey(pro)){
                continue;
            }
            List<segment> segmentList = entry.getValue();
            List<SegmentTable> segmentTableList = new ArrayList<>();//每个进程构建一张段表
            List<Memory> Copy_memoryList = new ArrayList<>(memoryList);
            for (int i=0;i<segmentList.size();i++){       //遍历当前进程中的所有段
                segment se = segmentList.get(i);
                boolean flag = true;
                for (int j = 0;j<Copy_memoryList.size();j++){    //遍历内存，满足条件进行分配
                    Memory m = memoryList.get(j);
                    if (se.SegmentLength<=m.length && m.status.equals("free")){
                        Memory memory1 = new Memory(m.InitialAddress,se.SegmentLength,se.SegmentName);
                        memoryList.add(j,memory1);
                        m.InitialAddress = m.InitialAddress+se.SegmentLength;
                        m.length = m.length-se.SegmentLength;
                        //刚好把这块空闲的分配完，移除这块的记录
                        if(m.length==0)   memoryList.remove(m);
                        //添加当前进程下的某个段的段表映射信息
                        int address = memory1.InitialAddress;  //基址
                        SegmentTable segmentTable = new SegmentTable(i,se.SegmentLength,address);
                        segmentTableList.add(segmentTable);
                        flag = false;
                        break;
                    }

                }
                if(flag){
                    System.out.println("-----------------------------------------------------------------");
                    System.out.printf("分配段 %s 失败！\n",se.SegmentName);
                }
            }
            segmenttablemap.put(pro,segmentTableList);

        }

        return segmenttablemap;

    }



    /**
     * 回收内存
     * 按作业回收
     * @param memoryList 物理内存区
     * @param segmentMap 进程和其对应划分的段
     */
    public void RecoverMermory(List<Memory> memoryList, HashMap<Process,List<segment>> segmentMap,HashMap<Process,List<SegmentTable>> segmenttablemap){
        Scanner input = new Scanner(System.in);
        System.out.println("请输入需要回收的进程名");
        String ProcessName = input.nextLine();
        Process process;
        Boolean flag = false;     //标记当前进程是否回收成功
        HashMap<Process,List<segment>> Copy_segmentMap = new HashMap<>();
        Copy_segmentMap.putAll(segmentMap);
        for(Map.Entry<Process,List<segment>> entry:Copy_segmentMap.entrySet()){
            process = entry.getKey();
            if(process.ProcessName.equals(ProcessName)){
                flag = true;
                //每一段去查找对应内存中的区域并删除
                for(Iterator<segment> iter = entry.getValue().iterator();iter.hasNext();){
                    List<Memory> Copy_memoryList = new ArrayList<>(memoryList);    //备份MemoryLsit,用于更新和返回
                    segment se = iter.next();
                    Merge_Memory(memoryList,Copy_memoryList,se.SegmentName);
                }
                //从进程map中移除改进程
                segmentMap.remove(process);
                //删除该进程的段表
                segmenttablemap.remove(process);
            }
        }
        if(!flag){
            System.out.println("该进程回收失败！重新输入回收进程");
        }


    }


    /**
     * 回收内存并合并空闲分区
     * @param memoryList
     * @param Copy_memoryList
     * @param SegmentName
     */
    private void Merge_Memory(List<Memory> memoryList,List<Memory> Copy_memoryList,String SegmentName){
        for(int i = 0;i < Copy_memoryList.size();i++){
            Memory me = Copy_memoryList.get(i);
            if(me.status.equals(SegmentName)){
                Memory copy_me1 = memoryList.get(i);
                copy_me1.status = "free";
                //判断是否合并
                Memory copy_me2 =  null;
                Memory copy_me3 = null;
                //如果上面或者下面为空闲的就获取
                if (i+1<=memoryList.size()  && memoryList.get(i+1).status.equals("free"))
                    copy_me2 = memoryList.get(i+1);
                if (i-1>=0  && memoryList.get(i-1).status.equals("free"))
                    copy_me3 = memoryList.get(i-1);
                //与上面空闲合并
                if((copy_me3!=null) && (copy_me2==null)){
                    copy_me1.length += copy_me3.length;
                    copy_me1.InitialAddress = copy_me3.InitialAddress;
                    memoryList.remove(copy_me3);
                    break;
                }
                //与下面空闲合并
                else if((copy_me2!=null) && (copy_me3==null)){
                    copy_me1.length += copy_me2.length;
                    memoryList.remove(copy_me2);
                    break;
                }
                //上下都合并
                else if((copy_me2!=null) && (copy_me3!=null)){
                    copy_me1.length +=(copy_me3.length+copy_me2.length);
                    copy_me1.InitialAddress = copy_me3.InitialAddress;
                    memoryList.remove(copy_me3);
                    memoryList.remove(copy_me2);
                    break;
                }
            }
        }
    }

    /**
     * 更新段表
     */
    public void UpdateSegment(){

    }

    /**
     * 根据逻辑地址去找物理地址
     * @param segmentMap
     * @param segmenttablemap
     */
    public int searchAddress(HashMap<Process,List<segment>> segmentMap,HashMap<Process,List<SegmentTable>> segmenttablemap){
        Scanner input = new Scanner(System.in);
        System.out.println("输入你想查询的进程及其对应的某个地址");
        System.out.println("输入你想查询的进程");
        String ProcessName = input.nextLine();
        System.out.println("输入要查询的其对应的某个地址");
        int address = input.nextInt();
        int PhysicalAddress = 0;
        //根据进程名和地址找到地址属于哪一个段，获取其与该段首地址的距离长度
        for(Map.Entry<Process,List<segment>> entry:segmentMap.entrySet()){
            Process process = entry.getKey();
            List<segment> segmentList = entry.getValue();
            int length=0;
            if(process.ProcessName.equals(ProcessName)){
                for(int i=0;i<segmentList.size();i++){
                    if(i-1>=0) length +=segmentList.get(i-1).SegmentLength;
                    if(address>length && address<=length+segmentList.get(i).SegmentLength){
                        int distance = address-length;    //得到这个地址相对这段中的地址
                        int SegmentNumber = i;           //得到这一段的段号
                        //去段表查询这一段对应的物理的基址
                        SegmentTable segmentTable  = segmenttablemap.get(process).get(SegmentNumber);
                        PhysicalAddress = distance+segmentTable.address;
                    }
                }
            }
        }
        return PhysicalAddress;


    }


    /**
     * 划分作业为子段
     * @param process 待划分的作业
     * @param segmentMap  存放对应进程段的进程段
     */
    public void  DivideWorkToSegment(Process process,HashMap<Process,List<segment>> segmentMap){
        Scanner input = new Scanner(System.in);
        List<segment> segmentlist = new ArrayList<>();
        int length = process.ProcessSegmentNumber;
        int temp=0;     //记录输入长度是否超过了该进程总长
        for(int i=0;i<length;i++){
            System.out.printf("请输入第 %d 段的长度",i);
            int SegmentLength = input.nextInt();
            int segmentNumber = i;
            String SegmentName = process.ProcessName+"-"+i;
            segment se = new segment(segmentNumber,SegmentLength,SegmentName);
            segmentlist.add(se);
            temp+=SegmentLength;
        }
        if(temp!=process.ProcessLength)
            System.out.println("*********************段长总和不等于进程长度，进程创建失败*************************");
        else
        {
            segmentMap.put(process,segmentlist);
            System.out.println("*******************************进程创建成功***************************************");
        }
    }


    /**
     * 菜单选项
     */
    public void Menu(){
        System.out.println("------------------------------------------菜单----------------------------------------------");
        System.out.println(" 1.创建进程   2.显示进程    3.进程调度    4.回收内存    5.显示段表     6.查询地址    0.退出"    );
        System.out.println("---------------------------------------------------------------------------------------------");

    }

}
