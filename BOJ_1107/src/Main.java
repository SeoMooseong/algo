import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * BOJ 17144
 */
public class Main {
	static int dst; //최종 채널
	static int current; //현재 채널
	static boolean[] error;	//고장난 채널
	static int amount; //고장 채널 갯수
	static boolean[] visited;
	static boolean[] channel;
	static int num;
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		error = new boolean[10];
		channel = new boolean[1000000 + 1];
		current = 100;
		num = Integer.MAX_VALUE;
		
		dst = Integer.parseInt(br.readLine());
		amount = Integer.parseInt(br.readLine());
		
		if(amount > 0){
			StringTokenizer st = new StringTokenizer(br.readLine());
			for(int i = 0; i < amount; i++){
				error[Integer.parseInt(st.nextToken())] = true; //true => 고장
			}
		}
		
		check();
		select();
		
		//위, 아래 이동 시 버튼 입력 횟수와 비교
		if(Math.abs(dst - current) < num){
			num = Math.abs(dst - current);
		}
		
//		System.out.println(answer + " / " + num);
		System.out.println(num);
	}
	
	// 모든 채널 중 접근 가능한 수(false), 접근 불가능한 수(true) 판별
	static void check(){
		String tmp;
		for(int i = 0; i <= 1000000; i++){
			tmp = i + "";
			for(int j = 0; j < tmp.length(); j++){
				if(error[tmp.charAt(j)-48]){
					channel[i] = true; // 사용하지 못하는 번호
					break;
				}
			}
		}
	}
	
	//선택 가능한 번호 중 가장 가까운 수 선별
	static void select(){
		int tmp;
		for(int i = 0; i <= 1000000; i++){
			if(!channel[i]){
				tmp = Math.abs(dst - i) + (i + "").length();
				if(num > tmp){
					num = tmp;
				}
			}
		}
	}
}
