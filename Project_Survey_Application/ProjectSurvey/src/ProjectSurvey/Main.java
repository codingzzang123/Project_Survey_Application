package ProjectSurvey;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import Database.*; //��Ű�� import

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
			System.out.println("\n�������� ���� ������ּ���.��");
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
		
		System.out.print("  ����ȯ���մϴ�.�ڡ�");
		
		loopMenu:
		while(flag){
			
			state=cv.checkInt("\n1.\t�����Ϸ�����\n2.\t���� ��Ȳ����"
					+ "\n3.\t���� ���� �����\n4.\t����(0��=�ʱ�ȭ)\n�ѹ�ȣ�� �����ϼ��� �� :  ");			
			switch(state) {
			
			case 1: // 1. �����Ϸ�����
				if(!view())
					continue;System.out.println();
				
				loop1:
					while(true) {
						for(TopicVO e : tvo1)
							System.out.println(e);
						loop2:
							while(true) {
								option = cv.checkInt("������ ���� ��ȣ�� �����ϼ��� (-1�Է½� �ڷΰ���)�� :  ");
								if(option==-1)continue loopMenu;
								
								for(int i=1; i<=numOfTopics; i++) {	
									if(option==i)
										break loop2;
								}
								System.out.print("���ش� ��ȣ�� �����ϴ�, ");
								continue loop2;
							}
						
						ArrayList<Object[]> op2 = idao.selectItem(option);
		
						int viewNum = 1;
						System.out.println("\n\t��"+tdao.selectTopic(option)+" ���������");
						for(Object[] o : op2) {
							System.out.println(String.format("%d.%-10s",viewNum,o[0]));
							viewNum++;
						}

						int addOption = idao.getAddOption()+1;
						System.out.printf("%d.%-10s\n",addOption,"�����Է��ϱ�");
						
						
						while(true) {
							int choiceOption = cv.checkInt("����ȣ�� �����ϼ��� (-1�Է½� �ڷΰ���) �� :  ");
							System.out.println();
							if(choiceOption==-1)continue loop1;
							
							if(choiceOption < addOption && choiceOption > 0) {
								idao.choice(choiceOption,option);
								break loop1;
							}else if(choiceOption == addOption) {
								
								while(true) {
									System.out.print(choiceOption+"�� �׸��� �Է��ϼ��� : ");
									String tempOp=scan.nextLine();
								
									if(idao.checkOption(tempOp,option)) { 
										System.out.print("�ߺ��� �����Ͱ� �̹� �����մϴ�.");
										continue;
									}else { 
										idao.choiceOther(option ,tempOp, choiceOption);
										break loop1;
									}
								}
								
							}else {
								System.out.print("���ش� ��ȣ�� �����ϴ�2, ");
								continue;
							}
						}
					}
				break;
				
			case 2: // 2. ���� ��Ȳ ����
				if(!view())
					continue;
				
				System.out.println(); 
				for(TopicVO e : tvo1) {
					int sum = idao.totalcount(e.getNum());
					System.out.println(e+"\t\t"+"( "+sum+"�� ���� )");
					ArrayList<Object[]> op = idao.selectItem(e.getNum());
					
					for(Object[] o : op) {
						System.out.print(String.format("��\t %-10s \t\t%dǥ\t",o[0],o[1]));
						System.out.printf("( %.1f%% )\n",persent((Integer)o[1],sum));
					}
					System.out.println();
				}
				break;
			
			case 3: // 3. ���� ���� �����
				
				while(true) {
					String text = cv.checkTopic("\n�������� �Է��ϼ��䡹(-1�Է½� �ڷΰ���) :  ");
					if(text.equals("-1"))
						continue loopMenu;
					
					if(tdao.checkTopic(text)) { 
						System.out.println("�ߺ��� �����Ͱ� �̹� �����մϴ�. ");
						continue;
					}else {
						tdao.createTopic(text); 
						System.out.println("\n���� �ݿ��Ϸ�");
						 break;
					}
				}
				
				for(int i=1; i<=3; i++) {
					System.out.print("��"+i+"�� �׸��� �Է��ϼ��� ��: ");
					String op=scan.nextLine();
					if(idao.checkOption(op,tdao.getNOT())) { 
						System.out.print("�ߺ��� �����Ͱ� �̹� �����մϴ�.");
						i--;
					}else { 
						idao.createOption(tdao.getNOT(),op,i,0);
					}
				}System.out.println();
								
				break;
		
			case 4: // ����
				flag = false; System.out.println("\n���ø����̼� ����.");break;
				
			case 0: // �ʱ�ȭ
				System.out.print("\n���� �ʱ�ȭ �Ͻðڽ��ϱ�..(y/n) : ");
				String init=scan.nextLine(); System.out.println();
				
				if(init.equals("y")||init.equals("Y")) {
					tdao.initialization();
					break;
				}else {
					System.out.println("�ʱ�ȭ ���..\n");
					break;
				}
			
			default : 
				System.out.println("���ش� ��ȣ�� �����ϴ�");
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


