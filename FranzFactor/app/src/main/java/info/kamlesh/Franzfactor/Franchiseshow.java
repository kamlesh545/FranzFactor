package info.kamlesh.Franzfactor;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Franchiseshow extends AppCompatActivity {
    ImageView imageView;
    TextView fnn,scc,fdes,finv;
    Button apply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_franchiseshow);

        imageView = (ImageView) findViewById(R.id.img);
        fnn = (TextView) findViewById(R.id.fn);
        scc = (TextView) findViewById(R.id.sc);
        fdes = (TextView) findViewById(R.id.franchise_desc);
        finv = (TextView) findViewById(R.id.frainv);
        apply = (Button) findViewById(R.id.btn_apply);

        Bundle extras = getIntent().getExtras();
        Bitmap bmp = (Bitmap) extras.getParcelable("imagebitmap");
       final String s = (String) extras.get("fname");
       String ss = (String) extras.get("subc");
        String fdesc = (String) extras.get("fdesc");
        String fin = (String) extras.get("finv");
        imageView.setImageBitmap(bmp );
        fnn.setText(s);
        scc.setText(ss);
        fdes.setText(fdesc);
        finv.setText(fin);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(Franchiseshow.this,Apply.class);

                Bundle extras = new Bundle();
                extras.putString("fname", s);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
    }
}
