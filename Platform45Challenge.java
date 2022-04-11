
import java.io.*;
import java.util.ArrayList;

public class Platform45Challenge {

	public static void main(String[] args) throws FileNotFoundException, IOException {

		// Read from file in args or form standard input (default)
		InputStream inStream;
		boolean isInput = true;
		if (args.length > 0) { // Is file
			File fd = new File(args[0]);
			inStream = new FileInputStream(fd);
			isInput = false;
		} else // No file (Standard Input)
			inStream = System.in;
		
		InputStreamReader reader = new InputStreamReader(inStream);
		BufferedReader br = new BufferedReader(reader);
		if (isInput)
			System.out.println("Please input map shape e.g. 5 5:");

		// Read in Map Size First and Validate
		String[] mapLine = br.readLine().split(" ");
		if (mapLine.length != 2) {
			System.out.println("Invalid Map Shape - Wrong Dimension");
			System.exit(2);
		}
		int[] mapShape;
		try {
			mapShape = new int[]{Integer.parseInt(mapLine[0]), Integer.parseInt(mapLine[1])};
		} catch(NumberFormatException e) {
			System.out.println("Invalid Map Shape - Not An Integer");
			mapShape = new int[]{0, 0};
			System.exit(2);
		}

		String line;
		int idx = 0;
		boolean hasRover = false;
		ArrayList<Rover> rovers = new ArrayList();
		if (isInput)
			System.out.println("Please input rover start position (e.g. 1 1 E):");
		while (!(line = br.readLine()).trim().equals("")) {

			if (!hasRover) { // Rover First Line
				String[] rovStart = line.split(" ");
				if (rovStart.length != 3) {
					System.out.println("Invalid Rover Start Position for rover " + (++idx) + " - Wrong Formatting");
					System.exit(2);
				}
				int[] rovPosition;
				try {
					rovPosition = new int[]{Integer.parseInt(rovStart[0]) - 1, Integer.parseInt(rovStart[1]) - 1};
				} catch(NumberFormatException e) {
					System.out.println("Invalid Start Position For Rover " + (++idx) + " - Not A Number");
					rovPosition = new int[]{0, 0};
					System.exit(2);
				}
				char rovDirection = rovStart[2].charAt(0);
				int[] rovOrientation = parseCompass(rovDirection);
				if (rovOrientation[0] == 0 && rovOrientation[1] == 0) {
					System.out.println("Invalid Rover Direction For Rover " + (++idx));
					System.exit(2);
				}
				Rover rover = new Rover(rovPosition[0], rovPosition[1], mapShape, rovOrientation);
				rovers.add(rover);
				hasRover = true;
				if (isInput)
					System.out.println("Please input Rover Instruction (e.g. LMRMLM):");
			} else { // Rover Second Line
				rovers.get(rovers.size() - 1).setInstruction(line.trim());
				hasRover = false;
				if (isInput)
					System.out.println("Please input rover start position (e.g. 1 1 E) or new line to execute:");
			}
			
		}

		for (int i = 0; i < rovers.size(); i++) {
			boolean result = rovers.get(i).executeInstruction();
			System.out.println(rovers.get(i));
			if (!result) {
				System.out.println("Invalid instruction for rover " + (++i));
				System.exit(2);
			}
		}
		drawRovers(rovers, mapShape);
	}

	// Draw Rover Positions And Orientations
	public static void drawRovers(ArrayList<Rover> rovers, int[] mapShape) {
		char[][] map = new char[mapShape[0]][mapShape[1]];
		for (int idx = 0; idx < rovers.size(); idx++) {
			Rover rover = rovers.get(idx);
			int roverX = rover.getX();
			int roverY = rover.getY();
			char roverDirection = rover.getOorientation();
			map[roverY][roverX] = roverDirection;
		}
		for (int i = mapShape[0] - 1; i >= 0; i--) {
			System.out.println("");
			for (int j = 0; j < mapShape[1]; j++) {
				System.out.print("  ");
				if (map[i][j] == 0)
					System.out.print("*");
				else
					System.out.print(map[i][j]);
			}
		}
		System.out.println("\n");
	}

	//Convert N,S,E,W orientation into direction vector
	public static int[] parseCompass(char direction) {
		int[] directionVector;
		switch(direction) {
			case 'N':
				directionVector = new int[]{0, 1};
			break;
			case 'E':
				directionVector = new int[]{1, 0};
			break;
			case 'S':
				directionVector = new int[]{0, -1};
			break;
			case 'W':
				directionVector = new int[]{-1, 0};
			break;
			default:
				directionVector = new int[]{0, 0};
		}
		return directionVector;
	}

}