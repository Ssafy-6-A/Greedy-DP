package choiseohyun.common;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;

/** BOJ_9251 LCS : 최장부분공통수열
 * 두 문자열이 주어졌을때 공통부분집합중 가장 긴것의 크기를 구하는 문제
 * 
 * 풀이기간 : 자그마치 삼일(ㅋㅋ)
 * 풀이방법 : 이차원배열을 통해 공통수열의 길이를 찾는다. 
 * 포인트는 이때 이차원배열의 의미하는바를 통해 규칙성을 찾는것!! 미친디피
 * 규칙성 찾기가 어렵게 느껴졌다... ,,
 * 
 */

/** 규칙성 정리
 * map[i][j]의 의미 : arr1[i]의 문자와 arr2[j]의 문자 까지의 LCS 크기
 * 맵 자체는 arr1[i]와 arr2[j]의 문자를 비교하면서 값을 채운다.
 * 
 * 같은 문자일때 : map[i-1][j-1]+1
 * (ex) ACA와 CA를 비교했을때 비교포인트는 A와 A, 즉 같은문자가 나왔으므로 +1을 해주어야하는데
 * 		그 누적합 대상은 두 A가 없을때의 비교점이었을것이다. 즉 AC와 C의 비교점에 +1 => map[i-1][j-1]+1
 *  
 * 다른 문자일때 : Math.max(map[i-1][j], map[i][j-1])
 * (ex) ACAY와 CA를 비했을때, A와 Y는 서로 다르므로 이전의 누적합을 끌어와야하는데,
 * 		그 대상은 ACA와 CA / ACAY와 C가 있고 그중 큰것으로 누적한다.
 */

public class BOJ_9251 {
	
	public static void main(String[] args) throws IOException {
		
		//Scanner sc = new Scanner(System.in);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		char[] arr1 = br.readLine().toCharArray(); //열값
		char[] arr2 = br.readLine().toCharArray(); //행값
		int[][] map = new int[arr2.length+1][arr1.length+1]; //0행 0열에 패딩값 추가

		
		for(int i=1; i<=arr2.length; i++) {
			for(int j=1; j<=arr1.length; j++) {
				//같은 문자일때
				if(arr2[i-1]==arr1[j-1]) map[i][j] = map[i-1][j-1]+1;
				//다른문자일때
				else map[i][j]=Math.max(map[i-1][j], map[i][j-1]);
			}
		}
		//System.out.println(Arrays.deepToString(map));
		System.out.println(map[arr2.length][arr1.length]);
	}
}