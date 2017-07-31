import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

public class MultiThreadServer implements Runnable {
    Socket csocket;
    static Queue<Socket> queue=new ArrayDeque<Socket>();

    MultiThreadServer(Socket csocket) {

        this.csocket = csocket;
    }
    public static void main(String args[]) throws Exception {

        Scanner reader = new Scanner(System.in);
        System.out.println("Enter thread size: ");
        int n = reader.nextInt();

        int clientCount = 1;
        ServerSocket ssock = new ServerSocket(1234);
        System.out.println("Listening...");

        while (true) {

            if (clientCount <= n) {
                Socket sock = ssock.accept();
                System.out.println("Connected " + clientCount);
                new Thread(new MultiThreadServer(sock)).start();
                clientCount++;
            }else {
                Socket sock = ssock.accept();
                queue.add(sock);
            }

        }


    }

  @Override
   public void run() {
        try {
            csocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}




