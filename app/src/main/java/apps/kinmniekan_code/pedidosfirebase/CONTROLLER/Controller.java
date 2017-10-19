package apps.kinmniekan_code.pedidosfirebase.CONTROLLER;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import apps.kinmniekan_code.pedidosfirebase.MODEL.Category;
import apps.kinmniekan_code.pedidosfirebase.MODEL.Product;
import apps.kinmniekan_code.pedidosfirebase.Util.HTTPConnectionManager;
import apps.kinmniekan_code.pedidosfirebase.Util.ResultListener;

/**
 * Created by Felix on 16/10/2017.
 */

public class Controller {
    private FirebaseDatabase database;


    public Controller() {
        database = FirebaseDatabase.getInstance();
    }

    public void downLoadCategories(final ResultListener<ArrayList<Category>> viewListener) {

        DatabaseReference myRef = database.getReference("Categorias");
        final ArrayList<Category> categories = new ArrayList<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Category category = data.getValue(Category.class);
                    categories.add((category));
                }
                viewListener.finish(categories);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
