package gmedia.net.id.absenigmedia;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.DatePicker;

public class Calendar extends AppCompatActivity {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(gmedia.net.id.absenigmedia.R.layout.calendar);
        final DatePicker datePicker = findViewById(gmedia.net.id.absenigmedia.R.id.calendarView);
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        datePicker.init(calendar.get(java.util.Calendar.YEAR), calendar.get(java.util.Calendar.MONTH), calendar.get(java.util.Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {
                int a = dayOfMonth;
                int b = month + 1;
                int c = year;
                String dateMulai = "" + a + "-" + b + "-" + c;
                String dateSelesai = "" + a + "-" + b + "-" + c;
                Bundle bundle = getIntent().getExtras();
                if (bundle != null) {
                    String flag = bundle.getString("flag");
                    String tombol = bundle.getString("tombol");
                    if (flag.equals("cuti")) {
                        if (tombol.equals("dari")) {
                            Cuti.setDate(dateMulai);
                            finish();
                        } else if (tombol.equals("sampai")) {
                            Cuti.setDate2(dateSelesai);
                            finish();
                        }
                    } else if (flag.equals("absen")) {
                        if (tombol.equals("dari")) {
                            RekapitulasiAbsensi.setDate(dateMulai);
                            finish();
                        } else if (tombol.equals("sampai")) {
                            RekapitulasiAbsensi.setDate2(dateSelesai);
                            finish();
                        }
                    }
                }
            }
        });
    }
}
/*datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                int a = dayOfMonth;
                int b = monthOfYear + 1;
                int c = year;
                String dateMulai = "" + a + "-" + b + "-" + c;
                String dateSelesai = "" + a + "-" + b + "-" + c;
                Bundle bundle = getIntent().getExtras();
                if (bundle != null) {
                    String flag = bundle.getString("flag");
                    String tombol = bundle.getString("tombol");
                    if (flag.equals("cuti")) {
                        if (tombol.equals("dari")) {
                            Cuti.setDate(dateMulai);
                            finish();
                        } else if (tombol.equals("sampai")) {
                            Cuti.setDate2(dateSelesai);
                            finish();
                        }
                    } else if (flag.equals("absen")) {
                        if (tombol.equals("dari")) {
                            RekapitulasiAbsensi.setDate(dateMulai);
                            finish();
                        } else if (tombol.equals("sampai")) {
                            RekapitulasiAbsensi.setDate2(dateSelesai);
                            finish();
                        }
                    }
                }
            }
        });*/
/*final CalendarView calendar = (CalendarView) findViewById(R.id.calendarView);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                *//*SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String selectedDate = sdf.format(new Date(calendar.getDate()));
                Cuti.setDate(selectedDate);*//*
                int a = dayOfMonth;
                int b = month + 1;
                int c = year;
                String dateMulai = "" + a + "-" + b + "-" + c;
                String dateSelesai = "" + a + "-" + b + "-" + c;
                Bundle bundle = getIntent().getExtras();
                if (bundle != null) {
                    String flag = bundle.getString("flag");
                    String tombol = bundle.getString("tombol");
                    if (flag.equals("cuti")) {
                        if (tombol.equals("dari")) {
                            Cuti.setDate(dateMulai);
                            finish();
                        } else if (tombol.equals("sampai")) {
                            Cuti.setDate2(dateSelesai);
                            finish();
                        }
                    } else if (flag.equals("absen")) {
                        if (tombol.equals("dari")) {
                            RekapitulasiAbsensi.setDate(dateMulai);
                            finish();
                        } else if (tombol.equals("sampai")) {
                            RekapitulasiAbsensi.setDate2(dateSelesai);
                            finish();
                        }
                    }
                }
            }
        });*/