package kimsuhyeok.common;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Dynamic Programming: 동적 계획법
 * 
 * 하나의 큰 문제를 여러 개의 작은 문제로 나누어서 그 결과를 저장하여 다시 큰 문제를 해결할 때 사용하는 것
 * 즉 큰 문제를 작은 문제들로 쪼개고 그 답들을 저장해서 재활용한다.
 * 
 * 예) 피보나치
 * 피보나치는 재귀로 사용하지만 재귀로만 하면 비효율적인 -> 동적 계획법으로 해결
 * 피보나치의 점화식은 f(n) = f(n-1)+f(n-2)이므로
 * dp[i] = dp[i-1]+dp[i-2]로 해결가능
 * 
 * DP는 언제 사용하는가?
 * 1. Overlapping Subproblem(겹치는 부분 문제)
 * 2. Optimal Substructure(최적 부분 구조)
 * 
 * 만약 부분 문제가 반복되어 나타나지 않으면 재사용이 불가능하다 -> 부분 문제가 중복되지 않으니 dp를 사용할 수 없다.
 * 
 * DP 사용하는 방법
 * 1. DP로 풀 수 있는 문제인지 확인 -> 이게 어렵다
 * 2. 문제의 변수 파악
 * 3. 변수 간 관계식 만들기
 * 4. 메모하기 -> 변수의 값들에 따른 결과 저장
 * 5. 기저 상태 파악하기 -> 가장 작은 문제의 상태 파악. 예) 피보나치의 f(0)=f(1)=1과 같은 것
 * 6. 구현
 * 
 */
public class BOJ_1912 {
	
	public static int[] arr;
	public static int[] dp;
	public static int n;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		
		n = sc.nextInt();
		arr = new int[n];
		
		for(int i=0;i<n;i++) {
			arr[i] = sc.nextInt();
		}
		
		dp = new int[n];
		dp[0] = arr[0];
		for(int i=1;i<n;i++) {
			int temp = dp[i-1]+arr[i];
			dp[i] = Math.max(arr[i], temp);
		}
		Arrays.sort(dp);
		System.out.println(dp[n-1]);
	}

}
