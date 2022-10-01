import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;       
public class Display extends JPanel implements MouseListener, KeyListener, MouseMotionListener{
    public Timer timer;
    boolean[][] mat = new boolean[25][25];
    public Display(){
        setVisible(true);
        this.addMouseListener(this);
        this.addKeyListener(this);
        this.addMouseMotionListener(this);        
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(mat.length*20, mat.length*20+90));
        setFocusable(true);
        timer = new Timer(100, new ActionListener() 
            {
                public void actionPerformed(ActionEvent e) {
                    makeNextMat();
                    repaint();
                }
            });
    }

    public boolean isAlive(int r, int c){
        if(r<0 || r>= mat.length)
            return false;
        if(c<0 || c>= mat.length)
            return false;
        return mat[r][c];
    }

    public int findNeighNum(int r, int c){
        int ans = 0;
        if(isAlive(r-1,c-1))
            ans++;
        if(isAlive(r-1,c))
            ans++;
        if(isAlive(r-1,c+1))
            ans++;
        if(isAlive(r,c-1))
            ans++;
        if(isAlive(r,c+1))
            ans++;
        if(isAlive(r+1,c-1))
            ans++;
        if(isAlive(r+1,c))
            ans++;
        if(isAlive(r+1,c+1))
            ans++;
        return ans;
    }

    public void makeNextMat(){
        boolean[][] copy = new boolean[mat.length][mat[0].length];
        for(int r = 0; r < mat.length; r++){
            for(int c = 0; c < mat[0].length; c++){
                if(mat[r][c]){
                    if(findNeighNum(r,c) <= 1)
                        copy[r][c] = false;
                    else if(findNeighNum(r,c) >3)
                        copy[r][c] = false;
                    else
                        copy[r][c] = true;
                } else if(findNeighNum(r,c) == 3)
                    copy[r][c] = true;
            }
        }
        mat = copy;
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        try{
            Image img = ImageIO.read(new File("D:\\Honors AI\\Game of Life\\logo.jpg"));
            g.drawImage(img, -300, -100, Color.WHITE, this);
        }catch(Exception e){
        }
        Color randColor;
        for(int r = 0; r < mat.length; r++)
            for(int c = 0; c < mat[r].length; c++){
                if(mat[r][c]){
                    randColor = new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255));
                    g.setColor(randColor);
                    g.fillRect(c*20, r*20, 20, 20);
                }else{
                    g.setColor(Color.WHITE);
                    g.drawRect(c*20, r*20, 20, 20);
                }
            }
        Font f = new Font("TimesRoman", Font.PLAIN, 30);
        //g.setColor(Color.WHITE);
        g.setFont(f);
        g.drawString("Press S to Start/Stop the Game", 0, mat.length*20+30);
        g.drawString("Click to Give Life to a cell", 0, mat.length*20 + 60);
        g.drawString("Press R to Restart the Game", 0, mat.length*20+90);
    }

    @Override
    public void mousePressed(MouseEvent e){
        if(e.getX()/20 < 0 || e.getX()/20 >= mat.length) return;
        if(e.getY()/20 < 0 || e.getY()/20 >= mat[0].length) return;
        mat[e.getY() / 20][e.getX() / 20] = true;
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_R){
            timer.stop();
            mat = new boolean[mat.length][mat[0].length];
            repaint();
        } else if(e.getKeyCode() == KeyEvent.VK_S){
            if(timer.isRunning()) timer.stop();
            else timer.start();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e){
        if(e.getX()/20 < 0 || e.getX()/20 >= mat.length) return;
        if(e.getY()/20 < 0 || e.getY()/20 >= mat[0].length) return;        
        mat[e.getY() / 20][e.getX() / 20] = true;
        repaint();
    }

    @Override public void mouseEntered(MouseEvent e){}

    @Override public void mouseExited(MouseEvent e){}

    @Override public void mouseClicked(MouseEvent e){}

    @Override public void mouseReleased(MouseEvent e){}

    @Override public void keyReleased(KeyEvent e){}

    @Override public void keyTyped(KeyEvent e){}

    @Override public void mouseMoved(MouseEvent e){}
}