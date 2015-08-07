public class Programm
{

    public static void main(String[] args) throws Exception
    {
	// String path =
	// "G:\\ProjectsGit\\Frequenzverteilung.G18.IT4b_2015\\Sendefrequenzzuordnung\\src\\SendefrequenzzuordnungPackage\\Eingabe Dateien.txt";
	int count = 0;

	String s = new StringBuilder().append("**\n")
		.append("** Beispiel der Aufgabenstellung\n").append("**\n")
		.append("30 110 12.5\n").append("65 100.3 27\n")
		.append("34.2 60 31.9\n").append("114 100 18\n")
		.append("87.43 72.57 12.5\n").append("94 120 12.5\n")
		.append("78.28 42.168 22.119\n").append("118 60 28.5\n")
		.append("145 38 22.12\n").append("125 122 22.12\n")
		.append("140 82 17\n").append("145 102 27.5\n").toString();

	String[] str = s.split("\n");

	try
	{
	    count = new Sender().getLines(str);
	}
	catch (Exception e)
	{
	    throw new Exception(e.getMessage());
	}

	new Overlaps().CalculateOverlaps();
	Frequencies frequenz = new Frequencies();

	for (int i = 1; i != count; i++)
	{
	    frequenz.FindFrequencies();
	}

	outPut();
    }

    static void outPut()
    {
	for (SenderProperties sender : SenderCollection.senderwithFrequencies)
	{
	    System.out.println("S" + sender.getSender() + "->"
		    + sender.getFrequency());
	}
    }

}
