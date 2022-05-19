import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


class OnscreenInput extends JDialog{
    private Controller controller;
    
    private JLabel headLabel;
    private JSpinner head;
    private JLabel requestLabel;
    private JTextField requests;
    private JLabel directionLabel;
    private JRadioButton right;
    private JRadioButton left;
    private ButtonGroup direction;
    private JButton save;
    private JButton cancel; 
    
    OnscreenInput(Controller controller, boolean modal){
        super(controller.getFrame(), true);
        this.controller = controller;        
        initComponents();
        initPanels();
    }
    private void initComponents(){
        headLabel = new JLabel("Initial Head Position:");
        head = new JSpinner(new SpinnerNumberModel(0, 0, 199, 1));
        requestLabel = new JLabel("Cylinder Requests(separated with a comma):");
        requests = new JTextField(10);
        directionLabel = new JLabel("Initial Direction:");
        right = new JRadioButton("Right");
        left = new JRadioButton("Left");
        direction = new ButtonGroup();
        direction.add(right);
        direction.add(left);
        left.setSelected(true);
        save = new JButton("Continue");
        save.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt){
                try{
                    controller.setInput(processForm());
                    controller.show(Controller.SIMULATION_SCREEN);
                    OnscreenInput.this.dispose();
                }catch(NumberFormatException e){
                    JOptionPane.showMessageDialog(OnscreenInput.this, e.getMessage(), "Non-number input", JOptionPane.ERROR_MESSAGE);
                }catch(IllegalArgumentException e){
                    JOptionPane.showMessageDialog(OnscreenInput.this, e.getMessage(), "Request out of bounds", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        cancel = new JButton("Cancel");
        cancel.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt){
                OnscreenInput.this.dispose();
            }
        });
    }
    private void initPanels(){
        setLayout(new java.awt.BorderLayout());

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new java.awt.GridLayout(3,2,10,5));
        formPanel.add(headLabel);
        formPanel.add(head);
        formPanel.add(requestLabel);
        formPanel.add(requests);
        formPanel.add(directionLabel);
        JPanel directionButtonPanel = new JPanel();
        directionButtonPanel.setLayout(new java.awt.GridLayout(0,2));
        directionButtonPanel.add(left);
        directionButtonPanel.add(right);
        formPanel.add(directionButtonPanel);
        
        add(formPanel, java.awt.BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(save);
        buttonPanel.add(cancel);
        add(buttonPanel, java.awt.BorderLayout.SOUTH);

        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }
    private Input processForm() throws NumberFormatException, IllegalArgumentException{
        try{
            int headVal = (Integer)head.getValue();
            if(headVal < 0 || headVal > 199){
                throw new IllegalArgumentException("Initial position must be an integer between 0 and 199!");
            }

            String[] tokenizedRequests = requests.getText().split(",");
            int[] requestCylinders = new int[tokenizedRequests.length];
            for(int i=0; i<requestCylinders.length; i++){
                requestCylinders[i] = Integer.parseInt(tokenizedRequests[i]);
                if(requestCylinders[i] < 0 || requestCylinders[i] > 199){
                    throw new IllegalArgumentException("Requested cylinders must be an integer between 0 and 199");
                }
            }
            boolean goingLeft = left.isSelected();
            return new Input(headVal, requestCylinders, goingLeft);
        }catch(NumberFormatException e){
            throw new NumberFormatException("Request contains a non-number.");
        }
    }
    void display(){
       setVisible(true);
       head.setValue(0);
       requests.setText("");
       left.setSelected(true);
    }
}
