package ProjectSurvey;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CheckValue {
	private Scanner scan2;
	public int checkInt(String input) {
		scan2= new Scanner(System.in);
		int number;
		System.out.print(input);
		loop:
			while(true) {
				try {
					number = scan2.nextInt();
					break;
				}catch(InputMismatchException e) {
					scan2 = new Scanner(System.in);
					System.out.print("\n���ٽ� �Է����ּ���..��: ");
					continue loop;
				}
			}
		return number;
	}
	
	public String checkTopic(String topic) {
		scan2 = new Scanner(System.in);
		String text;
		System.out.print(topic);
		loop:
			while(true) {
				text = scan2.nextLine();
				if(!(text.length()<6))
					break;
				else {
					System.out.print("\n�������̰ų�, �ʹ� ª���ϴ�..�ٽ� �Է��ϼ���.��: ");
					continue loop;
				}
			}
		return text;
	}
}
