package apps.kinmniekan_code.pedidosfirebase.MODEL;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Felix on 16/10/2017.
 */

public class Product implements Serializable{
    private String id;
    private Double price;
    private String name;
    private String category;
    private String imageLarge;
    private String imageSmall;
    private String description;
    private ArrayList<Commentary> commentaries;

    public Product() {
    }

    public Product(Double price, String name, String category, String imageLarge, String imageSmall, String description) {
        this.price = price;
        this.name = name;
        this.category = category;
        this.imageLarge = imageLarge;
        this.imageSmall = imageSmall;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageSmall() {
        return imageSmall;
    }

    public void setImageSmall(String imageSmall) {
        this.imageSmall = imageSmall;
    }

    public String getImageLarge() {
        return imageLarge;
    }

    public void setImageLarge(String imageLarge) {
        this.imageLarge = imageLarge;
    }

    public ArrayList<Commentary> getCommentaries() {
        return commentaries;
    }

    public void setCommentaries(ArrayList<Commentary> commentaries) {
        this.commentaries = commentaries;
    }
}
