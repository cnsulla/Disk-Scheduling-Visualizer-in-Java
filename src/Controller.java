import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.CardLayout;
class Controller {    
    private static int FRAME_WIDTH = 800;
    private static int FRAME_HEIGHT = 500;
    
    private JFrame frame;
    private JPanel cardPanel;
    private CardLayout cardLayout;

    private MainMenu mainMenu;
    static String MAIN_MENU = "MainMenu";
    private InputSelect inputSelect;
    static String INPUT_SELECT = "InputSelect";
    private SimulationScreen simulationScreen;
    static String SIMULATION_SCREEN = "SimulationScreen";
    private ExportScreen exportScreen;
    static String EXPORT_SCREEN = "ExportScreen";
    
    private OnscreenInput onscreenInput;  //dialog for user input
    private Input input;
    Controller(){
    }
    void initialize(){
        initFrame();
        initComponents();
    }
    private void initFrame(){
        frame = new JFrame();
        cardPanel = new JPanel();
        cardPanel.setPreferredSize(new java.awt.Dimension(800,500));
        cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);
        
        frame.add(cardPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    private void initComponents(){
        mainMenu = new MainMenu(this);
        cardPanel.add(mainMenu, MAIN_MENU);

        inputSelect = new InputSelect(this);
        cardPanel.add(inputSelect, INPUT_SELECT);

        onscreenInput = new OnscreenInput(this, true);
        
        simulationScreen = new SimulationScreen(this);
        cardPanel.add(simulationScreen, SIMULATION_SCREEN);

        exportScreen = new ExportScreen(this);
        cardPanel.add(exportScreen, EXPORT_SCREEN);
    }
    void show(String screenName){
        cardLayout.show(cardPanel,screenName);
    }
    int getHeight(){
        return FRAME_HEIGHT;
    }
    int getWidth(){
        return FRAME_WIDTH;
    }
    void setInput(Input input){
        this.input = input;
        simulationScreen.setInitialParams(input.getHead(),
            input.getRequestsAsString(input.getRequests()),
            input.isGoingLeft()
        );
    }
    Input getInput(){
        return input;
    } 
    JFrame getFrame(){
        return frame;
    }
    void promptUserInput(){
        onscreenInput.display();
    }
    void initExport(int head, boolean direction, String algo){
        exportScreen.init(head,direction, algo);   
    }
}
