package kimsuhyeok.group;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;


/**
 * 0: 빈칸
 * 1: 벽
 * 2: 바이러스가 있는 곳
 * 
 * 바이러스는 상하좌우로 인접한 빈 칸으로 모두 퍼질 수 있음.
 */

class Node{
	int x;
	int y;
	public Node(int x, int y) {
		this.x =x;
		this.y = y;
	}
}

public class BOJ_14502 {

	public static final int[] dx = {-1,1,0,0};
	public static final int[] dy = {0,0,-1,1};
	public static int n,m;
	public static int[][] oriMap;
	public static int[][] virusMap;
	public static int max = Integer.MIN_VALUE;
	public static Queue<Node> queue;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		
		n = sc.nextInt();
		m = sc.nextInt();
		
		oriMap = new int[n][m];
		
		for(int i=0;i<n;i++) {
			for(int j=0;j<m;j++) {
				oriMap[i][j] = sc.nextInt();
			}
		}
		
		//벽은 (0,0)부터 돌면서 완전탐색해서 세워야하기 때문에 처음은 0으로 설정
		dfs(0);
		
		System.out.println(max);
		
	}
	
	//벽 세우는거에서 막혀서 어떻게 해야하는지 찾아봄.
	//벽을 세워야 하는데 벽을 세우는 건 백트래킹을 이용해야 함.
	/**
	 * 백트래킹이란: 해를 찾는 도중 해가 아니어서 막히면, 되돌아가서 다시 해를 찾는 방법
	 * -> 최적화 문제와 결정 문제를 푸는 방법이 됨
	 * 
	 * DFS와 백트래킹의 관계: DFS는 모든 방법을 탐색하지만, 여기에 조건문을 추가하여 일정 조건에서는 가지치기를 수행하여 백트래킹으로 구현할 수 있다.
	 */
	
	//벽을 세워야하는데 벽은 완전탐색으로 어디에 세울지 정해야하고 + 백트래킹도 해야한다.
	public static void dfs(int wall) {
		if(wall == 3) {
			bfs();
			return;
		}
		
		//완전 탐색을 진행하면서
		for(int i=0;i<n;i++) {
			for(int j=0;j<m;j++) {
				//만약 빈칸이면
				if(oriMap[i][j] == 0) {
					//해당 위치에 벽 세워주고
					oriMap[i][j] = 1;
					//dfs재귀
					dfs(wall+1);
					//0으로 돌려놔야 나중에 다시 벽을 세울 수 있음
					oriMap[i][j]=0;
				}
			}
		}
	}
	
	
	//바이러스는 bfs를 이용해서 찾는게 더 효율적 => 미로나 최단거리를 찾을 떄는 bfs가 더 유용하다.
	public static void bfs() {
		queue = new LinkedList<Node>();
		
		/**
		 * 벽이 세개가 만들어질 때마다 bfs를 돌면서 다 계산을 해줘야하는데
		 * originalMap에서 한번에 해보려니까 도저히 안되서 그냥 복사해서 사용하는 map이 virusMap
		 */
		virusMap = new int[n][m];
		
		/**
		 * 나는 일반적인 bfs였으면 main에서 해당 점을 queue에 다 넣고 bfs에 node를 넣어서 하는걸 좋아하는데, 여기는 dfs를 하고 그 조건에 따라 bfs를 실행해야 해서 그냥 여기서 돌면서 넣고 빼고 하는게 나을 것 같음.
		 */
		
		for(int i=0;i<n;i++) {
			for(int j=0;j<m;j++) {
				//기존 맵 복사해주고
				virusMap[i][j] = oriMap[i][j];
				
				//바이러스면 queue에 바로 담아주기
				if(virusMap[i][j]==2) {
					queue.offer(new Node(i,j));
				}
			}
		}
		
		//for문을 나오게 되면 queue에 바이러스가 있는 곳까지 다 넣어줬고
		//queue가 빌때까지 돌면서
		while(!queue.isEmpty()) {
			Node node = queue.poll();
			int cx = node.x;
			int cy = node.y;
			
			for(int d=0;d<4;d++) {
				int nx = cx+dx[d];
				int ny = cy+dy[d];
				
				//경계값 체크
				if(nx<0 || ny <0 || nx>=n || ny>=m) continue;
				
				//해당 위치가 빈칸이면
				if(virusMap[nx][ny]==0) {
					//바이러스 퍼지고
					virusMap[nx][ny]=2;
					//그 위치 queue에 넣어주고
					queue.offer(new Node(nx,ny));
				}
			}
		}
		
		int blankCnt = 0;
		for(int i=0;i<n;i++) {
			for(int j=0;j<m;j++) {
				if(virusMap[i][j]==0) {
					blankCnt++;
				}
			}
		}
		
		max = Math.max(blankCnt, max);
		
	}

}
