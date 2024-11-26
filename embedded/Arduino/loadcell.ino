#include "HX711.h"

#define LOADCELL_1_DOUT_PIN 2
#define LOADCELL_1_SCK_PIN 3

// scale - 10Kg loadcell : 226 / 5kg loadcell : 372
// ADC 모듈에서 측정된 결과값을 (loadcellValue)값 당 1g으로 변환해 줌
float loadcellValue = 372.0;
HX711 loadcell1;
#define THRESHOLD_WEIGHT 300

int weight[4];

void setup()
{
  Serial.begin(9600);

  loadcell1.begin(LOADCELL_1_DOUT_PIN, LOADCELL_1_SCK_PIN);

  delay(500);

  // 스케일 설정
  loadcell1.set_scale(loadcellValue);

  // 오프셋 설정(10회 측정 후 평균값 적용) - 저울 위에 아무것도 없는 상태를 0g으로 정하는 기준점 설정(저울 위에 아무것도 올려두지 않은 상태여야 합니다.)
  loadcell1.tare(10);

  delay(500);
}

void loop()
{
  float weight1 = loadcell1.get_units(10); // 10번 측정 평균 값
  weight[0] = int(weight1);

  for (int i = 0; i < 4; i++)
  {
    Serial.print(weight[i]); // 5회 측정 평균값, 소수점 아래 2자리 출력
    Serial.print(" ");
  }
  Serial.println();

  delay(500);
}