public class ArrayDeque<T> {
    private int size;
    private T[] items;
    private int nextFirst;
    private int nextLast;
    private int length;

    public ArrayDeque(){
        length = 8;
        items = (T[]) new Object[length];
        size = 0;
        nextFirst = 0;
        nextLast = 1;
    }

    public boolean isEmpty(){
        if (size == 0){
            return true;
        }
        return false;
    }

    public void addFirst(T item){


        if (nextFirst != 0) {
            items[nextFirst] = item;
            nextFirst--;
            size++;
        }
        else{
            items[0] = item;
            nextFirst = length - 1;
            size++;
        }
    }
    public void addLast(T item){
        if (nextLast != length -1) {
            items[nextLast] = item;
            nextLast++;
            size++;
        }
        else{
            items[length-1] = item;
            nextLast = 0;
            size++;
        }
    }

    public int size(){
        return size;
    }

    public void printDeque() {
        if (nextFirst < nextLast) {
            for (int i = nextFirst + 1; i < nextLast; i++) {
                System.out.print(this.items[i]);
                System.out.print(" ");
            }
        } else {
            for (int i = nextFirst + 1; i < length; i++) {
                System.out.print(this.items[i]);
                System.out.print(" ");
            }
            for (int i = 0; i < nextLast; i++) {
                System.out.print(this.items[i]);
                System.out.print(" ");
            }
        }
    }

//    private T[] resizing(){
//        if (size > length){
//            T[] new_items = (T[]) new Object[3*length];
//            System.arraycopy(this.items,0,new_items,0,length);
//            length *= 3;
//            return new_items;
//        }
//        return this.items;
//    }



    public T removeFirst(){
        if (nextFirst != length - 1) {
            T output = this.items[nextFirst+1];
            this.items[nextFirst+1] = null;
            nextFirst++;
            size--;
            return output;
        }
        else {
            T output = this.items[0];
            this.items[0] = null;
            nextFirst = 0;
            size--;
            return output;
        }
    }

    public T removeLast(){
        if (nextLast != 0) {
            T output = this.items[nextLast-1];
            this.items[nextLast-1] = null;
            nextLast--;
            size--;
            return output;
        }
        else {
            T output = this.items[length-1];
            this.items[length-1] = null;
            nextLast = length-1;
            size--;
            return output;
        }
    }

    public T get(int index){
        return items[index];
    }

/**
    private static void main(String[] args) {
        ArrayDeque<String> a = new ArrayDeque<String>();
        a.addFirst("1111");
        a.addFirst("2222");
        a.addLast("3333");
        a.addLast("4444");
        a.addLast("5555");
        a.addLast("6666");
        a.addLast("7777");
        a.addLast("8888");
        a.removeFirst();
        a.removeLast();
        a.removeLast();
        a.removeLast();
        a.printDeque();
    }
*/
}