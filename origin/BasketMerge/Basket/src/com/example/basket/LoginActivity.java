package com.example.basket;

import java.util.LinkedList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {
	private LinkedList<UserAccount> mUserList;
	private EditText mUsernameEditText, mPasswordEditText;
	private TextView mCreateAccountTextView, mForgotAccountTextView;
	private Button mLoginButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		LoginActivity.this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_login);
		
		//
		mCreateAccountTextView = (TextView) this.findViewById(R.id.createAccount);
		
		mCreateAccountTextView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent signup = new Intent(LoginActivity.this,SignUpActivity.class);
				startActivity(signup);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
			}
		});
		//
		mForgotAccountTextView = (TextView)findViewById(R.id.forgotField);
		
		mForgotAccountTextView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent forgot = new Intent(LoginActivity.this, ForgotFieldActivity.class);
				startActivity(forgot);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
			}
		});
		//
		mLoginButton = (Button)findViewById(R.id.button1);
		mLoginButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(try_login()==true){
					Intent logon = new Intent(LoginActivity.this, HomePageActivity.class);
					startActivity(logon);
				}
				
			}
		});
		
	}
	protected boolean try_login() {
		return true;
	}

}
