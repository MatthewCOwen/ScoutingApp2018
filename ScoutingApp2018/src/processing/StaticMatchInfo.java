package processing;

import java.util.Calendar;

//singleton class that the gui elements will funnel their data into
public class StaticMatchInfo {
	
	public static int teamNumber;
	public static int matchNumber;
	public static String teamColor;
	
	public static int replayNumber;
	public static int allyCubesPlaced;
	public static int allyCubesMissed;
	public static int opponentCubesPlaced;
	public static int opponentCubesMissed;
	public static int scaleCubesPlaced; 
	public static int scaleCubesMissed;
	public static int allyFenceCubes;
	public static int opponentFenceCubes; 
	public static int floorCubes;
	public static int portalCubes;
	public static int exchangeCubes;
	
	public static long matchStartTime;
	public static long startClimbTime;
	public static long finishClimbTime;
	
	public static String climbBegins;
	public static String climbEnds;
	public static int climbDuration;
	
	public static String comment;
	
	public static boolean autoLineCross;
	public static boolean autoSwitchPlace;
	public static boolean autoScalePlace;
	public static boolean isReplay;
	public static boolean anyOrientation;
	public static boolean smartPlacement;
	public static boolean hasClimbingHardware;
	public static boolean canGetFromPortal;
	public static boolean canPlaceInExchange;
	public static boolean failedClimb;
	public static boolean parkedOnPlatform;

	public static void setTimeStamps()
	{
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(startClimbTime - matchStartTime);
		
		climbBegins = String.format("%02d:%02d", cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));
		
		cal.setTimeInMillis(finishClimbTime - matchStartTime);
		
		climbEnds = String.format("%02d:%02d", cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));
		
		cal.setTimeInMillis(finishClimbTime - startClimbTime);
		
		climbDuration = cal.get(Calendar.MINUTE) * 60 + cal.get(Calendar.SECOND);
	}
	
	public static void getNextMatchAndTeam()
	{
		MatchTeamColor mtc = FileManager.getNextMatchAndTeam();
		
		if (mtc != null)
		{
			matchNumber = mtc.matchNumber;
			teamNumber = mtc.teamNumber;
			teamColor = mtc.teamColor;
		}
		else
		{
			matchNumber = 0;
			teamNumber = 0;
			teamColor = "u";
		}
	}
	
	public static void resetFields()
	{
		replayNumber = 0;
		
		matchStartTime = 0;
		startClimbTime = 0;
		finishClimbTime = 0;
		climbDuration = 0;
		
		allyCubesPlaced = 0;
		allyCubesMissed = 0;
		opponentCubesPlaced = 0;
		opponentCubesMissed = 0;
		scaleCubesPlaced = 0;
		scaleCubesMissed = 0;
		allyFenceCubes = 0;
		
		climbBegins = "No Climb";
		climbEnds = "No Climb";
		
		isReplay = false;
		anyOrientation = false;
		smartPlacement = false;
		hasClimbingHardware = false;
		canGetFromPortal = false;
		canPlaceInExchange = false;
		failedClimb = false;
		parkedOnPlatform = false;
		
		comment = "No comment";
	}
	
	public static String getInfo()
	{
		return String.format(  "%s, %s, %s, %s, %s, %s, %s,"
							+ " %s, %s, %s, %s, %s, %s, %s,"
							+ " %s, %s, %s, %s, %s, %s,"
							+ " %s, %s, %s, %s, %s, %s",
							
							teamColor,
							
							Boolean.toString(autoLineCross),
							Boolean.toString(autoScalePlace),
							Boolean.toString(autoLineCross),
							
							Boolean.toString(anyOrientation), 
							Boolean.toString(smartPlacement), 
							Boolean.toString(hasClimbingHardware), 
							Boolean.toString(canPlaceInExchange), 
							Boolean.toString(canGetFromPortal), 
							Boolean.toString(parkedOnPlatform),
							
							Integer.toString(allyCubesPlaced), 
							Integer.toString(allyCubesMissed),
							Integer.toString(opponentCubesPlaced), 
							Integer.toString(opponentCubesMissed),
							Integer.toString(scaleCubesPlaced), 
							Integer.toString(scaleCubesMissed),
							
							Integer.toString(allyFenceCubes), 
							Integer.toString(opponentFenceCubes), 
							Integer.toString(floorCubes), 
							Integer.toString(portalCubes), 
							Integer.toString(exchangeCubes),
							
							climbBegins, 
							climbEnds,
							Boolean.toString(failedClimb), 
							Integer.toString(climbDuration),
							
							comment);
	}
}

class MatchInfo implements Comparable<MatchInfo> {
	
	public int matchNumber;
	public String teamColor;
	public boolean isReplay;
	public int replayNumber;
	
	public boolean autoLineCross;
	public boolean autoSwitchPlace;
	public boolean autoScalePlace;
	public boolean anyOrientation;
	public boolean smartPlacement;
	public boolean hasClimbingHardware;
	public boolean canGetFromPortal;
	public boolean canPlaceInExchange;
	public boolean parkedOnPlatform;
	
	public int allyCubesPlaced;
	public int allyCubesMissed;
	public int opponentCubesPlaced;
	public int opponentCubesMissed;
	public int scaleCubesPlaced; 
	public int scaleCubesMissed;
	
	public int allyFenceCubes;
	public int opponentFenceCubes; 
	public int floorCubes;
	public int portalCubes;
	public int exchangeCubes;
	
	public String climbBegins;
	public String climbEnds;
	public boolean failedClimb;
	public int climbDuration;
	
	public String comment;
	
	public MatchInfo(String[] args, int matchNumber, int replayNumber)
	{
		
		this.matchNumber = matchNumber;
		this.replayNumber = replayNumber;
		this.isReplay = replayNumber != 0;
		
		teamColor 				= args[0];
		
		autoLineCross			= Boolean.parseBoolean(args[1]);
		autoSwitchPlace 		= Boolean.parseBoolean(args[2]);
		autoScalePlace 			= Boolean.parseBoolean(args[3]);
		
		anyOrientation 			= Boolean.parseBoolean(args[4]);
		smartPlacement 			= Boolean.parseBoolean(args[5]);
		hasClimbingHardware		= Boolean.parseBoolean(args[6]);
		canPlaceInExchange 		= Boolean.parseBoolean(args[7]);
		canGetFromPortal 		= Boolean.parseBoolean(args[8]);
		parkedOnPlatform		= Boolean.parseBoolean(args[9]);
		
		allyCubesPlaced 		= Integer.parseInt(args[10]);
		allyCubesMissed 		= Integer.parseInt(args[11]);
		opponentCubesPlaced 	= Integer.parseInt(args[12]);
		opponentCubesMissed		= Integer.parseInt(args[13]);
		scaleCubesPlaced		= Integer.parseInt(args[14]);
		scaleCubesMissed		= Integer.parseInt(args[15]);
		
		allyFenceCubes			= Integer.parseInt(args[16]);
		opponentFenceCubes		= Integer.parseInt(args[17]);
		floorCubes 				= Integer.parseInt(args[18]);
		exchangeCubes 			= Integer.parseInt(args[19]);
		portalCubes				= Integer.parseInt(args[20]);
		
		climbBegins				= args[21];
		climbEnds				= args[22];
		failedClimb				= Boolean.parseBoolean(args[23]);
		climbDuration			= Integer.parseInt(args[24]);
		
		comment					= args[25];
	}
	
	public String toString()
	{
	
		return String.format( "%n%s, %s, %s, %s, %s, %s, "
							+	"%s, %s, %s, %s, %s, %s, "
							+	"%s, %s, %s, %s, %s, %s, "
							+	"%s, %s, %s, %s, %s, %s, "
							+	"%s, %s, %s, %s, %s",
				
							matchNumber,
							isReplay ? "Yes" : "No",
							replayNumber,
							teamColor,
							
							autoLineCross ? "Yes" : "No",
							autoSwitchPlace ? "Yes" : "No",
							autoScalePlace ? "Yes" : "No",
							
							anyOrientation ? "Yes" : "No",
							smartPlacement ? "Yes" : "No",
							hasClimbingHardware ? "Yes" : "No",
							canGetFromPortal ? "Yes" : "No",
							canPlaceInExchange ? "Yes" : "No",
							parkedOnPlatform ? "Yes" : "No",
							
							allyCubesPlaced,
							allyCubesMissed,
							opponentCubesPlaced,
							opponentCubesMissed,
							scaleCubesPlaced,
							scaleCubesMissed,
							
							allyFenceCubes,
							opponentFenceCubes,
							floorCubes,
							exchangeCubes,
							portalCubes,
																
							climbBegins,
							climbEnds,
							failedClimb ? "Yes" : "No",
							climbDuration,	
							
							comment);
	}

	public int compareTo(MatchInfo other) 
	{
		return this.matchNumber > other.matchNumber ? 1 : -1;
	}
}
