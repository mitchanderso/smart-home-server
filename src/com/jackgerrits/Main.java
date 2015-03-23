package com.jackgerrits;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        Options ops = new Options("options.prop");
        SensorController sc = new SensorController(ops.getPhidgetIp(), ops.getPhidgetPort(), ops);
        Server server = new Server(sc, ops.getServerPort());
//        Server server = new Server(ops.getServerPort());

        System.out.println("\nPress enter to end...\n");

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        server.stop();
//        sc.stop();
    }
}
