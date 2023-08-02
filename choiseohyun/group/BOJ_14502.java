package choiseohyun.group;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/** 14502. 연구소
 * 풀이 시간 : 60분
 *  연구소의 크기는 N*M 크기의 직사각형, 바이러스 막기위해 벽 세울것임
 * 빈칸(0), 벽(1)으로 나뉘며 벽은 칸 하나를 가득 차지
 * 일부칸은 바이러슥 존재, 상하좌우 빈칸으로 퍼져나갈 수 있음
 * 새로 세울 벽의 개수는 3개(다세워야함)
 * 결과 : 바이러스가 퍼지지 않는 안전영역의 크기의 최댓값을 구하시오
 *
 * 풀이방법
 * dfs를 통해 3가지 벽을 고르는 모든 경우의수를 완전탐색 하고
 * 3가지 벽에 대하여 bfs를 통해 바이러스를 전이시킨 뒤, 안전영역의 크기를 구한다.
 * 그중에서 가장 큰 안전영역을 리턴한다!
 *
 */

class Node{
    int x,y;
    Node(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "x :"+x+", y :"+y;
    }
}
public class BOJ_14502 {
    static int[][] arr;
    static int[] dx = {0,0,1,-1};
    static int[] dy = {1,-1,0,0};
    static Node[] virus = new Node[10];
    static int answer=Integer.MIN_VALUE;
    private static void dfs(int wall) {
        if(wall == 3) {
            bfs(); // 칸을 세개 선택해서 벽으로 만들었다면 bfs를 통해 안전영역 탐색
            return;
        }
        for(int i=0; i<arr.length; i++){
            for(int j=0; j<arr[0].length; j++){
                if(arr[i][j]==0){
                    arr[i][j] = 1;
                    dfs(wall+1);
                    arr[i][j] = 0; //(1,2,3)이 벽일경우를 bfs()했다면, 곧바로 (1,2,4)를 bfs()할 수 있도록 백트래킹
                }
            }
        }
    }

    // 안전영역 탐색하기 - 바이러스를 퍼트린 뒤 더이상 퍼트릴 수 없으면 영역이 빈칸인곳 리턴
    private static void bfs() {
        Queue<Node> q = new LinkedList<>();

        // arr을 똑같이 복제하여 바이러스 전이 현황을 구현한다.
        //int[][] check = Arrays.copyOf(arr, arr.length); //copyOf는 일차원배열 전용이라서 깊은복사가 안되는듯함!
        int[][] check = new int[arr.length][arr[0].length]; //각 경우의수마다 arr을 깊은복사하여 사용한다.
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                check[i][j] = arr[i][j];
                if (check[i][j] == 2) {
                    q.offer(new Node(i,j));
                }
            }
        }

        while(!q.isEmpty()){
            Node node = q.poll();
            for(int i=0; i<4; i++){
                int nx = node.x+dx[i];
                int ny = node.y+dy[i];
                // 바이러스가 빈칸 방문시 해당칸을 2로 바꾸므로 방문배열 체크가 따로 필요없음. 주위에 0이 있는지만 확인하면 됨
                if(nx>=0 && nx< arr.length && ny>=0 && ny<arr[0].length
                && check[nx][ny] == 0){
                    check[nx][ny] = 2;
                    q.offer(new Node(nx,ny));
                }
            }
        }

        // 바이러스가 퍼진 후 안전영역(빈칸)의 갯수 카운트
        int result = 0;
        for(int i=0; i<arr.length; i++){
            for(int j=0; j<arr[i].length; j++){
                if(check[i][j] == 0) result++;
            }
        }
        answer = Math.max(answer, result);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        arr = new int[n][m];
        int k=0, wall=0;
        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                arr[i][j]=sc.nextInt();
            }
        }
        //벽이 될 수 있는 모든 경우의 수에 대해 완전탐색한다(DFS이용)
        dfs(0);
        System.out.println(answer);
    }

}
