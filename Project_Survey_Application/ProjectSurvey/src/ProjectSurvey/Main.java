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
			System.out.println("�������� ���� ������ּ���.��\n");
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
			
			state=cv.checkInt("\n\t��ȯ���մϴ�.��\n1.\t�����Ϸ�����\n2.\t���� ��Ȳ����\n3.\t���� ���� �����\n4.\t����(0��=�ʱ�ȭ)\n");
			
			switch(state) {
			case 1: 
				if(!view())
					continue;System.out.println();
				
				for(TopicVO e : tvo1)
					System.out.println(e);
				
				
				loop2:
					while(true) {
						option = cv.checkInt("������ ���� ��ȣ�� �����ϼ��䡻 :  ");	
						for(int i=1; i<=numOfTopics; i++) {	
							if(option == i)
								break loop2;
							else
								System.out.print("�ش� ��ȣ�� �����ϴ�, ");
						}
					}
				
				ArrayList<Object[]> op2 = idao.selectItem(option);

				int viewNum = 1;
				System.out.println("\n\t��"+tdao.selectTopic(option)+" ���������");
				for(Object[] o : op2) {
					System.out.println(String.format("%d.%-10s",viewNum,o[0]));
					viewNum++;
				}
				
				int addOption = idao.countOption(option)+1; 
				System.out.printf("%d.%-10s\n",addOption,"�����Է��ϱ�");
				
				loop3:
					while(true) {
						int choiceOption = cv.checkInt("����ȣ�� �����ϼ��䡻 :  ");
						if(choiceOption < addOption && choiceOption > 0) {
							idao.choice(choiceOption,option);
							break;
						}else if(choiceOption == addOption) {
							
							System.out.print("\n"+choiceOption+"�� �׸��� �Է��ϼ��� : ");
							String tempOp=scan.nextLine();
							idao.choiceOther(option ,tempOp, choiceOption);
							System.out.println();
							break;
						}else {
							System.out.print("�ش� ��ȣ�� �����ϴ�, ");
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
						System.out.println(String.format("��\t %-10s \t\t%dǥ",o[0],o[1]));
					System.out.println();
				}
				System.out.println();
				break;
			
			case 3:
				String text = cv.checkTopic("\n�������� �Է��ϼ��䡻 :  ");
				tdao.createTopic(text); 
				System.out.println("\n���� �ݿ��Ϸ�");

				for(int i=1; i<=3; i++) {
					System.out.print("��"+i+"�� �׸��� �Է��ϼ��� ��: ");
					idao.createOption(tdao.getNOT(),scan.nextLine(),i,0);
				}System.out.println();
				
				break;
		
			case 4:
				flag = false;break;
				
			case 0:
				tdao.initialization();
				break;
			
			default : 
				System.out.println("�ش� ��ȣ�� �����ϴ�");
				break;
			}
		}
		scan.close();
	}
}


