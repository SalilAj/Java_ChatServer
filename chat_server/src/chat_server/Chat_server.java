package chat_server;
import java.net.*;
import java.io.*;

public class Chat_server {

    private Socket          socket   = null;
    private ServerSocket    server   = null;
    private DataInputStream streamIn =  null;
    
    public Chat_server(int port)
   {  
      try
      {  
         server = new ServerSocket(port);  
         System.out.println("Chat server started on port " + port);
         System.out.println("Waiting for a client ..."); 
         socket = server.accept();
         System.out.println("Client accepted: " + socket);
         open();
         boolean done = false;
         while (!done)
         {  
            try
            {  
               String line = streamIn.readUTF();
               System.out.println(line);
               done = line.equals(".bye");
            }
            catch(IOException ex)
            {  
                System.out.println("Error: " + ex.getMessage());
                done = true;
            }
         }
         close();
      }
      catch(IOException ioe)
      {  
          System.out.println(ioe); 
      }
   }
    public void open() throws IOException
   {  
       streamIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
   }
   public void close() throws IOException
   {  
      if (socket != null)    
          socket.close();
      if (streamIn != null)  
          streamIn.close();
   }
    
    public static void main(String[] args) 
    {
        Chat_server server = new Chat_server(8000);
    }
}