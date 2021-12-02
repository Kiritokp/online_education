import org.junit.Test;

/**
 * @author xiaobai
 * @create 2021-09-07 10:42
 */
public class Demo {
    public static void add() {
        int a=10;
        int b=10;
        method(a,b);
        System.out.println("a="+a);   //100
        System.out.println("b="+b);   //200
    }

    public static void method(int a ,int b){

    }

    @Test
    public void test(){
        Demo.add();
    }

    /**
     * 测试递归调用的次数
     */
    @Test
    public void binomial(){
        recursion(10);
    }

    private static int count=0;
    public static  int recursion(int k){
        count++;
        System.out.println("count1:"+count+"   k:"+k);
        if (k<=0){
            return 0;
        }
        return recursion(k-1)+recursion(k-2);
    }
}
