import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class SimpleChatClient {


    JTextField outgoing;
    BufferedReader reader;
    PrintWriter writer;
    Socket sock;

    JPanel mainPanel;
    JPanel chatPanel;
    JFrame frame;
    JScrollPane qScroller;
    User user;
    ObjectOutputStream outToClient;
    public static void main(String[] args) {

        SimpleChatClient client = new SimpleChatClient();
        client.go(args[0], args[1]);
    }

    public void go(String username, String userAvatar) {

        user = new User(username, userAvatar);
        frame = new JFrame("Simple Chat");
        mainPanel = new JPanel();
        chatPanel = new JPanel();
        chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));

        outgoing = new JTextField(20);
        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(new SendButtonListener());

        mainPanel.add(outgoing);
        mainPanel.add(sendButton);
        qScroller = new JScrollPane(chatPanel);
        qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        qScroller.setPreferredSize(new Dimension(500,500));
        //chatPanel.add(qScroller);
        setUpNetworking();

        Thread readerThread = new Thread(new IncomingReader());
        readerThread.start();
        frame.getContentPane().add(BorderLayout.NORTH, qScroller);
        frame.getContentPane().add(BorderLayout.SOUTH, mainPanel);

        frame.setSize(800, 500);
        frame.setVisible(true);
    }

    private void setUpNetworking() {
        try {
            sock = new Socket("127.0.0.1", 4127);
            //writer = new PrintWriter(sock.getOutputStream());
            //InputStreamReader sr = new InputStreamReader(sock.getInputStream());
            //reader = new BufferedReader(sr);
             outToClient = new ObjectOutputStream(sock.getOutputStream());
            System.out.println("networking established");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class SendButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
            try {
                user.setMessage(outgoing.getText());
                outToClient.writeObject(user);

                System.out.println(user.getMessage());
                outToClient.reset();
                //writer.println(userNickName + ": " + outgoing.getText());
                //writer.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
            outgoing.setText("");
            outgoing.requestFocus();
        }
    }

    public class IncomingReader implements Runnable {
        public void run() {
            try {
                while (true) {
                    ObjectInputStream ois = new ObjectInputStream(sock.getInputStream());
                    ImageAndText clientMessage = (ImageAndText) ois.readObject();

                    JLabel label1 = new JLabel(clientMessage.getText());
                    label1.setIcon(clientMessage.getImage());
                    chatPanel.add(label1);
                    frame.revalidate();
                    frame.repaint();


                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }


}
