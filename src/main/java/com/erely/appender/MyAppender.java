package com.erely.appender;

import com.sun.javafx.binding.StringFormatter;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.text.Format;
import java.util.logging.SimpleFormatter;


public class MyAppender extends AppenderSkeleton {

    private boolean append;
    private Socket socket;
    private OutputStream out;

    public boolean isAppend() {
        return append;
    }

    public void setAppend(boolean append) {
        this.append = append;
    }

    public MyAppender() {
        super();
        init();
    }

    private void init() {
        try {
            this.socket = new Socket("127.0.0.1", 9898);
            out = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    protected void append(LoggingEvent event) { //调用处理方法

        String msg = (String) event.getMessage();
        byte[] msgByte = msg.getBytes();
        String len = String.format("%04d", msgByte.length);
        try {
            out.write(len.getBytes());
            out.write(msgByte);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void close() {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.closed = true;
        }
    }

    public boolean requiresLayout() {
        return false;
    }
}
