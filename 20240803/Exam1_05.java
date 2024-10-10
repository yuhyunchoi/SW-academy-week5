public class Exam1_05 {
    
    public static int search(int[] a, int num){
        if(a == null){
            throw new NullPointerException();
        }

        int start = 0;
        int end = a.length;
        while(start < end){
            int middle = (start + end)/ 2;
            if(a[middle] == num){
                return middle;
            }
            if(a[middle] > num){
                end = middle;
            }
            else{
                start = middle + 1;
            }
            
        }
        return -1;
    }

    public static void print(String str, int[] a){
        System.out.print(str + " :");
        for(int arr : a){
            System.out.print(" " + arr);
        }
        System.out.println();
    }
    
    public static void main(String[] args) {
        int[] a = { 22, 33, 44, 55, 66, 77, 88, 99 };
        Exam1_05.print("a", a);
        System.out.println("search(a, 44): " + search(a, 44));
        System.out.println("search(a, 50): " + search(a, 50));
        System.out.println("search(a, 77): " + search(a, 77));
        System.out.println("search(a, 100): " + search(a, 100));
    }
    
}
