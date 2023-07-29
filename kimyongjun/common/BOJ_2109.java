package kimyongjun.common;
/**
 * 풀이 시작 : 22:02
 * 풀이 완료 : 22:33
 * 풀이 시간 : 31분
 *
 * 문제 해석
 * n개의 대학에서 d일안에 강연을 하면 p만큼 강연료 지불한다고 할 때
 * 가장 돈을 많이 벌 수 있는 경우를 구해야 한다
 *
 * 구해야 하는 것
 * 최대로 벌 수 있는 금액
 *
 * 문제 입력
 * 첫째 줄 : 대학의 수 n
 * 둘째 줄 ~ n개의 줄 : 강연료 p와 데드라인 d가 공백을 두고 주어짐
 *
 * 제한 요소
 * 0 <= n <= 10_000
 * 0 <= d <= 10_000
 * 1 <= p <= 10_000
 * 강연은 하루에 하나만 가능
 * 각 대학에서 제시하는 d와 p는 다 다를 수 있음
 *
 * 생각나는 풀이
 * 시작 날짜는 모두 같지만 종료 날짜가 모두 다름
 * 앞에서부터 풀기 어려움 -> 뒤에서부터 탐색해야 함 -> 날짜 내림차순 정렬
 * 가능한 강의 = 데드라인이 현재 날짜보다 큰 경우
 * 가능한 강의가 여러 개일 때 어떻게 고를 것인가?
 * => 가장 비싼 강의를 해야 함. -> 여러 개 중 최댓값을 뽑는 자료구조 : 우선순위 큐(최대 힙) 사용
 *
 * 구현해야 하는 기능
 * 1. 가장 데드라인이 긴 날부터 1일까지 날짜 내림차순으로 탐색
 * 2. 현재 날짜보다 데드라인이 긴 강의인 경우 우선순위 큐에 삽입
 * 3. 우선순위 큐의 정렬 기준은 강의료 내림차순
 * 4. 우선순위 큐에서 가장 위의 값을 뽑은 것이 현재 날짜에 하는 강의
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BOJ_2109 {
    static class Lecture { // 강의료와 마감일을 저장하는 클래스
        int pay, deadline;

        public Lecture(int pay, int deadline) {
            this.pay = pay;
            this.deadline = deadline;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int N = Integer.parseInt(br.readLine());
        Lecture[] lectures = new Lecture[N];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int pay = Integer.parseInt(st.nextToken());
            int deadline = Integer.parseInt(st.nextToken());

            lectures[i] = new Lecture(pay, deadline);
        }
        // * 1. 가장 데드라인이 긴 날부터 1일까지 날짜 내림차순으로 탐색하기 위해 내림차순 정렬
        Arrays.sort(lectures, (x, y) -> Integer.compare(y.deadline, x.deadline)); // 정렬 방식은 멋있게 람다 사용

        int tot = 0; // 총 수입

        // * 3. 우선순위 큐의 정렬 기준은 강의료 내림차순
        PriorityQueue<Integer> pq = new PriorityQueue<>((x, y) -> y - x); // 정렬 방식은 멋있게 람다 사용

        // 강의의 수가 0일 수도 있기 때문에 N != 0인 경우에만 탐색 (예외처리 안하면 ArrayIndexOutOfBounds 에러 발생)
        for (int nowDay = (N != 0) ? lectures[0].deadline : 0, lectureIdx = 0; nowDay >= 1; nowDay--) {
            // * 2. 현재 날짜보다 데드라인이 긴 강의인 경우 우선순위 큐에 삽입
            while (lectureIdx < N && lectures[lectureIdx].deadline >= nowDay) {
                pq.offer(lectures[lectureIdx++].pay);
            }

            // * 4. 우선순위 큐에서 가장 위의 값을 뽑은 것이 현재 날짜에 하는 강의
            if (!pq.isEmpty()) {
                tot += pq.poll();
            }
        }

        System.out.println(tot);
    }
}
