package choiseohyun.group;

import java.util.Arrays;
import java.util.Scanner;

/** 15649. N과M
 * [풀이 시간] : 60분
 * N과 M이 주어질때 1~N의 자연수중에서 M개의 자연수를 갖는 수열 구하기
 * 
 * [풀이 방법]
 * dfs를 사용한 백트래킹까지는 생각했으나.. 
 * 첫트때 (1,2) (1,3)이 아니라 (1,2) (3)으로 출력되도록 구현해버림 
 *  => 그걸 고칠 아이디어를 생각하는데 오래걸림(그래도 해결!)
 * 
 * 1. DFS를 돌며 방문한 노드가 M개가 될때까지 재귀
 * 2. 해당 경로의 모든 노드를 출력해야하므로 방문현황을 tmp배열에 저장
 *   CF. 이때 tmp의 인덱스는 몇번째 방문인지 len을 이용하였음
 * 3. 한 경로의 탐색을 마친 후에는 백트래킹을 통해 끝쪽만 갈아끼며(?) 효율적으로 탐색한다.
 *   CF. 즉, 1,2,3 노드가 있을때 1->2에대한 탐색을 마친 후, 1->3을 바로 탐색하도록 구현한다.
 * 
 */
public class BOJ_15649 {
	static int[] arr,check,tmp;
	static int n,m;
	public static void dfs(int len){
		if(len == m) {
			for(int t : tmp) System.out.print(t+" ");
			System.out.println();
			return;
		}
		for(int i=0; i<n; i++) {
			if(check[i]==0) {
				check[i] = 1;
				tmp[len] = arr[i];
				dfs(len+1);  
				check[i] = 0;
			}
		}
		
	}
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		n = sc.nextInt(); //1~N까지의 자연수
		m = sc.nextInt(); //길이가 M인 수열
		arr = new int[n];
		for(int i=0; i<n; i++) arr[i]=i+1;
		check = new int[n];
		tmp = new int[m];
		// dfs를 통해 순열을 구할 수 있음
		dfs(0);
	}
}
