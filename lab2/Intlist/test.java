public class test {
    public static void main(String[] args) {
        IntList A = new IntList(1,null);
        A.rest = new IntList(2,null);
        A.rest.rest = new IntList(3,null);

        IntList B = new IntList(4,null);
        B.rest = new IntList(5,null);
        B.rest.rest = new IntList(6,null);


        IntList.dcatenate(A,B);
        System.out.println(A);
    }
}
