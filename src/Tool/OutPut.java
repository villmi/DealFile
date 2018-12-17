package Tool;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class OutPut {
    /******Init*****/
    private SimpleDateFormat simpleDateFormat;
    private String name;
    private File file;
    private FileOutputStream fileOutputStream;
    private BufferedOutputStream bufferedOutputStream;
    private void initOutPut(String s) throws IOException {
        simpleDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
        name = simpleDateFormat.format(new Date());
        file = new File("/Users/vill/Desktop/complexNet" + s + name + ".txt");
        fileOutputStream = new FileOutputStream(file);
        bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
    }
/******Deal Function*****/
    private StringBuffer getId(Point point,StringBuffer stringBuffer){//获取领接链表
        if(point.nextPoint == null) {
            stringBuffer.append(point.id);
        }
        else
        {
            stringBuffer.append(point.id + " ");
            getId(point.nextPoint,stringBuffer);
        }
        return stringBuffer;
    }

    private int getOut_degree(Point point)  {//获取每个节点的出度
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer = getId(point,stringBuffer);
        return stringBuffer.toString().split(" ").length - 1;
    }

    private int[] getIn_degree(Point point,int[] in_count) throws IOException {//获取每个节点的入度
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer = getId(point,stringBuffer);
        String[] ss = stringBuffer.toString().split(" ");
        if(ss.length > 1)                                                     //如果字符串数组的长度大于1则代表该节点存在邻居节点
        {
            for(int i = 1 ; i < ss.length ; i ++)
            {
                in_count[Integer.parseInt(ss[i])] ++;
            }
        }
        return in_count;
    }

    private List<List<Integer>> insertList(List<List<Integer>> classifyByCounts,int i,int a)
    {
        List<Integer> firstList = classifyByCounts.get(i);
        firstList.add(a);
        classifyByCounts.set(i, firstList);
        return classifyByCounts;
    }



/*****Out Function******/
    public void outPutBZ(Point[] points) throws IOException {
        initOutPut("_BZ");
        for(int i = 0 ; i < points.length; i ++)
        {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer = getId(points[i],stringBuffer);
            bufferedOutputStream.write((stringBuffer.toString() + "\n").getBytes());
            bufferedOutputStream.flush();
        }
    }

    public void outPutOut_degree(Point[] points) throws IOException {
        initOutPut("_Out_degree");
        for(int i = 0 ; i < points.length; i ++)
        {
            int count = getOut_degree(points[i]);
            bufferedOutputStream.write((i + " " + count + "\n").getBytes());
            bufferedOutputStream.flush();
        }

    }

    public void outPutIn_degree(Point[] points) throws IOException {
        int[] count_in = new int[points.length];
        initOutPut("_In_degree");
        for(int i = 0 ; i < points.length; i++)
        {
            count_in = getIn_degree(points[i],count_in);
        }
        for (int i = 0 ; i < count_in.length ; i++)
        {
            bufferedOutputStream.write((i + " " + count_in[i] + "\n").getBytes());
            bufferedOutputStream.flush();
        }
    }

    public void outPutCountOfOut_degree(Point[] points) throws IOException {
        initOutPut("_CountOfOut_degree");
        int max = 0;
        for(int i = 0 ; i < points.length; i ++)
        {
            int count = getOut_degree(points[i]);
            if(count > max)
                max = count;
        }
        int[] countOfOut_degree = new int[max+1];
        for(int i = 0; i < points.length ; i ++)
        {
            int count = getOut_degree(points[i]);
            countOfOut_degree[count] ++;
        }
        for(int i = 0 ; i < countOfOut_degree.length; i ++)
        {
            bufferedOutputStream.write((i + " " + countOfOut_degree[i] + "\n").getBytes());
            bufferedOutputStream.flush();
        }
    }

    public void outPutCountOfIn_degree(Point[] points) throws IOException {
        int[] count_in = new int[points.length];
        initOutPut("_CountOfIn-degree");
        for(int i = 0 ; i < points.length; i++)
        {
            count_in = getIn_degree(points[i],count_in);
        }
        int max = 0;
        for(int i = 0 ; i < count_in.length ; i ++)
        {
            if(count_in[i] > max)
                max = count_in[i];
        }
        int[] countOfIn_degree = new int[max + 1];
        for(int i = 0 ; i < count_in.length ; i ++)
        {
            countOfIn_degree[count_in[i]] ++ ;
        }
        for(int i = 0 ; i < countOfIn_degree.length ; i ++)
        {
            bufferedOutputStream.write((i + " " + countOfIn_degree[i] + "\n").getBytes());
            bufferedOutputStream.flush();
        }
    }

    public void outPutRebuild(Point[] points) throws IOException {//随机生成新图的函数
        initOutPut("_random");
        int[] outCounts = new int[points.length];
        int[] inCounts = new int[points.length];
        int[] totalCounts = new int[points.length];
        List<Integer> firstList;
        List<Integer> secondList;
        boolean doubleFlag;
        for(int i = 0 ; i < points.length ; i++)
        {
            outCounts[i] = getOut_degree(points[i]);  //获取总出度
        }
        for(int i = 0 ; i < points.length; i++)
        {
            inCounts = getIn_degree(points[i],inCounts);//获取总入度
        }
        for(int i = 0 ; i < points.length; i ++)
        {
            totalCounts[i] = outCounts[i] + inCounts[i];//出入度之和
        }
        int max = 0 ;
        for(int i = 0; i < totalCounts.length; i++)
        {
            if(max < totalCounts[i])
                max = totalCounts[i];//寻找出点最大的出入度之和
        }
        max ++;//需要多一个的空间
        List<List<Integer>> classifyByCounts = new ArrayList<>();//第0位定义为废纸篓，当处理的出入度之和为0则放入废纸篓
        for(int i = 0 ; i < max; i ++)
        {
            classifyByCounts.add(new ArrayList<>());//初始化以度为index的list
        }
        for(int i = 0; i < totalCounts.length; i++ )
        {
            List<Integer> countList = classifyByCounts.get(totalCounts[i]);//依次以度为标准，将所有点分类
            countList.add(i);
            classifyByCounts.set(totalCounts[i],countList);
        }
        int i = classifyByCounts.size() - 1;//
        Random random = new Random();
        while (classifyByCounts.get(0).size() != totalCounts.length)//如果出入度的和为0的点为所有点个数之和，则表示随机图已经生成
        {
            if(classifyByCounts.get(i).size() != 0)//从度之和最高的点开始随机连接，最高的度的点的个数为0
            {
                firstList = classifyByCounts.get(i);//为了防止世界被破坏（出现两个随机需要从度不同的list中去除）
                if(firstList.size() == 1){          //故取两个list，若第一个list里面只有一个点则，第二个list从下一个list中取出（即度-1）
                    secondList = classifyByCounts.get(i - 1);
                    doubleFlag = true;//doubleFlag表示是否从两个不同的list中去除
                }
                else{
                    secondList = firstList;
                    doubleFlag = false;
                }
                int random_a;
                int random_b;
                if(firstList.size() == 1)//因为随机数并不支持0为种子，所以偷懒了，就这样吧，我还要写爬虫，上王者，没时间了
                    random_a = 0;
                else
                    random_a = random.nextInt(firstList.size() - 1);
                if(secondList.size() == 1)
                    random_b = 0;
                else
                    random_b = random.nextInt(secondList.size() - 1);
                int a = firstList.get(random_a);
                firstList.remove(random_a);//既然随机过了，于是可以用的度-1，所以从之前的list移除，进入下一个list
                int b = secondList.get(random_b);
                if(doubleFlag)
                    secondList.remove(random_b);
                else
                    firstList.remove(random_b);
                if(doubleFlag) {
                    classifyByCounts.set(i ,firstList);
                    classifyByCounts.set(i - 1, secondList);
                    classifyByCounts = insertList(classifyByCounts,i - 1,a);//因为点的度数-1，所以将点加入到，下一个list
                    classifyByCounts = insertList(classifyByCounts,i - 2,b);
                }
                else
                {
                    classifyByCounts.set(i ,firstList);
                    classifyByCounts = insertList(classifyByCounts,i - 1,a);
                    classifyByCounts = insertList(classifyByCounts,i - 1,b);
                }
                bufferedOutputStream.write((a + "\t" + b + "\n").getBytes());
                bufferedOutputStream.flush();
            }
            else
                i --;//下一个list是指度小1的list
        }
    }

    public void outPutDegreeCentrality(Point[] points) throws IOException {
        initOutPut("_Degree_centrality");
        int[] outCounts = new int[points.length];
        int[] inCounts = new int[points.length];
        int[] totalCounts = new int[points.length];
        for(int i = 0 ; i < points.length ; i++)
            outCounts[i] = getOut_degree(points[i]);  //获取总出度
        for(int i = 0 ; i < points.length; i++)
            inCounts = getIn_degree(points[i],inCounts);//获取总入度
        for(int i = 0 ; i < points.length; i ++)
            totalCounts[i] = outCounts[i] + inCounts[i];//出入度之和
        for(int i = 0 ; i < points.length; i ++)
        {
            bufferedOutputStream.write((i + "\t" + ((double)totalCounts[i])/(points.length -1) + "\n").getBytes());
            bufferedOutputStream.flush();
        }

    }
}
