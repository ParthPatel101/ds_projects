import java.util.ArrayList;

/**
 * This class contains methods which perform various operations on a layered linked
 * list to simulate transit
 * 
 * @author Ishaan Ivaturi
 * @author Prince Rawal
 */
public class Transit {
	/**
	 * Makes a layered linked list representing the given arrays of train stations, bus
	 * stops, and walking locations. Each layer begins with a location of 0, even though
	 * the arrays don't contain the value 0.
	 * 
	 * @param trainStations Int array listing all the train stations
	 * @param busStops Int array listing all the bus stops
	 * @param locations Int array listing all the walking locations (always increments by 1)
	 * @return The zero node in the train layer of the final layered linked list
	 */
	public static TNode makeList(int[] trainStations, int[] busStops, int[] locations) {
		// WRITE YOUR CODE HERE	
		TNode walkf = new TNode();
		TNode walkl = walkf;
		TNode busf = new TNode(0, null, walkf);
		TNode busl = busf;
		TNode trainf = new TNode(0, null, busf);
		TNode trainl = trainf;
		trainf.down = busf;
		int indexoftrain = 0;
		int indexofbus = 0;
		for (int i = 0; i < locations.length; i++) {
			TNode walkol = walkl;
			walkl = new TNode(locations[i]);
			walkol.next = walkl;

			if ((indexofbus < busStops.length) && busStops[indexofbus] == locations[i]) {
				TNode busol = busl;
				busl = new TNode(busStops[indexofbus]);
				busol.next = busl;
				busl.down = walkl;
				indexofbus++;
			}
			if ((indexoftrain < trainStations.length) && trainStations[indexoftrain] == locations[i]) {
				TNode trainol = trainl;
				trainl = new TNode(trainStations[indexoftrain]);
				trainol.next = trainl;
				trainol.down = busl;
				indexoftrain++;
			}
			

		}
		return trainf;
	}
	
	/**
	 * Modifies the given layered list to remove the given train station but NOT its associated
	 * bus stop or walking location. Do nothing if the train station doesn't exist
	 * 
	 * @param trainZero The zero node in the train layer of the given layered list
	 * @param station The location of the train station to remove
	 */
	public static void removeTrainStation(TNode trainZero, int station) {
		// WRITE YOUR CODE HERE
		Boolean stationexist = false;
		TNode existchecker = trainZero;
		while(existchecker != null) {
			if(existchecker.location == station) {
				stationexist = true;
			}
			existchecker = existchecker.next;
		}
		if (stationexist) {
		TNode pointer = trainZero;
		while (pointer.next.location != station) {
			pointer = pointer.next;
		}
		pointer.next = pointer.next.next;
		}
   	}

	/**
	 * Modifies the given layered list to add a new bus stop at the specified location. Do nothing
	 * if there is no corresponding walking location.
	 * 
	 * @param trainZero The zero node in the train layer of the given layered list
	 * @param busStop The location of the bus stop to add
	 */
	public static void addBusStop(TNode trainZero, int busStop) {
		// WRITE YOUR CODE HERE
		Boolean stop = false;
		TNode check = trainZero.down;
		while(check != null) {
			if(check.location == busStop) {
				stop = true;
			}
			check = check.next;
		}
		if (!stop) {
			TNode before = trainZero.down;
			while (before.next != null && before.next.location < busStop) {
				before = before.next;
			}
			
			TNode walkingpointer = before.down;
			while (walkingpointer != null && walkingpointer.location != busStop) {
				walkingpointer = walkingpointer.next;
				
			}
			Boolean walkexists = false;
			if (walkingpointer.location == busStop) {
				walkexists = true;
			}
			if (walkexists) {
				before.next = new TNode(busStop, before.next, walkingpointer);
			}
		}
	}
	
	/**
	 * Determines the optimal path to get to a given destination in the walking layer, and 
	 * collects all the nodes which are visited in this path into an arraylist. 
	 * 
	 * @param trainZero The zero node in the train layer of the given layered list
	 * @param destination An int representing the destination
	 * @return
	 */
	public static ArrayList<TNode> bestPath(TNode trainZero, int destination) {
		// WRITE YOUR CODE HERE
		ArrayList<TNode> bestroute = new ArrayList<TNode>();
		bestroute.add(trainZero);
		TNode pointer = trainZero;
		while (pointer.next != null && pointer.next.location <= destination) {
			pointer = pointer.next;
			bestroute.add(pointer);
		}
		pointer = pointer.down;
		bestroute.add(pointer);
		while (pointer.next != null && pointer.next.location <= destination) {
			pointer = pointer.next;
			bestroute.add(pointer);
		}
		pointer = pointer.down;
		bestroute.add(pointer);
		while (pointer != null && pointer.location != destination) {
			pointer = pointer.next;
			bestroute.add(pointer);
		}
		return bestroute;
	}

	/**
	 * Returns a deep copy of the given layered list, which contains exactly the same
	 * locations and connections, but every node is a NEW node.
	 * 
	 * @param trainZero The zero node in the train layer of the given layered list
	 * @return
	 */
	public static TNode duplicate(TNode trainZero) {
		// WRITE YOUR CODE HERE
		TNode headoftrain = new TNode();
		TNode headofbus = new TNode();
		TNode headofwalk = new TNode();
		headoftrain.down = headofbus;
		headofbus.down = headofwalk;
		TNode duplicatewalkptr = headofwalk;
		TNode duplicatebusptr = headofbus;
		TNode duplicatetrainptr = headoftrain;
		TNode trainpointer = trainZero;
		TNode buspointer = trainZero.down;
		TNode walkpointer = trainZero.down.down;
		buspointer = buspointer.next;
		walkpointer = walkpointer.next;
		trainpointer = trainpointer.next;
		while(walkpointer != null) {
			TNode dupwalkoldptr = duplicatewalkptr;
			duplicatewalkptr = new TNode(walkpointer.location);
			dupwalkoldptr.next = duplicatewalkptr;
			
			if (buspointer != null && walkpointer.location == buspointer.location) {
				TNode dupbusoldptr = duplicatebusptr;
				duplicatebusptr = new TNode(buspointer.location);
				dupbusoldptr.next = duplicatebusptr;
				duplicatebusptr.down = duplicatewalkptr;
				buspointer = buspointer.next;
			}

			if (trainpointer != null && walkpointer.location == trainpointer.location) {
				TNode duptrainoldptr = duplicatetrainptr;
				duplicatetrainptr = new TNode(trainpointer.location);
				duptrainoldptr.next = duplicatetrainptr;
				duplicatetrainptr.down = duplicatebusptr;
				trainpointer = trainpointer.next;
			}
			walkpointer = walkpointer.next;
		}


		return headoftrain;
	}

	/**
	 * Modifies the given layered list to add a scooter layer in between the bus and
	 * walking layer.
	 * 
	 * @param trainZero The zero node in the train layer of the given layered list
	 * @param scooterStops An int array representing where the scooter stops are located
	 */
	public static void addScooter(TNode trainZero, int[] scooterStops) {
		// WRITE YOUR CODE HERE
		TNode buspointer = trainZero.down;
		TNode walkpointer = trainZero.down.down;
		buspointer = buspointer.next;
		TNode scooterf = new TNode();
		TNode scooterpointer = scooterf;
		scooterf.down = walkpointer;
		trainZero.down.down = scooterf;
		buspointer.down = scooterf;
		TNode newscooterpointer = scooterf;
		for (int i = 0; i < scooterStops.length; i++) {
			TNode scootoldlast = scooterpointer;
			scooterpointer = new TNode(scooterStops[i]);
			scootoldlast.next = scooterpointer;
			if (buspointer != null && buspointer.location == scooterStops[i]) {
				buspointer.down = scooterpointer;
				buspointer = buspointer.next;
			}
		}
		while (walkpointer != null) {
			if (newscooterpointer != null && newscooterpointer.location == walkpointer.location) {
			newscooterpointer.down = walkpointer;
			newscooterpointer = newscooterpointer.next;
			}
			walkpointer = walkpointer.next;
		}

	}
	
}