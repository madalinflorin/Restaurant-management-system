package presentationLayer;

import businessLayer.Subject;

public abstract class Observer {
	   protected Subject subject;
	   public abstract void update();
	}