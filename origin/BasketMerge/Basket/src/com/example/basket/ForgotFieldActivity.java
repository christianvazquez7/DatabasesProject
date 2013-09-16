package com.example.basket;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgotFieldActivity extends Activity {
	private Button mSendEmailButton;
	private EditText mEmailInput;
	private String mUserEmail;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgot_field);
		overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
		mEmailInput = (EditText) findViewById(R.id.forgotFieldEmailEditText);

		mSendEmailButton = (Button) findViewById(R.id.forgotFieldButton);
		mSendEmailButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mUserEmail = mEmailInput.getText().toString();

				if(mUserEmail!="" && mUserEmail!=null){
					if(!mUserEmail.contains(".com") || !mUserEmail.contains("@")){
						Toast.makeText(ForgotFieldActivity.this, "Wrong input", Toast.LENGTH_SHORT).show();
					}
					else
						sendAccountDetailsEmail(mUserEmail);
				}
				else{
					Toast.makeText(ForgotFieldActivity.this, "Wrong input", Toast.LENGTH_SHORT).show();
				}
			}
		});

	}

	private void sendAccountDetailsEmail(String userEmail){
		//action to send email
		Toast.makeText(ForgotFieldActivity.this, "Email with information sent", Toast.LENGTH_LONG).show();
	}

}
