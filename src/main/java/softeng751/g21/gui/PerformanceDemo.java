package softeng751.g21.gui;

import softeng751.g21.Measurements;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.Timer;
import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
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
                Thread thread = new Thread(Measurements::work);
                thread.setPriority(1);
                thread.start();
            }
        });

        AtomicBoolean doNothing = new AtomicBoolean(false);

        JButton measureButton = new JButton();
        measureButton.setText("Measure");
        measureButton.addActionListener((evt) -> {
            if (doNothing.getAndSet(true)) {
                return;
            }

            new Thread(() -> {
                ArrayList<Thread> threads = new ArrayList<>();

                int noThreads = 0;
                int ptick = 0;
                int tick = 0;

                while (true) {
                    System.out.println("Tick " + ptick);

                    tick++;
                    if (tick % (noThreads <= 50 ? 5 : 15) == 0) {
                        Thread thread = new Thread(Measurements::work);
                        threads.add(thread);
                        thread.start();
                        noThreads++;
                        System.out.println("Created " + noThreads + " threads");
                    }

                    if (tracker.getFps() <= 59) {
                        ptick++;
                        if (ptick >= 25) {
                            System.out.println("Created " + noThreads + " threads before performance deteriorated");
                            break;
                        }
                    } else {
                        ptick = Math.max(0, ptick - 1);
                    }

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                try {
                    Thread.sleep(10_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                ptick = 0;

                while (true) {
                    System.out.println("Tick " + ptick);

                    tick++;
                    if (tick % 25 == 0) {
                        threads.remove(0).interrupt();
                        noThreads--;
                    }

                    if (tracker.getFps() >= 59) {
                        ptick++;
                        if (ptick >= 25) {
                            break;
                        }
                    } else {
                        ptick = Math.max(0, ptick - 1);
                    }

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                System.out.println("Completed, decided on " + noThreads + " for optimal performance");
            }).start();
        });

        // add(startWorkButton, BorderLayout.SOUTH);
        add(measureButton, BorderLayout.SOUTH);

        add(new Bounce(), BorderLayout.CENTER);
    }
}
