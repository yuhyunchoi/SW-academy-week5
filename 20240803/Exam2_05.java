public class Exam2_05 {
    static class Node{
        int data;
        Node next;
        public Node(int data){
            this.data = data;
        }

        public Node(int data, Node next){
            this.data = data;
            this.next = next;
        }
    }


    public static Node insert(Node start, int data){
        if(start == null){
            return new Node(data);
        }

        if(data < start.data){
            return new Node(data, start);
        }

        Node p = start;
        while(p.next != null && p.next.data < data){
            p = p.next;
        }
        p.next = new Node(data, p.next);
        return start;        
    }

    public static Node delete(Node start, int data){
        if(start == null){
            return start;
        }
        if(start.data == data){
            start = start.next;
            return start;
        }

        Node p = start;

        while(p.next != null){
            if(p.next.data == data){
                p.next = p.next.next;
                break;
            }
            p = p.next;
        }

        return start;

    }

    public static void main(String[] args) {
        int[] datas = {22,55,44,66,33};
        Node start = new Node(datas[0]);
        for(int data : datas){
            start = insert(start, data);
            for(Node p = start ; p != null ; p = p.next){
                System.out.print(p.data + " ");
            }
            System.out.println();
        }
        start = delete(start, 44);
        for(Node p = start; p != null; p = p.next){
            System.out.print(p.data + " ");
        }
    }
}
