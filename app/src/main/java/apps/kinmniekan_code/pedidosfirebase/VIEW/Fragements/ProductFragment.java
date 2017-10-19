package apps.kinmniekan_code.pedidosfirebase.VIEW.Fragements;


import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import apps.kinmniekan_code.pedidosfirebase.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFragment extends Fragment {
    public static final String KEY_PRODUCTNAME = "name";
    public static final String KEY_PRODUCTPRICE = "price";
    public static final String KEY_IMG_SMALL = "small";
    public static final String KEY_IMG_BIG = "big";
    public static final String KEY_COMENTS = "coments";
    public static final String KEY_DESCRIPTION = "description";

    private View view;
    private TextView tv_description, tv_price;
    private ImageView imageView;
    private Button btnquantity, btnobservations, btnAdd;
    private String img;

    public ProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_product, container, false);

        Bundle bundle = getArguments();
        String name = bundle.getString(KEY_PRODUCTNAME);
        Double price = bundle.getDouble(KEY_PRODUCTPRICE);
        String description = bundle.getString(KEY_DESCRIPTION);
        img = bundle.getString(KEY_IMG_BIG);

        tv_description = view.findViewById(R.id.tv_description_fragmentProduct);
        tv_description.setText(description);
        tv_price = view.findViewById(R.id.tv_price_fragmentProduct);
        tv_price.setText(("$"+String.format( "%.2f", price)));
        btnquantity = view.findViewById(R.id.btn_number_piker_fragment_producto);
        btnquantity.setOnClickListener(listener);
        imageView = view.findViewById(R.id.iv_img_fragmentProduct);
        upLoadImg();

        return view;
    }

    private void upLoadImg(){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference raiz = storage.getReferenceFromUrl("gs://pedidosfirebase.appspot.com/");
        StorageReference imagenes = raiz.child("imagenes");
        StorageReference foto = imagenes.child(img);

        foto.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(getContext())
                        .load(uri)
                        .into(imageView);
            }
        });
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_number_piker_fragment_producto:
                    numberPikerDialog();
                    break;
            }
        }
    };

    private void numberPikerDialog(){
        NumberPicker numberPicker = new NumberPicker(view.getContext());
        numberPicker.setMaxValue(20);
        numberPicker.setMinValue(0);

        NumberPicker.OnValueChangeListener valueChangeListener = new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                btnquantity.setText("cantidad ("+ i1 + ")");
            }
        };

        numberPicker.setOnValueChangedListener(valueChangeListener);
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext()).setView(numberPicker);
        builder.setTitle("cantidad")
                .setIcon(R.drawable.ic_price1custom);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.show();
    }

}
