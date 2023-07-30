package kimsuhyeok.group;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 백트래킹을 이용한 순열 문제
 * 
 * 토요일날 순열 구현했는데 문제 보니까 그대로 하면 되어서 빠르게 해결함
 */

public class BOJ_15649 {

	public static int n,m;
	static int[] arr;
	static int[] out;
	static boolean[] visited;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		
		n = sc.nextInt();
		m = sc.nextInt();
		arr = new int[n];
		
		for(int i=0;i<n;i++) {
			arr[i]=i+1;
		}
		visited = new boolean[n];
		out = new int[m];
		
		permutation(arr, out, visited, 0, m);
	}

	public static void permutation(int[] arr, int[] out, boolean[] visited, int depth, int r) {
		if(depth==r) {
			for(int a: out) {
				System.out.print(a+" ");
			}
			System.out.println();
			return;
		}
		
		for(int i=0;i<n;i++) {
			if(!visited[i]) {
				visited[i]=true;
				out[depth]=arr[i];
				permutation(arr, out, visited, depth+1, r);
				visited[i]=false;
			}
		}
	}
	
}
