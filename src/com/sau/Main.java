package com.sau;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {
        FileReader fin = new FileReader("proverb.txt");
        BufferedReader f = new BufferedReader(fin);

        ServerSocket serverSocket = new ServerSocket(8089);

        String sockStr, pStr;
        int rNumber, lCnt;

        while(true) {
            Socket clientSocket = serverSocket.accept();
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            InputStreamReader sin = new InputStreamReader(clientSocket.getInputStream());
            BufferedReader in = new BufferedReader(sin);

            sockStr = in.readLine();
            System.out.println(sockStr);

            rNumber = (int) (Math.random() * 10);
            lCnt = 0;
            f.mark(32);
            while((pStr = f.readLine()) != null){
                if( rNumber == lCnt) {
                    System.out.println(lCnt + ": " + pStr);
                    break;
                }
                lCnt++;
            }
            f.reset();

            while (in.readLine().equals("\r\n")); // Strip out empty lines

            if (sockStr.equals("GET / HTTP/1.1")) {
                System.out.println("Tamam");
                out.println("HTTP/1.1 200 OK");
                out.println("Connection: close");
                out.println("Content-Type: text/html; charset=utf-8");
                out.println("Content-Language: tr-TR");
                out.println();
                out.println("<h1>Your proverb is:</h1>");

                out.println("<h2 style=\"color:red;\">" + pStr + "</h2>");
            } else {
                System.out.println("NOT found!");
                out.println("HTTP/1.1 404 Not Found");
                out.println("Connection: close");
                out.println("Content-Type: text/html; charset=utf-8");
                out.println("Content-Language: tr-TR");
                out.println();
            }
            out.flush();

        }
        //f.close();
    }
}
