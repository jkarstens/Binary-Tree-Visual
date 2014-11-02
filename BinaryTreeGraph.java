import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.Color;
import java.awt.Image;
import java.awt.BorderLayout;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;

public class BinaryTreeGraph extends Applet implements KeyListener, Runnable{

	private Thread thread;
	private Graphics dbGraphics;
	private Image dbImage;

	private BinaryTree tree;
	private boolean search;
	private int searchNum;

	private Font titleFont = new Font("Kristen ITC", Font.BOLD, 17);
	private Font leafFont = new Font("Kristen ITC", Font.BOLD, 35);
	String number = "";

	public void init(){

		setSize(1200, 750);
		setBackground(new Color(100, 220, 100));
		addKeyListener(this);

		search = false;
		searchNum = -1;
	}

	public void paint(Graphics g){

		g.setFont(titleFont);

		if(!search){

			if(tree != null) g.drawString("Press (Space) to Search Numbers in the Tree", 380, 680);
			g.drawString("Enter a number into the tree: " + number, 450, 30);
		}

		else{

			g.drawString("Press (Space) to Enter Numbers in the Tree", 380, 680);
			g.drawString("Search a number in the tree: " + number, 450, 30);

			if(searchNum > -1) g.drawString( tree.search( searchNum ), 420, 200);
		}

		if(tree != null){

			g.drawString("Current Size = " + tree.size(), 150, 30);
			g.drawString("Current Depth = " + tree.depth(), 850, 30);

			g.setFont(leafFont);
			g.setColor(new Color(128, 0, 0));
			tree.drawTree(g, getSize().width, getSize().height);
		}
	}

	public void update(Graphics g){

		if(dbImage == null){

			dbImage = createImage(getSize().width, getSize().height);
			dbGraphics = dbImage.getGraphics();
		}

		dbGraphics.setColor(getBackground());
		dbGraphics.fillRect(0, 0, getSize().width, getSize().height);
		dbGraphics.setColor(getForeground());
		paint(dbGraphics);

		g.drawImage(dbImage, 0, 0, this);
	}

	public void keyPressed(KeyEvent e){
	}

	public void keyReleased(KeyEvent e){

		int keyCode = e.getKeyCode();

		for(int i=48; i<58; i++) if(keyCode == i) number += (i-48);

		if(keyCode == KeyEvent.VK_BACK_SPACE) number = number.substring(0, number.length()-1);

		if(keyCode == KeyEvent.VK_ENTER && number.length() != 0){

			if(search){

				tree.search( Integer.parseInt( number ) ); //unnecessary...
				searchNum = Integer.parseInt( number );
			}

			else{

				if(tree == null) tree = new BinaryTree( Integer.parseInt( number ) );
				else tree.add( Integer.parseInt ( number ) );
			}

			number = "";
		}

		if(keyCode == KeyEvent.VK_SPACE){

			if(search) search = false;
			else search = true;
		}
	}

	public void keyTyped(KeyEvent e){
	}

	public void start(){

		if(thread == null){

			thread = new Thread(this);
			thread.start();
		}
	}

	public void run(){

		while(thread != null){

			repaint();

			try{

				Thread.sleep(20);
			}
			catch(InterruptedException e){
			}
		}
	}

	public void stop(){

		thread = null;
	}

	public static void main(String[] args){

		Applet thisApplet = new BinaryTreeGraph();
		thisApplet.init();
		thisApplet.start();

		JFrame frame = new JFrame("Basic Binary Tree");
		frame.setSize(thisApplet.getSize());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.getContentPane().add(thisApplet, BorderLayout.CENTER);
		frame.setVisible(true);
	}
}
