package one;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import db.*;
public class Main {
	private static TopicDAO tdao = new TopicDAO();
	private static ItemDAO idao = new ItemDAO();
	private static Scanner scan = new Scanner(System.in);
	private static int option;
	private static String others = "직접입력하기";
	private static List<TopicVO> tvo1;
		
	public static boolean view() {
		tvo1 = tdao.selectAll();
		if(tvo1==null) {
			System.out.println("『주제를 먼저 만들어주세요.』\n");
			return false;
		}
		return true;
	}
	
	public static void main(String[] args) {

		boolean flag = true; int state;

		while(flag) {
			System.out.println("1. 설문하러가기\n2. 설문 현황보기\n3. 설문 주제 만들기\n4. 종료");
			state = scan.nextInt(); scan.nextLine(); System.out.println();
			
			switch(state) {
			case 1: 
				if(!view())
					continue;
				
				for(TopicVO e : tvo1)
					System.out.println(e);
				
				System.out.print("『번호를 선택하세요』 :  ");
				option = scan.nextInt();System.out.println(); scan.nextLine();
				
				ArrayList<Object[]> op2 = idao.selectItem(option);
				
				
				int viewNum = 1;
				for(Object[] o : op2) {
					System.out.println(String.format("%d.%-10s",viewNum,o[0]));
					viewNum++;
				}
				
				int addOption = idao.countOption(option)+1; 
				System.out.printf("%d.%-10s\n",addOption,others);
				
				int choiceOption = scan.nextInt(); System.out.println();
				
				//topic_num => option(외래키 참조)
				if(choiceOption < addOption && choiceOption > 0) {
					idao.choice(choiceOption,option);
				}else if(choiceOption == addOption) {
					scan.nextLine();
					System.out.print("항목을 입력하세요 : ");
					String tempOp=scan.nextLine();
					idao.choiceOther(option ,tempOp, choiceOption);
					System.out.println();
				}else
					break;
				
				break;
				
			case 2: 
				
				if(!view())
					continue;
				
				for(TopicVO e : tvo1) {
					System.out.println(e);
					ArrayList<Object[]> op = idao.selectItem(e.getNum());
					for(Object[] o : op) 
						System.out.println(String.format("·%-10s\t\t%d표",o[0],o[1]));
					System.out.println();
				}
				
				System.out.println();
				break;
			
			case 3:
				System.out.print("만드실 주제를 입력해주세요 : "); 
				String topic = scan.nextLine(); 
				if(topic.equals("")) {
					System.out.println("공백의 주제는 생성 불가합니다.\n"); //문자열 공백이면 다시 입력창으로 되돌아오는게 나을듯
					continue;
				}
				if(tdao.createTopic(topic)) {
					System.out.println("\n주제 반영완료");

					for(int i=1; i<=3; i++) {
						System.out.print(i+"번 항목을 입력하세요 : ");
						idao.createOption(tdao.getNOT(),scan.nextLine(),i,0);
					}System.out.println();
				}
				else 
					System.out.println("\n에러발생");
				break;
			
			case 4:
				flag = false;
			}
		}
		scan.close();
	}

}


//package one;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Scanner;
//
//import db.*;
//public class Main {
//		static TopicDAO tdao = new TopicDAO();
//		static ItemDAO idao = new ItemDAO();
//		static Scanner scan = new Scanner(System.in);
//		private static int option;
//		private static String others = "직접입력하기";
//		
//	public static boolean view() {
//		List<TopicVO> tvo1 = tdao.selectAll();
//		if(tvo1==null) {
//			System.out.println("『주제를 먼저 만들어주세요.』\n");
//			return false;
//		}
//		return true;
//	}
//	
//	public static void main(String[] args) {
//
//		
//		boolean flag = true; int state;
//		
//		
//		while(flag) {
//			System.out.println("1.설문하러가기\n2.설문 현황보기\n3.설문 주제 만들기\n4.종료(0번)");
//			state = scan.nextInt(); scan.nextLine(); System.out.println();
//			
//			switch(state) {
//			case 1: 
//				if(!view())
//					continue;
//				
//			
//				
//				ArrayList<Object[]> op2 = idao.selectItem(option);
//				
//				
//				int viewNum = 1;
//				for(Object[] o : op2) {
//					System.out.println(String.format("%d.%-10s",viewNum,o[0]));
//					viewNum++;
//				}
//				int addOption = idao.countOption(option)+1;
//				System.out.printf("%d.%-10s\n",addOption,others);
//				int choiceOption = scan.nextInt(); System.out.println();
//				
//				if(choiceOption != 4)
//					idao.Choice(choiceOption,option);
//				
//				
//				
//				break;
//				
//			case 2: 
//				
//				List<TopicVO> tvo1 = tdao.selectAll();
//				if(tvo1==null) {
//					System.out.println("『주제를 먼저 만들어주세요.』\n");
//					break;
//				}
//				else {
//					for(TopicVO e : tvo1) {
//						System.out.println(e);
//						ArrayList<Object[]> op = idao.selectItem(e.getNum());
//						for(Object[] o : op) 
//							System.out.println(String.format("·%-10s\t\t%d표",o[0],o[1]));
//						System.out.println();
//					}
//				}
//				System.out.println();
//				break;
//			
//			case 3:
//				System.out.println("만드실 주제를 입력해주세요 :");
//				String topic = scan.nextLine();
//				if(topic.equals("")) {
//					System.out.println("공백의 주제는 생성 불가합니다."); //문자열 공백이면 다시 입력창으로 되돌아오는게 나을듯
//					for(int i=0; i<10; i++)					//loop 2: 뭐이런거 써보자
//						System.out.print(".");
//					System.out.println();
//					continue;
//				}
//				boolean check = tdao.createTopic(topic);
//				if(check) {
//					System.out.println("주제 반영완료");
//					/* 항목을 일단 3개만 만듦 */
//					for(int i=1; i<=3; i++) {
//						System.out.println(i+"번 항목을 입력하세요");
//						String option = scan.nextLine();
//						idao.createOption(tdao.getNOT(),option,i,0);
//					}
//				}
//				else 
//					System.out.println("오류가 났어요");
//				break;
//			
//			case 0:
//				flag = false;
//			}
//		}
//		
//		
//		
//		scan.close();
//	}
//
//}
