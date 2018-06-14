package demo;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class TestTX {
	public boolean transMethod() throws InterruptedException {
	     Jedis jedis = new Jedis("127.0.0.1", 6379);
	     int balance;// 鍙敤浣欓
	     int debt;// 娆犻
	     int amtToSubtract = 10;// 瀹炲埛棰濆害

	     jedis.watch("balance");
	     //jedis.set("balance","5");//姝ゅ彞涓嶈鍑虹幇锛岃璇炬柟渚裤�妯℃嫙鍏朵粬绋嬪簭宸茬粡淇敼浜嗚鏉＄洰
	     Thread.sleep(7000);
	     balance = Integer.parseInt(jedis.get("balance"));
	     if (balance < amtToSubtract) {
	       jedis.unwatch();
	       System.out.println("modify");
	       return false;
	     } else {
	       System.out.println("***********transaction");
	       Transaction transaction = jedis.multi();
	       transaction.decrBy("balance", amtToSubtract);
	       transaction.incrBy("debt", amtToSubtract);
	       transaction.exec();
	       balance = Integer.parseInt(jedis.get("balance"));
	       debt = Integer.parseInt(jedis.get("debt"));

	       System.out.println("*******" + balance);
	       System.out.println("*******" + debt);
	       return true;
	     }
	  }

	  /**
	   * 閫氫織鐐硅锛寃atch鍛戒护灏辨槸鏍囪涓�釜閿紝濡傛灉鏍囪浜嗕竴涓敭锛�
	   * 鍦ㄦ彁浜や簨鍔″墠濡傛灉璇ラ敭琚埆浜轰慨鏀硅繃锛岄偅浜嬪姟灏变細澶辫触锛岃繖绉嶆儏鍐甸�甯稿彲浠ュ湪绋嬪簭涓�	   * 閲嶆柊鍐嶅皾璇曚竴娆°�
	   * 棣栧厛鏍囪浜嗛敭balance锛岀劧鍚庢鏌ヤ綑棰濇槸鍚﹁冻澶燂紝涓嶈冻灏卞彇娑堟爣璁帮紝骞朵笉鍋氭墸鍑忥紱 
	   * 瓒冲鐨勮瘽锛屽氨鍚姩浜嬪姟杩涜鏇存柊鎿嶄綔锛�	   * 濡傛灉鍦ㄦ鏈熼棿閿産alance琚叾瀹冧汉淇敼锛�閭ｅ湪鎻愪氦浜嬪姟锛堟墽琛宔xec锛夋椂灏变細鎶ラ敊锛�
	   * 绋嬪簭涓�甯稿彲浠ユ崟鑾疯繖绫婚敊璇啀閲嶆柊鎵ц涓�锛岀洿鍒版垚鍔熴�
	 * @throws InterruptedException 
	   */
	  public static void main(String[] args) throws InterruptedException {
	     TestTX test = new TestTX();
	     boolean retValue = test.transMethod();
	     System.out.println("main retValue-------: " + retValue);
	  }	
}
