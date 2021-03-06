import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SenderManager
{
    public SenderManager() {}
    
    final static Charset ENCODING = StandardCharsets.UTF_8;
    
    /// <summary>
    /// READ AND SAVE THE FILE IN A LIST STRING AND RETURN IT VALUES 
    /// </summary>
    public Object[] readFileFromText(String fileName) throws IOException
    {
	List<String> lines = new ArrayList<String>();
	Path path = Paths.get(fileName);
	BufferedReader reader = Files.newBufferedReader(path, ENCODING);
	String line = null;
	
	while ((line = reader.readLine()) != null) 
	    lines.add(line);

	return lines.toArray();
    }
    
    public void outPut() throws NullPointerException
    {
	System.out.println("\n");
	System.out.println("Senderpositionen (X,Y) und Senderadien:");
	for (Sender sender : SenderCollection.senderCollection)
	    System.out.println("S" + sender.getSender() + " " + sender.get_X() + " " + sender.get_Y() + " " + sender.get_Radius());
	
	System.out.println("\n");
	System.out.println("Frequenzzuordnung");
	for (Sender sender : SenderCollection.senderwithFrequencies)
	    System.out.println("S" + sender.getSender() + "->" + sender.getFrequency());
	
	System.out.println("\n");
	int count = 1;
	do 
	{
	    String Sender = "";
	    String s = "";
	
	    for (Sender sender : SenderCollection.senderwithFrequencies)
		if(sender.getFrequency() == count)
		{
		    s += " S" + sender.getSender();
		    Sender = "Frequenz: " + sender.getFrequency() + " " + " Sender:" + s;
		}
	    count++;

	    if(!Sender.isEmpty())
		System.out.println(Sender);
	    
	}while(count != SenderCollection.senderwithFrequencies.size());
    }
    
    /// <summary>
    /// GET THE LINES FROM THE STREAM LIST, SPLITT IT AND STORES IN SENDERCOLLECTION
    /// </summary>
    public int splittLinesFromStreamString(Object[] lines)
    {
	int countSenders = 1;
	for(int i = 0; i < lines.length; i++)
	{
	    String[] splittedLine = ((String) lines[i]).split(" ");
	    
	    if (Character.isDigit(splittedLine[0].charAt(0)))
	    {
		Sender senderp = new Sender();
		senderp.setSender(countSenders);
		senderp.set_X(Float.valueOf(splittedLine[0]).floatValue());
		senderp.set_Y(Float.valueOf(splittedLine[1]).floatValue());
		senderp.set_Radius(Float.valueOf(splittedLine[2]).floatValue());
		SenderCollection.senderCollection.add(senderp);
		countSenders++;
	    }
	}
	return countSenders;
    }
    
    /// <summary>
    /// CALCULATE THE SENDER WITH MOST OVERLAPS TO CHOOSE THE INITIAL SENDER
    /// Get the sender from a "SenderCollection List", calculate the overlaps and save the result in a "Overlaps List".
    /// Nested " For-each " used for the calculation
    /// </summary>
    public void CalculateOverlaps()
    {
	for (Sender sender : SenderCollection.senderCollection)
	    for (Sender sender2 : SenderCollection.senderCollection)
		if (sender.getSender() != sender2.getSender())
		{
		    double x = (sender2.get_X() - sender.get_X());
		    double y = (sender2.get_Y() - sender.get_Y());
		
                    // Multiply the X , Y coordinates, get the Sum of the Radius and save the Sender with most Overlaps.
		    if (sender.get_Radius() + sender2.get_Radius() > Math.sqrt(Math.pow(x, 2)  + Math.pow(y, 2)))
			sender.setOverlapsList(sender2.getSender());
		}
    }
    
    /// <summary>
    /// GET THE FREQUENCIES
    /// Find the Disabled(Barrier) frequencies and save it in "SenderWithFrequencies List".
    /// </summary>
    public void FindFrequencies()
    {
	// Temporal "tempSender Instance" for Frequencies comparison.
	Sender tempSender = new Sender();
	for (Sender sender : SenderCollection.senderCollection)
	{
	    if (sender.getFrequency() == 0)
	    {
		if (sender.getDisableFrecuencies().size() > tempSender.getDisableFrecuencies().size())
		    tempSender = sender;
		
		 // Phase 1 --> Choose the Sender with the most Overlaps ( 1. die meisten Überschneidungen)
		else if (sender.getOverlapsList().size() > tempSender.getOverlapsList().size())
		    tempSender = sender;
		
		else if (sender.getOverlapsList().size() == tempSender.getOverlapsList().size())
		{
                    // Phase 2 --> Choose the Sender where X have the lowest coordinate ( 2. den westlichsten (kleinste x-Koordinate))
		    if (sender.get_X() < tempSender.get_X())
			tempSender = sender;
				
		    else if (sender.get_X() == tempSender.get_X())
		    {
                        // Phase 3 --> Choose the Sender where Y have the lowest coordinate ( 3. den südlichsten (kleinste y-Koordinate))
			if (sender.get_Y() < tempSender.get_Y())
			    tempSender = sender;
		    }
		}
	    }
	}
	for (int i = SenderCollection.senderCollection.size(); i > 0; i--)
	{ 
            // Get and Assign the most low Frequency to the Sender
	    if (!tempSender.getDisableFrecuencies().contains(i))
		tempSender.setFrequency(i);
	}
	
        // Save the obtained frequencies in the "SenderwithFrequencies List".
	SenderCollection.senderwithFrequencies.add(tempSender);
	DisabledFrequencies(tempSender.getFrequency(), tempSender.getOverlapsList());
    }

    /// <summary>
    /// SAVE THE DISABLED(barrier) FREQUENCIES
    /// </summary>
    /// <param name="frequency"> Get the frequencies from "FindFrequencies()" method @param
    /// <param name="senderOverlaps"> Get the Overlaps from the "FindFrequencies()" method @param
    private void DisabledFrequencies(int frequency, List<Integer> senderOverlap)
    {
	for (int overlap : senderOverlap)
	    SenderCollection.senderCollection.stream().filter(s -> s.getSender() == overlap).findFirst().get().setDisableFrecuencies(frequency);
    }
}
