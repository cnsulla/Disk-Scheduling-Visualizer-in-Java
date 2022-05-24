import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

class SimulationScreen extends JPanel implements Runnable{
    private Controller controller;
    private JComboBox<String> algoBox;
    private JSpinner head;
    private JTextArea requests;
    private JRadioButton left;
    private JRadioButton right;
    
    private JSpinner speedSpinner;
    private JButton start;
    private JButton reset;

    private SimulatorPanel simPanel;
    private boolean finished;
    private boolean running;

    private Thread simulationThread;
    SimulationScreen(Controller controller){
        this.controller = controller;
        initComponents();
        initPanels();
    }
    private void initComponents(){
        algoBox = new JComboBox<String>();
        // algoBox.setBackground(Color.decode("#17256f"));
        // algoBox.setForeground(Color.WHITE);
        algoBox.addItem("FCFS");
        algoBox.addItem("SSTF");
        algoBox.addItem("LOOK");
        algoBox.addItem("C-LOOK");
        algoBox.addItem("SCAN");
        algoBox.addItem("C-SCAN");
        algoBox.setPreferredSize(new java.awt.Dimension(150,50));

        head = new JSpinner(new SpinnerNumberModel(0, 0, 199, 1));
        // head.getEditor().getComponent(0).setBackground(Color.white);

        head.setPreferredSize(new java.awt.Dimension(150,50));

        requests = new JTextArea("", 4, 20);
        requests.setBackground(Color.decode("#17256f"));
        requests.setForeground(Color.WHITE);
        requests.setLineWrap(true);
        requests.setEditable(false);
        
        left = new JRadioButton("Left");
        left.setBackground(Color.decode("#17256f"));
        left.setForeground(Color.WHITE);
        right = new JRadioButton("Right");
        right.setBackground(Color.decode("#17256f"));
        right.setForeground(Color.WHITE);
        ButtonGroup direction = new ButtonGroup();
        direction.add(left);
        direction.add(right);

        speedSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 199, 1));
        speedSpinner.setPreferredSize(new java.awt.Dimension(200,50));
        
        start = new JButton("Start");
        start.setBackground(Color.decode("#17256f"));
        start.setForeground(Color.WHITE);
        start.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!running && !finished){
                    start();
                    start.setText("Stop");
                }
                else if(running && !finished){
                    stop();
                    start.setText("Start");
                }
                else if(!running && finished){
                    export();
                }

            }
        });
        reset = new JButton("Back to Input Select");
        reset.setBackground(Color.decode("#17256f"));
        reset.setForeground(Color.WHITE);
        reset.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(simulationThread == null){
                    controller.show(Controller.INPUT_SELECT);
                }
                else{
                    reset();
                }
            }
        });

        simPanel = new SimulatorPanel();
    }
    private void initPanels(){
        setLayout(new java.awt.BorderLayout());        
        Border emptyBorder = BorderFactory.createEmptyBorder();

        JPanel algoPanel = new JPanel();        
        algoPanel.setBorder(BorderFactory.createTitledBorder(emptyBorder, "Disk Scheduling Algorithm", 
        TitledBorder.LEADING, TitledBorder.TOP, new Font("sans", Font.PLAIN,12), Color.WHITE));
        algoPanel.setBackground(Color.decode("#17256f"));
        algoPanel.add(algoBox);

        JPanel headPanel = new JPanel();
        headPanel.setBorder(BorderFactory.createTitledBorder(emptyBorder, "Initial Position",
        TitledBorder.LEADING, TitledBorder.TOP, new Font("sans", Font.PLAIN,12), Color.WHITE));
        headPanel.setBackground(Color.decode("#17256f"));
        headPanel.add(head);

        JPanel directionPanel = new JPanel();
        directionPanel.setLayout(new java.awt.GridLayout(0,2,0,0));
        directionPanel.setBackground(Color.decode("#17256f"));
        directionPanel.setBorder(BorderFactory.createTitledBorder(emptyBorder,"Direction",
        TitledBorder.LEADING, TitledBorder.TOP, new Font("sans", Font.PLAIN,12), Color.WHITE));
        directionPanel.add(left);
        directionPanel.add(right);      

        JPanel requestsPanel = new JPanel();
        requestsPanel.setBorder(BorderFactory.createTitledBorder(emptyBorder, "Requests",
        TitledBorder.LEADING, TitledBorder.TOP, new Font("sans", Font.PLAIN,12), Color.WHITE));
        requestsPanel.setBackground(Color.decode("#17256f"));
        JScrollPane requestScroll = new JScrollPane(requests);
        requestScroll.setBackground(Color.decode("#17256f"));
        requestsPanel.add(requestScroll);
        
        JPanel settingsPanel = new JPanel();
        settingsPanel.setBackground(Color.decode("#17256f"));
        settingsPanel.setLayout(new java.awt.GridLayout(4,0,0, 20));
        settingsPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        settingsPanel.add(algoPanel);
        settingsPanel.add(headPanel);        
        settingsPanel.add(requestsPanel);        
        settingsPanel.add(directionPanel);  


        JPanel speedPanel = new JPanel();
        speedPanel.setBackground(Color.decode("#17256f"));
        speedPanel.setBorder(BorderFactory.createTitledBorder(emptyBorder, "Speed (cylinders/second)", 
        TitledBorder.LEADING, TitledBorder.TOP, new Font("sans", Font.PLAIN,12), Color.WHITE));
        speedPanel.add(speedSpinner);

        JPanel buttonsPanel = new JPanel();      
        buttonsPanel.setBackground(Color.decode("#17256f"));  
        buttonsPanel.setLayout(new java.awt.GridLayout(1,3,0,0));
        buttonsPanel.add(start);
        buttonsPanel.add(reset);

        JPanel controlsPanel = new JPanel();
        controlsPanel.setBackground(Color.decode("#17256f"));  
        controlsPanel.setLayout(new java.awt.BorderLayout());
        controlsPanel.add(speedPanel, java.awt.BorderLayout.WEST);
        controlsPanel.add(buttonsPanel, java.awt.BorderLayout.CENTER);
        
        add(controlsPanel, java.awt.BorderLayout.SOUTH);
        add(simPanel, java.awt.BorderLayout.CENTER);
        add(settingsPanel, java.awt.BorderLayout.WEST);
    }
    private void setHead(int headValue){
        head.setValue(headValue);
    }
    private void setRequestString(String requestString) {
        requests.setText(requestString);
    }
    private void setDirection(boolean goingLeft){
        if(goingLeft){
            left.setSelected(true);
        }else{
            right.setSelected(true);
        }
    }

    void start(){
        running = true;
        if(simulationThread!= null){
            return;
        }
        Input input = controller.getInput();
        int headVal = (Integer) head.getValue();
        boolean direction = left.isSelected();

        switch((String)algoBox.getSelectedItem()){
            case "FCFS":
                input.setStrategy(new FCFS());
            break;
            case "SSTF":
                input.setStrategy(new SSTF());
            break;
            case "LOOK":
                input.setStrategy(new LOOK());
            break;
            case "C-LOOK":
                input.setStrategy(new CLOOK());
            break;
            case "SCAN":
                input.setStrategy(new SCAN());
            break;
            case "C-SCAN":
                input.setStrategy(new CSCAN());
            break;
        }
        int[] path = input.getPath(headVal, direction);
        int[] schedule = input.getSchedule(headVal, direction);
        simPanel.init(path, schedule);
        requests.setText(input.getRequestsAsString(schedule));
        
        toggleControls(false);
        simulationThread = new Thread(this);        
        simulationThread.start();
    }
    void stop(){
        running = false;
    }
    void reset(){
        running = false;
        finished = false;
        if(simulationThread!=null){
            simulationThread.interrupt();
        }
        simulationThread = null;
        simPanel.init(null, null);
        start.setText("Start");
        toggleControls(true);
    }
    void export(){
        controller.initExport((Integer)head.getValue(), left.isSelected(), (String)algoBox.getSelectedItem());
        controller.show(Controller.EXPORT_SCREEN);
    }
    void finish(){
        if(simulationThread != null){
            finished = true;
            running = false;
            start.setText("Export");
        }else{
            finished = false;
            running = false;
            start.setText("Start");
        }
    }
    public void run(){
        double t = 0.0;
        double dt = 1/60.0;

        double currTime = System.currentTimeMillis();
        while(!finished){
            if(running){
                double newTime = System.currentTimeMillis();
                double frameTime = newTime - currTime;
                currTime = newTime;
                while(frameTime > 0.0){
                    double deltaTime = Math.min(frameTime, dt);
                    simPanel.tick((Integer)speedSpinner.getValue(), deltaTime);
                    frameTime -=deltaTime; 
                    t+=deltaTime;
                }
            }
            else{
                currTime = System.currentTimeMillis();
            }
            finished = simPanel.isFinished();
        }
        finish();
    }

    private void toggleControls(boolean toggle){
        algoBox.setEnabled(toggle);
        head.setEnabled(toggle);
        left.setEnabled(toggle);
        right.setEnabled(toggle);

        reset.setText(toggle? "Back to Input Select":"Reset");
    }

    void setInitialParams(int head, String requestString, boolean goingLeft) {
        setHead(head);
        setRequestString(requestString);
        setDirection(goingLeft);
        reset();
        //simPanel.setPath(controller.getInput().getFCFS(head));
        //simPanel.setRequests(controller.getInput().getRequests());
    }
    
}
