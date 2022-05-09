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
	private static String others = "�����Է��ϱ�";
	private static List<TopicVO> tvo1;
		
	public static boolean view() {
		tvo1 = tdao.selectAll();
		if(tvo1==null) {
			System.out.println("�������� ���� ������ּ���.��\n");
			return false;
		}
		return true;
	}
	
	public static void main(String[] args) {

		boolean flag = true; int state;

		while(flag) {
			System.out.println("1. �����Ϸ�����\n2. ���� ��Ȳ����\n3. ���� ���� �����\n4. ����");
			state = scan.nextInt(); scan.nextLine(); System.out.println();
			
			switch(state) {
			case 1: 
				if(!view())
					continue;
				
				for(TopicVO e : tvo1)
					System.out.println(e);
				
				System.out.print("����ȣ�� �����ϼ��䡻 :  ");
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
				
				//topic_num => option(�ܷ�Ű ����)
				if(choiceOption < addOption && choiceOption > 0) {
					idao.choice(choiceOption,option);
				}else if(choiceOption == addOption) {
					scan.nextLine();
					System.out.print("�׸��� �Է��ϼ��� : ");
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
						System.out.println(String.format("��%-10s\t\t%dǥ",o[0],o[1]));
					System.out.println();
				}
				
				System.out.println();
				break;
			
			case 3:
				System.out.print("����� ������ �Է����ּ��� : "); 
				String topic = scan.nextLine(); 
				if(topic.equals("")) {
					System.out.println("������ ������ ���� �Ұ��մϴ�.\n"); //���ڿ� �����̸� �ٽ� �Է�â���� �ǵ��ƿ��°� ������
					continue;
				}
				if(tdao.createTopic(topic)) {
					System.out.println("\n���� �ݿ��Ϸ�");

					for(int i=1; i<=3; i++) {
						System.out.print(i+"�� �׸��� �Է��ϼ��� : ");
						idao.createOption(tdao.getNOT(),scan.nextLine(),i,0);
					}System.out.println();
				}
				else 
					System.out.println("\n�����߻�");
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
//		private static String others = "�����Է��ϱ�";
//		
//	public static boolean view() {
//		List<TopicVO> tvo1 = tdao.selectAll();
//		if(tvo1==null) {
//			System.out.println("�������� ���� ������ּ���.��\n");
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
//			System.out.println("1.�����Ϸ�����\n2.���� ��Ȳ����\n3.���� ���� �����\n4.����(0��)");
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
//					System.out.println("�������� ���� ������ּ���.��\n");
//					break;
//				}
//				else {
//					for(TopicVO e : tvo1) {
//						System.out.println(e);
//						ArrayList<Object[]> op = idao.selectItem(e.getNum());
//						for(Object[] o : op) 
//							System.out.println(String.format("��%-10s\t\t%dǥ",o[0],o[1]));
//						System.out.println();
//					}
//				}
//				System.out.println();
//				break;
//			
//			case 3:
//				System.out.println("����� ������ �Է����ּ��� :");
//				String topic = scan.nextLine();
//				if(topic.equals("")) {
//					System.out.println("������ ������ ���� �Ұ��մϴ�."); //���ڿ� �����̸� �ٽ� �Է�â���� �ǵ��ƿ��°� ������
//					for(int i=0; i<10; i++)					//loop 2: ���̷��� �Ẹ��
//						System.out.print(".");
//					System.out.println();
//					continue;
//				}
//				boolean check = tdao.createTopic(topic);
//				if(check) {
//					System.out.println("���� �ݿ��Ϸ�");
//					/* �׸��� �ϴ� 3���� ���� */
//					for(int i=1; i<=3; i++) {
//						System.out.println(i+"�� �׸��� �Է��ϼ���");
//						String option = scan.nextLine();
//						idao.createOption(tdao.getNOT(),option,i,0);
//					}
//				}
//				else 
//					System.out.println("������ �����");
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
