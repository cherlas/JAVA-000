import java.util.concurrent.*;

public class ThreadDemo {
    private static String result2 = "";
    private static String result3 = "";
    private static String result4 = "";
    private static String result5 = "";

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        method1();
        method2();
        method3();
        method4();
        method5();
        method6();
    }

    private static void method1() throws InterruptedException, ExecutionException {
        FutureTask<String> futureTask = new FutureTask<>(() -> "test1");
        futureTask.run();
        String result = futureTask.get();
        System.out.println(result);
    }

    private static void method2() throws InterruptedException {
        new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignored) {
            }
            result2 = "test2";
        }).start();
        Thread.sleep(300);
        System.out.println(result2);
    }

    private static void method3() throws InterruptedException {
        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
            result3 = "test3";
        }).start();
        while (result3.isEmpty()) {
            Thread.sleep(200);
        }
        System.out.println(result3);
    }

    private static void method4() throws InterruptedException {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
            result4 = "test4";
        });
        thread.start();
        thread.join();
        System.out.println(result4);
    }

    private static void method5() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        System.out.println(executorService.submit(() -> "test5").get());
    }

    private static void method6() throws InterruptedException {
        CountDownLatch count = new CountDownLatch(1);
        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
            result5 = "test5";
            count.countDown();
        }).start();
        count.await();
        System.out.println(result5);
    }
}
