package kimyongjun.group;
/**
 * 풀이 시작 : 17:58
 * 풀이 완료 : 18:53
 * 풀이 시간 : 55분
 *
 * 문제 해석
 * 두 전봇대 A와 B 사이에 여러 개의 전깃줄이 있다.
 * 몇 개의 전깃줄을 없애 교차하는 전깃줄이 없도록 해야 한다.
 *
 * 구해야 하는 것
 * 모든 전깃줄이 교차하지 않기 위해 없애야 하는 전깃줄의 최소 개수
 * 없애야 하는 전깃줄의 A전봇대 인덱스 번호
 *
 * 문제 입력
 * 첫 줄 : 두 전봇대 사이의 전깃줄의 개수 M
 * 둘째 줄 ~ N개의 줄 : A전봇대와 연결되는 위치, B전봇대와 연결되는 위치가 공백을 두고 주어짐
 *
 * 제한 요소
 * 1 <= N <= 100_000
 * 연결 위치 M, 1 <= M <= 500_000
 * 같은 위치에는 하나의 전깃줄만 연결 가능
 *
 * 생각나는 풀이
 * LIS(가장 긴 증가하는 부분수열) 문제
 * 제한시간 1초이고 전깃줄의 개수 N = 최대 10만이므로 dp로 풀면 O(N^2) = 10_000_000_000 (10억) 시간초과
 * O(NlogN) 방식의 LIS 풀이 => lower-bound 이분탐색 사용
 *
 * 놓친 풀이
 * LIS에 저장되는 값은 그 자체가 LIS가 되지는 않는다 => 단순히 LIS의 길이를 알기 위한 것
 *
 * 구현해야 하는 기능
 * 1. 입력에 따른 전깃줄 저장 배열
 * 2. 배열의 한 전봇대 위치 기준 오름차순 정렬
 * 3. lower-bound 이분 탐색으로 현재 값이 들어갈 수 있는 최적 지점 탐색
 * 4. 처음 전깃줄의 개수 - 연결된 전깃줄의 개수 = 없앤 전깃줄의 개수
 * 5. 없애야 하는 전깃줄의 A전봇대 인덱스 저장할 배열
 *
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Stack;
import java.util.StringTokenizer;

public class BOJ_2568 {
    static ElectricWire[] LIS;
    static int[] connectedIdx; // * 5. 없애야 하는 전깃줄의 A전봇대 인덱스 저장할 배열
    static StringBuilder sb = new StringBuilder();

    static class ElectricWire implements Comparable<ElectricWire> { // 전깃줄의 A전봇대 위치, B전봇대 위치를 저장하는 클래스
        int aIdx, bIdx;

        public ElectricWire(int aIdx, int bIdx) {
            this.aIdx = aIdx;
            this.bIdx = bIdx;
        }

        @Override
        public int compareTo(ElectricWire o) { // 정렬을 위해 정렬 기준 Override
            if (this.aIdx == o.aIdx) return this.bIdx - o.bIdx;
            return this.aIdx - o.aIdx;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int N = Integer.parseInt(br.readLine());
        ElectricWire[] electricWires = new ElectricWire[N]; // * 1. 입력에 따른 전깃줄 저장 배열

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int aIdx = Integer.parseInt(st.nextToken());
            int bIdx = Integer.parseInt(st.nextToken());

            electricWires[i] = new ElectricWire(aIdx, bIdx);
        }

        Arrays.sort(electricWires); // * 2. 배열의 한 전봇대 위치 기준 오름차순 정렬

        LIS = new ElectricWire[N + 1]; // LIS[i] = LIS의 길이가 i가 될 때 i번째 수로 가능한 수 중 최솟값
        connectedIdx = new int[N]; // connectedIdx[i] = 정렬된 전깃줄 electricWires[i]가 가능한 LIS 길이
        int length = 1; // LIS의 초기값 설정
        LIS[1] = electricWires[0]; // LIS의 초기값 설정
        connectedIdx[0] = 1; // 첫 번째 전깃줄은 LIS 길이 1

        for (int i = 1; i < N; i++) { // A인덱스 기준 오름차순 정렬 된 전깃줄
            if (LIS[length].bIdx < electricWires[i].bIdx) { // 가장 긴 LIS에서 최댓값보다 현재 값이 크다면 LIS의 뒤에 붙일 수 있음
                LIS[++length] = electricWires[i]; // LIS 길이 증가 후 인덱스에 현재 전깃줄 정보 저장
                connectedIdx[i] = length; // 현재 인덱스의 LIS는 length
            } else { // 그렇지 않다면 이분 탐색 lower-bound로 삽입 가능한 위치 탐색
                int idx = binarySearchLowerBound(length, electricWires[i]); // 최적의 인덱스
                LIS[idx] = electricWires[i];
                connectedIdx[i] = idx; // idx가 3이라면 electricWires[i]에 해당하는 전깃줄이 만들 수 있는 LIS의 최대 길이는 3이라는 것
            }
        }

        buildAnswer(N, electricWires, length); // 정답 출력용 메서드
        System.out.println(sb);
    }

    // * 3. lower-bound 이분 탐색으로 현재 값이 들어갈 수 있는 최적 지점 탐색
    private static int binarySearchLowerBound(int hi, ElectricWire e) {
        int lo = 1, value = e.bIdx; // 현재 전깃줄의 B 인덱스

        /*
        lower-bound = 이분 탐색의 응용으로,
        일반적인 이분 탐색은 내가 찾고자 하는 값의 인덱스를 정확하게 찾는 것이 목적이지만
        lower-bound는 내가 찾고자 하는 값 이상의 값이 처음으로 나오는 인덱스를 찾는 것이 목적이다.
         */
        while (lo < hi) {
            int mid = (lo + hi) / 2;

            if (LIS[mid].bIdx < value) {
                lo = mid + 1;
            } else hi = mid;
        }

        return lo; // lower-bound에서 리턴값은 lo나 hi나 상관 없다. 항상 동일
    }

    private static void buildAnswer(int N, ElectricWire[] electricWires, int length) {
        // * 4. 처음 전깃줄의 개수 - 연결된 전깃줄의 개수 = 없앤 전깃줄의 개수
        sb.append(N - length).append('\n'); // 제거해야 할 전깃줄의 개수

        Stack<Integer> stack = new Stack<>(); // 뒤에서부터 탐색하지만 출력은 오름차순으로 해야 하므로 Stack 사용
        for (int i = N - 1, len = length; i >= 0; i--) {
            if (connectedIdx[i] == len) { // 현재 인덱스의 전깃줄이 만들 수 있는 LIS 길이가 최대 길이인 len와 같다면
                len--; // 최대 길이를 1 감소시킨다 => 현재 인덱스 전깃줄은 제거하지 않는다는 의미
            } else stack.push(electricWires[i].aIdx); // 그렇지 않다면 현재 인덱스 전깃줄을 제거하고 스택에 담는다
        }

        while (!stack.isEmpty()) { // 역순으로 탐색했으므로 스택에서 하나씩 pop하여 StringBuilder에 담는다
            sb.append(stack.pop()).append('\n');
        }
    }

}
