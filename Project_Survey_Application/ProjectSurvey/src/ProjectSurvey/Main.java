package ProjectSurvey;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Database.*;
public class Main {
	protected static TopicDAO tdao = new TopicDAO();
	protected static ItemDAO idao = new ItemDAO();
	private static int numOfTopics;
	private static int option;
	private static List<TopicVO> tvo1;
		
	public static boolean view() {
		tvo1 = tdao.selectAll();
		if(tvo1==null) {
			System.out.println("『주제를 먼저 만들어주세요.』\n");
			return false;
		}else {
			numOfTopics = tvo1.size();
			return true;
		}
		
	}
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		boolean flag = true; int state=-1;
		CheckValue cv = new CheckValue();

		while(flag) {
			
			state=cv.checkInt("\n\t★환영합니다.★\n1.\t설문하러가기\n2.\t설문 현황보기\n3.\t설문 주제 만들기\n4.\t종료(0번=초기화)\n");
			
			switch(state) {
			case 1: 
				if(!view())
					continue;System.out.println();
				
				for(TopicVO e : tvo1)
					System.out.println(e);
				
				
				loop2:
					while(true) {
						option = cv.checkInt("『설문 주제 번호를 선택하세요』 :  ");	
						for(int i=1; i<=numOfTopics; i++) {	
							if(option == i)
								break loop2;
							else
								System.out.print("해당 번호가 없습니다, ");
						}
					}
				
				ArrayList<Object[]> op2 = idao.selectItem(option);

				int viewNum = 1;
				System.out.println("\n\t★"+tdao.selectTopic(option)+" 설문조사★");
				for(Object[] o : op2) {
					System.out.println(String.format("%d.%-10s",viewNum,o[0]));
					viewNum++;
				}
				
				int addOption = idao.countOption(option)+1; 
				System.out.printf("%d.%-10s\n",addOption,"직접입력하기");
				
				loop3:
					while(true) {
						int choiceOption = cv.checkInt("『번호를 선택하세요』 :  ");
						if(choiceOption < addOption && choiceOption > 0) {
							idao.choice(choiceOption,option);
							break;
						}else if(choiceOption == addOption) {
							
							System.out.print("\n"+choiceOption+"번 항목을 입력하세요 : ");
							String tempOp=scan.nextLine();
							idao.choiceOther(option ,tempOp, choiceOption);
							System.out.println();
							break;
						}else {
							System.out.print("해당 번호가 없습니다, ");
							continue loop3;
						}
					}
				break;
				
			case 2: 
				if(!view())
					continue;
				System.out.println();
				for(TopicVO e : tvo1) {
					System.out.println(e);
					ArrayList<Object[]> op = idao.selectItem(e.getNum());
					for(Object[] o : op) 
						System.out.println(String.format("·\t %-10s \t\t%d표",o[0],o[1]));
					System.out.println();
				}
				System.out.println();
				break;
			
			case 3:
				String text = cv.checkTopic("\n『주제를 입력하세요』 :  ");
				tdao.createTopic(text); 
				System.out.println("\n주제 반영완료");

				for(int i=1; i<=3; i++) {
					System.out.print("『"+i+"번 항목을 입력하세요 』: ");
					idao.createOption(tdao.getNOT(),scan.nextLine(),i,0);
				}System.out.println();
				
				break;
		
			case 4:
				flag = false;break;
				
			case 0:
				tdao.initialization();
				break;
			
			default : 
				System.out.println("해당 번호가 없습니다");
				break;
			}
		}
		scan.close();
	}
}


