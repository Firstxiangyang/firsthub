package com.company.zero;

public class ThreedayDemo1 {
    public static void main(String[] args){

        int sum = 0;
        for (int i = 0; i < 10; i++) {
            if(i % 2 == 1){
                System.out.print(i + " \t"); // 输出1 -10之间的奇数
                sum += i;


            }
        }
        System.out.println("");
        System.out.println(sum);  //输出1-10之间奇数的和

        int sum1 = 0;
        for (int j = 100; j <= 999; j++) {

            // j = 156
            int ge = j % 10;

            int shi = j / 10 % 10;

            int bai =   j / 100;

            if(ge*ge*ge + shi*shi*shi + bai*bai*bai == j){
                System.out.println(j);
                sum1 ++;
            }
        }
        System.out.println(sum1);
    }
}
