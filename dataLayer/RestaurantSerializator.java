package dataLayer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import businessLayer.Restaurant;

public class RestaurantSerializator {
	private static final String filename = "restaurant.ser";
	public static Restaurant deserialize(Restaurant restaurant) {

		// Deserialization
		try {
			// Reading the object from a file
			FileInputStream file = new FileInputStream(filename);
			ObjectInputStream in = new ObjectInputStream(file);

			// Method for deserialization of object
			restaurant = (Restaurant) in.readObject();

			in.close();
			file.close();
		} catch (IOException ex) {
			System.out.println("IOException is caught");
		} catch (ClassNotFoundException ex) {
			System.out.println("ClassNotFoundException is caught");
		}
		return restaurant;
	}
	
	public static Restaurant serialize(Restaurant restaurant) {
		try {
 	         FileOutputStream fileOut = new FileOutputStream(filename);
 	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
 	         out.writeObject(restaurant);
 	         out.close();
 	         fileOut.close();
 	         System.out.printf("Serialized data is saved in " + filename);
 	      } catch (IOException i) {
 	         i.printStackTrace();
 	      }
		return restaurant;
	}


}
