package unitech.au.com.unitechsolution;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import uitech.au.com.unitechsolution.utils.CommonUtils;

public class MainActivity extends AppCompatActivity {
    public EditText dobEdit = null;
    public Button saveBtn = null;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private Activity thisActivity = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        thisActivity = this;
        setContentView(R.layout.activity_main);
        dobEdit = (EditText)findViewById(R.id.regDobText);
        saveBtn = (Button)findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ID
                EditText tv_regIdText = (EditText)findViewById(R.id.regIdText);
                //PASS
                EditText tv_regPassText = (EditText)findViewById(R.id.regPassText);
                //Name
                EditText tv_regNameText = (EditText)findViewById(R.id.regNameText);
                //Name
                EditText tv_regAddressText = (EditText)findViewById(R.id.regAddressText);
                //DOB
                EditText tv_regDobText = (EditText)findViewById(R.id.regDobText);
                //Mbile
                EditText tv_regMobileText = (EditText)findViewById(R.id.regMobileText);
                //EMobile
                EditText tv_regEMobileText = (EditText)findViewById(R.id.regEMobileText);
                //Radio
                RadioGroup mfRadio = (RadioGroup)findViewById(R.id.regMfRadio);
                int selectedId = mfRadio.getCheckedRadioButtonId();

                String id = tv_regIdText.getEditableText().toString();
                String pw = tv_regPassText.getEditableText().toString();
                String nm = tv_regNameText.getEditableText().toString();
                String ads = tv_regAddressText.getEditableText().toString();
                String dob = tv_regDobText.getEditableText().toString();
                String mn = tv_regMobileText.getEditableText().toString();
                String emn = tv_regEMobileText.getEditableText().toString();
                String mftext = null;
                if(R.id.regMRadio == selectedId)
                {
                    mftext = "0";
                }else if(R.id.regFRadio == selectedId){
                    mftext = "1";
                }

                if(id == null || "".equals(id.trim()) ||
                   pw == null || "".equals(pw.trim()) ||
                   nm == null || "".equals(nm.trim()) ||
                   ads == null || "".equals(ads.trim()) ||
                   dob == null || "".equals(dob.trim()) ||
                   mn == null ||  "".equals(mn.trim()) ||
                   emn == null || "".equals(emn.trim()))
                {
                    thisActivity.runOnUiThread(new Runnable() {
                        public void run() {
                            Dialog dialog = CommonUtils.showSimpleMessage(thisActivity, R.string.AlertDialog_FILL_MORE, "fonts/Roboto-Regular.ttf",null);
                            int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
                            int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.25);
                            dialog.getWindow().setLayout(width, height);
                            dialog.show();
                        }
                    });
                    return;
                }else {
                    boolean tf = true;
                    if (mn != null) {
                        String str = mn;
                        char[] charArray = str.toCharArray();
                        for (char a : charArray) {
                            try {
                                Integer.parseInt(String.valueOf(a));
                            } catch (Exception e) {
                                tf = false;
                                break;
                            }
                        }//for
                    }//if
                    if (emn != null) {
                        String str = emn;
                        char[] charArray = str.toCharArray();
                        for (char a : charArray) {
                            try {
                                Integer.parseInt(String.valueOf(a));
                            } catch (Exception e) {
                                tf = false;
                                break;
                            }
                        }//for
                    }//if

                    if (!tf) {
                        thisActivity.runOnUiThread(new Runnable() {
                            public void run() {
                                Dialog dialog = CommonUtils.showSimpleMessage(thisActivity, R.string.AlertDialog_FORMAT_ERROR, "fonts/Roboto-Regular.ttf", null);
                                int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
                                int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.25);
                                dialog.getWindow().setLayout(width, height);
                                dialog.show();
                            }
                        });
                    return;
                    }
                }
                StringBuffer vtext = new StringBuffer();

                vtext.append(id + "\n");
                vtext.append(pw + "\n");
                vtext.append(nm + "\n");
                vtext.append(ads + "\n");
                vtext.append(dob + "\n");
                vtext.append(mn + "\n");
                vtext.append(emn + "\n");
                vtext.append(mftext + "\n");

                UserVo uvo = new UserVo();
                uvo.setID(id);
                uvo.setPASS(pw);
                uvo.setNAME(nm);
                uvo.setADDRESS(ads);
                uvo.setMOBILE(mn);
                uvo.setEMOBILE(emn);
                uvo.setDOB(dob);
                uvo.setGENDER(mftext);
                addAndGetUser(uvo);
                saveBtnSetEnabledInThread(false,"Processing");
            }
        });
    }
    private void addAndGetUser(final UserVo _vo) {
        Thread dataLoadTread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    AsyncCallWS async = new AsyncCallWS(_vo);
                    Object resObj = async.execute().get(60000 * 5, TimeUnit.MILLISECONDS);
                    if(resObj != null)
                    {
                        try{
                            JSONObject resJson = (JSONObject)resObj;
                            String result = resJson.getString("RESULT").trim();
                            if("FAIL".equalsIgnoreCase(result))
                            {
                                final String reason = resJson.getString("REASON");
                                thisActivity.runOnUiThread(new Runnable() {
                                    public void run() {
                                        Dialog dialog = CommonUtils.showSimpleMessage(thisActivity, R.string.AlertDialog_ERROR, "fonts/Roboto-Regular.ttf",reason);
                                        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
                                        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.25);
                                        dialog.getWindow().setLayout(width, height);
                                        dialog.show();
                                    }
                                });
                            }else{
                                UserVo resultVo = new UserVo();
                                resultVo.setID(resJson.getString("ID"));
                                resultVo.setPASS(resJson.getString("PASS"));
                                resultVo.setNAME(resJson.getString("NAME"));
                                resultVo.setDOB(resJson.getString("DOB"));
                                resultVo.setADDRESS(resJson.getString("ADDRESS"));
                                resultVo.setGENDER(resJson.getString("GENDER"));
                                resultVo.setMOBILE(resJson.getString("CONTACT"));
                                resultVo.setEMOBILE(resJson.getString("ECONTACT"));
                                resultVo.setREG_DATE(resJson.getString("REGDATE"));
                                Intent view_intent = new Intent(MainActivity.this,ViewActivity.class);
                                view_intent.putExtra("USER_VO",(Parcelable)resultVo);
                                startActivity(view_intent);
                        }//if
                        saveBtnSetEnabledInThread(true,"Register");
                        }catch (Exception e){}
                    }//if
                }catch (Exception e){}
            }
        });
        dataLoadTread.start();
    }

    public void showTimePickerDialog(View v) {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        Log.i("App","YEAR =" + mYear);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        dobEdit.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();

    }


    public void saveBtnSetEnabledInThread(final boolean _tf,final String _str){
        saveBtn.post(new Runnable() {
            @Override
            public void run() {
                if (_str != null) saveBtn.setText(_str);
                saveBtn.setEnabled(_tf);
            }
        });
    }
}
