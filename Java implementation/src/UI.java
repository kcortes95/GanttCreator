import javax.swing.JFrame;
import javax.swing.JLabel;

public class UI {

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event-dispatching thread.
	 */
	
	private static int W = 800;
	private static int H = 600;
	
	private static void createAndShowGUI() {
		// Create and set up the window.
		JFrame frame = new JFrame("GanttCreator UI");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel l1 = new JLabel("Label 1");
		System.out.println(l1.getPreferredSize().getWidth());
		System.out.println(l1.getPreferredSize().getHeight());
		frame.getContentPane().add(l1);
		
		JLabel l2 = new JLabel("Label 2");
		frame.getContentPane().add(l2);
		
		JLabel l3 = new JLabel("Label 3");
		frame.getContentPane().add(l3);
		
		frame.setSize(W, H); // << not working!!!
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

}
