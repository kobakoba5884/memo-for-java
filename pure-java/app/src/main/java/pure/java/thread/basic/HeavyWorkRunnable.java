package pure.java.thread.basic;

public class HeavyWorkRunnable implements Runnable{
    @Override
    public void run() {
        Thread currentThread = Thread.currentThread();
        int threadSleepTime = 1000;

        System.out.println("Doing heavy processing - start %s (state : %s, priority : %d)"
            .formatted(currentThread.getName(),currentThread.getState().toString() ,currentThread.getPriority()) );    
        
        try{
            Thread.sleep(threadSleepTime);
        }catch(InterruptedException e){
            System.err.println(e.getMessage());
        }

        System.out.println("Doing heavy processing - end %s".formatted(currentThread.getName()));

    }
}
