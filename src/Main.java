/*
 * Main
 * AUTHORS:
 * 	Gonzales, Ma. Pauline
 * 	Sanchez, Mark Stephen
 * 	Sta. Ana, Yves Robert
 * 	Vaethbrueckner, Jan-Josel
 * References: 
 *	http://www.fun2code.de/articles/multicast_images_java/multicast_images_java.html
 */
public class Main {
	public static void main(String[] args) {
		System.setProperty("java.net.preferIPv4Stack" , "true");
		new Login().show();
	  }
}
