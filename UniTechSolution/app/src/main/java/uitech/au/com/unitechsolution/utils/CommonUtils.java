package uitech.au.com.unitechsolution.utils;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import unitech.au.com.unitechsolution.R;

/**
 * Created by autoj on 2017-03-16.
 */
public class CommonUtils {
    private static AppCompatActivity parent;
    public CommonUtils(AppCompatActivity _parent) {
        this.parent = _parent;
    }
    public static Dialog showSimpleMessage(Activity _activity, int _messageNo, String _fontface,String _addmore) {
        final Dialog dialog = new Dialog(_activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.message_dialog);
        /*Setting Specific Font*/
        Typeface typeface = Typeface.createFromAsset(_activity.getAssets(), _fontface);
        LinearLayout ld = (LinearLayout) dialog.findViewById(R.id.alertDialogView);
        TextView text = (TextView) ld.findViewById(R.id.alertTextView);
        text.setText(_messageNo);
        if(_addmore != null) {
            text.setText(text.getText() + _addmore);
        }
        text.setTypeface(typeface);
        TextView aok = (TextView) ld.findViewById(R.id.alertTextViewOk);
        aok.setTypeface(typeface);
        aok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        return dialog;
    }
}
