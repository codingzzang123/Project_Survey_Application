package Database;

public class ItemVO {
	private int num;
	private int topicNum;
	private String items;
	private int count;
	private int option;

	ItemVO(){}
	ItemVO(int num,int topicNum,String items,int count,int option){
		this.num=num;
		this.topicNum=topicNum;
		this.items=items;
		this.count=count;
		this.option=option;	
	}
	ItemVO(String items,int count){
		this.items=items;
		this.count=count;
	}

	public int getNum() {
		return num;
	}


	public void setNum(int num) {
		this.num = num;
	}


	public int getTopicNum() {
		return topicNum;
	}


	public void setTopicNum(int topicNum) {
		this.topicNum = topicNum;
	}


	public String getItems() {
		return items;
	}


	public void setItems(String items) {
		this.items = items;
	}


	public int getCount() {
		return count;
	}


	public void setCount(int count) {
		this.count = count;
	}


	public int getOption() {
		return option;
	}


	public void setOption(int option) {
		this.option = option;
	}
	
}
