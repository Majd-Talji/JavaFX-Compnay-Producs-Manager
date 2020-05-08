
import java.io.File;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 *
 * @author Majd Talji <en.majd.talji@gmail.com>
 */
public class AddNewProductDialog extends Dialog implements DBInfo {

    Pane pane = new Pane();
    Label nameLabel = new Label("Name");
    Label productImage = new Label("No image Selected");
    Label priceLabel = new Label("Price ( $ )");
    TextField nameField = new TextField();
    TextField priceField = new TextField();
    Button chooseImageButton = new Button("choose Image", new ImageView(new Image(this.getClass().getResourceAsStream("/images/add-image.png"))));
    Button addButton = new Button("Add Product", new ImageView(new Image(this.getClass().getResourceAsStream("/images/add-product.png"))));

    ObservableList data;

    SpecialAlert alert = new SpecialAlert();

    File selectedFile = null;

    public AddNewProductDialog(ObservableList data) {

        this.data = data;

        pane.setPrefSize(610, 390);
        productImage.setPrefSize(224, 224);
        nameLabel.setPrefSize(80, 40);
        nameField.setPrefSize(270, 40);
        priceLabel.setPrefSize(80, 40);
        priceField.setPrefSize(270, 40);
        chooseImageButton.setPrefSize(274, 45);
        addButton.setPrefSize(538, 60);

        productImage.setTranslateX(36);
        productImage.setTranslateY(40);
        nameLabel.setTranslateX(300);
        nameLabel.setTranslateY(30);
        nameField.setTranslateX(300);
        nameField.setTranslateY(70);
        priceLabel.setTranslateX(300);
        priceLabel.setTranslateY(120);
        priceField.setTranslateX(300);
        priceField.setTranslateY(160);
        chooseImageButton.setTranslateX(298);
        chooseImageButton.setTranslateY(220);
        addButton.setTranslateX(34);
        addButton.setTranslateY(310);

        chooseImageButton.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        addButton.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        nameField.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        priceLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        priceField.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        productImage.setStyle("-fx-border-color: lightgray; -fx-border-width: 2;");
        nameField.setStyle("-fx-border-color: lightgray; -fx-border-width: 2;");
        priceField.setStyle("-fx-border-color: lightgray; -fx-border-width: 2;");
        addButton.setStyle("-fx-background-color: #444; -fx-text-fill: white; -fx-cursor: hand;");
        productImage.setAlignment(Pos.CENTER);

        pane.getChildren().add(productImage);
        pane.getChildren().add(chooseImageButton);
        pane.getChildren().add(nameLabel);
        pane.getChildren().add(priceLabel);
        pane.getChildren().add(nameField);
        pane.getChildren().add(priceField);
        pane.getChildren().add(addButton);

        this.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        Node closeButton = this.getDialogPane().lookupButton(ButtonType.CLOSE);
        closeButton.managedProperty().bind(closeButton.visibleProperty());
        closeButton.setVisible(false);

        this.setTitle("Add New Product");

        this.getDialogPane().setContent(pane);

        nameField.requestFocus();

        chooseImageButton.setOnAction((event) -> {
            chooseImage();
        });

        addButton.setOnAction((event) -> {
            insertProduct();
        });
    }

    private void resetWindow() {
        nameField.setText("");
        priceField.setText("");
        productImage.setText("No image selected");
        productImage.setGraphic(null);
        selectedFile = null;
        nameField.requestFocus();
    }

    public void display(boolean value) {
        if (value) {
            resetWindow();
            this.showAndWait();
        } else {
            this.hide();
        }
    }

    private boolean checkInputs() {

        if (nameField.getText().equals("") && priceField.getText().equals("")) {
            alert.show("Required fields are missing", "Name and Price fields cannot be empt!", AlertType.INFORMATION);
            return false;
        } else if (nameField.getText().equals("")) {
            alert.show("Required fields are missing", "Please enter product name", AlertType.INFORMATION);
            return false;
        } else if (priceField.getText().equals("")) {
            alert.show("Required fields are missing", "Please enter product price", AlertType.INFORMATION);
            return false;
        }

        try {

            Double.parseDouble(priceField.getText());
            return true;

        } catch (NumberFormatException e) {
            alert.show("Error", "Price should be a decimal number (eg: 25, 10.5)", AlertType.ERROR);
            return false;
        }

    }

    private void chooseImage() {

        FileChooser fileChooser = new FileChooser();

        fileChooser.getExtensionFilters().add(
                new ExtensionFilter("Select a .JPG .PNG .GIF image", "*.jpg", "*.png", "*.gif")
        );

        selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            try {
                productImage.setText("");
                productImage.setGraphic(new ImageView(
                        new Image(selectedFile.toURI().toString(), 224, 224, true, true)
                ));
            } catch (Exception e) {
                alert.show("Error", "Failed to add Image", AlertType.ERROR);
            }
        }

    }

    private void insertProduct() {

        if (checkInputs()) {

            try {

                Connection con = Common.getConnection();

                if (con == null) {
                    alert.show("Error", "Failed to connect to database server", AlertType.ERROR);
                }

                PreparedStatement ps;

                if (selectedFile == null) {
                    ps = con.prepareStatement("INSERT INTO products(name, price, added_date) values (?,?,?)");
                } else {
                    String createImagePath = Common.saveSelectImage(selectedFile);

                    ps = con.prepareStatement("INSERT INTO products(name, price, added_date, image_url) values (?,?,?,?)");
                    ps.setString(4, createImagePath);
                }

                ps.setString(1, nameField.getText());
                ps.setDouble(2, Double.parseDouble(priceField.getText()));

                LocalDate todayLocalDate = LocalDate.now();
                Date sqlDate = Date.valueOf(todayLocalDate);

                ps.setDate(3, sqlDate);

                ps.executeUpdate();
                con.close();

                resetWindow();

                viewProductsInTheTable();
            } catch (Exception e) {
                alert.show("Error", "Insert Error", AlertType.ERROR);
            }

        }

    }

    private void viewProductsInTheTable() {

        Connection con = Common.getConnection();
        String query = "SELECT * FROM products ORDER BY id DESC LIMIT 1";

        Statement st;
        ResultSet rs;

        try {

            st = con.createStatement();
            rs = st.executeQuery(query);

            rs.next();
            Product product = new Product();

            product = new Product();
            product.setId(rs.getInt(DataBaseProduct.ID));
            product.setName(rs.getString(DataBaseProduct.NAME));
            product.setPrice(Double.parseDouble(rs.getString(DataBaseProduct.PRICE)));
            product.setAddedDate(rs.getDate(DataBaseProduct.ADD_DATE).toString());
            product.setImageUrl(rs.getString(DataBaseProduct.IMAGE_URL));

            data.add(product);

            con.close();
        } catch (Exception e) {
            alert.show("Error", e.getMessage(), AlertType.ERROR);
        }

    }

}
