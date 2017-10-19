package apps.kinmniekan_code.pedidosfirebase.VIEW.Fragements;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import apps.kinmniekan_code.pedidosfirebase.MODEL.Category;
import apps.kinmniekan_code.pedidosfirebase.MODEL.Product;
import apps.kinmniekan_code.pedidosfirebase.R;
import apps.kinmniekan_code.pedidosfirebase.VIEW.Adapters.ProductAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {
    private Integer idfotoPrueba;
    private String categoryName;
    private ArrayList<Product> menu;
    private Notify notify;

    private ImageView iv_Background;
    private TextView tv_Title;
    private RecyclerView recycler;
    private ProductAdapter adapter;

    public static final String KEY_IMAGE = "key_image";
    public static final String KEY_NAME = "key_name";
    public static final String KEY_MENU = "key_menu";

    public static CategoryFragment fragmentFactory(Category category) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_IMAGE,category.getImage());
        bundle.putString(KEY_NAME, category.getName());
        bundle.putSerializable(KEY_MENU,category.getMenu());

        CategoryFragment categoryFragment = new CategoryFragment();
        categoryFragment.setArguments(bundle);
        return categoryFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        idfotoPrueba = bundle.getInt(KEY_IMAGE);
        categoryName = bundle.getString(KEY_NAME);
        menu = (ArrayList<Product>) bundle.getSerializable(KEY_MENU);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        Typeface face=Typeface.createFromAsset(getContext().getAssets(),"fonts/permanent_marker.ttf");

        iv_Background = view.findViewById(R.id.iv_background_category);
        tv_Title = view.findViewById(R.id.tv_titulo_category);
        tv_Title.setTypeface(face);
        recycler = view.findViewById(R.id.recycler_category);

        tv_Title.setText(categoryName);

        adapter = new ProductAdapter(menu);
        recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        adapter.setListener(listener);
        recycler.setAdapter(adapter);

        return view;
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Integer position = recycler.getChildAdapterPosition(view);
            Product product = adapter.selectProduct(position);

            notify = (Notify) getActivity();
            notify.goProductDescription(product);
        }
    };

    public interface Notify{
        void goProductDescription(Product product);
    }

}
