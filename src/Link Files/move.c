#include <sys/types.h>  // für Datentypen
#include <string.h> // für Stringfunktionen

void takepom(int color);
int foundit(int color);

#define CLAW1 2
#define CLAW2 0
#define RIGHT_MOTOR 2
#define LEFT_MOTOR 0

#define Camera 1
#define BLOB_SIZE_TAKE 5

void claw_move(int move) {
  enable_servos();
  set_servo_position(CLAW1, 1200+move);
  set_servo_position(CLAW2, 800-move);
}

void camera_move(int pos){
  enable_servos();
  set_servo_position(Camera,pos );
}
void drive(int leftMotorSpeed,int rightMotorSpeed) {
  motor(RIGHT_MOTOR,rightMotorSpeed);
  motor(LEFT_MOTOR,leftMotorSpeed);
}

void search(int color){
	double sec=seconds();
  do {
    camera_update();
	  printf("search\n");
	  if(sec+50<seconds()){
		  printf("nothing found\n");
		  msleep(1000);
		  break;
	  }
  }
  while(get_object_count(color) ==0);
	  printf("found something \n");
  takepom(color);
  printf("done");
  /*if(get_object_bbox(0,0).width>BLOB_SIZE_TAKE&&get_object_bbox(0,0).height>BLOB_SIZE_TAKE){
    takepom(color);
  }	*/
}
void stop(){
  drive(0,0);
}
void takepom(int color){
  printf("takepom \n");
  set_servo_position(CLAW1, 1000);
  set_servo_position(CLAW2, 1000);
  set_servo_position(Camera,1000);
  stop();

  int findit=1;
  int left_or_right=1; //ob nach linksoder rechts drehen l=0, r=1 wird vill später implimentiert???
  int getit=0;   //wenn er das pom hat
  int foundNothingToLong=0;
  double sec=seconds();
  while(!getit){

    if(left_or_right){
      //drive(-30,30);
      while(!get_object_count(color)>0){ //solange Kammera und Sensor nichts sehen rechts drehen
        camera_update();
      }
      stop();  //wenn etwas gefunden stehen bleiben
      msleep(200);
      camera_update();
      if(get_object_bbox(color,0).width>5&&get_object_bbox(color,0).height>5){ //War blob ein zu kleiner Blob(was falsches gesehen), wenn blob groß stehen bleiben und feinjustireung(foundit) machen
		graphics_rectangle_fill(get_object_bbox(color,0).ulx/2,get_object_bbox(color,0).uly/2,
								get_object_bbox(color,0).ulx+get_object_bbox(color,0).width*2,
								get_object_bbox(color,0).uly+get_object_bbox(color,0).height*2,255,0,0);


        getit=foundit(color);
      }
    }
    /*  else{
      drive(30,-30);
      while(!get_object_count(color) >0){
        camera_update();

      }
      stop();
      msleep(200);
      else{
        camera_update();
        if(get_object_bbox(0,0).width>5&&get_object_bbox(0,0).height>5){
          getit=foundit();

        }
      }
    }*/
  }
}

int foundit(int color){
  printf("foundit \n");
	stop();
  int x;
  camera_update();
  if(get_object_count(color)>0){
    while(get_object_center(color,0).y < 100){ //solange bis blob nahe genug
      camera_update();
      if(get_object_count(color)>0){ //schaut ob Blob noch in der Kamera
		  printf("blob gone");
        camera_update();
        x = get_object_center(color,0).x;
        if (x > 120)  {							//wenn Blob rechts--> rechts drehen ....
          drive(30,-30);  //je näher er dem pom kommt umso langsamer dreht er sich
        } else if (x < 40) {
          drive(-30,30);    //je näher er dem pom kommt umso langsamer dreht er sich
        } else if ((x > 40)||(x < 120)) {
          drive(100,100);
        }
      }
      else{//wenn blob nicht mehr in Kamera wieder in grobjustierung(takepom) gehen
        return 0;
      }
    }
    printf("target aquired! \n");
    drive(100,100);						//etwas vor fahren um Blob sicher zu erwischen
    msleep(1200);
    stop();
    msleep(100);
	enable_servos();
    set_servo_position(CLAW1, 2000);
    set_servo_position(CLAW2, 0);
	stop();
    return 1;
  }
  return 0;
}

