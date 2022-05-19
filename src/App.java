public class App {
    public static void main(String[] args) throws Exception {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try{
                    Controller ds = new Controller();
                    ds.initialize();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}
