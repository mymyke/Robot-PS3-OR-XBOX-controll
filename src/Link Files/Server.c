//File server.c
#include <sys/types.h>  // für Datentypen
#include <sys/socket.h>  // für Socket-Funktionen
#include <stdio.h>  // für allgemeine Ein/Ausgabe
#include <netinet/in.h>  //für IP-Adresse
#include <arpa/inet.h> // für die Socket-Datenstruckturen
#include <string.h> // für Stringfunktionen
#include <stdlib.h>
#include <unistd.h>
#include <sys/param.h>
#include <sys/uio.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <netdb.h>
#include <sys/fcntl.h>
#include <sys/time.h>

void *Graphics(void *threadid);


main(){
  enable_servos();
  int server_socket, neuer_socket;   // um Server - / Clientinformationen zu speichern
  int anzahl, laenge;  // [anzahl] die Anzhal der empfangenen Zeichen,[laenge] die Laenge/Größe der Server- / Clientinformationen, diese brauchen wir später als Parameter
  char empfangen[1000]; // Buffer für empfangene Zeichen
  char *lastGet;
  char senden[1000]; //Buffer für gesendete Zeichen

  int verbindung_nummer = 1; // ein Counter für die Anzahl der Verbindungen

  struct sockaddr_in serverinfo, clientinfo; //structuren für Server / Client - in diesen werden Port, Ip-Adresse Art der Verbindung gespeichert
  unsigned short int portnummer = 7777; //unser Port an dem der Server horchen soll
  char ip_adresse[] = "192.168.43.206"; // Die IP-Adresse an die der Server "gebunden" wird

                                                 //printf("\n Server socket()...");

  /*
  Wir erstellen einen neuen Socket und zwar mit den Parametern:
  AF_INET, d.h. wir benutzen die Transportprotokolle des Internets, also IP und TCP oder UDP
  SOCK_STREAM, d.h. wir benutzen das TCP Protokoll
  0 hier kan man nochmal einen speziellen Typ eines Transportprotokolls auszuwählen, was in unserem Beispiel aber nicht nötig ist, denn wir haben mit den vorherigen Parametern schon klar gemacht, was für einen Socket wir haben wollen
  */
  server_socket = socket(AF_INET, SOCK_STREAM, 0);

  // die Serverstruktur mit Daten füllen
  serverinfo.sin_family = AF_INET; //Family setzen
  serverinfo.sin_addr.s_addr = inet_addr(ip_adresse); //ip-adresse setzen, dabei inet_addr verwenden, um die Zeichenkette in eine 32-Bit-Zahl umzuwandeln, man kann hier auch die Konstante INADDR_ANY setzen, um dem Socket zu sagen, dass er an allen IP Adressen horchen soll
  serverinfo.sin_port = htons(portnummer); // Port setzen, dabei htons() verwenden, um auch den Port in BIts umzuwandeln
  laenge = sizeof(serverinfo); // wir müssen die länge der Struktur speichern, dabei wir diese noch als Parameter benötigen
  /*
        wer sich mehr für Ip-Adressen und dessen Umwandlung in Bits ineteressiert und warum dies nötig ist. schaut mal Wikipedia vorbei:
        http://de.wikipedia.org/wiki/IPv4
        */

						//printf("\n Server: bind()...");

  /*
  Den Server an seinen Port und IP binden.
  wir brauchen einmal den erstellten Socket also server_socket
  und wir brauchen die Struktur allerdings, benötigt bind möglicherweise nicht sockaddr_in sondern nur sockaddr , deswegen müssen wir die serverinfo mittel Zeiger und Refenz in sockaddr umwandeln
  der Parameter ist die laenge der Struktur
  */
  bind(server_socket, (struct sockaddr *)&serverinfo, laenge);

                                                 //printf("\n Server: listen()...\n Server mit IP %s an Port %d wartet...", ip_adresse, portnummer);

  /*
  fange an zu horchen
  die 3 gibt hierbei an, wie lang die Warteschlange für den Socket ist, d.h. es können 3 Clients gleichzeitig einen Verbindungswunsch schicken, dadurch wird der Server selber etwas entlastet. Natürlich kann man den Wert auch höher stellen, nur das jeweilige Betriebsystem nimmt eine zu große Warteschlange nicht als akzeptabel an und verringert sie bis zur Obergrenze
  */
  listen(server_socket, 3);
	int camWorked=camera_open();
	printf(camWorked==1 ?  "\ncamera geöffnet\n" : "\ncamera fehler beim öffnen\n");

  pthread_t camThread;
  long  t;

  if(pthread_create(&camThread,NULL,Graphics,(void *)t)) {

		fprintf(stderr, "Error creating thread\n");
		return 1;

	}
  // Endlosschleife um Clients zu akzeptieren
  while (1){

    printf("\n Verbindung %d wartet...", verbindung_nummer);

    stop();
    if (fcntl(server_socket, F_SETFL, O_NDELAY) < 0||fcntl(server_socket, F_SETFL, O_NDELAY)==NULL) {
					//perror("Can't set socket to non-blocking\n");
      stop();
      empfangen;
    }

    /*
                neue Clients akzeptieren und dabei die Clientstruktur setzen, deswegen ist clientinfo als Referenz und die länge auch
                */
    neuer_socket = accept(server_socket, (struct sockaddr *)&clientinfo, &laenge);

                                                   //printf("\n Verbindung mit %s \n", inet_ntoa(clientinfo.sin_addr));

    /* Daten die man empfängt in die Variable empfangen schreiben, man muss hier als letzten Parameter einen Puffer angeben, wie viele Daten empfangen werden können, ist der Puffer zu klein, können möglicherweise nicht alle Daten erfasst werden, man könnte dann eine weitere Schleife einbauen
                */
    anzahl = read(neuer_socket, &empfangen, sizeof(empfangen));
    // Man muss am Ende der Daten eine 0 setzen, damit erkannt wird wo die Nachricht endet, sonst hat man den "Datenmüll" mit dem man nichts anfangen kann noch in seinen Empfangen Daten, um zu sehen wie dies aussieht, könnt ihr ja mal diesen Schritt weglassen
    empfangen[anzahl] = 0;

    //printf("\n\n Empfangen : \n%s\n", empfangen);

    /*
                Was wurde empfangen?
                */
	 printf("\nempfangen %s, lastGet: %s\n", empfangen,lastGet);
    /*if(lastGet==empfangen){
		stop();
	}*/
    if(empfangen!=NULL){
	  lastGet=empfangen;
      char* token;
      char* string;
      int right[2];
      int left[2];
      int i=0;
      int speed=0;
      int rightspeed;
      int leftspeed ;
       int color=-1;
      string = strdup(empfangen);

      if (string != NULL) {

        while ((token = strsep(&string, " ")) != NULL)
        {
          int value=atoi(token);
          printf("Nummer %i, Inhalt: %i\n", i,value);

          if(i==0){
            speed=value;
          }
          else if(i==1){

            if(value<-5){
              if(speed>5){
                leftspeed =100+value*2;
                rightspeed= 100;
              }
              else if(speed<-5){
                leftspeed =value*(-2)-100;
                rightspeed= -100;
              }

            }
            else if(value>5){
              if(speed>5){
                rightspeed =100-(value*2);
                leftspeed = 100;
              }
              else if(speed<-5){
                rightspeed =(value*2)-100;
                leftspeed = -100;
              }
            }

            else{
              leftspeed=speed;
              rightspeed=speed;
            }

                                                           //printf("Speed %i, rightspeed: %i\n", speed,rightspeed);
                                                           //printf("Speed %i, leftspeed: %i\n", speed,leftspeed);
            drive(leftspeed,rightspeed);
          }
          else if(i==2){

          }
          else if(i==3){
            camera_move(value);
          }
          else if(i==4){
            claw_move(value);
          }
          else if(i==5){
            if(value==0){
				color=-1;
			}
            else if(value==1){
              color=0;
			 }
             else if(value==3){
              color=1;
			 }
           else if(value==5){
              color=2;
			}
           else if(value==7){
              color=3;
            }
          }
          else if(i==6&&color>=0){
			                                                 //printf("i: %i \n color: %i\n",i,color);
			  stop();

			  search(color);
			  msleep(2000);
              color=-1;
          }
          i++;
        }
      }
    }

    close(neuer_socket);
                                                   //printf("\n");
    verbindung_nummer++;

  }

  // Wir schließen den Server Socket, allerdings wird diese Stellen niemals erreicht, da wir eine Endlosschleife zuvor eingebaut haben
                                                 //printf("Beenden\n\n");
  close(server_socket);

  return(0);
}

void *Graphics(void *threadid){
	graphics_open(160,120);
	while(!side_button()){
		camera_update();
		graphics_blit_enc(get_camera_frame(), BGR, 0, 0, get_camera_width(), get_camera_height());
		graphics_update();
	}
	graphics_close();
	return NULL;
}
