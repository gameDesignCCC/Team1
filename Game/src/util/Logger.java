package util;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Logger {

    public enum Type {
        INFO, WARNING, ERROR, FATAL_ERROR, DEBUG
    }

    private File outputFile;
    private SimpleDateFormat df = new SimpleDateFormat("[yyyy/MM/dd HH:mm:ss] ");

    private BufferedWriter bw;
    private FileWriter fw;
    private PrintWriter pw;

    private boolean canWrite = false;

    public Logger(File outputFile) {
        this.outputFile = outputFile;

        log("Initializing logger...", false);
        if (!outputFile.getParentFile().exists()) {
            boolean successful = outputFile.getParentFile().mkdirs();
            if (successful) {
                log("No log output directory was located, a new directory was created at \"" + outputFile.getParentFile().getPath() + "\".");
            } else {
                log("No log output directory was located, and was unable to be created.", Type.ERROR);
            }
        }

        try {
            fw = new FileWriter(outputFile, true);
            bw = new BufferedWriter(fw);
            pw = new PrintWriter(bw);
            canWrite = true;
        } catch (IOException e) {
            e.printStackTrace();
            log(e);
            log("Logger could not initialize, no log file will be created.", Type.ERROR);
        }
    }

    public void log(String msg, Type lvl, boolean fileWrite) {
        Date d = new Date();
        String s;
        if (lvl == Type.INFO) {
            s = df.format(d) + "INFO: " + msg;
            System.out.println(s);
            if (fileWrite) write(s);

        } else if (lvl == Type.WARNING) {
            s = df.format(d) + "WARNING: " + msg;
            System.out.println(s);
            if (fileWrite) write(s);

        } else if (lvl == Type.ERROR) {
            s = df.format(d) + "ERROR: " + msg;
            System.err.println(s);
            if (fileWrite) write(s);

        } else if (lvl == Type.FATAL_ERROR) {
            s = df.format(d) + "FATAL: " + msg;
            System.err.println(s);
            if (fileWrite) write(s);

        } else if (lvl == Type.DEBUG) {
            s = df.format(d) + "DEBUG: " + msg;
            System.out.println(s);
            if (fileWrite) write(s);

        }
    }

    public void log(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        write(df.format(new Date()) + "EXCEPTION: -- BEGIN EXCEPTION OUTPUT --");
        write(sw.toString());
        write(df.format(new Date()) + "EXCEPTION: -- END EXCEPTION OUTPUT --");
    }

    public void log(String msg, Type type) {
        log(msg, type, true);
    }

    public void log(String msg, boolean fileWrite) {
        log(msg, Type.INFO, fileWrite);
    }

    public void log(String msg) {
        log(msg, Type.INFO, true);
    }

    public void write(String s) {
        if (canWrite) {
            pw.println(s);
            pw.flush();

        }
    }

    public void close() {
        if (canWrite) {
            try {
                bw.close();
                fw.close();
                pw.close();
                canWrite = false;
                log("Logger closed, the output file can be found at \"" + outputFile.getPath() + "\".");
            } catch (IOException e) {
                e.printStackTrace();
                log(e);
                log("Logger could not close file writer.", Type.ERROR);
            }
        }
    }

    public File getOutputFile() {
        return outputFile;
    }

}
