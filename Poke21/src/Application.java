import service.Game1;
import utils.OptionScanner;

public class Application {

    public void start(){
//        OptionScanner optionScanner=new OptionScanner();
//        optionScanner.run();
        Game1 game1=new Game1();
        game1.run();
    }

    public static void main(String[] args) {
        Application application =new Application();
        application.start();
    }
}
