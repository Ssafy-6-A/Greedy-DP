package kimsuhyeok.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 요소를 제거하는거에서 어떻게 해야할지 고민했는데
 * 해당 요소를 기준으로 왼쪽에서 요소 바로 전까지 더한것중에 제일 큰거하고
 * 해당 요소를 기준으로 오른쪽에서 요소 바로 오른쪽까지 더한 것중에 제일 큰거를 다 더했을 때
 * 
 * 가장 큰 값을 가져오면 되는거 아님?
 * 그리고 그 값을 요소 제거 안했을때랑 비교하면 된다고 판단
 * 
 */

public class BOJ_13398 {
	
	static int[] arr;
	static int[] dp;
	static int[] dp2;
	static int n;
	static int[] dp3;
	static int answer = Integer.MIN_VALUE;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
		//강사님이 BufferedReader 마스터 해오라고 하셨으니 연습하자
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		//StringTokenizer도 사용하는거 연습하자
		StringTokenizer st;
		
		//하나 받을 때는 굳이 StringTokenizer를 사용할 필요가 없다고 판단하여 br.readline을 parseint만 했다.
		n = Integer.parseInt(br.readLine());
		arr = new int[n];
		dp = new int[n];
		dp2 = new int[n];
		dp3 = new int[n];
		
		st = new StringTokenizer(br.readLine());
		for(int i=0;i<arr.length;i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}
		
		dp[0] = arr[0];
		answer = dp[0];
		//왼쪽에서 쭉 더해줄때
		for(int i=1;i<n;i++) {
			dp[i] = Math.max(arr[i], dp[i-1]+arr[i]);
			
			//특정 수 제거 안했을 때 가장 큰 값
			answer = Math.max(answer, dp[i]);
		}
		
		//오른쪽에서 쭉 더해줄때
		dp2[n-1] = arr[n-1];		
		for(int i=0;i<n-1;i++) {
			int temp = dp2[n-1-i]+arr[n-2-i];
			dp2[n-2-i] = Math.max(dp2[n-1-i]+arr[n-2-i], arr[n-2-i]);
		}
		
		//요소 제거
		for(int i=1;i<n-1;i++) {
			//해당 요소 기준 왼쪽까지의 최댓값하고 해당 요소 기준 오른쪽까지의 최댓값을 더해준다음 max값 가져오기
			answer = Math.max(answer, dp[i-1]+dp2[i+1]);
		}
		
		
		System.out.println(answer);
		br.close();
	}

}
