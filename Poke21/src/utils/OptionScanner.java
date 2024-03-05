package utils;

import java.util.Scanner;

public class OptionScanner implements Runnable{

    static Scanner scanner=new Scanner(System.in);

    static String in;

    @Override
    public void run() {
        System.out.println("scanner 已启动");

        while(true){
            String in = scanner.nextLine();
            System.out.println(in);
        }
    }
}
