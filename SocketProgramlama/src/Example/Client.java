package Example;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Socket clientSocket;
        BufferedReader in;
        PrintWriter out;
        Scanner sc = new Scanner(System.in);

        try {
            clientSocket = new Socket("127.0.0.1", 3000);
            out = new PrintWriter(clientSocket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));


            String kullan�c�Ad�;

            System.out.print("kullan�c� ad�n� giriniz:");


            kullan�c�Ad� = sc.nextLine();
            out.println(kullan�c�Ad�);
            out.flush();

            Thread al�c� = new Thread(new Runnable() {
                String msg;

                @Override
                public void run() {
                    try {
                        msg = in.readLine();

                        while (msg != null) {
                            System.out.println("Server : " + msg);
                            msg = in.readLine();
                        }
                        System.out.println("ba�lant� bitti");
                        out.close();
                        clientSocket.close();
                    } catch (IOException e) {
                        System.out.println("Error: " + e.getLocalizedMessage());
                    }
                }
            });
            al�c�.start();
        } catch (IOException e) {
            System.out.println("Error2: " + e.getLocalizedMessage());
        }
    }
}