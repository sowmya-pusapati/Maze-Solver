
package GUI;

import GraphAlgorithm.DFS;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class View extends JFrame implements ActionListener, MouseListener
{
    
    /*values :0=Not visited
              1=Blocked wall
              2=Visited block
              9=target
        */
    
    private int maze[][]={    
        {1,1,1,1,1,1,1,1,1,1,1,1,1},
        {1,0,1,0,1,0,1,0,0,0,0,0,1},
        {1,0,1,0,0,0,1,0,1,1,1,0,1},
        {1,0,0,0,1,1,1,0,0,0,0,0,1},
        {1,0,1,0,0,0,0,0,1,1,1,0,1},
        {1,0,1,0,1,1,1,0,1,0,0,0,1},
        {1,0,1,0,1,0,0,0,1,1,1,0,1},
        {1,0,1,0,1,1,1,0,1,0,1,0,1},
        {1,0,0,0,0,0,0,0,0,0,1,9,1},
        {1,1,1,1,1,1,1,1,1,1,1,1,1}
    };
   private int[] target={8,11};
   private List<Integer> path=new ArrayList<Integer>();
   
   JButton submitButton;
   JButton clearButton;
   JButton cancelButton;
   
   public View(){
       this.setTitle("MazeSolver");
       this.setSize(520,500);
       this.setLayout(null);
       this.setLocationRelativeTo(null);
       this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
       submitButton =new JButton("submit");
       submitButton.addActionListener(this);
       submitButton.setBounds(100, 400, 80,30 );
       
       clearButton =new JButton("clear");
       clearButton.addActionListener(this);
       clearButton.setBounds(200, 400, 80,30 );
       
       cancelButton =new JButton("cancel");
       cancelButton.addActionListener(this);
       cancelButton.setBounds(300, 400, 80,30 );
       this.addMouseListener(this);

       this.add(submitButton);
       this.add(clearButton);
       this.add(cancelButton);
       
       
   }
   @Override
   public void paint(Graphics g){
       super.paint(g);
       for(int row=0;row<maze.length;row++){
           for(int col=0;col<maze[0].length;col++){
               Color color;
               switch(maze[row][col]){
                   case 1: color=Color.BLACK;break;
                   case 9 :color=Color.RED;break;
                   default: color=Color.WHITE;
               }
               g.setColor(color);
               g.fillRect(40*col, 40*row, 40, 40);
               g.setColor(Color.BLACK);
               g.drawRect(40*col, 40*row, 40, 40);
               
           }
       }
       for(int p=0;p<path.size();p+=2)
       {
           int pathX=path.get(p);
           int pathY=path.get(p+1);
           g.setColor(Color.GREEN);
           g.fillRect(40*pathY,40*pathX,40,40);
       }
       
   }
    
    @Override
    public void actionPerformed(ActionEvent e){
        
        if(e.getSource()==submitButton)
        {
            try{
                boolean res=DFS.searchPath(maze,1,1,path);
                System.out.println(res);
                this.repaint();
            }
            catch(Exception excp)
            {
                JOptionPane.showMessageDialog(null, excp.toString());
            }
        }
        
        if(e.getSource()==cancelButton)
        {
            int response=JOptionPane.showConfirmDialog(null, "do you want to exit", "submit", JOptionPane.YES_NO_OPTION);
            if(response==0)
            {
                System.exit(0);
            }
            
        }
        if(e.getSource()==clearButton)
        {
            for(int row=0;row<maze.length;row++)
            {
                for(int col=0;col<maze[0].length;col++)
                {
                    if(maze[row][col]==2){
                        maze[row][col]=0;
                    }
                }
            }
            path.clear();
            this.repaint();
        }
        
       
        
        
    }
        @Override
        public void mouseClicked(MouseEvent e){
            if(e.getX()>=0&&e.getX()<=maze[0].length*40&&e.getY()>=0&&e.getY()<=maze.length*40)
            {
                int x=e.getY()/40;
                int y=e.getX()/40;
                if(maze[x][y]==1)
                {
                    return;
                }
                Graphics g=getGraphics();
                g.setColor(Color.WHITE);
                g.fillRect(40*target[1],40*target[0],40,40);
                g.setColor(Color.RED);
                g.fillRect(40*y,40*x,40,40);
                maze[target[0]][target[1]]=0;
                maze[x][y]=9;
                target[0]=x;
                target[1]=y;
                
            }
       
        }
        
     
      @Override
        public void mousePressed(MouseEvent e)
        {
        }
        @Override
        public void mouseReleased(MouseEvent e)
        {
        }
        @Override
        public void mouseEntered(MouseEvent e){
        
        }
        @Override
        public void mouseExited(MouseEvent e){
        }
        

    public static void main(String[] args)
    {
        View view=new View();
        view.setVisible(true);
    }
    
}
