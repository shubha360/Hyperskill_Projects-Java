import java.util.*;

public class Main {

    public static void main(String[] args) {        
        Queue<Integer> queue = new ArrayDeque<>(Arrays.asList(1, 2, 3, 4));

        // write your code here
        queue.offer(5);
        queue.poll();
        queue.poll();

        System.out.println(queue);
    }
}