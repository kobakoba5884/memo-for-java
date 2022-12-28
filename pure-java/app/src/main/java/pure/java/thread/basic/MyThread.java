package pure.java.thread.basic;

public class MyThread extends Thread{

    public MyThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        Thread currentThread = Thread.currentThread();
        int threadSleepTime = 1000;

        System.out.println("MyThread  - start %s (state : %s, priority : %d)"
            .formatted(currentThread.getName(),currentThread.getState().toString() ,currentThread.getPriority()) );    
        
        try{
            Thread.sleep(threadSleepTime);
        }catch(InterruptedException e){
            System.err.println(e.getMessage());
        }

        System.out.println("MyThread  - end %s".formatted(currentThread.getName()));
    }
    
}
