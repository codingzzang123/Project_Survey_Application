package Database;

public class TopicVO {
	private int num;
	private String topic; 
	 
	TopicVO(){}
	TopicVO(int num,String topic){
		super();
		this.num = num; this.topic = topic;
	}
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	@Override
	public String toString() {
		return num + ".\t" + topic;
	}
	
	
}