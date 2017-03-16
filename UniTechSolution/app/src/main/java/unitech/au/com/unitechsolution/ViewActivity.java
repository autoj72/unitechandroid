package unitech.au.com.unitechsolution;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.sql.SQLOutput;

public class ViewActivity extends AppCompatActivity {
    private UserVo savedVo = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        savedVo = (UserVo)(getIntent().getParcelableExtra("USER_VO"));
        //ID
        EditText tv_viewIdText = (EditText)findViewById(R.id.viewIdText);
        tv_viewIdText.setText(savedVo.getID());

        EditText tv_viewPassText = (EditText)findViewById(R.id.viewPassText);
        tv_viewPassText.setText(savedVo.getPASS());

        EditText tv_viewNameText = (EditText)findViewById(R.id.viewNameText);
        tv_viewNameText.setText(savedVo.getNAME());

        EditText tv_viewAddressText = (EditText)findViewById(R.id.viewAddressText);
        tv_viewAddressText.setText(savedVo.getADDRESS());

        EditText tv_viewDobText = (EditText)findViewById(R.id.viewDobText);
        tv_viewDobText.setText(savedVo.getDOB());

        EditText tv_viewMobileText = (EditText)findViewById(R.id.viewMobileText);
        tv_viewMobileText.setText(savedVo.getMOBILE());

        EditText tv_viewEMobileText = (EditText)findViewById(R.id.viewEMobileText);
        tv_viewEMobileText.setText(savedVo.getEMOBILE());
        TextView tv_viewGenderText = (TextView)findViewById(R.id.tvGender);

        ImageView giv = (ImageView)findViewById(R.id.ivGender);

        if("0".equalsIgnoreCase(savedVo.getGENDER()))
        {
            giv.setImageResource(R.drawable.male_48);
            tv_viewGenderText.setText("(Male)");
        }else{
            giv.setImageResource(R.drawable.female_48);
            tv_viewGenderText.setText("(Female)");
        }

        Button closeBtn = (Button)findViewById(R.id.backBtn);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              finish();

            }
        });

    }
}
