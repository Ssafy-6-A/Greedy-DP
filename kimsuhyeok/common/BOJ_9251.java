package kimsuhyeok.common;

import java.util.Scanner;

public class BOJ_9251 {

	public static char[] str;
	public static char[] str1;
	
	public static int[][] dp;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);
		
		str = in.nextLine().toCharArray();
		str1 = in.nextLine().toCharArray();
		
		int len1 = str.length;
		int len2 = str1.length;
		
		int[][] dp = new int[len1 + 1][len2 + 1];
		
		for(int i = 1; i < len1+1; i++) {
			for(int j = 1; j < len2+1; j++) {
				
				//문자가 같으면
				if(str[i - 1] == str1[j - 1]) {
					
					//대각선 왼쪽 위의 값에서 +1해주기
					dp[i][j] = dp[i - 1][j - 1] + 1;	
				}
				 
				//안같으면
				else {
					//위 혹은 왼쪽에서 큰 값 아무거나 가져오기
					dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
				}
			}
		}
        
		//그렇게 해서 가장 큰 값 가져오기
		System.out.println(dp[len1][len2]);
		
//		System.out.println(LCS(strarr.length-1, strarr2.length-1));
	}
	
	//topdown방식으로 LCS함수를 만들어서 풀어보자
	
}
