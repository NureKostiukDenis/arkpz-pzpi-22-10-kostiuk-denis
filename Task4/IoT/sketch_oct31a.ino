#include <SPI.h>
#include <MFRC522.h>
#include <ESP8266WiFi.h>
#include <PubSubClient.h>

// --------- MQTT SETTINGS ------------

const char *_mqtt_address = "192.168.50.165"; 
const int _mqtt_port = 8223; 
const char *_mqtt_user = "client"; 
const char *_mqtt_pass = "aboba228"; 

// --------- GATE SETTINGS ------------

String tag = "GATE002";
String _apikey = "QWxsbyAyOjI6RnpycUw0NHRlVlF5NWNqMGVpSm1GUkluZFRHMjpvakdzYVVYVnVOcG44eVpHMWVtNVB3Q1U1dHZKRysySzZkOVhCVm44Ly9zPQ==";

// --------- WIFI connection ----------

const char *_ssid = "ASUS_28";
const char *_password = "aboba228";

// --------- PINS connection ----------
constexpr uint8_t RST_PIN = D3;
constexpr uint8_t SS_PIN = D2;


MFRC522 rfid(SS_PIN, RST_PIN);
WiFiClient espClient;
PubSubClient client(espClient);

void WIFIinit() {
  WiFi.mode(WIFI_STA);
  WiFi.begin(_ssid, _password);
  Serial.print("Connecting to WiFi");
  
  while (WiFi.status() != WL_CONNECTED) {
    delay(1000);
    Serial.print(".");
  }
  Serial.println("\nWiFi connected!");
  Serial.print("IP address: ");
  Serial.println(WiFi.localIP());
}

void callback(char* topic, byte* payload, unsigned int length) {
  Serial.print("Message received [");
  Serial.print(topic);
  Serial.print("]: ");
  for (int i = 0; i < length; i++) {
    Serial.print((char)payload[i]);
  }
  Serial.println();
}

void MQTTinit() {
  client.setServer(_mqtt_address, _mqtt_port);
  client.setCallback(callback);

  while (!client.connected()) {
    Serial.print("Connecting to MQTT server...");
    if (client.connect(tag.c_str(), _mqtt_user, _mqtt_pass)) {
      Serial.println("Connected to MQTT server!");
    } else {
      Serial.print("Failed, rc=");
      Serial.print(client.state());
      Serial.println(" retrying in 5 seconds...");
      delay(5000);
    }
  }
}

void reconnect() {
  while (!client.connected()) {
    Serial.print("Reconnecting to MQTT server...");
    if (client.connect(tag.c_str(), _mqtt_user, _mqtt_pass)) {
      Serial.println("Reconnected to MQTT server!");
    } else {
      Serial.print("Failed, rc=");
      Serial.print(client.state());
      Serial.println(" retrying in 5 seconds...");
      delay(5000);
    }
  }
}

void setup() {
  Serial.begin(9600);
  SPI.begin();
  rfid.PCD_Init();
  WIFIinit();
  MQTTinit();
}

void loop() {
  if (!client.connected()) {
    reconnect();
  }
  client.loop();

  if (rfid.PICC_IsNewCardPresent() && rfid.PICC_ReadCardSerial()) {
    String rfidTag = ""; 
    for (byte i = 0; i < rfid.uid.size; i++) {
      rfidTag += String(rfid.uid.uidByte[i], HEX);
    }
    Serial.println("Card read: " + rfidTag);
    
    String topic = "warehouse/" + _apikey + "/entry/" + tag;

    client.publish(topic.c_str(), (const byte*)rfidTag.c_str(), rfidTag.length());
    Serial.println("Published to topic: " + topic);
    
    rfid.PICC_HaltA();
    rfid.PCD_StopCrypto1();
  }
  delay(100);
}
