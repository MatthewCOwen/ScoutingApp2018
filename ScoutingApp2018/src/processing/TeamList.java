package processing;

import java.util.ArrayList;

public class TeamList {
	
	private ArrayList<TeamInfo> list;
	
	public TeamList()
	{
		list = new ArrayList<TeamInfo>();
	}
	
	public boolean hasEntry(int teamNumber)
	{
		for (TeamInfo teamInfo : list)
		{
			if (teamNumber == teamInfo.getTeamNumber())
			{
				return true;
			}
		}
		
		return false;
	}
	
	public void addTeam(int teamNumber)
	{
		if (!hasEntry(teamNumber))
		{
			list.add(new TeamInfo(teamNumber));
		}
	}
	
	public TeamInfo getTeam(int teamNumber)
	{
		for (TeamInfo teamInfo : list)
		{
			if (teamNumber == teamInfo.getTeamNumber())
			{
				return teamInfo;
			}
		}
		
		return null;
	}
	
	public int[] getListOfTeams()
	{
		int[] retVal = new int[list.size()];
		
		for (int i = 0; i < list.size(); i++)
		{
			retVal[i] = list.get(i).getTeamNumber();
		}
		
		return retVal;
	}
	
}
