import java.applet.*;
import java.awt.*;
import java.util.*;
import java.lang.*;
import java.text.*;
import java.awt.event.*; 
import java.io.*;

@SuppressWarnings("serial")
public class backendfile extends Applet {
    String strBuffer;
	Panel panel = new Panel();
	public TextArea area;
	public Button button;
	public backendfile() {
		panel.setName("ModBus Live Text Feed Details");
		Font fontSize;
		String osname = System.getProperty("os.name","");
		if (!osname.startsWith("Windows")) {
			fontSize = new Font("Calibri",Font.BOLD,11);
		} else {
			fontSize = new Font("Calibri Light",Font.PLAIN,12);
		}
		panel.setFont(fontSize);
		area = new TextArea (40,110);
		panel.add (area);
		button = new Button("Choose File");
		panel.add (button);
		//panel.add(new Button("Choose File"));
		//panel.setBackground(new Color(255, 255, 255));
		
		add("North",panel);
	}
	@SuppressWarnings({ "resource", "deprecation" })
	public boolean action(Event evt, Object arg) {
		if (arg.equals("Choose File")) {
			System.out.println("Choose File Button CLICKED");
			// Variables
			int arrlen = 10000;
			byte[] infile = new byte[arrlen];
			Frame parent = new Frame();
			FileDialog fd = new FileDialog(parent, "Please choose a file:",
			    FileDialog.LOAD);
			fd.show();
			String fileSelected = fd.getFile();
			if (fileSelected == null) {
				// no file selected
			} else {
				File textFile = new File( fd.getDirectory() + File.separator +
				                     fd.getFile());
				// read the file
				System.out.println("reading file " + fd.getDirectory() +
				                        File.separator + fd.getFile() );
				try {
					FileInputStream fis = new FileInputStream(textFile); 
					BufferedInputStream bis = new BufferedInputStream(fis);
					DataInputStream dis = new DataInputStream(bis);
					try {
						int filelength = dis.read(infile);
						String fileString = new String(infile, 0,
						                           filelength);
						//Assign fileString to strBuffer
						strBuffer = fileString;
						// Print to console
						System.out.println(strBuffer);
						// Display in Applet TextArea
						area.setText("CONTENT OF THE MODBUS *.txt FILE in the directory:   " 
						+ fd.getDirectory() + File.separator + fd.getFile() + "   " 
						+ "IS DISPLAYED BELOW" + "\n" + "\n" + strBuffer);
					} catch(IOException iox) {
						System.out.println("File read error...");
						iox.printStackTrace();
					}
				} catch (FileNotFoundException fnf) {
					System.out.println("File not found...");
					fnf.printStackTrace();
				}
			}		
			
		} else return false;
		return true;
	}
}