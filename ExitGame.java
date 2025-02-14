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
	static final String red      = "\u001B[31m" ;	//�� �ο��� ���� ���� �߰�
	static final String blue     = "\u001B[34m" ;
	static final String exit     = "\u001B[0m" ;
	static String diff = "";
	static int[] rankingPoint={50,40,30,20,10,8,6,4,2,1};
	static String[] rankingName=new String[10];
	
	public static void main(String[] args) {
		int len_x = 0;
		int len_y = 0;
		int E_x = 0; //�ⱸ x ��ǥ
		int E_y = 0; //�ⱸ y ��ǥ
		long timeLimit = 0;
		long beforeTime = 0;
		long afterTime = 0;
		long remainTime = 0;
		String input = ""; 
		
		
	
		
		while(true) {
			int itemCnt = 5;
			
			System.out.println("1.����   2.���ӽ���   3.��ŷ   4.����");
			input=playerInput();
			
			if(input.equals("����")) {
				help();
			}else if(input.equals("���ӽ���")) {
				
				character(); //ĳ���� ����
				
				for(int i = 0; i < 25; i++) {
					for(int j = 0; j < 54; j++) {
						maze[i][j] = '*'; //�� ������ �ǹ� -> ����� ���� �̷� �ʿ��� �� �̷θ��� ũ�⸸ŭ�� ����ϱ� ���ؼ� 
					}
				}
				
				while(diff.equals("")) {
					System.out.println("���̵��� ������ '����' or '�����'");
					input = playerInput();
					
					if(input.equals("����")) {
						maze1();
						len_y = 20;
						len_x = 40;
						diff = "����";
						timeLimit = 50;
						remainTime = 50;
					}
					else if(input.equals("�����")){
						maze2();
						len_y = 25;
						len_x = 54;
						diff = "�����";
						timeLimit = 100;
						remainTime = 100;
					}else {
						System.out.println("1�̳� 2�� �Է��� �ּ���.");
					}
				}
				
				createGem(len_x, len_y);
				
				//�÷��̾��� ��ġ
				maze[y][x] = character;
				
				E_x = E_x(); 
				E_y = E_y();
				
				gemPoint = 0; // ���� �ʱ�ȭ
				System.out.println("���̵�: "+diff+"			�����ð�(��) : "+remainTime+"			���� : "+gemPoint);
				mazePrint(len_y);
				beforeTime = System.currentTimeMillis();
				
				while(true) {
					input = playerInput();
					
					//�÷��̾��� �Է��� w �� ��
					if(input.equals("w"))
						up();
					//�÷��̾��� �Է��� s �� ��
					else if(input.equals("s"))
						down();
					//�÷��̾��� �Է��� a �� ��
					else if(input.equals("a"))
						left();
					//�÷��̾��� �Է��� d �� ��
					else if(input.equals("d"))
						right();			
					//�Է��� give up �� �� ���� ����
					else if(input.equals("give up"))
						break;
					else if(input.equals("e")) {
						if(item(len_x,len_y)==0) 
							System.out.println("�������� ����Ҽ� �����ϴ�.");
						else {
							itemCnt--;
						}
					}
					for(int i = 0; i < len_y; i++) {
						System.out.println();
					}
					
					afterTime = System.currentTimeMillis(); // �ڵ� ���� �Ŀ� �ð� �޾ƿ���
					remainTime = timeLimit-(afterTime - beforeTime)/1000; //�� �ð��� �� ���
					
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
						System.out.println("������: "+gemPoint+",			�����ð�: "+remainTime+"��");
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
					System.out.println("���̵�: "+diff+"			�����ð�(��) : "+remainTime+"			���� : "+gemPoint);
					mazePrint(len_y);
				}
				
				
				
				
				x= 1;
				y= 0; 
				character = ' ';
				gemPoint = 0;
				diff = "";
				
			}else if(input.equals("����")) {
				System.out.println("������ �����մϴ�");
				break;
			}else if(input.equals("��ŷ")) {
				rankview();
			}else {
				System.out.println("�޴��� �Է����ּ���.");
			}
		}
	}
	
	//�÷��̾� �Է� �޾ƿ���
	public static String playerInput() {
		String input="";
		input = in.nextLine();
		
		return input;
	}
	
	//�̷� ���
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
	
	//�̷� ����
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
	
	//�̷� ����
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
				
		//�÷��̾� �̵� - ����
		public static void left() {
			//�߰���
			if((int)maze[y][x-1] >= '0' && (int)maze[y][x-1] <= '9')
				gemPoint += (int)maze[y][x-1]-48;
				
			if(maze[y][x-1]=='X') {
				System.out.println("�̵��� �� �����ϴ�.");
			}else {
				maze[y][x] = ' ';
				maze[y][--x] = character;
			}	
		}
		
		//�÷��̾� �̵� - ������
		public static void right() {
			//�߰���
			if((int)maze[y][x+1] >= '0' && (int)maze[y][x+1] <= '9')
				gemPoint += (int)maze[y][x+1]-48;
			
			if(maze[y][x+1]=='X') {
				System.out.println("�̵��� �� �����ϴ�.");
			}else {
				maze[y][x] = ' ';
				maze[y][++x] = character;
			}		
		}
		
		//�÷��̾� �̵� - ��
		public static void up() {
			//�߰���
			
			
			if(x == 1 && y == 0) {
				System.out.println("�̵��� �� �����ϴ�.");
			}else if(maze[y-1][x]=='X') {
				System.out.println("�̵��� �� �����ϴ�.");
			}else {
				if((int)maze[y-1][x] >= '0' && (int)maze[y-1][x] <= '9')
					gemPoint += (int)maze[y-1][x]-48;
				maze[y][x] = ' ';
				maze[--y][x] = character;
			}
		}
		
		//�÷��̾� �̵� - �Ʒ�
		public static void down() {
			//�߰���
			if((int)maze[y+1][x] >= '0' && (int)maze[y+1][x] <= '9')
				gemPoint += (int)maze[y+1][x]-48;
			
			if(maze[y+1][x]=='X') {
				System.out.println("�̵��� �� �����ϴ�.");
			}else {
				maze[y][x] = ' ';
				maze[++y][x] = character;
			}	
		}
		//�ⱸ x ��ǥ
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
		//�ⱸ y ��ǥ
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
			
			System.out.println("���� �� ĳ���͸� ������ (��ȣ�� �Է�)");
			
			while(true) {
				System.out.println("1�� : '@' | 2�� : 'P' | 3�� : '#' | 4�� : '$' | 5�� : '&'");
				input = in.nextLine();
				
				if(input.equals("1")) {
					System.out.println("@�� ���õǾ����ϴ�");
					character ='@';
					break;
				}else if(input.equals("2")) {
					System.out.println("P�� ���õǾ����ϴ�");
					character ='P';
					break;
				}else if(input.equals("3")) {
					System.out.println("#�� ���õǾ����ϴ�");
					character ='#';
					break;
				}else if(input.equals("4")) {
					System.out.println("$�� ���õǾ����ϴ�");
					character ='$';
					break;
				}else if(input.equals("5")) {
					System.out.println("&�� ���õǾ����ϴ�");
					character ='&';
					break;
				}else
					System.out.println("��ġ���� �ʽ��ϴ�");
			}
		}
		//�Ű������� �ش� ���� ũ�⸦ �ǹ��Ѵ�
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
			System.out.println("�������� ����� ������ �Է��ϼ��� : ");
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
			
			System.out.println("�������� ����߽��ϴ�.");
			return 1;
			
			
		}
		
		public static void rank(int point) {
			System.out.println("��ŷ�� ����Ͻðڽ��ϱ�? y/n");
			String ans=playerInput();
			int ck=0;
			if(ans.equals("y")) {
				for(int i=0;i<10;i++) {
					if(rankingPoint[i]<point) {
						System.out.println("�г��� �Է�");
						String name=playerInput();
						for(int j=8;j>=i;j--) {
							rankingPoint[j+1]=rankingPoint[j];
							rankingName[j+1]=rankingName[j];
						}
						rankingPoint[i]=point;
						rankingName[i]=name;
						System.out.println("��ϵǾ����ϴ�.");
						ck=1;
						break;
					}
				}
				if(ck==0) {
					System.out.println("��Ͻ��� ������ �����ϴ�.");
				}
			}else if(ans.equals("n")){
				
			}else {
				System.out.println("�ٽ��Է����ּ���");
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
			System.out.println("|	�̵��� ��(w), ��(s), ��(a), ��(d) �� �մϴ�	.			|");
			System.out.println("|	������ ����� e�� �Է��Ͽ� ����ϰ�					|");
			System.out.println("|	���� �������� ����ϸ� ���� �μ����ϴ�!					|");
			System.out.println("|	��ο� �������� ����Ѵٸ� �÷��̾ 2ĭ �̵��մϴ�.				|");
			System.out.println("|	���̵��� ����� ������� �ְ� ���� ���ѽð��� �ֽ��ϴ�.				|");
			System.out.println("|	�̷ο� �����ϴ� ���ڸ� ȹ���ϸ� ���ڸ�ŭ ������ �ö󰩴ϴ�.			|");
			System.out.println("|	�������� ������ ����� �ִ��� ���� ������ ȹ���ϰ� ���ѽð����� �̷θ� Ż���ϼ���!	|");
			System.out.println("-------------------------------------------------------------------------");
		}

}

