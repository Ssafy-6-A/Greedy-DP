package kimsuhyeok.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * 각 문자열을 입력받고
 * 길이를 비교해서
 * 길이가 빈 문자열의 가장 앞에 있는 문자부터 가장 큰 값을 부여
 * 부여를 하다 다른 문자열과 길이가 같아지는 시점에서
 * 서로 번갈아가며 문자에 큰값을 부여하면 되지 않을까
 * 
 * 모든 단어에 포함되어 있는 알파벳은 최대 10개
 * 수의 최대 길이는 8
 * 
 * 그렇다면 key는 각 알파벳
 * 나오는 알파벳들을 정리한 후 그 value들을 dictionary형태처럼 해서 value가 가장 큰 key를 선정하여 해당 key에 가장 높은 숫자를 순차적으로 할당
 * ABC+BD의 경우
 * 100*A+20*B+(C+D)이므로 value가 100으로 가장 큰 A에 9, value가 두번째로 큰 B에 8, 나머지 c와 d에 7과 6할당 하는 방식으로
 * 
 * 근데 그럼 Map의 key에는 뭐가 들어가야지?
 * 알파벳이니까 
 * value는 Integer
 */

/**
 * 파이썬 dictionary와 비슷하게 key와 value를 사용하는 map을 사용
 * map의 사용법
 * map.put(key, value) -> hashmap 추가
 * map.remove(key) -> hashmap 삭제
 */

public class BOJ_1339 {
	
	static int n;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		
		Map<Integer, Integer> map = new HashMap<>();
		for(int i=0; i<n; i++) {
			char[] ch = br.readLine().toCharArray();
			for(int j=ch.length-1; j>=0; j--) {
				
				//map에 key에는 현재 문자열 - A값한 Integer값, value에는 현재 key가 나온 횟수값
				map.put(ch[j]-'A', map.getOrDefault(ch[j]-'A',0)+(int)Math.pow(10, ch.length-1-j));
//				System.out.println(map);
			}
		}
		
		List<Integer> keyList = new ArrayList<>(map.keySet());
//		System.out.println(keyList);
		
		
		//얘는 수업한 내용 찾아봄
		//comparator도 사용할 수 있는데 솔직히 너무 많이 적어야해서 람다씀
		//list를 정렬하는데, map의 value값을 기준으로 내림차순으로 정렬

		Collections.sort(keyList, (o1,o2) -> (map.get(o2) - map.get(o1)));
//		System.out.println(keyList);
		
		int sum=0;
		
		//9부터 할당해주기
		int num = 9;
		for(int key: keyList) {
			sum += map.get(key)*num;
			num--;
		}
		System.out.println(sum);
		
	}

}
