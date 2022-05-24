import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.imageio.ImageIO;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;


class MainMenu extends JPanel{
    private static int BUTTON_VGAP = 20;
    
    private Controller controller;
    private JButton start;
    private JButton exit;
    private JLabel label1;
    private BufferedImage img;

    MainMenu(Controller controller){
        this.controller = controller;
        initComponents();
        initPanels();
    }

    private void initComponents(){
        
        label1 = new JLabel("Disk Scheduling Visualizer", SwingConstants.CENTER);
        label1.setPreferredSize(new Dimension(200, 45));

        start = new JButton("Start");
        start.setBackground(Color.decode("#17256f"));
        start.setForeground(Color.WHITE);
        start.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt){
                MainMenu.this.controller.show(Controller.INPUT_SELECT);
            }
        });

        exit = new JButton("Exit");
        exit.setBackground(Color.decode("#17256f"));
        exit.setForeground(Color.WHITE);
        exit.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt){
               System.exit(0);
            }
        });
    }

    private void initPanels(){        
        setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2,1,0,BUTTON_VGAP));
        buttonPanel.setBackground(Color.decode("#17256f"));
        
        buttonPanel.add(start);
        buttonPanel.add(exit);
        
        JPanel centeringPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(img.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_DEFAULT), 0, 0, null);
            }
        };
        centeringPanel.setBorder(BorderFactory.createEmptyBorder((controller.getHeight()/2),0,0,0));  
        centeringPanel.setBackground(Color.BLUE);
        try {
            img = ImageIO.read(new File(System.getProperty("user.dir") + "/src/images/splash.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        // centeringPanel.setSize(800, 500);
        centeringPanel.repaint();

        // centeringPanel.add(label1);
        centeringPanel.add(buttonPanel);
        add(centeringPanel, BorderLayout.CENTER);
    }
}
