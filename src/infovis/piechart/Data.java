package infovis.piechart;

<<<<<<< HEAD
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Data {
	
	private int year = 0;
	String name = "";
	private String path = "";
	private int level = 0;
	ArrayList<String> parent_list = new ArrayList<>();
	private static String[] months_ordered = {"Januar", "Februar", "M�rz", "April", "Mai", "Juni", "Juli", "August", "September", "Oktober", "November", "Dezember"};
	
	private HashMap<String, Data> children = new HashMap<>();
	private HashMap<String, Integer> values = new HashMap<>();
	
	public Data(int y, String n) throws IOException {
		year = y;
		name = n;
		readData();
		
		createChildren();
	}
	
	public Data(int y, int lvl, String n, String p, ArrayList<String> p_list) throws IOException {
//		System.out.println("");
//		System.out.println("new Data");
		
		year = y;
		level = lvl + 1;
		name = n;
		path = p;
		parent_list = p_list;
		
		readData();
		
		if (level < 3) {
			createChildren();
		}
	}
	
	private void createChildren() throws IOException {
//		System.out.println("Create Children path " + path);
		BufferedReader reader = new BufferedReader(new FileReader(path));
		String line = null;
		System.out.println("Name: " + name + " Parent_list: " + parent_list);
		
//		for (String month : months_ordered) {
//			if (parent_list.contains(month)) {
//			
//		}
		if (parent_list.contains(name)) {
			System.out.println("List contains Name");
			return;
		} else {			
			ArrayList<String> new_parent_list = new ArrayList<String>(parent_list);		
			new_parent_list.add(name);
			
			while ((line = reader.readLine()) != null) {
				
				String[] words = line.split(";");
				if (words[0].equals("year")) {
					for (String word : words) {
						Data data = null;
						if (word.equals("year") || word.equals("number")) {
							
						} else if ((name.equals("sex")) && word.equals("male")) {
							data = new Data(year, level, "male", path, new_parent_list);
							
						} else if ((name.equals("sex")) && word.equals("female")) {
							data = new Data(year, level, "female", path, new_parent_list);
							
						} else if ((!name.equals("sex")) && word.equals("female") && !name.equals("female") && !name.equals("male")) {
							data = new Data(year, level, "sex", path, new_parent_list);
						
						} else if ((!name.equals("sex")) && word.equals("male")) {
							
						} else if ((name.equals("month"))) {
							data = new Data(year, level, "Januar", path, new_parent_list);
							children.put(data.name, data);						
							data = new Data(year, level, "Februar", path, new_parent_list);
							children.put(data.name, data);						
							data = new Data(year, level, "M�rz", path, new_parent_list);
							children.put(data.name, data);						
							data = new Data(year, level, "April", path, new_parent_list);
							children.put(data.name, data);						
							data = new Data(year, level, "Mai", path, new_parent_list);
							children.put(data.name, data);						
							data = new Data(year, level, "Juni", path, new_parent_list);
							children.put(data.name, data);						
							data = new Data(year, level, "Juli", path, new_parent_list);
							children.put(data.name, data);						
							data = new Data(year, level, "August", path, new_parent_list);
							children.put(data.name, data);						
							data = new Data(year, level, "September", path, new_parent_list);
							children.put(data.name, data);						
							data = new Data(year, level, "Oktober", path, new_parent_list);
							children.put(data.name, data);						
							data = new Data(year, level, "November", path, new_parent_list);
							children.put(data.name, data);						
							data = new Data(year, level, "Dezember", path, new_parent_list);
							
							
						} else {
							if (name != word && !parent_list.contains(word)) {							
								data = new Data(year, level, word, path, new_parent_list);
							}
						}
						
						if (data != null) {
							children.put(data.name, data);						
						}
							
						
					}
				} else {
					reader.close();
	//				System.out.println(name + "'s children: " + children);
					return;
				}
			}
		}
	}
	

	private void readData() throws IOException {
		
		if (name.equals("birth")) {
			path = "data/Geburten_Monat_Sex_1950-2015.csv";
			readBirth();
		} else if (name.equals("death")) { 
			path = "data/Tode_Monat_Sex_1950-2015.csv";
			readDeath();
		} else if (name.equals("month")) { 
			readMonth();
		} else if (name.equals("sex")) {
			readSex();
		} else {
			//...
		}
//		System.out.println(name + path);
				
	}
	
	
	
	private void readBirth() throws NumberFormatException, IOException {
//		System.out.println("path" + path);
		BufferedReader reader = new BufferedReader(new FileReader(path));
		String line = null;
		String[] header = null;
		
		int number = 0;
		
		while ((line = reader.readLine()) != null) {
			
			String[] words = line.split(";");
			if (words[0].equals("year")) {
				header = words;
				
			} else {
				if (words[0].equals(year + "")) {
//					System.out.println("CHECK");
					number += Integer.parseInt(getValueOfCell(header, words, "number"));
				}
			}
		}
		values.put("birth", number);
		reader.close();
		
	}
	
	private void readDeath() throws NumberFormatException, IOException {
		BufferedReader reader = new BufferedReader(new FileReader(path));
		String line = null;
		String[] header = null;
		
		int number = 0;
		
		while ((line = reader.readLine()) != null) {
			
			String[] words = line.split(";");
			if (words[0].equals("year")) {
				header = words;
				
			} else {
				if (words[0].equals(year + "")) {
					number += Integer.parseInt(getValueOfCell(header, words, "number"));
				}
			}
		}
		values.put("death", number);
		
		reader.close();
	}
	
	
	
	private void readSex() throws NumberFormatException, IOException {
		BufferedReader reader = new BufferedReader(new FileReader(path));
		String line = null;
		String[] header = null;
		
		int number_male = 0;
		int number_female = 0;
		
		while ((line = reader.readLine()) != null) {
			
			String[] words = line.split(";");
			if (words[0].equals("year")) {
				header = words;
				
			} else {
				if (words[0].equals(year + "")) {
					number_male += Integer.parseInt(getValueOfCell(header, words, "male"));
					number_female += Integer.parseInt(getValueOfCell(header, words, "female"));
				}
			}
		}
		values.put("male", number_male);
		values.put("female", number_female);
		
		reader.close();
	}
	

	private void readMonth() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(path));
		String line = null;
		String[] header = null;
		
		while ((line = reader.readLine()) != null) {
			
			String[] words = line.split(";");
			if (words[0].equals("year")) {
				header = words;
				
			} else {
				if (words[0].equals(year + "")) {
					String value = getValueOfCell(header, words, "month");
					int number = Integer.parseInt(getValueOfCell(header, words, "number"));
					values.put(value, number);
				}
			}
		}
		reader.close();
	}
	

	private String getValueOfCell(String[] header, String[] words, String name) {
//		System.out.println("header " + header);
//		System.out.println("words " + words);
//		System.out.println("name " + name);
		for (int i = 0; i < header.length; i++) {
			if (header[i].equals(name)) {
				return words[i];
			}
		}
		return null;
	}
	
	public HashMap<String, Integer> getValues() {
		return values;
	}
	
	public HashMap<String, Data> getChildrenMap() {
		return children;
	}
	
	
	
	
}
=======
public class Data {
	
	public Pair number = new Pair();
	public Pair male = new Pair();
	public Pair female = new Pair();
	
	// birth information
	
	
	// death information
	
	
	public void setBirth(int male, int female, int number) {
		this.number.birth = number;
		this.male.birth = male;
		this.female.birth = female;
	}
	
	
	public void setDeath(int male, int female, int number) {
		this.number.death = number;
		this.male.death = male;
		this.female.death = female;
	}
	
	
	public String print() {
		String result = "number: " + number.print();
		result += "male: " + male.print();
		result += "female: " + female.print();
		return result;
	}
	
}
>>>>>>> 6c1fcd73997e10b62aff6083292e7bb6ae9bd5f6
