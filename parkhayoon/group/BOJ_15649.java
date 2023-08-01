package algo.week2.group;

/*
P 15649
길이가 M인 수열을 모두 구하는 프로그램
1부터 N까지 자연수 중에서 중복 없이 M개를 고른 수열

m = 1 -> 배열 수 = n = nP1 = n!/(n-1)!
m = 2 -> 배열 수 = n(n-1) = nP2 = n!/(n-2)!
m = 3 -> 배열 수 = n(n-1)(n-2) = nP3 = n!/(n-2)!
...
총 배열 수 = nPr = n!/(n-m)!

ex)
n, m = 4, 4
1 2 3 4
1 2 4 3
1 3 2 4
1 3 4 2
1 4 2 3
1 4 3 2
2 1 3 4
...

출력 결과를 회전해보자
1 1 1 1 1 1 2 ...
2 2 3 3 4 4 1 ...
3 4 2 4 2 3 3 ...
4 3 4 2 3 2 4 ...
m회 중첩된 for문 출력?
for문이 변동성을 가짐 - 무조건 재귀
 */
import java.util.Scanner;
// 수업 학습 내용: 재귀(2023.08.01.)
public class BOJ_15649 {

    static int n, m;
    static int[] numbers = new int[9]; // 순열 저장 배열
    static boolean[] isSelected = new boolean[9]; // flag 배열

    // 재귀로 풀어보기
    static void perm(int cnt) {
        if (cnt==m+1) { // 출력하려는 범위 넘음 - 남은 배열 출력
            for(int i=1; i<=m; i++)
                System.out.print(numbers[i]+" ");
            System.out.println();
        }
        else {
            for(int i=1; i<=n; i++) {
                if (isSelected[i]) continue; // 만약 이미 앞에 있는 숫자라면 스킵
                numbers[cnt] = i; // 없었으면 현재 자리에 추가
                isSelected[i] = true; // 추가했으므로 고름 숫자로 체크
                perm(cnt+1); // 그 다음 자리수로 가서 다시 실행
                isSelected[i] = false;
            }
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        m = sc.nextInt();

        perm(1);
    }
}
