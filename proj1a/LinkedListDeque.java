public class LinkedListDeque<GenType> {
    public StuffNode sentinel;
    public int size;

    public class StuffNode {
        public StuffNode prev;
        public GenType item;
        public StuffNode next;

        public StuffNode(StuffNode p, GenType i, StuffNode n) {
            prev = p;
            item = i;
            next = n;
            if (prev != null) {
                p.next = this;
            }
        }
    }

    public LinkedListDeque(GenType x){
        sentinel = new StuffNode(null, x,null);
    }
    public LinkedListDeque(){
        sentinel =new StuffNode(null,null,null);
    }



    public StuffNode connectFirst(){
        return sentinel.next;
    }
    public StuffNode connnectLast(){
        if (sentinel.prev != null) {
            return sentinel.prev;
        }return sentinel;
    }



    public void addFirst(GenType x){
//        if (sentinel == null){
//            sentinel = new StuffNode(null,"Start",null);
//            return;
//        }
        StuffNode p = sentinel;
        if (p.next != null){
            p.next.prev = new StuffNode(p,x,p.next);
            size++;
        }
        else {
            p.next = new StuffNode(p, x, p);
            p.prev = p.next;
            size++;
        }
    }

    public void addLast(GenType x){
//        if (sentinel == null){
//            sentinel = new StuffNode(null,100,null);
//            return;
//        }
        StuffNode p = sentinel;
        p.prev = new StuffNode(connnectLast(), x, p);
        size++;
    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        if (this.size == 0) {
            return true;
        }
        else{
            return false;
        }
    }

    public void printDeque(){
        StuffNode p = sentinel;
        while ((p.next != null)&(p.next != sentinel)){
            p = p.next;
            System.out.print(p.item+" ");
        }
    }

    public GenType removeFirst(){
        StuffNode p = sentinel;
        p.next.next.prev = p;
        GenType output = p.next.item;
        p.next = p.next.next;
        size--;
        return output;
    }


    


//    public static void main(String[] args) {
//        LinkedListDeque<String> a = new LinkedListDeque<String>();
//        a.addLast("str1");
//        a.addLast("str2");
//        a.addLast("str3");
////        a.addFirst(1);
////        a.addFirst(-11);
//    }
}
