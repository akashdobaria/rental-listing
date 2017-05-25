package rental.listing.programs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FeatureExtractor {

	static Map<String,Integer> superSetFeatureMap = new LinkedHashMap<String,Integer>();

	public static void main(String...args){
		try{
			List<Listing> rentalListing = new ArrayList<Listing>();
			FileReader reader = new FileReader("C:\\Users\\shekh\\Desktop\\rentallisting\\trained_data.csv");
			BufferedReader bReader = new BufferedReader(reader);
			String input = null;
			while((input = bReader.readLine()) != null){
				try{
					int length = input.trim().split("\t").length;
					String[] tempArray = input.trim().split("\t");
					if(length == 16){
						if("bathrooms".equals(tempArray[1])){
							continue;
						}
						Listing listing = new Listing();
						listing.setNoOfBathrooms(tempArray[1]);
						listing.setNoOfBedrooms(tempArray[2]);
						listing.setBuildingId(tempArray[3]);
						listing.setCreated(tempArray[4]);
						
						String tempDesc = tempArray[5];
						tempDesc = tempDesc.replaceAll(";", "");
						listing.setDescription(tempDesc);
						
						String displayAddress = tempArray[6];
						displayAddress = displayAddress.replaceAll(";","");
						
						listing.setDisplayAddress(displayAddress);
						listing.setInterestLevel(tempArray[8]);
						listing.setLatitude(tempArray[9]);
						listing.setListingId(tempArray[10]);
						listing.setLongitude(tempArray[11]);
						listing.setManagerId(tempArray[12]);
						listing.setPhotos(tempArray[13]);
						if(tempArray[13].equals("[]")){
							listing.setNoOfPhotos(0);
						}else{
							String[] photos = tempArray[13].split(",");
							listing.setNoOfPhotos(photos.length);
						}
						
						String price = tempArray[14];
						if(price==null || price.isEmpty()){
							price = "0.0";
						}
						listing.setPrice(price);
						
						listing.setStreetAddress(tempArray[15]);
						String tempFeatures = tempArray[7];
						tempFeatures = tempFeatures.substring(1,tempFeatures.length()-1);
						listing.setFeatures(Arrays.asList(tempFeatures.split(",")));
						removeDelimitersFromListingFeatures(listing);
						rentalListing.add(listing);
					}
				}catch(Exception e){
					System.out.println(input);
					e.printStackTrace();
				}
			}
			processRentalListings(rentalListing);
			generateExtrapolatedCSV(rentalListing);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void removeDelimitersFromListingFeatures(Listing listing){
		List<String> tempList = new ArrayList<String>();
		for(String feature:listing.getFeatures()){
			feature = feature.toLowerCase().trim();
			if(feature.contains("pre-war") || feature.contains("pre war")){
				feature = "pre-war";
			}
			if(feature.contains("post-war") || feature.contains("post war")){
				feature = "post-war";
			}
			feature = feature.replaceAll("\\bprewar\\b","pre-war");
			feature = feature.replaceAll("\\bpostwar\\b","post-war");
			feature = feature.replaceAll("\\bdishwasher\\b","dish_washer");
			feature = feature.replaceAll("\\bin\\b","");
			feature = feature.replaceAll("\\bunit\\b","");
			feature = feature.replaceAll("\\bft\\b","full_time");
			feature = feature.replaceAll("\\bhi\\b","high");
			feature = feature.replaceAll("and","");
			feature = feature.replaceAll("much","");
			feature = feature.replaceAll("more","");
			feature = feature.replaceAll("by","");
			feature = feature.replaceAll("\\(s\\)","");
			feature = feature.replaceAll("building","bldg");
			feature = feature.replaceAll("pets","pet");
			feature = feature.replaceAll("pets","pet");
			feature = feature.replaceAll("\\bokay\\b","ok");
			feature = feature.replaceAll("dogs","dog");
			feature = feature.replaceAll("cats","cat");
			
			feature = feature.replaceAll("[:!/-]","_");
			feature = feature.replaceAll("&(?!amp;)","_");
			feature = feature.replaceAll("\\s+","_");
			feature = feature.replaceAll("_+","_");
			feature = feature.replaceAll("\\*+","");
			feature = feature.replaceAll("24_7","full_time");
			feature = feature.replaceAll("\\.","");
			feature = feature.replaceAll("_in_","_");
			feature = feature.replaceAll("_+","_");
			feature = feature.replaceAll("'","");
			feature = feature.replaceAll("^_+","");
			feature = feature.replaceAll("_+$","");
			
			feature = feature.replaceAll("dog_ok","dog_allowed");
			feature = feature.replaceAll("cat_ok","cat_allowed");
			tempList.add(feature);
		}
		listing.setFeatures(tempList);
	}

	public static void processRentalListings(List<Listing> rentalListing){
		Set<String> uniqueFeatures = new HashSet<String>();	
		for(Listing listing:rentalListing){
			for(String feature:listing.getFeatures()){
				if(!"".equals(feature) && feature!=null){
					uniqueFeatures.add(feature);
				}
			}
		}

		for(String uniqueFeature:uniqueFeatures){
			superSetFeatureMap.put(uniqueFeature, 0);
		}
		
		System.out.println("Unique Features: ");
		System.out.println(uniqueFeatures);

		for(Listing listing:rentalListing){
			Map<String,Integer> featureMap = new HashMap<String,Integer>(superSetFeatureMap);
			for(String feature:listing.getFeatures()){
				if(!"".equals(feature)){
					feature = feature.trim().toLowerCase();
					if(featureMap.get(feature) == 0){
						featureMap.put(feature,1);
					}
				}
			}
			listing.setExpandedFeatures(featureMap);
		}
	}

	public static void generateExtrapolatedCSV(List<Listing> rentalListing){
		StringBuilder headers = new StringBuilder("bathrooms;bedrooms;building_id;created;description;display_address;interest_level"
				+ ";latitude;listing_id;longitude;manager_id;no_of_photos;photos;price;street_address");
		for(String key:superSetFeatureMap.keySet()){
			headers.append(";").append(key);
		}

		System.out.println(headers);

		try{
			FileWriter writer = new FileWriter("C:\\Users\\shekh\\Desktop\\rentallisting\\extrapolated_trained_data_new.csv");
			BufferedWriter bWriter = new BufferedWriter(writer);
			PrintWriter out = new PrintWriter(bWriter,true);
			out.println(headers);
			
			for(Listing listing:rentalListing){
				Map<String,Object> tempMap = new LinkedHashMap<String,Object>();
				tempMap.put("bathrooms", listing.getNoOfBathrooms());
				tempMap.put("bedrooms", listing.getNoOfBedrooms());
				tempMap.put("building_id", listing.getBuildingId());
				tempMap.put("created", listing.getCreated());
				tempMap.put("description", listing.getDescription());
				tempMap.put("display_address", listing.getDisplayAddress());
				tempMap.put("interest_level", listing.getInterestLevel());
				tempMap.put("latitude", listing.getLatitude());
				tempMap.put("listing_id", listing.getListingId());
				tempMap.put("longitude", listing.getLongitude());
				tempMap.put("manager_id", listing.getManagerId());
				tempMap.put("no_of_photos", listing.getNoOfPhotos());
				tempMap.put("photos", listing.getPhotos());
				tempMap.put("price", listing.getPrice());
				tempMap.put("street_address", listing.getStreetAddress());
				tempMap.putAll(listing.getExpandedFeatures());
				
				StringBuilder tempStr = new StringBuilder();
				for(String key:tempMap.keySet()){
					tempStr.append(tempMap.get(key)).append(";");
				}
				String outputStr = tempStr.substring(0,tempStr.length()-1);
				out.println(outputStr);
			}
			out.close();
			bWriter.close();
			writer.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}