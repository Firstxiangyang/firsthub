package com.company.zero;

import java.util.Scanner;

public class ScannerDemo {
    public static void main(String[] args){
        //目标：学会使用键盘录入技术
        //1.得到一个键盘扫描对象

        Scanner sc = new Scanner(System.in);
        //2.调用sc对象的功能接受用户输入的数据\

        System.out.println("请输入年龄：");
        int age = sc.nextInt();

        System.out.println("你的年龄是："+age);


        System.out.println("请输入你的姓名");

        String name = sc.next();

        System.out.println("你的姓名是："+name);


    }
}
