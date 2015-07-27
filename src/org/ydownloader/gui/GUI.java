package org.ydownloader.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import java.awt.FlowLayout;

import javax.swing.JTextField;
import javax.swing.JTable;

import java.awt.Font;
import java.awt.Color;

import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.ydownloader.convert.Konverter3;
import org.ydownloader.downloader.youtube.Youtube;

import com.thoughtworks.xstream.io.path.Path;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;


public class GUI {

	private JFrame frame;
	private JTextField link;
	private JLabel lbleingefuegtelinnks;
	private JList list;
	private DefaultListModel model;
	private String speicherort;
	private Youtube you=null;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 820, 460);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lbllinkeinfuegen = new JLabel("Link einf\u00FCgen:");
		lbllinkeinfuegen.setBounds(12, 49, 90, 30);
		frame.getContentPane().add(lbllinkeinfuegen);
		
		lbleingefuegtelinnks = new JLabel("Links zum herunterladen:");
		lbleingefuegtelinnks.setBounds(12, 157, 155, 30);
		frame.getContentPane().add(lbleingefuegtelinnks);
		
		JButton btnlinkhinzufuegen = new JButton("Link hinzuf\u00FCgen");
		btnlinkhinzufuegen.setBounds(12, 107, 131, 25);
		frame.getContentPane().add(btnlinkhinzufuegen);
		
		btnlinkhinzufuegen.addActionListener(new ActionListener()
	    {
	        public void actionPerformed(ActionEvent e)
	        {
	           addListe();
	        }

	    });
		JButton btnherunterladen = new JButton("Herunterladen");
		btnherunterladen.setBounds(12, 383, 131, 25);
		frame.getContentPane().add(btnherunterladen);
		btnherunterladen.addActionListener(new ActionListener()
	    {
	        public void actionPerformed(ActionEvent e)
	        {
	           startDL();
	        }

	    });
		JButton btnschliessen = new JButton("Schlie\u00DFen");
		btnschliessen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnschliessen.setBounds(693, 383, 97, 25);
		frame.getContentPane().add(btnschliessen);
		
		link = new JTextField();
		link.setBounds(12, 77, 778, 22);
		frame.getContentPane().add(link);
		link.setColumns(10);
		 model = new DefaultListModel();
		list = new JList(model);
		list.setBounds(12, 190, 754, 100);
		frame.getContentPane().add(list);
		
		JProgressBar progressBargesamt = new JProgressBar();
		progressBargesamt.setBounds(12, 356, 778, 14);
		frame.getContentPane().add(progressBargesamt);
		
		JProgressBar progressBareinzel = new JProgressBar();
		progressBareinzel.setBounds(12, 318, 778, 14);
		frame.getContentPane().add(progressBareinzel);
		
		JScrollBar scrollBar = new JScrollBar();
		scrollBar.setBounds(769, 190, 21, 100);
		frame.getContentPane().add(scrollBar);
		
		JLabel lblgesamt = new JLabel("Gesamt:");
		lblgesamt.setBounds(12, 340, 56, 16);
		frame.getContentPane().add(lblgesamt);
		
		JLabel lblaktuell = new JLabel("Aktuell:");
		lblaktuell.setBounds(12, 303, 56, 16);
		frame.getContentPane().add(lblaktuell);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(12, 10, 119, 26);
		frame.getContentPane().add(menuBar);
		
		JMenu mnNewMenudatei = new JMenu("Datei");
		menuBar.add(mnNewMenudatei);
		
		JMenuItem mntmNewMenuItemschliessen = new JMenuItem("Schlie\u00DFen");
		mntmNewMenuItemschliessen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		
		JMenuItem mntmNewMenuItemordnereoffnen = new JMenuItem("Ordner \u00F6ffnen ...");
		mnNewMenudatei.add(mntmNewMenuItemordnereoffnen);
		mnNewMenudatei.add(mntmNewMenuItemschliessen);
		
		JMenu mnNewMenuoptionen = new JMenu("Optionen");
		menuBar.add(mnNewMenuoptionen);
		
		JMenuItem mntmNewMenuItemspeicherort = new JMenuItem("Speicherort");
		mntmNewMenuItemspeicherort.addActionListener(new ActionListener()
	    {
	        public void actionPerformed(ActionEvent e)
	        {
	        	speicherort = JOptionPane.showInputDialog(frame, "Speicherort wählen!");
	        }

	    });
		
		
		mnNewMenuoptionen.add(mntmNewMenuItemspeicherort);
		
		 class ProgressBar implements Runnable {
		        public void run() {
		        	while(true) try {
		        		if(you!=null) {
		        			progressBareinzel.setValue((int)(you.getProgress()));		
		        		} 
		        			//progressBareinzel.setValue((int)(Math.random()*100));
						Thread.sleep(250);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
		        }
		        
		    }
			Thread thread1 = new Thread(new ProgressBar());
			thread1.start();
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
	}
	private void addListe() {
		int pos = list.getModel().getSize();
		if(new Youtube().validYoutube(this.link.getText())) {
			model.add(pos, this.link.getText());
		}
		link.setText("");
	}
	private void startDL() {
		Queue queue = new LinkedList();
		
		int downloads = model.getSize();
		for(int i=0;i<downloads;i++) {
			queue.add(model.getElementAt(i).toString());
		}		
			class MyDownloader implements Runnable {
					Queue dls;
			        MyDownloader(Queue p) { dls = p; }
			        public void run() {
			        	try {
			        		String ele = (String) dls.element();
			        		System.out.println(dls.element() +" = PFAD =>"+ speicherort +ele.substring(ele.length() - 6)  + ".dl");
							you = new Youtube();
							File path = new File( speicherort + ele.substring(ele.length() - 6)   + ".dl");
							you.run(ele, path);  
							Konverter3 k = new Konverter3();
							k.Konvert(path,speicherort + ele.substring(ele.length() - 6)+ ".mp3");
							System.out.println("Done");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
			        }
			    }
			Thread thread1 = new Thread(new MyDownloader(queue));
			thread1.start();		
		
		
	}
}
