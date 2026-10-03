package ir.hashemi.smsforwarder;
import android.content.*;
public class BootReceiver extends BroadcastReceiver{public void onReceive(Context c,Intent i){if(Intent.ACTION_BOOT_COMPLETED.equals(i.getAction())||Intent.ACTION_MY_PACKAGE_REPLACED.equals(i.getAction()))if(c.getSharedPreferences("cfg",0).getBoolean("enabled",false))NotificationHelper.show(c,"Forwarding is active");}}
