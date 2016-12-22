package balls;

import java.awt.BorderLayout; 
import java.awt.Color; 
import java.awt.Dimension; 
import java.awt.Graphics; 
import java.awt.event.MouseEvent; 
import java.awt.event.MouseListener; 
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.swing.JFrame; 
import javax.swing.JPanel;




 

public class BouncingBall extends JPanel implements MouseListener { 

protected List<Ball> balls = new ArrayList<Ball>(50); 
private Container container; 
private DrawCanvas canvas; 
private int canvasWidth; 
private int canvasHeight; 
public static final int UPDATE_RATE = 30; 
int x = random(480); 
int y = random(480); 
int speedX =3; //random(30); 
int speedY =3; //random(30); 
int radius =10; //random(50); 
int red = random(255); 
int green = random(255); 
int blue = random(255); 
int count = 0; 
int status=0;
int xn,yn,r,x1,x2,x3,x4,y1,y2,y3,y4,r1,r2,r3,r4,dx4,dy4;
boolean st;
public static int random(int maxRange) { 
return (int) Math.round((Math.random() * maxRange)); 
} 






public BouncingBall(int width, int height) { 

canvasWidth = width; 
canvasHeight = height; 

container = new Container(); 

canvas = new DrawCanvas(); 
this.setLayout(new BorderLayout()); 
this.add(canvas, BorderLayout.CENTER); 
this.addMouseListener(this); 

start(); 

} 

public void start() { 

Thread t = new Thread() { 
public void run() { 

while (true) { 

update(); 
repaint(); 
try { 
Thread.sleep(1000 / UPDATE_RATE); 
System.out.println("thread draws");
} catch (InterruptedException e) { 
} 
} 
} 
}; 
t.start(); 
} 

public void move(Container container,Ball ball) { 

ball.x += ball.speedX; 
ball.y += ball.speedY; 

} 

public synchronized void checkwall()
{count++; 
balls.add(new Ball(5,5)); 
	}

public void update() { 

	for (int i = 0; i < balls.size(); i++) { 
	   if (balls.get(i).radius<3)
	        	balls.remove(i);
	      
	}
	
for (Ball ball : balls) { 

move(container,ball); 

}

for (int i = 0; i < balls.size(); i++) { 
    for (int j = i + 1; j < balls.size(); j++) {
        if (balls.get(i) != null && balls.get(j) != null && balls.get(i).collide(balls.get(j)))
        		{
        	balls.get(i).merge(balls.get(j));
        
        	balls.remove(j);
        	
        }
    }
}
xn=0;
yn=0;
r=0;

for (int i = 0; i < balls.size(); i++) { 
	x1=x2=x3=x4=0;
	y1=y2=y3=y4=0;
	r1=r2=r3=r4=0;
	dx4=dy4=0;
	if (balls.get(i).x - balls.get(i).radius < 0) { 
	
	st=true;
	x1=3*balls.get(i).radius;
	y1=balls.get(i).y;
	r1=(int) (0.5*balls.get(i).radius);

	balls.get(i).speedX = -balls.get(i).speedX; 
	balls.get(i).x = balls.get(i).radius; 
	balls.get(i).radius=(int) (0.5*balls.get(i).radius);




	} else if (balls.get(i).x + balls.get(i).radius > 500) { 
	
	x2=2*(500 - balls.get(i).radius);
	y2=balls.get(i).y;
	r2=(int) (0.5*balls.get(i).radius);
	
	
	System.out.println(balls.get(i).radius);

	balls.get(i).speedX = -balls.get(i).speedX; 
	balls.get(i).x = 500 - balls.get(i).radius; 
	balls.get(i).radius=(int) (0.5*balls.get(i).radius);
} 

if (balls.get(i).y - balls.get(i).radius < 0) { 
	x3= balls.get(i).x;
	y3=3*balls.get(i).radius;
	r3=(int) (0.5*balls.get(i).radius);

	balls.get(i).speedY = -balls.get(i).speedY; 
	balls.get(i).y = balls.get(i).radius; 
	balls.get(i).radius=(int) (0.5*balls.get(i).radius);
} else if (balls.get(i).y + balls.get(i).radius > 500) { 
	y4=3*(500 - balls.get(i).radius);
	x4=balls.get(i).x;
	r4=(int) (0.9*balls.get(i).radius);
	//dx4=balls.get(i).speedX;
	//dy4=-balls.get(i).speedY; 

	balls.get(i).speedY = -balls.get(i).speedY; 
	balls.get(i).y = 500 - balls.get(i).radius; 
	//balls.get(i).radius=(int) (0.25*balls.get(i).radius);
	balls.get(i).radius=(int) (0.5*balls.get(i).radius);
	
} 
if (r1!=0)
balls.add(new Ball(x1,y1,r1)); 

if (r2!=0)
balls.add(new Ball(x2,y2,r2)); 

if (r3!=0)
balls.add(new Ball(x3,y3,r3));

if (r4!=0)
balls.add(new Ball(x4,y4,r4)); 

}


} 

class DrawCanvas extends JPanel { 

public void paintComponent(Graphics g) { 

super.paintComponent(g); 
container.draw(g); 

for (Ball ball : balls) { 
ball.draw(g); 

} 
} 

public Dimension getPreferredSize() { 

return (new Dimension(canvasWidth, canvasHeight)); 
} 
} 

public static void main(String[] args) { 

javax.swing.SwingUtilities.invokeLater(new Runnable() { 
public void run() { 
JFrame f = new JFrame("Bouncing Balls"); 
f.setDefaultCloseOperation(f.EXIT_ON_CLOSE); 
f.setContentPane(new BouncingBall(500, 500)); 
f.pack(); 
f.setVisible(true); 
} 
}); 
} 

@Override 
public void mouseClicked(MouseEvent e) { 
// TODO Auto-generated method stub 
} 

@Override 
public void mouseEntered(MouseEvent e) { 
// TODO Auto-generated method stub 
} 

@Override 
public void mouseExited(MouseEvent e) { 
// TODO Auto-generated method stub 
} 

@Override 
public void mousePressed(MouseEvent e) { 
	
count++; 
balls.add(new Ball(e.getX(),e.getY())); 

} 
public void mouseDragged(MouseEvent e)
{
	
}

@Override 
public void mouseReleased(MouseEvent e) { 
// TODO Auto-generated method stub 
	speedX=e.getX();
	speedY=e.getX();
} 

public static class Ball { 

	
public Ball(int xinp,int yinp)
{x=xinp;
y=yinp;
speedX=-random(5);
speedY=-random(5);
	}
public Ball(int xinp,int yinp,int dx, int dy)
{x=xinp;
y=yinp;
speedX=dx;
speedY=dy;
	}

public Ball(int xinp,int yinp,int r,int dx, int dy)
{x=xinp;
y=yinp;
speedX=dx;
speedY=dy;
radius=r;
	}

public Ball(int xinp,int yinp,int r)
{x=xinp;
y=yinp;
speedX=random(3);
speedY=random(3);
radius=r;
	}
public int random(int maxRange) { 
return (int) Math.round(Math.random() * maxRange); 
} 

public void merge(Ball ball) {
	 ExecutorService service = Executors.newCachedThreadPool();
     
	double tWeight = this.getArea() / (this.getArea() + ball.getArea());
    double bWeight = ball.getArea() / (this.getArea() + ball.getArea());
    
    // update position, velocity and size of ball
    x = (int) (x * tWeight + ball.x * bWeight);
    y = (int) (y * tWeight + ball.y * bWeight);
  //  speedX = (int) (speedX * tWeight + ball.speedX * bWeight);
  //  speedY = (int) (speedY * tWeight + ball.speedY * bWeight);
    radius = (int) Math.sqrt((getArea() + ball.getArea()) / Math.PI);
    
    
    ball.radius = 0;
}

// returns the area of the ball
private double getArea() {
    return Math.PI * radius * radius;
}

public boolean collide(Ball ball) {
	
	        double deltaX = this.x - ball.x;
	        double deltaY = this.y - ball.y;
	        double dist = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
	        return dist <= this.radius + ball.radius;
	    
}



int x = random(480); 
int y = random(480); 
int speedX =3; //random(30); 
int speedY =3; //random(30); 
int radius =30; //random(50); 
int red = random(255); 
int green = random(255); 
int blue = random(255); 
int i = 0; 
int status=0;



public void draw(Graphics g) { 

g.setColor(new Color(red, green, blue)); 
g.fillOval((int) (x - radius), (int) (y - radius), 
(int) (2 * radius), (int) (2 * radius)); 

} 


}


public static class Container{ 

private static final int HEIGHT = 500; 
private static final int WIDTH = 500; 
private static final Color COLOR = Color.WHITE; 

public void draw(Graphics g) { 

g.setColor(COLOR); 
g.fillRect(0, 0, WIDTH, HEIGHT); 
}


} 
}


