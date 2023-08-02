package kimsuhyeok.common;

import java.util.Scanner;

/**
 * Scanner를 통해 매번 출력을 하게 되면 시간초과가 난다.
 * => StringBuilder의 중요성을 깨달아버리게 되었다.
 * @author SSAFY
 *
 */

public class BOJ_11729 {

	static int N;
	static int cnt=0;
	static StringBuilder sb = new StringBuilder();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		//원판의 개수
		N = sc.nextInt();
		
		cnt = (int)Math.pow(2, N)-1;
		sb.append(cnt+"\n");
		hanoi(N,1,2,3);
		System.out.println(sb);
	}
	
	public static void hanoi(int N, int start, int via, int end) {
		if(N==1) {
			sb.append(start+" "+end+"\n");
		}
		else {
			hanoi(N-1, start, end, via);
			sb.append(start+" "+end+"\n");
			hanoi(N-1, via, start, end);
		}
	}
}
