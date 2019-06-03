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

        paintables.add(new Shape(6, 254, 106, 13, 1, -1, 35, 137, 123, Shape.Type.RECTANGLE));
        paintables.add(new Shape(186, 73, 22, 108, 1, 0, 97, 149, 48, Shape.Type.RECTANGLE));
        paintables.add(new Shape(138, 15, 170, 83, 0, 1, 70, 127, 158, Shape.Type.F_RECTANGLE));
        paintables.add(new Shape(110, 215, 69, 228, -1, 0, 22, 220, 141, Shape.Type.OVAL));
        paintables.add(new Shape(31, 0, 131, 57, 2, 3, 195, 56, 108, Shape.Type.OVAL));
        paintables.add(new Shape(182, 157, 80, 117, 2, 3, 134, 242, 155, Shape.Type.RECTANGLE));
        paintables.add(new Shape(164, 59, 227, 191, 1, 3, 99, 43, 206, Shape.Type.RECTANGLE));
        paintables.add(new Shape(218, 86, 222, 185, -1, 1, 63, 80, 115, Shape.Type.F_RECTANGLE));
        paintables.add(new Shape(134, 80, 249, 226, 1, 2, 52, 237, 36, Shape.Type.F_OVAL));
        paintables.add(new Shape(81, 100, 249, 230, 0, 3, 227, 103, 144, Shape.Type.OVAL));
        paintables.add(new Shape(113, 72, 218, 10, 1, -1, 236, 116, 13, Shape.Type.OVAL));
        paintables.add(new Shape(61, 70, 212, 173, 2, 3, 53, 93, 21, Shape.Type.RECTANGLE));
        paintables.add(new Shape(165, 244, 63, 176, -1, 3, 228, 22, 107, Shape.Type.F_RECTANGLE));
        paintables.add(new Shape(174, 69, 119, 32, 0, 2, 16, 35, 243, Shape.Type.F_RECTANGLE));
        paintables.add(new Shape(194, 88, 166, 214, -1, 1, 241, 4, 180, Shape.Type.F_RECTANGLE));
        paintables.add(new Shape(119, 77, 245, 221, 3, -1, 0, 206, 57, Shape.Type.RECTANGLE));
        paintables.add(new Shape(201, 73, 92, 58, 2, 1, 29, 219, 111, Shape.Type.F_RECTANGLE));
        paintables.add(new Shape(23, 135, 135, 80, 2, 1, 205, 248, 177, Shape.Type.F_RECTANGLE));
        paintables.add(new Shape(58, 39, 43, 116, 3, 2, 200, 78, 177, Shape.Type.F_OVAL));
        paintables.add(new Shape(150, 202, 230, 63, 2, 1, 234, 74, 0, Shape.Type.F_OVAL));
        paintables.add(new Shape(255, 32, 132, 178, 3, -1, 217, 8, 212, Shape.Type.F_RECTANGLE));
        paintables.add(new Shape(255, 76, 158, 189, 1, 0, 8, 160, 90, Shape.Type.F_OVAL));
        paintables.add(new Shape(213, 154, 88, 61, 2, 0, 97, 17, 9, Shape.Type.F_OVAL));
        paintables.add(new Shape(42, 94, 139, 102, 3, 3, 7, 139, 246, Shape.Type.OVAL));
        paintables.add(new Shape(90, 97, 69, 251, 2, -1, 85, 56, 159, Shape.Type.F_RECTANGLE));
        paintables.add(new Shape(225, 153, 228, 102, 0, -1, 199, 20, 43, Shape.Type.F_OVAL));
        paintables.add(new Shape(86, 99, 132, 54, 3, 2, 125, 108, 18, Shape.Type.RECTANGLE));
        paintables.add(new Shape(148, 214, 188, 238, 3, 1, 236, 76, 9, Shape.Type.F_OVAL));
        paintables.add(new Shape(200, 0, 48, 6, 3, 0, 67, 4, 84, Shape.Type.RECTANGLE));
        paintables.add(new Shape(223, 239, 141, 226, 1, -1, 218, 187, 32, Shape.Type.OVAL));
        paintables.add(new Shape(253, 19, 108, 73, 0, 0, 165, 233, 234, Shape.Type.RECTANGLE));
        paintables.add(new Shape(123, 62, 206, 91, 1, 1, 149, 227, 49, Shape.Type.F_RECTANGLE));
        paintables.add(new Shape(70, 183, 5, 222, 3, 2, 13, 22, 24, Shape.Type.OVAL));
        paintables.add(new Shape(99, 227, 214, 129, 1, 3, 80, 30, 80, Shape.Type.F_OVAL));
        paintables.add(new Shape(46, 0, 207, 181, 2, 0, 231, 249, 192, Shape.Type.F_OVAL));
        paintables.add(new Shape(101, 192, 48, 159, -1, -1, 139, 240, 164, Shape.Type.F_RECTANGLE));
        paintables.add(new Shape(29, 121, 109, 178, 2, 1, 39, 118, 111, Shape.Type.F_RECTANGLE));
        paintables.add(new Shape(98, 174, 77, 197, 2, -1, 160, 43, 7, Shape.Type.F_OVAL));
        paintables.add(new Shape(146, 42, 46, 108, 3, 0, 197, 130, 150, Shape.Type.RECTANGLE));
        paintables.add(new Shape(116, 2, 87, 189, 1, 2, 161, 128, 7, Shape.Type.F_RECTANGLE));
        paintables.add(new Shape(125, 59, 230, 202, 3, 1, 112, 43, 86, Shape.Type.F_RECTANGLE));
        paintables.add(new Shape(6, 56, 172, 10, 2, 2, 187, 131, 85, Shape.Type.RECTANGLE));
        paintables.add(new Shape(130, 216, 186, 74, 0, 2, 214, 102, 247, Shape.Type.F_OVAL));
        paintables.add(new Shape(25, 72, 164, 2, -1, -1, 23, 184, 236, Shape.Type.OVAL));
        paintables.add(new Shape(169, 140, 139, 245, 0, 3, 117, 54, 32, Shape.Type.F_OVAL));
        paintables.add(new Shape(15, 130, 96, 236, 0, 3, 63, 128, 218, Shape.Type.OVAL));
        paintables.add(new Shape(255, 225, 152, 28, 2, 1, 39, 176, 78, Shape.Type.RECTANGLE));
        paintables.add(new Shape(4, 1, 186, 153, 3, 2, 203, 249, 176, Shape.Type.F_RECTANGLE));
        paintables.add(new Shape(200, 247, 35, 124, 3, 2, 249, 81, 212, Shape.Type.RECTANGLE));
        paintables.add(new Shape(14, 122, 29, 211, 1, 0, 48, 158, 159, Shape.Type.F_RECTANGLE));
        paintables.add(new Shape(116, 87, 124, 55, -1, 1, 248, 140, 184, Shape.Type.OVAL));
        paintables.add(new Shape(95, 94, 87, 183, 0, -1, 64, 228, 106, Shape.Type.F_OVAL));
        paintables.add(new Shape(87, 247, 223, 196, 3, -1, 26, 24, 105, Shape.Type.F_RECTANGLE));
        paintables.add(new Shape(241, 235, 188, 203, 3, 3, 65, 131, 72, Shape.Type.F_RECTANGLE));
        paintables.add(new Shape(196, 25, 143, 190, 2, -1, 107, 248, 121, Shape.Type.RECTANGLE));
        paintables.add(new Shape(97, 171, 245, 243, 2, -1, 17, 93, 118, Shape.Type.F_OVAL));
        paintables.add(new Shape(240, 235, 233, 79, 1, -1, 30, 65, 177, Shape.Type.RECTANGLE));
        paintables.add(new Shape(21, 55, 65, 190, -1, 1, 65, 254, 51, Shape.Type.F_OVAL));
        paintables.add(new Shape(44, 154, 171, 202, 1, 2, 252, 181, 229, Shape.Type.OVAL));
        paintables.add(new Shape(250, 14, 118, 213, 2, -1, 239, 243, 123, Shape.Type.OVAL));
        paintables.add(new Shape(211, 212, 205, 35, 0, 1, 113, 178, 129, Shape.Type.F_OVAL));
        paintables.add(new Shape(46, 49, 80, 59, 2, 3, 238, 218, 171, Shape.Type.RECTANGLE));
        paintables.add(new Shape(145, 71, 175, 192, 0, 2, 95, 229, 89, Shape.Type.F_OVAL));
        paintables.add(new Shape(108, 86, 222, 97, 1, -1, 194, 78, 95, Shape.Type.RECTANGLE));
        paintables.add(new Shape(221, 29, 23, 27, 3, 1, 115, 186, 43, Shape.Type.OVAL));
        paintables.add(new Shape(109, 129, 169, 95, 0, 3, 204, 134, 223, Shape.Type.OVAL));
        paintables.add(new Shape(188, 207, 242, 215, 3, 1, 152, 108, 52, Shape.Type.F_RECTANGLE));
        paintables.add(new Shape(117, 94, 150, 90, 2, -1, 27, 2, 189, Shape.Type.OVAL));
        paintables.add(new Shape(160, 255, 208, 100, 2, -1, 217, 3, 254, Shape.Type.F_RECTANGLE));
        paintables.add(new Shape(47, 64, 201, 2, 0, 3, 42, 59, 128, Shape.Type.OVAL));
        paintables.add(new Shape(245, 86, 102, 219, -1, 1, 111, 75, 127, Shape.Type.F_RECTANGLE));
        paintables.add(new Shape(209, 54, 102, 175, 2, 2, 62, 46, 170, Shape.Type.F_OVAL));
        paintables.add(new Shape(5, 71, 19, 104, 0, -1, 56, 163, 42, Shape.Type.RECTANGLE));
        paintables.add(new Shape(219, 121, 237, 159, 2, 1, 80, 105, 90, Shape.Type.RECTANGLE));
        paintables.add(new Shape(223, 228, 3, 139, 2, 2, 106, 28, 26, Shape.Type.RECTANGLE));
        paintables.add(new Shape(104, 75, 67, 177, -1, 1, 22, 209, 180, Shape.Type.F_OVAL));
        paintables.add(new Shape(139, 81, 188, 16, 3, -1, 22, 94, 254, Shape.Type.OVAL));
        paintables.add(new Shape(186, 88, 99, 22, 0, -1, 40, 5, 118, Shape.Type.OVAL));
        paintables.add(new Shape(89, 160, 201, 81, 1, -1, 64, 67, 124, Shape.Type.F_RECTANGLE));
        paintables.add(new Shape(241, 106, 234, 198, 3, 0, 63, 161, 171, Shape.Type.OVAL));
        paintables.add(new Shape(58, 99, 28, 45, 3, 1, 128, 81, 231, Shape.Type.F_OVAL));
        paintables.add(new Shape(61, 173, 211, 202, 1, 0, 227, 171, 118, Shape.Type.RECTANGLE));
        paintables.add(new Shape(141, 34, 28, 238, 1, 2, 41, 241, 68, Shape.Type.OVAL));
        paintables.add(new Shape(208, 201, 253, 15, 3, 2, 80, 233, 207, Shape.Type.F_RECTANGLE));
        paintables.add(new Shape(100, 139, 106, 173, -1, -1, 196, 102, 52, Shape.Type.OVAL));
        paintables.add(new Shape(248, 55, 81, 9, 2, 1, 91, 65, 144, Shape.Type.F_OVAL));
        paintables.add(new Shape(215, 139, 38, 203, -1, 2, 117, 189, 199, Shape.Type.RECTANGLE));
        paintables.add(new Shape(102, 224, 144, 196, 1, 2, 168, 152, 47, Shape.Type.F_RECTANGLE));
        paintables.add(new Shape(61, 156, 210, 98, 2, 2, 104, 61, 220, Shape.Type.RECTANGLE));
        paintables.add(new Shape(74, 16, 132, 245, -1, 2, 97, 147, 122, Shape.Type.OVAL));
        paintables.add(new Shape(77, 115, 149, 249, 2, 2, 170, 53, 58, Shape.Type.F_RECTANGLE));
        paintables.add(new Shape(111, 88, 125, 171, 2, 2, 67, 239, 135, Shape.Type.F_RECTANGLE));
        paintables.add(new Shape(165, 254, 216, 44, 0, 2, 156, 35, 102, Shape.Type.F_OVAL));
        paintables.add(new Shape(189, 239, 40, 30, 3, 3, 187, 213, 7, Shape.Type.OVAL));
        paintables.add(new Shape(130, 101, 113, 255, 3, 0, 120, 215, 36, Shape.Type.F_RECTANGLE));
        paintables.add(new Shape(214, 127, 127, 170, 2, 2, 156, 128, 93, Shape.Type.F_RECTANGLE));
        paintables.add(new Shape(207, 25, 216, 0, -1, -1, 164, 137, 80, Shape.Type.F_RECTANGLE));
        paintables.add(new Shape(115, 185, 43, 18, 3, 2, 73, 235, 146, Shape.Type.F_OVAL));
        paintables.add(new Shape(8, 38, 157, 0, 2, -1, 102, 145, 90, Shape.Type.RECTANGLE));
        paintables.add(new Shape(175, 174, 22, 227, -1, 2, 45, 228, 129, Shape.Type.F_RECTANGLE));
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
