package util;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Logger {

    public enum TYPE {
        INFO, WARNING, ERROR, FATAL_ERROR
    }

    private File outputFile;
    private SimpleDateFormat df = new SimpleDateFormat("[yyyy/MM/dd HH:mm:ss] ");

    private BufferedWriter bw;
    private FileWriter fw;
    private PrintWriter pw;

    private List<String> out = new ArrayList<>();

    private boolean canWrite = false;

    public Logger(File outputFile) {
        this.outputFile = outputFile;

        log("Initializing logger...", false);
        if (!outputFile.getParentFile().exists()) {
            boolean successful = outputFile.getParentFile().mkdirs();
            if (successful) {
                log("No log output directory was located, a new directory was created at \"" + outputFile.getParentFile().getPath() + "\".");
            } else {
                log("No log output directory was located, and was unable to be created.", Logger.TYPE.ERROR);
            }
        }

        try {
            fw = new FileWriter(outputFile, true);
            bw = new BufferedWriter(fw);
            pw = new PrintWriter(bw);
            canWrite = true;
        } catch (IOException e) {
            e.printStackTrace();
            log("Logger could not initialize, no log file will be created.", TYPE.ERROR, false);
        }
    }

    public void log(String msg, TYPE lvl, boolean fileWrite) {
        Date d = new Date();
        String s;
        if (lvl == TYPE.INFO) {
            s = df.format(d) + "INFO: " + msg;
            System.out.println(s);
            if (fileWrite) {
                out.add(s);
                write();
            }
        } else if (lvl == TYPE.WARNING) {
            s = df.format(d) + "WARNING: " + msg;
            System.out.println(s);
            if (fileWrite) {
                out.add(s);
                write();
            }
        } else if (lvl == TYPE.ERROR) {
            s = df.format(d) + "ERROR: " + msg;
            System.err.println(s);
            if (fileWrite) {
                out.add(s);
                write();
            }
        } else if (lvl == TYPE.FATAL_ERROR) {
            s = df.format(d) + "FATAL: " + msg;
            System.err.println(s);
            if (fileWrite) {
                out.add(s);
                write();
            }
        }
    }

    public void log(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        out.add(df.format(new Date()) + "EXCEPTION: -- BEGIN EXCEPTION OUTPUT --");
        out.add(sw.toString());
        out.add(df.format(new Date()) + "EXCEPTION: -- END EXCEPTION OUTPUT --");
        write();
    }

    public void log(String msg, TYPE type) {
        log(msg, type, true);
    }

    public void log(String msg, boolean fileWrite) {
        log(msg, TYPE.INFO, fileWrite);
    }

    public void log(String msg) {
        log(msg, TYPE.INFO, true);
    }

    public void write() {
        if (canWrite) {
            for (String s : out) {
                pw.println(s);
                pw.flush();
            }
            out.clear();
        }
    }

    public void close() {
        if (canWrite) {
            write();
            try {
                bw.close();
                fw.close();
                pw.close();
                log("Logger closed, the output file can be found at \"" + outputFile.getPath() + "\".");
            } catch (IOException e) {
                e.printStackTrace();
                log("Logger could not close file writer.", TYPE.ERROR);
            }
        }
    }

    public File getOutputFile() {
        return outputFile;
    }

}
