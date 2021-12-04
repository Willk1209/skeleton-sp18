
public class ArrayDeque<T> {

    private T[] items;
    private int left;
    private int right;
    private int capacity = 8;

    public ArrayDeque() {
        items = (T[]) new Object[capacity];
        left = right = 0;
    }

    /** Adds an item of type T to the front of the deque. */
    public void addFirst(T item) {
        if (isFull()) {
            resize((int) (capacity * 1.5));
        }
        left = (left - 1 + capacity) % capacity;
        items[left] = item;
    }

    /** Adds an item of type T to the back of the deque. */
    public void addLast(T item) {
        if (isFull()) {
            resize((int) (capacity * 1.5));
        }
        items[right] = item;
        right = (right + 1 + capacity) % capacity;
    }

    /** Returns true if deque is empty, false otherwise. */
    public boolean isEmpty() {
        return left == right;
    }

    /** Returns the number of items in the deque. */
    public int size() {
        return (right - left + capacity) % capacity;
    }

    /** Prints the items in the deque from first to last, separated by a space. */
    public void printDeque() {
        if (left < right) {
            for (int i = left; i < right; i++) {
                if (i == right - 1) {
                    System.out.println(items[i]);
                    break;
                }
                System.out.print(items[i] + " ");
            }
        } else if (left > right) {
            for (int i = left; i < capacity; i++) {
                System.out.print(items[i] + " ");
            }
            for (int i = 0; i < right; i++) {
                if (i == right - 1) {
                    System.out.println(items[i]);
                    break;
                }
                System.out.print(items[i] + " ");
            }
        }
    }

    /**
     * Removes and returns the item at the front of the deque. If no such item
     * exists, returns null.
     */
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        T res = items[left];
        left = (left + 1) % capacity;
        if (isLowUsageRate()) {
            resize((int) (capacity * 0.5));
        }
        return res;
    }

    /**
     * Removes and returns the item at the back of the deque. If no such item
     * exists, returns null.
     */
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        right = (right - 1 + capacity) % capacity;
        T res = items[right];
        if (isLowUsageRate()) {
            resize((int) (capacity * 0.5));
        }
        return res;
    }

    /**
     * Gets the item at the given index, where 0 is the front, 1 is the next item,
     * and so forth. If no such item exists, returns null. Must not alter the deque!
     */
    public T get(int index) {
        if (index < 0 || index >= size() || isEmpty()) {
            return null;
        }
        if (left < right) {
            return items[index + left];
        } else if (left > right) {
            if (index + left < capacity) {
                return items[index + left];
            } else {
                return items[(index + left) % capacity];
            }
        }
        return null;
    }

    private boolean isFull() {
        return size() == capacity - 1;
    }

    private boolean isLowUsageRate() {
        return capacity >= 16 && size() / (double) capacity < 0.25;
    }

    private void resize(int newSize) {
        T[] newArray = (T[]) new Object[newSize];

        int size = size();
        if (left < right) {
            for (int i = left, j = 0; i < right && j < size; i++, j++) {
                newArray[j] = items[i];
            }
        } else if (left > right) {
            int j = 0;
            for (int i = left; j < capacity - left; i++, j++) {
                newArray[j] = items[i];
            }
            for (int i = 0; j < size; i++, j++) {
                newArray[j] = items[i];
            }
        }
        left = 0;
        right = size;
        items = newArray;
        capacity = newSize;
    }

}
//    private int size;
//    private T[] items;
//    private int nextFirst;
//    private int nextLast;
//    private int length;
//
//    public ArrayDeque(){
//        length = 8;
//        items = (T[]) new Object[length];
//        size = 0;
//        nextFirst = 0;
//        nextLast = 1;
//    }
//
//    public boolean isEmpty(){
//        if (size == 0){
//            return true;
//        }
//        return false;
//    }
//
//    public void addFirst(T item){
//        if (size == length){
//            this.resizing();
//            items[nextFirst] = item;
//
//        }
//
//        if (nextFirst != 0) {
//            items[nextFirst] = item;
//            nextFirst--;
//            size++;
//        }
//        else{
//            items[0] = item;
//            nextFirst = length - 1;
//            size++;
//        }
//    }
//    public void addLast(T item){
//        if (nextLast != length -1) {
//            items[nextLast] = item;
//            nextLast++;
//            size++;
//        }
//        else{
//            items[length-1] = item;
//            nextLast = 0;
//            size++;
//        }
//    }
//
//    public int size(){
//        return size;
//    }
//
//    public void printDeque() {
//        if (nextFirst < nextLast) {
//            for (int i = nextFirst + 1; i < nextLast; i++) {
//                System.out.print(this.items[i]);
//                System.out.print(" ");
//            }
//        } else {
//            for (int i = nextFirst + 1; i < length; i++) {
//                System.out.print(this.items[i]);
//                System.out.print(" ");
//            }
//            for (int i = 0; i < nextLast; i++) {
//                System.out.print(this.items[i]);
//                System.out.print(" ");
//            }
//        }
//    }
//
//    private void resizing(){
//        if (size == length){
//            nextFirst = length;
//            nextLast = length + 1;
//            T[] new_items = (T[]) new Object[3*length];
//            System.arraycopy(this.items,0,new_items,0,length);
//            length *= 3;
//            this.items = new_items;
//        }
//
//    }
//
//
//
//    public T removeFirst(){
//        if (nextFirst != length - 1) {
//            T output = this.items[nextFirst+1];
//            this.items[nextFirst+1] = null;
//            nextFirst++;
//            size--;
//            return output;
//        }
//        else {
//            T output = this.items[0];
//            this.items[0] = null;
//            nextFirst = 0;
//            size--;
//            return output;
//        }
//    }
//
//    public T removeLast(){
//        if (nextLast != 0) {
//            T output = this.items[nextLast-1];
//            this.items[nextLast-1] = null;
//            nextLast--;
//            size--;
//            return output;
//        }
//        else {
//            T output = this.items[length-1];
//            this.items[length-1] = null;
//            nextLast = length-1;
//            size--;
//            return output;
//        }
//    }
//
//    public T get(int index){
//        return items[index];
//    }