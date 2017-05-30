package info.kamlesh.Franzfactor;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.Collections;
import java.util.List;

import static android.R.string.ok;

/**
 * Created by vijay on 5/8/2017.
 */
public class Adapterproposalfranchise extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private RecyclerView mRVfranchiselist;
    List<Dataproposal> data = Collections.emptyList();
    Dataproposal current;
    int currentPos = 0;
    private Adapterfranchiselist mAdapter;


    public Adapterproposalfranchise(Context context, List<Dataproposal> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_view_proposal_franchise, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;
        Dataproposal current = data.get(position);

        myHolder.franchisename.setText(current.franchise_name);
        myHolder.fname.setText(current.firstname);
        myHolder.lname.setText(current.lastname);
        myHolder.email.setText(current.emaill);
        myHolder.city.setText(current.city_);
        myHolder.state.setText(current.state_);
        myHolder.pincode.setText(current.pincode_);
        myHolder.contact.setText(current.contact_);
        myHolder.flocation.setText(current.flocation_);
        myHolder.budget.setText(current.budget_);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        TextView franchisename, fname, lname, email, city, state, pincode, contact, flocation, budget;
        ImageButton optionmenu;
        Button btncall;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            franchisename = (TextView) itemView.findViewById(R.id.franchisename);
            fname = (TextView) itemView.findViewById(R.id.fname);
            lname = (TextView) itemView.findViewById(R.id.lname);
            email = (TextView) itemView.findViewById(R.id.email);
            city = (TextView) itemView.findViewById(R.id.city);
            state = (TextView) itemView.findViewById(R.id.state);
            pincode = (TextView) itemView.findViewById(R.id.pincode);
            contact = (TextView) itemView.findViewById(R.id.contact);
            flocation = (TextView) itemView.findViewById(R.id.flocation);
            budget = (TextView) itemView.findViewById(R.id.budget);
            btncall = (Button) itemView.findViewById(R.id.btn_call);

            btncall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    int pos = getAdapterPosition();

                    Dataproposal dataproposal = data.get(pos);

                    final String cl = dataproposal.contact_;

                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + cl));
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    context.startActivity(callIntent);

                }
                    });
        }
            }



            }


