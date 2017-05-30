package info.kamlesh.Franzfactor;

/**
 * Created by hp on 13-03-2017.
 */

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.Collections;
import java.util.List;

public class Adapterfranchiselist extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private RecyclerView mRVfranchiselist;
    List<Datafranchise> data= Collections.emptyList();
    Datafranchise current;
    int currentPos=0;
    private Adapterfranchiselist mAdapter;

    public Adapterfranchiselist(Context context, List<Datafranchise> data){
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data=data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.card_view_list, parent,false);
        MyHolder holder=new MyHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder= (MyHolder) holder;
        Datafranchise current=data.get(position);
        myHolder.fname.setText(current.franchisename);
        myHolder.subcate.setText(current.subcategory);
        myHolder.finv.setText(current.franchiseinv);

        Glide.with(context).load("http://newtechgadget.in/fpadminpanel/images/" + current.franchiselogo)
                .placeholder(R.drawable.ic_img_error)
                .error(R.drawable.ic_img_error)
                .into(myHolder.franchisePhoto);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    class MyHolder extends RecyclerView.ViewHolder{

        TextView fname;
        ImageView franchisePhoto;
        TextView subcate;
        TextView finv;
        Button apply;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            franchisePhoto = (ImageView)itemView.findViewById(R.id.franchise_photo);
            fname = (TextView)itemView.findViewById(R.id.fname);
            subcate = (TextView)itemView.findViewById(R.id.subcategory);
            finv = (TextView)itemView.findViewById(R.id.frainv);
            apply = (Button) itemView.findViewById(R.id.btn_apply);
            apply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context,Apply.class);
                    int pos = getAdapterPosition();
                    Datafranchise clickedDataItem = data.get(pos);
                    String s = clickedDataItem.franchisename;
                    Bundle extras = new Bundle();
                    extras.putString("fname", s);
                    intent.putExtras(extras);
                    context.startActivity(intent);
                }
            });
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {

                    int pos = getAdapterPosition();

                    if(pos != RecyclerView.NO_POSITION){
                        Datafranchise clickedDataItem = data.get(pos);
                            String f = clickedDataItem.franchisename;
                           String s = clickedDataItem.subcategory;
                        String fdesc = clickedDataItem.franchisedesc;
                        String finv = clickedDataItem.franchiseinv;
                        franchisePhoto.buildDrawingCache();
                        Bitmap image= franchisePhoto.getDrawingCache();
                        Bundle extras = new Bundle();
                        extras.putParcelable("imagebitmap", image);
                        extras.putString("fname", f);
                        extras.putString("subc",s );
                        extras.putString("fdesc", fdesc);
                        extras.putString("finv", finv);
                        Intent intent = new Intent(context, Franchiseshow.class);
                        intent.putExtras(extras);
                        context.startActivity(intent);
                    }
                }
            });
        }
        }

    }

