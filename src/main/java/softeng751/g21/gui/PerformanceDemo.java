package softeng751.g21.gui;

import softeng751.g21.Measurements;
import softeng751.g21.executors.factors.FactorExecutorService;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.Timer;
import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.util.concurrent.atomic.AtomicBoolean;

public class PerformanceDemo extends JFrame {
    public static void main(String[] args) {
        new PerformanceDemo().setVisible(true);
    }

    public PerformanceDemo() throws HeadlessException {
        setSize(1024, 768);
        setLocationRelativeTo(null);
        toFront();
        setTitle("Performance demo");

        int targetFps = 60;
        RenderTracker tracker = new RenderTracker(targetFps);
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

        AtomicBoolean doNothing = new AtomicBoolean(false);

        JButton testButton = new JButton();
        testButton.setText("Test");
        testButton.addActionListener((event) -> {
            if (doNothing.getAndSet(true)) {
                return;
            }

            FactorExecutorService service = new FactorExecutorService();

            // Performance deteriorates after 58 FPS
            service.addThreadCountFactor(() -> tracker.getFps() <= 58);

            for (int i = 0; i < 1000; i++) {
                service.submit(Measurements::randomWork);
            }
        });

        // add(startWorkButton, BorderLayout.SOUTH);
        add(testButton, BorderLayout.SOUTH);

        add(new Bounce(), BorderLayout.CENTER);
    }
}
