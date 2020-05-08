
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Majd Talji <en.majd.talji@gmail.com>
 */
public class Product {

    SimpleIntegerProperty id;
    SimpleStringProperty name;
    SimpleDoubleProperty price;
    SimpleStringProperty addedDate;
    SimpleStringProperty imageUrl;

    public Product() {
        this(0, "", 0.0, "", "");
    }

    public Product(int id, String name, double price, String addedDate, String imageUrl) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
        this.addedDate = new SimpleStringProperty(addedDate);
        this.imageUrl = new SimpleStringProperty(imageUrl);
    }

    public int getId() {
        return this.id.getValue();
    }

    public void setId(int id) {
        this.id = new SimpleIntegerProperty(id);
    }

    public String getName() {
        return this.name.getValue();
    }

    public void setName(String name) {
        this.name = new SimpleStringProperty(name);
    }

    public double getPrice() {
        return this.price.getValue();
    }

    public void setPrice(double price) {
        this.price = new SimpleDoubleProperty(price);
    }

    public String getAddedDate() {
        return this.addedDate.getValue();
    }

    public void setAddedDate(String addedDate) {
        this.addedDate = new SimpleStringProperty(addedDate);
    }

    public String getImageUrl() {
        return this.imageUrl.getValue();
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = new SimpleStringProperty(imageUrl);
    }

}
