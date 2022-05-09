package one;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import db.*;
public class Main {
		static TopicDAO tdao = new TopicDAO();
		static ItemDAO idao = new ItemDAO();
		static Scanner scan = new Scanner(System.in);
		private static int option;
		private static String others = "�����Է��ϱ�";
		
	public static boolean view() {
		List<TopicVO> tvo1 = tdao.selectAll();
		if(tvo1==null) {
			System.out.println("�������� ���� ������ּ���.��\n");
			return false;
		}
		else {
			for(TopicVO e : tvo1)
				System.out.println(e);
		}
		System.out.print("����ȣ�� �����ϼ��䡻 :  ");
		option = scan.nextInt();System.out.println(); scan.nextLine();
		return true;
	}
	
	public static void main(String[] args) {

		
		boolean flag = true; int state;
		
		
		while(flag) {
			System.out.println("1.�����Ϸ�����\n2.���� ��Ȳ����\n3.���� ���� �����\n4.����(0��)");
			state = scan.nextInt(); scan.nextLine(); System.out.println();
			
			switch(state) {
			case 1: 
				if(!view())
					continue;
				
			
				
				ArrayList<Object[]> op2 = idao.selectItem(option);
				
				if(op2==null)
					System.out.println("���� ��ǥ�� ���ϼ̾��");
				else {
					int viewNum = 1;
					for(Object[] o : op2) {
						System.out.println(String.format("%d.%-10s",viewNum,o[0]));
						viewNum++;
					}
					int addOption = idao.countOption(option)+1;
					System.out.printf("%d.%-10s\n",addOption,others);
//					int choiceOption = scan.nextInt(); scan.nextLine();
//					if(choiceOption == idao.countOption()) {
//						System.out.print("�Է� : ");
//						String tempOption = scan.nextLine();
//						idao.createOption(option1,tempOtion,addOption,1);	
//					}
//					else {
//						idao.choice();
//					}
					
				}
				
				break;
				
			case 2: 
				if(!view())
					continue;
				
				ArrayList<Object[]> op = idao.selectItem(option);
				
				if(op==null)
					System.out.println("���� ��ǥ�� ���ϼ̾��"); //������� �޼��忡 �ְ� ��ȯ�� boolean������ print����
				else {
					for(Object[] o : op) {
						System.out.println(String.format("��%-10s\t\t%dǥ",o[0],o[1]));
					}
				}
				System.out.println();
				break;
			
			case 3:
				System.out.println("����� ������ �Է����ּ��� :");
				String topic = scan.nextLine();
				if(topic.equals("")) {
					System.out.println("������ ������ ���� �Ұ��մϴ�."); //���ڿ� �����̸� �ٽ� �Է�â���� �ǵ��ƿ��°� ������
					for(int i=0; i<10; i++)					//loop 2: ���̷��� �Ẹ��
						System.out.print(".");
					System.out.println();
					continue;
				}
				boolean check = tdao.createTopic(topic);
				if(check) {
					System.out.println("���� �ݿ��Ϸ�");
					/* �׸��� �ϴ� 3���� ���� */
					for(int i=1; i<=3; i++) {
						System.out.println(i+"�� �׸��� �Է��ϼ���");
						String option = scan.nextLine();
						idao.createOption(tdao.getNOT(),option,i,0);
					}
				}
				else 
					System.out.println("������ �����");
				break;
			
			case 0:
				flag = false;
			}
		}
		
		
		
		scan.close();
	}

}
