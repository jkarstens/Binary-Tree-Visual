import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;

public class BinaryTree{ //Integer Binary Tree class

	private Node root;

	public BinaryTree(int n){

		root = new Node(n);
	}

	public int getRoot(){

		return root.getValue();
	}

	public void add(int n){

		add(n, root);
	}

	private void add(int n, Node node){ //no equals allowed

		if(n < node.getValue()){

			if(node.hasLeftChild()) add(n, node.getLeftChild());

			else node.addLeftChild( new Node(n, node) );
		}

		if(n > node.getValue()){

			if(node.hasRightChild()) add(n, node.getRightChild());

			else node.addRightChild( new Node(n, node) );
		}
	}

	public String search(int n){ //returns String to paint in graphics of main class

		int tries = search(n, root, 1);
		if(tries < 0) return "The number " + n + " was not found after " + (tries/-1) + " tries";
		else return "The number " + n + " was found after " + tries + " tries";
	}

	private int search(int target, Node n, int i){

		if( target == n.getValue() ) return i;

		if(  target > n.getValue() ){

			if( n.hasRightChild() ) return search(target, n.getRightChild(), i+1);
			else return -i; //not found after i tries
		}

		if(  target < n.getValue() ){

			if( n.hasLeftChild() ) return search(target, n.getLeftChild(), i+1);
			else return -i;
		}

		return 0; //keep compiler happy
	}

	public int size(){

		return 1 + traverseSize(root);
	}

	private int traverseSize(Node n){ //pre-order traversal, starting with root, left->right, but cant count root - size helper method

		if( n.hasChildren() ) return 2 + traverseSize( n.getLeftChild() ) + traverseSize( n.getRightChild() );
		if( n.hasLeftChild() ) return 1 + traverseSize( n.getLeftChild() );
		if( n.hasRightChild() ) return 1 + traverseSize( n.getRightChild() );

		return 0;
	}

	public int depth(){

		return depth(root) - 1;
	}

	private int depth(Node n){

		if(n == null) return 0;

		else return 1 + Math.max( depth(n.getLeftChild()), depth(n.getRightChild()) );
	}

	public void drawTree(Graphics g, int width, int height){

		traverseGraphics(g, root, width , height);
	}

	private void traverseGraphics(Graphics g, Node n, int width, int height){

		drawNode(g, n, width, height);

		if( n.hasLeftChild() ) traverseGraphics(g, n.getLeftChild(), width, height);
		if( n.hasRightChild() ) traverseGraphics(g, n.getRightChild(), width, height);
	}

	private void drawNode(Graphics g, Node n, int width, int height){

		g.drawString(n.getValue() + "", n.getX(width) - 10*( (n.getValue() + "").length() ), n.getY(height)-10);

		Graphics2D g2 = (Graphics2D)(g);
		g2.setStroke(new BasicStroke(3) );

		if( n.hasLeftChild() ) g.drawLine(n.getX(width), n.getY(height)+10, n.getLeftChild().getX(width), n.getLeftChild().getY(height)-40);
		if( n.hasRightChild() ) g.drawLine(n.getX(width), n.getY(height)+10, n.getRightChild().getX(width), n.getRightChild().getY(height)-40);

		g = (Graphics)(g2);
	}

	public class Node{

		private int value;
		private Node parent;
		private Node leftChild, rightChild;

		public Node(int v, Node p, Node l, Node r){

			value = v;
			parent = p;
			leftChild = l;
			rightChild = r;
		}

		public Node(int v){

			this(v, null, null, null);
		}

		public Node(int v, Node p){ //most common constructor

			this(v, p, null, null);
		}

		public void addLeftChild(Node l){

			leftChild = l;
		}

		public void addRightChild(Node r){

			rightChild = r;
		}

		public int getValue(){

			return value;
		}

		public Node getParent(){

			return parent;
		}

		public Node getLeftChild(){

			return leftChild;
		}

		public Node getRightChild(){

			return rightChild;
		}

		public boolean hasParent(){

			return parent != null;
		}

		public boolean hasLeftChild(){

			return leftChild != null;
		}

		public boolean hasRightChild(){

			return rightChild != null;
		}

		public boolean hasChildren(){

			return hasLeftChild() && hasRightChild();
		}

		public int getDepth(){

			return getDepth(this);
		}

		private int getDepth(Node n){

			if( n.hasParent() ) return 1 + getDepth( n.getParent() );
			else return 0;
		}

		public Node getRoot(){

			Node position = this;

			while(position.hasParent()) position = position.getParent(); //traverse up the tree

			return position;
		}

		public int getX(int width){ //for drawing Node on graphical tree

			int powCounter = 1;
			int totalX = (int)(double)(width/Math.pow(2, powCounter));

			Node position = getRoot();

			while(!position.equals(this)){

				powCounter++;

				if(value < position.getValue()){

					totalX -= width/Math.pow(2, powCounter);
					position = position.getLeftChild();
				}

				else if(value > position.getValue()){

					totalX += width/Math.pow(2, powCounter);
					position = position.getRightChild();
				}
			}

			return totalX;
		}

		public int getY(int height){ //for drawing Node on graphical tree

			return (height*(getDepth()+1))/7;
		}

		public String toString(){

			return value + "";
		}

		public boolean equals(Node n){

			return value == n.getValue();
		}
	}
}

