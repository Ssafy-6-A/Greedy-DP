package algo.week2.common;
// ##########시간 초과 코드 ##########
/*
n개의 정수로 이루어진 임의의 수열
연속된 몇 개의 수를 선택해서 구할 수 있는 합 중 가장 큰 합(단, 수는 한 개 이상 선택, 수열에서 수를 하나 제거 가능 (제거하지 않아도 된다))

ex)
 10, -4, 3, 1, 5, 6, -35, 12, 21, -1 수열
수를 제거하지 않았을 때의 정답은 12+21인 33이 정답이 된다.
만약, -35를 제거한다면, 수열은 10, -4, 3, 1, 5, 6, 12, 21, -1, 정답은 10-4+3+1+5+6+12+21인 54가 된다.

idea
일단 연속합 중 가장 큰 값 구해보기
1) 전체탐색
전체에서 하나씩 빼보면서 최대값 구하기
=> 분명 효율적인 방법 있는데...
가설) 음수를 빼면 무조건 증가한다? - 그런듯?
가설2) 최대 음수를 빼면 무조건 증가한다? - X
100 -1000 -1000 50 -10 80

구상
시간 초과 코드 분석
for문 3개(O(N^2logN))
1) 음수 하나 씩 제거하는 for문(O(N))
2) 제거 없이 최대값 구하는 이중for문(O(NlogN)) - 생략 가능해보임

k에 있는 음수 제거 시 합
a(i,j) = i부터 j까지의 함
a(i,j) = {a(i,k)-arr[k]}+{a(k,j)-arr[k]}
       = a(i,k)+a(k,j)-2*arr[k]
 */

import java.util.Scanner;

public class BOJ_13398 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int[] arr = new int[n];
        for (int i=0; i<n; i++) {
            arr[i]=sc.nextInt();
        }
		/*
		// 시간초과 코드
		int max = -1000;
		for (int removeNum=0; removeNum<n; removeNum++) {
			if (arr[removeNum]>=0) continue;
			for (int i=0; i<n; i++) {
				if (i==removeNum) continue;
				int sum = 0;
				for (int j=i; j<n; j++) {
					if (j==removeNum) continue;
					sum += arr[j];
					if (sum > max)
						max = sum;
				}
			}

		}
		System.out.println(max);
		*/
		/*
		// a(i,j) = sum from i to j, O(NlogN)
		int[][] sumArr = new int[n][n];
		for(int i=0;i<n;i++) {
			int sum = 0;
			for(int j=i;j<n;j++) {
				sum += arr[j];
				sumArr[i][j] = sum;
			}
		}

		// arr[k] 제거 -> a(i,k)+a(k,j)-2*arr[k]
		int max = -1000; // worst Case
		for (int k=0; k<n; k++) {
			if(arr[k]<0) {
				for(int i=0;i<n;i++) {
					for(int j=i;j<n;j++) {
						int currSum = sumArr[i][k]+sumArr[k][j]-2*arr[k];
						if(max < currSum)
							max = currSum;
					}
				}
			}
		}
		System.out.println(max);
		*/

    }

}
