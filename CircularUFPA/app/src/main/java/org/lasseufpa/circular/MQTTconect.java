package org.lasseufpa.circular;


import android.content.Context;
import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *    ALPHAMAGE TECHNOLOGY
 *    Desenvolvimento de aplicações com qualidade
 *
 */

public class MQTTconect {


    private static int CONECTION_TIMEOUT = 5;
    private static int KEEP_ALIVE_INERVAL = 30;
    private static MQTTconect MQTTCONNECT_GLOBAL_INSTANCE;
    private final MqttConnectOptions connectOptions;
    private MqttAndroidClient mqttAndroidClient;                //objeto Cliente MQTT do Android
    private String serverAdress = "tcp://iot.eclipse.org";     //variavel para armazenar o endereço do servidor
    private String clientID;                                    //armazena o identificador do cliente MQTT
    private String username = "alberto";                        //armazena o nome de usuário
    private String password = "null";                           //armazena a senha de conexão do broker
    private String subscriptionTopic = "/ufpa/circular/#";           //Tópico para o cliente subescrever no servidor
    private List<MQTTActionListener> listeners;
    private int status;




    public static MQTTconect getInstance() {
        return MQTTCONNECT_GLOBAL_INSTANCE;
    }

    public static void createInstance(Context context) {
        if (MQTTCONNECT_GLOBAL_INSTANCE==null) {
            MQTTCONNECT_GLOBAL_INSTANCE = new MQTTconect(context);
        }
    }


    public MQTTconect(Context context) {

        //gera um código randômico que serve como identificação do cliente
        clientID = MqttClient.generateClientId()+"_circularUFPAapp";
        //cria um objeto MQTTClient android entregando como parametro o endereço o servidor e o id do cliente
        mqttAndroidClient = new MqttAndroidClient(context, serverAdress, clientID);

        listeners = new ArrayList<>();
        //configura um objeto CallBack (objeto de chamada caso tenha alteração)
        mqttAndroidClient.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {
                for ( MQTTActionListener listener:
                     listeners) {
                    listener.onConnectComplete(reconnect,serverURI);
                }
            }

            @Override
            public void connectionLost(Throwable cause) {
                for ( MQTTActionListener listener:
                        listeners) {
                    listener.onConnectionLost(cause);
                }
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                for ( MQTTActionListener listener:
                        listeners) {
                    listener.onMessageArrived(topic,message.toString());
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });

        //criando conectivityOptions
        connectOptions = new MqttConnectOptions();
        connectOptions.setAutomaticReconnect(true);
        connectOptions.setConnectionTimeout(CONECTION_TIMEOUT);
        connectOptions.setKeepAliveInterval(KEEP_ALIVE_INERVAL);
    }

    public boolean isconnected () {
        return mqttAndroidClient.isConnected();
    }

    public void addMQTTActionListener (MQTTActionListener listener) {
        listeners.add(listener);
    }

    public void removeMQTTActionListener (MQTTActionListener listener) {
        for (MQTTActionListener currentListener : listeners) {
            if (currentListener == listener) {
                listeners.remove(listener);
            }
        }
    }

    public void connect(IMqttActionListener listener) throws MqttException {
            mqttAndroidClient.connect(connectOptions,null,listener);
    }

    public void subscribeToTopic(IMqttActionListener listener) throws MqttException {
            mqttAndroidClient.subscribe(subscriptionTopic, 0, null, listener);
    }


    public void disconnect(IMqttActionListener listener) throws MqttException {
         mqttAndroidClient.disconnect(0,null,listener);
    }


    //getters and setters methods

    public void setServerAndress(String adress) {
        this.serverAdress = adress;
    }

    public void setUsername (String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getServerAndress() {
        return serverAdress;
    }

    public String getClientID() {
        return clientID;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getSubscriptionTopic() {
        return subscriptionTopic;
    }

    public void setSubscriptionTopic(String subscriptionTopic) {
        this.subscriptionTopic = subscriptionTopic;
    }

    public int getStatus() {
        return status;
    }

    public String getStatusString() {
        String retorno = "";
        switch (status) {
            case MQTTConectStatus.STATUS_INITALIZING:
                retorno = "Iniciando o serviço";
                break;
            case MQTTConectStatus.STATUS_CONNECTING:
                retorno = "Conectando ao servidor";
                break;
            case MQTTConectStatus.STATUS_CONNECTED:
                retorno = "Conectado ao servidor";
                break;
            case MQTTConectStatus.STATUS_SUBSCRIBING:
                retorno = "Estabelecendo comunicação";
                break;
            case MQTTConectStatus.STATUS_SUBSCRIBED:
                retorno = "Comunicação estabelecida";
                break;
            case MQTTConectStatus.STATUS_DISCONNECTED:
                retorno = "Conexão com o servidor perdida";
                break;
            case MQTTConectStatus.CONNECTION_FAILED:
                retorno = "Falha na conexão com o servidor";
                break;
            case MQTTConectStatus.DEVICE_OFFLINE:
                retorno = "Dispositivo sem conexão com a internet";
                break;
        }
        return  retorno;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public interface MQTTActionListener {

        public void onConnectComplete(boolean Reconnect,String ServerUri);

        public void onConnectionLost(Throwable cause);

        public void onMessageArrived(String topic,String message) throws Exception;

    }

    public static class MQTTConectStatus {
        public final static int STATUS_INITALIZING = 1;
        public final static int STATUS_CONNECTING = 2;
        public final static int STATUS_CONNECTED = 3;
        public final static int STATUS_SUBSCRIBING = 4;
        public final static int STATUS_SUBSCRIBED = 5;
        public final static int STATUS_DISCONNECTED = 6;
        public final static int CONNECTION_FAILED = 7;
        public final static int DEVICE_OFFLINE = 8;
    }


}
