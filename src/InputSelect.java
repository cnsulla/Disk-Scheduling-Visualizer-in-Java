import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics;

import java.io.File;
import java.io.IOException;

class InputSelect extends JPanel{
    private static int BUTTON_HGAP = 50;

    private Controller controller;
    private JButton randomInput;
    private JButton textInput;
    private JButton userInput;
    private JButton back;
    private BufferedImage img;

    InputSelect(Controller controller){
        this.controller = controller;
        initComponents();
        initPanels();
    }
    private void initComponents(){
        randomInput = new JButton("Random");
        randomInput.setBackground(Color.decode("#17256f"));
        randomInput.setForeground(Color.WHITE);
        randomInput.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt){
               controller.setInput(new Input());
               controller.show(Controller.SIMULATION_SCREEN);
            }
        });

        textInput = new JButton("Text-File");
        textInput.setBackground(Color.decode("#17256f"));
        textInput.setForeground(Color.WHITE);
        textInput.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt){
                try{
                    JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir")+"/src/inputs");
                    FileNameExtensionFilter txtFilter=  new FileNameExtensionFilter("Text files", "txt");
                    fileChooser.setFileFilter(txtFilter);

                    if(fileChooser.showOpenDialog(controller.getFrame()) == JFileChooser.APPROVE_OPTION){
                        Input input = new Input(fileChooser.getSelectedFile());   
                        controller.setInput(input);
                        controller.show(Controller.SIMULATION_SCREEN);
                    }
                }
                catch(Exception e){
                    JOptionPane.showMessageDialog(controller.getFrame(),e.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        userInput = new JButton("User Input");   
        userInput.setBackground(Color.decode("#17256f"));
        userInput.setForeground(Color.WHITE);
        userInput.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt){
               controller.promptUserInput();             
            }
        });  

        back = new JButton("Back");
        back.setBackground(Color.decode("#17256f"));
        back.setForeground(Color.WHITE);
        back.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.show(Controller.MAIN_MENU);
            }
        });
    }
    private void initPanels(){
        setLayout(new java.awt.BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new java.awt.GridLayout(0,3,BUTTON_HGAP, 0));        
        buttonPanel.add(randomInput);
        buttonPanel.add(textInput);
        buttonPanel.add(userInput);
        buttonPanel.setBackground(Color.decode("#17256f"));


        JPanel centeringPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(img, 0, 0, null);
            }
        };
        centeringPanel.setBorder(BorderFactory.createEmptyBorder(controller.getHeight()/2,0,0,0));
        centeringPanel.add(buttonPanel);
        try {
            img = ImageIO.read(new File(System.getProperty("user.dir") + "/src/images/inputscreen.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        centeringPanel.setBackground(Color.decode("#17256f"));
        // centeringPanel.setSize(800, 500);
        centeringPanel.repaint();

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new java.awt.BorderLayout());
        southPanel.setBackground(Color.decode("#17256f"));
        southPanel.add(back, java.awt.BorderLayout.WEST);
        add(centeringPanel, java.awt.BorderLayout.CENTER);
        add(southPanel, java.awt.BorderLayout.SOUTH);
    }
}
