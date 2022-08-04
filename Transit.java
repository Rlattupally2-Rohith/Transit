import java.util.ArrayList;

import org.w3c.dom.Node;


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
	public static TNode makeList(int[] trainStations, int[] busStops, int[] locations) 
{

	TNode head = new TNode();
	TNode head2 = new TNode();
	TNode head3 = new TNode();
	TNode ptr = new TNode();
	TNode ptr2 = new TNode();

	TNode ptr3 = new TNode();
	ptr = head;
	ptr2 = head2;
	ptr3 = head3;

	for(int i = 0; i<trainStations.length;i++)
	{
		TNode temp = new TNode();
		temp.location = trainStations[i];
		ptr.next = temp;
		ptr = ptr.next;
	}

	for(int j = 0; j<busStops.length;j++)
	{
		TNode temp2 = new TNode();
		temp2.location = busStops[j];
		ptr2.next = temp2;
		ptr2 = ptr2.next;
	}

	for(int k = 0; k<locations.length;k++)
	{
		TNode temp3 = new TNode();
		temp3.location = locations[k];
		ptr3.next = temp3;
		ptr3 = ptr3.next;
	}
	
	for (TNode ptr4= head; ptr4 != null; ptr4= ptr4.next)
	{
		for(TNode ptr5 = head2; ptr5 != null; ptr5= ptr5.next)
		{
			if (ptr4.location == ptr5.location)
			{
				ptr4.down= ptr5;  
			}
		}
	}

	for(TNode ptr6 = head2; ptr6 != null; ptr6 = ptr6.next)
	{
		for(TNode ptr7 = head3; ptr7 != null; ptr7 = ptr7.next)
		{
			if(ptr6.location == ptr7.location)
			{
				ptr6.down = ptr7;
			}
		}
	}
	return head;

	
}


	/**
	 * Modifies the given layered list to remove the given train station but NOT its associated
	 * bus stop or walking location. Do nothing if the train station doesn't exist
	 * 
	 * @param trainZero The zero node in the train layer of the given layered list
	 * @param station The location of the train station to remove
	 */
	public static void removeTrainStation(TNode trainZero, int station) 
	{
		
		for(TNode ptr = trainZero; ptr != null; ptr = ptr.next)
		{
			if(ptr.next != null && ptr.location == station)
			{
				return;
			}
			if(ptr.next != null && ptr.next.location == station)
			{
				ptr.next = ptr.next.next;
				break;
			}
		}
	}

	/**
	 * Modifies the given layered list to add a new bus stop at the specified location. Do nothing
	 * if there is no corresponding walking location.
	 * 
	 * @param trainZero The zero node in the train layer of the given layered list
	 * @param busStop The location of the bus stop to add
	 */
	public static void addBusStop(TNode trainZero, int busStop) 
{
		TNode walkingPointer = null;		
		TNode busNode = new TNode(busStop, null, null);
		TNode busHead=trainZero.down;


		for(walkingPointer=trainZero.down.down; walkingPointer !=null; walkingPointer=walkingPointer.next){
				if(walkingPointer.location == busStop){
					break;
				}
		}

		while(busHead != null){
			if(busHead.next == null){
				busHead.next=busNode;
				busNode.next=null;
				busNode.down=walkingPointer;
				break;
			}
			
			if(busHead.next.location > busStop){
				busNode.next=busHead.next;
				busHead.next=busNode;
				busNode.down=walkingPointer;
				break;
			}
			busHead=busHead.next;

			if(busHead != null && busHead.location == busStop)
			{
				return;
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
		
		ArrayList <TNode> newList = new ArrayList <TNode>();
		TNode newListPointer = trainZero;
		
		//newList.add(newListPointer);

		while(newListPointer.next != null && newListPointer.next.location <= destination)
		{
			newList.add(newListPointer);
			if(newListPointer.location == destination)
			{
				return newList;
			}
				newListPointer = newListPointer.next;
		}
			newList.add(newListPointer);
			TNode newListPointer2 = newListPointer.down;
			while(newListPointer2.next != null && newListPointer2.next.location <= destination)
			{
				newList.add(newListPointer2);
				if(newListPointer.location == destination)
				{
					return newList;
				}
				newListPointer2 = newListPointer2.next;
			}
			newList.add(newListPointer2);
			TNode newListPointer3 = newListPointer2.down;
			while(newListPointer3.location <= destination)
			{
				newList.add(newListPointer3);
				if(newListPointer3.location == destination)
				{
					return newList;
				}
				newListPointer3 = newListPointer3.next;
	
			}


		return newList;
	}

	/**
	 * Returns a deep copy of the given layered list, which contains exactly the same
	 * locations and connections, but every node is a NEW node.
	 * 
	 * @param trainZero The zero node in the train layer of the given layered list
	 * @return
	 */
	public static TNode duplicate(TNode trainZero) 
	{
		TNode train = trainZero;
		TNode bus = trainZero.down;
		TNode walking = trainZero.down.down;
		TNode train2 = new TNode(train.location);
		TNode bus2 = new TNode (bus.location);
		TNode walking2 = new TNode(walking.location);
		TNode old = train2;
		TNode old2 = bus2;
		TNode old3 = walking2;

		for(TNode ptr = train.next; ptr != null; ptr = ptr.next)
		{
			TNode temp = new TNode(ptr.location);
			old.next = temp;
			old = temp;
		}
		for(TNode ptr = bus; ptr != null; ptr = ptr.next)
		{
			TNode temp = new TNode(ptr.location);
			old2.next = temp;
			old2 = temp;
		}
		for(TNode ptr = walking; ptr != null; ptr = ptr.next)
		{
			TNode temp = new TNode(ptr.location);
			old3.next = temp;
			old3 = temp;
		
		}
		for(TNode ptr = train2 ;ptr != null; ptr = ptr.next)
		{
			for(TNode ptr2 = bus2; ptr2 != null; ptr2 = ptr2.next)
			{
				if(ptr.location == ptr2.location)
				{
					ptr.down = ptr2;
				}
			}
		}
		for(TNode ptr3 = bus2; ptr3 != null; ptr3 = ptr3.next)
		{
			for(TNode ptr4 = walking2; ptr4 != null; ptr4 = ptr4.next)
			{
				if (ptr3.location == ptr4.location)
				{
					ptr3.down = ptr4;
				}
			}
		}
		

		return train2;
	}

	/**
	 * Modifies the given layered list to add a scooter layer in between the bus and
	 * walking layer.
	 * 
	 * @param trainZero The zero node in the train layer of the given layered list
	 * @param scooterStops An int array representing where the scooter stops are located
	 */
	public static void addScooter(TNode trainZero, int[] scooterStops) 
	{
		TNode ScooterHead = new TNode();
		TNode ScooterPtr = new TNode();
		TNode busPointer = new TNode();
		TNode walkingPointer = new TNode();
		TNode busHead = new TNode(); 
		TNode walkingHead = new TNode();
		ScooterPtr = ScooterHead;
		walkingPointer = walkingHead;
		busPointer = busHead;
		TNode walkingzero = trainZero.down.down;


			for(int i = 0; i<scooterStops.length; i++)
			{
			TNode scooterTemp = new TNode();
			scooterTemp.location = scooterStops[i];
			ScooterPtr.next = scooterTemp;
			ScooterPtr = ScooterPtr.next;
			}

	for (TNode ptr4 = trainZero.down ; ptr4 != null; ptr4= ptr4.next)
	{
		for(TNode ptr5 = ScooterHead; ptr5 != null; ptr5= ptr5.next)
		{
			if (ptr4.location == ptr5.location)
			{
				ptr4.down= ptr5;
				
			}
		}
		
	}

	for (TNode ptr5= ScooterHead; ptr5 != null; ptr5= ptr5.next)
		{
		for(TNode ptr6 = walkingzero; ptr6 != null; ptr6= ptr6.next)
			{
				if (ptr5.location == ptr6.location)
					{
						ptr5.down= ptr6;
				
					}
			
			}

		}
	}
}