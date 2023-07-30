package kimyongjun.common;
/**
 * 풀이 시작 : 13:58
 * 풀이 완료 : 15:40
 * 풀이 시간 : 102분
 *
 * 문제 해석
 * 구슬 탈출 게임
 * 직사각형 보드에 빨간 구슬과 파란 구슬을 하나씩 넣어 빨간 구슬을 구멍을 통해 빼내는 게임
 * 파란 구슬이 구멍에 들어가지 않은 상태로 빨간 구슬을 구멍을 통해 빼내야 함
 * 왼쪽으로 기울이기, 오른쪽으로 기울이기, 위쪽으로 기울이기, 아래쪽으로 기울이기의 4가지 동작만 가능
 * 공은 동시에 움직이며 빨간 구슬과 파란 구슬이 동시에 구멍에 빠지면 실패
 * 빨간 구슬과 파란 구슬은 같은 칸에 있을 수 없음
 *
 * 구해야 하는 것
 * 빨간 구슬을 구멍을 통해 빼내는데 걸리는 최소 횟수
 * 만약 10번 이하로 움직여서 빼내지 못하는 경우 -1 출력
 *
 * 문제 입력
 * 첫 번째 줄 : 보드의 세로 N, 가로 M
 * 두 번째 줄 ~ N개의 줄 : 보드의 모양
 * '.' = 빈 칸
 * '#' = 벽
 * 'O' = 구멍
 * 'R' = 빨간 구슬
 * 'B' 파란 구슬
 *
 * 제한 요소
 * 3 <= N, M <= 10
 * 'R' 과 'B'는 항상 1개
 * 보드의 테두리는 전부 '#'
 * 빨간 구슬과 파란 구슬은 같은 위치에 있을 수 없음
 * 하나의 행동에서 빨간 구슬과 파란 구슬이 구멍에 둘 다 들어간다면 실패
 *
 * 생각나는 풀이
 * 최대 10번의 움직임 * 상하좌우 4방향 = 4^10 = 104만
 * 움직임의 각각의 방향대로 시뮬레이션
 * 탈출 가능하다면 그 움직임 횟수와 현재 가장 작은 횟수를 비교하여 작은값으로 갱신
 * 전체 가능한 횟수를 탐색했으멩도 탈출 불가능하면 -1 출력
 *
 * 구현해야 하는 기능
 * 1. 보드의 모양 저장하는 배열
 * 2. 위치 x,y좌표 저장하는 클래스
 * 3. 방향을 저장할 델타배열 (불필요)
 * 4. 각 방향 움직임 기능
 * 5. 움직임 방향 전방이 벽이나 다른 구슬인지 체크하는 기능
 * 6. 움직임 방향 전방이 구멍인지 체크하는 기능
 * 6-1. 빨간 구슬만 들어갔는지, 그렇지 않은지 체크하는 기능
 *
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_13460 {
    static int minTime = Integer.MAX_VALUE;
    static char[][] board; //  * 1. 보드의 모양 저장하는 배열
    static int[] directions = new int[10]; // 10번 움직임의 방향 저장
    static Node hole; // 구멍의 위치
    static Node originalRed, originalBlue; // 빨간 공과 파란 공의 초기 위치
    static class Node { // * 2. 위치 x,y좌표 저장하는 클래스
        int x, y;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        board = new char[N][M];

        for (int i = 0; i < N; i++) {
            board[i] = br.readLine().toCharArray();
            for (int j = 0; j < M; j++) {
                if (board[i][j] == 'O') {
                    hole = new Node(i, j);
                } else if (board[i][j] == 'R') {
                    originalRed = new Node(i, j);
                    board[i][j] = '.';
                } else if (board[i][j] == 'B'){
                    originalBlue = new Node(i, j);
                    board[i][j] = '.';
                }
            }
        }

        backTracking(0); // 4방향 * 10번 모든 경우의 수 탐색
        System.out.println(minTime == Integer.MAX_VALUE ? -1 : minTime);
    }

    private static void backTracking(int depth) {
        if (depth == 10) {
            minTime = Math.min(minTime, moveSimulation());
            return;
        }

        for (int i = 0; i < 4; i++) {
            directions[depth] = i;
            backTracking(depth + 1);
        }
    }

    private static int moveSimulation() {
        Node red = new Node(originalRed.x, originalRed.y); // 현재 시뮬레이션에서 사용할 임시 공
        Node blue = new Node(originalBlue.x, originalBlue.y); // 현재 시뮬레이션에서 사용할 임시 공

        for (int i = 0; i < 10; i++) { // 10번 움직임
            boolean gameEnd; // 10번 이전에 공이 하나라도 구멍에 들어갔는지 체크
            if (directions[i] == 0) {
                gameEnd = moveTop(red, blue);
            } else if (directions[i] == 1) {
                gameEnd = moveBottom(red, blue);
            } else if (directions[i] == 2) {
                gameEnd = moveLeft(red, blue);
            } else {
                gameEnd = moveRight(red, blue);
            }

            if (gameEnd) { // 공이 이번 움직임에 들어갔다면
                if (isEscaped(red, blue)) { // 빨간공만 들어갔는지 체크
                    return i + 1;
                } else return Integer.MAX_VALUE;
            }
        }

        return Integer.MAX_VALUE;
    }

    private static boolean isEscaped(Node red, Node blue) { // * 6-1. 빨간 구슬만 들어갔는지, 그렇지 않은지 체크하는 기능
        if (red.x == hole.x && red.y == hole.y) {
            if (blue.x == hole.x && blue.y == hole.y) return false;
            else return true;
        } else return false;
    }

    // * 4. 각 방향 움직임 기능
    private static boolean moveTop(Node red, Node blue) {
        Node firstBall, secondBall; // 움직이는 방향에 따라 움직이는 곳 가까운 공이 먼저 움직이게
        if (red.x > blue.x) { // 위쪽 방향으로 움직이므로 위쪽에 가까운 공을 먼저 움직이게 함
            firstBall = blue;
            secondBall = red;
        } else {
            firstBall = red;
            secondBall = blue;
        }

        while (true) {
            int nextX = firstBall.x - 1; // 델타배열 없이 바로 x값 - 1해줌 (윗방향)

            if (board[nextX][firstBall.y] == 'O') { // 앞이 구멍이라면
                firstBall.x = nextX; // 구멍으로 이동 후 반복문 탈출
                break;
            } else if (board[nextX][firstBall.y] == '.') { // 이동 가능하면 이동
                firstBall.x = nextX;
            } else break;
        }

        while (true) {
            int nextX = secondBall.x - 1;

            if (board[nextX][secondBall.y] == 'O') { // * 6. 움직임 방향 전방이 구멍인지 체크하는 기능
                secondBall.x = nextX; // 구멍으로 이동 후 반복문 탈출
                break;
            } else if (firstBall.y == secondBall.y && firstBall.x == nextX) { // 방향 전방이 다른 구슬인지 체크
                break;
            } else if (board[nextX][secondBall.y] == '.') { // 이동 가능하다면 이동
                secondBall.x = nextX;
            } else break; // 방향 전방이 벽인지 체크
        }

        // 구멍에 하나라도 들어갔다면 반복문 종료시키기 위해 true 반환, 그렇지 않다면 반복문 진행 위해 false 반환
        if ((firstBall.x == hole.x && firstBall.y == hole.y) || (secondBall.x == hole.x && secondBall.y == hole.y)) return true;
        else return false;
    }

    // * 4. 각 방향 움직임 기능
    private static boolean moveBottom(Node red, Node blue) {
        Node firstBall, secondBall;
        if (red.x < blue.x) {
            firstBall = blue;
            secondBall = red;
        } else {
            firstBall = red;
            secondBall = blue;
        }

        while (true) {
            int nextX = firstBall.x + 1;

            if (board[nextX][firstBall.y] == 'O') {
                firstBall.x = nextX;
                break;
            } else if (board[nextX][firstBall.y] == '.') {
                firstBall.x = nextX;
            } else break;
        }

        while (true) {
            int nextX = secondBall.x + 1;

            if (board[nextX][secondBall.y] == 'O') {
                secondBall.x = nextX;
                break;
            } else if (firstBall.y == secondBall.y && firstBall.x == nextX) {
                break;
            } else if (board[nextX][secondBall.y] == '.') {
                secondBall.x = nextX;
            } else break;
        }

        if ((firstBall.x == hole.x && firstBall.y == hole.y) || (secondBall.x == hole.x && secondBall.y == hole.y)) return true;
        else return false;
    }

    // * 4. 각 방향 움직임 기능
    private static boolean moveLeft(Node red, Node blue) {
        Node firstBall, secondBall;
        if (red.y > blue.y) {
            firstBall = blue;
            secondBall = red;
        } else {
            firstBall = red;
            secondBall = blue;
        }

        while (true) {
            int nextY = firstBall.y - 1;

            if (board[firstBall.x][nextY] == 'O') {
                firstBall.y = nextY;
                break;
            } else if (board[firstBall.x][nextY] == '.') {
                firstBall.y = nextY;
            } else break;
        }

        while (true) {
            int nextY = secondBall.y - 1;

            if (board[secondBall.x][nextY] == 'O') {
                secondBall.y = nextY;
                break;
            } else if (firstBall.x == secondBall.x && firstBall.y == nextY) {
                break;
            } else if (board[secondBall.x][nextY] == '.') {
                secondBall.y = nextY;
            } else break;
        }

        if ((firstBall.x == hole.x && firstBall.y == hole.y) || (secondBall.x == hole.x && secondBall.y == hole.y)) return true;
        else return false;
    }

    // * 4. 각 방향 움직임 기능
    private static boolean moveRight(Node red, Node blue) {
        Node firstBall, secondBall;
        if (red.y < blue.y) {
            firstBall = blue;
            secondBall = red;
        } else {
            firstBall = red;
            secondBall = blue;
        }

        while (true) {
            int nextY = firstBall.y + 1;

            if (board[firstBall.x][nextY] == 'O') {
                firstBall.y = nextY;
                break;
            } else if (board[firstBall.x][nextY] == '.') {
                firstBall.y = nextY;
            } else break;
        }

        while (true) {
            int nextY = secondBall.y + 1;

            if (board[secondBall.x][nextY] == 'O') {
                secondBall.y = nextY;
                break;
            } else if (firstBall.x == secondBall.x && firstBall.y == nextY) {
                break;
            } else if (board[secondBall.x][nextY] == '.') {
                secondBall.y = nextY;
            } else break;
        }

        if ((firstBall.x == hole.x && firstBall.y == hole.y) || (secondBall.x == hole.x && secondBall.y == hole.y)) return true;
        else return false;
    }
}
