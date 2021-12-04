public class LinkedListDeque<T> {
    private StuffNode sentinel;
    private int size;

    private class StuffNode {
        private StuffNode prev;
        private T item;
        private StuffNode next;

        public StuffNode(StuffNode p, T i, StuffNode n) {
            prev = p;
            item = i;
            next = n;
            if (prev != null) {
                p.next = this;
            }
        }
    }

    public LinkedListDeque() {
        sentinel = new StuffNode(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next =sentinel;
        size = 0;
    }




    private StuffNode connectFirst() {
        return sentinel.next;
    }

    private StuffNode connnectLast() {
        if (sentinel.prev != null) {
            return sentinel.prev;
        }
        return sentinel;
    }


    public void addFirst(T x) {
//        if (sentinel == null){
//            sentinel = new StuffNode(null,"Start",null);
//            return;
//        }
        StuffNode p = sentinel;
        if (p.next != null) {
            p.next.prev = new StuffNode(p, x, p.next);
            size++;
        } else {
            p.next = new StuffNode(p, x, p);
            p.prev = p.next;
            size++;
        }
    }

    public void addLast(T x) {
//        if (sentinel == null){
//            sentinel = new StuffNode(null,100,null);
//            return;
//        }
        StuffNode p = sentinel;
        p.prev = new StuffNode(connnectLast(), x, p);
        size++;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        if (this.size == 0) {
            return true;
        } else {
            return false;
        }
    }

    public void printDeque() {
        StuffNode p = sentinel;
        while ((p.next != null) & (p.next != sentinel)) {
            p = p.next;
            System.out.print(p.item + " ");
        }
    }

    public T removeFirst() {
        if (size == 0){
            return null;
        }
        StuffNode p = sentinel;
        p.next.next.prev = p;
        T output = p.next.item;
        p.next = p.next.next;
        size--;
        return output;
    }

    public T removeLast() {
        if (size == 0){
            return null;
        }
        StuffNode p = sentinel;
        p.prev.prev.next = p;
        T output = p.prev.item;
        p.prev = p.prev.prev;
        size--;
        return output;
    }

    public T get(int index){
        if (size<index){
            return null;
        }

        StuffNode p = sentinel;
        for (int i = 0; i < index; i++){
            p = p.next;
        }
        return p.next.item;
    }

    public T getRecursive(int index) {
        if (size < index) {
            return null;
        }
        return getRecursive(sentinel.next, index);
    }
    private T getRecursive(LinkedListDeque<T>.StuffNode a, int diff){
        if (diff == 0){
            return a.item;
        }
        else{
            return getRecursive(a.next, diff - 1);
        }
    }

/**
    public static void main(String[] args) {
        LinkedListDeque<String> a = new LinkedListDeque<>();
        a.addFirst("second");
        a.addFirst("first");
        a.addLast("later");
        a.addLast("last");
        a.removeFirst();
        a.removeLast();
        System.out.println(a.get(2));
        System.out.println(a.getRecursive(2));
    }
*/

}