package android.carolynbicycleshop.bikedolphin.Database;

import android.carolynbicycleshop.bikedolphin.DAO.PartDAO;
import android.carolynbicycleshop.bikedolphin.DAO.ProductDAO;
import android.carolynbicycleshop.bikedolphin.Entities.Part;
import android.carolynbicycleshop.bikedolphin.Entities.Product;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * Project: Bike Dolphin
 * Package: android.carolynbicycleshop.bikedolphin.Database
 * <p>
 * User: carolyn.sher
 * Date: 10/22/2022
 * Time: 2:10 PM
 * <p>
 * Created with Android Studio
 * To change this template use File | Settings | File Templates.
 */
@Database(entities = {Product.class, Part.class}, version = 1,exportSchema = false)
public abstract class BicycleDatabaseBuilder extends RoomDatabase {
    public abstract ProductDAO productDAO();
    public abstract PartDAO partDAO();

    private static volatile BicycleDatabaseBuilder INSTANCE;

    static BicycleDatabaseBuilder getDatabase(final Context context){
        if(INSTANCE==null){
            synchronized (BicycleDatabaseBuilder.class){
                if(INSTANCE==null){
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(),BicycleDatabaseBuilder.class,"MyBicycleDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
