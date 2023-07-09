import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    Socket socket;
    BufferedReader br;
    PrintWriter out;

    public Client(){
        try {
            System.out.println("Sending Request to server");
            socket = new Socket("127.0.0.1",7777);
            System.out.println("Connection Established");


            br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting(); 
        } catch (Exception e) {
            
        }
    }
    public void startReading(){
        //thread--read karke deta rahega
        Runnable r1=()->{

            System.out.println("Reader Started...");
            try{
            while(!socket.isClosed()){
                String msg = br.readLine();
               if(msg.equals("Bye")){
                System.out.println("Server Terminated the Chat...");
                socket.close();
                break;
               }
               System.out.println("Server : " +msg );
               } 

            }catch(Exception e){
                System.out.println("Client END Communication...");
            }

        };
        new Thread(r1).start();
    }

    public void startWriting(){
        //thread -- data user lega and send to the client
        Runnable r2=()->{
            System.out.println("Writer Started...");
            try{
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
            }catch(Exception e){
                System.out.println("Server END Communication...");
            }
        };
        new Thread(r2).start();
     }

    public static void main(String[] args) {
        System.out.println("NAMASTE CLIENT");
        // Client c = new Client();
        new Client();
    }
}
