package gmedia.net.id.absenigmedia;

/**
 * Created by Bayu on 29/12/2017.
 */

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.Toast;

public class SessionManager {
	// Shared Preferences
	private SharedPreferences pref;

	// Editor for Shared preferences
	private Editor editor;

	// Context
	private Context _context;

	// Shared pref mode

	private int PRIVATE_MODE = 0;

	// Sharedpref file name
	private static final String PREF_NAME = "AndroidHivePref";
	private static final String CHECK_NAME = "check";

	// All Shared Preferences Keys
	private static final String IS_LOGIN = "IsLoggedIn";
	private static final String IS_REGIONAL = "Regional";

	// User name (make variable public to access from outside)
	public static final String KEY_NAME = "name";
	public static final String KEY_TOKEN = "token";
	public static final String KEY_NIP = "Nip";
	public static final String KEY_APPROVAL = "approval";
	public static final String KEY_REGIONAL = "0";
	public static final String KEY_PIN = "0";

	// Email address (make variable public to access from outside)
	public static final String KEY_EMAIL = "email";
	public static final String KEY_CHECK = "check";

	// Constructor
	public SessionManager(Context context) {
		this._context = context;
		pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
		editor = pref.edit();
	}

	/**
	 * Create login session
	 */
	public void createLoginSession(String name, String email, String token, String Nip, String approval) {
		// Storing login value as TRUE
		editor.putBoolean(IS_LOGIN, true);
		// Storing name in pref
		editor.putString(KEY_NAME, name);
		// Storing email in pref
		editor.putString(KEY_EMAIL, email);
		// Storing token in pref
		editor.putString(KEY_TOKEN, token);
		editor.putString(KEY_NIP, Nip);
		editor.putString(KEY_APPROVAL, approval);
		// commit changes
		editor.commit();
	}

	/*public void createPIN(String pin) {
		editor.putString(KEY_PIN, pin);
		editor.commit();
	}*/

	public void createRegional(String regional) {
		editor.putBoolean(IS_REGIONAL, true);
		editor.putString(KEY_REGIONAL, regional);
		editor.commit();
	}


	/**
	 * Check login method wil check user login status
	 * If false it will redirect user to login page
	 * Else won't do anything
	 */
	public void checkLogin() {
		// Check login status
		if (this.isLoggedIn()) {
			// user is not logged in redirect him to Login Activity
			Intent i = new Intent(_context, MainActivity.class);
			// Closing all the Activities
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

			// Add new Flag to start new Activity
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

			// Staring Login Activity
			_context.startActivity(i);
		}

	}

	public void checkRegional() {
		if (this.isRegional()) {

		}
	}

	/**
	 * Get stored session data
	 */

	public String getToken() {
		return pref.getString(KEY_TOKEN, "");
	}

	public String getNip() {
		return pref.getString(KEY_NIP, "");
	}

	public String getApproval() {
		return pref.getString(KEY_APPROVAL, "");
	}

	public String getRegional() {
		return pref.getString(KEY_REGIONAL, "0");
	}

//	public String getPIN() {
//		return pref.getString(KEY_PIN, "0");
//	}

	/**
	 * Clear session details
	 */
	public void logoutUser() {
		// Clearing all data from Shared Preferences
//		editor.clear();
		editor.putBoolean(IS_LOGIN, false);
		// Storing name in pref
		editor.putString(KEY_NAME, "");
		// Storing email in pref
		editor.putString(KEY_EMAIL, "");
		// Storing token in pref
		editor.putString(KEY_TOKEN, "");
		editor.putString(KEY_NIP, "");
		editor.putString(KEY_APPROVAL, "");
//		editor.putString(KEY_PIN, "");
		editor.commit();

		// After logout redirect user to Loing Activity
		Intent i = new Intent(_context, Login.class);
		// Closing all the Activities
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		// Add new Flag to start new Activity
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		// Staring Login Activity
		_context.startActivity(i);
	}

	public void deleteRegional() {
		editor.putBoolean(IS_REGIONAL, false);
		editor.putString(KEY_REGIONAL, "0");
		editor.commit();
	}

	/**
	 * Quick check for login
	 **/
	// Get Login State
	public boolean isLoggedIn() {
		return pref.getBoolean(IS_LOGIN, false);
	}

	public boolean isRegional() {
		return pref.getBoolean(IS_REGIONAL, false);
	}
}