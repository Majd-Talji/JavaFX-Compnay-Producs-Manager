
import java.io.File;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Majd Talji <en.majd.talji@gmail.com>
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        
        AllProductsPane allProductsPane = new AllProductsPane();
        
        Scene scene = new Scene(allProductsPane, 1070, 640);
        stage.setTitle("Company Products Manager");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        File file = new File(DBInfo.UPLOADED_FILE_PATH);
        if (!file.exists()) {
            file.mkdirs();
        }
        launch(args);
    }

}
