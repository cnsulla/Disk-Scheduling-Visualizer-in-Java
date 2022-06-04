import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;

import java.awt.Font;
class ExportScreen extends JPanel{
    private Controller controller;
    private JButton asImage;
    private JButton asPDF;
    private BufferedImage outputImage;

    private JButton backToMainMenu;
    private JButton backToSimulator;
    // private static final int WIDTH = 852;
    // private static final int HEIGHT = 480;
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;

    private int head;
    private boolean direction;
    private String algo;
    private int[] path;
    private BufferedImage img;
    private Input input;

    ExportScreen(Controller controller){
        this.controller = controller;
        initComponents();
        initPanels();
    }    

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if(outputImage != null){
            g.drawImage(outputImage, 0,0, null);
        }
    }
    void initComponents(){
        asImage = new JButton("Export as PNG");
        asImage.setBackground(Color.decode("#17256f"));
        asImage.setForeground(Color.WHITE);
        asImage.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                saveAsImage();                
            }
        });

        asPDF = new JButton("Export as PDF");
        asPDF.setBackground(Color.decode("#17256f"));
        asPDF.setForeground(Color.WHITE);
        asPDF.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                saveAsPDF();   
            }
        });

        backToMainMenu = new JButton("Back to Main Menu");
        backToMainMenu.setBackground(Color.decode("#17256f"));
        backToMainMenu.setForeground(Color.WHITE);
        backToMainMenu.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.show(Controller.MAIN_MENU);                
            }
        });

        backToSimulator = new JButton("Back to Simulator");
        backToSimulator.setBackground(Color.decode("#17256f"));
        backToSimulator.setForeground(Color.WHITE);
        backToSimulator.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.show(Controller.SIMULATION_SCREEN);                
            }
        });
    }
    void initPanels(){
        setLayout(new java.awt.BorderLayout());
        JPanel centeringPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(img.getScaledInstance(this.getWidth(), this.getHeight(), java.awt.Image.SCALE_DEFAULT), 0, 0, null);
            }
        };
        centeringPanel.setBorder(BorderFactory.createEmptyBorder(controller.getHeight()/3,0,0,0));
        centeringPanel.add(asImage);
        centeringPanel.add(asPDF);
        try {
            //img = ImageIO.read(new File(System.getProperty("user.dir") + "/src/images/exportscreen.png"));
            img = ImageIO.read(getClass().getResource("images/exportscreen.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        centeringPanel.setBackground(Color.decode("#17256f"));
        // centeringPanel.setSize(800, 500);
        centeringPanel.repaint();

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new java.awt.BorderLayout());
        southPanel.setBackground(Color.decode("#17256f"));
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new java.awt.GridLayout(0,2,10,0));
        gridPanel.setBackground(Color.decode("#17256f"));
        gridPanel.add(backToMainMenu);
        gridPanel.add(backToSimulator);
        southPanel.add(gridPanel, java.awt.BorderLayout.WEST);

        add(centeringPanel, java.awt.BorderLayout.CENTER);
        add(southPanel, java.awt.BorderLayout.SOUTH);
    }
    void init(int head, boolean direction, String algo) {
        this.input = controller.getInput();
        this.head = head;
        this.direction = direction;
        this.algo = algo;

        this.path = input.getPath(head, direction);
        outputImage = generateBufferedImage(
            this.path, 
            input.getSchedule(head, direction));
    }    
    void saveAsImage(){
        JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
        FileNameExtensionFilter pngFilter = new FileNameExtensionFilter("PNG files", "png");
        fileChooser.setFileFilter(pngFilter);
        if(fileChooser.showSaveDialog(controller.getFrame()) == JFileChooser.APPROVE_OPTION){
            File outputFile = fileChooser.getSelectedFile();  
            try {
                ImageIO.write(outputImage, "png", outputFile);
                JOptionPane.showMessageDialog(controller.getFrame(), "Saved as image successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(controller.getFrame(), "An error occured", "Error", JOptionPane.ERROR_MESSAGE);
            } 
        }
    }
    void saveAsPDF(){
        JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
        FileNameExtensionFilter pdfFilter=  new FileNameExtensionFilter("PDF files", "pdf");
        fileChooser.setFileFilter(pdfFilter);
        if(fileChooser.showSaveDialog(controller.getFrame()) == JFileChooser.APPROVE_OPTION){
            File outputFile = fileChooser.getSelectedFile();
            String address = outputFile.getAbsolutePath();
            try {
                PdfWriter pdfWriter = new PdfWriter(address);
                PdfDocument doc = new PdfDocument(pdfWriter);
                Document document = new Document(doc);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(outputImage, "png", baos);
                Image iTextImage = new Image(ImageDataFactory.create(outputImage, java.awt.Color.WHITE));
                document.add(iTextImage);
                document.close();

                JOptionPane.showMessageDialog(controller.getFrame(), "Saved as PDF successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                
            } catch (IOException e) {
                JOptionPane.showMessageDialog(controller.getFrame(), "An error occured", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        
    }
    private BufferedImage generateBufferedImage(int[] path, int[] schedule){
        BufferedImage bufferedImage = new BufferedImage(WIDTH,HEIGHT+100,BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
                RenderingHints.VALUE_STROKE_PURE);

        g2d.setPaint(new Color(255,255,255));
        g2d.fillRect(0, 0, WIDTH, HEIGHT+100);

        g2d.setStroke(new java.awt.BasicStroke(1));
        drawBar(g2d);
        drawPath(path, g2d);
        drawSchedule(path, schedule, g2d);
        drawDetails(g2d);
        return bufferedImage;
    }
    private void drawBar(Graphics2D g2d){
        g2d.setPaint(Color.BLACK);
        g2d.drawLine(20,20,WIDTH-20, 20);

        int barWidth = WIDTH-40;

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
    private void drawPath(int[] path, Graphics2D g2d){
        if(path == null || path.length == 0){
            return;
        }
        for(int i = 0; i < path.length-1; i++){
            int x1 = getXPos(path[i]);
            int y1 = getYPos(i, path.length);
            int x2 = getXPos(path[i+1]);
            int y2 = getYPos(i+1, path.length);

            g2d.drawLine(x1,y1,x2,y2);
            g2d.drawLine(x1, y1, x2, y2);
        }
    }
    private void drawSchedule(int[] path, int[] schedule, Graphics2D g2d){
        int scheduleIndex = 0;
        for(int i = 0; i < path.length; i++){
            if(path[i] != schedule[scheduleIndex]){
                continue;
            }
            int x1 = getXPos(path[i]);
            int y1 = getYPos(i, path.length);

            g2d.fillOval(x1-5, y1-5, 10, 10);
            //g2d.setStroke(new java.awt.BasicStroke(5));
            g2d.drawString(path[i]+"",x1,y1+20);

            scheduleIndex++;
        }
        //g2d.setStroke(new java.awt.BasicStroke(1));
    }
    private void drawDetails(Graphics2D g2d){
        g2d.setFont(new Font("TimesRoman", Font.PLAIN, 12));
        String requestString = input.getRequestsAsString(input.getRequests());
        g2d.drawString("Requests: "+requestString,20,HEIGHT+10);

        g2d.drawString("Head: "+head, 20, HEIGHT+30);

        g2d.drawString("Direction: "+ (direction? "Left":"Right"), 20, HEIGHT+50);

        g2d.drawString("Algorithm: "+algo, 20, HEIGHT + 70);

        g2d.drawString("Duration in Cylinders: "+ getDuration(), 20, HEIGHT+90);

    }
    private int getDuration(){
        int duration = 0;
        for(int i = 0; i < path.length-1; i++){
            int increment = path[i] -path[i+1];
            if(increment < 0){
                increment*= -1;
            }
            duration+= increment;
        }
        return duration;
    }
    private int getXPos(int value){
        int barWidth = WIDTH-40;
        return (int)((value/199.0)*barWidth)+20;
    }
    private int getYPos(int index, int pathLength){
        int verticalAllowance = (HEIGHT-60)/pathLength;
        return verticalAllowance*index+60;
    }
}
