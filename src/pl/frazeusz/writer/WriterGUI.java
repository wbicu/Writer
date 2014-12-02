package pl.frazeusz.writer;

import java.io.*;
import java.util.Map;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import pl.frazeusz.patternmatcher.IPatternMatcherResults;
import pl.frazeusz.plotter.IPlotter;

public class WriterGUI extends JPanel
                             implements ActionListener {
	private JButton openButton, saveButton;
	private JTextArea log;
	private JFileChooser fc;

	private IPatternMatcherResults patternMatcher = null;
	private IPlotter iplotter = null;
	private WriterAPI writerAPI = null;
	private Map<Phrase, Integer> resultsFromFile = null;
	private Map<Phrase, Integer> resultsToSave = null;
	

    public WriterGUI(IPatternMatcherResults patternMatcher, IPlotter iplotter, WriterAPI writerAPI) {
        super(new BorderLayout());

        this.iplotter = iplotter;
        this.patternMatcher = patternMatcher;
        this.writerAPI = writerAPI;
        
        log = new JTextArea(5,20);
        log.setMargin(new Insets(5,5,5,5));
        log.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(log);

        fc = new JFileChooser();

        openButton = new JButton("Open a File...");
        openButton.addActionListener(this);

        saveButton = new JButton("Save a File...");
        saveButton.addActionListener(this);


        JPanel buttonPanel = new JPanel(); 
        buttonPanel.add(openButton);
        buttonPanel.add(saveButton);

        add(buttonPanel, BorderLayout.PAGE_START);
        add(logScrollPane, BorderLayout.CENTER);
    }

    public void setPatternMatcher(IPatternMatcherResults patternMatcher){
    	this.patternMatcher = patternMatcher;
    }
    
    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == openButton) {
            int returnVal = fc.showOpenDialog(WriterGUI.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                resultsFromFile = writerAPI.read(file);
                iplotter.updateChart(resultsFromFile);
            }

        } else if (e.getSource() == saveButton) {
        	if (patternMatcher == null){
        		//tu ma coœ wyskakiwaæ ¿e nie ma nic do zapisania
        	} else {
        		resultsToSave = patternMatcher.getResults();
                int returnVal = fc.showSaveDialog(WriterGUI.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    writerAPI.save(file, resultsToSave);
                }
        	}
        }
    }

    
    
    
    
    
    
    
    
    
    
    
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = WriterGUI.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("FileChooser");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(new WriterGUI(null, null, null));

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                UIManager.put("swing.boldMetal", Boolean.FALSE); 
                createAndShowGUI();
            }
        });
    }
}
