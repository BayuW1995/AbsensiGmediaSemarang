package gmedia.net.id.absenigmedia;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import gmedia.net.id.absenigmedia.Volley.ApiVolley;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {
	public ImageView logo;
	EditText NIK, password;
	public Button login;
	public CheckBox remeberme;
	private boolean doubleBackToExitPressedOnce = false;
	SessionManager session;
	public static String level = "";
	private String[] regional = new String[]
			{
					"Pilih Regional",
					"Regional I",
					"Regional II",
					"Regional III",
			};
	private Spinner menuRegional;
	public static String posisiMenuRegional;
	private ImageView btnResetRegional;

	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		session = new SessionManager(getApplicationContext());
		MainActivity.posisi = true;
		session.checkLogin();
		if (android.os.Build.VERSION.SDK_INT >= 21) {
			Window window = this.getWindow();
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			window.setStatusBarColor(this.getResources().getColor(R.color.statusbar));
		}
		NIK = (EditText) findViewById(R.id.nik);
		password = (EditText) findViewById(R.id.password);
		login = (Button) findViewById(R.id.login);
		menuRegional = (Spinner) findViewById(R.id.menuRegional);
		/*btnResetRegional = (ImageView) findViewById(R.id.imageLogin);
		btnResetRegional.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				session.deleteRegional();
				Intent intent = new Intent(Login.this, Login.class);
				startActivity(intent);
				finish();
				Toast.makeText(Login.this, "posisi spiner: " + posisiMenuRegional, Toast.LENGTH_LONG).show();
			}
		});*/
//        remeberme = (CheckBox) findViewById(R.id.rememberme);
		ArrayAdapter<String> spinnerAdapterBulan = new ArrayAdapter<String>(this, R.layout.spinner_items, regional) {
			@Override
			public boolean isEnabled(int position) {
				if (position == 0) {
					// Disable the first item from Spinner
					// First item will be use for hint
					menuRegional.setAlpha(0.5f);
					return false;
				} else {
					menuRegional.setAlpha(1f);
					return true;
				}
			}

			@Override
			public View getDropDownView(int position, View convertView,
										ViewGroup parent) {
				View view = super.getDropDownView(position, convertView, parent);
				TextView tv = (TextView) view;
				if (position == 0) {
					// Set the hint text color gray
					tv.setTextColor(Color.BLACK);
					tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,
							getResources().getDimension(R.dimen.textLogin));
				} else {
					tv.setTextColor(Color.BLACK);
					tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,
							getResources().getDimension(R.dimen.textLogin));
				}
				return view;
			}
		};
		spinnerAdapterBulan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		menuRegional.setAdapter(spinnerAdapterBulan);
		posisiMenuRegional = session.getRegional();
		menuRegional.setSelection(Integer.parseInt(posisiMenuRegional));
		menuRegional.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				posisiMenuRegional = String.valueOf(menuRegional.getSelectedItemPosition());
//				posisiMenuRegional = menuRegional.getSelectedItem().toString();
//				Log.d("spinner", posisiMenuRegional);
				((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#FFFFFF"));
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
		LinearLayout layoutLogin = findViewById(R.id.layoutLogin);
		layoutLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				HideKeyboard.hideSoftKeyboard(Login.this);
			}
		});
		login.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String username = NIK.getText().toString();
				String pass = password.getText().toString();
				if (username.trim().length() > 0 && pass.trim().length() > 0) {
					loginRequest();
					/*if (posisiMenuRegional.equals("0")) {
						Toast.makeText(Login.this, "Silahkan Pilih Regional Kantor Anda", Toast.LENGTH_LONG).show();
					} else {
					}*/
				} else {
//                    alert.showAlertDialog(Login.this, "Login failed..", "Please enter username and password", false);
					final Dialog dialog = new Dialog(Login.this);
					dialog.setContentView(R.layout.popupenterusernamepassword);
					RelativeLayout close = dialog.findViewById(R.id.closeenterusernamepassword);
					close.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							dialog.dismiss();
						}
					});
					dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
					dialog.show();
					final Handler handler = new Handler();
					final Runnable runnable = new Runnable() {
						@Override
						public void run() {
							if (dialog.isShowing()) {
								dialog.dismiss();
							}
						}
					};
					dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
						@Override
						public void onDismiss(DialogInterface dialog) {
							handler.removeCallbacks(runnable);
						}
					});
					handler.postDelayed(runnable, 3000);
				}

			}
		});
	}

	private void loginRequest() {
		final Dialog dialogLoading = new Dialog(Login.this);
		dialogLoading.setContentView(R.layout.loading);
		dialogLoading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		dialogLoading.setCanceledOnTouchOutside(false);
		dialogLoading.show();
		final JSONObject jBody = new JSONObject();
		try {
			jBody.put("username", NIK.getText().toString());
			jBody.put("password", password.getText().toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}

		ApiVolley request = new ApiVolley(Login.this, jBody, "POST", URL.urlLogin, "", "", 0, new ApiVolley.VolleyCallback() {
			@Override
			public void onSuccess(String result) {
				dialogLoading.dismiss();
				try {
					JSONObject object = new JSONObject(result);
					final String status = object.getJSONObject("metadata").getString("status");
					if (status.equals("1")) {
						String token = object.getJSONObject("response").getString("token");
						String nip = object.getJSONObject("response").getString("nip");
						level = object.getJSONObject("response").getString("approval");
						session.createLoginSession("gmedia", "gmedia@gmail.com", token, nip, level);
						session.createRegional(posisiMenuRegional);
						Intent intent = new Intent(Login.this, MainActivity.class);
						startActivity(intent);
						finish();
						dialogLoading.dismiss();
					} else {
						dialogLoading.dismiss();
						final Dialog dialog = new Dialog(Login.this);
						dialog.setContentView(R.layout.popupincorectusernamepassword);
						RelativeLayout close = dialog.findViewById(R.id.closeincorectusernamepassword);
						close.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								dialog.dismiss();
							}
						});
						dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
						dialog.show();
						final Handler handler = new Handler();
						final Runnable runnable = new Runnable() {
							@Override
							public void run() {
								if (dialog.isShowing()) {
									dialog.dismiss();
								}
							}
						};

						dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
							@Override
							public void onDismiss(DialogInterface dialog) {
								handler.removeCallbacks(runnable);
							}
						});

						handler.postDelayed(runnable, 3000);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onError(String result) {
				dialogLoading.dismiss();
				Toast.makeText(getApplicationContext(), "Terjadi Kesalahan", Toast.LENGTH_LONG).show();
			}
		});
	}

	@Override
	public void onBackPressed() {
		if (doubleBackToExitPressedOnce) {
			Intent startMain = new Intent(Intent.ACTION_MAIN);
			startMain.addCategory(Intent.CATEGORY_HOME);
			startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(startMain);
		}

		this.doubleBackToExitPressedOnce = true;
		Toast.makeText(this, "Klik sekali lagi untuk keluar", Toast.LENGTH_SHORT).show();

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				doubleBackToExitPressedOnce = false;
			}
		}, 2000);
	}

}
 /*//setting layout text "welcome"
        int Ywelcome = (20*height)/100;
        RelativeLayout.LayoutParams welcome = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        welcome.addRule(RelativeLayout.BELOW,logo.getId());
        welcome.addRule(RelativeLayout.CENTER_HORIZONTAL);
//        welcome.setMargins(0,250,0,0);
        textWelcome.setLayoutParams(welcome);
        textWelcome.setText("Welcome!");
        textWelcome.setY(Ywelcome);*/

        /*/setting layout text 1

        RelativeLayout.LayoutParams logo1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        logo1.addRule(RelativeLayout.BELOW,textWelcome.getId());
        logo1.addRule(RelativeLayout.CENTER_HORIZONTAL);
        textLogo1.setLayoutParams(logo1);
        textLogo1.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit,");

        //setting layout text 2

        RelativeLayout.LayoutParams logo2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        logo2.addRule(RelativeLayout.BELOW,textLogo1.getId());
        logo2.addRule(RelativeLayout.CENTER_HORIZONTAL);
        textLogo2.setLayoutParams(logo2);
        textLogo2.setText("sed do euismod tempor incididunt ut labore et dolore");

        //setting layout text 3

        RelativeLayout.LayoutParams logo3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        logo3.addRule(RelativeLayout.BELOW,textLogo2.getId());
        logo3.addRule(RelativeLayout.CENTER_HORIZONTAL);
        textLogo3.setLayoutParams(logo3);
        textLogo3.setText("magna aliqua");*/
        /*logo = (ImageView) findViewById(R.id.logo);
        NIK = (EditText) findViewById(R.id.nik);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        remeberme = (CheckBox) findViewById(R.id.rememberme);
        forgot = (TextView) findViewById(R.id.forgot);
        RelativeLayout layoutLogin = findViewById(R.id.layoutLogin);
        layoutLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HideKeyboard.hideSoftKeyboard(Login.this);
            }
        });
        *//*NIK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NIK.requestFocusFromTouch();
            }
        });*//**//*
        //get width, height image logo

        Drawable b = getResources().getDrawable(R.drawable.logo_baru);
        int w = b.getIntrinsicWidth();
        int h = b.getIntrinsicHeight();
        int screensize = getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;
        switch (screensize) {
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                int widthLogoBaru = (24 * w) / 100;
                int heightLogoBaru = (24 * h) / 100;
                RelativeLayout.LayoutParams gbrLogo = new RelativeLayout.LayoutParams(widthLogoBaru, heightLogoBaru);
                logo.setLayoutParams(gbrLogo);
                logo.setImageResource(R.drawable.logo_baru);
                gbrLogo.addRule(RelativeLayout.CENTER_HORIZONTAL);
                gbrLogo.setMargins(0, 150, 0, 0);
                break;
            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                int widthLogoBaruLarge = (50 * w) / 100;
                int heightLogoBaruLarge = (50 * h) / 100;
                RelativeLayout.LayoutParams gbrLogoLarge = new RelativeLayout.LayoutParams(widthLogoBaruLarge, heightLogoBaruLarge);
                logo.setLayoutParams(gbrLogoLarge);
                logo.setImageResource(R.drawable.logo_baru);
                gbrLogoLarge.addRule(RelativeLayout.CENTER_HORIZONTAL);
                gbrLogoLarge.setMargins(0, 150, 0, 0);
                break;
        }


        //get width, height display

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        //setting layout image logo


        int widthLogo = ((15 * width) / 100);
        int heightLogo = ((5 * height) / 100);


        //setting edit text nik

        int widthEditText = (75 * width) / 100;
        int heightEditText = (7 * height) / 100;
        RelativeLayout.LayoutParams editNIK = new RelativeLayout.LayoutParams(widthEditText, heightEditText);
        NIK.setLayoutParams(editNIK);
        editNIK.addRule(RelativeLayout.ABOVE, password.getId());
        editNIK.addRule(RelativeLayout.CENTER_HORIZONTAL);
        editNIK.setMargins(0, 300, 0, 0);

        //setting edit text password

        RelativeLayout.LayoutParams editpassword = new RelativeLayout.LayoutParams(widthEditText, heightEditText);
        password.setLayoutParams(editpassword);
        editpassword.addRule(RelativeLayout.ABOVE, remeberme.getId());
        editpassword.addRule(RelativeLayout.CENTER_HORIZONTAL);
        editpassword.setMargins(0, 35, 0, 0);

        //setting button login

        int widthButton = (50 * width) / 100;
        int heightButton = heightEditText;
        RelativeLayout.LayoutParams buttonlogin = new RelativeLayout.LayoutParams(widthButton, heightButton);
        buttonlogin.addRule(RelativeLayout.ABOVE, forgot.getId());
        buttonlogin.addRule(RelativeLayout.CENTER_HORIZONTAL);
        buttonlogin.setMargins(0, 0, 0, 10);*/
