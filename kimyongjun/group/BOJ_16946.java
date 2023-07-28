package kimyongjun.group;
/**
 * 풀이 시작 : 22:30
 * 풀이 완료 : 23:30
 * 풀이 시간 : 60분
 *
 * 문제 해석
 * N * M의 맵이 있다. 맵에서 0은 이동 가능한 곳, 1은 벽이다.
 * 벽에 대해서 다음을 구해야 한다.
 * - 벽을 부수고 이동할 수 있는 곳으로 변경
 * - 그 위치에서 이동할 수 있는 칸의 개수를 셈
 * 한 칸에서 이동 가능한 칸은 상하좌우 인접한 칸
 *
 * 구해야 하는 것
 * 각각의 벽을 이동 가능한 칸이라고 가정했을 때 그 위치에서 이동 가능한 칸의 수를 10으로 나눈 나머지
 *
 * 문제 입력
 * 첫째 줄 : 행의 수 N, 열의 수 M
 * 둘째 줄부터 N개의 줄 : 맵의 상태를 나타내는 M개의 숫자
 *
 * 제한 요소
 * 1 <= N <= 1000
 * 1 <= M <= 1000
 *
 * 생각나는 풀이
 * 벽이 있는 모든 위치를 저장 후 하나씩 꺼내며 해당 위치에서 그래프 탐색 => 최악의 상황에서 시간 초과로 사용 불가
 * 벽이 아닌 지점을 미리 그룹핑(그래프 탐색) 후 모든 벽에서 인접한 칸의 그룹 구성원 수를 더함
 *
 * 구현해야 하는 기능
 * 1. 입력에 따른 2차원 배열 저장
 * 2. 속한 그룹을 저장하는 2차원 배열
 * 3. 그룹핑 기능
 * 4. 벽의 위치를 저장할 자료구조
 * 5. 사방탐색용 델타 배열
 * 6. 2차원 배열 인덱스를 저장할 Node 클래스
 * 7. 인접 칸이 같은 그룹일 경우 더하면 안됨 => 중복 처리
 * 8. 배열 범위 벗어나는지 체크
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_16946 {
    static int N, M;
    static int[][] map;
    static int[][] group; // * 2. 속한 그룹을 저장하는 2차원 배열
    static Queue<Node> wallList = new LinkedList<>(); // * 4. 벽의 위치를 저장할 자료구조
    static int[] dx = {-1, 1, 0, 0}; // * 5. 사방탐색용 델타 배열
    static int[] dy = {0, 0, -1, 1}; // * 5. 사방탐색용 델타 배열

    static class Node { // * 6. 2차원 배열 인덱스를 저장할 Node 클래스
        int x, y;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new int[N][M];
        group = new int[N][M];

        // * 1. 입력에 따른 2차원 배열 저장
        for (int i = 0; i < N; i++) {
            String s = br.readLine();
            for (int j = 0; j < M; j++) {
                int now = s.charAt(j) - '0';
                map[i][j] = now;
                if (now == 1) { // 벽이라면
                    wallList.offer(new Node(i, j)); // 벽의 위치를 저장하는 Queue에 현재 위치 추가
                    group[i][j] = -1; // 벽은 그룹번호를 -1로 지정
                }
            }
        }

        HashMap<Integer, Integer> numOfGroupMembers = new HashMap<>(); // key = 그룹번호, value = 그룹 구성원 수를 저장할 HashMap
        numOfGroupMembers.put(-1, 0); // 벽은 그룹 구성원 수 0으로 설정

        // * 3. 그룹핑 기능
        int groupNum = 1; // 그룹 번호
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (group[i][j] == 0) { // 그룹번호 = 0이면 아직 방문하지 않은 빈 공간
                    numOfGroupMembers.put(groupNum, bfs(new Node(i, j), groupNum++)); // 해당 지점부터 그래프 탐색하여 새로운 그룹의 정보를 HashMap에 저장
                }
            }
        }

        HashSet<Integer> nearGroup = new HashSet<>(); // * 7. 인접 칸이 같은 그룹일 경우 더하면 안됨 => 중복 처리 => Set의 특성을 이용
        while (!wallList.isEmpty()) { // 벽을 전부 탐색할 때까지
            nearGroup.clear(); // 재사용하기 위해 Set의 모든 원소 삭제
            Node now = wallList.poll();

            for (int i = 0; i < 4; i++) { // 인접구역 4방탐색
                int nextX = now.x + dx[i];
                int nextY = now.y + dy[i];

                if (!isInRange(nextX, nextY)) continue;
                nearGroup.add(group[nextX][nextY]); // Set은 중복된 자료가 들어가도 1개가 됨
            }

            for (int idx : nearGroup) { // 인접 칸의 그룹들
                map[now.x][now.y] += numOfGroupMembers.get(idx); // 벽의 기본값 1 + 그룹 멤버 수 = 현재 위치에서 이동 가능한 칸 수
            }
            map[now.x][now.y] %= 10; // 문제에서 요구하는 출력은 10으로 나눈 나머지
        }

        // 정답 출력 생성 과정
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                sb.append(map[i][j]);
            }
            sb.append('\n');
        }

        System.out.println(sb);
    }

    // 현재 지점에서 탐색 가능한 칸의 수를 반환 및 현재 탐색한 모든 칸을 하나의 그룹으로 묶는 메서드
    private static int bfs(Node start, int groupNum) {
        Queue<Node> q = new LinkedList<>();
        q.offer(start);

        int cnt = 0;

        while (!q.isEmpty()) {
            Node now = q.poll();
            group[now.x][now.y] = groupNum; // 방문한 모든 칸은 하나의 그룹에 속함
            cnt++; // 방문 칸 수 증가

            for (int i = 0; i < 4; i++) {
                int nextX = now.x + dx[i];
                int nextY = now.y + dy[i];

                if (!isInRange(nextX, nextY) || group[nextX][nextY] != 0) continue;
                group[nextX][nextY] = groupNum;
                q.offer(new Node(nextX, nextY));
            }
        }

        return cnt;
    }

    // * 8. 배열 범위 벗어나는지 체크
    private static boolean isInRange(int x, int y) {
        return x >= 0 && x < N && y >= 0 && y < M;
    }
}
