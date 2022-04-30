package atmdemo;

/*
 * 模拟ATM系统
 */
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class ATMSystem {
    public static void main(String[] args) {
        //1.定义一个账号类
        //2.定义一个集合容器，复制以后负责存储全部的账户对象,进行相关的业务
        ArrayList<Account> accounts = new ArrayList<>();
        //3.展示系统主页面
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("=================ATM系统=======================");
            System.out.println("1.登录账户");
            System.out.println("2.注册账户");

            System.out.println("请选择您的操作：");
            int command = sc.nextInt();
            switch (command){
                case 1:
                    //登录账户
                    login(accounts,sc);
                    break;
                case 2:
                    //注册账户
                    register(accounts,sc);
                    break;
                default:
                    System.out.println("您输入操作指令不存在~~~~~~~~");
            }
        }
    }

    /**
     * 用户登录功能
     * @param accounts
     * @param sc
     */
    private static void login(ArrayList<Account> accounts, Scanner sc) {
        System.out.println("================账号登录页面=====================");
        //1.判断账户集合中是否存在账户，如果不存在账户，登录功能就不能实现
        if(accounts.size() == 0){
            System.out.println("当前无账户，请注册账户");
            return;
        }
        //2.正式进入登录操作
        while (true) {
            System.out.println("请输入您的登录卡号：");
            String cardId = sc.next();
            //3.判断卡号是否存在，根据卡号去账户集合中查找账户对象
            Account acc = getAccountByCardId(cardId, accounts);
            if (acc != null){
                //卡号存在
                while (true) {
                    //4.让用户认证密码
                    System.out.println("请输入登录密码：");
                    String password = sc.next();
                    if(acc.getPassword().equals(password)){
                        //密码正确，登录成功
                        System.out.println("恭喜您，登录成功了" + acc.getUsername() +"");
                        //展示登录成功后的功能
                        showUserCommond(sc,acc,accounts);
                        return; //退出
                    }else {
                        //密码错误，登录失败
                        System.out.println("您输入的密码有误，请重新输入");
                    }
                }
            }else {
                //卡号不存在
                System.out.println("对不起，您输入的卡号不存在~~");
            }
        }
    }
    /*
    用户登录成功后的功能
     */
    private static void showUserCommond(Scanner sc, Account acc,ArrayList<Account> accounts) {
        while (true) {
            System.out.println("=================用户功能页面========================");
            System.out.println("1.展示信息：");
            System.out.println("2.存款：");
            System.out.println("3.取款：");
            System.out.println("4.转账：");
            System.out.println("5.修改密码：");
            System.out.println("6.退出：");
            System.out.println("7.注销账户：");
            System.out.println("请选择您要执行的操作：");
            int commond = sc.nextInt();
            switch (commond){
                case 1:
                    //展示当前账户信息：
                    showAccount(acc);
                    break;
                case 2:
                    //存款
                    depositMoney(acc,sc);
                    break;
                case 3:
                    //取款
                    drowMoney(acc,sc);
                    break;
                case 4:
                    //转账
                    transferMoney(sc, acc ,accounts);
                    break;
                case 5:
                    //修改密码
                    updatePassword(sc,acc);
                    return;
                case 6:
                    //退出
                    return; //退出当前页面
                case 7:
                    //注销账户
                    if(deleteAccount(sc,acc,accounts)){
                        //销户成功，回到首页
                        return;
                    }else{
                        //没有销户成功
                    break;
                    }
                default:
                    System.out.println("您输入的命令不存在，请重新输入：");
            }
        }
    }

    /**
     * 销户
     * @param sc
     * @param acc
     * @param accounts
     */
    private static boolean deleteAccount(Scanner sc, Account acc, ArrayList<Account> accounts) {
        System.out.println("===============销户===========================");
        System.out.println("您真的要销户吗？？ Y/N");
        String yes = sc.next();
        switch (yes){
            case "Y":
                //销户
                if (acc.getMoney() > 0){
                    System.out.println("您的账户中还有钱，建议您取完再销户");

                }else {
                    accounts.remove(acc);
                    System.out.println("销户完成！！");
                    return true;
                }
                break;
            default:
                System.out.println("账户继续保留");
                break;
        }
        return false;
    }

    /**
     * 修改用户密码
     * @param sc
     * @param acc
     */

    private static void updatePassword(Scanner sc, Account acc) {
        System.out.println("===============用户密码修改===========================");
        while (true) {
            System.out.println("请您输入当前密码");
            String password = sc.next();
            if(acc.getPassword().equals(password)){
                while (true) {
                    //输入的密码与当前密码一样,可以进行修改密码了
                    System.out.println("请您输入新密码~~");
                    String newPassword = sc.next();

                    System.out.println("请您再次输入密码！");
                    String okPassword = sc.next();

                    if (newPassword.equals(okPassword)){
                        //两次密码输入一致，更新密码
                        acc.setPassword(okPassword);
                        System.out.println("密码修改成功~");
                        return;
                    }else {
                        //两次密码不一样，重新输入
                        System.out.println("两次密码不一致，请重新输入~~");
                    }
                }
            }else {
                //密码输入错误
                System.out.println("您输入的密码有误，请重新输入！");
            }
        }

    }

    /**
     * 转账功能
     * @param sc
     * @param acc
     * @param accounts
     */
    private static void transferMoney(Scanner sc, Account acc, ArrayList<Account> accounts) {
        System.out.println("===============用户转账操作===========================");
        //1.判断是否足够两个账户
        if(accounts.size() > 2){
            System.out.println("当前系统账户不足两个，请再去开一个账户吧！！");
            return; //结束当前方法
        }
        //2.判断自己账户是否有钱
        if(acc.getMoney() == 0){
            System.out.println("您自己都没钱，就别转了。去存点钱再来转吧！");
            return; //结束当前方法
        }
        while (true) {
            //3.开始转账
            System.out.println("请输入对方的卡号：");
            String cardId = sc.next();

            //不能自己给自己转账
            if(cardId.equals(acc.getCardId())){
                System.out.println("您不能自己给自己进行转账！请重新输入账号");
                continue;
            }

            //判断这个卡号是否存在，根据这个卡号去查询对方账户对象
            Account account = getAccountByCardId(cardId, accounts);
            if (account == null){
                System.out.println("您输入的账户不存在！");
            }else {
                //账号存在
                //判断对方的姓氏
                String username = account.getUsername();
                String tip = "*" + username.substring(1);
                System.out.println("请输入["+ tip +"]的姓氏~！");
                String prename = sc.next();
                //判断输入的姓氏是否正确
                if(username.startsWith(prename)){
                    while (true) {
                        //认证成功
                        System.out.println("请您输入转账金额:");
                        double money = sc.nextDouble();
                        //判断余额是否足够
                        if(money > acc.getMoney()){
                            System.out.println("您的账号余额不足");
                        }else{
                            //条件满足，可以进行转账了
                            acc.setMoney(acc.getMoney() - money);
                            account.setMoney(account.getMoney() + money);
                            System.out.println("转账成功！");
                            return;
                        }
                    }
                }else {
                    System.out.println("您输入的信息有误！");
                }
            }
        }
    }

    private static void drowMoney(Account acc, Scanner sc) {
        System.out.println("===============用户存钱操作===========================");
        if (acc.getMoney() < 100){
            System.out.println("您当前账户余额小于100元，不能取钱~");
            return;
        }
        //提示用户输入取钱金额
        while (true) {
            System.out.println("请输入取款金额：");
            double money = sc.nextDouble();
            //判断去取款金额是否满足要求
            if(money > acc.getQuotamoney()){
                //所取金额超过限额
                System.out.println("您所取的金额超过本次取款额度！！");
            }else {
                //没有超出限额
                //判断所取金额是否超过总余额
                if(money > acc.getMoney()){
                    //提示余额不足
                    System.out.println("您的账户余额不足~");
                }else {
                    //取款成功
                    System.out.println("恭喜您，取钱"+ money +"成功");
                    acc.setMoney( acc.getMoney() - money);
                    //更新余额，展示信息
                    showAccount(acc);
                    return;
                }
            }
        }
    }
    private static void depositMoney(Account acc, Scanner sc) {
        System.out.println("===============用户存钱操作===========================");
        System.out.println("请输入存储金额：");
        double money = sc.nextDouble();

        //余额 = 原来的 + 新存入的
        acc.setMoney(acc.getMoney() + money);
        System.out.println("最新的账户信息如下：");
        showAccount(acc);
    }

    /**
     * 展示当前用户信息
     * @param acc
     */
    private static void showAccount(Account acc) {
        System.out.println("================当前账户信息=====================");
        System.out.println("账号:" + acc.getCardId());
        System.out.println("用户名：" + acc.getUsername());
        System.out.println("账号余额：" + acc.getMoney());
        System.out.println("当前限额：" + acc.getQuotamoney());
    }

    /**
     * 系统开户功能
     * @param accounts   接收的账户集合
     */
    private static void register(ArrayList<Account> accounts,Scanner sc) {
        System.out.println("================账号开户页面=====================");
        //1.创建一个账户对象，用于后期封装账户信息
        Account account = new Account();

        //2.录入当前这个账户的信息，注入到账户对象中去
        System.out.println("请您输入账户的用户名：");
        String userName = sc.next();
        account.setUsername(userName);

        while (true) {
            System.out.println("请您输入账户密码：");
            String passWord = sc.next();
            System.out.println("请确认您的密码：");
            String okpassWord = sc.next();

            if (okpassWord.equals(passWord)){
                //密码输入正确，可以注入给对象账户
                account.setPassword(okpassWord);
                break; //密码录入成功，退出死循环
            }else {
                System.out.println("您输入的两次密码不一致，请重新确认");
            }
        }
        //设置账户限额
        System.out.println("请您输入当次限制额度：");
        Double quotamoney = sc.nextDouble();
        account.setQuotamoney(quotamoney);

        //为账户随机生成一个与其他账户不重复的账户
        String cardId = getRandomCardId(accounts);
        account.setCardId(cardId);

        //3.把账户对象添加到账户集合中去

        accounts.add(account);
        System.out.println("恭喜您"+ userName +"，成功创建了一个账户"+ cardId +"，请牢记您的账号！！！");
    }

    /**
     * 为用户生成一个8为卡id
     * @return
     */
    private static String getRandomCardId(ArrayList<Account> accounts) {
        //生成一个8位的cardId
        Random r = new Random();
        while (true) {
            String cardId = "";
            for (int i = 0; i < 16; i++) {
                cardId += r.nextInt(10);
            }

            //判断生成的卡号是否存在
            Account acc = getAccountByCardId(cardId, accounts);
            if (acc == null){
                //账户创建成功
                return cardId;
            }
            return null;
        }
    }
    /**
     * 根据卡号查询一个账户对象
     * @param cardId
     * @param accounts
     * @return
     */
    private static Account getAccountByCardId(String cardId,ArrayList<Account> accounts){
        for (int i = 0; i < accounts.size(); i++) {
            Account acc = accounts.get(i);
            if(acc.getCardId().equals(cardId)){
                return acc;
            }
        }
        return null;  //查无此账号
    }
}

