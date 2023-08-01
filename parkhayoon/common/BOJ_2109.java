package algo.week2.common;
// ##########미완성 코드##########
// 아이디어는 어느정도 나왔으나 구현을 하지 못함
/*
info.
0 ≤ n ≤ 10,000, 1 ≤ d ≤ 10,000

idea
order: 빠른 순서부터? or 가격 높은 순서부터?
ex)
10 1
20 2
30 2
=> 20, 30 하는 것이 제일 높은 가치

sol 1)
d=1인 배열 - 최대값 1개만 고르기 - 1번(a)
d=2인 배열 - 최대값 2개 고르기 + 그 중 낮은 값과 1번 값 비교해서 더 큰값 넣기 - 2번(b+k, b + a)
            만약 1개 밖에 없다면 그 값 + 1번 / 값이 없으면 1번 값만
d=3인 배열 - 최대값 3개 고르기 + 그 중 낮은 값 2개와 2번 값 2개, 총 4개 비교해서 더 큰 두 개 값 넣기(d, e, f + (b+k, a))
            만약 2개 밖에 없다면 그 중 낮은 값과 2번 값들 중 높은 2개 값
...

sol 2)
d값 상관 없이 최대값 순 정렬
배열에 [d-1]에 삽입(최대한 늦춘 시간)

점화식 - 너무 어렵게 생각하나?
전체 price를
1) 같은 d값을 가진 price 끼리 묶기
2) price 순서로 내림차순
3) 점화식
d=1일 때, d=1인 요소들 중 가장 높은 price 최대 1개 선택 - maxPrice1[]
d=2일 때, d=2인 요소들 중 가장 높은 price 최대 2개 선택
         그 후, 그 중 낮은 price 1개와 maxPrice1[] 요소 1개 중 높은 값 1개로 교체 - maxPrice2[]
d=3일 때, d=3인 요소들 중 가장 높은 price 최대 3개 선택
         그 후, 그 중 낮은 price 2개와 maxPrice2[] 요소 2개 중 높은 값 2개로 교체 - maxPrice3[]
...
d=k일 때, d=k인 요소들 중 가장 높은 price 최대 k개 선택
         그 후, 그 중 낮은 price (k-1)개와 maxPrice(k-1)[] 요소 (k-1)개 중 높은 값 (k-1)개로 교체 - maxPricek[]
단, 선택 시 값이 k개 보다 작은 경우, 해당 값을 모두 가져갈 수 있음

ex)
d=1: [p1,p2,p3, ...] - 각 배열은 내림차순 정렬
d=2: [p11,p22,p33, ...]
d=3: [p111,p222,p333,...]
...
d=1일 때: p1 선택
d=2일 때: p11, max 1개(p1,p22) = px 선택
d=3일 때: p111, max 2개(p222,p333,p11,px) 선택
...
*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class BOJ_2109 {

    static int n;
    static int[][] arr;
    static int maxDay = 0;
    static ArrayList<Integer> maxPriceList = new ArrayList<>();

    static void maxPrice(int d) {
        int currDay = 1;
        ArrayList<Integer> currentPrices = new ArrayList<>();
        for (int i=0; i<n; i++) {
            if(arr[i][1]==currDay)
                currentPrices.add(arr[i][0]);
        }
        // prices 내림차순 정렬
        // prices.sort(reversed())
        Collections.sort(currentPrices, Collections.reverseOrder());
        // prices에서 가장 높은 d개의 price 선정해 prices에 저장
        // prices.slice(0~d-1)
        // 만약 d개보다 적으면 모두 선정
        // if prices.length < d { prices 유지 }
        ArrayList<Integer> currentMaxPrices = new ArrayList<>();
        if (currentPrices.size() > currDay) {
            for(int i=0; i<currDay; i++)
                currentMaxPrices.add(currentPrices.get(i));
        }
        else
            for(int i=0; i<currentPrices.size(); i++)
                currentMaxPrices.add(currentPrices.get(i));

        // prices에서 최대값 제외 나머지 값과 maxPriceList 값들 비교
        // totalPrices = {prices[1], prices[2], ..., maxPriceList[0], maxPriceList[1], ...}
        ArrayList<Integer> mixedPriceList = new ArrayList<>();
        int currentDayMaxPrice = currentMaxPrices.get(0);
        for (int i=1; i<currentMaxPrices.size(); i++)
            mixedPriceList.add(currentMaxPrices.get(i));
        for (int i=1; i<maxPriceList.size(); i++)
            mixedPriceList.add(maxPriceList.get(i));
        // prices의 최대값 제외 높은 순서대로 d-1개 선정
        // totalPrices.sort(reversed())
        Collections.sort(mixedPriceList, Collections.reverseOrder());
        // totalPrices.slice(0~d-2)
        // totalPrices.add(prices[0])
        // 만약 d개보다 적으면 모두 선정
        // if totalPrices.length < d-1 { totalPrices 유지 }
        if (mixedPriceList.size() > d-1) {

        }
        else {

        }
        mixedPriceList.add(currentDayMaxPrice);
        currDay++;
        if (currDay == d) return;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        n = sc.nextInt();
        arr = new int[n][2];

        for (int i=0; i<n; i++) {
            arr[i][0] = sc.nextInt();
            arr[i][1] = sc.nextInt();
            if (maxDay < arr[i][1])
                maxDay = arr[i][1];
        }
        /*
        for(int i=0;i<n;i++) {
            for(int j=0;j<n;j++) {
                if(arr[i][1] > arr[j][1]) { // d값 기준 오름차순 정렬
                    int[] temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }

        // 현재 arr - d값 순 오름차순

        // int[] currnetMaxPrice = new int[10000];
        ArrayList<Integer> currentMaxPrice = new ArrayList<>();
        for(int i=0;i<n;i++) {
            int d = arr[i][1];
            ArrayList<Integer> priceWithSameDay = new ArrayList<Integer>();
            for(int j=0;j<n;j++) { // 마감일이 같은 날인 강의끼리 모음
            	if (d==arr[j][1])
            		priceWithSameDay.add(arr[j][0]);
            }
            Collections.sort(priceWithSameDay, Collections.reverseOrder()); // 내림차순 정렬

            for(int j=0; j<d; j++) // 순서대로 price가 높은 d개를 저장
            	currentMaxPrice.add(priceWithSameDay.get(j));



        }
        */
        maxPrice(1);
    }

}