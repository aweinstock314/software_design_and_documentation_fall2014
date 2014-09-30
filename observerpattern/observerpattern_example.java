import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

class Observer extends KeyAdapter {
    public void keyPressed(KeyEvent e) { System.out.printf("A key was pressed: '%c'\n", e.getKeyChar()); }
    //public void keyPressed(KeyEvent e) { JOptionPane.showMessageDialog(null, String.format("A key was pressed: '%c'\n", e.getKeyChar())); }
}
class Subject extends JFrame {
    public Subject() {
        this.setSize(64, 64);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
class MainClass {
    public static void main(String[] args) {
        Subject s = new Subject();
        Observer o = new Observer();
        s.addKeyListener(o);
    }
}
