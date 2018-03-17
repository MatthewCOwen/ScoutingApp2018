package processing;

import java.util.ArrayList;
import java.util.Comparator;

class MatchComparator implements Comparator<MatchInfo>
{
	public int compare(MatchInfo m1, MatchInfo m2)
	{
		return m1.compareTo(m2);
	}
}

public class TeamInfo {
	
	private final int teamNumber;
	
	private ArrayList<MatchInfo> matches;
	
	public TeamInfo(int teamNumber)
	{
		this.teamNumber = teamNumber;
		
		matches = new ArrayList<>();
	}
	
	public void addToList(String[] data, int teamNumber, int replayNumber)
	{
		matches.add(new MatchInfo(data, teamNumber, replayNumber));
	}
	
	public int getTeamNumber()
	{
		return teamNumber;
	}
	
	public String toString()
	{
		matches.sort(new MatchComparator());
		
		String retVal = String.format(	"%s, %s, %s, %s, %s, %s, "
									+	"%s, %s, %s, %s, %s, %s, "
									+	"%s, %s, %s, %s, %s, %s, "
									+	"%s, %s, %s, %s, %s, %s, "
									+	"%s, %s, %s, %s, %s",
									
									"Match Number",
									"Match Replay?",
									"Replay Match",
									"Alliance Color",
									
									"Autonomous-Line Crossed",
									"Autonomous Switch Attempt",
									"Autonomous Scale Attempt",
									
									"Robot Can Pick Up Cubes In Any Orientation",
									"Robot Can Place Cubes Strategically",
									"Robot Has Climbing Mechanism",
									"Robot Can Place Cubes In The Exchange",
									"Robot Can Recieve Cubes From The Portal",
									"Robot Parked On The Platform At Match End",
									
									"Cubes Scored On Alliance Switch",
									"Cubes Missed On Alliance Switch",
									"Cubes Scored On Opponent's Switch",
									"Cubes Missed On Opponent's Switch",
									"Cubes Scored On The Scale",
									"Cubes Missed On The Scale",
									
									"Cubes Picked Up From Alliance Fence",
									"Cubes Picked Up From Opponent's Fence",
									"Cubes Picked Up From The Floor",
									"Cubes Placed In Exchange",
									"Cubes Recieved From Portal",
									
									"Robot Began Climb",
									"Robot Finished Climb",
									"Robot Successfully Climbed",
									"Climb Duration (sec)",
									
									"Additional Comments");
		
		for (MatchInfo match : matches)
		{
			retVal += match;
		}
		
		return retVal;
	}
}