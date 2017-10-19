package apps.kinmniekan_code.pedidosfirebase.VIEW;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;


import java.util.ArrayList;

import apps.kinmniekan_code.pedidosfirebase.CONTROLLER.Controller;
import apps.kinmniekan_code.pedidosfirebase.MODEL.Category;
import apps.kinmniekan_code.pedidosfirebase.MODEL.Product;
import apps.kinmniekan_code.pedidosfirebase.R;
import apps.kinmniekan_code.pedidosfirebase.Util.HTTPConnectionManager;
import apps.kinmniekan_code.pedidosfirebase.Util.ResultListener;
import apps.kinmniekan_code.pedidosfirebase.VIEW.Adapters.CategoryAdapter;
import apps.kinmniekan_code.pedidosfirebase.VIEW.Fragements.CategoryFragment;
import apps.kinmniekan_code.pedidosfirebase.VIEW.Fragements.ProductFragment;

public class MenuActivity extends AppCompatActivity implements CategoryFragment.Notify{
    private CategoryAdapter categoryAdapter;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if(HTTPConnectionManager.isNetworkinOnline(this)){
            new Controller().downLoadCategories(new ResultListener<ArrayList<Category>>() {
                @Override
                public void finish(ArrayList<Category> resultado) {
                    TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayoutMenu);
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    categoryAdapter = new CategoryAdapter(fragmentManager,resultado);
                    ViewPager viewPager = (ViewPager) findViewById(R.id.viewPagerMenu);
                    viewPager.setAdapter(categoryAdapter);
                    tabLayout.setupWithViewPager(viewPager);
                }
            });
        } else {
            Toast.makeText(this,R.string.connection_lost,Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void goProductDescription(Product product) {
        Intent intent = new Intent(this, ProductActivity.class);
        Bundle bundle = new Bundle();

        bundle.putString(ProductFragment.KEY_PRODUCTNAME,product.getName());
        bundle.putString(ProductFragment.KEY_DESCRIPTION,product.getDescription());
        bundle.putSerializable(ProductFragment.KEY_COMENTS,product.getCommentaries());
        bundle.putString(ProductFragment.KEY_IMG_BIG,product.getImageLarge());
        bundle.putString(ProductFragment.KEY_IMG_SMALL,product.getImageSmall());
        bundle.putDouble(ProductFragment.KEY_PRODUCTPRICE,product.getPrice());

        intent.putExtras(bundle);

        startActivity(intent);
    }


}
