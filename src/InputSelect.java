import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
class InputSelect extends JPanel{
    private static int BUTTON_HGAP = 50;

    private Controller controller;
    private JButton randomInput;
    private JButton textInput;
    private JButton userInput;

    private JButton back;

    InputSelect(Controller controller){
        this.controller = controller;
        initComponents();
        initPanels();
    }
    private void initComponents(){
        randomInput = new JButton("Random");
        randomInput.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt){
               controller.setInput(new Input());
               controller.show(Controller.SIMULATION_SCREEN);
            }
        });
        textInput = new JButton("Text-File");
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
        userInput.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt){
               controller.promptUserInput();             
            }
        });  

        back = new JButton("Back");
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


        JPanel centeringPanel = new JPanel();
        centeringPanel.setBorder(BorderFactory.createEmptyBorder(controller.getHeight()/2,0,0,0));
        centeringPanel.add(buttonPanel);

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new java.awt.BorderLayout());
        southPanel.add(back, java.awt.BorderLayout.WEST);
        add(centeringPanel, java.awt.BorderLayout.CENTER);
        add(southPanel, java.awt.BorderLayout.SOUTH);
    }
}
