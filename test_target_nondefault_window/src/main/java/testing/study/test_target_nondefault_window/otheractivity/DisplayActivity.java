package testing.study.test_target_nondefault_window.otheractivity;


import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import testing.study.test_target_nondefault_window.R;
import testing.study.test_target_nondefault_window.SendActivity;

/**
 * Simple activity used to display data received from another activity.
 */
public class DisplayActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_activity);
        TextView textView = (TextView) findViewById(R.id.display_data);
        textView.setText(getIntent().getStringExtra(SendActivity.EXTRA_DATA));
    }
}