import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.imageio.ImageIO;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Color;
import java.io.File;
import java.io.IOException;


class MainMenu extends JPanel{
    private static int BUTTON_VGAP = 20;
    
    private Controller controller;
    private JButton start;
    private JButton exit;
    private JLabel label1;

    MainMenu(Controller controller){
        this.controller = controller;
        initComponents();
        initPanels();
    }

    private void initComponents(){

        
        // label1 = new JLabel();
        // label1.setPreferredSize(new Dimension(250, 100));

        // BufferedImage img = null;
        // try {
        //     img = ImageIO.read(new File("src/images/test.png"));
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
        
        // Image dimg = img.getScaledInstance(label1.getWidth(), label1.getHeight(), Image.SCALE_SMOOTH);
        // ImageIcon icon = new ImageIcon (dimg);
        // label1.setIcon(icon);

        start = new JButton("Start");
        start.setBackground(Color.GRAY);
        start.setForeground(Color.WHITE);
        start.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt){
                MainMenu.this.controller.show(Controller.INPUT_SELECT);
            }
        });

        exit = new JButton("Exit");
        exit.setBackground(Color.GRAY);
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
        buttonPanel.setLayout(new GridLayout(2,0,0,BUTTON_VGAP));
        buttonPanel.add(start);
        buttonPanel.add(exit);
        
        JPanel centeringPanel = new JPanel();
        centeringPanel.setBorder(BorderFactory.createEmptyBorder(controller.getHeight()/2,0,0,0));

        centeringPanel.add(buttonPanel);
        // centeringPanel.add(label1);
        add(centeringPanel, BorderLayout.CENTER);
    }
}
