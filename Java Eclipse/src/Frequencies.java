import java.util.List;

public class Frequencies
{
    public void FindFrequencies()
    {
	SenderProperties tempSender = new SenderProperties();
	for (SenderProperties sender : SenderCollection.senderCollection)
	{
	    if (sender.getFrequency() == 0)
	    {
		if (sender.getDisableFrecuencies().size() > tempSender
			.getDisableFrecuencies().size())
		    tempSender = sender;
		else if (sender.getOverlapsList().size() > tempSender
			.getOverlapsList().size())
		    tempSender = sender;
		else if (sender.getOverlapsList().size() == tempSender
			.getOverlapsList().size())
		    if (sender.get_X() < tempSender.get_X())
			tempSender = sender;
		    else if (sender.get_X() == tempSender.get_X())
			if (sender.get_Y() < tempSender.get_Y())
			    tempSender = sender;
	    }
	}
	for (int i = SenderCollection.senderCollection.size(); i > 0; i--)
	{
	    if (!tempSender.getDisableFrecuencies().contains(i))
		tempSender.setFrequency(i);
	}
	SenderCollection.senderwithFrequencies.add(tempSender);
	CountDisabledFrequencies(tempSender.getFrequency(),
		tempSender.getOverlapsList());
    }

    private void CountDisabledFrequencies(int frequence,
	    List<Integer> senderOverlap)
    {
	for (int overlap : senderOverlap)
	{
	    SenderCollection.senderCollection.stream().filter(r -> r.get_Radius() == overlap).findFirst().get().setDisableFrecuencies(frequence);
	}
    }
}
