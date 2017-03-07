
public class Handler {
	private MessageQueue mQueue;
	private Looper mLooper;
	
	//handler的初始化，在主线程当中完成。
	public Handler() {
		mLooper = Looper.myLooper();
		this.mQueue= mLooper.mQueue;
	}
	/**
	 * 发送消息，压入队列
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
