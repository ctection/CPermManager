package cscm;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class SCMBuilder {
	
	private LinkedHashMap<String, LinkedHashMap<String, Object>> datasets = new LinkedHashMap<>();
	private boolean isPrettyPrinting = false;
	public SCMBuilder() {
		
	}
	
	public SCMBuilder(HashMap<String, Object> dataset) {
		datasets.put(""+(datasets.size()+1), new LinkedHashMap<>(dataset));
	}
	
	public SCMBuilder(Map<String, Object>[] pdatasets) {
		for(int i = 0; i<pdatasets.length; i++) {
			datasets.put(""+(datasets.size()+1), new LinkedHashMap<>(pdatasets[i]));
		}
	}
	
	public SCMBuilder(String[] dataset) {
		LinkedHashMap<String, Object> t_hm = new LinkedHashMap<>();
		for(int i = 0; i<dataset.length; i++) {
			t_hm.put(i+"", dataset[i]);
		}
		datasets.put(""+(datasets.size()+1), t_hm); 
	}
	
	public SCMBuilder setPrettyPrinting(boolean prettyPrinting) {
		this.isPrettyPrinting = prettyPrinting;
		return this;
	}

	public SCMBuilder add(Map<String, Object> dataset) {
		datasets.put(""+(datasets.size()+1), new LinkedHashMap<>(dataset));
		return this;
	}
	
	public SCMBuilder add(int position, Map<String, Object> dataset) {
		datasets.put(""+position, new LinkedHashMap<String, Object>(dataset));
		return this;
	}
	
	public SCMBuilder add(String[] dataset) {
		LinkedHashMap<String, Object> t_hm = new LinkedHashMap<>();
		for(int i = 0; i<dataset.length; i++) {
			t_hm.put(i+"", dataset[i]);
		}
		datasets.put(""+(datasets.size()+1), t_hm);
		return this;
	}
	
	public SCMBuilder add(int position, String[] dataset) {
		LinkedHashMap<String, Object> t_hm = new LinkedHashMap<>();
		for(int i = 0; i<dataset.length; i++) {
			t_hm.put(i+"", dataset[i]);
		}
		datasets.put(""+position, t_hm);
		return this;
	}
	
	public SCMBuilder addMultiple(Map<String, Object>[] pdatasets) {
		for(int i = 0; i<pdatasets.length; i++) {
			datasets.put(""+(datasets.size()+1), new LinkedHashMap<String, Object>(pdatasets[i]));
		}
		return this;
	}
	
	public SCMBuilder addMultiple(int start_at, Map<String, Object>[] pdatasets) {
		for(int i = 0; i<pdatasets.length; i++) {
			datasets.put(""+(start_at+1), new LinkedHashMap<String, Object>(pdatasets[i]));
		}
		return this;
	}

	public SCMBuilder addArray(String name, Collection<String> collection) {
		LinkedHashMap<String, Object> t_hm = new LinkedHashMap<>();
		t_hm.put(name, "{" + String.join(",", collection) + "}");
		datasets.put(""+(datasets.size()+1), t_hm);
		return this;
	}

	
	public String build() {
		String temp = "";
		for(Map.Entry<String, LinkedHashMap<String, Object>> entry : datasets.entrySet()) {
			String key = entry.getKey();
			LinkedHashMap<String, Object> value = entry.getValue();
			for(Map.Entry<String, Object> h_entry : value.entrySet()) {
				String d_key = h_entry.getKey();
				String d_value = h_entry.getValue().toString().replace("[","{").replace("]","}").replace(" ", "");
				
				d_key = d_key.replaceAll("(?<!\\\\)(?:\\\\\\\\)*,", "\\,").replaceAll("(?<!\\\\)(?:\\\\\\\\)*:", "\\:").replaceAll("(?<!\\\\)(?:\\\\\\\\)*;", "\\;");
				d_value = d_value.replaceAll("(?<!\\\\)(?:\\\\\\\\)*,", "\\,").replaceAll("(?<!\\\\)(?:\\\\\\\\)*:", "\\:").replaceAll("(?<!\\\\)(?:\\\\\\\\)*;", "\\;");
				
				temp = temp + d_key + ":" + d_value + ",";
				if (isPrettyPrinting)
					temp = temp + "\n";
			}
			if (temp.length() >= 1 && temp.charAt(temp.length()-1) == ',') {
				temp = temp.substring(0, temp.length() -1) + ";";
				if (isPrettyPrinting)
				temp = temp + "\n\n";
			}
			else{
				temp = temp + ";";
				if (isPrettyPrinting)
					temp = temp + "\n\n";
			}
		}
		return temp;
	}
	
}
