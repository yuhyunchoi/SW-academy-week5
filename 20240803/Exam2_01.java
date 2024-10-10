import java.util.Arrays;
import java.util.Random;

public class Exam2_01 {

    public static void insert(int[] a, int index, int num){
        int i = 0;
        if(a == null){
            throw new NullPointerException();
        }
        if(index < 0 || a.length < index){
            throw new IllegalArgumentException();
        }
        for(; i < index ; i++){
            if(num <= a[i]){
                break;
            }
        }

        System.arraycopy(a, i, a, i + 1, index - i);

        a[i] = num;
    }

    public static void main(String[] args) {
        int[] a = new int[10];
        Random random = new Random();

        for(int i = 0; i < a.length; i++){
            int x = random.nextInt(100);
            System.out.print(Arrays.toString(a) + " + " + x);
            insert(a, i, x);
            System.out.println(" = " + Arrays.toString(a));
                
        }

    }
}
