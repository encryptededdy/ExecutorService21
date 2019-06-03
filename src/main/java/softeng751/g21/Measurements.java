package softeng751.g21;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Measurements {

    public static void main(String[] args) throws Exception {
//        if (true) {
//            int maxDiff = 0;
//            int[] x = new int[]{20005000, 20005000, 17334000, 20004000, 10128000, 20004000, 20006000, 20004000, 20007000, 20004000, 20004000, 20005000, 20005000, 20005000, 20005000, 20005000, 20250000, 20005000, 20006000, 20062000, 20004000, 20008000, 20008000, 20006000, 20007000, 20006000, 20005000, 20005000, 20007000, 20006000, 20006000, 20006000, 20005000, 20005000, 20005000, 24102000, 20006000, 20004000, 20004000, 20004000, 15880000, 20006000, 20004000, 20006000, 20004000, 20006000, 20005000, 20005000, 20003000, 20005000, 17504000, 20006000, 10006000, 20006000, 20005000, 20005000, 28117000, 20004000, 20006000, 20005000, 20004000, 30005000, 20005000, 20004000, 20004000, 20007000, 20004000, 20018000, 20005000, 20005000, 20005000, 20004000, 20005000, 20003000, 20005000, 20006000, 20007000, 20004000, 20005000, 20006000, 20007000, 20005000, 20007000, 20005000, 20004000, 20005000, 20004000, 20005000, 20003000, 19855000, 20007000, 20008000, 20004000, 20004000, 20005000, 10002000, 20006000, 10002000, 10213000, 20005000, 20005000, 20005000, 20006000, 20005000, 20006000, 20006000, 20005000, 20004000, 20005000, 20252000, 20005000, 20005000, 20005000, 20005000, 20005000, 20007000, 20005000, 20005000, 20003000, 20004000, 20001000, 20005000, 20005000, 20005000, 20006000, 20005000, 20005000, 20004000, 20005000, 20005000, 20004000, 20005000, 20005000, 20005000, 20005000, 20005000, 20005000, 20005000, 20007000, 20005000, 20004000, 20007000, 20006000, 20005000, 20033000, 15624000, 20004000, 20006000, 18920000, 17878000, 20005000, 20005000, 20002000, 20004000, 20006000, 20004000, 20005000, 20005000, 20003000, 20020000, 20006000, 24383000, 20006000, 20018000, 20006000, 20019000, 20007000, 20004000, 10002000, 20005000, 15021000, 20006000, 20005000, 20005000, 20004000, 19922000, 20250000, 20005000, 20005000, 20008000, 20004000, 20003000, 20005000, 20007000, 20006000, 20004000, 17146000, 20005000, 10007000, 14077000, 20005000, 20006000, 20006000, 20005000, 10006000, 20005000, 20004000, 10019000, 20005000, 19907000, 20006000, 20005000, 20003000, 20004000, 20006000, 20005000, 20004000, 20009000, 20005000, 20003000, 20005000, 20005000, 20006000, 20006000, 20004000, 29799000, 20006000, 20006000, 20006000, 20006000, 10007000, 20005000, 20005000, 20007000, 20004000, 20006000, 20007000, 20003000, 20006000, 20007000, 20005000, 20003000, 20005000, 20005000, 20004000, 20004000, 20004000, 20007000, 20006000, 20004000, 20005000, 20005000, 20006000, 20005000, 20006000, 20006000, 20008000, 20004000, 20007000, 20007000, 10004000, 20004000, 19859000, 20006000, 20006000, 20004000, 10002000, 19781000, 20005000, 20005000, 20005000, 20006000, 20005000, 20004000, 20005000, 20005000, 20006000, 20005000, 20006000, 20005000, 20004000, 20005000, 20002000, 19509000, 20005000, 20005000, 20007000, 11294000, 20006000, 20004000, 28145000, 20004000, 19828000, 20004000, 20005000, 20004000, 20006000, 20005000, 21233000, 20005000, 20006000, 20004000, 20005000, 20004000, 20005000, 20007000, 20005000, 20006000, 20004000, 20005000, 20004000, 10002000, 20005000, 20004000, 19757000, 20006000, 20005000, 20005000, 20003000, 20006000, 20004000, 20006000, 20006000, 20005000, 20006000, 20005000, 20004000, 20004000, 20006000, 20003000, 20005000, 20004000, 20005000, 20006000, 20006000, 20005000, 19778000, 20005000, 20005000, 20006000, 20006000, 20006000, 20004000, 20005000, 20004000, 20006000, 20005000, 10004000, 15773000, 20005000, 20005000, 20005000, 20005000, 20006000, 20004000, 20005000, 19762000, 10224000, 20005000, 14254000, 20006000, 20005000, 20005000, 15577000, 20005000, 16671000, 15594000, 20003000, 20006000, 20006000, 20005000, 13705000, 20006000, 20023000, 20005000, 10003000, 26788000, 20004000, 20005000, 20005000, 20006000, 16115000, 20007000, 20176000, 14566000, 20005000, 19889000, 16981000, 20006000, 20005000, 20005000, 20005000, 20002000, 20005000, 20006000, 20007000, 20006000, 20151000, 20005000, 20005000, 20005000, 20005000, 20005000, 20005000, 20006000, 20003000, 20004000, 20006000, 20004000, 20006000};
//            for (int i = 0; i < x.length; i++) {
//                for (int j = 0; j < x.length; j++) {
//                    int diff = Math.abs(x[j] - x[i]);
//                    if (diff > maxDiff) {
//                        maxDiff = diff;
//                    }
//                }
//            }
//            System.out.println(maxDiff);
//            return;
//        }

        int numThreads = Runtime.getRuntime().availableProcessors() * 20;
        List<Thread> pool = new ArrayList<>();

        for (int i = 0; i < numThreads; i++) {
            Thread thread = new Thread(Measurements::randomWork);
            pool.add(thread);
            thread.start();
        }

        ThreadMXBean bean = ManagementFactory.getThreadMXBean();

        if (!bean.isThreadCpuTimeSupported()) {
            throw new RuntimeException("No thread time support");
        }

        long[] oldTimes = new long[pool.size()];
        while (true) {

            long start = System.nanoTime();
            long[] times = new long[pool.size()];
            long[] dx = new long[pool.size()];

            for (int i = 0; i < pool.size(); i++) {
                Thread thread = pool.get(i);
                long tid = thread.getId();
                times[i] = bean.getThreadCpuTime(tid);

                dx[i] = times[i] - oldTimes[i];
            }

            System.out.println(Arrays.toString(oldTimes));
            System.out.println(Arrays.toString(times));
            System.out.println(Arrays.toString(dx));

            long sum = 0;
            for (long l : dx) {
                sum += l;
            }
            System.out.println(sum);
            long end = System.nanoTime();
            System.out.println((end - start) + " time to measure");

            System.out.println();
            Thread.sleep(1_000);

            oldTimes = times;
        }
    }

    public static double memes = 1.0;

    public static void randomWork() {
        work(100_000_000);
    }

    public static void work(int iterations) {
        long tid = Thread.currentThread().getId();

        System.out.println("[" + tid + "] Started");

        long start = System.currentTimeMillis();

        for (int i = 0; i < iterations; i++) {
            double x = 4.0;
            memes = x * Math.PI / -Math.acos(Math.PI) * Math.scalb(2, 5);
            if (Thread.interrupted()) {
                System.out.println("[" + tid + "] Interrupted");
                return;
            }
        }

        long end = System.currentTimeMillis();

        System.out.println("[" + tid + "] Completed in " + (end - start) + "ms");
    }

}
