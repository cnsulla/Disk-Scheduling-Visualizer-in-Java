import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import java.awt.Graphics2D;

class SimulatorPanel extends JPanel{
    private int[] path;
    private int index; 
    private int[] schedule;

    private Graphics2D g2d;
    private double needleX;
    private double needleY;
    SimulatorPanel(){
        setBorder(BorderFactory.createLineBorder(java.awt.Color.BLACK, 1));
        path = new int[0];
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.g2d = (Graphics2D) g;
        drawBar(g2d);
        drawPath(g2d);
        repaint();
    }
    void init(int[] path, int[] schedule){
        this.path = path;
        this.schedule = schedule;
        index = 0;

        //Input.printArray(path, true);
        //Input.printArray(schedule, true);
        if(path != null){
            needleX = path[index];
            needleY = 0;
        }
    }
    void tick(int speed, double deltaTime){
        if(index == path.length-1){
            return;
        }

        double xDisplacement = deltaTime*speed/1000;
        if(path[index] > path[index+1]){
            xDisplacement *= -1;
        }
        needleX += xDisplacement;

        needleY += Math.abs((Math.abs(xDisplacement)/Math.abs(path[index] - path[index+1])));
 
        if((int)needleX == path[index+1]){
            index++;
        }
    }
    private void drawBar(Graphics2D g2d){
        g2d.drawLine(20,20,this.getWidth()-20,20);

        int barWidth = this.getWidth()-40;

        for(int i = 0; i < 200;i++){
            int xPos = (int)((i/199.0)*barWidth)+20; 

            //draw bar dash
            if(i%10 == 0){
                g2d.drawLine(xPos, 5, xPos,35);
            }
            else{
                g2d.drawLine(xPos, 10, xPos, 30);
            }

            //draw label values
            if(i == 199 || i % 10 == 0){
                g2d.drawString(i+"", xPos-5, 50);
            }
        }
    }
    private void drawPath(Graphics2D g2d){
        if(path == null || path.length == 0){
            return;
        }
        int x1, x2, y1, y2;
        for(int i = 0; i < index; i++){            
            x1 = getXPos(path[i]);
            y1 = getYPx()*i+60;
            x2 = getXPos(path[i+1]);
            y2 = getYPx()*(i+1)+60;

            g2d.drawLine(x1,y1,x2,y2);
        }

        int scheduleIndex = 0;
        for(int i = 0; i <= index; i++){
            if(schedule[scheduleIndex] != path[i]){
                continue;
            }
            x1 = getXPos(path[i]);
            y1 = getYPx()*i+60;

            g2d.fillOval(x1-5, y1-5, 10, 10);
            g2d.drawString(path[i]+"",x1,y1+20);

            scheduleIndex++;
        }

        if(index < path.length-1){
            g2d.drawLine(getXPos(path[index]), getYPx()*index+60,getXPos((int) needleX), getYPosMoving());
        }
    }
    boolean isFinished(){
        return (path == null) || (index == path.length-1);
    }

    int getXPos(int cylinder){
        int barWidth = this.getWidth()-40;
        return (int)((cylinder/199.0)*barWidth)+20;
    }
    int getYPx(){
        return (getHeight()-80)/path.length;
    }
    int getYPosMoving(){
        return (int)Math.ceil(needleY*getYPx())+60;
    }
} 
