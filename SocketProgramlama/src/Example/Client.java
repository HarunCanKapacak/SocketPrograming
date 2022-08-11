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


            String kullanýcýAdý;

            System.out.print("kullanýcý adýný giriniz:");


            kullanýcýAdý = sc.nextLine();
            out.println(kullanýcýAdý);
            out.flush();

            Thread alýcý = new Thread(new Runnable() {
                String msg;

                @Override
                public void run() {
                    try {
                        msg = in.readLine();

                        while (msg != null) {
                            System.out.println("Server : " + msg);
                            msg = in.readLine();
                        }
                        System.out.println("baðlantý bitti");
                        out.close();
                        clientSocket.close();
                    } catch (IOException e) {
                        System.out.println("Error: " + e.getLocalizedMessage());
                    }
                }
            });
            alýcý.start();
        } catch (IOException e) {
            System.out.println("Error2: " + e.getLocalizedMessage());
        }
    }
}