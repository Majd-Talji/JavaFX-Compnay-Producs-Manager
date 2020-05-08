
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 *
 * @author Majd Talji <en.majd.talji@gmail.com>
 */
public class Common implements DBInfo {

    public static Connection getConnection() {

        Connection con;

        try {
            con = DriverManager.getConnection(DB_NAME_WITH_ENCODING, USER, PASSWORD);
            return con;
        } catch (SQLException e) {
            SpecialAlert alert = new SpecialAlert();
            alert.show("Error", e.getMessage(), Alert.AlertType.ERROR);
            return null;
        }

    }

    public static String generateImagePath(File selectFile) {
        Date date = new Date();

        SimpleDateFormat sdf = new SimpleDateFormat("Y-M-d-hh-mm-ss");

        String fileExtension = selectFile.getName().substring(selectFile.getName().lastIndexOf("."));

        return UPLOADED_FILE_PATH + sdf.format(date) + fileExtension;
    }

    public static String saveSelectImage(File selectFile) {
        String createImagePath = Common.generateImagePath(selectFile);
        try {
            FileInputStream in = new FileInputStream(selectFile);

            FileOutputStream out = new FileOutputStream(createImagePath);

            int c;
            while ((c = in.read()) != -1) {
                out.write(c);
            }

            in.close();
            out.close();
        } catch (Exception e) {
            SpecialAlert alert = new SpecialAlert();
            alert.show("Connection Error", e.getMessage(), Alert.AlertType.ERROR);
        }
        return createImagePath;
    }

    public static void deleteImage(String filePath) {
        try {
            File imageToDelete = new File(filePath);
            imageToDelete.delete();
        } catch (Exception e) {
            SpecialAlert alert = new SpecialAlert();
            alert.show("Connection Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

}
