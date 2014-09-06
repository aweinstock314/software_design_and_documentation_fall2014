//import javax.swing.*;        

public class HelloWorldWebSwing {
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI(int port) {
        WebWidgetFactory wwf = WebWidgetFactory();
        //Create and set up the window.
        //JFrame frame = new JFrame("HelloWorldSwing");
        WebJFrame frame = wwf.JFrame("HelloWorldSwing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add the ubiquitous "Hello World" label.
        //JLabel label = new JLabel("Hello World");
        WebJLabel label = wwf.JLabel("Hello World");
        frame.getContentPane().add(label);

        //Display the window.
        frame.pack();
        frame.setVisible(true);

        wwf.beginServing(port);
    }

    public static void main(String[] args) {
        ////Schedule a job for the event-dispatching thread:
        ////creating and showing this application's GUI.
        //javax.swing.SwingUtilities.invokeLater(new Runnable() {
        //    public void run() {
        //        createAndShowGUI();
        //    }
        //});
        createAndShowGUI(8000);
    }
}
