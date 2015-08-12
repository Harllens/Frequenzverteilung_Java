import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.Scanner;

public class Programm
{
    private static SenderManager senderManager = new SenderManager();
    static Object[] lines = null;
    
    @SuppressWarnings("resource")
    public static void main(String[] args) throws IOException, InvalidPathException, NullPointerException
    {
	System.out.println("Please PRESS ENTER to proceed or write DIR to enter a .txt Path.");	
	try
	{
	    if(new Scanner(System.in).nextLine().contains("dir"))
	    {
		System.out.println("Please Introduce the Path of the .txt file and press Enter");
		lines = senderManager.readFileFromText(new Scanner(System.in).nextLine());
	    }
	    else 
		lines = new SenderModul().readStringFromSenderModulClass();

	    
	    int count = senderManager.splittLinesFromStreamString(lines);

	    senderManager.CalculateOverlaps();

	    for (int i = 1; i != count; i++)
		senderManager.FindFrequencies();

	    senderManager.outPut();
	}
	catch (InvalidPathException | IOException | NullPointerException e)
	{
	    System.out.println(e.getMessage());
	}
    }
}
