package components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import processing.StaticMatchInfo;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

class ScorePane extends JPanel implements ActionListener, IMatchUtils
{
	enum scoreType
	{
		AllySwitch,
		OpponentSwitch,
		Scale;
	}
	
	private JButton placedIncrement, placedDecrement, missedIncrement, missedDecrement;
	private JTextField scorePaneTitle, placedField, missedField;
	private JPanel placedPane, missedPane;
	
	private int placedCount, missedCount;
	
	private final scoreType type;
	
	public ScorePane(String title, scoreType type)
	{
		this.type = type;
		placedCount = 0;
		missedCount = 0;
	
		initComponents(title);
	}
	
	private void initComponents(String title)
	{
		this.setLayout(new GridLayout(1, 5));
				
		scorePaneTitle = new JTextField(title, 1);
		scorePaneTitle.setHorizontalAlignment(SwingConstants.CENTER);
		scorePaneTitle.setFont(new Font("Ubuntu", Font.PLAIN, 16));
		scorePaneTitle.setEnabled(false);
		scorePaneTitle.setDisabledTextColor(Color.BLACK);
		
		placedField = new JTextField(Integer.toString(placedCount), 1);
		placedField.setHorizontalAlignment(SwingConstants.CENTER);
		placedField.setFont(new Font("Ubuntu", Font.PLAIN, 24));
		placedField.setEditable(false);
		placedField.setEnabled(false);
		placedField.setDisabledTextColor(Color.BLACK);
		
		missedField = new JTextField(Integer.toString(missedCount), 1);
		missedField.setHorizontalAlignment(SwingConstants.CENTER);
		missedField.setFont(new Font("Ubuntu", Font.PLAIN, 24));
		missedField.setEditable(false);
		missedField.setEnabled(false);
		missedField.setDisabledTextColor(Color.BLACK);
		
		placedIncrement = new JButton("+");
		placedIncrement.setFont(new Font("Ubuntu", Font.BOLD, 32));
		placedIncrement.setActionCommand("PLACED_INCREMENT");
		placedIncrement.addActionListener(this);
		
		placedDecrement = new JButton("-");
		placedDecrement.setFont(new Font("Ubuntu", Font.BOLD, 32));
		placedDecrement.setActionCommand("PLACED_DECREMENT");
		placedDecrement.addActionListener(this);
		
		placedPane = new JPanel();
		placedPane.setLayout(new GridLayout(2, 1));
		placedPane.add(placedIncrement);
		placedPane.add(placedDecrement);
		
		missedIncrement = new JButton("+");
		missedIncrement.setFont(new Font("Ubuntu", Font.BOLD, 32));
		missedIncrement.setActionCommand("MISSED_INCREMENT");
		missedIncrement.addActionListener(this);
		
		missedDecrement = new JButton("-");
		missedDecrement.setFont(new Font("Ubuntu", Font.BOLD, 32));
		missedDecrement.setActionCommand("MISSED_DECREMENT");
		missedDecrement.addActionListener(this);
		
		missedPane = new JPanel();
		missedPane.setLayout(new GridLayout(2, 1));
		missedPane.add(missedIncrement);
		missedPane.add(missedDecrement);
		
		this.add(scorePaneTitle);
		this.add(placedField);
		this.add(placedPane);
		this.add(missedField);
		this.add(missedPane);
	}
	
	public void matchSave()
	{
		if (this.type.equals(scoreType.AllySwitch))
		{
			StaticMatchInfo.allyCubesPlaced = placedCount;
			StaticMatchInfo.allyCubesMissed = missedCount;
		}
		else if (this.type.equals(scoreType.OpponentSwitch))
		{
			StaticMatchInfo.opponentCubesPlaced = placedCount;
			StaticMatchInfo.opponentCubesMissed = missedCount;
		}
		else if (this.type.equals(scoreType.Scale))
		{
			StaticMatchInfo.scaleCubesPlaced = placedCount;
			StaticMatchInfo.scaleCubesMissed = missedCount;
		}
	}
	
	public void matchReset()
	{
		placedCount = 0;
		missedCount = 0;
		
		placedField.setText(Integer.toString(placedCount));
		missedField.setText(Integer.toString(missedCount));
	}
	
	public void matchEnable(boolean enabled)
	{
		placedIncrement.setEnabled(enabled);
		placedDecrement.setEnabled(enabled);
		
		missedIncrement.setEnabled(enabled);
		missedDecrement.setEnabled(enabled);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		String cmd = e.getActionCommand();
		
		if (cmd.equals("PLACED_INCREMENT"))
		{
			placedField.setText(Integer.toString(++placedCount));
		}
		else if (cmd.equals("PLACED_DECREMENT") && placedCount > 0)
		{
			placedField.setText(Integer.toString(--placedCount));
		}
		else if (cmd.equals("MISSED_INCREMENT"))
		{
			missedField.setText(Integer.toString(++missedCount));
		}
		else if (cmd.equals("MISSED_DECREMENT") && missedCount > 0)
		{
			missedField.setText(Integer.toString(--missedCount));
		}
	}
}

public class ScoringPanel extends JPanel implements IMatchUtils
{
	private ScorePane topPane, midPane, botPane;
	private JTextField cubesPlaced, cubesMissed, l1, l2, l3;
	
	public ScoringPanel() 
	{
		initComponents();
	}
	
	private void initComponents()
	{
		this.setLayout(new GridBagLayout());
		GridBagConstraints constraints;
		
		l1 = new JTextField("", 1);
		l1.setEditable(false);
		l1.setEnabled(false);
		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 0.5;
		constraints.weighty = 0.2;
		this.add(l1, constraints);
		
		cubesPlaced = new JTextField("Cubes Placed", 1);
		cubesPlaced.setEditable(false);
		cubesPlaced.setHorizontalAlignment(SwingConstants.CENTER);
		cubesPlaced.setFont(new Font("Ubuntu", Font.PLAIN, 16));
		cubesPlaced.setEnabled(false);
		cubesPlaced.setDisabledTextColor(Color.BLACK);
		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.weightx = 0.5;
		constraints.weighty = 0.2;
		this.add(cubesPlaced, constraints);
		
		l2 = new JTextField("", 1);
		l2.setEditable(false);
		l2.setEnabled(false);
		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.gridx = 2;
		constraints.gridy = 0;
		constraints.weightx = 0.5;
		constraints.weighty = 0.2;
		this.add(l2, constraints);
		
		cubesMissed = new JTextField("Cubes Missed", 1);
		cubesMissed.setEditable(false);
		cubesMissed.setHorizontalAlignment(SwingConstants.CENTER);
		cubesMissed.setFont(new Font("Ubuntu", Font.PLAIN, 16));
		cubesMissed.setEnabled(false);
		cubesMissed.setDisabledTextColor(Color.BLACK);
		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.gridx = 3;
		constraints.gridy = 0;
		constraints.weightx = 0.5;
		constraints.weighty = 0.2;
		this.add(cubesMissed, constraints);
		
		l3 = new JTextField("", 1);
		l3.setEditable(false);
		l3.setEnabled(false);
		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.gridx = 4;
		constraints.gridy = 0;
		constraints.weightx = 0.5;
		constraints.weighty = 0.2;
		this.add(l3, constraints);
		
		topPane = new ScorePane("Ally Switch", ScorePane.scoreType.AllySwitch);
		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.weightx = 0.5;
		constraints.weighty = 0.5;
		constraints.gridwidth = 5;
		this.add(topPane, constraints);
		
		midPane = new ScorePane("Opponent Switch", ScorePane.scoreType.OpponentSwitch);
		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.weightx = 0.5;
		constraints.weighty = 0.5;
		constraints.gridwidth = 5;
		this.add(midPane, constraints);
		
		botPane = new ScorePane("Scale", ScorePane.scoreType.Scale);
		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.weightx = 0.5;
		constraints.weighty = 0.5;
		constraints.gridwidth = 5;
		this.add(botPane, constraints);
	}
	
	public void matchSave()
	{
		topPane.matchSave();
		midPane.matchSave();
		botPane.matchSave();
	}
	
	public void matchReset()
	{
		topPane.matchReset();
		midPane.matchReset();
		botPane.matchReset();
	}
		
	public void matchEnable(boolean enabled)
	{
		topPane.matchEnable(enabled);
		midPane.matchEnable(enabled);
		botPane.matchEnable(enabled);
	}
}
