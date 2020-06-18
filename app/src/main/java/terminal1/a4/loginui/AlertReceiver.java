package terminal1.a4.loginui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        SharedPreferences preferences = context.getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String embarque = preferences.getString("Horaembarque","");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notifyembarque")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Cierra de la puerta de embarque a las " + embarque + "h")
                .setContentText("Quedan menos de 30 minutos para que cierre el embarque")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(200, builder.build());
    }
}
