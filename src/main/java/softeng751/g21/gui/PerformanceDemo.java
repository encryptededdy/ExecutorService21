package softeng751.g21.gui;

import softeng751.g21.Measurements;
import softeng751.g21.executors.factors.FactorExecutorService;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Consumer;

public class PerformanceDemo extends JFrame {

    private final RenderTracker tracker;

    public static void main(String[] args) {
        new PerformanceDemo().setVisible(true);
    }

    public PerformanceDemo() throws HeadlessException {
        setSize(1024, 768);
        setLocationRelativeTo(null);
        toFront();
        setTitle("Performance demo");

        int targetFps = 60;
        tracker = new RenderTracker(targetFps);
        add(tracker, BorderLayout.EAST);

        Label label = new Label();
        add(label, BorderLayout.NORTH);

        new Timer(100, (ActionEvent e) -> {
            label.setText("Fps is " + tracker.getFps());
        }).start();

        JButton startWorkButton = new JButton();
        startWorkButton.setText("Start Work");
        startWorkButton.addActionListener((evt) -> {
            System.out.println("Doing work");
            for (int i = 0; i < 100; i++) {
                Thread thread = new Thread(Measurements::randomWork);
                thread.setPriority(1);
                thread.start();
            }
        });

        JPanel buttons = new JPanel();

        Consumer<Boolean> setButtonsEnabled = (enabled) -> {
            for (int i = 0; i < buttons.getComponentCount(); i++) {
                buttons.getComponent(i).setEnabled(enabled);
            }
        };

        JButton dynamic = new JButton();
        dynamic.setText("Benchmark Dynamic Executor Service");
        dynamic.addActionListener((event) -> {
            setButtonsEnabled.accept(false);
            FactorExecutorService service = new FactorExecutorService();
            service.addThreadCountFactor(() -> tracker.getFps() <= 58);
            benchmark(service);
        });

        JButton cached = new JButton();
        cached.setText("Benchmark Cached Executor Service");
        cached.addActionListener((event) -> {
            setButtonsEnabled.accept(false);
            ExecutorService service = Executors.newCachedThreadPool();
            benchmark(service);
        });

        JButton fixed = new JButton();
        fixed.setText("Benchmark Fixed Thread Pool Service");
        fixed.addActionListener((event) -> {
            String input = JOptionPane.showInputDialog(PerformanceDemo.this, "How many threads in the pool?");
            int count;
            try {
                count = Integer.parseInt(input);
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(PerformanceDemo.this, "Invalid integer");
                return;
            }
            setButtonsEnabled.accept(false);
            ExecutorService service = Executors.newFixedThreadPool(count);
            benchmark(service);
        });

        buttons.add(dynamic);
        buttons.add(cached);
        buttons.add(fixed);

        add(buttons, BorderLayout.SOUTH);

        add(new Bounce(), BorderLayout.CENTER);
    }

    private void benchmark(ExecutorService service) {
        int tasks = 100;

        List<Future> futures = new ArrayList<>(tasks);

        long start = System.currentTimeMillis();

        for (int i = 0; i < tasks; i++) {
            Future<?> future = service.submit(Measurements::randomWork);
            futures.add(future);
        }

        new Thread(() -> {
            for (Future future : futures) {
                try {
                    future.get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }

            long end = System.currentTimeMillis();
            System.out.println("Time taken is " + (end - start) + "ms");

            System.exit(0);
        }).start();
    }
}
