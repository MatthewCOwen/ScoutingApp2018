package processing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.JOptionPane;

class MatchTeamColor 
{
	public final int matchNumber;
	public final int teamNumber;
	public final String teamColor;
	
	MatchTeamColor()
	{
		matchNumber = 0;
		teamNumber = 0;
		teamColor = "r";
	}
		
	MatchTeamColor(int matchNumber, int teamNumber, String color)
	{
		this.matchNumber = matchNumber;
		this.teamNumber = teamNumber;
		this.teamColor = color;
	}
	
	public String toString()
	{
		return String.format("%s %s %s", matchNumber, teamNumber, teamColor);
	}
}

public class FileManager 
{
	public static final char PATH_SEPARATOR = System.lineSeparator().equals("\n") ? '/' : '\\';
	public static final String ROOT_FOLDER = System.getProperty("user.dir");
	public static final String RAW_DATA_FOLDER = PATH_SEPARATOR + "raw data";
	public static final String COMPILED_DATA_FOLDER = PATH_SEPARATOR + "compiled data";
	public static final String MATCH_LIST_FILE = PATH_SEPARATOR + "match_list.txt";
	
	private static final int TEAM_NUMBER_INDEX = 0;
	private static final int MATCH_NUMBER_INDEX = 5;
	private static final int REPLAY_NUMBER_INDEX = 6;
	
	private static LinkedList<MatchTeamColor> matchList = new LinkedList<MatchTeamColor>();
	
	public static void createReadMe() 
	{
		File readme = new File(ROOT_FOLDER + PATH_SEPARATOR + "readme.txt");
		
		if (!readme.exists())
		{
			try
			{
				PrintWriter pw = new PrintWriter(readme);
				pw.println(String.format(	"%s%n%s%n%s%n%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s%n%s", 
											"In order to auto-pull match numbers you will ",
											"need to create a file named 'match_list.txt'",
											"and place it in the same folder as the .jar file.",
											"Inside of match_list.txt it should look like this:",
											"MatchNumber TeamNumber AllianceColor",
											"  -Team numbers should be 4 digit numbers.",
											"  -Alliance color can be a simple 'r' or 'b'",
											"for example:",
											"1 0001 r",
											"2 0012 b",
											"3 0123 r",
											"4 1234 b"));
				pw.flush();
				pw.close();
			}
			catch (FileNotFoundException e)
			{
				
			}
		}
	}
	
	public static void populateMatchList() throws FileNotFoundException
	{
		Scanner scan = new Scanner(new File(ROOT_FOLDER + MATCH_LIST_FILE));
		
		while(scan.hasNext())
		{
			matchList.add(new MatchTeamColor(scan.nextInt(), scan.nextInt(), scan.next()));
		}
		scan.close();
	}
	
	public static MatchTeamColor getNextMatchAndTeam()
	{
		return matchList.peek();
	}
	
	public static void updateMatchList()
	{
		matchList.poll();
		
		try
		{
			PrintWriter pw = new PrintWriter(new FileOutputStream(ROOT_FOLDER + MATCH_LIST_FILE, false));
			
			for (MatchTeamColor mtc : matchList)
			{
				pw.println(mtc);
				pw.flush();
			}
			pw.close();
		}
		catch (FileNotFoundException e)
		{
			
		}
	}
	
	public static void createMatchFileFolder()
	{
		File matchFileFolder = new File(ROOT_FOLDER + RAW_DATA_FOLDER);
		
		if (!matchFileFolder.isDirectory())
		{
			matchFileFolder.mkdir();
		}
	}
	
	public static void printToFile() throws FileNotFoundException
	{
		PrintWriter pw = new PrintWriter(new FileOutputStream(ROOT_FOLDER + RAW_DATA_FOLDER + makeFileName()));
		
		pw.println(StaticMatchInfo.getInfo());
		pw.flush();
		pw.close();
	}
	
	private static String makeFileName()
	{
		//output as 2637_YYYY_MM_DD_MATCH_#
		//replays:  2637_YYYY_MM_DD_MATCH_#_R_#
		
		Calendar cal = new GregorianCalendar();
		
		if (StaticMatchInfo.replayNumber == 0)
		{
			return PATH_SEPARATOR + String.format("%d_%s_%s_%s_MATCH_%s.csv", 	
													StaticMatchInfo.teamNumber,
													cal.get(Calendar.YEAR),
													cal.get(Calendar.MONTH),
													cal.get(Calendar.DAY_OF_MONTH),
													StaticMatchInfo.matchNumber);
		}
		else
		{
			return PATH_SEPARATOR + String.format("%s_%s_%s_%s_MATCH_%s_REPLAY_%s.csv", 	
													StaticMatchInfo.teamNumber, 
													cal.get(Calendar.YEAR),
													cal.get(Calendar.MONTH),
													cal.get(Calendar.DAY_OF_MONTH),
													StaticMatchInfo.matchNumber,
													StaticMatchInfo.replayNumber);
		}
	}
	
	public static void compileFiles() //throws FileNotFoundException
	{
		File matchFileFolder = new File(ROOT_FOLDER + RAW_DATA_FOLDER);
		File compiledFolder = new File(ROOT_FOLDER + COMPILED_DATA_FOLDER);
		
		TeamList teams = new TeamList();
		
		if (!matchFileFolder.exists())
		{
			JOptionPane.showMessageDialog(null, "No files to compile.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if (!compiledFolder.isDirectory())
		{
			compiledFolder.mkdir();
		}
		
		File[] fileList = matchFileFolder.listFiles();
		
		for (File file : fileList)
		{
			
			String[] fileName = file.getName().replace(".csv", "").split("_");
			
			int teamNumber = Integer.parseInt(fileName[TEAM_NUMBER_INDEX]);
			int matchNumber = Integer.parseInt(fileName[MATCH_NUMBER_INDEX]);
			
			int replayNumber;
			
			try 
			{
				replayNumber = Integer.parseInt(fileName[REPLAY_NUMBER_INDEX]);
			}
			catch (Exception e)
			{
				replayNumber = 0;
			}
			
			teams.addTeam(teamNumber);
			
			try
			{
				Scanner scan = new Scanner(file);
				
				teams.getTeam(teamNumber).addToList(scan.nextLine().split(", "), matchNumber, replayNumber);
				
				scan.close();
			}
			catch (FileNotFoundException e)
			{
				
			}
		}
		
		int[] teamNumbers = teams.getListOfTeams();
		
		PrintWriter pw = null;
		
		for (int i = 0; i < teamNumbers.length; i++)
		{
			try 
			{
				pw = new PrintWriter(
						new FileOutputStream(
								ROOT_FOLDER + COMPILED_DATA_FOLDER + PATH_SEPARATOR 
								+ Integer.toString(teamNumbers[i]) + ".csv"));
				
				pw.print(teams.getTeam(teamNumbers[i]).toString());
				pw.flush();
				pw.close();
			}
			catch (FileNotFoundException e)
			{
				
			}
		}
	}
}