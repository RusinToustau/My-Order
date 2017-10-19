package apps.kinmniekan_code.pedidosfirebase.MODEL;


import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Felix on 16/10/2017.
 */

public class Category implements Serializable{
    private String id;
    private String image;
    private String name;
    private ArrayList<Product> menu = new ArrayList<>();

    public Category() {
    }

    public Category(String image, String name) {
        this.image = image;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Product> getMenu() {
        return menu;
    }

    public void setMenu(ArrayList<Product> menu) {
        this.menu = menu;
    }

    public void agregarProducto(Product product){
        menu.add(product);
    }
}
