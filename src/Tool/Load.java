package Tool;


import java.io.*;
import java.util.Scanner;

public class Load {

    public static final String REGEX = "\t";

    public  Point[] Loading() throws IOException {
        System.out.println("Please type file path");
        Scanner scanner = new Scanner(System.in);
        String path = scanner.nextLine();
        System.out.println("Get the Path!!");
        File file = new File(path);
        if(!file.exists() || file.isDirectory())
        {
            System.out.println("Please Check your File!!");
            return null;
        }
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        Point[] points = new Point[getLength(bufferedReader) + 1];
        points = initPoints(points);
        bufferedReader.close();
        fileReader.close();
        points = addToArray(new BufferedReader(new FileReader(file)),points);
        return points;

    }

    private int getLength(BufferedReader bufferedReader) throws IOException {
        int max = 0;
        String temp = bufferedReader.readLine();
        String[] ss;
        while(temp != null)
        {
            ss = temp.split(REGEX);
            if(Integer.parseInt(ss[0]) > max)
                max = Integer.parseInt(ss[0]);
            if(Integer.parseInt(ss[1]) > max)
                max = Integer.parseInt(ss[1]);
            temp = bufferedReader.readLine();
        }
        return max;
    }

    private Point[] addToArray(BufferedReader bufferedReader,Point[] points) throws IOException {
        int m,n;
        float w;
        String temp = bufferedReader.readLine();
        while (temp != null)
        {
            String[] datas = temp.split(REGEX);
            m = Integer.parseInt(datas[0]);
            n = Integer.parseInt(datas[1]);
            w = Float.parseFloat(datas[2]);
            insertPoint(points[m],new Point(n,w,null));
            temp = bufferedReader.readLine();
        }
        return points ;
    }

    private Point[] initPoints(Point[] points)
    {
        for(int i = 0 ; i < points.length; i ++)
        {
            points[i] = new Point(i,0,null);
        }
        return  points;
    }

    private Point insertPoint(Point point,Point newPoint)
    {
        if(point.nextPoint == null)
            point.nextPoint = newPoint;
        else
            insertPoint(point.nextPoint,newPoint);
        return point;
    }
}
