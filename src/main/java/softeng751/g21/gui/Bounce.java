package softeng751.g21.gui;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class Bounce extends JPanel {

    private final List<Paintable> paintables = new ArrayList<>();

    public Bounce() {
        int timePerFrame = 1000 / 60;
        new Timer(timePerFrame, (ActionEvent e) -> repaint()).start();

        for (int i = 0; i < 100; i++) {
            paintables.add(new Shape());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0, size = paintables.size(); i < size; i++) {
            Paintable paintable = paintables.get(i);
            paintable.paint(g, this);
        }
    }

}
