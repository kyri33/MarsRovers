/*
	Rover Class For instances of each mars rover
*/


public class Rover {

	private int x;
	private int y;
	private int[] mapShape;
	private String instruction;

	// Orientation represented by a direction vector in the x, y coordinates e.g. North = [0, 1] & West = [-1, 0]
	private int[] oorientation;

	// Default constructor
	public Rover() {
		this.x = 0;
		this.y = 0;
		this.mapShape = new int[]{5, 5};
		this.oorientation = new int[]{0, 1};
	}

	public Rover(int x, int y, int[] mapShape, int[] oorientation) {
		this.x = x;
		this.y = y;
		this.mapShape = mapShape;
		this.oorientation = oorientation;
	}

	public void move() {

		this.x += this.oorientation[0];
		this.y += this.oorientation[1];

		// Check if rover moved off the map
		if (this.x >= this.mapShape[0])
			this.x = this.mapShape[0] - 1;
		else if (this.x < 0)
			this.x = 0;
		
		if (this.y >= this.mapShape[1])
			this.y = this.mapShape[1] - 1;
		else if (this.y < 0)
			this.y = 0;

	}

	// Boolean set to true for clockwise and false for anti-clockwise
	public void turn(boolean clockwise) {
		int tmpo = this.oorientation[0];
		this.oorientation[0] = this.oorientation[1];
		this.oorientation[1] = tmpo;
		if (clockwise)
			this.oorientation[1] = this.oorientation[1] * -1;
		else
			this.oorientation[0] = this.oorientation[0] * -1;
	}

	public boolean executeInstruction() {
		for (int i = 0; i < this.instruction.length(); i++) {
			char command = this.instruction.charAt(i);
			switch(command) {
				case 'L':
					this.turn(false);
				break;
				case 'R':
					this.turn(true);
				break;
				case 'M':
					this.move();
				break;
				default:
					return false;
			}
		}
		return true;
	}

	// Convert oorientation to N, E, S, W
	public char parseOorientation() {
		char oor = 'W';
		if (this.oorientation[1] == 1)
			oor = 'N';
		else if (this.oorientation[0] == 1)
			oor = 'E';
		else if (this.oorientation[1] == -1)
			oor = 'S';

		return oor;
	}

	// Override To String method for printing
	public String toString() {
		String str = "Rover at position: [" + (this.x + 1) + "," + (this.y + 1) + "]";
		str = str + " and orientation: " + parseOorientation();
		return str;
	}

	// Setter Methods

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setOorientation(int[] oorientation) {
		this.oorientation = oorientation;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}

	// Getter Methods

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public char getOorientation() {
		return this.parseOorientation();
	}

	public String getInstruction() {
		return this.instruction;
	}

}