package kimyongjun.common;
/**
 * 풀이 시작 : 21:34
 * 풀이 완료 : 21:48
 * 풀이 시간 : 14분
 *
 * 문제 해석
 * LCS(Longest Common Subsequence)
 * 두 수열이 주어졌을 때 모두의 부분수열이 되는 수열 중 가장 긴 수열
 *
 * 구해야 하는 것
 * LCS의 길이를 찾는 문제
 *
 * 문제 입력
 * 첫 줄과 둘째 줄에 각각 문자열 주어짐
 *
 * 제한 요소
 * 문자열은 알파벳 대문자
 * 문자열의 길이 <= 1000
 *
 * 생각나는 풀이
 * 2차원 메모이제이션 배열을 이용한 dp문제
 * 첫 문자열의 i번째 인덱스 = A[i], 두 번째 문자열의 j번째 인덱스 = B[j]라고 했을 때
 * dp[i][j] = A[i]와 B[j]까지 탐색했을 때 LCS의 길이
 * A[i]와 B[j]가 같은 경우
 * => 각각 문자열의 바로 이전 글자까지 탐색한 경우(dp[i - 1][j - 1])에서 +1만큼 한 값이 dp[i][j]가 됨
 * 즉 dp[i][j] = dp[i - 1][j - 1] + 1
 * A[i]와 B[j]가 다른 경우
 * => dp[i][j] = dp[i - 1][j], dp[i][j - 1] 앞에서부터 순차적으로 탐색하므로 둘 중 하나의 경우에서 파생된다. 두 값중 큰 값을 선택해야 한다.
 * 즉 dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1])
 *
 * 구현해야 하는 기능
 * 풀이에 해당하는 기능을 구현하면 된다.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BOJ_9251 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        char[] A = br.readLine().toCharArray();
        char[] B = br.readLine().toCharArray();

        int[][] dp = new int[A.length + 1][B.length + 1];

        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < B.length; j++) {
                if (A[i] == B[j]) {
                    dp[i + 1][j + 1] = dp[i][j] + 1;
                } else {
                    dp[i + 1][j + 1] = Math.max(dp[i][j + 1], dp[i + 1][j]);
                }
            }
        }

        System.out.println(dp[A.length][B.length]);
    }
}
