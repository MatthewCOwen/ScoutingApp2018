package components;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import processing.StaticMatchInfo;

class LocationPanel extends JPanel implements ActionListener, IMatchUtils
{
	enum Locations 
	{
		allyFence,
		opponentFence,
		floor,
		portal,
		exchange
	}
	
	private JTextField cubeCount, location;
	private JButton increment, decrement;
	
	private Locations loc;
	private int count;
	
	public LocationPanel(String title, Locations loc)
	{
		count = 0;
		this.loc = loc;
		initComponents(title);
	}
	
	private void initComponents(String title)
	{
		GridBagConstraints constraints;
		
		this.setLayout(new GridBagLayout());
		
		location = new JTextField(title, 1);
		location.setHorizontalAlignment(SwingConstants.CENTER);
		location.setEditable(false);
		location.setFont(new Font("Ubuntu", Font.PLAIN, 16));
		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 1.0;
		constraints.weighty = 0.2;
		constraints.gridwidth = 3;
		this.add(location, constraints);
		
		cubeCount = new JTextField(Integer.toString(count), 1);
		cubeCount.setHorizontalAlignment(SwingConstants.CENTER);
		cubeCount.setFont(new Font("Ubuntu", Font.PLAIN, 16));
		cubeCount.setBackground(Color.WHITE);
		cubeCount.setOpaque(true);
		cubeCount.setEditable(false);
		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.weightx = 0.6;
		constraints.weighty = 0.8;
		//constraints.gridwidth = 2;
		constraints.gridheight = 2;
//		constraints.insets = new Insets(4, 4, 4, 4);
		this.add(cubeCount, constraints);
		
		increment = new JButton("+");
		increment.setFont(new Font("Ubuntu", Font.BOLD, 32));
		increment.setActionCommand("ADD");
		increment.addActionListener(this);
		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.gridx = 2;
		constraints.gridy = 1;
		constraints.weightx = 0.4;
		constraints.weighty = 0.4;
		//constraints.insets = new Insets(8, 8, 8, 8);
		this.add(increment, constraints);
		
		decrement = new JButton("-");
		decrement.setFont(new Font("Ubuntu", Font.BOLD, 32));
		decrement.setActionCommand("SUBTRACT");
		decrement.addActionListener(this);
		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.gridx = 2;
		constraints.gridy = 2;
		constraints.weightx = 0.4;
		constraints.weighty = 0.4;
		//constraints.insets = new Insets(8, 8, 8, 8);
		this.add(decrement, constraints);
	}
	
	public void matchSave()
	{
		if (loc.equals(Locations.allyFence))
		{
			StaticMatchInfo.allyFenceCubes = count;
		}
		else if (loc.equals(Locations.opponentFence))
		{
			StaticMatchInfo.opponentFenceCubes = count;
		}
		else if (loc.equals(Locations.floor))
		{
			StaticMatchInfo.floorCubes = count;
		}
		else if (loc.equals(Locations.portal))
		{
			StaticMatchInfo.portalCubes = count;
		}
		else
		{
			StaticMatchInfo.exchangeCubes = count;
		}
	}
	
	public void matchReset()
	{
		count = 0;
		cubeCount.setText(Integer.toString(count));
	}
	
	public void matchEnable(boolean enabled)
	{
		increment.setEnabled(enabled);
		decrement.setEnabled(enabled);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		String cmd = e.getActionCommand();
		
		if (cmd.equals("ADD"))
		{
			cubeCount.setText(Integer.toString(++count));
		}
		else if (cmd.equals("SUBTRACT") && count > 0)
		{
			cubeCount.setText(Integer.toString(--count));
		}
	}
}

@SuppressWarnings("serial")
public class CubePickUpPanel extends JPanel implements IMatchUtils
{
	private JLabel description;
	private LocationPanel allyFence, oppoFence, floor, portal, exchange;
	
	public CubePickUpPanel()
	{
		initComponents();
	}
	
	private void initComponents()
	{
		this.setLayout(new GridBagLayout());
		GridBagConstraints constraints;
		
		description = new JLabel("Blocks Picked Up From:", SwingConstants.CENTER);
		description.setFont(new Font("Ubuntu", Font.PLAIN, 16));
		description.setOpaque(true);
		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.weightx = 0.5;
		constraints.weighty = 0.5;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 5;
		this.add(description, constraints);
		
		allyFence = new LocationPanel("Ally Fence", LocationPanel.Locations.allyFence);
		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.weightx = 0.5;
		constraints.weighty = 0.5;
		constraints.gridx = 0;
		constraints.gridy = 1;
		this.add(allyFence, constraints);
		
		oppoFence = new LocationPanel("Opp. Fence", LocationPanel.Locations.opponentFence);
		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.weightx = 0.5;
		constraints.weighty = 0.5;
		constraints.gridx = 1;
		constraints.gridy = 1;
		this.add(oppoFence, constraints);
		
		floor = new LocationPanel("Floor", LocationPanel.Locations.floor);
		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.weightx = 0.5;
		constraints.weighty = 0.5;
		constraints.gridx = 2;
		constraints.gridy = 1;
		this.add(floor, constraints);
		
		portal = new LocationPanel("Portal", LocationPanel.Locations.portal);
		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.weightx = 0.5;
		constraints.weighty = 0.5;
		constraints.gridx = 3;
		constraints.gridy = 1;
		this.add(portal, constraints);
	
		exchange = new LocationPanel("Exchange", LocationPanel.Locations.exchange);
		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.weightx = 0.5;
		constraints.weighty = 0.5;
		constraints.gridx = 4;
		constraints.gridy = 1;
		this.add(exchange, constraints);
	}
	
	public void matchSave()
	{
		allyFence.matchSave();
		oppoFence.matchSave();
		floor.matchSave();
		portal.matchSave();
		exchange.matchSave();
	}
	
	public void matchReset()
	{
		allyFence.matchReset();
		oppoFence.matchReset();
		floor.matchReset();
		portal.matchReset();
		exchange.matchReset();
	}
	
	public void matchEnable(boolean enabled)
	{
		allyFence.matchEnable(enabled);
		oppoFence.matchEnable(enabled);
		floor.matchEnable(enabled);
		portal.matchEnable(enabled);
		exchange.matchEnable(enabled);
	}
}