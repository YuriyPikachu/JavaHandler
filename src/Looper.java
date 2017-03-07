
public final class Looper {
	
	//ÿһ�����̶߳�����һ��Looper
	//Looer ���󱣴���ThreaLocal
	static final ThreadLocal<Looper> sThreadLocal = new ThreadLocal<Looper>();
	
	//һ��Looper���󣬶�Ӧһ����Ϣ����
	MessageQueue mQueue;
	
	//Looper����ĳ�ʼ��
	public static void perpare(){
		if(sThreadLocal.get()!=null){
			throw new RuntimeException("Only one Looper may be created per thread");
		}
		sThreadLocal.set(new Looper());
	}
	//��ȡ��ǰ�̵߳�Looper����
	public static Looper myLooper(){
		return sThreadLocal.get();
	}
	public Looper(){
		mQueue = new MessageQueue();
	}
	//��ѯ��Ϣ����
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
			//ת����handler
			msg.target.dispatchMessage(msg);
		}
	}
}
