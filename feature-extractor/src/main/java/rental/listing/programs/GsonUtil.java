package rental.listing.programs;

import com.google.gson.Gson;

public class GsonUtil {
	
	private static Gson INSTANCE = new Gson();
	
	public static Gson getGson(){
		if(GsonUtil.INSTANCE ==  null){
			INSTANCE = new Gson();
		}
		return INSTANCE;
	}
	
	public static void main(String...args){
		String str = "**8th & lexington ave*.";
		str = str.replaceAll("[:!/-]","_");
		System.out.println(str);
		str = str.replaceAll("&(?!amp;)","_");
		System.out.println(str);
		str = str.replaceAll("\\s+","_");
		System.out.println(str);
		str = str.replaceAll("_+","_");
		System.out.println(str);
		str = str.replaceAll("\\*+","");
		System.out.println(str);
		//^((?!pre-war).)*$
		System.out.println("[]".split(",").length);
		
	}
}










