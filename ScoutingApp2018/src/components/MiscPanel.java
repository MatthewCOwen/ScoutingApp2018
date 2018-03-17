package components;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;

import processing.StaticMatchInfo;

/*
 * 	the class 'MyIntFilter' was copied from:
 * 	https://stackoverflow.com/questions/11093326/restricting-jtextfield-input-to-integers
 */

class MyIntFilter extends DocumentFilter 
{
	@Override
	public void insertString(FilterBypass fb, int offset, String string,
		   					AttributeSet attr) throws BadLocationException 
	{
		Document doc = fb.getDocument();
		StringBuilder sb = new StringBuilder();
		sb.append(doc.getText(0, doc.getLength()));
		sb.insert(offset, string);

		if (test(sb.toString())) 
		{
			super.insertString(fb, offset, string, attr);
		} 
		else 
		{
			// warn the user and don't allow the insert
		}
	}

	private boolean test(String text) 
	{
		try 
		{
			Integer.parseInt(text);
			return true;
		} 
		catch (NumberFormatException e) 
		{
			return false;
		}
	}

	@Override
	public void replace(FilterBypass fb, int offset, int length, String text,
						AttributeSet attrs) throws BadLocationException 
	{
		Document doc = fb.getDocument();
		StringBuilder sb = new StringBuilder();
		sb.append(doc.getText(0, doc.getLength()));
		sb.replace(offset, offset + length, text);

		if (test(sb.toString())) 
		{
			super.replace(fb, offset, length, text, attrs);
		} 
		else 
		{
			// warn the user and don't allow the insert
		}
	}
	
	/*
	 *	I tweaked the function to allow the deletion of the last remaining digit
	 */

	@Override
	public void remove(FilterBypass fb, int offset, int length) 
									throws BadLocationException 
	{
		Document doc = fb.getDocument();
		StringBuilder sb = new StringBuilder();
		sb.append(doc.getText(0, doc.getLength()));
		sb.delete(offset, offset + length);

		if (test(sb.toString()) || sb.length() == 0) 
		{
			super.remove(fb, offset, length);
		} 
		else
		{
			// warn the user and don't allow the insert
		}
	}
}

class TimerPane extends JPanel implements ActionListener, IMatchUtils
{
	private JButton startTimer;
	private ScoutingApp frame;

	public TimerPane(ScoutingApp frame)
	{
		this.frame = frame;
		
		initComponents();
	}
	
	private void initComponents()
	{
		this.setLayout(new GridBagLayout());
		GridBagConstraints constraints;	

		startTimer = new JButton("Match Started");
		startTimer.setFont(new Font("Ubuntu", Font.PLAIN, 24));
		startTimer.setActionCommand("START_TIMER");
		startTimer.addActionListener(this);
		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 0.5;
		constraints.weighty = 0.5;
		constraints.gridwidth = 2;
		constraints.gridheight = 2;
		this.add(startTimer, constraints);	
	}
	
	public void matchSave()
	{
		
	}
	
	public void matchReset()
	{
		startTimer.setEnabled(true);
		startTimer.setText("Match Started");
	}
	
	public void matchEnable(boolean enabled)
	{
		
	}
	
	public void actionPerformed(ActionEvent e)
	{
		String cmd = e.getActionCommand();
		
		if (cmd.equals("START_TIMER"))
		{
			StaticMatchInfo.matchStartTime = Calendar.getInstance().getTimeInMillis();
			frame.matchEnable(true);
			startTimer.setText("Match In Progress");
			startTimer.setEnabled(false);
		}
	}
}

class CommentPane extends JDialog implements ActionListener, IMatchUtils
{
	private JTextArea commentArea;
	private JButton save;
	private JFrame frame;
		
	public CommentPane(JFrame frame)
	{
		super(frame, "Add Comment", true);
		
		this.frame = frame;
		initComponents();
	}
	
	private void initComponents()
	{
		this.setLayout(new GridBagLayout());
		GridBagConstraints constraints;
		commentArea = new JTextArea();
		commentArea.setFont(new Font("Ubuntu", Font.PLAIN, 16));
		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 0.5;
		constraints.weighty = 0.8;
		constraints.gridwidth = 3;
		constraints.gridheight = 3;
		this.add(commentArea, constraints);
		
		save = new JButton("Save");
		save.setFont(new Font("Ubuntu", Font.PLAIN, 32));
		save.setActionCommand("SAVE");
		save.addActionListener(this);
		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.weightx = 0.5;
		constraints.weighty = 0.2;
		this.add(save, constraints);
		
		//setting the placement of the popup window relative to the application
		Point p = frame.getLocation();
		this.setLocation(p.x + (frame.getWidth() / 3), p.y);
		
		this.setMinimumSize(new Dimension(800, 600));
		this.pack();
		this.setVisible(true);
	}
	
	public void matchSave()
	{
		
	}
	
	public void matchReset()
	{
		
	}
	
	public void matchEnable(boolean enabled)
	{
		
	}
	
	public void actionPerformed(ActionEvent e)
	{
		String cmd = e.getActionCommand();
		
		if (cmd.equals("SAVE"))
		{
			StaticMatchInfo.comment = commentArea.getText().replaceAll(",", "");
			
			this.dispose();
		}
	}
}

@SuppressWarnings("serial")
class TeamPane extends JPanel implements ActionListener, IMatchUtils
{
	private static final String[] colors = {"RED", "BLUE"};
	
	private JComboBox<String> colorChoice;
	private JTextField teamField, matchField;
	private JLabel teamLabel, matchLabel, teamColorLabel;
	private JButton addComment;
	private JCheckBox isReplayGame;
	private CommentPane commentPane;
	private JFrame frame;
	
	public TeamPane(JFrame frame)
	{
		this.frame = frame;
		StaticMatchInfo.getNextMatchAndTeam();
		initComponents();
	}
	
	private void initComponents()
	{
		this.setLayout(new GridBagLayout());
		GridBagConstraints constraints;
		
		matchLabel = new JLabel("Match Number", SwingConstants.CENTER);
		matchLabel.setFont(new Font("Ubuntu", Font.PLAIN, 16));
		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 0.5;
		constraints.weighty = 0.5;
		this.add(matchLabel, constraints);
		
		matchField = new JTextField(Integer.toString(StaticMatchInfo.matchNumber));
		//matchField.setEditable(StaticMatchInfo.matchNumber == 0 ? true : false);
		matchField.setHorizontalAlignment(SwingConstants.CENTER);
		matchField.setFont(new Font("Ubuntu", Font.PLAIN, 16));
		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.weightx = 0.5;
		constraints.weighty = 0.5;
		this.add(matchField, constraints);
		
		PlainDocument matchDoc = (PlainDocument) matchField.getDocument();
		matchDoc.setDocumentFilter(new MyIntFilter());
		
		isReplayGame = new JCheckBox("Is Replay Match?");
		isReplayGame.setFont(new Font("Ubuntu", Font.PLAIN, 16));
		isReplayGame.setActionCommand("IS_REPLAY");
		isReplayGame.addActionListener(this);
		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.gridx = 2;
		constraints.gridy = 0;
		constraints.weightx = 0.5;
		constraints.weighty = 0.5;
		this.add(isReplayGame, constraints);
		
		teamLabel = new JLabel("Team Number", SwingConstants.CENTER);
		teamLabel.setFont(new Font("Ubuntu", Font.PLAIN, 16));
		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.weightx = 0.5;
		constraints.weighty = 0.5;
		this.add(teamLabel, constraints);
		
		teamField = new JTextField(Integer.toString(StaticMatchInfo.teamNumber));
		//teamField.setEditable(StaticMatchInfo.teamNumber == 0 ? true : false);
		teamField.setFont(new Font("Ubuntu", Font.PLAIN, 16));
		teamField.setHorizontalAlignment(SwingConstants.CENTER);
		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.weightx = 0.5;
		constraints.weighty = 0.5;
		this.add(teamField, constraints);
		
		PlainDocument teamDoc = (PlainDocument) teamField.getDocument();
		teamDoc.setDocumentFilter(new MyIntFilter());
		
		teamColorLabel = new JLabel("Team Color", SwingConstants.CENTER);
		teamColorLabel.setFont(new Font("Ubuntu", Font.PLAIN, 16));
		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.weightx = 0.5;
		constraints.weighty = 0.5;
		this.add(teamColorLabel, constraints);
		
		colorChoice = new JComboBox<String>(colors);
		colorChoice.setFont(new Font("Ubuntu", Font.PLAIN, 16));
		colorChoice.setSelectedIndex(StaticMatchInfo.teamColor.equalsIgnoreCase("r") ? 0 : 1);
		//colorChoice.setEnabled(StaticMatchInfo.teamColor.equalsIgnoreCase("u") ? true : false);
		
		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.gridx = 1;
		constraints.gridy = 2;
		constraints.weightx = 0.5;
		constraints.weighty = 0.5;
		this.add(colorChoice, constraints);

		addComment = new JButton("Add Comment");
		addComment.setFont(new Font("Ubuntu", Font.PLAIN, 24));
		addComment.setActionCommand("ADD_COMMENT");
		addComment.addActionListener(this);
		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.gridx = 2;
		constraints.gridy = 1;
		constraints.weightx = 0.5;
		constraints.weighty = 0.5;
		constraints.gridheight = 2;
		this.add(addComment, constraints);
	}
	
	public void matchSave()
	{
		if (StaticMatchInfo.teamNumber == 0)
		{
			StaticMatchInfo.teamNumber = Integer.parseInt(teamField.getText());
		}
		
		if (StaticMatchInfo.matchNumber == 0 || isReplayGame.isSelected())
		{
			try
			{
				StaticMatchInfo.matchNumber = Integer.parseInt(matchField.getText());
			}
			catch (NumberFormatException e)
			{
				StaticMatchInfo.matchNumber = 0;
			}
		}
	
		if (StaticMatchInfo.teamColor.equals("u"))
		{
			StaticMatchInfo.teamColor = (String) colorChoice.getSelectedItem();
		}
	}
	
	public void matchReset()
	{
		StaticMatchInfo.getNextMatchAndTeam();
		//matchField.setEditable(false);
		//teamField.setEditable(false);
		
		matchField.setText(Integer.toString(StaticMatchInfo.matchNumber));
		teamField.setText(Integer.toString(StaticMatchInfo.teamNumber));
		colorChoice.setSelectedIndex(StaticMatchInfo.teamColor.equals("RED") ? 0 : 1);
	}
	
	public void matchEnable(boolean enabled)
	{
		addComment.setEnabled(enabled);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		String cmd = e.getActionCommand();
		
		if (cmd.equals("IS_REPLAY"))
		{
			StaticMatchInfo.isReplay = isReplayGame.isSelected();
			
			if (StaticMatchInfo.isReplay)
			{
				Object[] nums = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
				
				try
				{
					StaticMatchInfo.replayNumber = Integer.parseInt((String)JOptionPane.showInputDialog(
																		null,
																		"What Replay Is This?",
																		"Replay Number",
																		JOptionPane.PLAIN_MESSAGE,
																		null,
																		nums,
																		"1"));
				}
				catch (NumberFormatException nfe)
				{
					isReplayGame.setSelected(false);
					StaticMatchInfo.isReplay = false;
					StaticMatchInfo.replayNumber = 0;
				}
			}
			
			matchField.setEditable(true);
		}
		else if (cmd.equals("ADD_COMMENT"))
		{
			commentPane = new CommentPane(frame);
		}
	}
}

class CheckBoxPane extends JPanel implements ActionListener, IMatchUtils
{	
	private JLabel l1, l2;
	private JCheckBox autoLineCross, autoSwitchPlace, autoScalePlace,
						orientation, strategicPlacement, canClimb, 
						canRecieveFromPortal, canExchange;
	
	public CheckBoxPane()
	{	
		initComponents();
	}
	
	private void initComponents()
	{
		this.setLayout(new GridLayout(10, 1));

		l1 = new JLabel("During autonomous, did the robot...", SwingConstants.LEFT);
		l1.setFont(new Font("Ubuntu", Font.BOLD, 16));
		this.add(l1);
		
		autoLineCross = new JCheckBox("cross over the auto-line?");
		autoLineCross.setFont(new Font("Ubuntu", Font.PLAIN, 16));
		autoLineCross.setActionCommand("AUTO_LINE_CROSSED");
		autoLineCross.addActionListener(this);
		this.add(autoLineCross);
		
		autoSwitchPlace = new JCheckBox("attempt to place a cube on the correct side of the switch?");
		autoSwitchPlace.setFont(new Font("Ubuntu", Font.PLAIN, 16));
		autoSwitchPlace.setActionCommand("AUTO_SWITCH_PLACED");
		autoSwitchPlace.addActionListener(this);
		this.add(autoSwitchPlace);
		
		autoScalePlace = new JCheckBox("attempt to place a cube on the correct side of the scale?");
		autoScalePlace.setFont(new Font("Ubuntu", Font.PLAIN, 16));
		autoScalePlace.setActionCommand("AUTO_SCALE_PLACED");
		autoScalePlace.addActionListener(this);
		this.add(autoScalePlace);
		
		l2 = new JLabel("Is the robot capable of...");
		l2.setFont(new Font("Ubuntu", Font.BOLD, 16));
		this.add(l2);
		
		orientation = new JCheckBox("picking up cubes in any orientation?");
		orientation.setFont(new Font("Ubuntu", Font.PLAIN, 16));
		orientation.setActionCommand("ORIENTATION");
		orientation.addActionListener(this);
		this.add(orientation);
		
		strategicPlacement = new JCheckBox("placing cubes strategically?");
		strategicPlacement.setFont(new Font("Ubuntu", Font.PLAIN, 16));
		strategicPlacement.setActionCommand("STRATEGIC_PLACEMENT");
		strategicPlacement.addActionListener(this);
		this.add(strategicPlacement);
		
		canClimb = new JCheckBox("climbing?");
		canClimb.setFont(new Font("Ubuntu", Font.PLAIN, 16));
		canClimb.setActionCommand("CAN_CLIMB");
		canClimb.addActionListener(this);
		this.add(canClimb);
				
		canRecieveFromPortal = new JCheckBox("recieving cubes from the portal?");
		canRecieveFromPortal.setFont(new Font("Ubuntu", Font.PLAIN, 16));
		canRecieveFromPortal.setActionCommand("CAN_RECIEVE_FROM_PORTAL");
		canRecieveFromPortal.addActionListener(this);
		this.add(canRecieveFromPortal);
		
		canExchange = new JCheckBox("placing cubes in the exchange?");
		canExchange.setFont(new Font("Ubuntu", Font.PLAIN, 16));
		canExchange.setActionCommand("CAN_EXCHANGE");
		canExchange.addActionListener(this);
		this.add(canExchange);
	}
	
	public void matchSave()
	{
		
	}

	public void matchReset()
	{
		autoLineCross.setSelected(false);
		autoSwitchPlace.setSelected(false);
		autoScalePlace.setSelected(false);
				
		orientation.setSelected(false);
		strategicPlacement.setSelected(false);
		canClimb.setSelected(false);
		canRecieveFromPortal.setSelected(false);
		canExchange.setSelected(false);
	}
	
	public void matchEnable(boolean enabled)
	{
		autoLineCross.setEnabled(enabled);
		autoSwitchPlace.setEnabled(enabled);
		autoScalePlace.setEnabled(enabled);
		
		l1.setEnabled(enabled);
		l2.setEnabled(enabled);
		
		orientation.setEnabled(enabled);
		strategicPlacement.setEnabled(enabled);
		canClimb.setEnabled(enabled);
		canRecieveFromPortal.setEnabled(enabled);
		canExchange.setEnabled(enabled);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		String cmd = e.getActionCommand();
		
		if (cmd.equals("ORIENTATION"))
		{
			StaticMatchInfo.anyOrientation = ((JCheckBox)e.getSource()).isSelected();
		}
		else if (cmd.equals("STRATEGIC_PLACEMENT"))
		{
			StaticMatchInfo.smartPlacement = ((JCheckBox)e.getSource()).isSelected();
		}
		else if (cmd.equals("CAN_CLIMB"))
		{
			StaticMatchInfo.hasClimbingHardware = ((JCheckBox)e.getSource()).isSelected();
		}
		else if (cmd.equals("CAN_RECIEVE_FROM_PORTAL"))
		{
			StaticMatchInfo.canGetFromPortal = ((JCheckBox)e.getSource()).isSelected();
		}
		else if (cmd.equals("CAN_EXCHANGE"))
		{
			StaticMatchInfo.canPlaceInExchange = ((JCheckBox)e.getSource()).isSelected();
		}
		else if (cmd.equals("AUTO_LINE_CROSSED"))
		{
			StaticMatchInfo.autoLineCross = autoLineCross.isSelected();
		}
		else if (cmd.equals("AUTO_SWITCH_PLACED"))
		{
			StaticMatchInfo.autoSwitchPlace = autoSwitchPlace.isSelected();
		}
		else if (cmd.equals("AUTO_SCALE_PLACED"))
		{
			StaticMatchInfo.autoScalePlace = autoScalePlace.isSelected();
		}
	}
}


@SuppressWarnings("serial")
class ClimbPane extends JPanel implements ActionListener, IMatchUtils
{
	private JButton startClimb, finishClimb;
	private JCheckBox failedClimb, parkedOnPlatform;
	private JTextField timeAtStart, timeAtEnd;
	
	public ClimbPane()
	{
		initComponents();
	}
	
	private void initComponents()
	{
		this.setLayout(new GridBagLayout());
		GridBagConstraints constraints;
		
		startClimb = new JButton("Robot Started Climb");
		startClimb.setActionCommand("STARTED_CLIMB");
		startClimb.addActionListener(this);
		startClimb.setFont(new Font("Ubuntu", Font.PLAIN, 16));
		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.weightx = 0.5;
		constraints.weighty = 0.5;
		constraints.gridx = 0;
		constraints.gridy = 0;
		this.add(startClimb, constraints);

		timeAtStart = new JTextField("00:00", 1);
		timeAtStart.setEditable(false);
		timeAtStart.setFont(new Font("Ubuntu", Font.PLAIN, 16));
		timeAtStart.setHorizontalAlignment(SwingConstants.CENTER);
		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.weightx = 0.5;
		constraints.weighty = 0.5;
		constraints.gridx = 1;
		constraints.gridy = 0;
		this.add(timeAtStart, constraints);
		
		finishClimb = new JButton("Robot Finished Climb");
		finishClimb.setEnabled(false);
		finishClimb.setActionCommand("FINISHED_CLIMB");
		finishClimb.addActionListener(this);
		finishClimb.setFont(new Font("Ubuntu", Font.PLAIN, 16));
		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.weightx = 0.5;
		constraints.weighty = 0.5;
		constraints.gridx = 0;
		constraints.gridy = 1;
		this.add(finishClimb, constraints);
	
		timeAtEnd = new JTextField("00:00", 1);
		timeAtEnd.setEditable(false);
		timeAtEnd.setFont(new Font("Ubuntu", Font.PLAIN, 16));
		timeAtEnd.setHorizontalAlignment(SwingConstants.CENTER);
		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.weightx = 0.5;
		constraints.weighty = 0.5;
		constraints.gridx = 1;
		constraints.gridy = 1;
		this.add(timeAtEnd, constraints);
		
		failedClimb = new JCheckBox("Robot Failed Climb");
		failedClimb.setFont(new Font("Ubuntu", Font.PLAIN, 16));
		failedClimb.setActionCommand("FAILED_CLIMB");
		failedClimb.addActionListener(this);
		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.weightx = 0.5;
		constraints.weighty = 0.5;
		constraints.gridx = 0;
		constraints.gridy = 2;
		this.add(failedClimb, constraints);
		
		parkedOnPlatform = new JCheckBox("Robot parked on platform");
		parkedOnPlatform.setFont(new Font("Ubuntu", Font.PLAIN, 16));
		parkedOnPlatform.setActionCommand("RECIEVED_LEVITATE");
		parkedOnPlatform.addActionListener(this);
		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.weightx = 0.5;
		constraints.weighty = 0.5;
		constraints.gridx = 1;
		constraints.gridy = 2;
		this.add(parkedOnPlatform, constraints);
	}
	
	public void matchSave()
	{
		
	}
	
	public void matchReset()
	{
		failedClimb.setSelected(false);
		parkedOnPlatform.setSelected(false);
		
		timeAtStart.setText("00:00");
		timeAtEnd.setText("00:00");
	}
	
	public void matchEnable(boolean enabled)
	{
		startClimb.setEnabled(enabled);
		timeAtStart.setEnabled(enabled);
		timeAtEnd.setEnabled(enabled);
		failedClimb.setEnabled(enabled);
		parkedOnPlatform.setEnabled(enabled);
		
		if (!enabled)
		{
			finishClimb.setEnabled(enabled);
		}
	}
	
	public void actionPerformed(ActionEvent e)
	{
		String cmd = e.getActionCommand();
		
		if (cmd.equals("STARTED_CLIMB"))
		{
			StaticMatchInfo.startClimbTime = Calendar.getInstance().getTimeInMillis();
			StaticMatchInfo.setTimeStamps();
			timeAtStart.setText(StaticMatchInfo.climbBegins);
			
			startClimb.setEnabled(false);
			finishClimb.setEnabled(true);
		}
		else if (cmd.equals("FINISHED_CLIMB"))
		{
			StaticMatchInfo.finishClimbTime = Calendar.getInstance().getTimeInMillis();
			StaticMatchInfo.setTimeStamps();
			timeAtEnd.setText(StaticMatchInfo.climbEnds);
			
			finishClimb.setEnabled(false);
		}
		else if (cmd.equals("FAILED_CLIMB"))
		{
			StaticMatchInfo.failedClimb = ((JCheckBox)e.getSource()).isSelected();
		}
		else if (cmd.equals("RECIEVED_LEVITATE"))
		{
			StaticMatchInfo.parkedOnPlatform = ((JCheckBox)e.getSource()).isSelected();
		}
	}
}

@SuppressWarnings("serial")
public class MiscPanel extends JPanel implements IMatchUtils 
{
	private TimerPane timePane;
	private TeamPane teamPane;
	private CheckBoxPane cbPane;
	private ClimbPane climbPane;
	
	private ScoutingApp frame;
	
	public MiscPanel(ScoutingApp frame)
	{
		this.frame = frame;
		initComponents();
	}
	
	private void initComponents()
	{
		this.setLayout(new GridBagLayout());		
		GridBagConstraints constraints;

		timePane = new TimerPane(frame);
		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 0.5;
		constraints.weighty = 0.5;
		this.add(timePane, constraints);
		
		teamPane = new TeamPane(frame);
		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.weightx = 0.5;
		constraints.weighty = 0.5;
		this.add(teamPane, constraints);
		
		cbPane = new CheckBoxPane();
		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.weightx = 0.5;
		constraints.weighty = 0.5;
		this.add(cbPane, constraints);
		
		climbPane = new ClimbPane();
		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.weightx = 0.5;
		constraints.weighty = 0.5; 
		this.add(climbPane, constraints);
	}
	
	public void matchSave()
	{
		timePane.matchSave();
		teamPane.matchSave();
		cbPane.matchSave();
		climbPane.matchSave();
	}
	
	public void matchReset()
	{
		timePane.matchReset();
		teamPane.matchReset();
		cbPane.matchReset();
		climbPane.matchReset();
	}
	
	public void matchEnable(boolean enabled)
	{
		timePane.matchEnable(enabled);
		teamPane.matchEnable(enabled);
		cbPane.matchEnable(enabled);
		climbPane.matchEnable(enabled);
	}
}