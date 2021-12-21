import java.util.Vector;

public final class commandProcessingClass {
	public static Vector<String> processingSystemCommand(String s) {
		Vector<String> optionsVector = new Vector<String>();
		String processString = "";
		if (s.indexOf("_") != -1) {
			
			processString = s.substring(s.indexOf("_") - 1);
			processString = processString.trim();
			System.out.println("Command got: " + processString);
			String listString[]  = processString.split("_");
			for(String str: listString) {
				optionsVector.add(str);
			}
		} else {
			System.out.println("Command not found!");
		}
		System.out.println("String vector got: " + optionsVector);
		return optionsVector;
	}
	public static String getSuite(String suite) {
		switch(suite) {
			case "C":
				return "clubs";
			case "S":
				return "spades";
			case "D":
				return "diamonds";
			case "H":
				return "hearts";
		}
		return null;
	}
	
	public static String getNameCard(String value, String suite) {
		String nameCard = value + "_of_" + getSuite(suite) + ".png";
		
		System.out.println("nameCard got: " + nameCard);
		return nameCard;
	}
}
