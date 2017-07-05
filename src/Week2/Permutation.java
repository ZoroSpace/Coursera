package Week2;

import edu.princeton.cs.algs4.StdIn;

/**
 * Created by Zoro on 17-4-30.
 */
public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();
        int i = Integer.parseInt(args[0]);
        while (!StdIn.isEmpty()) {
            randomizedQueue.enqueue(StdIn.readString());
        }
        for (int j = 0; j < i; j++) {
            System.out.println(randomizedQueue.dequeue());
        }
    }

}
