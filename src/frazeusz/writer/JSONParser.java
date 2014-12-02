package frazeusz.writer;

import java.io.File;
import java.util.Map;

import org.json.JSONObject;

public class JSONParser {
	
	public JSONParser() {}

	public void toJSON(File file, Map<Phrase, Integer> resultsToSave) {
		JSONObject calosc = new JSONObject();
		for (Map.Entry<Phrase, Integer> r : resultsToSave.entrySet()){
			JSONObject entry = new JSONObject();
			
			Phrase p = r.getKey();
			Integer i = r.getValue();
		}
	}



	public Map<Phrase, Integer> readFromJSON(File file) {
		return null;
	}
	
	
}
