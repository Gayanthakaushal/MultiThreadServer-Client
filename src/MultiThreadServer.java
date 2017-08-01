import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.*;

import static com.sun.activation.registries.LogSupport.log;

public class MultiThreadServer implements Runnable {
    Socket csocket;
    static ExecutorService executorService;
    public static int QUEUE_SIZE = 2;
    public static int MIN_SIZE = 2;
   // static Queue<Socket> queue = new ArrayDeque<Socket>();
    final static BlockingQueue<Runnable> queueR = new ArrayBlockingQueue<Runnable>(QUEUE_SIZE);


    MultiThreadServer(Socket csocket) {

        this.csocket = csocket;
    }

    public static void main(String args[]) throws Exception {

        Scanner reader = new Scanner(System.in);
        System.out.println("Enter thread size: ");
        int n = reader.nextInt();

        executorService = new ThreadPoolExecutor(MIN_SIZE, n, 0L, TimeUnit.MILLISECONDS, queueR);

        System.out.println("The worker thread server is running.");
        int clientNumber = 0;
        ServerSocket listener = new ServerSocket(1234);

        try {
           while (true) {

               executorService.execute(new getServerDetails(listener.accept(), clientNumber++));
               System.out.println("connected " + clientNumber);
               if (clientNumber > n){
                   break;
               }

           }
       }finally {
           executorService.shutdown();
       }
    }


    private static class getServerDetails extends Thread {
        private Socket socket;
        private int clientNumber;

        public getServerDetails(Socket socket, int clientNumber) {
            this.socket = socket;
            this.clientNumber = clientNumber;
            log("New connection with client# " + clientNumber + " at " + socket);

        }
    }

    @Override
    public void run() {
        try {

            PrintWriter out = new PrintWriter(csocket.getOutputStream(), true);

            System.out.println("Connected");


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



