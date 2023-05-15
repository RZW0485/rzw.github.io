package lab1.com.demo.sysmenu;

import java.util.Scanner;

public class SystemMenu {
    public void start(){
        boolean isEnd = true;
        int choose = 0;
        while (isEnd){
            System.out.println("****** 欢迎来到自助在线业务系统 ******");
            System.out.println("         \t1.注册");
            System.out.println("         \t2.登录");
            System.out.println("         \t3.退出");
            System.out.println("*****************************************");
            System.out.println("请输入操作功能");
            choose = getNum(1,3);
            switch (choose){
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    isEnd = false;
                    System.out.println("感谢使用本系统，系统关闭！");
            }

        }
    }
      public int getNum(int start ,int end){
          Scanner sc = new Scanner(System.in);
          int num = 0;
          while (true){
              while (true){
              String str = sc.next();
              try {
                  num = Integer.parseInt(str);
              } catch (NumberFormatException e) {
                  System.out.println("输入的不是数字！请重新输入");
                  continue;
              }
              break;
          }
        if (num>end||num<start) {
            System.out.println("无效操作，输入数字必须在" + start + "与" + end + "之间，请重新输入：");
        continue;
        }
        break;
        }
         return num;
      }
}

