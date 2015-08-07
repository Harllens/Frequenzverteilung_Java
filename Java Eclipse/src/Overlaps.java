public class Overlaps
{
    public void CalculateOverlaps()
    {
	double distance = 0;
	for (SenderProperties sender : SenderCollection.senderCollection)
	{
	    for (SenderProperties sender2 : SenderCollection.senderCollection)
	    {
		if (sender.getSender() != sender2.getSender())
		{
		    // double d = (double)((sender2.get_X() - sender.get_X()) *
		    // 2 + ((sender2.get_Y() - sender.get_Y()) * 2));

		    distance = Math.sqrt((sender2.get_X() - sender.get_X())
			    * (sender2.get_X() - sender.get_X())
			    + ((sender2.get_Y() - sender.get_Y()) * (sender2
				    .get_Y() - sender.get_Y())));
		    
		    if (sender.get_Radius() + sender2.get_Radius() > distance)
			sender.setOverlapsList(sender2.getSender());
		}
	    }
	}
    }
}
