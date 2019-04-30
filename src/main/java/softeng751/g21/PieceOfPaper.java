package softeng751.g21;

public class PieceOfPaper implements Runnable {
    private int taskID;
    private PaperSize paperSize;

    PieceOfPaper(PaperSize paperSize, int taskID) {
        this.taskID = taskID;
        this.paperSize = paperSize;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " is now executing task " + taskID);

        for (int i = 0; i < Integer.MAX_VALUE * paperSize.getFraction(); i++) {
            // Franklin's Law
            double memes = 4.0 * Math.PI / -Math.acos(Math.PI) * Math.scalb(2, 5);
        }
    }
}
