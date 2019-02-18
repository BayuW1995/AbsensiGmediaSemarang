package gmedia.net.id.absenigmedia.Volley;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import gmedia.net.id.absenigmedia.SessionManager;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

/**
 * Created by Shin on 2/24/2017.
 */

public class ApiVolley {
    public static RequestQueue requestQueue;
    private SessionManager session;

    public ApiVolley(final Context context, JSONObject jsonBody, String requestMethod, String REST_URL, final String successDialog, final String failDialog, final int showDialogFlag, final VolleyCallback callback) {

        /*
        context : Application context
        jsonBody : jsonBody (usually be used for POST and PUT)
        requestMethod : GET, POST, PUT, DELETE
        REST_URL : Rest API URL
        successDialog : custom Dialog when success call API
        failDialog : custom Dialog when failed call API
        showDialogFlag : 1 = show successDialog / failDialog with filter
        callback : return of the response
        */
        session = new SessionManager(context);
        final String requestBody = jsonBody.toString();

        int method = 0;

        switch (requestMethod.toUpperCase()) {

            case "GET":
                method = Request.Method.GET;
                break;
            case "POST":
                method = Request.Method.POST;
                break;
            case "PUT":
                method = Request.Method.PUT;
                break;
            case "DELETE":
                method = Request.Method.DELETE;
                break;
            default:
                method = Request.Method.GET;
                break;
        }

        //region initial of stringRequest
        StringRequest stringRequest = new StringRequest(method, REST_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                // Important Note : need to use try catch when parsing JSONObject, no need when parsing string
                try {
                    JSONObject responseAPI = new JSONObject(response);
                    String status = responseAPI.getJSONObject("metadata").getString("status");
                    String message = responseAPI.getJSONObject("metadata").getString("message");
                    responseAPI = null;

                    if (status != null) {
                        /*if (status.equals("0") && message.equals("Unauthorized")) {
                            Toast.makeText(context,"Session Expired",Toast.LENGTH_LONG).show();
                            session.logoutUser();
                        }*/
                        callback.onSuccess(response);
                    }  else {
                        Toast.makeText(context, "Terjadi kesalahan saat memuat data", Toast.LENGTH_LONG).show();
                    }
                    ShowCustomDialog(context, showDialogFlag, successDialog);
                } catch (Exception e) {

                    e.printStackTrace();
//                     Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
                    if (showDialogFlag == 3) {
                        callback.onSuccess(response);
                    } else {
                        Toast.makeText(context, "Terjadi kesalahan saat memuat data", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError(error.toString());
                ShowCustomDialog(context, showDialogFlag, failDialog);
                return;
            }
        }) {

            // Request Header
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Consumer-id", "absensi");
                headers.put("Secret-key", "986bf02c97e4738cff389ec0b3d784bc");
                headers.put("Nip", session.getNip());
                headers.put("Token", session.getToken());
                return headers;
            }

            @Override
            public String getBodyContentType() {
                return String.format("application/json; charset=utf-8");
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                            requestBody, "utf-8");
                    return null;
                }
            }
        };
        //endregion
        trustAllCertivicate();
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }

        // retry when timeout
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                8*1000, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
        requestQueue.getCache().clear();

    }

    // interface for call callback from response API
    public interface VolleyCallback {
        void onSuccess(String result);

        void onError(String result);
    }

    public void ShowCustomDialog(Context context, int flag, String message) {
        if (flag == 1) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }
    }
    private void trustAllCertivicate() {

        try {
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier(){
                public boolean verify(String hostname, SSLSession session) {
                    if (hostname.equalsIgnoreCase("erpsmg.gmedia.id") ||
                            hostname.equalsIgnoreCase("api.ipify.org") ||
                            hostname.equalsIgnoreCase("api.crashlytics.com") ||
                            hostname.equalsIgnoreCase("settings.crashlytics.com") ||
                            hostname.equalsIgnoreCase("clients4.google.com") ||
                            hostname.equalsIgnoreCase("www.facebook.com") ||
                            hostname.equalsIgnoreCase("www.instagram.com") ||
                            hostname.equalsIgnoreCase("lh1.googleusercontent.com") ||
                            hostname.equalsIgnoreCase("lh2.googleusercontent.com") ||
                            hostname.equalsIgnoreCase("lh3.googleusercontent.com") ||
                            hostname.equalsIgnoreCase("lh4.googleusercontent.com") ||
                            hostname.equalsIgnoreCase("lh5.googleusercontent.com") ||
                            hostname.equalsIgnoreCase("lh6.googleusercontent.com") ||
                            hostname.equalsIgnoreCase("lh7.googleusercontent.com") ||
                            hostname.equalsIgnoreCase("lh8.googleusercontent.com") ||
                            hostname.equalsIgnoreCase("lh9.googleusercontent.com") ||
                            hostname.equalsIgnoreCase("googleusercontent.com") ||
                            hostname.equalsIgnoreCase("scontent.xx.fbcdn.net") ||
                            hostname.equalsIgnoreCase("lookaside.facebook.com"))
                    {
                        return true;
                    } else {
                        return false;
                    }
                }});
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new X509TrustManager[]{new X509TrustManager(){
                public void checkClientTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {}
                public void checkServerTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {}
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }}}, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(
                    context.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
