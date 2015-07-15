package me.danielpan.youtubelike;

import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import me.danielpan.youtubelike.abs.AbsActivity;
import me.danielpan.youtubelike.util.RegexUtil;

public class LoginActivity extends AbsActivity implements View.OnClickListener {
    private TextInputLayout tilEmail;
    private TextInputLayout tilPwd;
    private AppCompatEditText acetEmail;
    private AppCompatEditText acetPwd;
    private Button btnLogin;

    @Override
    protected void inflateContentView() {
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void findViewsById() {
        tilEmail = (TextInputLayout) findViewById(R.id.text_input_layout_email);
        tilPwd = (TextInputLayout) findViewById(R.id.text_input_layout_password);
        acetEmail = (AppCompatEditText) findViewById(R.id.app_compat_edit_text_email);
        acetPwd = (AppCompatEditText) findViewById(R.id.app_compat_edit_text_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
    }

    @Override
    protected void initWidgets() {
        btnLogin.setOnClickListener(this);
        acetEmail.setText(getPref().getString("username", null));
        acetPwd.setText(getPref().getString("password", null));
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                String username = acetEmail.getText().toString().trim();
                if (TextUtils.isEmpty(username)) {
                    tilEmail.setError("Content can't be EMPTY");
                    return;
                }
                if (!(RegexUtil.isEmailValid(username) || RegexUtil.isMobileNumValid(username))) {
                    tilEmail.setError("Email or Phone are not VALID");
                    return;
                }
                String pwd = acetPwd.getText().toString().trim();
                if (TextUtils.isEmpty(pwd)) {
                    tilPwd.setError("Password can't be EMPTY");
                    return;
                }
                if (!RegexUtil.isPasswordValid(pwd)) {
                    tilEmail.setError("Password is not VALID");
                    return;
                }
                getEditor().putString("username", username).putString("password", pwd).commit();
                startActivity(HomeActivity.class);
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
