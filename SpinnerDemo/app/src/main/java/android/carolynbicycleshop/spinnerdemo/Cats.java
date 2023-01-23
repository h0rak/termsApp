package android.carolynbicycleshop.spinnerdemo;

/**
 * Project: SpinnerDemo
 * Package: android.carolynbicycleshop.spinnerdemo
 * <p>
 * User: carolyn.sher
 * Date: 3/1/2022
 * Time: 4:01 PM
 * <p>
 * Created with Android Studio
 * To change this template use File | Settings | File Templates.
 */
public class Cats {
    private String catName;
    private String catColor;

    public Cats(String catName,String catColor){
        this.catColor=catColor;
        this.catName=catName;
    }
    public String toString(){
        return this.catName;
    }
    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getCatColor() {
        return catColor;
    }

    public void setCatColor(String catColor) {
        this.catColor = catColor;
    }

}
