package softeng751.g21.benchmarker;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.LinkedList;
import java.util.Set;

public class LatencyMonitor {
    private static LatencyMonitor ourInstance = new LatencyMonitor();

    private LinkedList<Long> latencyLog = new LinkedList<>();

    public static LatencyMonitor getInstance() {
        return ourInstance;
    }

    private LatencyMonitor() {
    }

    public void addEntry(long startTime, long endTime) {
        latencyLog.add(endTime - startTime);
    }

    public void writeCSV(String fileName) {
        try (CSVPrinter printer = new CSVPrinter(new FileWriter(fileName), CSVFormat.EXCEL)) {
            printer.printRecord("timeNS");
            for (Long aLong : latencyLog) {
                printer.printRecord(aLong);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
