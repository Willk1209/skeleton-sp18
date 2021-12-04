public class SSList {
    public IntNode sentinel;
    public int size;

    public class IntNode {
        public IntNode prev;
        public int item;
        public IntNode next;

        public IntNode(IntNode p, int i, IntNode n) {
            prev = p;
            item = i;
            next = n;
            if (prev != null) {
                p.next = this;
            }
        }
    }

    public SSList(int x){
        sentinel = new IntNode(null, x,null);
    }

    public IntNode connectFirst(){
        return sentinel.next;
    }
    public IntNode connnectLast(){
        if (sentinel.prev != null) {
            return sentinel.prev;
        }return sentinel;
    }


//    public void addLast(int x){
//        if (sentinel == null){
//            sentinel = new IntNode(null,100,null);
//            return;
//        }
//        IntNode p = sentinel;
//        while((p.next != connectFirst())&(p.next != null)){
//            p = p.next;
//        }
//        p.next = new IntNode(p,x, connectFirst());
//        sentinel.prev = p.next;
//        size++;
//    }

    public void addFirst(int x){
        if (sentinel == null){
            sentinel = new IntNode(null,100,null);
            return;
        }
        IntNode p = sentinel;
        if (p.next != null){
            p.next.prev = new IntNode(p,x,p.next);
            size++;
        }
        else {
            p.next = new IntNode(p, x, p);
            p.prev = p.next;
            size++;
        }
    }

    public void new_addLast(int x){
        if (sentinel == null){
            sentinel = new IntNode(null,100,null);
            return;
        }
        IntNode p = sentinel;
        p.prev = new IntNode(connnectLast(), x, p);
        size++;
    }


    public static void main(String[] args) {
        SSList a = new SSList(999999);
        a.new_addLast(5);
        a.new_addLast(10);
        a.new_addLast(20);
        a.addFirst(1);
        a.addFirst(-11);
    }
}
