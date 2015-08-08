import java.util.List;

public class SenderManager
{
    public SenderManager() {}
    
    public void outPut()
    {
	for (Sender sender : SenderCollection.senderwithFrequencies)
	{
	    System.out.println("S" + sender.getSender() + "->" + sender.getFrequency());
	}
    }
    
    public int getLinesFromStream(String[] lines)
    {
	int countSenders = 1;
	for(int i = 0; i < lines.length; i++)
	{
	    String[] splittedLine = lines[i].split(" ");
	    
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
    /// Nested " Foreach " used for the calculation
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
	// Temporal "tempSender Instance" for Frequencies comparation.
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
            // Get and Asing the most low Frequency to the Sender
	    if (!tempSender.getDisableFrecuencies().contains(i))
		tempSender.setFrequency(i);
	}
	
        // Save the obteined frequencies in the "SenderwithFrequencies List".
	SenderCollection.senderwithFrequencies.add(tempSender);
	DisabledFrequencies(tempSender.getFrequency(), tempSender.getOverlapsList());
    }

    /// <summary>
    /// SAVE THE DISABLED(barrier) FREQUENCIES
    /// </summary>
    /// <param name="frequency"> Get the frequencies from "FindFrequencies()" method </param>
    /// <param name="senderOverlaps"> Get the Overlaps from the "FindFrequencies()" method </param>
    private void DisabledFrequencies(int frequency, List<Integer> senderOverlap)
    {
	for (int overlap : senderOverlap)
	    SenderCollection.senderCollection.stream().filter(s -> s.getSender() == overlap).findFirst().get().setDisableFrecuencies(frequency);
    }
}
