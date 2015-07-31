package org.ydownloader.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import java.awt.FlowLayout;

import javax.swing.JTextField;
import javax.swing.JTable;

import java.awt.Desktop;
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
import java.util.List;
import java.util.ListIterator;
import java.util.Queue;


public class GUI {

	//Hier werden die für die GUI relevanten Variablen definiert
	public JFrame frame;
	private JTextField link;
	private JLabel lbleingefuegtelinnks;
	private JList list;
	private DefaultListModel model;
	private String speicherort;
	private Youtube you=null;
	//Diese Variablen werden für die Progressbar benötigt
	private int downloads=0;
	private int downloadsfertig=0;
	//erstellen eines leeren Konverterobjekts das später für die Konvertierung benötigt wird
	private Konverter3 kv=null;
	public JLabel lblaktuell;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
	
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
		//Initialisieren der GUI hier wird alles an seinen Platz gesetzt
		frame = new JFrame();
		frame.setBounds(100, 100, 820, 460);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lbllinkeinfuegen = new JLabel("Link einf\u00FCgen:");
		lbllinkeinfuegen.setBounds(12, 49, 120, 30);
		frame.getContentPane().add(lbllinkeinfuegen);
		
		lbleingefuegtelinnks = new JLabel("Links zum herunterladen:");
		lbleingefuegtelinnks.setBounds(12, 157, 200, 30);
		frame.getContentPane().add(lbleingefuegtelinnks);
		
		JButton btnlinkhinzufuegen = new JButton("Link hinzuf\u00FCgen");
		btnlinkhinzufuegen.setBounds(12, 107, 150, 25);
		frame.getContentPane().add(btnlinkhinzufuegen);
		
		//Sobald der Nutzer den Button Link hinzüfugen klickt wird der in Textfield eingetragene Link in die Downloadliste eingefügt.
		btnlinkhinzufuegen.addActionListener(new ActionListener()
	    {
	        public void actionPerformed(ActionEvent e)
	        {
	           addListe();
	        }

	    });
		JButton btnherunterladen = new JButton("Herunterladen");
		btnherunterladen.setBounds(12, 383, 180, 25);
		frame.getContentPane().add(btnherunterladen);
		//Startet die Herunterlade Funktion wenn der User sie anklickt.
		btnherunterladen.addActionListener(new ActionListener()
	    {
	        public void actionPerformed(ActionEvent e)
	        {
	           startDL();
	        }

	    });
		/**JButton btnschliessen = new JButton("Schlie\u00DFen");
		btnschliessen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnschliessen.setBounds(693, 383, 140, 25);
		frame.getContentPane().add(btnschliessen);**/
		
		link = new JTextField();
		link.setBounds(12, 77, 778, 22);
		frame.getContentPane().add(link);
		link.setColumns(10);
		 model = new DefaultListModel();
		list = new JList(model);
		list.setBounds(12, 190, 754, 100);
		frame.getContentPane().add(list);
		//Einfügen der Progressbar die uns den Fortschritt des Downloads anzeigt.
		JProgressBar progressBargesamt = new JProgressBar();
		progressBargesamt.setBounds(12, 356, 778, 14);
		frame.getContentPane().add(progressBargesamt);
		
		JProgressBar progressBareinzel = new JProgressBar();
		progressBareinzel.setBounds(12, 318, 778, 14);
		frame.getContentPane().add(progressBareinzel);
		//Scrollbar um sich die Liste der Links anzusehen die auf den Download warten.
		JScrollBar scrollBar = new JScrollBar();
		scrollBar.setBounds(769, 190, 21, 100);
		frame.getContentPane().add(scrollBar);
		//Gesamt und Aktuell sind die Beschreibung der beiden Progressbars.
		JLabel lblgesamt = new JLabel("Gesamt:");
		lblgesamt.setBounds(12, 340, 90, 16);
		frame.getContentPane().add(lblgesamt);
		
		lblaktuell = new JLabel("Aktuell:");
		lblaktuell.setBounds(12, 303, 600, 16);
		frame.getContentPane().add(lblaktuell);
		//Erstellen der Menubar in der der User den Speicherort der Datei auswählt oder auch das das Programm schließen.
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(12, 10, 119, 26);
		frame.getContentPane().add(menuBar);
		//Name des Punktes in der Menüleiste
		JMenu mnNewMenudatei = new JMenu("Datei");
		menuBar.add(mnNewMenudatei);
		//Der Unterpunkt des Menüpunktes Datei ist Schließen. Hier gleich mit Actionlistener er das Programm dann schließt.
		JMenuItem mntmNewMenuItemschliessen = new JMenuItem("Schlie\u00DFen");
		mntmNewMenuItemschliessen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		
		JMenuItem mntmNewMenuItemordnereoffnen = new JMenuItem("Ordner \u00F6ffnen ...");
		
		mntmNewMenuItemordnereoffnen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Desktop.getDesktop().open(new File(speicherort));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		mnNewMenudatei.add(mntmNewMenuItemordnereoffnen);
		mnNewMenudatei.add(mntmNewMenuItemschliessen);
		//Zweiter Menüpunkt Optionen in dem sich u. a. der Speicherort einstellen lässt.
		JMenu mnNewMenuoptionen = new JMenu("Optionen");
		menuBar.add(mnNewMenuoptionen);
		
		JMenuItem mntmNewMenuItemspeicherort = new JMenuItem("Speicherort");
		mntmNewMenuItemspeicherort.addActionListener(new ActionListener()
	    {
	        public void actionPerformed(ActionEvent e)
	        {
	        	speicherort = JOptionPane.showInputDialog(frame, "Speicherort wÃ¤hlen!");
	        }

	    });
		
		
		mnNewMenuoptionen.add(mntmNewMenuItemspeicherort);
		//Diese Funktion steuert die Progressbar, läuft als Thread neben dem Download
		 class ProgressBar implements Runnable {
		        public void run() {
		        	while(true) try {
		        		if(you!=null) {
		        			//Holt sich den Download Fortschritt aus der Youtube Klasse.
		        			progressBareinzel.setValue((int)(you.getProgress()));
		        			//Berechnet den aktuellen Wert den die Progressbargesamt hat basierend auf den Verbleibenden Downloads.
		        			progressBargesamt.setValue((int)(downloadsfertig/downloads * 100) + (int)(you.getProgress()/downloads));
		        			
		        		} else {
		        			//Wenn kein Download läuft dann ist die Progressbar bei 0
		        			progressBareinzel.setValue(0);	
		        			progressBargesamt.setValue(0);
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
	//Funktion für das Einfügen der Links in die Downloadliste
	private void addListe() {
		int pos = list.getModel().getSize();
		//Wenn der Link ein valider YOutube link ist wird er zur Liste hinzugefügt
		if(new Youtube().validYoutube(this.link.getText())) {
			model.add(pos, this.link.getText());
		}
		link.setText("");
	}
	private void startDL() {
		if(speicherort==null) {
			//Fehler wenn kein Speicherort angegeben ist
			JOptionPane.showMessageDialog(null, "Bitte einen Speicherort unter Optionen einstellen!", "Speicherort-Warnung", JOptionPane.WARNING_MESSAGE);
			return;
		}
		//Linkedlist für die Download Queue
		Queue queue = new LinkedList();
		
		int dls = model.getSize();
		
		//Einfügen in die queue
		for(int i=0;i<dls;i++) {
			queue.add(model.getElementAt(i).toString());
			
		}	
		
		model.clear();
		downloads=queue.size();
		downloadsfertig=0;
		//Start des Programmes wieder als Thread
			class MyDownloader implements Runnable {
					Queue dls;
			        MyDownloader(Queue p) { dls = p; }
			        public void run() {
			        	//Läuft solange etwas in der Queue ist
			        	while(dls.size()!=0) {
			        	try {
			        			//Das Programm holt sich den Link des nächsten Elements aus der Liste
			        			String ele = (String) dls.element();
			        			System.out.println(dls.element() +" = PFAD =>"+ speicherort +ele.substring(ele.length() - 6)  + ".dl");
			        			//lblaktuell zeigt den Status des Downloaders an
			        			lblaktuell.setText("Aktuell: " + ele);			        		
			        			you = new Youtube();
			        			//Speichername der neuendatei ist ein Verkürzter Teil der Youtube URL
			        			File path = new File( speicherort + ele.substring(ele.length() - 6)   + ".dl");
			        			//Start des Downloads
			        			you.run(ele, path);  
			        			lblaktuell.setText("Aktuell: Konvertierung von " + ele);
			        			kv = new Konverter3();
			        			//Start des Konverters
			        			kv.Konvert(path,speicherort + ele.substring(ele.length() - 6)+ ".mp3");
			        			downloadsfertig++;
			        			//Löscht den Link aus der Queue
			        			dls.remove();
			        			lblaktuell.setText("Aktuell:");
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
			        	//Wenn die Queue Leer ist dann ist das Programm fertig.
			        	you=null;
			        	JOptionPane.showMessageDialog(null, "Deine Downloads sind heruntergeladen!", "Downloads Fertig", JOptionPane.OK_CANCEL_OPTION);


			        }
			    }
			//ERstellt neuen Thread für den Downloader
			Thread thread1 = new Thread(new MyDownloader(queue));
			thread1.start();		
		
		
	}
}
