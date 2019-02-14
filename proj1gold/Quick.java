public class Quick {
    public static void main(String args[]) {
        StudentArrayDeque<Integer> student = new StudentArrayDeque();
        student.addFirst(6);
        student.addFirst(7);
        student.addFirst(5);
        student.addFirst(7);
        student.addFirst(9);
        student.addFirst(7);
        student.addFirst(7);
        student.removeLast();// 6
        System.out.println(student.removeLast());// null
    }
}
