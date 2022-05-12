package ProjectSurvey;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import Database.*; //패키지 import

public class Main {
	private static TopicDAO tdao = new TopicDAO();
	private static ItemDAO idao = new ItemDAO();
	private static int numOfTopics;
	private static int option; 
	private static int state;
	private static List<TopicVO> tvo1;
		
	public static boolean view() {
		tvo1 = tdao.selectAll();
		if(tvo1==null) {
			System.out.println("\n『주제를 먼저 만들어주세요.』");
			return false;
		}else {
			numOfTopics = tvo1.size();
			return true;
		}
	}
	
	public static void main(String[] args) {
		boolean flag = true; 
		CheckValue cv = new CheckValue();
		Scanner scan = new Scanner(System.in);
		
		System.out.print("  「★환영합니다.★」");
		
		loopMenu:
		while(flag){
			
			state=cv.checkInt("\n1.\t설문하러가기\n2.\t설문 현황보기"
					+ "\n3.\t설문 주제 만들기\n4.\t종료(0번=초기화)\n☞번호를 선택하세요 》 :  ");			
			switch(state) {
			
			case 1: // 1. 설문하러가기
				if(!view())
					continue;System.out.println();
				
				loop1:
					while(true) {
						for(TopicVO e : tvo1)
							System.out.println(e);
						loop2:
							while(true) {
								option = cv.checkInt("「설문 주제 번호를 선택하세요 (-1입력시 뒤로가기)」 :  ");
								if(option==-1)continue loopMenu;
								
								for(int i=1; i<=numOfTopics; i++) {	
									if(option==i)
										break loop2;
								}
								System.out.print("》해당 번호가 없습니다, ");
								continue loop2;
							}
						
						ArrayList<Object[]> op2 = idao.selectItem(option);
		
						int viewNum = 1;
						System.out.println("\n\t★"+tdao.selectTopic(option)+" 설문조사★");
						for(Object[] o : op2) {
							System.out.println(String.format("%d.%-10s",viewNum,o[0]));
							viewNum++;
						}

						int addOption = idao.getAddOption()+1;
						System.out.printf("%d.%-10s\n",addOption,"직접입력하기");
						
						
						while(true) {
							int choiceOption = cv.checkInt("「번호를 선택하세요 (-1입력시 뒤로가기) 」 :  ");
							System.out.println();
							if(choiceOption==-1)continue loop1;
							
							if(choiceOption < addOption && choiceOption > 0) {
								idao.choice(choiceOption,option);
								break loop1;
							}else if(choiceOption == addOption) {
								
								while(true) {
									System.out.print(choiceOption+"번 항목을 입력하세요 : ");
									String tempOp=scan.nextLine();
								
									if(idao.checkOption(tempOp,option)) { 
										System.out.print("중복된 데이터가 이미 존재합니다.");
										continue;
									}else { 
										idao.choiceOther(option ,tempOp, choiceOption);
										break loop1;
									}
								}
								
							}else {
								System.out.print("》해당 번호가 없습니다2, ");
								continue;
							}
						}
					}
				break;
				
			case 2: // 2. 설문 현황 보기
				if(!view())
					continue;
				
				System.out.println(); 
				for(TopicVO e : tvo1) {
					int sum = idao.totalcount(e.getNum());
					System.out.println(e+"\t\t"+"( "+sum+"명 참여 )");
					ArrayList<Object[]> op = idao.selectItem(e.getNum());
					
					for(Object[] o : op) {
						System.out.print(String.format("·\t %-10s \t\t%d표\t",o[0],o[1]));
						System.out.printf("( %.1f%% )\n",persent((Integer)o[1],sum));
					}
					System.out.println();
				}
				break;
			
			case 3: // 3. 설문 주제 만들기
				
				while(true) {
					String text = cv.checkTopic("\n「주제를 입력하세요」(-1입력시 뒤로가기) :  ");
					if(text.equals("-1"))
						continue loopMenu;
					
					if(tdao.checkTopic(text)) { 
						System.out.println("중복된 데이터가 이미 존재합니다. ");
						continue;
					}else {
						tdao.createTopic(text); 
						System.out.println("\n주제 반영완료");
						 break;
					}
				}
				
				for(int i=1; i<=3; i++) {
					System.out.print("『"+i+"번 항목을 입력하세요 』: ");
					String op=scan.nextLine();
					if(idao.checkOption(op,tdao.getNOT())) { 
						System.out.print("중복된 데이터가 이미 존재합니다.");
						i--;
					}else { 
						idao.createOption(tdao.getNOT(),op,i,0);
					}
				}System.out.println();
								
				break;
		
			case 4: // 종료
				flag = false; System.out.println("\n애플리케이션 종료.");break;
				
			case 0: // 초기화
				System.out.print("\n정말 초기화 하시겠습니까..(y/n) : ");
				String init=scan.nextLine(); System.out.println();
				
				if(init.equals("y")||init.equals("Y")) {
					tdao.initialization();
					break;
				}else {
					System.out.println("초기화 취소..\n");
					break;
				}
			
			default : 
				System.out.println("》해당 번호가 없습니다");
				break;
			}
		}
		scan.close();
	}
	public static double persent(int num,int sum) {
		if(sum==0)
			return 0;
		else
			return (double)num/(double)sum*100.0;
	}
}


