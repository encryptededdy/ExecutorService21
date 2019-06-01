package softeng751.g21.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Shape implements Paintable {
    private int x, y, width, height;

    private int dx, dy;

    private Color color;

    private Type type;

    public Shape() {
        Random random = new Random();

        this.x = random.nextInt(256);
        this.y = random.nextInt(256);

        this.width = random.nextInt(256);
        this.height = random.nextInt(256);

        this.dx = random.nextInt(5) - 2 + 1;
        this.dy = random.nextInt(5) - 2 + 1;

        int r = random.nextInt(255);
        int g = random.nextInt(255);
        int b = random.nextInt(255);
        this.color = new Color(r, g, b);

        Type[] types = Type.values();
        type = types[random.nextInt(types.length)];
    }

    @Override
    public void paint(Graphics g, Bounce parent) {
        g.setColor(color);
        switch (type) {
            case OVAL:
                g.drawOval(x, y, width, height);
                break;
            case RECTANGLE:
                g.drawRect(x, y, width, height);
                break;
            case F_OVAL:
                g.fillOval(x, y, width, height);
                break;
            case F_RECTANGLE:
                g.fillRect(x, y, width, height);
                break;
        }

        x += dx;
        y += dy;

        if (x >= parent.getWidth() - width || x <= 0) {
            dx = -dx;
        }
        if (y >= parent.getHeight() - height || y <= 0) {
            dy = -dy;
        }
    }

    private enum Type {
        OVAL, F_OVAL, RECTANGLE, F_RECTANGLE
    }
}
