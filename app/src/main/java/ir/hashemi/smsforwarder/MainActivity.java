package ir.hashemi.smsforwarder;
import android.Manifest;import android.app.*;import android.content.*;import android.os.*;import android.widget.*;import java.text.*;import java.util.*;
public class MainActivity extends Activity{SharedPreferences p;EditText source,trigger,d1,d2,d3;TextView status,today,last;Switch enabled;static final int REQ=55;
public void onCreate(Bundle b){super.onCreate(b);setContentView(R.layout.activity_main);p=getSharedPreferences("cfg",0);source=findViewById(R.id.sourceEdit);trigger=findViewById(R.id.triggerEdit);d1=findViewById(R.id.dest1Edit);d2=findViewById(R.id.dest2Edit);d3=findViewById(R.id.dest3Edit);status=findViewById(R.id.statusText);today=findViewById(R.id.todayText);last=findViewById(R.id.lastText);enabled=findViewById(R.id.enabledSwitch);load();findViewById(R.id.saveButton).setOnClickListener(v->save());enabled.setOnCheckedChangeListener((v,on)->{if(v.isPressed()){p.edit().putBoolean("enabled",on).apply();refresh();}});findViewById(R.id.menuButton).setOnClickListener(v->menu());requestPermissions();}
void load(){source.setText(p.getString("source",""));trigger.setText(p.getString("trigger",""));d1.setText(p.getString("d1",""));d2.setText(p.getString("d2",""));d3.setText(p.getString("d3",""));enabled.setChecked(p.getBoolean("enabled",false));refresh();}
void save(){
    String s=source.getText().toString().trim(),
           t=trigger.getText().toString().trim(),
           x=d1.getText().toString().trim();

    if(t.isEmpty()||x.isEmpty()){
        Toast.makeText(this,"Trigger Text and Destination 1 are required.",1).show();
        return;
    }

    p.edit()
     .putString("source",s)
     .putString("trigger",t)
     .putString("d1",x)
     .putString("d2",d2.getText().toString().trim())
     .putString("d3",d3.getText().toString().trim())
     .putBoolean("enabled",true)
     .apply();

    refresh();
    Toast.makeText(this,"Forwarding activated",0).show();
}
void refresh(){boolean on=p.getBoolean("enabled",false);status.setText(on?"ACTIVE — forwarding is enabled":"INACTIVE");enabled.setChecked(on);String day=new SimpleDateFormat("yyyy-MM-dd",Locale.US).format(new Date());today.setText("Today: "+p.getInt("day_"+day+"_ok",0));last.setText("Last forwarding: "+p.getString("last","None"));if(on)NotificationHelper.show(this,"Forwarding is active");else NotificationHelper.hide(this);}
void requestPermissions(){if(Build.VERSION.SDK_INT>=23)requestPermissions(new String[]{Manifest.permission.RECEIVE_SMS,Manifest.permission.SEND_SMS},REQ);if(Build.VERSION.SDK_INT>=33)requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS},REQ+1);}
void menu(){String[] a={"Daily Report","Monthly Report","History","Settings","About"};new AlertDialog.Builder(this).setTitle("Menu").setItems(a,(d,w)->{if(w==0)report(false);else if(w==1)report(true);else if(w==2)history();else if(w==3)settings();else about();}).show();}
void report(boolean m){String k=new SimpleDateFormat(m?"yyyy-MM":"yyyy-MM-dd",Locale.US).format(new Date()),pre=m?"month_":"day_";int ok=p.getInt(pre+k+"_ok",0),f=p.getInt(pre+k+"_fail",0);new AlertDialog.Builder(this).setTitle(m?"Monthly Report":"Daily Report").setMessage("Successful forwards: "+ok+"\nFailed forwards: "+f).setPositiveButton("OK",null).show();}
void history(){new AlertDialog.Builder(this).setTitle("History").setMessage(p.getString("history","No forwarding history yet.")).setPositiveButton("OK",null).show();}
void settings(){new AlertDialog.Builder(this).setTitle("Settings").setMessage("Configuration and reports are stored locally on this device.\n\nFor reliable Samsung background operation, allow notifications and do not restrict background activity.").setPositiveButton("OK",null).show();}
void about(){new AlertDialog.Builder(this).setTitle("About").setMessage("SMS Forwarder\n\nI built this\n\nMobile: 09121988262\nHashemi.srh@gmail.com\nReza.hashemi@tourismbank.ir\n\nPersonal-use application.").setPositiveButton("OK",null).show();}}
