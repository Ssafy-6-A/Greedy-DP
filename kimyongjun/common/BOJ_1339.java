package kimyongjun.common;
/**
 * 풀이 시작 : 10:52
 * 풀이 완료 : 11:36
 * 풀이 시간 : 44분
 *
 * 문제 해석
 * 알파벳 대문자로 이루어진 N개의 단어가 주어질 때
 * 알파벳과 숫자 0~9를 1대1 매칭하여 N개의 수를 더해야 함
 *
 * 구해야 하는 것
 * N개의 수를 더했을 때의 최댓값
 *
 * 문제 입력
 * 첫째 줄 : 단어의 개수 N
 * 둘째 줄 ~ N개 줄 : 단어
 *
 * 제한 요소
 * 1 <= N <= 10
 * 단어는 알파벳 대문자로 이루어짐
 * 모든 단어에 포함되어 있는 알파벳은 최대 10개 => 0~9까지 매칭해야 하므로
 * 수의 최대 길이는 8 => 단어가 가능한 가장 큰 수 : 99999999
 * 서로 다른 문자는 서로 다른 수
 *
 * 생각나는 풀이
 * 1. 각 자릿수에서 알파벳의 등장 횟수를 카운트
 * 2. 각각의 알파벳마다 (현재 자릿수 등장 횟수) * (10^자릿수)를 수행
 * 2-1. 예시) ABC + BCDE 라면 각각의 알파벳으로 묶어 아래와 같이 표현 가능
 *      100A + 1010B + 101C + 10D + E
 *      알파벳에 곱한 값이 가장 큰 순서대로 9~0 매칭한다면
 *      A = 7, B = 9, C = 8, D = 6, E = 5
 *      계산해보면 700 + 9090 + 808 + 60 + 5 = 10663이 가장 큰 값
 * 3. 2번에서 나온 결과값을 내맄차순으로 정렬
 * 3. 순서대로 9부터 0까지 할당
 * 4. 합 계산
 *
 * 구현해야 하는 기능
 * 1. 입력받은 단어 저장 배열 (필요 없었음)
 * 2. 자릿수 별 카운트를 할 2차원 배열 생성 digitCount[입력받을 수 있는 최대 길이 = 8][알파벳의 수 26]
 * 3. 알파벳의 계수를 모두 구해 내림차순으로 정렬
 * 4. 첫 값부터 수를 할당하며 값 구함
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;

public class BOJ_1339 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[][] digitCount = new int[8][26]; // * 2. 자릿수 별 카운트를 할 2차원 배열 생성 digitCount[입력받을 수 있는 최대 길이 = 8][알파벳의 수 26]
        int[] digit = new int[8]; // 10의 자릿수 미리 전처리하기 위한 배열
        for (int i = 7, value = 1; i >= 0; i--) { // digit = {10^7, 10^6, 10^5, 10^4, 10^3, 10^2, 10^1, 10^0}
            digit[i] = value;
            value *= 10;
        }

        HashSet<Integer> alphabetIdx = new HashSet<>(); // 알파벳의 수 26을 전체탐색하지 않고 사용한 알파벳만 탐색하기 위해 알파벳 인덱스 저장하는 Set
        for (int i = 0; i < N; i++) {
            String word = br.readLine(); // 현재 단어
            for (int j = 0; j < word.length(); j++) {
                // 수를 더하기 위해서는 자릿수를 맞춰야 하기 때문에 맨 뒤 인덱스부터 파싱
                // 배열에 넣기 위해 0 ~ 25의 값을 가지는 인덱스로 변경
                int nowAlphabet = word.charAt(word.length() - 1 - j) - 'A';
                digitCount[7 - j][nowAlphabet]++; // digit[자릿수(7 = 1의자리, 0 = 10^7자리)][알파벳 인덱스]
                alphabetIdx.add(nowAlphabet); // 나온 알파벳 인덱스를 저장
            }
        }

        // * 3. 알파벳의 계수를 모두 구해 내림차순으로 정렬
        int[] coefficient = new int[alphabetIdx.size()]; // 계수만 저장하는 배열
        int coIdx = 0; // 배열에 저장하기 위해 사용하는 인덱스
        for (int idx : alphabetIdx) { // 현재 나온 알파벳
            for (int i = 7; i >= 0; i--) { // 1의자리부터 (10^7자리부터 해도 상관 없음)
                coefficient[coIdx] += digitCount[i][idx] * digit[i]; // 현재 카운트를 자릿수에 곱해서 더함
            }
            coIdx++; // 현재 인덱스 저장 끝났으므로 인덱스 한칸 이동
        }
        Arrays.sort(coefficient); // Primitive 타입은 Collections.reverseOrder 사용 불가

        int tot = 0;
        // 오름차순 정렬되었으므로 맨 뒤 인덱스부터 탐색
        for (int i = coefficient.length - 1, num = 9; i >= 0; i--) { // * 4. 첫 값부터 큰 수를 할당하며 값 구함
            tot += coefficient[i] * num--; // 가장 큰 계수 * 가장 큰 값을 모두 더한 것이 가장 큰 합
        }

        System.out.println(tot);
    }
}
