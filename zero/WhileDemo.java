package com.company.zero;

import java.util.Scanner;

public class WhileDemo {
    public static void main(String[] args) {

        //定义变量
        double peak = 8848860;
        double paper = 0.1;
        //定义一个变量接受循环次数
        int count = 0 ;
        //定义while循环
        while(paper < peak){
            paper *= 2;
            count ++ ;
        }
        System.out.println(count);  //输出循环次数
        System.out.println(paper);   // 输出折叠多少次后的高度

        //1.定义一个正确密码
        int pwd = 520 ;
        //2.定义一个循环，让用户输入密码，如果输入不正确则一直提醒
        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.println("请输入密码");
            int pwd1 = sc.nextInt();
            if (pwd == pwd1){
                System.out.println("密码正确");
                break;
            }else {
                System.out.println("密码错误！！！");
            }
        }
    }
}
