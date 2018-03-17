package components;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import processing.FileManager;
import processing.StaticMatchInfo;

class ScoutingAppMenu extends JMenu implements ActionListener
{
	private JMenuItem importList, saveAndReset, reset, compile;
	private ScoutingApp frame;
	
	public ScoutingAppMenu(ScoutingApp frame)
	{
		super("Menu");
		
		this.frame = frame;
		this.setFont(new Font("Ubuntu", Font.PLAIN, 16));
		initComponents();
	}
	
	private void initComponents()
	{
		importList = new JMenuItem("Import Match List");
		importList.setFont(new Font("Ubuntu", Font.PLAIN, 16));
		importList.setActionCommand("IMPORT");
		importList.addActionListener(this);
		
		saveAndReset = new JMenuItem("Save And Reset Fields");
		saveAndReset.setFont(new Font("Ubuntu", Font.PLAIN, 16));
		saveAndReset.setToolTipText("Click this once the match is finished "
									+ "and you've checked over your entries.");
		saveAndReset.setActionCommand("SAVE");
		saveAndReset.addActionListener(this);	
		
		reset = new JMenuItem("Reset Fields");
		reset.setFont(new Font("Ubuntu", Font.PLAIN, 16));
		reset.setToolTipText("Click this to clear all entries without saving them.");
		reset.setActionCommand("RESET");
		reset.addActionListener(this);
		
		compile = new JMenuItem("Compile Data");
		compile.setToolTipText("Click this to combine all of the of the scouting data."
								+ "To be used by the lead scouters.");
		compile.setFont(new Font("Ubuntu", Font.PLAIN, 16));
		compile.setActionCommand("COMPILE");
		compile.addActionListener(this);
		
		//this.add(importList);
		this.add(saveAndReset);
		this.add(reset);
		this.add(compile);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		String cmd = e.getActionCommand();
		
		if (cmd.equals("IMPORT"))
		{
			try 
			{
				FileManager.populateMatchList();
			}
			catch (FileNotFoundException exception)
			{
				JOptionPane.showMessageDialog(null, 
											"match_list.txt was not found.", 
											"Error", 
											JOptionPane.ERROR_MESSAGE);
			}	
		}
		else if (cmd.equals("SAVE"))
		{
		
			int confirm = JOptionPane.showConfirmDialog(this, 
											"This will save and clear the"
											+ " current match data. Continue?",
											"Continue?",
											JOptionPane.YES_NO_OPTION);
		
			if (confirm == JOptionPane.OK_OPTION)
			{
				frame.matchSave();
				
				try 
				{
					FileManager.printToFile();
				}
				catch (FileNotFoundException exception)
				{
					
				}
				
				frame.matchReset();
				frame.matchEnable(false);
			}
		}
		else if (cmd.equals("RESET"))
		{
			int confirm = JOptionPane.showConfirmDialog(this, 
					"This will clear the current match data. Continue?",
					"Continue?",
					JOptionPane.YES_NO_OPTION);

			if (confirm == JOptionPane.OK_OPTION)
			{
				frame.matchReset();
				frame.matchEnable(false);
			}
		}
		else if (cmd.equals("COMPILE"))
		{
			FileManager.compileFiles();
			JOptionPane.showMessageDialog(this, "Compilation Finished");
		}
	}
}

public class ScoutingApp extends JFrame implements IMatchUtils
{
	private ScoringPanel scoringPanel;
	private CubePickUpPanel cubePanel;
	private MiscPanel miscPanel;
	private JMenuBar menubar;
	private ScoutingAppMenu menu;
	
	public ScoutingApp()
	{	
		super("Scouting App 2018");
		initComponents();
		
		this.matchEnable(false);
				
		this.createAndShowGUI();
	}
	
	private void initComponents()
	{
		this.setLayout(new GridBagLayout());
		GridBagConstraints constraints;
		
		menu = new ScoutingAppMenu(this);
		
		menubar = new JMenuBar();
		menubar.add(menu);
		
		this.setJMenuBar(menubar);

		scoringPanel = new ScoringPanel();
		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 0.7;
		constraints.weighty = 0.5;
		constraints.insets = new Insets(2, 2, 2, 2);
		this.add(scoringPanel, constraints);
		
		cubePanel = new CubePickUpPanel();
		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.weightx = 0.7;
		constraints.weighty = 0.5;
		constraints.insets = new Insets(2, 2, 2, 2);
		this.add(cubePanel, constraints);

		miscPanel = new MiscPanel(this);
		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.weightx = 0.3;
		constraints.weighty = 0.5;
		constraints.gridheight = 4;
		constraints.insets = new Insets(2, 2, 2, 2);
		this.add(miscPanel, constraints);
		
	}
	
	public void matchSave()
	{
		scoringPanel.matchSave();
		miscPanel.matchSave();
		cubePanel.matchSave();
	}
	
	public void matchReset()
	{
		FileManager.updateMatchList();
		
		scoringPanel.matchReset();
		miscPanel.matchReset();
		cubePanel.matchReset();
		
		StaticMatchInfo.resetFields();
	}
		
	public void matchEnable(boolean enabled)
	{
		scoringPanel.matchEnable(enabled);
		miscPanel.matchEnable(enabled);
		cubePanel.matchEnable(enabled);
	}
	
	public void createAndShowGUI()
	{
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setMinimumSize(new Dimension(1300, 600));
		this.setPreferredSize(new Dimension(1300, 600));
		
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
}