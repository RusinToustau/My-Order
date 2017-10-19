package apps.kinmniekan_code.pedidosfirebase.VIEW.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import apps.kinmniekan_code.pedidosfirebase.MODEL.Category;
import apps.kinmniekan_code.pedidosfirebase.VIEW.Fragements.CategoryFragment;

/**
 * Created by Felix on 16/10/2017.
 */

public class CategoryAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> lfragments = new ArrayList<>();
    private ArrayList<Category> myList;

    public CategoryAdapter(FragmentManager fm, ArrayList<Category> list) {
        super(fm);
        myList = list;
        for (Category category : list){
            CategoryFragment categoryFragment = CategoryFragment.fragmentFactory(category);
            lfragments.add(categoryFragment);
        }
    }


    @Override
    public Fragment getItem(int position) {
        return lfragments.get(position);
    }

    @Override
    public int getCount() {
        return lfragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return myList.get(position).getName();
    }
}
