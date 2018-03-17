package components;

public interface IMatchUtils {
	
	//used to store information into MatchInfo
	public void matchSave();
	
	//called to reset the gui back to a blank slate
	public void matchReset();
		
	//called to enable all components after clicking the start
	//match button
	public void matchEnable(boolean enabled);
}
