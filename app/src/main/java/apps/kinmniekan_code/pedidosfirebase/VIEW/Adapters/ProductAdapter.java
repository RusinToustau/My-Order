package apps.kinmniekan_code.pedidosfirebase.VIEW.Adapters;

import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import apps.kinmniekan_code.pedidosfirebase.MODEL.Product;
import apps.kinmniekan_code.pedidosfirebase.R;

/**
 * Created by Felix on 16/10/2017.
 */

public class ProductAdapter extends RecyclerView.Adapter implements View.OnClickListener {
    private ArrayList<Product> list;
    private View.OnClickListener listener;

    public ProductAdapter(ArrayList<Product> list) {
        this.list = list;
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_producto, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(this);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.uploadView(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onClick(View view) {
        if(listener != null){
            listener.onClick(view);
        }
    }

    public Product selectProduct(Integer position){
        return list.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private TextView price;
        private ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_nombre_cv_producto);
            price = itemView.findViewById(R.id.tv_precio_cv_producto);
            image = itemView.findViewById(R.id.iv_imagen_cv_producto);
        }

        public void uploadView(Product product){
            Typeface face=Typeface.createFromAsset(itemView.getContext()
                    .getAssets(),"fonts/gloriahallelujah.ttf");
            title.setTypeface(face);
            title.setText(product.getName());
            price.setText("$"+String.format( "%.2f", product.getPrice()));

            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference raiz = storage.getReferenceFromUrl("gs://pedidosfirebase.appspot.com/");
            StorageReference imagenes = raiz.child("imagenes");
            StorageReference foto = imagenes.child(product.getImageSmall());

            foto.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.with(itemView.getContext())
                            .load(uri)
                            .into(image);
                }
            });

        }
    }

}
