
public final class Looper {
	
	//每一个主线程都会有一个Looper
	//Looer 对象保存在ThreaLocal
	static final ThreadLocal<Looper> sThreadLocal = new ThreadLocal<Looper>();
	
	//一个Looper对象，对应一个消息队列
	MessageQueue mQueue;
	
	//Looper对象的初始化
	public static void perpare(){
		if(sThreadLocal.get()!=null){
			throw new RuntimeException("Only one Looper may be created per thread");
		}
		sThreadLocal.set(new Looper());
	}
	//获取当前线程的Looper对象
	public static Looper myLooper(){
		return sThreadLocal.get();
	}
	public Looper(){
		mQueue = new MessageQueue();
	}
	//轮询消息队列
	public static void loop(){
		Looper me = myLooper();
		if(me == null){
			throw new RuntimeException("No looper");
		}
		MessageQueue queue = me.mQueue;
		for (; ; ) {
			Message msg = queue.next();
			if(msg == null){
				continue;
			}
			//转发给handler
			msg.target.dispatchMessage(msg);
		}
	}
}
