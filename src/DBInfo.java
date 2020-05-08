
/**
 *
 * @author Majd Talji <en.majd.talji@gmail.com>
 */
public interface DBInfo {

    String DB_NAME = "jdbc:mysql://localhost/products_db";

    String ENCODING = "?useUnicode=yes&characterEncoding=UTF-8";

    String DB_NAME_WITH_ENCODING = DB_NAME + ENCODING;

    String USER = "root";

    String PASSWORD = "";

    String UPLOADED_FILE_PATH = "C:/MyFiles/uploaded-files/";

}
