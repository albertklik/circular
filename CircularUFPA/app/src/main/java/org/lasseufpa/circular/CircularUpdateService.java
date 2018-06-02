package org.lasseufpa.circular;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.lasseufpa.circular.Domain.Circular;
import org.lasseufpa.circular.Utils.NetworkUtils;

import java.util.Calendar;

/**
 *
 *    ALPHAMAGE APPS TECHNOLOGY
 *    Desenvolvimento de aplicações com qualidade
 *
 */

public class CircularUpdateService extends Service implements Runnable, MQTTconect.MQTTActionListener {

    private boolean serviceOn = false;

    //recuperando instancia do repositorio
    private RepositorioCircular repositorio = MainActivity.repositorioCirculares;

    //criador de circulares interpretando suas mensagens
    private CircularBuilderGSM ciruclarBuilder;
    //Ultima atualização
    private Calendar lastUpdate = Calendar.getInstance();
    //tempo de atualização em milisegundos
    private final int timeUpdate = 500;

    //mqttConect
    private MQTTconect mqttConect;


    /**
     * Executado na criação do objeto CircularUpdateService
     */
    @Override
    public void onCreate() {
        super.onCreate();

        if (MQTTconect.getInstance()==null) {
            MQTTconect.createInstance(this);
            mqttConect = MQTTconect.getInstance();
            mqttConect.addMQTTActionListener(this);
        } else {
            mqttConect = MQTTconect.getInstance();
        }

        mqttConect.setStatus(MQTTconect.MQTTConectStatus.STATUS_INITALIZING);

    }

    public void connectToServerMqtt() {
        try {
            mqttConect.setStatus(MQTTconect.MQTTConectStatus.STATUS_CONNECTING);
            mqttConect.connect(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    printMQTTLog("the connection task is sucessfull");

                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    printMQTTLog("the conection task is failed");
                    if (NetworkUtils.isNetworkOnline(CircularUpdateService.this)) {
                        printMQTTLog("Network is online");
                        mqttConect.setStatus(MQTTconect.MQTTConectStatus.CONNECTION_FAILED);
                    } else {
                        printMQTTLog("Device is disconnected");
                        mqttConect.setStatus(MQTTconect.MQTTConectStatus.DEVICE_OFFLINE);
                    }

                    exception.printStackTrace();
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
            printMQTTLog("error on connect to mqtt server");
        }
    }

    public void disconnectToMqttServer() {
        try {
            mqttConect.disconnect(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    printMQTTLog("disconnected from server");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    mqttConect.setStatus(MQTTconect.MQTTConectStatus.STATUS_DISCONNECTED);
                    printMQTTLog("Error on disconnect from server mqtt");
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
            printMQTTLog("Error on disconnect from server mqtt");
        }
    }

    public void printMQTTLog(String msg) {
        Log.d("MqttStatus",msg);
    }



    public void subscribeToTopicMqtt () {
        try {
            mqttConect.setStatus(MQTTconect.MQTTConectStatus.STATUS_SUBSCRIBING);
            mqttConect.subscribeToTopic(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    mqttConect.setStatus(MQTTconect.MQTTConectStatus.STATUS_SUBSCRIBED);
                    printMQTTLog("subscribed on topic [ "+mqttConect.getSubscriptionTopic()+" ]");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    mqttConect.setStatus(MQTTconect.MQTTConectStatus.STATUS_CONNECTED);
                    printMQTTLog("failed on subscribe to topic [ "+mqttConect.getSubscriptionTopic()+" ]");
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
            printMQTTLog("error on subscribe to topic");
        }
    }

    @Override
    public boolean stopService(Intent name) {
        stop();
        return super.stopService(name);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        connectToServerMqtt();
        serviceOn = true;
        new Thread(this).start();
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void run() {
        //enquanto o serviço estiver ligado
        while (serviceOn) {
            //se houver conexão com o mqttServer
            if (isMqttConnected()) {
                try {
                    repositorio.UpdateCircularList();
                } catch (Exception e) {
                    e.printStackTrace();
                    stop();
                }
            }
                updateNotification(mqttConect.getStatusString());
                try {
                    Thread.sleep(timeUpdate);        //esperar 100 milisegundos
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

        }

        removeNotification();
        stopSelf();

    }

    private boolean isUpdated() {
        //pergunta para o repositorio se o mapa está atualizado
        return repositorio.isUpdated(lastUpdate);
    }

    public void stop() {
        serviceOn = false;
    }

    /**
     * Iniciar o processo de comunicação com o broker MQTT
     */
    private boolean isMqttConnected() {
        return mqttConect.isconnected();
    }

    @Override
    public void onDestroy() {
        stop();
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (mNotificationManager!=null) {
            mNotificationManager.cancel(1);
        }
        disconnectToMqttServer();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    //mensagem recebida do broker

    public void messageArrived(String topic, String message) {

        String topicos[] = topic.split("/");

        if (topicos.length==5 ) {

            String info = topicos[3];
            String nomeCircular = topicos[4];

            switch (info) {
                case "loc" :
                    Circular c = new CircularBuilderGSM().CircularBuild(message,nomeCircular);
                    repositorio.saveCircular(c);
                    printMQTTLog("localização do circular "+nomeCircular+" recebida");
                    break;
                default:
                    printMQTTLog("Mensagem não reconhecida");
                    break;
            }
        } else {
            printMQTTLog("topico não reconhecido");
        }

    }

    private void removeNotification () {
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (mNotificationManager != null) {
            mNotificationManager.cancel(1);
        }
    }

    private void updateNotification (String messageGet) {

        int mId = 1;
        String message[] = {"",""};
        message[0] = messageGet;
        message[1] = repositorio.activeCircularesNumber() + " Circulares ativos";



        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_informacoes)
                        .setContentTitle("CircularGPS service - Ativado")
                        .setContentText(messageGet).setOngoing(true)
                        ;

        if (isMqttConnected()) {

            NotificationCompat.InboxStyle style = new NotificationCompat.InboxStyle();
            style.setBigContentTitle("CircularGPS service - Ativado");
            for (String line : message) {
                style.addLine(line);
            }
            mBuilder.setStyle(style);
        }

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intentActivity =  new Intent(this, MainActivity.class);
        intentActivity.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
              intentActivity , PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(contentIntent);
        // mId allows you to update the notification later on.
        if (mNotificationManager != null) {
            mNotificationManager.notify(mId, mBuilder.build());
        }

    }


    @Override
    public void onConnectComplete(boolean Reconnect, String ServerUri) {
        if (Reconnect) {
            printMQTTLog("reconected to mqtt server: " + ServerUri);

        } else {
            printMQTTLog("connected to mqtt server: " + ServerUri);
        }

        mqttConect.setStatus(MQTTconect.MQTTConectStatus.STATUS_CONNECTED);
        subscribeToTopicMqtt();

    }

    @Override
    public void onConnectionLost(Throwable cause) {
        mqttConect.setStatus(MQTTconect.MQTTConectStatus.STATUS_DISCONNECTED);
        printMQTTLog("conection was lost");
    }

    @Override
    public void onMessageArrived(String topic, String message) throws Exception {
        printMQTTLog("Message arrived [ "+topic+" ] : " + message);
        new OnMessageArrived().execute(topic,message);
    }



    private class OnMessageArrived extends AsyncTask<String, Void, Void> {
        protected Void doInBackground(String... params) {
            messageArrived(params[0],params[1]);
            return null;
        }
    }
}



