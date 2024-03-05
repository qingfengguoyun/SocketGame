import utils.OptionScanner;

public class Application {

    public void start(){
        OptionScanner optionScanner=new OptionScanner();
        optionScanner.run();
    }

    public static void main(String[] args) {
        Application application =new Application();
        application.start();
    }
}
