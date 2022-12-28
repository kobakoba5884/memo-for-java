package pure.java.thread.basic;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ThreadRunExample {
    public static void main(String[] args) {
        final int initThreadNum = 1;
        final int threadNum = 20;
        final long barNum = 100;
        String bar = Stream.generate(() -> "-").limit(barNum).collect(Collectors.joining());

        System.out.println(bar);
        System.out.println("Starting Runnable threads");

        IntStream.rangeClosed(initThreadNum, threadNum)
            .forEach((i) -> new Thread(new HeavyWorkRunnable(), String.format("HeavyWorkRunnable%d", i)).start());

        System.out.println(bar);
        System.out.println("Starting MyThreads");

        IntStream.rangeClosed(initThreadNum, threadNum)
            .forEach((i) -> new MyThread(String.format("myThread%s", i)).start());

        System.out.println(bar);
        System.out.println("Threads has been started");
    }
}
