package kimyongjun.common;
/**
 * 풀이 시작 : 21:36
 * 풀이 완료 : 22:02
 * 풀이 시간 : 26분
 *
 * 문제 해석
 * n개의 정수로 이루어진 수열이 주어질 때, 연속된 몇 개의 수를 선택해서 더한 합을 가장 크게 만들어야 한다.
 * 수열에서 수를 하나 제거할 수 있다.
 *
 * 구해야 하는 것
 * 수열에서 가장 큰 연속합의 값
 *
 * 문제 입력
 * 첫째 줄 : 정수 n
 * 둘째 줄 : n개의 정수로 이루어진 수열
 *
 * 제한 요소
 * 1 <= n <= 100_000
 * 수열의 i번째 값 = S[i], -1000 <= S[i] <= 1000
 *
 * 생각나는 풀이
 * dp 2차원 배열, dp[제거 여부][현재 인덱스] = 처음 ~ 현재 인덱스까지 연속합의 최댓값
 * 현재 인덱스는 반드시 포함
 *
 * 숫자 제거를 생각하지 않았을 때
 * 바로 이전값의 연속합 최댓값이 음수라면 => 현재 값부터 시작하는 게 좋음
 * 그렇지 않고 양수라면 => 기존 최댓값에 현재 값을 더하는 게 좋음
 * dp[0][nowIdx] = Math.max(dp[0][nowIdx - 1], 0) + S[nowIdx]
 *
 * 하나의 수를 제거할 수 있기 때문에
 * 현재 제거한 수가 1개라면 => 이전에 하나의 수를 제거한 최댓값 or 현재 인덱스를 제거한 값 중 큰 값이 dp배열에 저장됨
 * dp[1][nowIdx] = Math.max(dp[1][nowIdx - 1] + S[i], dp[0][nowIdx - 1])
 *
 * 구현해야 하는 기능
 * 위의 문제 풀이를 구현
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_13398 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[] num = new int[N + 1];
        int[][] dp = new int[2][N + 1];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            num[i] = Integer.parseInt(st.nextToken());
        }

        dp[0][0] = dp[0][1] = -1001;
        int max = Integer.MIN_VALUE;
        for (int i = 1; i <= N; i++) {
            dp[0][i] = Math.max(dp[0][i - 1], 0) + num[i];
            dp[1][i] = Math.max(dp[1][i - 1] + num[i], dp[0][i - 1]);

            max = Math.max(max, Math.max(dp[0][i], dp[1][i]));
        }

        System.out.println(max);
    }
}