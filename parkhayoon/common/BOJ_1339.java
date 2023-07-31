package algo.week2.common;

/*
Question Info.
단어 수학 문제는 N개의 단어로 이루어져 있으며, 각 단어는 알파벳 대문자로만 이루어져 있다.
알파벳 대문자를 0부터 9까지의 숫자 중 하나로 바꿔서 N개의 수를 합하는 문제
같은 알파벳은 같은 숫자로 바꿔야 하며, 두 개 이상의 알파벳이 같은 숫자로 바뀌어지면 안 된다.

예를 들어, GCF + ACDEB를 계산한다고 할 때,
A = 9, B = 4, C = 8, D = 6, E = 5, F = 3, G = 7로 결정한다면,
두 수의 합은 99437이 되어서 최대

N개의 단어가 주어졌을 때, 그 수의 합을 최대로 만드는 프로그램

input
첫째 줄에 단어의 개수 N(1 ≤ N ≤ 10)
둘째 줄부터 N개의 줄에 단어가 한 줄에 하나씩, 단어는 알파벳 대문자로만
모든 단어에 포함되어 있는 알파벳은 최대 10개이고, 수의 최대 길이는 8
서로 다른 문자는 서로 다른 숫자를 나타낸다.

Idea
제일 높은 자리에 큰 숫자가 들어가야 함

ABC
BCA -> B에 최대값 들어가야 함
자리수 정리=> (A+B)*10^2 + (B+C)*10^1 + (C+A)*10^0
알파벳 순 정리 => (10^2+10^0)*A + (10^2+10^1)B + (10^1+10^0)C
= 110B + 101A + 11C => B, A, C 순으로 큰 값 삽입
단어 입력 -> 알파벳끼리 정렬 -> 최대 자릿수 알파벳부터 큰 수 대입
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class BOJ_1339 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        String[] str = new String[n];

        for(int i=0; i<n; i++)
            str[i] = sc.next();

        ArrayList<Character> alpha = new ArrayList<>(); // 각 알파벳 저장(알파벳 여부 확인)
        int[] posValue = new int[10]; // 각 알파벳 별 자리수 저장
        for(int i=0; i<n; i++) {
            char[] temp = str[i].toCharArray();
            for(int j=0; j<temp.length;j++) {
                if (alpha.contains(temp[j])) { // 만약 알파벳이 이미 존재하면
                    posValue[alpha.indexOf(temp[j])] += Math.pow(10, temp.length-1-j); // 알파벳의 자릿수 증가(ex) 10^(3-1-0))
                }
                else { // 알파벳이 아직 없으면
                    alpha.add(temp[j]); // 신규 알파벳 배열에 추가
                    posValue[alpha.indexOf(temp[j])] = (int) Math.pow(10, temp.length-1-j); // 알파벳의 자릿수 추가
                }
            }
        }
        // System.out.println(Arrays.toString(posValue));

        // 각 알파벳 별 자리수 정렬 - 가장 큰 자리수를 가진 값부터 내림차순으로 정렬(알파벳은 상관없음, 단순 구분용)
        for(int i=0; i<alpha.size(); i++) {
            for(int j=i+1; j<alpha.size(); j++) {
                if (posValue[i] < posValue[j]) {
                    int temp = posValue[i];
                    posValue[i] = posValue[j];
                    posValue[j] = temp;
                }
            }
        }
        // System.out.println(Arrays.toString(posValue));

        // for문 진행 후 wordToInt = {('A', 101), ('B', 110), ('C', 11)}
        // wordToInt 내림차순 정렬 후 큰 순서로 숫자 9~0 부여
        // {('A', 101), ('B', 110), ('C', 11)} ->  {('B', 110), ('A', 101), ('C', 11)}
        // => 9*110 + 8*101 + 7*11
        int answer = 0;
        for(int i=0; i<alpha.size(); i++) {
            answer += (9-i)*posValue[i];
        }
        System.out.println(answer);
    }

}