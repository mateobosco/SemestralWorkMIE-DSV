package client;

import java.awt.*;
import java.awt.event.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class ChatGUI extends JFrame implements WindowListener, MouseListener, KeyListener{

	private static final long serialVersionUID = 1L;
	private TextArea messageArea = null;
	private TextField sendArea = null;
	private Client client = null;
	
	
	public ChatGUI(String username){
		super("Chat");
		this.addWindowListener(this);
		this.setSize(400,500);
		this.setResizable(true);
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        client.exit();
		    }
		});
		
		this.messageArea = new TextArea();
		this.messageArea.setEditable(false);
		this.add(this.messageArea, "Center");
		this.messageArea.setFont(new Font("Arial", Font.PLAIN, 16));
		
		Panel p = new Panel();
		p.setLayout(new FlowLayout());
		
		this.sendArea = new TextField(30);
		this.sendArea.addKeyListener(this);
		this.sendArea.setFont(new Font("Arial", Font.PLAIN, 16));
		p.add(this.sendArea);
		p.setBackground(new Color(221,221,211));
		
		Button send = new Button("Send");
		send.addMouseListener(this);
		p.add(send);
		
		this.add(p, "South");
		this.setVisible(true);
		this.sendArea.requestFocus();
		
		this.client = new Client(username);
		try {
			this.client.connect();
		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
		Thread messageReceiver = new Thread(new MessageReceiver(this.client,this.messageArea));
		messageReceiver.start();
	}
	
	public void enterText(){
		String text = this.sendArea.getText();
		if (text.length() < 1) return;
		this.sendArea.setText("");		
		try {
			this.client.send(text);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void keyPressed(KeyEvent arg0) {	
		if (arg0.getKeyCode() == KeyEvent.VK_ENTER){
			enterText();
		}
	}

	public void keyReleased(KeyEvent arg0) {}

	public void keyTyped(KeyEvent arg0) {}

	public void mouseClicked(MouseEvent arg0) {
		if (SwingUtilities.isLeftMouseButton(arg0)){
			enterText();
		}
	}

	public void mouseEntered(MouseEvent arg0) {	}

	public void mouseExited(MouseEvent arg0) {	}

	public void mousePressed(MouseEvent arg0) {	}

	public void mouseReleased(MouseEvent arg0) {}

	public void windowActivated(WindowEvent e) {}

	public void windowClosed(WindowEvent e) {}

	public void windowClosing(WindowEvent e) {}

	public void windowDeactivated(WindowEvent e) {}

	public void windowDeiconified(WindowEvent e) {}

	public void windowIconified(WindowEvent e) {}

	public void windowOpened(WindowEvent e) {}
}
