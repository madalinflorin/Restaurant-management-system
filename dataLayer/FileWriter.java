package dataLayer;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class FileWriter {
	public static final Logger LOGGER = Logger.getLogger(FileWriter.class.getName());
	static FileHandler fh;

	public static void setupLogger() {
		LOGGER.setLevel(Level.ALL);
		try {int i=0;
			FileHandler fhandler = new FileHandler("bill"+i+".log");
			SimpleFormatter sformatter = new SimpleFormatter();
			fhandler.setFormatter(sformatter);
			LOGGER.addHandler(fhandler);
			i++;

		} catch (IOException ex) {
			LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
		} catch (SecurityException ex) {
			LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
		}
	}
}
