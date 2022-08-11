package Example;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileInputStream;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) {
		ServerSocket serverSocket;
		Socket clientSocket;
		BufferedReader in, msj;
		PrintWriter out;
		File dosya;
		String clientName;

		try {

			serverSocket = new ServerSocket(3000);
			dosya = new File("C:/javakamp/SocketProgramlama/src/Example/server.txt");

			System.out.println("Sunucu baslatildi. Baglanti bekleniyor..");
			clientSocket = serverSocket.accept();

			out = new PrintWriter(clientSocket.getOutputStream());
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			msj = new BufferedReader(new InputStreamReader(new FileInputStream(dosya)));

			clientName = in.readLine();
			System.out.println(clientName + " baðlandý");

			Thread gonderici = new Thread(new Runnable() {
				String dosyaOkuyucu;

				@Override
				public void run() {
					try {
						dosyaOkuyucu = msj.readLine();

						while (dosyaOkuyucu != null) {
							out.println(dosyaOkuyucu);
							out.flush();
							dosyaOkuyucu = msj.readLine();
						}

						System.out.println(clientName + " ayrýldý");

						out.close();
						clientSocket.close();
						serverSocket.close();
					} catch (IOException e) {
						System.out.println("Error: " + e.getLocalizedMessage());
					}
				}
			});
			gonderici.start();
		} catch (IOException a) {
			System.out.println("Error2: " + a.getLocalizedMessage());

		}

	}
}
