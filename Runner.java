import javax.swing.*;
public class Runner{
    public static void main(String[] args){
        JFrame frame = new JFrame("Game of Life");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Display display = new Display();
        frame.add(display);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }
}