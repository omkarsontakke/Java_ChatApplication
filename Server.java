import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

class Server{

    ServerSocket server;
    Socket socket;
    BufferedReader br;
    PrintWriter out;

    //Constructor..
    public Server(){
        try {
            server= new ServerSocket(7777);
            System.out.println("Server is ready to accept connection");
            System.out.println("Waiting...");
            socket=server.accept();

            br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void startReading(){
        //thread--read karke deta rahega
        Runnable r1=()->{

            System.out.println("Reader Started...");
            try {
            while(true){
                String msg = br.readLine();
               if(msg.equals("Bye")){
                System.out.println("Client Terminated the Chat...");
                socket.close();
                break;
               }
               System.out.println("Client : " +msg );
               } 
            }catch (Exception e) {
                    System.out.println("Client END Communication...");
            }

        };
        new Thread(r1).start();
    }
    public void startWriting(){
        //thread -- data user lega and send to the client
        Runnable r2=()->{
            System.out.println("Writer Started...");
            try {
            while(!socket.isClosed()){
            
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();
                    out.println(content);
                    out.flush(); 
                    
                    if(socket.equals("Bye")){
                        socket.close();
                        break;
                    }

                } 
            } catch (Exception e) {
                    System.out.println("Client END Communication...");
            }
        };
        new Thread(r2).start();
    }

    public static void main(String[] args) {
        System.out.println("Namaste Server");
        // Server s = new Server();
        new Server();
    }
}