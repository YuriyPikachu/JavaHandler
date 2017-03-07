
public class Handler {
	private MessageQueue mQueue;
	private Looper mLooper;
	
	//handler�ĳ�ʼ���������̵߳�����ɡ�
	public Handler() {
		mLooper = Looper.myLooper();
		this.mQueue= mLooper.mQueue;
	}
	/**
	 * ������Ϣ��ѹ�����
	 * @paraam msg
	 * 
	 */
	public void sendMessage(Message msg){
		msg.target = this;
		mQueue.enqueueMessage(msg);
	}
	
	public void handleMessage(Message msg){
		
	}
	
	public void dispatchMessage(Message msg){
		handleMessage(msg);
	}
}
