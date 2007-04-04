/**
 * @author The Blue Overdose Project
 * E-mail: blueover AT gmail com
 * Phlooder Website: http://code.google.com/p/phlooder
 * */

/** 
 * The flooder thread. Calls <code>form.send()</code>. 
 * TODO: Test pause with multiple sites!
 * */
	class PhlooderThread extends Thread{
		
		private Thread blinker;
		private int threadCheck;
		private Form form;
		private boolean threadSuspended; 
		
		public PhlooderThread(Form f){
			form=f;
			threadCheck=0;
		}
		
		/**The original stop() method is unsafe!
		 * This method is to be used instead of that!
		 * I don't know if this solution is good enough, but 
		 * the one on sun.com doesn't work :P
		 * */
		public void pause(){
			//threadSuspended=false;
			blinker=null;
			//notify();
			System.out.println(threadCheck+" requests sent.");
		}
	/*	public boolean isStarted(){
			return blinker;
		}
		public void restart(){
			threadSuspended=true;
		}*/
		public void start(){
			blinker=new Thread(this);
			blinker.start();
		}
		public void run(){
			Thread thisThread=Thread.currentThread();
			threadCheck=0;
			System.out.println(form.toString());
			while(blinker==thisThread){
				
				try{
					form.send();
					threadCheck++;
					if (threadSuspended){
						synchronized(this){
							while(threadSuspended){
								wait();
							}
						}
					}
					sleep(1000);
				}catch(InterruptedException ie){
					System.out.println("Interrupt cought!");
					return;
				}
			}
		}
	}