package Tool;

import java.io.IOException;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) throws IOException {
        boolean exit = false;
        while(!exit)
        {
            Load load = new Load();
            Point[] points = load.Loading();
            System.out.println("Deal Success!");
            OutPut outPut = new OutPut();
            System.out.println("Please choose output mode:\n" +
                    "1.BZOutPut\n" +
                    "2.out-degreeOutPut\n" +
                    "3.in-degreeOutPut\n" +
                    "4.number of nodes with out-degree\n" +
                    "5.number of nodes with in-degree\n" +
                    "6.create new graph\n" +
                    "7.degree centrality\n" +
                    "0.exit");
            Scanner scanner = new Scanner(System.in);
            int i = Integer.parseInt(scanner.nextLine());
            switch (i)
            {
                case 1 :
                    outPut.outPutBZ(points);
                    System.out.println("BZ has outputted!!!");break;
                case 2:
                    outPut.outPutOut_degree(points);
                    System.out.println("out-degreeOutPut has outputted!!!");break;
                case 3:
                    outPut.outPutIn_degree(points);
                    System.out.println("in-degreeOutPut has outputted!!!");break;
                case 4:
                    outPut.outPutCountOfOut_degree(points);
                    System.out.println("number of nodes with out-degree has outputted");break;
                case 5:
                    outPut.outPutCountOfIn_degree(points);
                    System.out.println("number of nodes with in-degree has outputted");break;
                case 6:
                    outPut.outPutRebuild(points);
                    System.out.println("Rebuild programing");break;
                case 7:
                    outPut.outPutDegreeCentrality(points);
                    System.out.println("degree centrality has benn outputted");break;
                case 0:
                    exit = false;
                    System.out.println("Closed!!!");break;
                default:break;
            }
        }

    }
}
