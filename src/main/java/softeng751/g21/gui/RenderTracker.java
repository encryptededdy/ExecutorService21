package softeng751.g21.gui;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.event.ActionEvent;

public class RenderTracker extends JPanel {
    private long lastPaintMs;

    private long[] measurements = new long[100];

    private int measurementsIndex = 0;

    private int fps;

    public RenderTracker(int targetFps) {
        lastPaintMs = System.currentTimeMillis();
        // Setup redrawing
        int timePerFrame = 1000 / (targetFps + 5);
        new Timer(timePerFrame, (ActionEvent e) -> repaint()).start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        long timeMs = System.currentTimeMillis();
        long msToRender = timeMs - lastPaintMs;
        measurements[measurementsIndex] = msToRender;
        measurementsIndex = (measurementsIndex + 1) % measurements.length;
        lastPaintMs = timeMs;

        long sum = 0;
        for (int i = 0; i < measurements.length; i++) {
            sum += measurements[i];
        }
        sum /= measurements.length;
        fps = (int) (1000 / sum);
    }

    public int getFps() {
        return fps;
    }
}
