import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class VerySimpleChatServer {
    ArrayList clientOutputStreams;

    public static void main(String[] args) {
        new VerySimpleChatServer().go();
    }

    public void go() {
        clientOutputStreams = new ArrayList();
        try {
            ServerSocket serverSock = new ServerSocket(4127);
            while (true) {
                Socket clientSocket = serverSock.accept();
                //PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
                //ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
                clientOutputStreams.add(clientSocket);
                Thread t = new Thread(new ClientHandler(clientSocket));
                t.start();
                System.out.println("got a connection");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void tellEveryone(User user) {
        Iterator it = clientOutputStreams.iterator();
        while (it.hasNext()) {
            try {
                Socket clientSocket = (Socket) it.next();
                System.out.println(user.getMessage());
                ObjectOutputStream outToClient = new ObjectOutputStream(clientSocket.getOutputStream());
                outToClient.writeObject(new ImageAndText(user));
                //PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
                //writer.println(message);
                //writer.flush();
                //BufferedImage img = ImageIO.read(new File("D:/teddy.jpg"));
                //ImageIO.write(img, "jpg", clientSocket.getOutputStream());
                //clientSocket.getOutputStream().flush();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class ClientHandler implements Runnable {
        BufferedReader reader;
        Socket sock;
        ObjectInputStream ois;

        public ClientHandler(Socket clientSocket) {
            try {
                sock = clientSocket;
                //InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
                //reader = new BufferedReader(isReader);
                ois = new ObjectInputStream(sock.getInputStream());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        public void run() {
            String message;
            try {

//                while ((message = reader.readLine()) != null) {
//                    System.out.println("read " + message);
//                    tellEveryone(message);
//                }
                User user;
                while ((user = (User) ois.readObject()) != null) {
                    System.out.println(user.hashCode());
                    tellEveryone(user);

                }
            } catch (Exception ex) {
                System.out.println("some error");
                ex.printStackTrace();
            }
        }
    }

}
