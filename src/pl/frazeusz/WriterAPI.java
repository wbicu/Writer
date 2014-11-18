package pl.frazeusz;

import java.io.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class WriterAPI extends JPanel
                             implements ActionListener {
    static private final String newline = "\n";
    JButton openButton, saveButton;
    JTextArea log;
    JFileChooser fc;
	private FileReader fileReader;
	private FileWriter fileWriter;
	
	void save(String path, String filename, SavedData savedData){
		fileWriter.saveToFile(path, filename, savedData);
	}
	
	SavedData read(String path, String filename){
		return fileReader.read(path, filename);
	}

    public WriterAPI() {
        super(new BorderLayout());


        log = new JTextArea(5,20);
        log.setMargin(new Insets(5,5,5,5));
        log.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(log);

        fc = new JFileChooser();

        openButton = new JButton("Open a File...",
                                 createImageIcon("images/Open16.gif"));
        openButton.addActionListener(this);

        saveButton = new JButton("Save a File...",
                                 createImageIcon("images/Save16.gif"));
        saveButton.addActionListener(this);


        JPanel buttonPanel = new JPanel(); 
        buttonPanel.add(openButton);
        buttonPanel.add(saveButton);

        add(buttonPanel, BorderLayout.PAGE_START);
        add(logScrollPane, BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == openButton) {
        	String filename = "tempFilename";
        	String path = "tempPath";
        	
            int returnVal = fc.showOpenDialog(WriterAPI.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
            }
            read(path, filename);

        } else if (e.getSource() == saveButton) {
        	String path = "tempPath";
        	String filename = "tempFilename";
        	SavedData savedData = new SavedData();
        	
            int returnVal = fc.showSaveDialog(WriterAPI.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
            }
            save(path, filename, savedData);
        }
    }

    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = WriterAPI.class.getResource(path);
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

        frame.add(new WriterAPI());

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
