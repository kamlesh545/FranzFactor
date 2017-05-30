package info.kamlesh.Franzfactor;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.Collections;
import java.util.List;

/**
 * Created by hp on 21-03-2017.
 */
public class Adaptermyfranchise extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private RecyclerView mRVfranchiselist;
    List<Datafranchise> data = Collections.emptyList();
    Datafranchise current;
    int currentPos = 0;
    private Adapterfranchiselist mAdapter;

    public Adaptermyfranchise(Context context, List<Datafranchise> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_view_myfranchise_list, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;
        Datafranchise current = data.get(position);
        myHolder.fname.setText(current.franchisename);
        myHolder.subcate.setText(current.subcategory);
        myHolder.finv.setText(current.franchiseinv);

        String st = current.status;

        if (st.equalsIgnoreCase("1")){

            myHolder.status.setImageResource(R.drawable.approved);

        }else if (st.equalsIgnoreCase("0")){
            myHolder.status.setImageResource(R.drawable.pending);
        } else if (st.equalsIgnoreCase("2")){
            myHolder.status.setImageResource(R.drawable.rejected);
        }

        Glide.with(context).load("http://newtechgadget.in/fpadminpanel/images/" + current.franchiselogo)
                .placeholder(R.drawable.ic_img_error)
                .error(R.drawable.ic_img_error)
                .into(myHolder.franchisePhoto);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        TextView fname;
        ImageView franchisePhoto;
        TextView subcate;
        TextView finv;
        ImageButton optionmenu;
        ImageView status;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            franchisePhoto = (ImageView) itemView.findViewById(R.id.franchise_photo);
            fname = (TextView) itemView.findViewById(R.id.fname);
            subcate = (TextView) itemView.findViewById(R.id.subcategory);
            finv = (TextView) itemView.findViewById(R.id.frainv);
            status = (ImageView) itemView.findViewById(R.id.status);

            optionmenu = (ImageButton) itemView.findViewById(R.id.action_menu);
            optionmenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //creating a popup menu
                    PopupMenu popup = new PopupMenu(context, view);
                    //inflating menu from xml resource
                    popup.inflate(R.menu.context_menu);
                    //adding click listener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.action_edit:
                                      show();
                                    return true;
                                case R.id.action_delete:
                                             confirmDeleteFranchise();
                                    return true;
                            }
                            return false;
                        }
                    });
                    //displaying the popup
                    popup.show();

                }
            });

        }
        private void deletefranchise() {
            int pos = getAdapterPosition();

            if (pos != RecyclerView.NO_POSITION) {
                Datafranchise clickedDataItem = data.get(pos);
                String fname = clickedDataItem.franchisename;
               final String id = fname.replaceAll("\\s+","");
                
                class DeleteFranchise extends AsyncTask<Void, Void, String> {
                    ProgressDialog loading;

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        loading = ProgressDialog.show(context, "Updating...", "Wait...", false, false);
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                        loading.dismiss();
                        Toast.makeText(context, s, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    protected String doInBackground(Void... params) {
                        RegisterUserClass rh = new RegisterUserClass();
                        String s = rh.sendGetRequestParam(Config.DELETE_URL, id);
                        return s;
                    }
                }
                DeleteFranchise de = new DeleteFranchise();
                de.execute();
            }
        }

        private void confirmDeleteFranchise(){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setMessage("Are you sure you want to delete this Franchise?");

            alertDialogBuilder.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                                deletefranchise();
                        }
                    });

            alertDialogBuilder.setNegativeButton("No",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {

                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
        public void show() {
            int pos = getAdapterPosition();

            if (pos != RecyclerView.NO_POSITION) {
                Datafranchise clickedDataItem = data.get(pos);
                String f = clickedDataItem.franchisename;
                String fr = f.replaceAll("\\s+","");
                String s = clickedDataItem.subcategory;
                String finv = clickedDataItem.franchiseinv;
                Bundle bundle=new Bundle();
                bundle.putString("message", fr);
                bundle.putString("sub", s);
                bundle.putString("inv", finv);
                //set Fragmentclass Arguments
                editmyfranchise fragobj=new editmyfranchise();
                fragobj.setArguments(bundle);
                android.app.FragmentTransaction ft = ((Activity)context).getFragmentManager().beginTransaction();
                ft.replace(R.id.mainFrame, fragobj);
                ft.commit();

            }

        }
    }
}
