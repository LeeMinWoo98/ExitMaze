package ExitGame;

import java.util.Random;
import java.util.Scanner;

public class ExitGame {
	static char[][] maze = new char [25][54];
	static Scanner in = new Scanner(System.in);
	static int x= 1;
	static int y= 0; 
	static char character = ' ';
	static int gemPoint = 0;
	static final String red      = "\u001B[31m" ;	//색 부여를 위해 변수 추가
	static final String blue     = "\u001B[34m" ;
	static final String exit     = "\u001B[0m" ;
	static String diff = "";
	static int[] rankingPoint={50,40,30,20,10,8,6,4,2,1};
	static String[] rankingName=new String[10];
	
	public static void main(String[] args) {
		int len_x = 0;
		int len_y = 0;
		int E_x = 0; //출구 x 좌표
		int E_y = 0; //출구 y 좌표
		long timeLimit = 0;
		long beforeTime = 0;
		long afterTime = 0;
		long remainTime = 0;
		String input = ""; 
		
		
	
		
		while(true) {
			int itemCnt = 5;
			
			System.out.println("1.도움말   2.게임시작   3.랭킹   4.종료");
			input=playerInput();
			
			if(input.equals("도움말")) {
				help();
			}else if(input.equals("게임시작")) {
				
				character(); //캐릭터 선택
				
				for(int i = 0; i < 25; i++) {
					for(int j = 0; j < 54; j++) {
						maze[i][j] = '*'; //빈 공간을 의미 -> 사이즈가 작은 미로 맵에서 그 미로멥의 크기만큼만 출력하기 위해서 
					}
				}
				
				while(diff.equals("")) {
					System.out.println("난이도를 고르세요 '쉬움' or '어려움'");
					input = playerInput();
					
					if(input.equals("쉬움")) {
						maze1();
						len_y = 20;
						len_x = 40;
						diff = "쉬움";
						timeLimit = 50;
						remainTime = 50;
					}
					else if(input.equals("어려움")){
						maze2();
						len_y = 25;
						len_x = 54;
						diff = "어려움";
						timeLimit = 100;
						remainTime = 100;
					}else {
						System.out.println("1이나 2만 입력해 주세요.");
					}
				}
				
				createGem(len_x, len_y);
				
				//플레이어의 위치
				maze[y][x] = character;
				
				E_x = E_x(); 
				E_y = E_y();
				
				gemPoint = 0; // 점수 초기화
				System.out.println("난이도: "+diff+"			남은시간(초) : "+remainTime+"			점수 : "+gemPoint);
				mazePrint(len_y);
				beforeTime = System.currentTimeMillis();
				
				while(true) {
					input = playerInput();
					
					//플레이어의 입력이 w 일 때
					if(input.equals("w"))
						up();
					//플레이어의 입력이 s 일 때
					else if(input.equals("s"))
						down();
					//플레이어의 입력이 a 일 때
					else if(input.equals("a"))
						left();
					//플레이어의 입력이 d 일 때
					else if(input.equals("d"))
						right();			
					//입력이 give up 일 때 게임 종료
					else if(input.equals("give up"))
						break;
					else if(input.equals("e")) {
						if(item(len_x,len_y)==0) 
							System.out.println("아이템을 사용할수 없습니다.");
						else {
							itemCnt--;
						}
					}
					for(int i = 0; i < len_y; i++) {
						System.out.println();
					}
					
					afterTime = System.currentTimeMillis(); // 코드 실행 후에 시간 받아오기
					remainTime = timeLimit-(afterTime - beforeTime)/1000; //두 시간에 차 계산
					
					if(remainTime<0)
						remainTime=0;
					
					
					if((x == E_x && y == E_y) || (x == E_x+1 && y == E_y) || (x == E_x+2 && y == E_y)) {
						System.out.println("     #####       ##   ##   ##   ######  \r\n" + 
								"    #######    #####  ### ###  #######  \r\n" + 
								"    ##         ## ##  #######  ##       \r\n" + 
								"    ##  ###   ##  ##  #######  #######  \r\n" + 
								"    ##   ##   ######  ## # ##  ##       \r\n" + 
								"    #######  ##   ##  ##   ##  #######  \r\n" + 
								"     #####   ##   ##  ##   ##   ######  \r\n\n" + 
								" #####   ##        ######      ##   ######   \r\n" + 
								"#######  ##       #######    #####  #######  \r\n" + 
								"##   ##  ##       ##         ## ##       ##  \r\n" + 
								"##       ##       #######   ##  ##  ######   \r\n" + 
								"##   ##  ##       ##        ######  ##   ##  \r\n" + 
								"#######  #######  #######  ##   ##  ##   ##  \r\n" + 
								" #####    ######   ######  ##   ##  ##   ##  \r\n" + 
								"                                                                              " + 
								"                                                                                          ");
						gemPoint+=remainTime;
						System.out.println("총점수: "+gemPoint+",			남은시간: "+remainTime+"초");
						rank(gemPoint);
						break;
					}
					
					if(remainTime == 0) {
						System.out.println("     #####       ##   ##   ##   ######  \r\n" + 
								"    #######    #####  ### ###  #######  \r\n" + 
								"    ##         ## ##  #######  ##       \r\n" + 
								"    ##  ###   ##  ##  #######  #######  \r\n" + 
								"    ##   ##   ######  ## # ##  ##       \r\n" + 
								"    #######  ##   ##  ##   ##  #######  \r\n" + 
								"     #####   ##   ##  ##   ##   ######  \r\n\n" + 
								"     #####   ##   ##   ######  ######   \r\n" + 
								"    #######  ##   ##  #######  #######  \r\n" + 
								"    ##   ##  ##   ##  ##            ##  \r\n" + 
								"    ##   ##  ##   ##  #######  ######   \r\n" + 
								"    ##   ##  ### ###  ##       ##   ##  \r\n" + 
								"    #######   #####   #######  ##   ##  \r\n" + 
								"     #####     ###     ######  ##   ##  \r\n" + 
								"                                     \r\n" + 
								"                    " + 
								"                      ");
						break;
					}
					System.out.println("난이도: "+diff+"			남은시간(초) : "+remainTime+"			점수 : "+gemPoint);
					mazePrint(len_y);
				}
				
				
				
				
				x= 1;
				y= 0; 
				character = ' ';
				gemPoint = 0;
				diff = "";
				
			}else if(input.equals("종료")) {
				System.out.println("게임을 종료합니다");
				break;
			}else if(input.equals("랭킹")) {
				rankview();
			}else {
				System.out.println("메뉴만 입력해주세요.");
			}
		}
	}
	
	//플레이어 입력 받아오기
	public static String playerInput() {
		String input="";
		input = in.nextLine();
		
		return input;
	}
	
	//미로 출력
	public static void mazePrint(int len) {
		for(int i=0; i<len; i++) {
			for(int j=0; j<maze[0].length; j++) {
				if(maze[i][j] == '*')
					break;
				if(maze[i][j] == character)
					System.out.print(red+maze[i][j]+exit+" ");
				else if(maze[i][j] == '_')
					System.out.print(blue+maze[i][j]+exit+" ");
				else
					System.out.print(maze[i][j]+" ");
			}
			System.out.println();
		}
	}
	
	//미로 선택
	public static void maze1() {
		String[] Maze ={"XPXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX",
						"X                 X X                  X",
						"XX XXXX  XXXXXXX  X X  XXXXXXXXXX      X",
						"X     X  X        X X  X        X      X",
						"XXXXX X  XXXXXXX  X X  X  XXX   XX     X",
						"X   X XXXX     X  X X  X  X       XX   X",
						"X   X X        X    X  X  X    X   X   X",
						"X   X XXXXX    XXXXXX  X  X    X   X   X",
						"X         X            XXXX    XXXX XXXX",
						"X   XXXX  XXXXXXXXX               X    X",
						"X   X     X       X  XXXXXXXX     XXXX X",
						"X   XX    XXXXXX  X         X        X X",
						"X X  X                      X        X X",
						"XX  XXXXXXXXXX  XXXX XXXX XXXXXXXX XXX X",
						"X  XX        X  X  X X               X X",
						"X  X  XXXX  XXXXX  X XXXXXX  XXXXX XXX X",
						"X  X         X     X         X   X X X X",
						"X  XXXXX  XXXX     XXXXXXXXXXX   XXX X X",
						"X                                      X",
						"XXXXXXXXXXXXXXXXXX___XXXXXXXXXXXXXXXXXXX"};		
				for(int i=0; i<Maze.length; i++) {
					 for(int j=0; j<Maze[0].length(); j++) {
						 maze[i][j]=Maze[i].charAt(j);
								}
							}
	}
	
	//미로 선택
	public static void maze2() {
		String[] Maze = {"XPXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX",
						"X     X       X X        X X                       X X",
						"X XXXXXX XXXXXX X X XXXXXX X X XXXX XX XXXX XXXXXXX XX",
						"X X    X X     X X   X X X       X X X     X       X X",
						"X X X X X X XXX X X X X X XXXXXX X X X XXXXXX XXXXXX X",
						"X   X X   X     X X X   X X       X X         X   X  X",
						"X X X XXX XX XXX X X XXXXXX X XXX X X X XXXXXXX X X XX",
						"X X X X   X   X X X X     X X     X X     X   X X X  X",
						"X X X X XXXXXX X X X XXXXXX X XXXXXX X XXX X X X X X X",
						"X X X   X     X X X X         X X       X   X X X X  X",
						"X X X XXXXXXXX X X X X XXXXXXXXX X XXXXXX X X X X X XX",
						"X X X         X X X X         X X       X X     X X XX",
						"X X X XXX XXXXX X X X X XXXXXXX X X XXXXXX X XXXXXX XX",
						"X X X     X   X X X X       X X X X       X X   X X XX",
						"X XXXXXX XXX X X X X XXXX X X X X XXXXXX X X XXX X X X",
						"X         X   X X   X   X X   X           X X     X XX",
						"X XXXXXXXXX XXXXXX XXXXXX X XXXXXX XXXXXX X   XX X X X",
						"X X         X       X           X         X X     X XX",
						"X X XXXXXX X XXXXXX XXX XXX XXXX X XXXXXX X X XXXXX XX",
						"X X     X X         X X   X     X X     X   X   X X XX",
						"X XXXXX X X XXXXXX X XXXXXX XXXXXXXX XXXXXX X X XXXXXX",
						"X X   X X X         X           X         X X        X",
						"X X XXXXXX XXXXXXXXXX XXXXXX XXXXXXXXXXXXXXXX XXXXXXXX",
						"X                       X X X       X X     X        X",
						"XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX___XXXXX"};
		
				for(int i=0; i<Maze.length; i++) {
					 for(int j=0; j<Maze[0].length(); j++) {
						 maze[i][j]=Maze[i].charAt(j);
					 }
				}
		}
				
		//플레이어 이동 - 왼쪽
		public static void left() {
			//추가됨
			if((int)maze[y][x-1] >= '0' && (int)maze[y][x-1] <= '9')
				gemPoint += (int)maze[y][x-1]-48;
				
			if(maze[y][x-1]=='X') {
				System.out.println("이동할 수 없습니다.");
			}else {
				maze[y][x] = ' ';
				maze[y][--x] = character;
			}	
		}
		
		//플레이어 이동 - 오른쪽
		public static void right() {
			//추가됨
			if((int)maze[y][x+1] >= '0' && (int)maze[y][x+1] <= '9')
				gemPoint += (int)maze[y][x+1]-48;
			
			if(maze[y][x+1]=='X') {
				System.out.println("이동할 수 없습니다.");
			}else {
				maze[y][x] = ' ';
				maze[y][++x] = character;
			}		
		}
		
		//플레이어 이동 - 위
		public static void up() {
			//추가됨
			
			
			if(x == 1 && y == 0) {
				System.out.println("이동할 수 없습니다.");
			}else if(maze[y-1][x]=='X') {
				System.out.println("이동할 수 없습니다.");
			}else {
				if((int)maze[y-1][x] >= '0' && (int)maze[y-1][x] <= '9')
					gemPoint += (int)maze[y-1][x]-48;
				maze[y][x] = ' ';
				maze[--y][x] = character;
			}
		}
		
		//플레이어 이동 - 아래
		public static void down() {
			//추가됨
			if((int)maze[y+1][x] >= '0' && (int)maze[y+1][x] <= '9')
				gemPoint += (int)maze[y+1][x]-48;
			
			if(maze[y+1][x]=='X') {
				System.out.println("이동할 수 없습니다.");
			}else {
				maze[y][x] = ' ';
				maze[++y][x] = character;
			}	
		}
		//출구 x 좌표
		public static int E_x() {
			int x = 0;
			
			for(int i = 0; i < 25; i++) {
				for(int j = 0; j < 54; j++) {
					if(maze[i][j] == '_') {
						x = j;
						break;
					}
				}
			}
			return x;
		}
		//출구 y 좌표
		public static int E_y() {
			int y = 0;
			
			for(int i = 0; i < 25; i++) {
				for(int j = 0; j < 54; j++) {
					if(maze[i][j] == '_') {
						y = i;
						break;
					}
				}
			}
			return y;
		}
		
		public static void character() {
			String input =new String();
			
			System.out.println("다음 중 캐릭터를 고르세요 (번호만 입력)");
			
			while(true) {
				System.out.println("1번 : '@' | 2번 : 'P' | 3번 : '#' | 4번 : '$' | 5번 : '&'");
				input = in.nextLine();
				
				if(input.equals("1")) {
					System.out.println("@로 선택되었습니다");
					character ='@';
					break;
				}else if(input.equals("2")) {
					System.out.println("P로 선택되었습니다");
					character ='P';
					break;
				}else if(input.equals("3")) {
					System.out.println("#로 선택되었습니다");
					character ='#';
					break;
				}else if(input.equals("4")) {
					System.out.println("$로 선택되었습니다");
					character ='$';
					break;
				}else if(input.equals("5")) {
					System.out.println("&로 선택되었습니다");
					character ='&';
					break;
				}else
					System.out.println("일치하지 않습니다");
			}
		}
		//매개변수는 해당 맵의 크기를 의미한다
		public static void createGem(int x_index, int y_index){
			Random r = new Random();
			char[] gem = {'1','2','3','4','5','6','7','8','9'};
			int gemType = 0;
			int gem_x = 0;
			int gem_y = 0;
			int[] dup_x = new int[gem.length];
			int[] dup_y = new int[gem.length];
			int key = 0;
			
			for(int i = 0; i < gem.length; i++) {
				key = 0;
				gemType = i;
				gem_x = r.nextInt(x_index);
				gem_y = r.nextInt(y_index);
				
				for(int j = 0; j < gem.length; j++) {
					if((dup_x[i] == gem_x && dup_y[i] == gem_y) || maze[gem_y][gem_x] == 'X') {
						key++;
						break;
					}
				}
				
				if(key == 1) {
					key = 0;
					i--;
					continue;
					
				}
				dup_x[i] = gem_x;
				dup_y[i] = gem_y;
						
				maze[gem_y][gem_x] = gem[gemType];	
			}
			
			
		}
		
		public static int item(int x_len,int y_len) {
			String Item = "";
			System.out.println("아이템을 사용할 방향을 입력하세요 : ");
			Item = in.nextLine();
			

			if(Item.equals("w") && (y-1)>0) {
				if(maze[y-1][x]=='X') {
					maze[y-1][x] = ' ';
				
				}
				else {
					up();
					up();
					}
			}
			
			else if(Item.equals("s") && y+1<y_len-1) {
				if(maze[y+1][x]=='X') {
					maze[y+1][x] = ' ';
				}else {
					down();
					down();
					}
			}
			else if(Item.equals("a") && x-1>0) {
				if(maze[y][x-1]=='X') {
					maze[y][x-1] = ' ';
				}else {
					left();
					left();
					}
					
			}
			else if(Item.equals("d") && x+1<x_len-1) {
				if(maze[y][x+1]=='X') {
					maze[y][x+1] = ' ';
				}else {
					right();
					right();
					}
			}else {
				return 0;
			}
			
			System.out.println("아이템을 사용했습니다.");
			return 1;
			
			
		}
		
		public static void rank(int point) {
			System.out.println("랭킹에 등록하시겠습니까? y/n");
			String ans=playerInput();
			int ck=0;
			if(ans.equals("y")) {
				for(int i=0;i<10;i++) {
					if(rankingPoint[i]<point) {
						System.out.println("닉네임 입력");
						String name=playerInput();
						for(int j=8;j>=i;j--) {
							rankingPoint[j+1]=rankingPoint[j];
							rankingName[j+1]=rankingName[j];
						}
						rankingPoint[i]=point;
						rankingName[i]=name;
						System.out.println("등록되었습니다.");
						ck=1;
						break;
					}
				}
				if(ck==0) {
					System.out.println("등록실패 점수가 낮습니다.");
				}
			}else if(ans.equals("n")){
				
			}else {
				System.out.println("다시입력해주세요");
				rank(point);
			}
			
		}
		
		public static void rankview() {
			for(int i=0;i<10;i++) {
				System.out.println(rankingName[i]+" "+rankingPoint[i]);
			}
		}
		
		public static void help() {
			System.out.println("[Help]");
			System.out.println("-------------------------------------------------------------------------");
			System.out.println("|	이동은 상(w), 하(s), 좌(a), 우(d) 로 합니다	.			|");
			System.out.println("|	아이템 사용은 e를 입력하여 사용하고					|");
			System.out.println("|	벽에 아이템을 사용하면 벽이 부서집니다!					|");
			System.out.println("|	통로에 아이템을 사용한다면 플레이어가 2칸 이동합니다.				|");
			System.out.println("|	난이도는 쉬움과 어려움이 있고 각각 제한시간이 있습니다.				|");
			System.out.println("|	미로에 등장하는 숫자를 획득하면 숫자만큼 점수가 올라갑니다.			|");
			System.out.println("|	아이템을 적절히 사용해 최대한 많은 점수를 획득하고 제한시간내에 미로를 탈출하세요!	|");
			System.out.println("-------------------------------------------------------------------------");
		}

}

