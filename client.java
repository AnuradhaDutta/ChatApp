import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;

public class client extends JFrame {
    
    Socket socket;

    BufferedReader br;
    PrintWriter out;

    //declaring components
    private JLabel heading=new JLabel("Client Area");
    private JTextArea messageArea=new JTextArea();
    private JTextField messageInput=new JTextField();
    private Font font=new Font("Roboto", Font.BOLD,20);



    public client() //constructor
    {

        try{ 
            System.out.println("Sending request to server");
            socket=new Socket("127.0.0.1",7777);//object 
            System.out.println("Connection done"); 


             br=new BufferedReader(new InputStreamReader(socket.getInputStream()));

       out=new PrintWriter(socket.getOutputStream());

       createGUI();
       handleEvents();

       startReading(); //calling 2 methods
       //startWriting();




        }
        catch(Exception e) {

        }
    }

    private void handleEvents()
    {
       messageInput.addKeyListener(new KeyListener(){

        @Override
        public void keyTyped(KeyEvent e) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void keyPressed(KeyEvent e) {
            // TODO Auto-generated method stub 

            
        }

        @Override
        public void keyReleased(KeyEvent e) {
            // TODO Auto-generated method stub

           // System.out.println("Key released"+e.getKeyCode()); 
           if(e.getKeyCode() ==10) 
           {
            String contentToSend=messageInput.getText();
            messageArea.append("Me: "+contentToSend + "\n");
            out.println(contentToSend);
            out.flush();
            messageInput.setText("");
            messageInput.requestFocus();


           }
           
        }

        
       });
       {}

    }

    /**
     * 
     */
    public void createGUI()
    {
//code to create GUI 
      this.setTitle("Client Messager[END]");
      this.setSize(600,700);
      this.setLocationRelativeTo(null);
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      //code for components
    heading.setFont(font);
    messageArea.setFont(font);
    messageInput.setFont(font);
    heading.setIcon(new ImageIcon("clogo.png"));
    heading.setHorizontalTextPosition(SwingConstants.CENTER);
    heading.setVerticalTextPosition(SwingConstants.BOTTOM);

    heading.setHorizontalAlignment(SwingConstants.CENTER);
    heading.setBorder(BorderFactory.createEmptyBorder(20,20,20,20)); 

    messageArea.setEditable(false);
    messageInput.setHorizontalAlignment(SwingConstants.CENTER);

    //LAyout for frame
    this.setLayout(new BorderLayout());

    //adding the components to frame
this.add(heading,BorderLayout.NORTH);
JScrollPane jScrollPane=new JScrollPane(messageArea);
this.add(jScrollPane,BorderLayout.CENTER);
this.add(messageInput,BorderLayout.SOUTH);

this.setVisible(true);
//making changes to commit
//mk


    }

    public void startReading() //method
{

    //Thread will read the data and give it
    Runnable r1=()->{   //lamda expression, this is a thread
       
       System.out.println("reader started");
    
       try{

       while(true)
       { 
        
        
        String msg= br.readLine(); //reading the line and storing it in string msg
        if(msg.equals("Exit"))
        {
            System.out.println("Server terminated the chat");
            JOptionPane.showMessageDialog(this, "Server Terminated the chat");
            messageInput.setEnabled(false);
            socket.close();
            break;
        }

        //System.out.println("Server :"+msg);
        messageArea.append("Server:"+msg+"\n");
    }

}
    catch(Exception e)
       {
        e.printStackTrace(); //Prints exception
       }
    };
new Thread(r1).start(); 

}

private void close() {
    }

public void startWriting() //method for writing
{

   //Thread will take user data and send to client 
   Runnable r2=()->{

    System.out.println("writer started");

    try{
       
    while(true)
    {
        BufferedReader br2=new BufferedReader(new InputStreamReader(System.in));
            String content=br2.readLine();
            out.println(content);
            out.flush();
        } 
        
    }
    catch (Exception e )
    {e.printStackTrace();
    }
   };
   new Thread(r2).start(); 
}





    public static void main(String[] args) {
        System.out.println("This is client");  
        new client();
    }
}
