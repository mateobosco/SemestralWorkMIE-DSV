package client;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class LoginGUI extends JFrame implements WindowListener, KeyListener, MouseListener{
	

	private static final long serialVersionUID = 1L;
	private TextField loginArea = null;

	public static void main(String[] args){
		new LoginGUI();
	}
	
	public LoginGUI(){
		super("Login");
		this.addWindowListener(this);
		this.setSize(350,70);
		this.setResizable(true);
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Panel p = new Panel();
		p.setLayout(new FlowLayout());
		
		this.loginArea = new TextField(30);
		this.loginArea.addKeyListener(this);
		this.loginArea.setFont(new Font("Arial", Font.PLAIN, 16));
		p.add(this.loginArea);
		p.setBackground(new Color(221,221,211));
		
		Button send = new Button("Enter");
		send.addMouseListener(this);
		p.add(send);
		
		this.add(p, "South");
		this.setVisible(true);
		this.loginArea.requestFocus();
	}
	
	private void enterText() {
		String username = this.loginArea.getText();
		if (username.length() > 1){
			new ChatGUI(username);
			this.setVisible(false);
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
