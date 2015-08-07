public class Sender
{
    public Sender() { }

    public int getLines(String[] lines)
    {
	int count = 1;

	for(int i = 0; i < lines.length; i++)
	{
	    String[] splittedLine = lines[i].split(" ");
	    
	    if (Character.isDigit(splittedLine[0].charAt(0)))
	    {
		SenderProperties senderp = new SenderProperties();
		senderp.setSender(count);
		senderp.set_X(Float.valueOf(splittedLine[0]).floatValue());
		senderp.set_Y(Float.valueOf(splittedLine[1]).floatValue());
		senderp.set_Radius(Float.valueOf(splittedLine[2]).floatValue());
		SenderCollection.senderCollection.add(senderp);
		count++;
	    }
	}
	return count;
    }
    
//    private void readFile(String file)
//    {
//	ArrayList<String> getText = new ArrayList<String>();
//	
//	BufferedReader textReader;
//	try
//	{
//	    textReader = new BufferedReader(new FileReader(lines));
//	    
//	    while(textReader.read() != -1)
//	    {
//	        getText.add(textReader.readLine());
//	    }
//	}
//	catch (Exception e)
//	{
//	    e.printStackTrace();
//	}
//	textReader.close();
//    }
}
