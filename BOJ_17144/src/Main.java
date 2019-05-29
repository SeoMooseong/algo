import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
/*
 * BOJ 17144
 */
public class Main {
	
	static int dx[] = {-1, 0, 1, 0};
	static int dy[] = {0, 1, 0, -1};
	
	static int cdx[][] = {{-1,1},{0,0},{1,-1},{0,0}};
	static int cdy[][] = {{0,0},{1,1},{0,0},{-1,-1}};
	
	static int R, C, T; //행, 열, 시간(초)
	static int[][] map;
	static int[][] amount; //미세먼지의 확산량 저장
	static boolean[][] visited; //미세먼지 확산여부 체크
	
	static int[][] cleaner = new int[2][2];
	static int answer;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		T = Integer.parseInt(st.nextToken());
		
		map = new int[R][C];
		amount = new int[R][C];
		
		int cleanerCnt = 0;
		for(int i = 0; i < R; i++){
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < C; j++){
				map[i][j] = Integer.parseInt(st.nextToken());
				if(map[i][j] == -1){
					cleaner[cleanerCnt][0] = i;
					cleaner[cleanerCnt][1] = j;
					cleanerCnt++;
				}
			}
		}
		
		while(T > 0){
			diffusion();
			running(0, cleaner[0][0] + cdx[0][0], cleaner[0][1] + cdy[0][0], 0);
			running(1, cleaner[1][0] + cdx[0][1], cleaner[1][1] + cdy[0][1], 0);
			T--;
		}
		
//		for(int i = 0; i < R; i++){
//			for(int j = 0; j < C; j++){
//				System.out.print(map[i][j] + "\t");
//			}
//			System.out.println();
//		}
		
		for(int i = 0; i < R; i++){
			for(int j = 0; j < C; j++){
				if(map[i][j] > 0){
					answer += map[i][j];
				}
			}
		}
		
		System.out.println(answer);
	}
	
	//미세먼지 확산
	static void diffusion(){
		for(int i = 0; i < R; i++){
			for(int j = 0; j < C; j++){
				if(map[i][j] > 0){
					int cnt = 0;
					for(int d = 0; d < 4; d++){
						int tx = i + dx[d];
						int ty = j + dy[d];
						
						if(tx < 0 || tx >= R || ty < 0 || ty >= C) continue;
						if(map[tx][ty] == -1) continue;
						
						amount[tx][ty] += map[i][j]/5;
						cnt++;
					}
					map[i][j] = map[i][j] - (map[i][j]/5)*cnt;
				}
			}
		}
		
		for(int i = 0; i < R; i++){
			for(int j = 0; j < C; j++){
				if(amount[i][j] != 0){
					map[i][j] += amount[i][j];
					amount[i][j] = 0;
				}
			}
		}
	}
	
	//공기청정기 작동
	static void running(int check, int x, int y, int cnt){
		if(cnt >= 4) return;
		int tx = x + cdx[cnt][check];
		int ty = y + cdy[cnt][check];
		if(ty < 0 || ty >= C || ((check == 0) && (tx < 0)) || ((check == 0) && (tx > cleaner[0][0])) ||  
				((check == 1) && (tx >= R)) || ((check == 1) && (tx < cleaner[1][0]))){
			running(check, x, y, cnt+1);
		}else if(map[tx][ty] == -1){
			map[x][y] = 0;
			return;
		}else{
			map[x][y] = map[tx][ty];
			running(check, tx, ty, cnt);
		}
	}
}
