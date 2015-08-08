import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.Scanner;

public class Programm
{
    private static SenderManager senderManager = new SenderManager();

    public static void main(String[] args) throws IOException, InvalidPathException, NullPointerException
    {
	System.out.println("Please Introduce the Path of the .txt file and press Enter");
	Scanner getPath = new Scanner(System.in);

	//new File("Sender.txt").getPath();
	try
	{
	    Object[] lines = senderManager.readFileFromText(getPath.nextLine());
	    getPath.close();

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
